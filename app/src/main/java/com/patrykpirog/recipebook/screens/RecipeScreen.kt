package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.patrykpirog.recipebook.data.Recipe

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(recipe: Recipe){
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = recipe.name)
                }
            )
        },
        content = {

        }
    )
}