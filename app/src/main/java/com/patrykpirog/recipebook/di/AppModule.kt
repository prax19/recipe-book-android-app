package com.patrykpirog.recipebook.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //Cursed af XD
    var recipe: Recipe?= null

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideRecipesRef(
        firebaseAuth: FirebaseAuth
    ) = Firebase.firestore
        .collection("users")
        .document("${firebaseAuth.currentUser?.uid}")
        .collection("recipes")

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