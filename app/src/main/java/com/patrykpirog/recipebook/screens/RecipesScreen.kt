package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.data.Recipe
import com.patrykpirog.recipebook.di.AppModule
import com.patrykpirog.recipebook.navigation.MainScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipesScreen(
    mainNavController: NavHostController,
    recipes: MutableList<Recipe>
) {
        LazyColumn(
            content = {
                item {
                    recipes.forEach { recipe ->
                        RecipeCard(
                            recipe,
                            mainNavController
                        )
                    }
                }
            }
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    recipe: Recipe,
    mainNavController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(16.dp, 4.dp),
        onClick = {
            AppModule.recipe = recipe
            mainNavController.navigate(MainScreen.RecipeScreen.route)
        }
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = recipe.name
            )
            if (recipe.description != null) {
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    text = recipe.description
                )
            }
        }
    }
}

fun loadRecipes(): MutableList<Recipe> {
    val recipes = mutableStateListOf<Recipe>()
    val db = Firebase.firestore
    val recipesRef = db.collection("users").document(AppModule.providesFirebaseAuth().uid.toString())
        .collection("recipes")
    recipesRef.get().addOnSuccessListener { snapshot ->
        for ( recipe in snapshot.documents ) {
            recipes.add(
                Recipe(
                    recipe.id,
                    recipe.get("name").toString(),
                    recipe.get("description")?.toString(),
                    recipe.get("ingredients")?.toString(),
                    recipe.get("steps")?.toString()
                )
            )
        }
    }
    return recipes
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