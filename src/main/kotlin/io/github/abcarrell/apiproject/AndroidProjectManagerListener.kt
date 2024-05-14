package io.github.abcarrell.apiproject

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.ProjectActivity

internal class AndroidProjectManagerListener : ProjectManagerListener {
    override fun projectClosed(project: Project) {
        projectInstance = null
        super.projectClosed(project)
    }
}

internal class AndroidProjectActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        projectInstance = project
    }
}