package pl.prax19.recipe_book_app.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepository @Inject constructor() {

    fun getAllRecipes(): Flow<List<String>> {
        // TODO: add a proper database binding
        var testRecipes = emptyList<String>()
        for(i in 0..200) {
            testRecipes += "Test recipe $i"
        }
        return flow {
            emit(testRecipes)
        }
    }

}