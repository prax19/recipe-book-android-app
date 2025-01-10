package pl.prax19.recipe_book_app.data.model

import java.util.UUID

data class RecipeIngredient (
    val id: UUID = UUID.randomUUID(),
    val recipeId: UUID,
    val ingredient: Ingredient,
    val amount: Double ?= null,
    val unit: String ?= null
)
