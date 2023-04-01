package com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firestore.admin.v1.Index.State
import com.patrykpirog.recipebook.feature_authentication.domain.model.AuthState
import com.patrykpirog.recipebook.feature_authentication.domain.model.Response
import com.patrykpirog.recipebook.feature_authentication.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
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

    private val _authState = Channel<AuthState>()
    val authState = _authState.receiveAsFlow()

    fun updateEmail(text: String) {
        email.value = text
        changeButtonState()
    }

    fun updatePassword(text: String) {
        password.value = text
        changeButtonState()
    }

    fun changeButtonState() {
        buttonsEnabled.value = !email.value.isNullOrEmpty() &&
                !password.value.isNullOrEmpty()
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