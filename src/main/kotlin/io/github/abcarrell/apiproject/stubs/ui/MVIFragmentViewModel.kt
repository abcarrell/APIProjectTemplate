package io.github.abcarrell.apiproject.stubs.ui

import com.android.tools.idea.wizard.template.escapeKotlinIdentifier
import com.android.tools.idea.wizard.template.impl.activities.common.layoutToViewBindingClass
import com.android.tools.idea.wizard.template.renderIf

fun emptyMVIViewModel(
    applicationPackage: String,
    packageName: String,
    className: String,
    includeFactory: Boolean,
    includeHilt: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ${escapeKotlinIdentifier(applicationPackage)}.mvi.MVI
import ${escapeKotlinIdentifier(applicationPackage)}.mvi.MVIActor
import ${escapeKotlinIdentifier(applicationPackage)}.mvi.mvi
${renderIf(includeHilt) { "import dagger.hilt.android.lifecycle.HiltViewModel" }}

${renderIf(includeHilt) { "@HiltViewModel" }}
class $className ${renderIf(includeHilt) { "@Inject constructor " }}(
    private val mvi: MVIActor<UIState, UIAction, Effect>
) : ViewModel(), MVI<$className.UIState, $className.UIAction, $className.Effect> by mvi {
    
    data class UIState(
        val loading: Boolean = false
    )
    
    sealed interface UIAction {
        // TODO: define actions
    }
    
    sealed interface Effect {
        // TODO: define any non-state related side effects
    }

    ${renderIf(!includeHilt && includeFactory) { """
    companion object {
        fun create() = viewModelFactory { 
            initializer { 
                $className(mvi = mvi(UIState()))
            }
        }
    }
    """ }} 
}
"""

fun emptyMVIFragment(
    applicationPackage: String,
    packageName: String,
    className: String,
    viewModelName: String,
    layoutName: String,
    includeHilt: Boolean
) = """
package ${escapeKotlinIdentifier(packageName)}
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
${renderIf(includeHilt) { "import dagger.hilt.android.AndroidEntryPoint" }}
import ${escapeKotlinIdentifier(applicationPackage)}.R
import ${escapeKotlinIdentifier(applicationPackage)}.binding.${layoutToViewBindingClass(layoutName)}
import kotlinx.coroutines.launch

${renderIf(includeHilt) { "@AndroidEntryPoint" }}
class $className : Fragment(R.layout.$layoutName) {
    private var _binding: ${layoutToViewBindingClass(layoutName)}? = null
    private val binding get() = checkNotNull(_binding) { "Binding not initialized" }
    
    private val viewModel: $viewModelName by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ${layoutToViewBindingClass(layoutName)}.inflate(inflater, container, false).also {
            _binding = it
        }.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { uiState ->
                        // TODO: Collect from UIState
                    }
                }
                launch {
                    viewModel.effects.collect { effect ->
                        // TODO: Collect on side effects
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = $className()
    }
}
"""
