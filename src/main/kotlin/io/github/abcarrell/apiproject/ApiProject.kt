package io.github.abcarrell.apiproject

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.ProjectActivity

object ApiProject {
    private var _projectInstance: Project? = null
    val projectInstance: Project get() = checkNotNull(_projectInstance) { "projectInstance not initialized" }

    internal class ManagerListener : ProjectManagerListener {
        override fun projectClosed(project: Project) {
            _projectInstance = null
            super.projectClosed(project)
        }
    }

    internal class PostStartupActivity : ProjectActivity {
        override suspend fun execute(project: Project) {
            _projectInstance = project
        }
    }
}

@Suppress("UNUSED_PARAMETER")
internal fun noOp(p0: Any? = null) = Unit
