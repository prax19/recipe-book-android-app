package pl.prax19.recipe_book_app.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import java.util.UUID.randomUUID

@Entity(tableName = "recipe_steps")
data class RecipeStepDTO(
    @PrimaryKey val id: UUID = randomUUID(),
    @ColumnInfo val recipeId: UUID,
    @ColumnInfo val stepIndex: Int,
    @ColumnInfo val description: String
)