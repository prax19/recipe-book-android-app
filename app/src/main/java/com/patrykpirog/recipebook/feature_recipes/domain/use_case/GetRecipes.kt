package com.patrykpirog.recipebook.feature_recipes.domain.use_case

import com.patrykpirog.recipebook.feature_recipes.domain.repository.RecipesRepository

class GetRecipes(
    private val repository: RecipesRepository
) {
    operator fun invoke() = repository.getRecipesFromFirestore()
}