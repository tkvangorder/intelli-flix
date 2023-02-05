package org.tkv.intelliflix.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IntelliFlixSettingsConfigurable implements Configurable {

    private IntelliFlixSettingsComponent settingsComponent;

    @Override
    public String getDisplayName() {
        return "IntelliFlix";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new IntelliFlixSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        IntelliFlixSettingsState settings = IntelliFlixSettingsState.getInstance();
        boolean modified = !settingsComponent.getOpenAiApiAccessKey().equals(settings.openaiApiAccessKey);
        return modified && !settingsComponent.getOpenaiApiUrl().equals(settings.openaiApiUrl);
    }

    @Override
    public void apply() {
        IntelliFlixSettingsState settings = IntelliFlixSettingsState.getInstance();
        settings.openaiApiAccessKey = settingsComponent.getOpenAiApiAccessKey();
        settings.openaiApiUrl = settingsComponent.getOpenaiApiUrl();
    }

    @Override
    public void reset() {
        IntelliFlixSettingsState settings = IntelliFlixSettingsState.getInstance();
        settingsComponent.setOpenAiApiAccessKey(settings.openaiApiAccessKey);
        settingsComponent.setOpenaiApiUrl(settings.openaiApiUrl);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
