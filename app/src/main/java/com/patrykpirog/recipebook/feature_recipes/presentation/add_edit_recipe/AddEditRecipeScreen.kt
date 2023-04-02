package com.patrykpirog.recipebook.feature_recipes.presentation.add_edit_recipe

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.patrykpirog.recipebook.feature_recipes.domain.model.Recipe
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun AddRecipeScreen(
    viewModel: AddEditRecipeViewModel = hiltViewModel(),
    navController: NavController
){
    val scope = rememberCoroutineScope()

    var recipeName by rememberSaveable { mutableStateOf("") }
    var recipeNameWrong by rememberSaveable { mutableStateOf(false) }
    val recipeNameFocusRequester = remember { FocusRequester() }
    val recipeNameBringIntoViewRequester = remember { BringIntoViewRequester() }

    var description by rememberSaveable { mutableStateOf("") }
    val descriptionFocusRequester = remember { FocusRequester() }
    val descriptionBringIntoViewRequester = remember { BringIntoViewRequester() }

    var ingredients by rememberSaveable { mutableStateOf("") }
    val ingredientsFocusRequester = remember { FocusRequester() }
    val ingredientsBringIntoViewRequester = remember { BringIntoViewRequester() }

    var steps by rememberSaveable { mutableStateOf("") }
    val stepsFocusRequester = remember { FocusRequester() }
    val stepsBringIntoViewRequester = remember { BringIntoViewRequester() }

//    val scrollBehavior =
//        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
//        modifier = Modifier
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 CenterAlignedTopAppBar(
                     title = {
                         Text("Add new recipe")
                     },
//                     scrollBehavior = scrollBehavior
                 )
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .padding(
                        16.dp,
                        it.calculateTopPadding(),
                        16.dp,
                        it.calculateBottomPadding()
                    )
                    .verticalScroll(rememberScrollState())
            ){
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(recipeNameFocusRequester)
                        .bringIntoViewRequester(recipeNameBringIntoViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) scope.launch { recipeNameBringIntoViewRequester.bringIntoView() }
                        },
                    label = {
                        Text(text = "Recipe name")
                    },
                    singleLine = true,
                    value = recipeName,
                    onValueChange = {
                        recipeName = it
                        recipeNameWrong = false
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if(recipeName == "")
                                recipeNameWrong = true
                            else
                                descriptionFocusRequester.requestFocus()
                        }
                    ),
                    isError = recipeNameWrong
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(descriptionFocusRequester)
                        .bringIntoViewRequester(descriptionBringIntoViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) scope.launch { descriptionBringIntoViewRequester.bringIntoView() }
                        },
                    label = {
                        Text(text = "Description (optional)")
                    },
                    singleLine = false,
                    minLines = 3,
                    maxLines = 3,
                    value = description,
                    onValueChange = {
                        description = it
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(ingredientsFocusRequester)
                        .bringIntoViewRequester(ingredientsBringIntoViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) scope.launch { ingredientsBringIntoViewRequester.bringIntoView() }
                        },
                    label = {
                        Text(text = "Ingredients")
                    },
                    singleLine = false,
                    minLines = 3,
                    value = ingredients,
                    onValueChange = {
                        ingredients = it
                    }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(stepsFocusRequester)
                        .bringIntoViewRequester(stepsBringIntoViewRequester)
                        .onFocusEvent {
                            if (it.isFocused) scope.launch { stepsBringIntoViewRequester.bringIntoView() }
                        },
                    label = {
                        Text(text = "Steps")
                    },
                    singleLine = false,
                    minLines = 3,
                    value = steps,
                    onValueChange = {
                        steps = it
                    }
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp),
                    onClick = {
                        if(recipeName == ""){
                            recipeNameWrong = true
                            recipeNameFocusRequester.requestFocus()
                        } else {
                            val recipe = Recipe(
                                name = recipeName,
                                description = description,
                                ingredients = ingredients,
                                steps = steps
                            )
                            viewModel.addRecipe(recipe)
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Add recipe")
                }
            }
        }
    )
}
