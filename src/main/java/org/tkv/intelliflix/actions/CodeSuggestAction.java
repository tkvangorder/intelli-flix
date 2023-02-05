package org.tkv.intelliflix.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import org.jetbrains.annotations.NotNull;
import org.tkv.intelliflix.popups.ChatGptCodeSuggestPopup;
import org.tkv.intelliflix.services.IntelliFlixProjectService;

/**
 * Code Suggest Action that is contextually available when a user has selected text in the editor.
 */
public class CodeSuggestAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            return;
        }
        IntelliFlixProjectService projectService = e.getProject().getService(IntelliFlixProjectService.class);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (projectService != null && editor != null) {
            ChatGptCodeSuggestPopup popup = new ChatGptCodeSuggestPopup(projectService, editor);
            JBPopupFactory.getInstance().createDialogBalloonBuilder(popup.getContent(), "Code Suggest")
                    .setHideOnAction(true)
                    .setHideOnClickOutside(true)
                    .createBalloon()
                    .showInCenterOf(e.getData(PlatformDataKeys.EDITOR).getComponent());
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        e.getPresentation().setEnabledAndVisible(
                project != null && editor != null && editor.getSelectionModel().hasSelection()
        );
    }
}
