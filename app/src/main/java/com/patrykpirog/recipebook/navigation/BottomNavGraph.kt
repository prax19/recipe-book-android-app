package com.patrykpirog.recipebook.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patrykpirog.recipebook.screens.FavoritesScreen
import com.patrykpirog.recipebook.screens.RecipesScreen
import com.patrykpirog.recipebook.screens.SettingsScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    mainNavController: NavController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Recipes.route
    ) {
        composable(route = BottomBarScreen.Recipes.route) {
            RecipesScreen(
                paddingValues,
                mainNavController
            )
        }
        composable(route = BottomBarScreen.Favorites.route) {
            FavoritesScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
    }
}