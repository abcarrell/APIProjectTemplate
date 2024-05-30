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

import ${escapeKotlinIdentifier(applicationPackage)}.data.ApiService
import ${escapeKotlinIdentifier(applicationPackage)}.data.TestDataMapper

interface $className {
    // TODO: create repository methods
}

class ${className}Impl(
    service: $dataSourceClassName,
    mapper: $dataMapperClassName
) {
    // TODO: implement repository interface methods
}

"""
