package pl.prax19.recipe_book_app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import pl.prax19.recipe_book_app.data.dto.IngredientDTO
import pl.prax19.recipe_book_app.data.dto.RecipeDTO
import pl.prax19.recipe_book_app.data.dto.RecipeIngredientDTO
import pl.prax19.recipe_book_app.data.dto.RecipeStepDTO
import java.util.UUID

@Dao
interface RecipeDAO {

    @Upsert
    suspend fun upsertRecipe(recipe: RecipeDTO)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeDTO>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: UUID): Flow<RecipeDTO>

    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    fun getRecipeIngredientsByRecipeId(recipeId: UUID): Flow<List<RecipeIngredientDTO>>

    @Query("SELECT * FROM recipe_steps WHERE recipeId = :recipeId")
    fun getRecipeStepsByRecipeId(recipeId: UUID): Flow<List<RecipeStepDTO>>

    @Upsert
    suspend fun upsertIngredient(ingredient: IngredientDTO)

    @Query("SELECT * FROM ingredients")
    fun getAllIngredients(): Flow<List<IngredientDTO>>

    @Query("SELECT * FROM ingredients WHERE id = :ingredientId")
    fun getIngredientById(ingredientId: UUID): Flow<IngredientDTO>

    @Query("DELETE FROM ingredients WHERE id = :ingredientId")
    suspend fun removeIngredientById(ingredientId: UUID)

    @Upsert
    suspend fun upsertRecipeIngredient(recipeIngredient: RecipeIngredientDTO)

    @Upsert
    suspend fun upsertRecipeIngredients(recipeIngredients: List<RecipeIngredientDTO>)

    @Delete
    suspend fun removeRecipeIngredient(recipeIngredient: RecipeIngredientDTO)

    @Query("DELETE FROM recipe_ingredients WHERE recipeId = :recipeId")
    suspend fun removeRecipeIngredientsByRecipeId(recipeId: UUID)

    @Upsert
    suspend fun upsertRecipeStep(recipeStep: RecipeStepDTO)

    @Upsert
    suspend fun upsertRecipeSteps(recipeSteps: List<RecipeStepDTO>)

    @Delete
    suspend fun removeRecipeStep(recipeStep: RecipeStepDTO)

    @Query("DELETE FROM recipe_steps WHERE recipeId = :recipeId")
    suspend fun removeRecipeStepsByRecipeId(recipeId: UUID)

    @Query("SELECT EXISTS(SELECT 1 FROM recipe_ingredients WHERE ingredientId = :ingredientId)")
    fun doesIngredientExistInAnyRecipe(ingredientId: UUID): Flow<Boolean>
}