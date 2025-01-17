package pl.prax19.recipe_book_app.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pl.prax19.recipe_book_app.data.dto.IngredientDTO
import pl.prax19.recipe_book_app.data.dto.RecipeDTO
import pl.prax19.recipe_book_app.data.dto.RecipeIngredientDTO
import pl.prax19.recipe_book_app.data.dto.RecipeStepDTO
import pl.prax19.recipe_book_app.data.model.Ingredient
import pl.prax19.recipe_book_app.data.model.Recipe
import pl.prax19.recipe_book_app.data.model.RecipeIngredient
import pl.prax19.recipe_book_app.data.model.RecipeStep
import java.util.UUID
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeDao: RecipeDAO
) {

    suspend fun upsertRecipe(
        recipe: Recipe,
        ingredients: List<RecipeIngredient> = emptyList(),
        steps: List<RecipeStep> = emptyList()
    ) {
        recipeDao.removeRecipeIngredientsByRecipeId(recipe.id)
        recipeDao.removeRecipeStepsByRecipeId(recipe.id)
        recipeDao.upsertRecipe(
            RecipeDTO(
                id = recipe.id,
                name = recipe.name,
                description = recipe.description,
                source = recipe.source
            )
        )
        recipeDao.upsertRecipeIngredients(
            ingredients.map {
                RecipeIngredientDTO(
                    id = it.id,
                    recipeId = it.recipeId,
                    ingredientId = it.ingredient.id,
                    amount = it.amount,
                    unit = it.unit
                )
            }
        )
        recipeDao.upsertRecipeSteps(
            steps.map {
                RecipeStepDTO(
                    id = it.id,
                    recipeId = it.recipeId,
                    stepIndex = it.stepIndex,
                    description = it.description
                )
            }
        )
    }

    suspend fun removeRecipeById(recipeId: UUID) {
        recipeDao.removeRecipeById(recipeId)
        val ingredients = getRecipeIngredientsByRecipeId(recipeId).first()

        recipeDao.removeRecipeIngredientsByRecipeId(recipeId)
        recipeDao.removeRecipeStepsByRecipeId(recipeId)

        ingredients.forEach {
            removeIngredientIfUnused(it.ingredient)
        }
    }

     fun getAllRecipes(): Flow<List<Recipe>> {
        val recipes = recipeDao.getAllRecipes()
        return recipes.map {
            it.map { recipe ->
                Recipe(
                    id = recipe.id,
                    name = recipe.name,
                    description = recipe.description,
                    source = recipe.source
                )
            }
        }
    }

    fun getRecipeById(recipeId: UUID): Flow<Recipe?> {
        return recipeDao.getRecipeById(recipeId).map { recipe ->
            recipe?.let {
                Recipe(
                    id = recipe.id,
                    name = recipe.name,
                    description = recipe.description,
                    source = recipe.source
                )
            } ?: run {
                null
            }
        }
    }

    fun getRecipeIngredientsByRecipeId(recipeId: UUID): Flow<List<RecipeIngredient>> {
        return recipeDao.getRecipeIngredientsByRecipeId(recipeId).map {
            it.map { ingredient ->
                RecipeIngredient(
                    id = ingredient.id,
                    recipeId = ingredient.recipeId,
                    ingredient = getIngredientById(ingredient.ingredientId).first(),
                    amount = ingredient.amount,
                    unit = ingredient.unit
                )
            }
        }
    }

    suspend fun removeRecipeIngredientsByRecipeId(recipeId: UUID) {
        recipeDao.removeRecipeStepsByRecipeId(recipeId)
    }

    fun getRecipeStepsByRecipeId(recipeId: UUID): Flow<List<RecipeStep>> {
        return recipeDao.getRecipeStepsByRecipeId(recipeId).map {
            it.map { step ->
                RecipeStep(
                    id = step.id,
                    recipeId = step.recipeId,
                    stepIndex = step.stepIndex,
                    description = step.description
                )
            }
        }
    }

    suspend fun removeRecipeStepsByRecipeId(recipeId: UUID) {
        recipeDao.removeRecipeStepsByRecipeId(recipeId)
    }

    suspend fun addIngredient(ingredient: Ingredient) {
        recipeDao.upsertIngredient(
            IngredientDTO(
                id = ingredient.id,
                name = ingredient.name
            )
        )
    }

    fun getAllIngredients(): Flow<List<Ingredient>> {
        return recipeDao.getAllIngredients().map {
            it.map { ingredient ->
                Ingredient(
                    id = ingredient.id,
                    name = ingredient.name
                )
            }
        }
    }

    fun getIngredientById(ingredientId: UUID): Flow<Ingredient> {
        return recipeDao.getIngredientById(ingredientId).map {
            Ingredient(
                id = it.id,
                name = it.name
            )
        }
    }

    suspend fun removeIngredientIfUnused(ingredient: Ingredient) {
        val recipeIngredients = recipeDao.getAllRecipeIngredientsByIngredientId(ingredient.id).first()
        if (recipeIngredients.isEmpty())
            recipeDao.removeIngredientById(ingredient.id)
    }

}