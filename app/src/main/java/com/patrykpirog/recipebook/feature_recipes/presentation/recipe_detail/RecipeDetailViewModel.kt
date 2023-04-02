package com.patrykpirog.recipebook.feature_recipes.presentation.recipe_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.domain.model.Response
import com.patrykpirog.recipebook.feature_recipes.domain.repository.DeleteRecipeResponse
import com.patrykpirog.recipebook.feature_recipes.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var deleteRecipeResponse by mutableStateOf<DeleteRecipeResponse>(Response.Success(false))
        private set

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        deleteRecipeResponse = Response.Loading
        deleteRecipeResponse = useCases.deleteRecipe(recipe)
    }

}