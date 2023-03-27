package com.patrykpirog.recipebook.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.patrykpirog.recipebook.auth.AuthViewModel
import com.patrykpirog.recipebook.navigation.MainScreen
import com.patrykpirog.recipebook.screens.main_menu.MainMenuViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by rememberSaveable { mutableStateOf("example@example.com") }
    var password by rememberSaveable { mutableStateOf("example") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.authState.collectAsState(initial = null)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme
                            .primary
                            .copy(alpha = 0.08f)
                            .compositeOver((MaterialTheme.colorScheme.surface.copy()))
                    )
                )
            )

    ) {

        Column {
            TextField(value = email, onValueChange = {
                email = it
            })
            TextField(value = password, onValueChange = {
                password = it
            })
            Row(){
                Button(onClick = {
                    scope.launch {
                        viewModel.loginUser(email, password)
                    }
                })
                {
                    Text(text = "Sign in")
                }
                Button(onClick = {
                    scope.launch {
                        viewModel.registerUser(email, password)
                    }
                })
                {
                    Text(text = "Sign up")
                }
            }

            Row {
                if (state.value?.isLoading == true) {
                    CircularProgressIndicator()

                }
            }

            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if(state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                        navController.navigate(MainScreen.MainMenu.route) {
                            navController.popBackStack()
                        }
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if(state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}
