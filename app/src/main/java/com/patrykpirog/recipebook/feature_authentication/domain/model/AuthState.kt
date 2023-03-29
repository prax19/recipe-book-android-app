package com.patrykpirog.recipebook.feature_authentication.domain.model

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)
