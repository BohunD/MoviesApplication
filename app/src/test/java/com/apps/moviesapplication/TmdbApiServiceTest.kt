package com.apps.moviesapplication


import com.apps.moviesapplication.data.network.TmdbApiService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TmdbApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: TmdbApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val client = OkHttpClient.Builder().build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getTrendingMovies returns expected response`() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{
                "results": [{"id": 1, "title": "Movie 1", "poster_path": "/path1", "release_date": "2024-07-07"}],
                "page": 1,
                "total_pages": 1,
                "total_results": 1
            }""")

        mockWebServer.enqueue(mockResponse)

        runBlocking {
            val response = apiService.getTrendingMovies(1, "Bearer fake_token")
            assertEquals(1, response.results.size)
            assertEquals(1, response.results[0].id)
            assertEquals("Movie 1", response.results[0].title)
        }
    }
}