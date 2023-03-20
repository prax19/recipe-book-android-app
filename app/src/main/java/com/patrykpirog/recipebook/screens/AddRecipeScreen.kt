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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.patrykpirog.recipebook.di.AppModule
import java.time.Duration

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController
){
    var db = Firebase.firestore

    var recipeName by rememberSaveable { mutableStateOf("") }
    var recipeNameWrong by rememberSaveable { mutableStateOf(false) }
    val recipeNameFocusRequester = remember { FocusRequester() }

    var description by rememberSaveable { mutableStateOf("") }
    val descriptionFocusRequester = remember { FocusRequester() }

    var ingredients by rememberSaveable { mutableStateOf("") }
    //val ingredientsFocusRequester = remember { FocusRequester() }

    var steps by rememberSaveable { mutableStateOf("") }
    //val stepsFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
                 MediumTopAppBar(
                     title = {
                         Text("Add new recipe")
                     }
                 )
        },
        content = {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 84.dp)
            ){
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .focusRequester(recipeNameFocusRequester),
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
                            .focusRequester(descriptionFocusRequester),
                        label = {
                            Text(text = "Description (optional)")
                        },
                        singleLine = false,
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
                            .fillMaxWidth(),
                        label = {
                            Text(text = "Ingredients")
                        },
                        singleLine = false,
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
                            .fillMaxWidth(),
                        label = {
                            Text(text = "Steps")
                        },
                        singleLine = false,
                        value = steps,
                        onValueChange = {
                            steps = it
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp),
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
        },
        floatingActionButtonPosition = FabPosition.Center
    )

}