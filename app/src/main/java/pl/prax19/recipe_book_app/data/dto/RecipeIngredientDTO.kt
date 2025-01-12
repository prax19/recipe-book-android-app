package pl.prax19.recipe_book_app.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "recipe_ingredients")
data class RecipeIngredientDTO (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo val recipeId: UUID,
    @ColumnInfo val ingredientId: UUID,
    @ColumnInfo val amount: Double ?= null,
    @ColumnInfo val unit: String ?= null
)