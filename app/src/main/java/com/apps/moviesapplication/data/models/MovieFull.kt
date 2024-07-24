package com.apps.moviesapplication.data.models

data class MovieFull(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float,
    val tagline: String,
    val revenue: Long,
    val budget: Int,
    val runtime: Int,
)