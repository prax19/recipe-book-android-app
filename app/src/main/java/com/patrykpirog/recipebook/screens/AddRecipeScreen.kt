package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.di.AppModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalTextApi::class
)
@Composable
fun AddRecipeScreen(
    navController: NavController
){
    var db = Firebase.firestore
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

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var scrollState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
                 MediumTopAppBar(
                     title = {
                         Text("Add new recipe")
                     },
                     scrollBehavior = scrollBehavior
                 )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 84.dp),
                state = scrollState
            ){
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(4.dp)
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
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(4.dp)
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
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .focusRequester(ingredientsFocusRequester)
                            .bringIntoViewRequester(ingredientsBringIntoViewRequester)
                            .onFocusEvent {
                                if (it.isFocused) scope.launch {ingredientsBringIntoViewRequester.bringIntoView() }
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
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(4.dp)
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
                }
                item {
                    ExtendedFloatingActionButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 16.dp),
                        onClick = {
                            if(recipeName == ""){
                                recipeNameWrong = true
                                recipeNameFocusRequester.requestFocus()
                            } else {
                                val recipe = hashMapOf(
                                    "name" to recipeName,
                                    "description" to description,
                                    "ingredients" to ingredients,
                                    "steps" to steps
                                )
                                db.collection("users")
                                    .document(AppModule.providesFirebaseAuth().uid.toString()).collection("recipes")
                                    .add(recipe)
                                navController.popBackStack()
                            }

                        }
                    ) {
                        Text("Add recipe")
                    }
                }
            }
        },
    )

}