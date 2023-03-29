package com.patrykpirog.recipebook.di

import com.google.firebase.auth.FirebaseAuth
import com.patrykpirog.recipebook.feature_authentication.domain.repository.AuthRepository
import com.patrykpirog.recipebook.feature_authentication.data.AuthRepositoryImpl
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Cursed af XD
    var recipe: Recipe?= null

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

}