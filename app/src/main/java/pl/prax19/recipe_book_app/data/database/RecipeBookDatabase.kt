package pl.prax19.recipe_book_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.prax19.recipe_book_app.data.dto.IngredientDTO
import pl.prax19.recipe_book_app.data.dto.RecipeDTO
import pl.prax19.recipe_book_app.data.dto.RecipeIngredientDTO
import pl.prax19.recipe_book_app.data.dto.RecipeStepDTO

@Database(
    entities = [RecipeDTO::class, IngredientDTO::class, RecipeIngredientDTO::class, RecipeStepDTO::class],
    version = 1
)
abstract class RecipeBookDatabase: RoomDatabase() {
    abstract fun recipeDAO(): RecipeDAO
}