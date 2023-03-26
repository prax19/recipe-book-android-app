package com.patrykpirog.recipebook.screens.main_menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.patrykpirog.recipebook.navigation.BottomBarScreen

class MainMenuViewModel: ViewModel() {

    var mainTopAppBarText: String by mutableStateOf(BottomBarScreen.Recipes.title)
        private set

    var mainFabVisibility: Boolean by mutableStateOf(true)
        private set

    fun changeBottomScreen(route: String) {
        when(route) {
            BottomBarScreen.Recipes.route -> {
                mainTopAppBarText = BottomBarScreen.Recipes.title
                mainFabVisibility = true
            }
            BottomBarScreen.Favorites.route -> {
                mainTopAppBarText = BottomBarScreen.Favorites.title
                mainFabVisibility = false
            }
        }
    }
}
