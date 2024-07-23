package com.apps.moviesapplication.presentation.moviesListScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val MOVIES_LIST_ROUTE = "movies_list"

fun NavController.navigateToMoviesList() = navigate(MOVIES_LIST_ROUTE)

fun NavGraphBuilder.moviesListScreen() {
    composable(
        route = MOVIES_LIST_ROUTE,
    ) {
        MoviesListRoute()
    }
}