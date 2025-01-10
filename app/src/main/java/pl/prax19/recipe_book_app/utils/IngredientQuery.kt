package pl.prax19.recipe_book_app.utils

data class IngredientQuery(
    val product: String,
    val amount: Double ?= null,
    val unit: String ?= null
) {

    companion object {

        fun parse(query: String): IngredientQuery {
            // TODO: add regex to parse query
            return IngredientQuery(query, null, null)
        }

    }

}