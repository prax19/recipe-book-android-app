package com.patrykpirog.recipebook.feature_recipes.presentation.recipe_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patrykpirog.recipebook.R
import com.patrykpirog.recipebook.feature_recipes.presentation.recipe_detail.RecipeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteRecipeDialog(
    viewModel: RecipeDetailViewModel = hiltViewModel(),
    onApprove: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss
    ) {
        ElevatedCard{
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        Icons.Outlined.Delete,
                        null,
                        tint = MaterialTheme.colorScheme.error
                        )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.delete_recipe_dialog_title),
                        style = typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.delete_recipe_dialog_text, viewModel.currentRecipe?.name.toString()),
                        style = typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = {
                            viewModel.hideDeleteDialog()
                        },
                        content = {
                            Text(stringResource(id = R.string.cancel))
                        }
                    )
                    TextButton(
                        onClick = onApprove,
                        content = {
                            Text(stringResource(id = R.string.delete))
                        },
                        colors = ButtonDefaults.textButtonColors( contentColor = MaterialTheme.colorScheme.error)
                    )
                }
            }
        }
    }

}