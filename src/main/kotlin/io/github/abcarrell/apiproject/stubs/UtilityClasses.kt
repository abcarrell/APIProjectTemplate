package io.github.abcarrell.apiproject.stubs

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import java.util.Base64

fun baseDataMapper(packageName: String) = """
package ${escapeKotlinIdentifier(packageName)}.data

fun interface DataMapper<in S, out T> : (S) -> T
"""

fun baseUseCase(packageName: String) = """
package ${escapeKotlinIdentifier(packageName)}.domain

fun interface UseCase<T> : suspend () -> T
"""

fun baseViewHolder(packageName: String) = """
package ${escapeKotlinIdentifier(packageName)}.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}
"""

fun emptyNavGraph() = """
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph">
    
</navigation>
"""

const val DOLLAR: String = "$"
