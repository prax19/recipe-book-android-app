package com.patrykpirog.recipebook.screens.main_menu

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.patrykpirog.recipebook.feature_recipes.presentation.MainMenuViewModel
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.BottomBarScreen
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.BottomNavGraph
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.MainScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    mainViewModel: MainMenuViewModel = viewModel(),
    navController: NavHostController
){
    val bottomController = rememberNavController()
    mainViewModel.recipes = remember{ mainViewModel.loadRecipes() }

//    val scrollBehavior =
//        enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
//        modifier = Modifier
//            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MainTopBar(
                navController = navController,
//                scrollBehavior = scrollBehavior
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
                    mainNavController = navController
                )
            }
        },
        floatingActionButton = {
            MainFab(
                mainNavController = navController
            )
        },
        bottomBar = {
            MainBottomBar(
                navController = bottomController
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    mainViewModel: MainMenuViewModel = viewModel(),
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) {
    CenterAlignedTopAppBar(
        title = {
            Text(mainViewModel.mainTopAppBarText)
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
    mainViewModel: MainMenuViewModel = viewModel(),
    mainNavController: NavController
){
    AnimatedVisibility(
        visible = mainViewModel.mainFabVisibility,
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

@Composable
fun MainBottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomBarScreen.Recipes,
        BottomBarScreen.Favorites
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    mainViewModel: MainMenuViewModel = viewModel(),
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
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
            mainViewModel.changeBottomScreen(screen.route)
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}