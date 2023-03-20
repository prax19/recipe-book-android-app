package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.data.Recipe
import com.patrykpirog.recipebook.di.AppModule

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(paddingValues: PaddingValues) {
    val recipes = remember { loadRecipes() }
    val scrollBehavior =
        exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                0.dp,
                0.dp,
                0.dp,
                paddingValues.calculateBottomPadding()
            )
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("Recipes")
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        it.calculateStartPadding(LayoutDirection.Ltr),
                        it.calculateTopPadding(),
                        it.calculateEndPadding(LayoutDirection.Ltr),
                        0.dp
                    )
            ) {
                item {
                    recipes.forEach { recipe ->
                        RecipeCard(recipe)
                    }
                }
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    recipe: Recipe
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(16.dp, 4.dp),
        onClick = {

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
        )
    )
}