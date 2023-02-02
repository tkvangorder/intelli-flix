package org.tkv.intelliflix.services;

import com.intellij.openapi.project.Project;

public class IntelliFlixProjectService {
    Project project;

    public IntelliFlixProjectService(Project project) {
        this.project = project;
        System.out.println("Project Name : " + project.getName());
    }

    public String fred() {
        return "yes, this is fred.";
    }
}
