package pl.prax19.recipe_book_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pl.prax19.recipe_book_app.data.database.RecipeRepository
import pl.prax19.recipe_book_app.data.model.Recipe
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    recipeRepository: RecipeRepository
): ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    private val _recipes = recipeRepository.getAllRecipes()

    val state = combine(_state, _recipes) { state, recipes ->
        state.copy(
            recipes = recipes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewState())

    data class ViewState(
        val recipes: List<Recipe> = emptyList()
    )
}