package io.github.abcarrell.apiproject

import com.android.tools.idea.npw.module.recipes.addTestDependencies
import com.android.tools.idea.npw.module.recipes.generateManifest
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageName
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import io.github.abcarrell.apiproject.stubs.app.emptyApplication
import io.github.abcarrell.apiproject.stubs.app.emptyManifestXml
import io.github.abcarrell.apiproject.stubs.baseDataMapper
import io.github.abcarrell.apiproject.stubs.baseUseCase
import io.github.abcarrell.apiproject.stubs.baseViewHolder
import io.github.abcarrell.apiproject.stubs.di.emptyAppModule
import io.github.abcarrell.apiproject.stubs.mviStub
import io.github.abcarrell.apiproject.stubs.ui.emptyViewsActivity
import io.github.abcarrell.apiproject.stubs.ui.emptyViewsActivityLayout

fun RecipeExecutor.projectRecipe(
    moduleData: ModuleTemplateData,
    packageName: PackageName,
    useRoom: Boolean,
    useHilt: Boolean,
    useNavigation: Boolean
) {
    val appName = projectInstance?.name?.replace(Regex("[\\W_]"), "") ?: "Android"
    applyPlugin("com.google.devtools.ksp", "1.9.10-1.0.13")
    addAllKotlinDependencies(moduleData, revision = "1.9.10")
    addTestDependencies()

    generateManifest(hasApplicationBlock = true)

    addViewsDependencies()
    addNetworkDependencies()
    if (useRoom) addRoomDependencies()
    if (useHilt) addHiltDependencies()
    if (useNavigation) addNavDependencies()

    requireJavaVersion("1.8", true)
    setBuildFeature("viewBinding", true)

    save(
        emptyViewsActivity(packageName, useHilt),
        moduleData.srcDir.resolve("MainActivity.kt")
    )
    save(
        emptyViewsActivityLayout(),
        moduleData.resDir.resolve("layout/activity_main.xml")
    )

    mergeXml(
        emptyManifestXml(
            appName,
            "@style/${moduleData.themesData.main.name}"
        ),
        moduleData.manifestDir.resolve("AndroidManifest.xml")
    )

    save(
        baseUseCase(packageName),
        moduleData.srcDir.resolve("domain/UseCase.kt")
    )
    save(
        baseDataMapper(packageName),
        moduleData.srcDir.resolve("data/DataMapper.kt")
    )
    save(
        baseViewHolder(packageName),
        moduleData.srcDir.resolve("ui/BaseViewHolder.kt")
    )
    save(
        mviStub(packageName),
        moduleData.srcDir.resolve("mvi/MVI.kt")
    )

    if (useHilt) {
        save(
            emptyApplication(packageName, appName),
            moduleData.srcDir.resolve("${appName}Application.kt")
        )
        save(
            emptyAppModule(packageName, appName),
            moduleData.srcDir.resolve("component/${appName}AppModule.kt")
        )
    }
}
