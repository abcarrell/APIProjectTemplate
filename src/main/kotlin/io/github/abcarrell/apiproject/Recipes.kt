package io.github.abcarrell.apiproject

import com.android.tools.idea.npw.module.recipes.addTestDependencies
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.extractClassName
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.generateManifest
import io.github.abcarrell.apiproject.stubs.app.emptyApplication
import io.github.abcarrell.apiproject.stubs.data.emptyApiService
import io.github.abcarrell.apiproject.stubs.di.emptyHiltModule
import io.github.abcarrell.apiproject.stubs.emptyNavGraph
import io.github.abcarrell.apiproject.stubs.ui.emptyViewsActivity
import io.github.abcarrell.apiproject.stubs.ui.emptyViewsActivityLayout

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
    applyPlugin("com.google.devtools.ksp", "2.0.21-1.0.28")
    addAllKotlinDependencies(moduleData)
    addTestDependencies()

    addViewsDependencies()
    when (networkLibrary) {
        NetworkLibrary.Retrofit -> addRetrofitDependencies(converter = converter)
        NetworkLibrary.Ktor -> addKtorDependencies(moduleData)
        NetworkLibrary.None -> noOp()
    }
    when (dependencyInjection) {
        DependencyInjection.Hilt -> addHiltDependencies()
        DependencyInjection.Koin -> addKoinDependencies()
        DependencyInjection.None -> noOp()
    }
    if (useRoom) addRoomDependencies()
    if (useNavigation) addNavDependencies()
    requireJavaVersion("11", true)
    setViewBinding(true)

    save(
        emptyViewsActivity(packageName, dependencyInjection == DependencyInjection.Hilt),
        moduleData.srcDir.resolve("MainActivity.kt")
    )
    save(
        emptyViewsActivityLayout(),
        moduleData.resDir.resolve("layout/activity_main.xml")
    )

    generateManifest(
        moduleData = moduleData,
        activityClass = "MainActivity",
        packageName = packageName,
        isLauncher = true,
        hasNoActionBar = true,
        generateActivityTitle = false,
        activityThemeName = moduleData.themesData.main.name
    )

    if (dependencyInjection == DependencyInjection.Hilt) {
        save(
            emptyApplication(packageName, appName),
            moduleData.srcDir.resolve("${appName}.kt")
        )
        save(
            emptyHiltModule(packageName, appName),
            moduleData.srcDir.resolve("component/${appName}AppModule.kt")
        )
    }

    if (useNavigation) {
        save(
            emptyNavGraph(),
            moduleData.resDir.resolve("navigation.nav_graph.xml")
        )
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
