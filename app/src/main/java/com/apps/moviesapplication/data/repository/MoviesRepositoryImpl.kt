package com.apps.moviesapplication.data.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.data.paging.MoviesPagingSource
import com.apps.moviesapplication.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MoviesRepository {
    override fun getTrendingMovies(): Flow<PagingData<TmdbApiService.MovieDemo>> {
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
