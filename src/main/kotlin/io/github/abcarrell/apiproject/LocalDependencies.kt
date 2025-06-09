package io.github.abcarrell.apiproject

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addMaterial3Dependency
import com.android.tools.idea.wizard.template.impl.activities.common.addMaterialDependency

enum class RetrofitConverter(val converterDependency: String = "", val dependency: String = "") {
    Gson("converter-gson", "com.google.code.gson:gson:2.11.0"),
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
    retrofitVersion: String = LibraryVersions.RETROFIT_BOM,
    okHttpVersion: String = LibraryVersions.OKHTTP_BOM,
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
    ktorVersion: String = LibraryVersions.KOTLIN_KTOR
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

fun RecipeExecutor.addRoomDependencies(roomVersion: String = LibraryVersions.ANDROIDX_ROOM) {
    addDependency(mavenCoordinate = "androidx.room:room-compiler:$roomVersion", configuration = "ksp")
    addDependency(mavenCoordinate = "androidx.room:room-runtime:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-ktx:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-paging:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-testing:$roomVersion", configuration = "testImplementation")
}

fun RecipeExecutor.addHiltDependencies(
    hiltVersion: String = LibraryVersions.ANDROID_HILT,
    withCompose: Boolean = false
) {
    applyPlugin("com.google.dagger.hilt.android", hiltVersion)
    addDependency(mavenCoordinate = "com.google.dagger:hilt-compiler:$hiltVersion", configuration = "ksp")
    addDependency(mavenCoordinate = "com.google.dagger:hilt-android:$hiltVersion")
    if (withCompose) {
        addDependency(mavenCoordinate = "androidx.hilt:hilt-navigation-compose:1.2.0")
    }
}

fun RecipeExecutor.addKoinDependencies(
    koinVersion: String = LibraryVersions.KOTLIN_KOIN,
    withCompose: Boolean = false
) {
    addPlatformDependency("io.insert-koin:koin-bom:$koinVersion")
    addDependency("io.insert-koin:koin-core")
    addDependency("io.insert-koin:koin-android")
    if (withCompose) {
        addDependency("io.insert-koin:koin-androidx-compose")
        addDependency("io.insert-koin:koin-androidx-compose-navigation")
    }
}

fun RecipeExecutor.addNavDependencies(navVersion: String = LibraryVersions.ANDROIDX_NAVIGATION) {
    addDependency(mavenCoordinate = "androidx.navigation:navigation-fragment-ktx:$navVersion")
    addDependency(mavenCoordinate = "androidx.navigation:navigation-ui-ktx:$navVersion")
    addClasspathDependency(mavenCoordinate = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    applyPlugin(plugin = "androidx.navigation.safeargs.kotlin", navVersion)
}

fun RecipeExecutor.addTestSupportDependencies() {
    addDependency("io.mockk:mockk:1.14.2", "testImplementation")
    addDependency("org.robolectric:robolectric:4.14.1", "testImplementation")
}

fun RecipeExecutor.addViewsDependencies(
    coreVersion: String = LibraryVersions.ANDROID_CORE,
    lifecycleVersion: String = LibraryVersions.ANDROIDX_LIFECYCLE,
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

fun RecipeExecutor.addComposeDependencies(
    data: ModuleTemplateData,
    composeBomVersion: String = LibraryVersions.COMPOSE_BOM_VERSION,
    composeUiVersion: String? = null
) {
    addPlugin(
        "org.jetbrains.kotlin.plugin.compose",
        "org.jetbrains.kotlin:compose-compiler-gradle-plugin"
    )
    applyPlugin("org.jetbrains.kotlin.plugin.compose", data.projectTemplateData.kotlinVersion)
    addPlatformDependency(mavenCoordinate = "androidx.compose:compose-bom:$composeBomVersion")
    addPlatformDependency(
        mavenCoordinate = "androidx.compose:compose-bom:$composeBomVersion",
        "androidTestImplementation"
    )
    val composeUiFormattedVersion = composeUiVersion?.let { ":$it" } ?: ""
    addDependency(mavenCoordinate = "androidx.compose.ui:ui$composeUiFormattedVersion")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-graphics")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-tooling", configuration = "debugImplementation")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-tooling-preview")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-test-manifest", configuration = "debugImplementation")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-test-junit4", configuration = "androidTestImplementation")
    addDependency(mavenCoordinate = "androidx.constraintlayout:constraintlayout-compose:1.1.1")
}

fun RecipeExecutor.addAdditionalComposeDependencies(
    coreVersion: String = LibraryVersions.ANDROID_CORE,
    activityComposeVersion: String = LibraryVersions.COMPOSE_ACTIVITY,
    lifecycleVersion: String = LibraryVersions.ANDROIDX_LIFECYCLE
) {
    addDependency(mavenCoordinate = "androidx.core:core-ktx:$coreVersion")
    addDependency(mavenCoordinate = "androidx.compose.material3:material3")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.activity:activity-compose:$activityComposeVersion")
    addDependency(mavenCoordinate = "androidx.navigation:navigation-compose:$lifecycleVersion")
    addDependency(mavenCoordinate = "io.github.fctmisc:support:0.0.1-SNAPSHOT")
    addDependency(mavenCoordinate = "io.github.fctmisc:support-compose:0.0.1-SNAPSHOT")
}

fun RecipeExecutor.addKotlinDateTimeDependency(
    version: String = LibraryVersions.KOTLIN_DATETIME
) {
    addDependency("org.jetbrains.kotlinx:kotlinx-datetime:$version")
}

object LibraryVersions {
    const val ANDROID_CORE = "1.16.0"
    const val ANDROIDX_LIFECYCLE = "2.9.0"
    const val ANDROIDX_ROOM = "2.7.1"
    const val ANDROID_HILT = "2.50"
    const val KOTLIN_KTOR = "3.1.3"
    const val KOTLIN_DATETIME = "0.6.2"
    const val RETROFIT_BOM = "2.11.0"
    const val OKHTTP_BOM = "4.12.0"
    const val KOTLIN_KOIN = "4.0.4"
    const val ANDROIDX_NAVIGATION = "2.9.0"
    const val COMPOSE_ACTIVITY = "1.10.1"
    const val COMPOSE_BOM_VERSION = "2024.09.00"
}
