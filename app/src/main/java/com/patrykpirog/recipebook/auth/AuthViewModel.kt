package com.patrykpirog.recipebook.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykpirog.recipebook.data.AuthRepository
import com.patrykpirog.recipebook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val _authState = Channel<AuthState>()
    val authState = _authState.receiveAsFlow()

    fun registerUser(
        email: String,
        password: String
    ) = viewModelScope.launch {
        repository.registerUser(email, password).collect{ result ->
            when(result) {
                is Resource.Success -> {
                    _authState.send(AuthState(isSuccess = "Sign Up sccess "))
                }
                is Resource.Loading -> {
                    _authState.send(AuthState(isLoading = true))
                }
                is Resource.Error -> {
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
                is Resource.Success -> {
                    _authState.send(AuthState(isSuccess = "Sign In sccess "))
                }
                is Resource.Loading -> {
                    _authState.send(AuthState(isLoading = true))
                }
                is Resource.Error -> {
                    _authState.send(AuthState(isError = result.message))
                }
            }
        }
    }

}