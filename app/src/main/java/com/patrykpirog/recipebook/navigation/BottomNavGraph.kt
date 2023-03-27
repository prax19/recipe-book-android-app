package com.patrykpirog.recipebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patrykpirog.recipebook.data.Recipe
import com.patrykpirog.recipebook.screens.main_menu.favorites.FavoritesScreen
import com.patrykpirog.recipebook.screens.main_menu.recipes.RecipesScreen

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