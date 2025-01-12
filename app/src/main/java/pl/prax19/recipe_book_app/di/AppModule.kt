package pl.prax19.recipe_book_app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.prax19.recipe_book_app.data.database.RecipeBookDatabase
import pl.prax19.recipe_book_app.data.database.RecipeDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): RecipeBookDatabase {
        return Room.databaseBuilder(
            context = context,
            RecipeBookDatabase::class.java,
            "recipe_book_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShiftDao(database: RecipeBookDatabase): RecipeDAO {
        return database.recipeDAO()
    }

}