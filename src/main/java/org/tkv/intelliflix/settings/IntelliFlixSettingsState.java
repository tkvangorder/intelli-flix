package org.tkv.intelliflix.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "org.tkv.intelliflix.settings.IntelliFlixSettingsState", storages = @Storage("IntelliFlixPlugin.xml"))
public class IntelliFlixSettingsState implements PersistentStateComponent<IntelliFlixSettingsState> {

    public String openaiApiAccessKey = "";
    public String openaiApiUrl = "";

    public static IntelliFlixSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(IntelliFlixSettingsState.class);
    }

    @Override
    public @Nullable IntelliFlixSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull IntelliFlixSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }


}
