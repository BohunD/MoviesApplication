package com.apps.moviesapplication.data.models

import com.google.gson.annotations.SerializedName

data class MovieDemo(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = ""
)

