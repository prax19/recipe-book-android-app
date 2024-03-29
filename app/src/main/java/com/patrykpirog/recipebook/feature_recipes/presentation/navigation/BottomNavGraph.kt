package com.patrykpirog.recipebook.feature_recipes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list.RecipesScreen
import com.patrykpirog.recipebook.screens.main_menu.favorites.FavoritesScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Recipes.route
    ) {
        composable(route = BottomBarScreen.Recipes.route) {
            RecipesScreen(
                mainNavController = mainNavController
            )
        }
        composable(route = BottomBarScreen.Favorites.route) {
            FavoritesScreen()
        }
    }
}