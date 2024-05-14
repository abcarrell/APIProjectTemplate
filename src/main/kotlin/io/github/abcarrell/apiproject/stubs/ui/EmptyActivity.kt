package io.github.abcarrell.apiproject.stubs.ui

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.renderIf

fun emptyViewsActivity(packageName: String, useHilt: Boolean) = """
package ${escapeKotlinIdentifier(packageName)}

import androidx.appcompat.app.AppCompatActivity
${renderIf(useHilt, trim = false, skipLine = false) { "import dagger.hilt.android.AndroidEntryPoint" }}

${renderIf(useHilt, trim = false, skipLine = false) { "@AndroidEntryPoint" }}
class MainActivity : AppCompatActivity(R.layout.activity_main)

"""

fun emptyViewsActivityLayout() = """
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

</androidx.constraintlayout.widget.ConstraintLayout>

"""