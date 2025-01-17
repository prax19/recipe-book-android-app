package pl.prax19.recipe_book_app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NoMeals
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.UUID

// TODO: improve UI
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView(
    onEnterRecipe: (recipeId: UUID) -> Unit,
    onAddRecipe: () -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold (
        topBar = {
            MediumTopAppBar(
                title = { Text("Recipe Book") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddRecipe()
                },
                content = {
                    Icon(
                        Icons.Filled.Add,
                        "Add recipe"
                    )
                }
            )
        },
        content = {
            when(state.recipes.isNotEmpty()) {
                true -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize().padding(it)
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            items(
                                items = state.recipes,
                                key = { it.id }
                            ) { recipe ->
                                Card (
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.elevatedCardColors(),
                                    onClick = {
                                        onEnterRecipe(recipe.id)
                                    },
                                    content = {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp),
                                            content = {
                                                Text(
                                                    text = recipe.name,
                                                )
                                                recipe.description?.let {
                                                    Text(
                                                        text = it,
                                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.secondary
                                                    )
                                                }
                                            }
                                        )
                                    }
                                )
                            }
                        }
                    )
                }
                false -> {
                    //TODO: fix flicking bug when list is loading
                    //TODO: fix no top app bar collapsing after deletion
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Filled.NoMeals,
                            "No recipes",
                            modifier = Modifier.padding(bottom = 8.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "No recipes yet",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    )
}