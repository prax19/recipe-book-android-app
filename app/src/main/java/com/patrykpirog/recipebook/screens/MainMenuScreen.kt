package com.patrykpirog.recipebook.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    navController: NavHostController
){
    val bottomController = rememberNavController()

    val recipes = remember { loadRecipes() }

    val fabVisibility = remember { mutableStateOf(true) }
    val topAppBarState = rememberTopAppBarState()
    val topAppBarTextState = remember { mutableStateOf( BottomBarScreen.Recipes.title ) }
    val scrollBehavior =
        enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainTopBar(
                scrollBehavior = scrollBehavior,
                textState = topAppBarTextState
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                BottomNavGraph(
                    navController = bottomController,
                    mainNavController = navController,
                    recipes
                )
            }
        },
        floatingActionButton = {
           MainFab(
               mainNavController = navController,
               fabVisibility = fabVisibility.value)
        },
        bottomBar = {
            MainBottomBar(
                navController = bottomController,
                fabVisability = fabVisibility,
                topAppBarTextState = topAppBarTextState,
                topAppBarState = topAppBarState
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    textState: MutableState<String>
) {
    CenterAlignedTopAppBar(
        title = {
            Text(textState.value)
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainFab(
    mainNavController: NavController,
    fabVisibility: Boolean
){
    AnimatedVisibility(
        visible = fabVisibility,
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomBar(
    navController: NavHostController,
    topAppBarState: TopAppBarState,
    topAppBarTextState: MutableState<String>,
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
                fabVisability = fabVisability,
                topAppBarState = topAppBarState,
                topAppBarTextState = topAppBarTextState
                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    fabVisability: MutableState<Boolean>,
    topAppBarState: TopAppBarState,
    topAppBarTextState: MutableState<String>
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
            when(screen.route) {
                BottomBarScreen.Recipes.route -> {
                    fabVisability.value = true
                    topAppBarTextState.value = BottomBarScreen.Recipes.title
                }
                BottomBarScreen.Favorites.route -> {
                    fabVisability.value = false
                    topAppBarTextState.value = BottomBarScreen.Favorites.title
                }
                BottomBarScreen.Settings.route -> {
                    fabVisability.value = false
                    topAppBarTextState.value = BottomBarScreen.Settings.title
                }
            }
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}