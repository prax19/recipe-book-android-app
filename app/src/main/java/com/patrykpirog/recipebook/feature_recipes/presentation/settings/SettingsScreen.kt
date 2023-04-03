package com.patrykpirog.recipebook.feature_recipes.presentation.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.patrykpirog.recipebook.BuildConfig
import com.patrykpirog.recipebook.R
import com.patrykpirog.recipebook.feature_recipes.presentation.navigation.MainScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(id = R.string.settings))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ){
                    SettingsInfoElement(
                        stringResource(id = R.string.version),
                        BuildConfig.BUILD_TYPE + " " + BuildConfig.VERSION_NAME
                    )
                    OutlinedButton(
                        modifier = Modifier
                            .width(128.dp),
                        content = {
                            Text(stringResource(id = R.string.sign_out))
                        },
                        onClick = {
                            viewModel.signOut()
                            navController.navigate(MainScreen.LoginScreen.route) {
                                popUpTo(navController.graph.id){
                                    inclusive = true
                                }
                                //navController.popBackStack()
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun SettingsElement() {

}

@Composable
fun SettingsInfoElement(
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Start
        )
        Text(
            text = description,
            textAlign = TextAlign.End
        )
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    SettingsScreen(
        rememberNavController()
    )
}

@Composable
@Preview
fun SettingsElementPreview() {
    SettingsElement()
}

@Composable
@Preview
fun SettingsInfoElementPreview() {
    SettingsInfoElement(
        "Version",
        BuildConfig.BUILD_TYPE + " " + BuildConfig.VERSION_NAME
    )
}