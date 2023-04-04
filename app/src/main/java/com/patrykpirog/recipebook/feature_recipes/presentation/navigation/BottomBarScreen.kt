package com.patrykpirog.recipebook.feature_recipes.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.patrykpirog.recipebook.R

sealed class BottomBarScreen(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector
) {
    object Recipes: BottomBarScreen(
        route = "recipes",
        titleResId = R.string.recipes,
        icon = Icons.Default.Home
    )

    object Favorites: BottomBarScreen(
        route = "favorites",
        titleResId = R.string.favorites,
        icon = Icons.Default.Favorite
    )

}