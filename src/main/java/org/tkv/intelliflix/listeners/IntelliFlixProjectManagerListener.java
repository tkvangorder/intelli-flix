package org.tkv.intelliflix.listeners;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;
import org.tkv.intelliflix.services.IntelliFlixProjectService;

public class IntelliFlixProjectManagerListener implements ProjectManagerListener {

    @Override
    public void projectOpened(@NotNull Project project) {
        project.getService(IntelliFlixProjectService.class);
    }
}
