package io.github.abcarrell.apiproject.stubs.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyUseCase(
    packageName: String,
    className: String,
    returnedClassName: String
) = """
package ${escapeKotlinIdentifier(packageName)}

fun interface $className : UseCase<$returnedClassName>

fun ${className.replaceFirstChar { it.lowercaseChar() }}() = $className {
    TODO("Implement Use Case method here")
}
"""
