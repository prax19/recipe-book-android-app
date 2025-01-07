package pl.prax19.recipe_book_app

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.prax19.recipe_book_app.presentation.MainView
import pl.prax19.recipe_book_app.ui.theme.RecipeBookTheme
import pl.prax19.recipe_book_app.utils.Screen

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            RecipeBookTheme {
                val navController = rememberNavController()
                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainView.route
                    ) {
                        composable(route = Screen.MainView.route) {
                            MainView()
                        }
                    }
                }
            }
        }
    }
}