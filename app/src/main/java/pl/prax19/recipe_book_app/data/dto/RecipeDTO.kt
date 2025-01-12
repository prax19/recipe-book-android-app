package pl.prax19.recipe_book_app.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import java.util.UUID.randomUUID

@Entity(tableName = "recipes")
data class RecipeDTO(
    @PrimaryKey val id: UUID = randomUUID(),
    @ColumnInfo val name: String,
    @ColumnInfo val description: String ?= null,
    @ColumnInfo val source: String ?= null
)