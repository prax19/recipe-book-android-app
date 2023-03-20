package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import android.media.Image
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.data.Recipe
import com.patrykpirog.recipebook.di.AppModule

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(padding: PaddingValues) {
    //val scrollBehavior = exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val recipes = remember { loadRecipes() }
    Scaffold(
        modifier = Modifier
            .padding(padding),
            //.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    recipes.forEach { recipe ->
                        RecipeCard(recipe)
                    }
                }
            }
        },
        topBar = {
            LargeTopAppBar(
                title = {
                    Text("Recipes")
                },
                //scrollBehavior = scrollBehavior
            )
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
    var db = Firebase.firestore
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