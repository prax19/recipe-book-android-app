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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Link
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.prax19.recipe_book_app.presentation.dialogs.CustomDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsView(
    onRecipeRemoval: () -> Unit,
    onRecipeEdition: () -> Unit
) {

    val uriHandler = LocalUriHandler.current

    val viewModel: RecipeDetailsViewModel = hiltViewModel()

    val state by viewModel.state.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val isMenuExtended = remember { mutableStateOf(false) }
    val isDeletionDialogShown = remember { mutableStateOf(false) }

    val onOpenSource: () -> Unit = {
        state.recipe?.source?.let {
            try {
                val url =
                    if (it.startsWith("http://") || it.startsWith("https://")) it else "https://$it"
                uriHandler.openUri(url)
            } catch (e: IllegalArgumentException) {
                // TODO: handle uri error
                e.printStackTrace()
            }
        }
    }

    if(isDeletionDialogShown.value)
        CustomDialog(
            dismissText = "Cancel",
            acceptText = "Delete",
            onDismiss = { isDeletionDialogShown.value = false },
            onAccept = {
                state.recipe?.let {
                    onRecipeRemoval()
                    viewModel.removeRecipe()
                }
            },
            dialogTitle = "Delete recipe",
            dialogText = "Are you sure you want to remove this recipe from your cookbook? This action is irreversible.",
            icon = Icons.Filled.DeleteForever,
            actionColor = MaterialTheme.colorScheme.error
        )

    // TODO: improve UI
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    // TODO: handle no recipe state
                    Text(state.recipe?.name ?: "")
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
                                text = { Text("Edit recipe") },
                                leadingIcon = { Icon(Icons.Filled.Edit, "Edit recipe")  },
                                onClick = {
                                    isMenuExtended.value = false
                                    state.recipe?.let {
                                        onRecipeEdition()
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete recipe") },
                                leadingIcon = { Icon(Icons.Filled.DeleteForever, "Delete recipe")  },
                                onClick = {
                                    isDeletionDialogShown.value = true
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Open source") },
                                leadingIcon = { Icon(Icons.Filled.Link, "Open source")  },
                                onClick = {
                                    onOpenSource()
                                },
                                enabled = state.recipe?.source != null
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
                    state.recipe?.description?.let {
                        item {
                            Text(
                                modifier = Modifier.animateItem(),
                                text = state.recipe?.description ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.padding(16.dp))
                        }
                    }
                    items(
                        items = state.ingredients,
                        key = { ingredient -> ingredient.id }
                    ) { ingredient ->
                        Row (
                            modifier = Modifier.animateItem()
                        ){
                            if (ingredient.amount != null)
                                Text(
                                    text = "${ingredient.amount.toInt()} ${ingredient.unit} ",
                                    color = MaterialTheme.colorScheme.secondary
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
                            modifier = Modifier.animateItem(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${it.stepIndex + 1}. ",
                                color = MaterialTheme.colorScheme.secondary
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