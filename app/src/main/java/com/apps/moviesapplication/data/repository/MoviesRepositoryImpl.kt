package com.apps.moviesapplication.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apps.moviesapplication.data.models.MovieDemo
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.data.paging.MoviesPagingSource
import com.apps.moviesapplication.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MoviesRepository {
    override fun getTrendingMovies(): Flow<PagingData<MovieDemo>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(apiService) }
        ).flow
        return pager
    }
}
