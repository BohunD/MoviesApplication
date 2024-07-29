package com.apps.moviesapplication.data.models

import com.google.gson.annotations.SerializedName

data class MovieFull(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("vote_average")
    val voteAverage: Float = 0f,
    @SerializedName("tagline")
    val tagline: String = "",
    @SerializedName("revenue")
    val revenue: Long = 0,
    @SerializedName("budget")
    val budget: Int = 0,
    @SerializedName("runtime")
    val runtime: Int = 0,
)