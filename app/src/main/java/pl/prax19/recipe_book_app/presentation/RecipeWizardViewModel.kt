package pl.prax19.recipe_book_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.prax19.recipe_book_app.data.database.RecipeRepository
import pl.prax19.recipe_book_app.data.model.Ingredient
import pl.prax19.recipe_book_app.data.model.Recipe
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecipeWizardViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    private val _availableIngredients = recipeRepository.getAllIngredients()
    val state = combine(_state, _availableIngredients) { state, availableIngredients ->
        state.copy(
            availableIngredients = availableIngredients
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewState())


    fun saveRecipe() {
        viewModelScope.launch {
            recipeRepository.addRecipe(
                Recipe(
                    id = state.value.id ?: UUID.randomUUID(),
                    name = state.value.name,
                    description = state.value.description,
                    source = state.value.source
                )
            )
        }
    }

    fun getIngredientSearchQueryResponse(query: String): List<Ingredient> {
        var matchingIngredients = state.value.availableIngredients.filter {
            it.name.contains(query)
        }
        if(state.value.availableIngredients.none { it.name == query })
            matchingIngredients = listOf(Ingredient(name = query)) + matchingIngredients
        return matchingIngredients.filterNot { it.name in listOf("") }
    }

    fun addIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            if (state.value.availableIngredients.none { it.name == ingredient.name })
                recipeRepository.addIngredient(ingredient)
        }
    }

    fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun updateSource(source: String) {
        _state.update { it.copy(source = source) }
    }

    fun updateIngredients(ingredients: List<Ingredient>) {
        _state.update { it.copy(ingredients = ingredients) }
    }

    data class ViewState(
        val availableIngredients: List<Ingredient> = emptyList(),
        val id: UUID ?= null,
        val name: String = "",
        val description: String ?= null,
        val source: String ?= null,
        val ingredients: List<Ingredient> = emptyList(),
    )

}