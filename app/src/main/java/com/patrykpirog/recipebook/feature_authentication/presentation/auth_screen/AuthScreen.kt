package com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen.components.LoginScreen
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    when(!viewModel.isLoggedIn.value) {
        true -> {
            navController.navigate(MainScreen.MainMenu.route) {
                navController.popBackStack()
            }
        }
        false -> LoginScreen(navController=navController)
    }
}
