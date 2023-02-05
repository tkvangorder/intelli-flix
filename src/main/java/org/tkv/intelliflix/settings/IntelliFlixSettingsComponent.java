package org.tkv.intelliflix.settings;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class IntelliFlixSettingsComponent {
    private final JPanel settingsPanel;
    private final JBTextField openaiApiAccessKey = new JBTextField();

    private final JBTextField openaiApiUrl = new JBTextField();

    public IntelliFlixSettingsComponent() {
        openaiApiAccessKey.setToolTipText("The OpenAI API access key.");
        openaiApiUrl.setToolTipText("The URL of the OpenAI API endpoint. If not specified, the default OpenAI API endpoint will be used.");
        settingsPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("OpenAI API access key: "), openaiApiAccessKey, 1, false)
                .addLabeledComponent(new JBLabel("OpenAI API URL: "), openaiApiUrl, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return settingsPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return openaiApiAccessKey;
    }

    @NotNull
    public String getOpenaiApiUrl() {
        return openaiApiUrl.getText();
    }

    public void setOpenaiApiUrl(@NotNull String newText) {
        openaiApiUrl.setText(newText);
    }

    public String getOpenAiApiAccessKey() {
        return openaiApiAccessKey.getText();
    }

    public void setOpenAiApiAccessKey(@NotNull String newText) {
        openaiApiAccessKey.setText(newText);
    }

}
