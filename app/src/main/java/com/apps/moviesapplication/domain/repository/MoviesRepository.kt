package com.apps.moviesapplication.domain.repository

import androidx.paging.PagingData
import com.apps.moviesapplication.data.network.TmdbApiService
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getTrendingMovies(): Flow<PagingData<TmdbApiService.MovieDemo>>
}