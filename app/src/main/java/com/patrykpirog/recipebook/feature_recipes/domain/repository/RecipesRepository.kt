package com.patrykpirog.recipebook.feature_recipes.domain.repository

import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias Recipes = List<Recipe>
typealias RecipeResponse = Response<Recipes>
typealias AddRecipeResponse = Response<Boolean>
typealias DeleteRecipeResponse = Response<Boolean>

interface RecipesRepository {

    fun getRecipesFromFirestore(): Flow<RecipeResponse>

    suspend fun addRecipeToFirestore(recipe: Recipe): AddRecipeResponse

    suspend fun deleteRecipeFromFirestore(recipe: Recipe): DeleteRecipeResponse

}