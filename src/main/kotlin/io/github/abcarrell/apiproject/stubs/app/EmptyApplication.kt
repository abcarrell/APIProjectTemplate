package io.github.abcarrell.apiproject.stubs.app

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyApplication(packageName: String, appName: String) = """
package ${escapeKotlinIdentifier(packageName)}

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ${appName}: Application()
"""
