package com.patrykpirog.recipebook.data

data class Recipe(
    val id: String,
    val name: String,
    val description: String? = null,
    val ingredients: String? = null,
    val steps: String? = null
)
