package com.patrykpirog.recipebook.feature_recipes.presentation.navigation

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.content.res.TypedArrayUtils.getString
import com.patrykpirog.recipebook.R
import com.patrykpirog.recipebook.RecipeBookApplication
import com.patrykpirog.recipebook.feature_recipes.presentation.MainActivity

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Recipes: BottomBarScreen(
        route = "recipes",
        title = "Recipes",
        icon = Icons.Default.Home
    )

    object Favorites: BottomBarScreen(
        route = "favorites",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )

}