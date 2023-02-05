package org.tkv.intelliflix.services;

import com.intellij.openapi.project.Project;
import org.tkv.intelliflix.chatgpt.ChatGptClient;
import org.tkv.intelliflix.chatgpt.ChatGptEnvironment;

public class IntelliFlixProjectService {

    private final Project project;

    ChatGptClient chatGptClient;

    public IntelliFlixProjectService(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public ChatGptClient getChatGptClient() {
        // TODO : If I get settings working, will need to check if the settings have changed and reinitialize the client.
        if (chatGptClient == null) {
            ChatGptEnvironment environment = new ChatGptEnvironment(
                    ChatGptEnvironment.loadProperties(System.getProperty("user.home") + "/.intelliflix/settings.properties")
            );
            chatGptClient = new ChatGptClient(environment.getOpenaiApiPersonalToken());
        }
        return chatGptClient;
    }
}
