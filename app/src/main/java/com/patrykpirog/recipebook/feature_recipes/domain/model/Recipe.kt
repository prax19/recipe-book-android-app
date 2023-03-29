package com.patrykpirog.recipebook.feature_recipes.domain.model

data class Recipe(
    val id: String? = null,
    val name: String,
    val description: String? = null,
    val ingredients: String? = null,
    val steps: String? = null
)
