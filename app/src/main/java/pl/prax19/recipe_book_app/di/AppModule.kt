package pl.prax19.recipe_book_app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.prax19.recipe_book_app.data.database.RecipeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(): RecipeRepository {
        return RecipeRepository()
    }

}