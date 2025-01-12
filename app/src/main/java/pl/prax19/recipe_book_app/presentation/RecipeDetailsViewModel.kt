package pl.prax19.recipe_book_app.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.prax19.recipe_book_app.data.database.RecipeRepository
import pl.prax19.recipe_book_app.data.model.Recipe
import pl.prax19.recipe_book_app.data.model.RecipeIngredient
import pl.prax19.recipe_book_app.data.model.RecipeStep
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(ViewState())

    private val recipeUUID = UUID.fromString(savedStateHandle.get<String>("recipeUUID"))

    private val _recipe = recipeRepository.getRecipeById(
        recipeId = recipeUUID
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _ingredients = recipeRepository.getRecipeIngredientsByRecipeId(
        recipeId = recipeUUID
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _steps = recipeRepository.getRecipeStepsByRecipeId(
        recipeId = recipeUUID
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _recipe, _ingredients, _steps) { state, recipe, ingredients, steps ->
        state.copy(
            recipe = recipe,
            ingredients = ingredients,
            steps = steps
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewState())

    fun removeRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.removeRecipeById(recipe.id)
        }

    }

    data class ViewState(
        val recipe: Recipe ?= null,
        val ingredients: List<RecipeIngredient> = emptyList(),
        val steps: List<RecipeStep> = emptyList()
    )

}