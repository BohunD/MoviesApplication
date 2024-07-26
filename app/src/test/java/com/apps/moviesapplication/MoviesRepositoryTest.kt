package com.apps.moviesapplication

import androidx.paging.testing.asSnapshot
import com.apps.moviesapplication.data.models.MovieDemo
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.data.repository.MoviesRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MoviesRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: TmdbApiService
    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TmdbApiService::class.java)

        repository = MoviesRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getTrendingMovies returns expected data`() = runTest {
        val movie = MovieDemo(1, "Movie 1", "/path1", "2024-07-07")
        val mockResponse = MockResponse()
            .setBody("""
            {
                "page": 1,
                "results": [
                    {
                        "id": 1,
                        "title": "Movie 1",
                        "poster_path": "/path1",
                        "release_date": "2024-07-07"
                    }
                ],
                "total_pages": 1,
                "total_results": 1
            }
            """)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val result = repository.getTrendingMovies()
        val items = result.asSnapshot().map { it }
        assertEquals(1, items.size)
        assertEquals(movie, items[0])
    }
}
