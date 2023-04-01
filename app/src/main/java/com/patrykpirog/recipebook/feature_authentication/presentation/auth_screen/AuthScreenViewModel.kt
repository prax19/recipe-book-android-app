package com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.patrykpirog.recipebook.feature_authentication.domain.model.AuthState
import com.patrykpirog.recipebook.feature_authentication.domain.model.Response
import com.patrykpirog.recipebook.feature_authentication.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val repository: AuthRepository,
    firebaseAuth: FirebaseAuth
) : ViewModel() {

    val isLoggedIn = mutableStateOf(firebaseAuth.currentUser?.uid.isNullOrEmpty())

    var email = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set

    var buttonsEnabled = mutableStateOf(false)
        private set
    var isEmailValid = mutableStateOf(false)

    private val _authState = Channel<AuthState>()
    val authState = _authState.receiveAsFlow()

    fun updateEmail(text: String) {
        email.value = text
        changeButtonState()
        checkIfEmailValid()
    }

    fun updatePassword(text: String) {
        password.value = text
        changeButtonState()
    }

    private fun changeButtonState() {
        buttonsEnabled.value = email.value.isNotEmpty() &&
                password.value.isNotEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    fun checkIfEmailValid() {
        isEmailValid.value = android.util.Patterns.EMAIL_ADDRESS.matcher(this.email.value).matches()
    }

    fun registerUser() = viewModelScope.launch {
        repository.registerUser(email.value, password.value).collect{ result ->
            when(result) {
                is Response.Success -> {
                    _authState.send(AuthState(isSuccess = "Sign Up sccess "))
                }
                is Response.Loading -> {
                    _authState.send(AuthState(isLoading = true))
                }
                is Response.Error -> {
                    _authState.send(AuthState(isError = result.message))
                }
            }
        }
    }

    fun loginUser() = viewModelScope.launch {
        repository.loginUser(email.value, password.value).collect{ result ->
            when(result) {
                is Response.Success -> {
                    _authState.send(AuthState(isSuccess = "Sign In sccess "))
                }
                is Response.Loading -> {
                    _authState.send(AuthState(isLoading = true))
                }
                is Response.Error -> {
                    _authState.send(AuthState(isError = result.message))
                }
            }
        }
    }

}