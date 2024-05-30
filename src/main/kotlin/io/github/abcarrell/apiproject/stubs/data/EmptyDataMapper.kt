package io.github.abcarrell.apiproject.stubs.data

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyDataMapper(
    packageName: String,
    className: String,
    receiverClass: String,
    mappedClass: String
) = """
package ${escapeKotlinIdentifier(packageName)}

fun interface $className : DataMapper<$receiverClass, $mappedClass>

fun ${className.replaceFirstChar { it.lowercaseChar() }}() = $className { 
    TODO("Data Mapper implementation here.")
}

"""
