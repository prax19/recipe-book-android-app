package com.patrykpirog.recipebook.feature_recipes.presentation.add_edit_recipe

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe

class AddEditRecipeViewModel: ViewModel(){

    fun addRecipe(recipe: Recipe) {
        Firebase.firestore.collection("users")
            .document("TEST123").collection("recipes")
            .add(recipe)
    }

}