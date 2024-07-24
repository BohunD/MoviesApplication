package com.apps.moviesapplication.data.network

import com.apps.moviesapplication.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Header("Authorization") authHeader: String = "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}"
    ): TrendingMoviesResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Header("Authorization") authHeader: String = "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}"
    ): MovieFull

    data class TrendingMoviesResponse(
        val results: List<MovieDemo>,
        val page: Int,
        val total_pages: Int,
        val total_results: Int
    )

    data class MovieDemo(
        val id: Int,
        val title: String,
        val poster_path: String,
        val release_date: String
    )

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
}
