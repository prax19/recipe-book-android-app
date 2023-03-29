package com.patrykpirog.recipebook.feature_recipes.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.di.AppModule
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.BottomBarScreen

class MainMenuViewModel: ViewModel() {

    var recipes: MutableList<Recipe> = loadRecipes()

    var mainTopAppBarText: String by mutableStateOf(BottomBarScreen.Recipes.title)
        private set

    var mainFabVisibility: Boolean by mutableStateOf(true)
        private set

    fun changeBottomScreen(route: String) {
        when(route) {
            BottomBarScreen.Recipes.route -> {
                mainTopAppBarText = BottomBarScreen.Recipes.title
                mainFabVisibility = true
            }
            BottomBarScreen.Favorites.route -> {
                mainTopAppBarText = BottomBarScreen.Favorites.title
                mainFabVisibility = false
            }
        }
    }

    fun loadRecipes(): MutableList<Recipe> {
        Log.v("My testing logs", "Load DB")
        val recipes = mutableListOf<Recipe>()
        val db = Firebase.firestore
        val recipesRef = db.collection("users").document(AppModule.providesFirebaseAuth().uid.toString())
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
