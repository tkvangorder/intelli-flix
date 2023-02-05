package org.tkv.intelliflix.windows;

import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.fields.IntegerField;
import com.intellij.util.ui.FormBuilder;
import org.tkv.intelliflix.chatgpt.ChatResponse;
import org.tkv.intelliflix.chatgpt.CompletionRequest;
import org.tkv.intelliflix.services.IntelliFlixProjectService;
import org.tkv.intelliflix.ui.WrappingEditorTextField;

import javax.swing.*;

public class ChatGptToolWindow {
    private final EditorTextField chatPrompt;

    private final IntegerField maxTokens;

    private final JButton submitButton;
    private final EditorTextField resultPane;

    private final IntelliFlixProjectService projectService;

    public ChatGptToolWindow(IntelliFlixProjectService projectService) {
        this.projectService = projectService;
        chatPrompt = new WrappingEditorTextField(200, 200);
        maxTokens = new IntegerField("maxTokens", 10, 500);
        maxTokens.setDefaultValue(50);
        maxTokens.setValue(50);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitButtonClicked());
        resultPane = new WrappingEditorTextField(200, 200);
        resultPane.setEnabled(false);
    }

    public JPanel getContent() {

        return FormBuilder.createFormBuilder()
                .addLabeledComponent("Enter prompt:", chatPrompt)
                .addLabeledComponent("Max tokens:", maxTokens)
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
            ChatResponse response = projectService.getChatGptClient().sendCompletionRequest(
                    CompletionRequest.builder()
                            .prompt(prompt)
                            .maxTokens(maxTokens.getValue())
                            .build());
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
}
