package pl.prax19.recipe_book_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.prax19.recipe_book_app.data.database.RecipeRepository
import pl.prax19.recipe_book_app.data.model.Recipe
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecipeWizardViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewState())

    fun saveRecipe() {
        viewModelScope.launch {
            recipeRepository.addRecipe(
                Recipe(
                    id = state.value.id ?: UUID.randomUUID(),
                    name = state.value.name,
                    description = state.value.description
                )
            )
        }
    }

    fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    data class ViewState(
        val id: UUID ?= null,
        val name: String = "",
        val description: String = ""
    )

}