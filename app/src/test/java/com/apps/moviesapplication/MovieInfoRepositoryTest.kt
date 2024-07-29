package com.apps.moviesapplication

import com.apps.moviesapplication.data.models.MovieFull
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.data.repository.MovieInfoRepositoryImpl
import com.apps.moviesapplication.domain.repository.MovieInfoRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito

class MovieInfoRepositoryTest {

    private val apiService = Mockito.mock(TmdbApiService::class.java)
    private val repository: MovieInfoRepository = MovieInfoRepositoryImpl(apiService)

    @Test
    fun testGetMovieDetails() = runBlocking {
        val movieId = 123
        val movieFull = MovieFull(
            id = movieId,
            title = "movie1",
            overview = "overview1",
            posterPath = "/path1",
            releaseDate = "2024-07-07",
            voteAverage = 1f,
            tagline = "Tagline",
            revenue = 1000000,
            budget = 500000,
            runtime = 120
        )

        Mockito.`when`(apiService.getMovieDetails(movieId)).thenReturn(movieFull)

        val result = repository.getMovieDetails(movieId)

        assertEquals(movieFull, result)
    }
}
