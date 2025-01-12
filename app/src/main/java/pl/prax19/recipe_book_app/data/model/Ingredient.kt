package pl.prax19.recipe_book_app.data.model

import java.util.UUID
import java.util.UUID.randomUUID

data class Ingredient(
    val id: UUID = randomUUID(),
    val name: String,
    //val defaultUnit: String ?= null
)