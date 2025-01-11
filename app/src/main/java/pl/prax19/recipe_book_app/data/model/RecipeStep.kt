package pl.prax19.recipe_book_app.data.model

import java.util.UUID

data class RecipeStep(
    val id: UUID = UUID.randomUUID(),
    val recipeId: UUID,
    val stepIndex: Int, // step position in recipe
    val description: String
)