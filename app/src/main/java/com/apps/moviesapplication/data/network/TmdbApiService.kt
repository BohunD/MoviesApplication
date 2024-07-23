package com.apps.moviesapplication.data.network



import com.apps.moviesapplication.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TmdbApiService {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Header("Authorization") authHeader: String = "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}"
    ): TrendingMoviesResponse

    data class TrendingMoviesResponse(
        val results: List<Movie>,
        val page: Int,
        val totalPages: Int,
        val totalResults: Int
    )

    data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val posterPath: String,
        val releaseDate: String
    )
}

