package pl.prax19.recipe_book_app.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import pl.prax19.recipe_book_app.data.model.Ingredient
import pl.prax19.recipe_book_app.data.model.Recipe
import pl.prax19.recipe_book_app.data.model.RecipeIngredient
import pl.prax19.recipe_book_app.data.model.RecipeStep
import java.util.UUID
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
// TODO: add a proper database binding

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: MutableStateFlow<List<Recipe>> = _recipes

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: MutableStateFlow<List<Ingredient>> = _ingredients

    private val _recipeSteps = MutableStateFlow<List<RecipeStep>>(emptyList())
    val recipeSteps: MutableStateFlow<List<RecipeStep>> = _recipeSteps

    private val _recipeIngredients = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    val recipeIngredients: MutableStateFlow<List<RecipeIngredient>> = _recipeIngredients

    init {
        addTestData()
    }

    private fun addTestData() {
        // TODO: remove this when database is ready
        val testRecipes = (0 until 3).map { i ->
            Recipe(name = "Test recipe $i")
        }
        _recipes.update { it + testRecipes }
        _ingredients.update { it + listOf(Ingredient(name="tomato"), Ingredient(name="potato"), Ingredient(name="potatato")) }
    }

    fun upsertRecipe(
        recipe: Recipe,
        ingredients: List<RecipeIngredient> = emptyList(),
        steps: List<RecipeStep> = emptyList()
    ) {
        removeRecipeIngredientByRecipeId(recipeId = recipe.id)
        removeRecipeStepsByRecipeId(recipeId = recipe.id)
        _recipes.update { it + recipe }
        _recipeIngredients.update { it + ingredients }
        _recipeSteps.update { it + steps }
    }

    fun getAllRecipes(): Flow<List<Recipe>> {
        return recipes
    }

    fun getRecipeIngredientsByRecipeId(recipeId: UUID): Flow<List<RecipeIngredient>> {
        return recipeIngredients.map { ingredients ->
            ingredients.filter { it.recipeId == recipeId }
        }
    }

    fun removeRecipeIngredientByRecipeId(recipeId: UUID) {
        _recipeIngredients.update { ingredients ->
            ingredients.filterNot { it.recipeId == recipeId }
        }
    }

    fun getRecipeStepsByRecipeId(recipeId: UUID): Flow<List<RecipeStep>> {
        return recipeSteps.map { steps ->
            steps.filter { it.recipeId == recipeId }
        }
    }

    fun removeRecipeStepsByRecipeId(recipeId: UUID) {
        _recipeSteps.update { steps ->
            steps.filterNot { it.recipeId == recipeId }
        }
    }

    fun addIngredient(ingredient: Ingredient) {
        _ingredients.update { it + ingredient }
    }

    fun getAllIngredients(): Flow<List<Ingredient>> {
        return ingredients
    }

    fun removeIngredientIfUnused(ingredient: Ingredient) {
        if(_recipeIngredients.value.none { it.ingredient.id == ingredient.id })
            _ingredients.update { it - ingredient }
    }

}