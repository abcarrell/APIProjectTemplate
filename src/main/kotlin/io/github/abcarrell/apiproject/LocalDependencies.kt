package io.github.abcarrell.apiproject

import com.android.tools.idea.wizard.template.RecipeExecutor

enum class RetrofitConverter(val converterDependency: String = "", val dependency: String = "") {
    Gson("converter-gson", "com.google.code.gson:gson:2.10.1"),
    Moshi("converter-moshi", "com.squareup.moshi:moshi:1.15.1"),
    None
}
fun RecipeExecutor.addNetworkDependencies(
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

fun RecipeExecutor.addRoomDependencies(roomVersion: String = "2.6.1") {
    addDependency(mavenCoordinate = "androidx.room:room-compiler:$roomVersion", configuration = "ksp")
    addDependency(mavenCoordinate = "androidx.room:room-runtime:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-ktx:$roomVersion")
    addDependency(mavenCoordinate = "androidx.room:room-compiler:$roomVersion", configuration = "annotationProcessor")
}

fun RecipeExecutor.addHiltDependencies(hiltVersion: String = "2.49") {
    applyPlugin("com.google.dagger.hilt.android", hiltVersion)
    addDependency(mavenCoordinate = "com.google.dagger:hilt-compiler:$hiltVersion", configuration = "ksp")
    addDependency(mavenCoordinate = "com.google.dagger:hilt-android:$hiltVersion")
}

fun RecipeExecutor.addNavDependencies(navVersion: String = "2.7.5") {
    addDependency(mavenCoordinate = "androidx.navigation:navigation-fragment-ktx:$navVersion")
    addDependency(mavenCoordinate = "androidx.navigation:navigation-ui-ktx:$navVersion")
    addClasspathDependency(mavenCoordinate = "androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    applyPlugin(plugin = "androidx.navigation.safeargs.kotlin", navVersion)
}

fun RecipeExecutor.addViewsDependencies(
    lifecycleVersion: String = "2.6.2"
) {
    addDependency(mavenCoordinate = "androidx.core:core-ktx:1.12.0")
    addDependency(mavenCoordinate = "androidx.appcompat:appcompat:1.6.1")
    addDependency(mavenCoordinate = "androidx.constraintlayout:constraintlayout:2.1.4")
    addDependency(mavenCoordinate = "com.google.android.material:material:1.11.0")
    addDependency(mavenCoordinate = "androidx.fragment:fragment-ktx:1.7.0")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-process-ktx:$lifecycleVersion")
}

fun RecipeExecutor.addComposeDependencies(
    coreVersion: String = "1.13.1",
    composeVersion: String = "2023.08.00",
    activityComposeVersion: String = "1.9.0",
    lifecycleVersion: String = "2.6.2"
) {
    addDependency(mavenCoordinate = "androidx.core:core-ktx:$coreVersion")
    addPlatformDependency(mavenCoordinate = "androidx.compose:compose-bom:$composeVersion")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-graphics")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-tooling", configuration = "debugImplementation")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-tooling-preview")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-test-manifest", configuration = "debugImplementation")
    addDependency(mavenCoordinate = "androidx.compose.ui:ui-test-junit4", configuration = "androidTestImplementation")
    addPlatformDependency(mavenCoordinate = "androidx.compose:compose-bom:$composeVersion", configuration = "androidTestImplementation")
    addDependency(mavenCoordinate = "androidx.compose.material3:material3")
    addDependency(mavenCoordinate = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    addDependency(mavenCoordinate = "androidx.activity:activity-compose:$activityComposeVersion")
}
