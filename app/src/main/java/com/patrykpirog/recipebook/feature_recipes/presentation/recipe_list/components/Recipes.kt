package com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.patrykpirog.recipebook.feature_recipes.domain.model.Response
import com.patrykpirog.recipebook.feature_recipes.domain.repository.Recipes
import com.patrykpirog.recipebook.feature_recipes.presentation.MainMenuViewModel

@Composable
fun Recipes(
    viewModel: MainMenuViewModel = hiltViewModel(),
    recipesContent: @Composable (recipes: Recipes) -> Unit
) {
    when(val recipesResponse = viewModel.recipesResponse) {
        is Response.Loading -> Log.v("Recipes", "Loading")
        is Response.Success -> {
            recipesContent(recipesResponse.data)
            Log.v("Recipes", recipesResponse.data.toString())
        }
        is Response.Failure -> Log.e("Recipes", recipesResponse.e.toString())
    }
}