package com.patrykpirog.recipebook.screens.recipe

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patrykpirog.recipebook.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    navController: NavController? = null,
    recipe: Recipe
){
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = recipe.name)
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.Delete, "Delete recipe")
                    }
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.Edit, "Edit recipe")
                    }
                },
                navigationIcon = {
                    if(navController != null)
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    if(recipe.description != "")
                        RecipeText(
                            headline = "Description",
                            text = recipe.description.toString())
                }
                item {
                    if(recipe.ingredients != "")
                        RecipeText(
                            headline = "Ingradients",
                            text = recipe.ingredients .toString())
                }
                item {
                    if(recipe.steps != "")
                        RecipeText(
                            headline = "Steps",
                            text = recipe.steps.toString())
                }
            }
        }
    )
}

@Composable
fun RecipeText(
    headline: String,
    text: String
){
    Text(
        text = headline,
        style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
    )
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
    )
}