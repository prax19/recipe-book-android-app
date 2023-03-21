package com.patrykpirog.recipebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patrykpirog.recipebook.auth.SignUpScreen
import com.patrykpirog.recipebook.di.AppModule
import com.patrykpirog.recipebook.screens.AddRecipeScreen
import com.patrykpirog.recipebook.screens.MainMenuScreen
import com.patrykpirog.recipebook.screens.RecipeScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.LoginScreen.route
    ) {
        composable(route = MainScreen.LoginScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(route = MainScreen.MainMenu.route) {
            MainMenuScreen(mainNavController=navController)
        }

        composable(route = MainScreen.AddRecipeScreen.route) {
            AddRecipeScreen(navController)
        }

        composable(route = MainScreen.RecipeScreen.route) {
            RecipeScreen(AppModule.recipe!!)
        }
    }
}