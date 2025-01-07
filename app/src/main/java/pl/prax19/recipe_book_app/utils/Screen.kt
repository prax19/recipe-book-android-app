package pl.prax19.recipe_book_app.utils

sealed class Screen(val route: String) {

    data object MainView : Screen("main_screen")

}