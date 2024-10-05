package io.github.abcarrell.apiproject.stubs.domain

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun emptyRepository(
    applicationPackage: String,
    packageName: String,
    className: String,
    dataSourceClassName: String,
    dataMapperClassName: String
) = """
package ${escapeKotlinIdentifier(packageName)}

import ${escapeKotlinIdentifier(applicationPackage)}.data.${dataSourceClassName}
import ${escapeKotlinIdentifier(applicationPackage)}.data.${dataMapperClassName}

interface $className {
    // TODO: create repository methods
}

class ${className}Impl(
    service: $dataSourceClassName,
    mapper: $dataMapperClassName
) : $className {
    // TODO: implement repository interface methods
}

"""
