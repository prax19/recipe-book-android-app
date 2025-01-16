package pl.prax19.recipe_book_app.presentation.dialogs

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    onRemove: (RecipeIngredient) -> Unit,
    onSearch: (query: IngredientQuery) -> List<IngredientQuery>,
    isShown: Boolean
) {
    // TODO: improve UI, replace plain dialog with bottom sheet
    val rawQuery = remember { mutableStateOf("") }
    val query = remember(rawQuery.value) {
        // TODO: improve
        IngredientQuery.parse(rawQuery.value) ?: IngredientQuery(rawQuery.value, null, null)
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
                                        modifier = Modifier
                                            .fillMaxWidth(),
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
                                    BackHandler(showResults.value) {
                                        showResults.value = false
                                    }
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
                            LazyColumn(
                                contentPadding = it,
                                content = {
                                    // TODO: add deletion animation
                                    items(
                                        items = selected,
                                        key = { it.id }
                                    ) { ingredient ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp, horizontal = 16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = ingredient.ingredient.name
                                                )
                                                if(ingredient.amount != null)
                                                    Text(
                                                        text = "${ingredient.amount} ${ingredient.unit}",
                                                        style = MaterialTheme.typography.bodySmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                            }
                                            IconButton(
                                                onClick = {
                                                    onRemove(ingredient)
                                                },
                                                content = {
                                                    Icon(
                                                        Icons.Filled.Close,
                                                        "Remove ingredient"
                                                    )
                                                }
                                            )
                                        }
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