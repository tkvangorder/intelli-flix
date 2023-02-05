package org.tkv.intelliflix.services;

import com.intellij.openapi.project.Project;
import org.tkv.intelliflix.chatgpt.ChatGptClient;
import org.tkv.intelliflix.chatgpt.ChatGptEnvironment;
import org.tkv.intelliflix.settings.IntelliFlixSettingsState;

/**
 * This service is used to store the project level state for the IntelliFlix plugin.
 */
public class IntelliFlixProjectService {

    private final Project project;

    ChatGptClient chatGptClient;

    ChatGptEnvironment chatGptEnvironment;

    public IntelliFlixProjectService(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public ChatGptClient getChatGptClient() {
        //First load any properties from the user's home directory
        if (chatGptEnvironment == null) {
            chatGptEnvironment = new ChatGptEnvironment(
                    ChatGptEnvironment.loadProperties(System.getProperty("user.home") + "/.intelliflix/settings.properties")
            );
        }

        //Check if the settings in the IDE match that of the environment, if they do not, then we need to reinitialize the client
        IntelliFlixSettingsState settings = IntelliFlixSettingsState.getInstance();
        if (isNotEmpty(settings.openaiApiAccessKey) &&
                (chatGptEnvironment.getOpenaiApiAccessKey() == null || !chatGptEnvironment.getOpenaiApiAccessKey().equals(settings.openaiApiAccessKey))) {

            //If the environment has a different key than what is in the settings, then we need to reinitialize the client.
            chatGptEnvironment.setOpenaiApiAccessKey(settings.openaiApiAccessKey);
            chatGptClient = null;
        }
        if (isNotEmpty(settings.openaiApiUrl) &&
                (chatGptEnvironment.getOpenaiApiUrl() == null || !chatGptEnvironment.getOpenaiApiUrl().equals(settings.openaiApiUrl))) {
            //If the environment has a different URL than what is in the settings, then we need to reinitialize the client.
            chatGptEnvironment.setOpenaiApiUrl(settings.openaiApiUrl);
            chatGptClient = null;
        }

        if (chatGptClient == null) {
            chatGptClient = new ChatGptClient(chatGptEnvironment.getOpenaiApiAccessKey());
        }
        return chatGptClient;
    }

    boolean isNotEmpty(String s) {
        return s != null && !s.isEmpty();
    }

}
