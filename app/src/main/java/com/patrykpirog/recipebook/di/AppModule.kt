package com.patrykpirog.recipebook.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.commons.Constants.SERVICE_USER_UUID
import com.patrykpirog.recipebook.feature_authentication.data.AuthRepositoryImpl
import com.patrykpirog.recipebook.feature_authentication.domain.repository.AuthRepository
import com.patrykpirog.recipebook.feature_recipes.data.repository.RecipesRepositoryImpl
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.domain.repository.RecipesRepository
import com.patrykpirog.recipebook.feature_recipes.domain.use_case.GetRecipes
import com.patrykpirog.recipebook.feature_recipes.domain.use_case.UseCases
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

    @Provides
    fun provideRecipesRef() = Firebase.firestore.collection("/users/${SERVICE_USER_UUID}/recipes")

    @Provides
    fun provideRecipesRepository(
        recipesRef: CollectionReference
    ): RecipesRepository = RecipesRepositoryImpl(recipesRef)

    @Provides
    fun provideUseCases(
        repo: RecipesRepository
    ) = UseCases(
        getRecipes = GetRecipes(repo),
//        addRecipe = AddRecipe(repo),
//        deleteRecipe = DeleteRecipe(repo)
    )

}