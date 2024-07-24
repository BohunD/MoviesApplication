package com.apps.moviesapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.apps.moviesapplication.presentation.movieInfoScreen.movieInfoScreen
import com.apps.moviesapplication.presentation.movieInfoScreen.navigateToMovieInfo
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
        moviesListScreen(
            onMovieClicked = {navController.navigateToMovieInfo(it)}
        )
        movieInfoScreen(onBackPressed = {navController.popBackStack()})
    }
}