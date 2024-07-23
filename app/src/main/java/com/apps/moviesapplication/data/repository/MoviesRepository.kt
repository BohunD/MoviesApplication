package com.apps.moviesapplication.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.apps.moviesapplication.data.network.TmdbApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiService: TmdbApiService
) {
    fun getTrendingMovies(): Flow<PagingData<TmdbApiService.Movie>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(apiService) }
        ).flow
        return pager
    }
}