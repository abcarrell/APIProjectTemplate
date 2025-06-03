package io.github.abcarrell.apiproject.stubs.di

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyKoinModule(packageName: String, appName: String) = """
package ${escapeKotlinIdentifier(packageName)}.component

import org.koin.dsl.module

fun appModule() = module {
}

// object for naming qualifiers
object ${appName}Module {
}
"""
