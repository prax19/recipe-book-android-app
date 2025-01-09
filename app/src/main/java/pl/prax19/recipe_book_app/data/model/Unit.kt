package pl.prax19.recipe_book_app.data.model

sealed class Unit(
    val symbol: String,
    val type: Type,
    val multiplier: Double
) {

    data object Gram : Unit("g", Type.WEIGHT, 1.0)
    data object Kilogram : Unit("kg", Type.WEIGHT, 1000.0)

    data object Liter : Unit("l", Type.VOLUME, 1.0)
    data object Milliliter : Unit("ml", Type.VOLUME, 0.001)

    enum class Type {
        WEIGHT, VOLUME, COUNT
    }

}