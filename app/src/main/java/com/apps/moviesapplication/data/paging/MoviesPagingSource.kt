package com.apps.moviesapplication.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apps.moviesapplication.data.network.TmdbApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val apiService: TmdbApiService
) : PagingSource<Int, TmdbApiService.MovieDemo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbApiService.MovieDemo> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getTrendingMovies(page)
            val movies = response.results
            if (page > 1) {
                delay(1000)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.total_pages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TmdbApiService.MovieDemo>): Int? {
        return state.anchorPosition
    }
}
