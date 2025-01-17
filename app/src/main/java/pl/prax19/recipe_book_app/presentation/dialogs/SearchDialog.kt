package pl.prax19.recipe_book_app.presentation.dialogs

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
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

    //TODO: fix nested scrolling issue

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val lazyListState = rememberLazyListState()
    val nestedScrollConnection = rememberNestedScrollInteropConnection()

    val rawQuery = remember { mutableStateOf("") }
    val query = remember(rawQuery.value) {
        // TODO: improve
        IngredientQuery.parse(rawQuery.value)
    }
    val showResults = remember { mutableStateOf(false) }

    when (isShown) {
        true -> {
            ModalBottomSheet(
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection),
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if(!sheetState.isVisible) {
                            onClose()
                            showResults.value = false
                        }
                    }
                },
                content = {
                    SearchBar(
                        shape = SearchBarDefaults.dockedShape,
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
                                modifier = Modifier,
                                contentPadding = PaddingValues(vertical =  16.dp)
                            ) {
                                val items = onSearch(query)
                                items(
                                    items = items,
                                    key = { item -> item.product }
                                ) { item ->
                                    DropdownMenuItem(
                                        modifier = Modifier.animateItem(),
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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = lazyListState,
                        content = {
                            items(
                                items = selected,
                                key = { it.id }
                            ) { ingredient ->
                                Box (
                                    modifier = Modifier.animateItem()
                                ) {
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
                            }
                        }
                    )
                }
            )
        }
        false -> { }
    }

}