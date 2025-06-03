package io.github.abcarrell.apiproject

import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.CheckBoxWidget
import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.EnumWidget
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.TemplateConstraint
import com.android.tools.idea.wizard.template.TemplateData
import com.android.tools.idea.wizard.template.TextFieldWidget
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.booleanParameter
import com.android.tools.idea.wizard.template.enumParameter
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template
import java.io.File

val supportProjectTemplate
    get() = template {
        name = "ABC Support Activity"
        description =
            "Creates a simple application with all necessary dependencies for calling an api."
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
            name = "Add Room Persistence support"
            default = true
        }

        val dependencyInjection = enumParameter<DependencyInjection> {
            name = "Add Dependency Injection Framework support"
            default = DependencyInjection.None
        }

        val networkLibrary = enumParameter<NetworkLibrary> {
            name = "Add Network Library support"
            default = NetworkLibrary.None
        }

        val converter = enumParameter<RetrofitConverter> {
            name = "Retrofit Converter Library"
            default = RetrofitConverter.None
            visible = { networkLibrary.value == NetworkLibrary.Retrofit }
        }

        val useNavigation = booleanParameter {
            name = "Add Jetpack Navigation Support"
            default = false
        }

        widgets(
            CheckBoxWidget(useRoom),
            EnumWidget(networkLibrary),
            EnumWidget(converter),
            EnumWidget(dependencyInjection),
            CheckBoxWidget(useNavigation),
            PackageNameWidget(packageName)
        )

        thumb { File("viewmodel-activity").resolve("template_blank_activity.png") }

        recipe = { data: TemplateData ->
            supportProjectRecipe(
                moduleData = data as ModuleTemplateData,
                packageName = packageName.value,
                useRoom = useRoom.value,
                networkLibrary = networkLibrary.value,
                dependencyInjection = dependencyInjection.value,
                useNavigation = useNavigation.value,
                converter = if (networkLibrary.value == NetworkLibrary.Retrofit) converter.value else RetrofitConverter.None
            )
        }
    }

val supportComposeProjectTemplate
    get() = template {
        name = "ABC Support Activity (Compose)"
        description =
            "Creates a simple Compose application with all necessary dependencies for calling an api."
        minApi = 24
        constraints = listOf(
            TemplateConstraint.AndroidX,
            TemplateConstraint.Kotlin,
            TemplateConstraint.Compose
        )
        category = Category.Application
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.NewProject,
            WizardUiContext.NewProjectExtraDetail
        )
        val packageName = defaultPackageNameParameter

        val useRoom = booleanParameter {
            name = "Add Room Persistence support"
            default = true
        }

        val dependencyInjection = enumParameter<DependencyInjection> {
            name = "Add Dependency Injection Framework support"
            default = DependencyInjection.None
        }

        val networkLibrary = enumParameter<NetworkLibrary> {
            name = "Add Network Library support"
            default = NetworkLibrary.None
        }

        val converter = enumParameter<RetrofitConverter> {
            name = "Retrofit Converter Library"
            default = RetrofitConverter.None
            visible = { networkLibrary.value == NetworkLibrary.Retrofit }
        }

        widgets(
            CheckBoxWidget(useRoom),
            EnumWidget(networkLibrary),
            EnumWidget(converter),
            EnumWidget(dependencyInjection),
            PackageNameWidget(packageName)
        )

        thumb { File("compose-activity-material3").resolve("template_compose_empty_activity_material3.png") }

        recipe = { data: TemplateData ->
            composeSupportProjectRecipe(
                moduleData = data as ModuleTemplateData,
                packageName = packageName.value,
                useRoom = useRoom.value,
                networkLibrary = networkLibrary.value,
                dependencyInjection = dependencyInjection.value,
                converter = if (networkLibrary.value == NetworkLibrary.Retrofit) converter.value else RetrofitConverter.None
            )
        }
    }
val retrofitServiceTemplate
    get() = template {
        name = "Retrofit API Service"
        description = ""
        minApi = 21
        formFactor = FormFactor.Mobile
        category = Category.Other
        screens = listOf(WizardUiContext.MenuEntry)

        val className = stringParameter {
            name = "ClassName"
            default = "MyApiService"
            constraints = listOf(Constraint.CLASS, Constraint.UNIQUE, Constraint.NONEMPTY)
        }

        val baseUrl = stringParameter {
            name = "Base URL for the service call"
            default = "https://localhost/api/"
            constraints = listOf(Constraint.NONEMPTY, Constraint.STRING)
        }

        val converter = enumParameter<RetrofitConverter> {
            name = "Retrofit converter to use"
            default = RetrofitConverter.Gson
        }

        widgets(
            TextFieldWidget(className),
            TextFieldWidget(baseUrl),
            EnumWidget(converter)
        )

        thumb {
            File("no_activity.png")
        }

        recipe = { data ->
            retrofitRecipe(data as ModuleTemplateData, className.value, baseUrl.value, converter.value)
        }
    }
