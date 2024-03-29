package com.patrykpirog.recipebook.feature_recipes.presentation.recipe_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.patrykpirog.recipebook.di.AppModule
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    recipe: Recipe,
    mainNavController: NavHostController
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp),
        onClick = {
            AppModule.recipe = recipe
            mainNavController.navigate(MainScreen.RecipeScreen.route)
        }
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = recipe.name!!
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (recipe.description != null) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = recipe.description.toString()
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}
@Composable
@Preview(showBackground = true)
fun RecipeCardPreview() {
    RecipeCard(recipe = Recipe(
        "69",
        "Example recipe text",
        "Example recipe description." +
                "\nIt can be" +
                "\nmultiline!"
    ),
        rememberNavController()
    )
}