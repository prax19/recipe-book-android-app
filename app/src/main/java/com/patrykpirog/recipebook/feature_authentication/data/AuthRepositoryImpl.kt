package com.patrykpirog.recipebook.feature_authentication.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.patrykpirog.recipebook.feature_authentication.domain.model.Response
import com.patrykpirog.recipebook.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            emit(Response.Success(result))
        }.catch {
            emit(Response.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            emit(Response.Success(result))
        }.catch {
            emit(Response.Error(it.message.toString()))
        }
    }

    override fun logOutUser() {
        firebaseAuth.signOut()
    }
}