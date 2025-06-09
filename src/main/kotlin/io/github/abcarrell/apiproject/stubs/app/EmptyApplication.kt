package io.github.abcarrell.apiproject.stubs.app

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyApplication(packageName: String, appName: String) = """
package ${escapeKotlinIdentifier(packageName)}

import android.app.Application

class $appName: Application
"""

fun emptyHiltApplication(packageName: String, appName: String) = """
package ${escapeKotlinIdentifier(packageName)}

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ${appName}: Application()
"""

fun emptyKoinApplication(packageName: String, appName: String) = """
package ${escapeKotlinIdentifier(packageName)}

import android.app.Application
import ${escapeKotlinIdentifier(packageName)}.component.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class $appName: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@${appName})
            modules(appModule())
        }
    }
}
"""

fun emptyKoinComposeAppFunction(packageName: String, appName: String) = """
package ${escapeKotlinIdentifier(packageName)}

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ${escapeKotlinIdentifier(packageName)}.component.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication

@Composable
fun $appName(content: @Composable () -> Unit) {
    val context = LocalContext.current.applicationContext
    KoinApplication(application = {
        androidLogger()
        androidContext(context)
        modules(appModule())
    }, content)
}
"""
