package com.patrykpirog.recipebook.feature_recipes.data.repository

import com.google.firebase.firestore.CollectionReference
import com.patrykpirog.recipebook.commons.Constants.RECIPE_NAME_KEY
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.domain.model.Response
import com.patrykpirog.recipebook.feature_recipes.domain.repository.AddRecipeResponse
import com.patrykpirog.recipebook.feature_recipes.domain.repository.DeleteRecipeResponse
import com.patrykpirog.recipebook.feature_recipes.domain.repository.RecipesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesRepositoryImpl @Inject constructor(
    private val recipesRef: CollectionReference
) : RecipesRepository {

    override fun getRecipesFromFirestore() = callbackFlow {
        val snapshotListener = recipesRef
            .orderBy(RECIPE_NAME_KEY).addSnapshotListener { snapshot, e ->
            val recipesResponse = if (snapshot != null) {
                val recipes = mutableListOf<Recipe>()
                for(document in snapshot.documents) {
                    val recipe = document.toObject(Recipe::class.java)
                    recipe!!.id = document.id
                    recipes.add(recipe)
                }
                Response.Success(recipes)
            } else {
                Response.Failure(e)
            }
            trySend(recipesResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addRecipeToFirestore(recipe: Recipe): AddRecipeResponse {
        return try {
            recipesRef.document().set(recipe).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun deleteRecipeFromFirestore(recipe: Recipe): DeleteRecipeResponse{
        return try {
            recipesRef.document(recipe.id!!).delete().await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}