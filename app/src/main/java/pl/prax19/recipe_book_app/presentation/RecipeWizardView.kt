package pl.prax19.recipe_book_app.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.prax19.recipe_book_app.presentation.dialogs.CustomDialog
import pl.prax19.recipe_book_app.presentation.dialogs.IngredientSearchDialog
import pl.prax19.recipe_book_app.presentation.dialogs.StepDialog

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

    var showIngredientSearchDialog by remember { mutableStateOf(false) }

    val showStepsDialog = remember { mutableStateOf(false) }
    val showExitDialog = remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    BackHandler {
        showExitDialog.value = true
    }

    if(showExitDialog.value)
        CustomDialog(
            dismissText = "Cancel",
            acceptText = "Exit without saving",
            onDismiss = { showExitDialog.value = false },
            onAccept = {
                onExit()
                viewModel.cancelRecipeSaving()
            },
            dialogTitle = "Unsaved recipe",
            dialogText = "Are you sure you want to exit recipe creator without saving?",
            icon = Icons.Outlined.Warning,
            actionColor = MaterialTheme.colorScheme.error
        )

    StepDialog(
        rawSteps = state.rawSteps,
        onValueChange = {
            viewModel.updateRawSteps(it)
        },
        onClose = {
            viewModel.updateSteps()
            showStepsDialog.value = false
        },
        isShown = showStepsDialog.value
    )

    IngredientSearchDialog(
        selected = state.ingredients,
        onClose = {
            showIngredientSearchDialog = false
        },
        onAdd = { ingredient ->
            viewModel.addIngredient(ingredient)
        },
        onRemove = { ingredient ->
            viewModel.removeIngredient(ingredient)
        },
        onSearch = { query ->
            viewModel.getIngredientSearchQueryResponse(query)
        },
        isShown = showIngredientSearchDialog
    )

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Dish recipe") },
                scrollBehavior = scrollBehavior,
                actions = {
                    TextButton(
                        modifier = Modifier
                            .focusRequester(nextButtonFocusRequester),
                        onClick = {
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
                            showExitDialog.value = true
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
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
                                    showIngredientSearchDialog = true
                                    ingredientsFocusRequester.freeFocus()
                                }
                            },
                        label = {
                            Text("Ingredients")
                        },
                        value = state.ingredients.joinToString(separator = "\n") { ing ->
                            if (ing.amount == null)
                                ing.ingredient.name
                            else
                                "${ing.amount} ${ing.unit} ${ing.ingredient.name}"
                         },
                        onValueChange = {},
                        readOnly = true,
                        enabled = !showIngredientSearchDialog
                    )
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(stepsFocusRequester)
                            .onFocusEvent {
                                if (it.isFocused) {
                                    showStepsDialog.value = true
                                    stepsFocusRequester.freeFocus()
                                }
                            },
                        label = {
                            Text("Steps")
                        },
                        value = state.steps.joinToString(separator = "\n\n") {
                            "${it.stepIndex + 1}. ${it.description}"
                        },
                        onValueChange = {},
                        readOnly = true,
                        maxLines = 5
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