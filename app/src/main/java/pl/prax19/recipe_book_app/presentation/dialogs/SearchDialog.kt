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
import pl.prax19.recipe_book_app.data.model.RecipeIngredient
import pl.prax19.recipe_book_app.utils.IngredientQuery

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun IngredientSearchDialog(
    selected: List<RecipeIngredient>,
    onClose: () -> Unit,
    onAdd: (IngredientQuery) -> Unit,
    onSearch: (query: IngredientQuery) -> List<IngredientQuery>,
    isShown: Boolean
) {
    // TODO: improve UI
    val rawQuery = remember { mutableStateOf("") }
    val query = remember(rawQuery.value) {
        IngredientQuery.parse(rawQuery.value)
    }
    val showResults = remember { mutableStateOf(false) }

    when (isShown) {
        true -> {
            Dialog(
                properties = DialogProperties(
                    decorFitsSystemWindows = false,
                    usePlatformDefaultWidth = false
                ),
                onDismissRequest = { onClose() },
                content = {
                    Scaffold(
                        topBar = {
                            SearchBar(
                                inputField = {
                                    SearchBarDefaults.InputField(
                                        query = rawQuery.value,
                                        onQueryChange = {
                                            rawQuery.value = it
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
                                            items = onSearch(query)
                                        ) { item ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text("${item.product} ${item.amount ?:""} ${item.unit ?: ""}")
                                               },
                                                onClick = {
                                                    onAdd(item)
                                                    showResults.value = false
                                                    rawQuery.value = ""
                                                },
                                                leadingIcon = {
                                                    if(selected.any { it.ingredient.name == item.product })
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
                                        items = selected
                                    ) { ingredient ->
                                        Text(
                                            modifier = Modifier.padding(16.dp),
                                            // TODO: add unit display
                                            text = ingredient.ingredient.name
                                        )
                                    }
                                    item {
                                        // TODO: remove this ugly button
                                        TextButton(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            onClick = {
                                                onClose()
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