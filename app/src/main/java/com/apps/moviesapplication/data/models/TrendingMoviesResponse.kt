package com.apps.moviesapplication.data.models

import com.google.gson.annotations.SerializedName

data class TrendingMoviesResponse(
    @SerializedName("results")
    val results: List<MovieDemo> = emptyList(),
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
)