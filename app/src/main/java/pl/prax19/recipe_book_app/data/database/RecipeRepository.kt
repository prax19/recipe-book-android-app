package pl.prax19.recipe_book_app.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.prax19.recipe_book_app.data.model.Recipe
import javax.inject.Inject

class RecipeRepository @Inject constructor() {

    fun getAllRecipes(): Flow<List<Recipe>> {
        // TODO: add a proper database binding
        var testRecipes = emptyList<Recipe>()
        for(i in 0..200) {
            testRecipes += Recipe(name = "Test recipe $i")
        }
        return flow {
            emit(testRecipes)
        }
    }

}