package org.tkv.intelliflix.windows;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.tkv.intelliflix.services.IntelliFlixProjectService;

public class ChatGptToolWindowFactory implements ToolWindowFactory {

    /**
     * Create the tool window content.
     *
     * @param project    current project
     * @param toolWindow current tool window
     */
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        IntelliFlixProjectService projectService = project.getService(IntelliFlixProjectService.class);
        ChatGptToolWindow chatWindow = new ChatGptToolWindow(projectService);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(chatWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
