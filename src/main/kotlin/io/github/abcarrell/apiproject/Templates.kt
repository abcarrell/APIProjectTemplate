package io.github.abcarrell.apiproject

import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.CheckBoxWidget
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.TemplateConstraint
import com.android.tools.idea.wizard.template.TemplateData
import com.android.tools.idea.wizard.template.TextFieldWidget
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.booleanParameter
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template
import java.io.File

val projectTemplate
    get() = template {
        name = "Activity w/ MVI"
        description =
            "Creates a simple application with MVI architecture and all necessary dependencies for calling an api."
        minApi = 24
        constraints = listOf(
            TemplateConstraint.AndroidX,
            TemplateConstraint.Kotlin
        )
        category = Category.Application
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.NewProject,
            WizardUiContext.NewProjectExtraDetail
        )
        val packageName = defaultPackageNameParameter

        val useRoom = booleanParameter {
            name = "Add Room Persistence Support"
            default = true
        }

        val useHilt = booleanParameter {
            name = "Add Hilt support"
            default = false
        }

        val useNavigation = booleanParameter {
            name = "Add Jetpack Navigation support"
            default = false
        }

        widgets(
            CheckBoxWidget(useRoom),
            CheckBoxWidget(useHilt),
            CheckBoxWidget(useNavigation),
            PackageNameWidget(packageName)
        )

        thumb {
            File("viewmodel-activity")
                .resolve("template_blank_activity.png")
        }

        recipe = { data: TemplateData ->
            projectRecipe(
                moduleData = data as ModuleTemplateData,
                packageName = packageName.value,
                useRoom = useRoom.value,
                useHilt = useHilt.value,
                useNavigation = useNavigation.value
            )
        }
    }

val apiTemplate
    get() = template {
        name = "API Service"
        description = "Create a Retrofit-enabled API service."
        constraints = listOf(
            TemplateConstraint.AndroidX,
            TemplateConstraint.Kotlin
        )
        category = Category.Other
        screens = listOf(
            WizardUiContext.NewProjectExtraDetail
        )

        val packageName = defaultPackageNameParameter

        val baseUrl = stringParameter {
            name = "Base Url for the API service"
            default = "https://localhost/api/"
        }

        widgets(
            TextFieldWidget(baseUrl),
            PackageNameWidget(packageName)
        )

    }