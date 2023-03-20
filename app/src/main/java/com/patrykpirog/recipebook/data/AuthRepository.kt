package com.patrykpirog.recipebook.data

import com.google.firebase.auth.AuthResult
import com.patrykpirog.recipebook.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun loginUser(
        email: String,
        password: String):
            Flow<Resource<AuthResult>>

    fun registerUser(
        email: String,
        password: String):
            Flow<Resource<AuthResult>>
}