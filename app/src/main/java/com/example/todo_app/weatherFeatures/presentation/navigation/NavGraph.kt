package com.example.todo_app.weatherFeatures.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.weatherFeatures.presentation.home.HomeScreen
import com.example.todo_app.weatherFeatures.presentation.home.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel

object MainDestinations {
    const val HOME_ROUTE = "home"
}
@Composable
fun NavGraph(startDestination: String = MainDestinations.HOME_ROUTE) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.HOME_ROUTE) { backStackEntry ->
            val viewModel:HomeViewModel = hiltViewModel()
            HomeScreen(viewModel)
        }
    }
}
