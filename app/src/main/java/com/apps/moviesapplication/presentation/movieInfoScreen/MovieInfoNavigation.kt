package com.apps.moviesapplication.presentation.movieInfoScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val MOVIE_INFO_ROUTE = "movie_info/{id}"
const val ID = "id"

fun NavController.navigateToMovieInfo(id: Int) = navigate("movie_info/$id")

fun NavGraphBuilder.movieInfoScreen(onBackPressed: ()->Unit) {
    composable(
        route = MOVIE_INFO_ROUTE,
        arguments = listOf(navArgument(ID) { type = NavType.IntType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt(ID) ?: 0
        MovieInfoScreen(id = id, onBackPressed = onBackPressed)
    }
}
