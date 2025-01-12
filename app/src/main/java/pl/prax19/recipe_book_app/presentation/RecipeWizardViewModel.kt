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
import pl.prax19.recipe_book_app.data.model.RecipeIngredient
import pl.prax19.recipe_book_app.data.model.RecipeStep
import pl.prax19.recipe_book_app.utils.IngredientQuery
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecipeWizardViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    private val _existingIngredients = recipeRepository.getAllIngredients()
    val state = combine(_state, _existingIngredients) { state, availableIngredients ->
        state.copy(
            availableIngredients = availableIngredients
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ViewState())

    fun saveRecipe() {
        viewModelScope.launch {
            recipeRepository.upsertRecipe(
                recipe = Recipe(
                    id = state.value.id,
                    name = state.value.name,
                    description = state.value.description,
                    source = state.value.source
                ),
                ingredients = state.value.ingredients,
                steps = state.value.steps
            )
        }
    }

    fun getIngredientSearchQueryResponse(query: IngredientQuery): List<IngredientQuery> {
        // TODO: optimize
        var matchingIngredients = state.value.availableIngredients.filter {
            it.name.contains(query.product)
        }
        if(state.value.availableIngredients.none { it.name == query.product })
            matchingIngredients = listOf(Ingredient(name = query.product)) + matchingIngredients
        return matchingIngredients
            .filterNot { it.name in listOf("") }
            .map { IngredientQuery(it.name, query.amount, query.unit) }
    }

    fun findIngredientByName(name: String): Ingredient? {
        return state.value.availableIngredients.find { it.name == name }
    }

    fun addIngredient(rawIngredient: IngredientQuery) {
        viewModelScope.launch {
            findIngredientByName(rawIngredient.product)?.let { ingredient ->
                val recipeIngredient = RecipeIngredient(
                    ingredient = ingredient,
                    recipeId = state.value.id,
                    unit = rawIngredient.unit,
                    amount = rawIngredient.amount
                )
                _state.update { it.copy(ingredients = it.ingredients + recipeIngredient) }
            } ?: run {
                val newIngredient = Ingredient(name = rawIngredient.product)
                createIngredient(newIngredient)
                val recipeIngredient = RecipeIngredient(
                    ingredient = newIngredient,
                    recipeId = state.value.id,
                    unit = rawIngredient.unit,
                    amount = rawIngredient.amount
                )
                _state.update { it.copy(ingredients = it.ingredients + recipeIngredient) }
            }
        }
    }

    fun removeIngredient(recipeIngredient: RecipeIngredient) {
        viewModelScope.launch {
            _state.update { it.copy(ingredients = it.ingredients - recipeIngredient) }
            recipeRepository.removeIngredientIfUnused(recipeIngredient.ingredient)
        }
    }

    fun createIngredient(ingredient: Ingredient) {
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

    fun updateSteps(steps: List<String>) {
        _state.update {
            it.copy(
                steps = steps.mapIndexed() { index, step ->
                    RecipeStep(
                        recipeId = it.id,
                        stepIndex = index,
                        description = step
                    )
                }
            )
        }
    }

    fun updateSource(source: String) {
        _state.update { it.copy(source = source) }
    }

    data class ViewState(
        val availableIngredients: List<Ingredient> = emptyList(),
        val id: UUID = UUID.randomUUID(),
        val name: String = "",
        val description: String ?= null,
        val source: String ?= null,
        val ingredients: List<RecipeIngredient> = emptyList(),
        val steps: List<RecipeStep> = emptyList()
    )

}