package pl.prax19.recipe_book_app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.prax19.recipe_book_app.presentation.dialogs.IngredientSearchDialog

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeWizardView(
    onExit: () -> Unit
) {

    val viewModel: RecipeWizardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    val nameTextFieldFocusRequester = remember { FocusRequester() }
    val descriptionTextFieldFocusRequester = remember { FocusRequester() }
    val ingredientsFocusRequester = remember { FocusRequester() }
    val stepsFocusRequester = remember { FocusRequester() }
    val nextButtonFocusRequester = remember { FocusRequester() }

    val showIngredientSearchDialog = remember { mutableStateOf(false) }
    val showStepsSearchDialog = remember { mutableStateOf(false) }

    IngredientSearchDialog(
        onDismissRequest = {
            showIngredientSearchDialog.value = false
       },
        onAccept = {
            viewModel.updateIngredients(it)
            showIngredientSearchDialog.value = false
        },
        onSearch = { query ->
            viewModel.getIngredientSearchQueryResponse(query)
        },
        onNewIngredient = { newIngredient ->
            viewModel.addIngredient(newIngredient)
        },
        isShown = showIngredientSearchDialog.value
    )

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Dish recipe") },
                actions = {
                    TextButton(
                        modifier = Modifier
                            .focusRequester(nextButtonFocusRequester),
                        onClick = {
                            // TODO: add serializable data auto fetching
                            // TODO: add automatic removal of unused ingredients
                            viewModel.saveRecipe()
                            onExit()
                        },
                        content = {
                            Text("Save")
                        },
                        enabled = state.name.isNotEmpty()
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onExit()
                        },
                        content = {
                            Icon(
                                Icons.Filled.Close,
                                "Close"
                            )
                        }
                    )
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = it,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // TODO: add a photo section
                // TODO: add field focusing
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(nameTextFieldFocusRequester),
                        label = {
                            Text("Recipe name")
                        },
                        value = state.name,
                        onValueChange = { name ->
                            viewModel.updateName(name)
                        },
                        placeholder = {
                            Text("Tori Karaage")
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                descriptionTextFieldFocusRequester.requestFocus()
                            }
                        ),
                        singleLine = true,
                        isError = state.name.isEmpty()
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(descriptionTextFieldFocusRequester),
                        label = {
                            Text("Recipe short description (optional)")
                        },
                        value = state.description ?: "",
                        onValueChange = { description ->
                            viewModel.updateDescription(description)
                        },
                        maxLines = 4
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(ingredientsFocusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    showIngredientSearchDialog.value = true
                                    ingredientsFocusRequester.freeFocus()
                                }
                            },
                        label = {
                            Text("Ingredients")
                        },
                        value = state.ingredients.joinToString(separator = "\n") { it.name },
                        onValueChange = {},
                        placeholder = {
                            Text("No ingredients")
                        },
                        readOnly = true
                    )
                }
                item {
                    // TODO: add steps
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(stepsFocusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    showStepsSearchDialog.value = true
                                    ingredientsFocusRequester.freeFocus()
                                }
                            },
                        label = {
                            Text("Steps")
                        },
                        value = "",
                        onValueChange = {},
                        placeholder = {
                            Text("No steps")
                        },
                        readOnly = true,
                        enabled = false
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize(),
                        label = {
                            Text("Source (optional)")
                        },
                        value = state.source ?: "",
                        onValueChange = { source ->
                            viewModel.updateSource(source)
                        },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Link,
                                "Source"
                            )
                        }
                    )
                }
            }
        }
    )

}