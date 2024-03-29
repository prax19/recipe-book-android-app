package com.patrykpirog.recipebook.feature_recipes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen.SignUpScreen
import com.patrykpirog.recipebook.feature_recipes.presentation.add_edit_recipe.AddRecipeScreen
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_detail.RecipeScreen
import com.patrykpirog.recipebook.feature_recipes.presentation.settings.SettingsScreen
import com.patrykpirog.recipebook.screens.main_menu.MainMenuScreen

@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.LoginScreen.route
    ) {
        composable(route = MainScreen.LoginScreen.route) {
            SignUpScreen(
                navController = navController
            )
        }

        composable(route = MainScreen.MainMenu.route) {
            MainMenuScreen(
                navController = navController
            )
        }

        composable(route = MainScreen.AddRecipeScreen.route) {
            AddRecipeScreen(
                navController = navController
            )
        }

        composable(route = MainScreen.RecipeScreen.route) {
            RecipeScreen(
                navController = navController
            )
        }

        composable(route = MainScreen.SettingsScreen.route) {
            SettingsScreen(
                navController = navController
            )
        }
    }
}