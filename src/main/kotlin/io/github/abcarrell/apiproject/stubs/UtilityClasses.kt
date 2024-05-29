package io.github.abcarrell.apiproject.stubs

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier

fun baseDataMapper(packageName: String) = """
package ${escapeKotlinIdentifier(packageName)}.data

fun interface DataMapper<S, T> : (S) -> T
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

fun baseNavGraph() = """
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/nav_graph">

</navigation>
"""

const val DOLLAR: String = "$"