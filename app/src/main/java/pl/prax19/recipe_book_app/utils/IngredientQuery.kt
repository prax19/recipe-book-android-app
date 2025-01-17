package pl.prax19.recipe_book_app.utils

data class IngredientQuery(
    val product: String,
    val amount: Double ?= null,
    val unit: String ?= null
) {

    companion object {

        fun parse(query: String): IngredientQuery {
            val regex = Regex("""(\d+\.?\d*)\s*([a-zA-Z]+)\s*(.+?)\s*$|(.+?)\s+(\d+\.?\d*)\s*([a-zA-Z]+)""")

            val matchResult = regex.matchEntire(query.trim())
            return if (matchResult != null) {
                val (amount1, unit1, name1, name2, amount2, unit2) = matchResult.destructured
                if (amount1.isNotEmpty() && unit1.isNotEmpty()) {
                    IngredientQuery(name1.trim(), amount1.toDouble(), unit1)
                } else if (amount2.isNotEmpty() && unit2.isNotEmpty()) {
                    IngredientQuery(name2.trim(), amount2.toDouble(), unit2)
                } else {
                    IngredientQuery(query.trim(), null, null)
                }
            } else {
                return IngredientQuery(query.trim(), null, null)
            }

        }
    }

}