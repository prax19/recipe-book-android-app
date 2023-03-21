package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.patrykpirog.recipebook.navigation.BottomBarScreen
import com.patrykpirog.recipebook.navigation.BottomNavGraph
import com.patrykpirog.recipebook.navigation.MainScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainMenuScreen(
    mainNavController: NavController
){
    val bottomNavController = rememberNavController()
    val fabVisibility = remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            BottomNavGraph(navController = bottomNavController, mainNavController, it)
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = fabVisibility.value,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        mainNavController.navigate(MainScreen.AddRecipeScreen.route)
                    }
                ) {
                    Icon(Icons.Default.Add, "Add")
                }
            }
        },
        bottomBar = {
            BottomBar(
                navController = bottomNavController,
                fabVisability = fabVisibility
            )
        },
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    fabVisability: MutableState<Boolean>
) {
    val screens = listOf(
        BottomBarScreen.Recipes,
        BottomBarScreen.Favorites,
        BottomBarScreen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar() {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
                fabVisability = fabVisability)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    fabVisability: MutableState<Boolean>
) {
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            fabVisability.value = screen.route == BottomBarScreen.Recipes.route
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}