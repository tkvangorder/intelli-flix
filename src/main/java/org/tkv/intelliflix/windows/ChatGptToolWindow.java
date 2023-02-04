package org.tkv.intelliflix.windows;

import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.ui.EditorTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import org.tkv.intelliflix.chatgpt.ChatResponse;
import org.tkv.intelliflix.chatgpt.CompletionRequest;
import org.tkv.intelliflix.services.IntelliFlixProjectService;

import javax.swing.*;
import java.awt.*;

public class ChatGptToolWindow {
    private final EditorTextField chatPrompt;
    private final JButton submitButton;
    private final EditorTextField resultPane;

    private final IntelliFlixProjectService projectService;

    public ChatGptToolWindow(IntelliFlixProjectService projectService) {
        this.projectService = projectService;
        chatPrompt = new WrappingEditorTextField();
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitButtonClicked());
        resultPane = new WrappingEditorTextField();
        resultPane.setEnabled(false);
    }

    public JPanel getContent() {

        return FormBuilder.createFormBuilder()
                .addLabeledComponent("Enter prompt:", chatPrompt)
                .addComponent(submitButton)
                .addComponentFillVertically(resultPane, 0)
                .getPanel();
    }

    public void submitButtonClicked() {
        String prompt = chatPrompt.getText();
        if (prompt.isEmpty()) {
            resultPane.setText("You must enter a prompt to get a response.");
        }
        String result;
        try {
            //Need to do this on a non-UI thread, how do I do that?
            ChatResponse response = projectService.getChatGptClient().sendCompletionRequest(CompletionRequest.builder().prompt(prompt).build());
            if (response.getChoices() != null && response.getChoices().size() > 0) {
                result = response.getChoices().get(0).getText();
            } else if (response.getError() != null)  {
                result = "ERROR: " + response.getError().getMessage();
            } else {
                result = "ERROR: No response from server.";
            }
            resultPane.setText(result);
        } catch (Exception e) {
            result = "ERROR: " + e.getMessage();
        }
        resultPane.setText(result);
    }


    private static class WrappingEditorTextField extends EditorTextField {

        @Override
        protected @NotNull EditorEx createEditor() {
            final EditorEx editor = super.createEditor();
            editor.setOneLineMode(false);
            editor.setCaretEnabled(true);
            editor.getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            editor.getComponent().setPreferredSize(new Dimension(200, 200));
            editor.getSettings().setUseSoftWraps(true);

            return editor;
        }
    }
}
