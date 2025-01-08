package pl.prax19.recipe_book_app.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

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
    val nextButtonFocusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Basic recipe info") },
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
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = it,
                verticalArrangement = Arrangement.spacedBy(24.dp)
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
                        value = state.description,
                        onValueChange = { description ->
                            viewModel.updateDescription(description)
                        }
                    )
                }
            }
        }
    )

}