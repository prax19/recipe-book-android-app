package com.patrykpirog.recipebook.feature_authentication.domain.repository

import com.google.firebase.auth.AuthResult
import com.patrykpirog.recipebook.feature_authentication.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(
        email: String,
        password: String):
            Flow<Response<AuthResult>>

    fun registerUser(
        email: String,
        password: String):
            Flow<Response<AuthResult>>
}