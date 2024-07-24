package com.apps.moviesapplication.domain.repository

import androidx.paging.PagingData
import com.apps.moviesapplication.data.models.MovieDemo
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getTrendingMovies(): Flow<PagingData<MovieDemo>>
}