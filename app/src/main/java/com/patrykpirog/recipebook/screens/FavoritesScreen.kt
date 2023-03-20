package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "Favorites")
                }
            )
        },
        content = {

        }
    )
}

@Composable
@Preview
fun FavoritesScreenPreview() {
    FavoritesScreen()
}