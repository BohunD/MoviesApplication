package com.apps.moviesapplication.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apps.moviesapplication.data.network.TmdbApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val apiService: TmdbApiService
) : PagingSource<Int, TmdbApiService.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbApiService.Movie> {
        val position = params.key ?: 1

        return try {
            val response = apiService.getTrendingMovies(position)
            val movies = response.results
            if (position > 1) {
                delay(2000)
            }

            LoadResult.Page(
                data = movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TmdbApiService.Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
