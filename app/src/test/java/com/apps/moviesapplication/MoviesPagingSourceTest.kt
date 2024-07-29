package com.apps.moviesapplication

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apps.moviesapplication.data.models.MovieDemo
import com.apps.moviesapplication.data.models.TrendingMoviesResponse
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.data.paging.MoviesPagingSource
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesPagingSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: TmdbApiService
    private lateinit var pagingSource: MoviesPagingSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)

        pagingSource = MoviesPagingSource(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `load returns correct data when successful`() = runBlocking {
        val movieList = listOf(MovieDemo(id = 1, title = "Movie 1", posterPath = "/path1", releaseDate = "2022-02-22"))
        val response = TrendingMoviesResponse(results = movieList, totalPages = 1,page = 1, totalResults = 1)
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(Gson().toJson(response))
        )

        val result = pagingSource.load(PagingSource.LoadParams.Refresh(1, 1, false))

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(movieList, page.data)
        assertEquals(null, page.prevKey)
        assertEquals(null, page.nextKey)
    }

    @Test
    fun `load returns error when IOException`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        val result = pagingSource.load(PagingSource.LoadParams.Refresh(1, 1, false))

        assertTrue(result is PagingSource.LoadResult.Error)
        val error = result as PagingSource.LoadResult.Error
        assertTrue(error.throwable is Exception)
    }

    @Test
    fun `load returns error when HttpException`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
        )

        val result = pagingSource.load(PagingSource.LoadParams.Refresh(1, 1, false))

        assertTrue(result is PagingSource.LoadResult.Error)
        val error = result as PagingSource.LoadResult.Error
        assertTrue(error.throwable is HttpException)
    }

    @Test
    fun `getRefreshKey returns correct key`() {
        val pagingState = PagingState<Int, MovieDemo>(
            pages = listOf(),
            anchorPosition = 1,
            config = PagingConfig(pageSize = 1),
            leadingPlaceholderCount = 0
        )

        val result = pagingSource.getRefreshKey(pagingState)

        assertEquals(1, result)
    }
}
