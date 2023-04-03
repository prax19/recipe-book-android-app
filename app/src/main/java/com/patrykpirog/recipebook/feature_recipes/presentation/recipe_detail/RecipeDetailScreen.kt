package com.patrykpirog.recipebook.feature_recipes.presentation.recipe_detail

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patrykpirog.recipebook.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_detail.components.DeleteRecipeDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    navController: NavController? = null
){
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = viewModel.currentRecipe?.name.toString())
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.showDeleteDialog()
                    }) {
                        Icon(Icons.Default.Delete, null)
                    }
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.Edit, null)
                    }
                },
                navigationIcon = {
                    if(navController != null)
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Default.ArrowBack, null)
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
                    if(!viewModel.currentRecipe?.description.isNullOrEmpty())
                        RecipeText(
                            headline = stringResource(id = R.string.description),
                            text = viewModel.currentRecipe?.description.toString())
                }
                item {
                    if(!viewModel.currentRecipe?.ingredients.isNullOrEmpty())
                        RecipeText(
                            headline = stringResource(id = R.string.ingredients),
                            text = viewModel.currentRecipe?.ingredients.toString())
                }
                item {
                    if(!viewModel.currentRecipe?.steps.isNullOrEmpty())
                        RecipeText(
                            headline = stringResource(id = R.string.steps),
                            text = viewModel.currentRecipe?.steps.toString())
                }
            }
        }
    )

    if(viewModel.isDeleteRecipeDialogShown) {
        DeleteRecipeDialog(
            onApprove = {
                viewModel.hideDeleteDialog()
                viewModel.deleteRecipe()
                navController?.popBackStack()
            },
            onDismiss = {
                viewModel.hideDeleteDialog()
            }
        )
    }

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
        modifier = Modifier.padding(8.dp),
    )
}