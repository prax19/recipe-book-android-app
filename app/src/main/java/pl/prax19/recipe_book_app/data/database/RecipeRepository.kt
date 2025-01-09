package pl.prax19.recipe_book_app.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pl.prax19.recipe_book_app.data.model.Ingredient
import pl.prax19.recipe_book_app.data.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
// TODO: add a proper database binding

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: MutableStateFlow<List<Recipe>> = _recipes

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: MutableStateFlow<List<Ingredient>> = _ingredients

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

    fun getAllRecipes(): Flow<List<Recipe>> {
        return recipes
    }

    fun addIngredient(ingredient: Ingredient) {
        _ingredients.update { it + ingredient }
    }

    fun getAllIngredients(): Flow<List<Ingredient>> {
        return ingredients
    }

    fun addRecipe(recipe: Recipe) {
        _recipes.update { it + recipe }
    }

}