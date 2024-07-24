package com.apps.moviesapplication.presentation.movieInfoScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.domain.repository.MovieInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val movieInfoRepository: MovieInfoRepository
) : ViewModel() {

    private val _movie = MutableStateFlow<TmdbApiService.MovieFull?>(null)
    val movie: StateFlow<TmdbApiService.MovieFull?> = _movie

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = movieInfoRepository.getMovieDetails(id)
                _movie.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
