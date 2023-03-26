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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
//    val scrollBehavior =
//        enterAlwaysScrollBehavior(topAppBarState)

    Scaffold(
//        modifier = Modifier
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainTopBar(
                navController = navController,
//                scrollBehavior = scrollBehavior,
                textState = topAppBarTextState
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        16.dp,
                        it.calculateTopPadding(),
                        16.dp,
                        it.calculateBottomPadding()
                    )
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
               fabVisibility = fabVisibility
           )
        },
        bottomBar = {
            MainBottomBar(
                navController = bottomController,
                topAppBarTextState = topAppBarTextState,
                topAppBarState = topAppBarState,
                fabVisibility = fabVisibility
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.pinnedScrollBehavior(),
    textState: MutableState<String>
) {
    CenterAlignedTopAppBar(
        title = {
            Text(textState.value)
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(MainScreen.SettingsScreen.route)
                },
                content = {
                    Icon(Icons.Default.Settings, "App settings")
                }
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainFab(
    mainNavController: NavController,
    fabVisibility: MutableState<Boolean>
){
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomBar(
    navController: NavHostController,
    topAppBarState: TopAppBarState,
    topAppBarTextState: MutableState<String>,
    fabVisibility: MutableState<Boolean>
) {
    val screens = listOf(
        BottomBarScreen.Recipes,
        BottomBarScreen.Favorites
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar() {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,
                topAppBarState = topAppBarState,
                topAppBarTextState = topAppBarTextState,
                fabVisibility = fabVisibility
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
    topAppBarState: TopAppBarState,
    topAppBarTextState: MutableState<String>,
    fabVisibility: MutableState<Boolean>
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
                    topAppBarTextState.value = BottomBarScreen.Recipes.title
                }
                BottomBarScreen.Favorites.route -> {
                    topAppBarTextState.value = BottomBarScreen.Favorites.title
                }
            }
            fabVisibility.value = currentDestination?.route != BottomBarScreen.Recipes.route
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}