package pl.prax19.recipe_book_app.presentation.dialogs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pl.prax19.recipe_book_app.data.model.Ingredient

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun IngredientSearchDialog(
    onDismissRequest: () -> Unit,
    onAccept: (List<Ingredient>) -> Unit,
    onSearch: (query: String) -> List<Ingredient>,
    onNewIngredient: (ingredient: Ingredient) -> Unit,
    isShown: Boolean
) {
    // TODO: improve UI
    val query = remember { mutableStateOf("") }
    val showResults = remember { mutableStateOf(false) }

    val selected = remember { mutableStateOf(emptyList<Ingredient>()) }

    when (isShown) {
        true -> {
            Dialog(
                properties = DialogProperties(
                    decorFitsSystemWindows = false,
                    usePlatformDefaultWidth = false
                ),
                onDismissRequest = { onDismissRequest() },
                content = {
                    Scaffold(
                        topBar = {
                            SearchBar(
                                inputField = {
                                    SearchBarDefaults.InputField(
                                        query = query.value,
                                        onQueryChange = {
                                            query.value = it
                                            if(it.isNotBlank())
                                                showResults.value = true
                                        },
                                        onSearch = {},
                                        expanded = showResults.value,
                                        onExpandedChange = {

                                        },
                                        placeholder = {
                                            Text("Search ingredient")
                                        },
                                        leadingIcon = {
                                            Icon(
                                                Icons.Filled.Search,
                                                "Search ingredient"
                                            )
                                        },
                                        trailingIcon = {
                                            if(showResults.value)
                                                IconButton(
                                                    content = {
                                                        Icon(
                                                            Icons.Filled.Close,
                                                            "Exit search"
                                                        )
                                                    },
                                                    onClick = {
                                                        showResults.value = false
                                                    }
                                                )
                                        }
                                    )
                                },
                                expanded = showResults.value,
                                onExpandedChange = {

                                },
                                content = {
                                    LazyColumn(
                                        contentPadding = PaddingValues(vertical =  16.dp),
                                        verticalArrangement = Arrangement.spacedBy(0.dp)
                                    ) {
                                        items(
                                            items = onSearch(query.value)
                                        ) { item ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(item.name)
                                               },
                                                onClick = {
                                                    // TODO: add support for units
                                                    val newIngredient = Ingredient(name = item.name)
                                                    // Include line below to filter out multiplied ingredients
                                                    // if(selected.value.none {it.name == item.name})
                                                        selected.value += newIngredient
                                                    onNewIngredient(newIngredient)
                                                    showResults.value = false
                                                    query.value = ""
                                                },
                                                leadingIcon = {
                                                    if(selected.value.any { it.name == item.name })
                                                        Icon(
                                                            Icons.Filled.Check,
                                                            "Ingredient added"
                                                        )
                                                    else
                                                        Icon(
                                                            Icons.Filled.Add,
                                                            "Add ingredient"
                                                        )
                                                },
                                                contentPadding = PaddingValues(12.dp)
                                            )
                                        }
                                    }
                                }
                            )
                        },
                        content = {
                            // TODO: add deletion functionality
                            // TODO: add oder modification functionality
                            LazyColumn(
                                contentPadding = it,
                                content = {
                                    items(
                                        items = selected.value
                                    ) { ingredient ->
                                        Text(
                                            modifier = Modifier.padding(16.dp),
                                            text = ingredient.name
                                        )
                                    }
                                    item {
                                        TextButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            onClick = {
                                                onAccept(selected.value)
                                            },
                                            content = {
                                                Text("Accept")
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
        false -> { }
    }

}