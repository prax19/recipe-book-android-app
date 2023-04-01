package com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list.components.RecipeCard
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list.components.RecipesFrame


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipesScreen(
    mainNavController: NavHostController
) {
    RecipesFrame(
        recipesContent = { recipes ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(recipes) { recipe ->
                            RecipeCard(
                                recipe = recipe,
                                mainNavController = mainNavController)
                        }
                }
        }
    )
}