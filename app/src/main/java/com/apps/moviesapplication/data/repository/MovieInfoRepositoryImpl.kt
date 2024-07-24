package com.apps.moviesapplication.data.repository

import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.domain.repository.MovieInfoRepository
import javax.inject.Inject

class MovieInfoRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieInfoRepository {
    override suspend fun getMovieDetails(id: Int): TmdbApiService.MovieFull {
        return apiService.getMovieDetails(id)
    }
}