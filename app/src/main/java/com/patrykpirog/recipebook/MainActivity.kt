package com.patrykpirog.recipebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.patrykpirog.recipebook.navigation.MainNavGraph
import com.patrykpirog.recipebook.ui.theme.RecipeBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.statusBarColor = Color.Transparent.toArgb()
//        window.navigationBarColor = Color.Transparent.toArgb()
//        window.isNavigationBarContrastEnforced = false
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            var navController = rememberNavController()
            RecipeBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavGraph(navController = navController)
                }
            }
        }
    }
}