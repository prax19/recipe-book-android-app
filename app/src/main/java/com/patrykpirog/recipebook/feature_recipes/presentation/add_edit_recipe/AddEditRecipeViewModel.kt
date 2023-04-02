package com.patrykpirog.recipebook.feature_recipes.presentation.add_edit_recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.domain.model.Response
import com.patrykpirog.recipebook.feature_recipes.domain.repository.AddRecipeResponse
import com.patrykpirog.recipebook.feature_recipes.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel(){

    var addRecipeResponse by mutableStateOf<AddRecipeResponse>(Response.Success(false))
        private set

    fun addRecipe(recipe: Recipe) = viewModelScope.launch {
        addRecipeResponse = Response.Loading
        addRecipeResponse = useCases.addRecipe(recipe)
    }

}