package com.apps.moviesapplication.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apps.moviesapplication.data.models.MovieDemo
import com.apps.moviesapplication.data.network.TmdbApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

const val PAGE_LOAD_DELAY_MS = 1000L
class MoviesPagingSource(
    private val apiService: TmdbApiService
) : PagingSource<Int, MovieDemo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDemo> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getTrendingMovies(page)
            val movies = response.results
            if (page > 1) {
                delay(PAGE_LOAD_DELAY_MS)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDemo>): Int? {
        return state.anchorPosition
    }
}
