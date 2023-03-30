package com.patrykpirog.recipebook.screens.main_menu.recipes

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patrykpirog.recipebook.feature_recipes.presentation.MainMenuViewModel
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list.components.RecipeCard
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list.components.Recipes


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipesScreen(
    mainViewModel: MainMenuViewModel = hiltViewModel(),
    mainNavController: NavHostController
) {
    Recipes(
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
//                    count = recipes.size,
//                    key = { id ->
//                        item{
//                            RecipeCard(
//                                recipe = recipes[id],
//                                mainNavController = mainNavController)
//                        }
//                    }
//                ) {}
//            }
        }
    )
}