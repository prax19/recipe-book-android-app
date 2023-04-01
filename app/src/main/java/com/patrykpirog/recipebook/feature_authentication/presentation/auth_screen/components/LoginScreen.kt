package com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen.AuthScreenViewModel
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.MainScreen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    navController: NavHostController
){
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.authState.collectAsState(initial = null)

    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .Center

        ) {
            val spaceBetween = 16.dp

            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = {
                    viewModel.updateEmail(it)
                }
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = {
                    viewModel.updatePassword(it)
                }
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            Button(
                modifier = Modifier
                    .width(256.dp)
                    .height(54.dp),
                onClick = {
                    scope.launch {
                        viewModel.loginUser()
                    }
                },
                content = {
                    Text(text = "Log in")
                },
                enabled = viewModel.buttonsEnabled.value
            )

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedButton(
                modifier = Modifier
                    .width(256.dp)
                    .height(54.dp),
                onClick = {
                    scope.launch {
                        viewModel.registerUser()
                    }
                },
                content = {
                    Text(text = "Sign up")
                },
                enabled = viewModel.buttonsEnabled.value
            )

            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if(state.value?.isSuccess?.isNotEmpty() == true) {
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
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }
}
