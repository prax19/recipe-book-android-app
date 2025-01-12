package pl.prax19.recipe_book_app.data.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import pl.prax19.recipe_book_app.data.model.Ingredient
import pl.prax19.recipe_book_app.data.model.Recipe
import pl.prax19.recipe_book_app.data.model.RecipeIngredient
import pl.prax19.recipe_book_app.data.model.RecipeStep
import java.util.UUID
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
// TODO: add a proper database binding

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: MutableStateFlow<List<Recipe>> = _recipes

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: MutableStateFlow<List<Ingredient>> = _ingredients

    private val _recipeSteps = MutableStateFlow<List<RecipeStep>>(emptyList())
    val recipeSteps: MutableStateFlow<List<RecipeStep>> = _recipeSteps

    private val _recipeIngredients = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    val recipeIngredients: MutableStateFlow<List<RecipeIngredient>> = _recipeIngredients

    init {
        addTestData()
    }

    private fun addTestData() {
        // TODO: remove this when database is ready
        val testRecipes = (0 until 3).map { i ->
            Recipe(name = "Test recipe $i")
        }

        // Example recipe
        val uuid_example = UUID.randomUUID()
        val recipe_example = Recipe(
            id = uuid_example,
            name = "Lasagne Bolognese",
            description = "Oryginalna, włoska lazania.",
            source = "https://www.kwestiasmaku.com/pasta/lasagne_bolognese/przepis.html"
        )
        val ingredients_example = listOf(
            Ingredient(
                name = "płatki lasagne"
            ),
            Ingredient(
                name = "tarty parmezan"
            ),
            Ingredient(
                name = "oliwa z oliwek"
            ),
            Ingredient(
                name = "cebula"
            ),
            Ingredient(
                name = "łodyga seleru naciowego"
            ),
            Ingredient(
                name = "marchewka"
            ),
            Ingredient(
                name = "boczek wędzony"
            ),
            Ingredient(
                name = "mięso wołowo - wieprzowe mielone"
            ),
            Ingredient(
                name = "białe wino"
            ),
            Ingredient(
                name = "koncentrat pomidorowy"
            ),
            Ingredient(
                name = "bulion"
            ),
            Ingredient(
                name = "passata pomidorowa"
            ),
            Ingredient(
                name = "masło"
            ),
            Ingredient(
                name = "mąka"
            ),
            Ingredient(
                name = "mleko"
            ),
            Ingredient(
                name = "gałka muszkatołowa"
            )
        )
        val recipe_ingredient_example = listOf<RecipeIngredient>(
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[0],
                amount = 15.0,
                unit = "x"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[1],
                amount = 150.0,
                unit = "g"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[2],
                amount = 3.0,
                unit = "x łyżka"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[3],
                amount = 1.0,
                unit = "x"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[4],
                amount = 2.0,
                unit = "x"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[5],
                amount = 1.0,
                unit = "x"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[6],
                amount = 150.0,
                unit = "g"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[7],
                amount = 500.0,
                unit = "g"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[8],
                amount = 1.0,
                unit = "x szklanka"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[9],
                amount = 4.0,
                unit = "x łyżka"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[10],
                amount = 1.0,
                unit = "x szklanka"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[11],
                amount = 400.0,
                unit = "g"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[12],
                amount = 4.0,
                unit = "x łyżka"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[13],
                amount = 3.0,
                unit = "x łyżka"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[14],
                amount = 650.0,
                unit = "ml"
            ),
            RecipeIngredient(
                recipeId = uuid_example,
                ingredient = ingredients_example[15],
                amount = 1.0,
                unit = "x szczypta"
            ),
        )

        val steps_example = listOf(
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 0,
                description = "Na oliwie, w dużym garnku, zeszklić drobno posiekaną cebulę, dodać posiekany w drobną kosteczkę seler naciowy oraz startą marchewkę (warzywa można też rozdrobnić w malakserze)."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 1,
                description = "Obsmażyć, następnie przesunąć na bok i w wolne miejsce włożyć pokrojony w drobną kosteczkę boczek. Zrumienić i wymieszać z warzywami."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 2,
                description = "Przesunąć wszystko na bok garnka i partiami wkładać mięso: włożyć 1/3 część mięsa i obsmażyć mieszając co chwilę, aż zmieni kolor z czerwonego na brązowy. Następnie wymieszać z warzywami i boczkiem, przesunąć na bok, powtórzyć z resztą mięsa."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 3,
                description = "Wlać wino i gotować na średnim ogniu przez 3 minuty, dodać gorący bulion wymieszany z koncentratem pomidorowym, zagotować, dodać passatę pomidorową."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 4,
                description = "Doprawić solą i pieprzem. Przykryć i gotować na małym ogniu 2 godziny (można dłużej). Od czasu do czasu zamieszać."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 5,
                description = "W średnim garnku dobrze rozgrzać masło, dodać mąkę i smażyć przez około 2 minuty ciągle mieszając. Stopniowo wlewać mleko cały czas energicznie mieszając aż sos będzie gładki."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 6,
                description = "Gotować na wolnym ogniu przez kilka minut. Odstawić z ognia, doprawić solą i gałką muszkatołową."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 7,
                description = "Piekarnik nagrzać do 175 stopni C (grzanie góra-dół bez termoobiegu). Przygotować żaroodporną formę o wymiarach około 19 x 27 cm. Wysmarować ją masłem, wlać i rozprowadzić ok. 100 ml sosu beszamelowego, ułożyć pierwszą warstwę płatów lasagne (mogą nieznacznie na siebie nachodzić)."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 8,
                description = "Na płatach lasagne rozprowadzić warstwę sosu bolońskiego, następnie polać sosem beszamelowym i posypać parmezanem. Powtórzyć jeszcze 4-krotnie."
            ),
            RecipeStep(
                recipeId = uuid_example,
                stepIndex = 9,
                description = "Tak złożoną lasagne włożyć do piekarnika i piec 45 minut (w połowie czasu pieczenia przykryć folią aluminiową). Po wyjęciu z piekarnika odczekać 5 minut przed porcjowaniem."
            ),
        )

        _recipes.update { it + testRecipes + recipe_example }
        _ingredients.update { it + ingredients_example }
        _recipeIngredients.update { it + recipe_ingredient_example }
        _recipeSteps.update { it + steps_example }
    }

    fun upsertRecipe(
        recipe: Recipe,
        ingredients: List<RecipeIngredient> = emptyList(),
        steps: List<RecipeStep> = emptyList()
    ) {
        removeRecipeIngredientByRecipeId(recipeId = recipe.id)
        removeRecipeStepsByRecipeId(recipeId = recipe.id)
        _recipes.update { it + recipe }
        _recipeIngredients.update { it + ingredients }
        _recipeSteps.update { it + steps }
    }

    fun getAllRecipes(): Flow<List<Recipe>> {
        return recipes
    }

    fun getRecipeById(recipeId: UUID): Flow<Recipe> {
        return _recipes.map { it.first { it.id == recipeId } }
    }

    fun getRecipeIngredientsByRecipeId(recipeId: UUID): Flow<List<RecipeIngredient>> {
        return recipeIngredients.map { ingredients ->
            ingredients.filter { it.recipeId == recipeId }
        }
    }

    fun removeRecipeIngredientByRecipeId(recipeId: UUID) {
        _recipeIngredients.update { ingredients ->
            ingredients.filterNot { it.recipeId == recipeId }
        }
    }

    fun getRecipeStepsByRecipeId(recipeId: UUID): Flow<List<RecipeStep>> {
        return recipeSteps.map { steps ->
            steps.filter { it.recipeId == recipeId }
        }
    }

    fun removeRecipeStepsByRecipeId(recipeId: UUID) {
        _recipeSteps.update { steps ->
            steps.filterNot { it.recipeId == recipeId }
        }
    }

    fun addIngredient(ingredient: Ingredient) {
        _ingredients.update { it + ingredient }
    }

    fun getAllIngredients(): Flow<List<Ingredient>> {
        return ingredients
    }

    fun removeIngredientIfUnused(ingredient: Ingredient) {
        if(_recipeIngredients.value.none { it.ingredient.id == ingredient.id })
            _ingredients.update { it - ingredient }
    }

}