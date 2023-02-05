package org.tkv.intelliflix.popups;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.ui.EditorTextField;
import com.intellij.util.ui.FormBuilder;
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
        String error = null;
        try {
            //Need to do this on a non-UI thread, how do I do that?
            String prompt = chatPrompt.getText();

            Document document = editor.getDocument();
            Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
            String selected = primaryCaret.getSelectedText();

            ChatResponse response = projectService.getChatGptClient().sendEditRequest(
                EditRequest.builder()
                        .instruction(prompt)
                        .input(selected)
                        .build());

            // Note: Result needs to be effectively final to be used in the lambda below.
            String result;
            if (response.getChoices() != null && response.getChoices().size() > 0) {
                result = response.getChoices().get(0).getText();
            } else if (response.getError() != null)  {
                error = response.getError().getMessage();
                result = null;
            } else {
                error = "No response from server.";
                result = null;
            }

            if (result != null) {
                // Replace the selected text with the result
                int start = primaryCaret.getSelectionStart();
                int end = primaryCaret.getSelectionEnd();
                WriteCommandAction.runWriteCommandAction(projectService.getProject(), () ->
                        document.replaceString(start, end, result)
                );

            }

        } catch (Exception e) {
            error = e.getMessage();
        }
        if (error != null) {
            errorPanel.setText(error);
        }
    }
}
