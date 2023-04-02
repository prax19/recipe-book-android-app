package com.patrykpirog.recipebook.feature_recipes.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.MainNavGraph
import com.patrykpirog.recipebook.ui.theme.RecipeBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var mainNavController: NavHostController
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val uiController = rememberSystemUiController()

            uiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = !isSystemInDarkTheme()
            )
            uiController.setNavigationBarColor(
                color = Color.Transparent,
                darkIcons = !isSystemInDarkTheme()
            )
            uiController.isNavigationBarContrastEnforced = false
            WindowCompat.setDecorFitsSystemWindows(window, false)

            RecipeBookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    mainNavController = rememberNavController()
                    MainNavGraph(
                        navController = mainNavController
                    )
                }
            }
        }
    }
}