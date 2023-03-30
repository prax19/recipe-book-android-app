package com.patrykpirog.recipebook.feature_recipes.domain.model

data class Recipe(
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var ingredients: String? = null,
    var steps: String? = null
)
