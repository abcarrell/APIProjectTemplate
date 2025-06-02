package io.github.abcarrell.apiproject

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import com.intellij.openapi.project.Project

class AndroidStudioTemplateProvider : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> {
        return listOf(apiServiceTemplate, supportProjectTemplate)
    }
}
