package com.patrykpirog.recipebook.feature_recipes.domain.use_case

import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.domain.repository.RecipesRepository

class AddRecipe(
    private val repository: RecipesRepository
) {
    suspend operator fun invoke(
        recipe: Recipe
    ) = repository.addRecipeToFirestore(recipe)
}