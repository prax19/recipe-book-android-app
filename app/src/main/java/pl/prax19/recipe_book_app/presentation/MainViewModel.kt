package pl.prax19.recipe_book_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewState())

    init {
        viewModelScope.launch {
            // TODO: add proper database binding
            var testRecipes = emptyList<String>()
            for(i in 0..200) {
                testRecipes += "Test recipe $i"
            }
            _state.value = state.value.copy(recipes = testRecipes)
        }
    }

    data class ViewState(
        // TODO: add recipe class
        val recipes: List<String> = emptyList()
    )
}