package com.apps.moviesapplication.domain.repository

import com.apps.moviesapplication.data.network.TmdbApiService

interface MovieInfoRepository {
    suspend fun getMovieDetails(id: Int): TmdbApiService.MovieFull
}