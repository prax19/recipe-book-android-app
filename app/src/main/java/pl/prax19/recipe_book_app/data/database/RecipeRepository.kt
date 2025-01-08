package pl.prax19.recipe_book_app.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pl.prax19.recipe_book_app.data.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
// TODO: add a proper database binding

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: MutableStateFlow<List<Recipe>> = _recipes

    init {
        addTestRecipes()
    }

    private fun addTestRecipes() {
        val testRecipes = (0 until 3).map { i ->
            Recipe(name = "Test recipe $i")
        }
        _recipes.update { it + testRecipes }
    }

    fun getAllRecipes(): Flow<List<Recipe>> {
        return recipes
    }

    fun addRecipe(recipe: Recipe) {
        _recipes.update { it + recipe }
    }

}