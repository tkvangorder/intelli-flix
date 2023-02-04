package org.tkv.intelliflix.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

public class OpenChatAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() != null) {
            ToolWindow intelliFlix = ToolWindowManager.getInstance(e.getProject()).getToolWindow("IntelliFlix");
            if (intelliFlix != null) {
                intelliFlix.show(null);
            }
        }
    }
}
