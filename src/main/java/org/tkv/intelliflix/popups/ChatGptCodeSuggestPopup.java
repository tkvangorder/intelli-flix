package org.tkv.intelliflix.popups;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.progress.impl.BackgroundableProcessIndicator;
import com.intellij.ui.EditorTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import org.tkv.intelliflix.chatgpt.ChatResponse;
import org.tkv.intelliflix.chatgpt.EditRequest;
import org.tkv.intelliflix.services.IntelliFlixProjectService;
import org.tkv.intelliflix.ui.WrappingEditorTextField;

import javax.swing.*;

public class ChatGptCodeSuggestPopup {
    private final EditorTextField chatPrompt;
    private final JButton submitButton;

    private final IntelliFlixProjectService projectService;
    private final Editor editor;

    private final EditorTextField errorPanel;

    public ChatGptCodeSuggestPopup(IntelliFlixProjectService projectService, Editor editor) {
        this.projectService = projectService;
        this.editor = editor;
        chatPrompt = new WrappingEditorTextField(500, 200);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitButtonClicked());
        errorPanel = new WrappingEditorTextField(200, 100);
        errorPanel.setEnabled(false);
    }

    public JPanel getContent() {

        return FormBuilder.createFormBuilder()
                .addLabeledComponent("Enter prompt:", chatPrompt)
                .addComponent(submitButton)
                .addComponent(errorPanel)
                .getPanel();
    }

    public void submitButtonClicked() {
        String prompt = chatPrompt.getText();
        if (prompt.isEmpty()) {
            errorPanel.setText("You must enter a prompt to get a response.");
        }
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        String selected = primaryCaret.getSelectedText();

        ChatGptTask task = new ChatGptTask(prompt, selected);

        ProgressManager.getInstance().runProcessWithProgressAsynchronously(
                task, new BackgroundableProcessIndicator(task)
        );
    }

    private class ChatGptTask extends Task.Backgroundable {

        private final String prompt;

        private final String input;

        private String result;

        private String error;

        public ChatGptTask(String prompt, String input) {
            super(projectService.getProject(), "Chat GPT");
            this.prompt = prompt;
            this.input = input;
        }

        @Override
        public void run(@NotNull ProgressIndicator indicator) {
            indicator.setIndeterminate(true);
            indicator.setText("Sending request to server...");


            try {

                ChatResponse response = projectService.getChatGptClient().sendEditRequest(
                        EditRequest.builder()
                                .instruction(prompt)
                                .input(input)
                                .build());

                if (response.getChoices() != null && response.getChoices().size() > 0) {
                    result = response.getChoices().get(0).getText();
                } else if (response.getError() != null) {
                    error = response.getError().getMessage();
                } else {
                    error = "No response from server.";
                }
            } catch (Exception e) {
                error = e.getMessage();
            }
            indicator.setText("Got response from server.");
        }

        @Override
        public void onSuccess() {
            if (result != null) {
                Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();

                // Replace the selected text with the result
                int start = primaryCaret.getSelectionStart();
                int end = primaryCaret.getSelectionEnd();
                WriteCommandAction.runWriteCommandAction(projectService.getProject(), () ->
                        editor.getDocument().replaceString(start, end, result)
                );

            } else if (error != null) {
                errorPanel.setText(error);
            }
        }
    }

}
