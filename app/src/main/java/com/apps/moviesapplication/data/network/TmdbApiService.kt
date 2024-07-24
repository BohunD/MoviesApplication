package com.apps.moviesapplication.data.network

import com.apps.moviesapplication.BuildConfig
import com.apps.moviesapplication.data.models.MovieFull
import com.apps.moviesapplication.data.models.TrendingMoviesResponse
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

}
