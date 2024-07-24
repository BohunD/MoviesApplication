package com.apps.moviesapplication.domain.repository

import com.apps.moviesapplication.data.models.MovieFull

interface MovieInfoRepository {
    suspend fun getMovieDetails(id: Int): MovieFull
}