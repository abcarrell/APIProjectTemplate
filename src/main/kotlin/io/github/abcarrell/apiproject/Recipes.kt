package io.github.abcarrell.apiproject

import com.android.tools.idea.npw.module.recipes.addTestDependencies
import com.android.tools.idea.wizard.template.BytecodeLevel
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.extractClassName
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.generateManifest
import io.github.abcarrell.apiproject.stubs.app.emptyApplication
import io.github.abcarrell.apiproject.stubs.app.emptyHiltApplication
import io.github.abcarrell.apiproject.stubs.app.emptyKoinApplication
import io.github.abcarrell.apiproject.stubs.data.emptyApiService
import io.github.abcarrell.apiproject.stubs.di.emptyHiltModule
import io.github.abcarrell.apiproject.stubs.di.emptyKoinModule
import io.github.abcarrell.apiproject.stubs.emptyNavGraph
import io.github.abcarrell.apiproject.stubs.ui.colorKt
import io.github.abcarrell.apiproject.stubs.ui.emptyComposeActivity
import io.github.abcarrell.apiproject.stubs.ui.emptyViewsActivity
import io.github.abcarrell.apiproject.stubs.ui.emptyViewsActivityLayout
import io.github.abcarrell.apiproject.stubs.ui.themeKt
import io.github.abcarrell.apiproject.stubs.ui.typeKt

fun RecipeExecutor.supportProjectRecipe(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    networkLibrary: NetworkLibrary,
    converter: RetrofitConverter = RetrofitConverter.None,
    dependencyInjection: DependencyInjection,
    useRoom: Boolean,
    useNavigation: Boolean
) {
    val appName = extractClassName(ApiProject.projectInstance.name) ?: "Android"
    addViewsDependencies()
    setViewBinding(true)
    setCommonLibraries(moduleData, packageName, appName, networkLibrary, dependencyInjection, converter, useRoom)

    save(
        emptyViewsActivity(packageName, dependencyInjection == DependencyInjection.Hilt),
        moduleData.srcDir.resolve("MainActivity.kt")
    )
    save(
        emptyViewsActivityLayout(),
        moduleData.resDir.resolve("layout/activity_main.xml")
    )

    if (useNavigation) {
        addNavDependencies()
        save(
            emptyNavGraph(),
            moduleData.resDir.resolve("navigation/nav_graph.xml")
        )
    }
}

fun RecipeExecutor.composeSupportProjectRecipe(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    networkLibrary: NetworkLibrary,
    converter: RetrofitConverter = RetrofitConverter.None,
    dependencyInjection: DependencyInjection,
    useRoom: Boolean
) {
    val appName = extractClassName(ApiProject.projectInstance.name) ?: "Android"

    addComposeDependencies(moduleData)
    addAdditionalComposeDependencies()

    setCommonLibraries(moduleData, packageName, appName, networkLibrary, dependencyInjection, converter, useRoom)

    val themeName = "${moduleData.themesData.appName}Theme"
    save(
        emptyComposeActivity(packageName, appName, dependencyInjection == DependencyInjection.Hilt, themeName),
        moduleData.srcDir.resolve("MainActivity.kt")
    )
    val uiThemeFolder = "ui/theme"
    save(colorKt(packageName), moduleData.srcDir.resolve("$uiThemeFolder/Color.kt"))
    save(themeKt(packageName, themeName), moduleData.srcDir.resolve("$uiThemeFolder/Theme.kt"))
    save(typeKt(packageName), moduleData.srcDir.resolve("$uiThemeFolder/Type.kt"))
    setBuildFeature("compose", true)
}

private fun RecipeExecutor.setCommonLibraries(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    appName: String,
    networkLibrary: NetworkLibrary,
    dependencyInjection: DependencyInjection,
    converter: RetrofitConverter,
    useRoom: Boolean
) {
    val kspVersion = moduleData.projectTemplateData.kotlinVersion + "-1.0.28"
    applyPlugin("com.google.devtools.ksp", kspVersion)
    addAllKotlinDependencies(moduleData)
    addDependency("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    addTestDependencies()
    addTestSupportDependencies()
    when (networkLibrary) {
        NetworkLibrary.Retrofit -> addRetrofitDependencies(converter = converter)
        NetworkLibrary.Ktor -> addKtorDependencies(moduleData)
        NetworkLibrary.None -> noOp()
    }
    if (useRoom) addRoomDependencies()
    requireJavaVersion(BytecodeLevel.default.versionString, true)

    generateManifest(
        moduleData = moduleData,
        activityClass = "MainActivity",
        packageName = packageName,
        isLauncher = true,
        hasNoActionBar = true,
        generateActivityTitle = false,
        activityThemeName = moduleData.themesData.main.name
    )
    val networkManifest = """
    <manifest xmlns:android="http://schemas.android.com/apk/res/android">
        <uses-permission android:name="android.permission.INTERNET" />
    </manifest>
    """

    if (networkLibrary != NetworkLibrary.None) {
        mergeXml(networkManifest, moduleData.manifestDir.resolve("AndroidManifest.xml"))
    }

    val (application, module) = when (dependencyInjection) {
        DependencyInjection.Hilt -> {
            addHiltDependencies()
            emptyHiltApplication(packageName, appName) to emptyHiltModule(packageName, appName)
        }

        DependencyInjection.Koin -> {
            addKoinDependencies()
            emptyKoinApplication(packageName, appName) to emptyKoinModule(packageName, appName)
        }

        DependencyInjection.None -> emptyApplication(packageName, appName) to null
    }

    save(application, moduleData.srcDir.resolve("${appName}.kt"))
    module?.run {
        save(this, moduleData.srcDir.resolve("component/${appName}Module.kt"))
    }
}

fun RecipeExecutor.retrofitRecipe(
    moduleData: ModuleTemplateData,
    className: String,
    baseUrl: String,
    converter: RetrofitConverter
) {
    val packageName = moduleData.packageName
    addRetrofitDependencies(converter = converter)

    save(
        emptyApiService(packageName, className, baseUrl, converter),
        moduleData.srcDir.resolve("${className}.kt")
    )
}
