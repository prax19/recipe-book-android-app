package com.patrykpirog.recipebook.feature_recipes.presentation.add_edit_recipe

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.commons.Constants.SERVICE_USER_UUID
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe

class AddEditRecipeViewModel: ViewModel(){

    fun addRecipe(recipe: Recipe) {
        Firebase.firestore.collection("users")
            .document(SERVICE_USER_UUID).collection("recipes")
            .add(recipe)
    }

}