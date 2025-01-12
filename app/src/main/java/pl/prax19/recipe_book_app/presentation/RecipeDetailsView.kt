package pl.prax19.recipe_book_app.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsView(
    onRecipeRemoval: () -> Unit
) {

    val viewModel: RecipeDetailsViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val isMenuExtended = remember { mutableStateOf(false) }

    // TODO: improve UI
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(state.recipe?.name ?: "Error")
                },
                actions = {
                    IconButton(
                        content = { Icon(Icons.Filled.MoreVert, "More button") },
                        onClick = { isMenuExtended.value = true }
                    )
                    DropdownMenu(
                        expanded = isMenuExtended.value,
                        onDismissRequest = { isMenuExtended.value = false },
                        content = {
                            DropdownMenuItem(
                                text = { Text("Delete") },
                                leadingIcon = { Icon(Icons.Filled.DeleteForever, "Delete recipe")  },
                                onClick = {
                                    // TODO: add dialog
                                    state.recipe?.let {
                                        onRecipeRemoval()
                                        isMenuExtended.value = false
                                        viewModel.removeRecipe(it)
                                    }
                                }
                            )
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = PaddingValues(16.dp),
                content = {
                    items(
                        items = state.ingredients,
                        key = { ingredient -> ingredient.id }
                    ) { ingredient ->
                        Row {
                            if (ingredient.amount != null)
                                Text(
                                    text = "${ingredient.amount.toInt()} ${ingredient.unit} ",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            Text(
                                text = "${ingredient.ingredient.name} "
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.padding(16.dp))
                    }
                    items(
                        items = state.steps,
                        key = { step -> step.id }
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${it.stepIndex + 1}. ",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(text = it.description)
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            )
        }
    )

}