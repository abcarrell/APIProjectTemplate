package io.github.abcarrell.apiproject

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addMaterial3Dependency
import com.android.tools.idea.wizard.template.impl.activities.common.addMaterialDependency

enum class RetrofitConverter(val converterDependency: String = "", val dependency: String = "") {
    Gson("converter-gson", "com.google.code.gson:gson:2.10.1"),
    Moshi("converter-moshi", "com.squareup.moshi:moshi:1.15.1"),
    None
}

enum class NetworkLibrary {
    Retrofit,
    Ktor,
    None
}

enum class DependencyInjection {
    Hilt,
    Koin,
    None
}

fun RecipeExecutor.addRetrofitDependencies(
    retrofitVersion: String = "2.11.0",
    okHttpVersion: String = "4.12.0",
    converter: RetrofitConverter
) {
    addPlatformDependency("com.squareup.retrofit2:retrofit-bom:$retrofitVersion")
    addDependency(mavenCoordinate = "com.squareup.retrofit2:retrofit")
    addPlatformDependency(mavenCoordinate = "com.squareup.okhttp3:okhttp-bom:$okHttpVersion")
    addDependency(mavenCoordinate = "com.squareup.okhttp3:okhttp")
    addDependency(mavenCoordinate = "com.squareup.okhttp3:logging-interceptor")
    if (converter != RetrofitConverter.None) {
        addDependency(mavenCoordinate = converter.dependency)
        addDependency(mavenCoordinate = "com.squareup.retrofit2:${converter.converterDependency}")
    }
    addDependency(mavenCoordinate = "com.squareup.okhttp3:mockwebserver", "testImplementation")
}

fun RecipeExecutor.addKtorDependencies(
    data: ModuleTemplateData,
    ktorVersion: String = "3.1.3"
) {
    addDependency("io.ktor:ktor-client-core:$ktorVersion")
    addDependency("io.ktor:ktor-client-okhttp:$ktorVersion")
    addDependency("io.ktor:ktor-client-serialization:$ktorVersion")
    addDependency("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    addDependency("io.ktor:ktor-client-logging:$ktorVersion")
    addDependency("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    addDependency("io.ktor:ktor-client-mock:$ktorVersion", "testImplementation")
    applyPlugin("org.jetbrains.kotlin.plugin.serialization", data.projectTemplateData.kotlinVersion)
}

fun RecipeExecutor.addRoomDependencies(roomVersion: String = "2.7.1") {
    addDependency(mavenCoordinate = "androidx.room:room-compiler:$roomVersion", configuration = "ksp")
    addDependency(mavenCoordinate = "androidx.room:room-runtime:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-ktx:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-paging:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-compiler:$roomVersion", configuration = "annotationProcessor")
}

fun RecipeExecutor.addHiltDependencies(hiltVersion: String = "2.49", withCompose: Boolean = false) {
    applyPlugin("com.google.dagger.hilt.android", hiltVersion)
    addDependency(mavenCoordinate = "com.google.dagger:hilt-compiler:$hiltVersion", configuration = "ksp")
    addDependency(mavenCoordinate = "com.google.dagger:hilt-android:$hiltVersion")
    if (withCompose) {
        addDependency(mavenCoordinate = "androidx.hilt:hilt-navigation-compose:1.2.0")
    }
}

fun RecipeExecutor.addKoinDependencies(koinVersion: String = "4.0.4", withCompose: Boolean = false) {
    addPlatformDependency("io.insert-koin:koin-bom:$koinVersion")
    addDependency("io.insert-koin:koin-core")
    addDependency("io.insert-koin:koin-android")
    if (withCompose) {
        addDependency("io.insert-koin:koin-androidx-compose")
        addDependency("io.insert-koin:koin-androidx-compose-navigation")
    }
}

fun RecipeExecutor.addNavDependencies(navVersion: String = "2.7.5") {
    addDependency(mavenCoordinate = "androidx.navigation:navigation-fragment-ktx:$navVersion")
    addDependency(mavenCoordinate = "androidx.navigation:navigation-ui-ktx:$navVersion")
    addClasspathDependency(mavenCoordinate = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    applyPlugin(plugin = "androidx.navigation.safeargs.kotlin", navVersion)
}

fun RecipeExecutor.addViewsDependencies(
    coreVersion: String = "1.16.0",
    lifecycleVersion: String = "2.9.0",
    useMaterial3: Boolean = false
) {
    addDependency(mavenCoordinate = "androidx.core:core-ktx:$coreVersion")
    addDependency(mavenCoordinate = "androidx.appcompat:appcompat:1.7.0")
    addDependency(mavenCoordinate = "androidx.constraintlayout:constraintlayout:2.2.1")
    addDependency(mavenCoordinate = "androidx.fragment:fragment-ktx:1.8.7")
    addDependency(mavenCoordinate = "androidx.recyclerview:recyclerview:1.4.0")
    addDependency(mavenCoordinate = "androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-beta01")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-process:$lifecycleVersion")
    if (useMaterial3) addMaterial3Dependency()
    else addMaterialDependency(true)
    addDependency("io.github.fctmisc:support:0.0.1-SNAPSHOT")
}

fun RecipeExecutor.addAdditionalComposeDependencies(
    coreVersion: String = "1.16.0",
    activityComposeVersion: String = "1.10.1",
    lifecycleVersion: String = "2.9.0"
) {
    addDependency(mavenCoordinate = "androidx.core:core-ktx:$coreVersion")
    addDependency(mavenCoordinate = "androidx.compose.material3:material3")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.activity:activity-compose:$activityComposeVersion")
    addDependency(mavenCoordinate = "androidx.navigation:navigation-compose:$lifecycleVersion")
    addDependency(mavenCoordinate = "io.github.fctmisc:support-compose:0.0.1-SNAPSHOT")
}
