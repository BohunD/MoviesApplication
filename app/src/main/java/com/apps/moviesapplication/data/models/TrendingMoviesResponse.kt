package com.apps.moviesapplication.data.models

data class TrendingMoviesResponse(
    val results: List<MovieDemo>,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)