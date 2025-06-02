package io.github.abcarrell.apiproject.stubs.di

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyHiltModule(
    packageName: String,
    appName: String
) = """
package ${escapeKotlinIdentifier(packageName)}.component

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ${appName}AppModule {
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }
}
"""
