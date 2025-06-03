package io.github.abcarrell.apiproject

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider

class AndroidStudioTemplateProvider : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> {
        return listOf(retrofitServiceTemplate, supportProjectTemplate, supportComposeProjectTemplate)
    }
}
