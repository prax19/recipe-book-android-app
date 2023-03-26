package com.patrykpirog.recipebook.navigation

sealed class MainScreen(
    val route: String,
    val title: String
) {

    object LoginScreen: MainScreen(
        route = "login",
        title = "Login"
    )

    object MainMenu: MainScreen(
        route = "mainMenu",
        title = "Main Menu"
    )

    object AddRecipeScreen: MainScreen(
        route = "addRecipe",
        title = "Add Recipe"
    )

    object RecipeScreen: MainScreen(
        route = "recipe",
        title = "Recipe"
    )

    object SettingsScreen: MainScreen(
        route = "settings",
        title = "Settings"
    )

}
