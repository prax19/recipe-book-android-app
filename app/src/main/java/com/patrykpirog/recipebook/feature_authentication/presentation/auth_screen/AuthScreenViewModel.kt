package com.patrykpirog.recipebook.feature_authentication.presentation.auth_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = Channel<AuthState>()
    val authState = _authState.receiveAsFlow()

    fun registerUser(
        email: String,
        password: String
    ) = viewModelScope.launch {
        repository.registerUser(email, password).collect{ result ->
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

    fun loginUser(
        email: String,
        password: String
    ) = viewModelScope.launch {
        repository.loginUser(email, password).collect{ result ->
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