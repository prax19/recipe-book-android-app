package com.patrykpirog.recipebook.feature_recipes.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.commons.Constants.SERVICE_USER_UUID
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.domain.model.Response
import com.patrykpirog.recipebook.feature_recipes.domain.repository.RecipeResponse
import com.patrykpirog.recipebook.feature_recipes.domain.use_case.UseCases
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.BottomBarScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
        private val useCases: UseCases
): ViewModel() {

    var recipesResponse by mutableStateOf<RecipeResponse>(Response.Loading)
        private set

    var mainTopAppBarTextResourceId by mutableStateOf(BottomBarScreen.Recipes.titleResId)
        private set

    var mainFabVisibility: Boolean by mutableStateOf(true)
        private set

    init {
        getRecipes()
    }

    private fun getRecipes() = viewModelScope.launch {
        useCases.getRecipes().collect { response ->
            recipesResponse = response
        }
    }

    fun changeBottomScreen(route: String) {
        when(route) {
            BottomBarScreen.Recipes.route -> {
                mainTopAppBarTextResourceId = BottomBarScreen.Recipes.titleResId
                mainFabVisibility = true
            }
            BottomBarScreen.Favorites.route -> {
                mainTopAppBarTextResourceId = BottomBarScreen.Favorites.titleResId
                mainFabVisibility = false
            }
        }
    }

    @Deprecated("Old loading method")
    fun loadRecipes(): MutableList<Recipe> {
        Log.v("My testing logs", "Load DB")
        val recipes = mutableListOf<Recipe>()
        val db = Firebase.firestore
        val recipesRef = db.collection("users").document(SERVICE_USER_UUID) //TEST DOCUMENT NAME
            .collection("recipes")
        recipesRef.get().addOnSuccessListener { snapshot ->
            for ( recipe in snapshot.documents ) {
                recipes.add(
                    Recipe(
                        recipe.id,
                        recipe.get("name").toString(),
                        recipe.get("description")?.toString(),
                        recipe.get("ingredients")?.toString(),
                        recipe.get("steps")?.toString()
                    )
                )
            }
        }
        return recipes
    }

}
