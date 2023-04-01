package com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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

    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .Center

        ) {

            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .focusRequester(emailFocusRequester),
                value = viewModel.email.value,
                onValueChange = { text ->
                    viewModel.updateEmail(text)
                },
                leadingIcon = {
                    Icon(Icons.Default.Email, null)
                },
                label = {
                    Text("E-mail")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        if(viewModel.isEmailValid.value)
                            passwordFocusRequester.requestFocus()
                    }
                ),
                isError = !viewModel.isEmailValid.value && viewModel.email.value.isNotEmpty()

            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .focusRequester(passwordFocusRequester),
                value = viewModel.password.value,
                onValueChange = { text ->
                    viewModel.updatePassword(text)
                },
                leadingIcon = {
                    Icon(Icons.Default.Lock, null)
                },
                label = {
                    Text("Password")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {

                    }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

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

            Spacer(modifier = Modifier.height(16.dp))

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
