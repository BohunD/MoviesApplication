package com.apps.moviesapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.apps.moviesapplication.presentation.moviesListScreen.MOVIES_LIST_ROUTE
import com.apps.moviesapplication.presentation.moviesListScreen.moviesListScreen

@Composable
fun MoviesAppNavigation(
    navController: NavHostController,
    startDestination: String = MOVIES_LIST_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        moviesListScreen()
    }
}