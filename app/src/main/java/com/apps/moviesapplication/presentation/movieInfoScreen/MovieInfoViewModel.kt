package com.apps.moviesapplication.presentation.movieInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.moviesapplication.data.models.MovieFull
import com.apps.moviesapplication.domain.repository.MovieInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val movieInfoRepository: MovieInfoRepository
) : ViewModel() {

    private val _movie = MutableStateFlow<MovieFull?>(null)
    val movie: StateFlow<MovieFull?> = _movie

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieInfoRepository.getMovieDetails(id)
                _movie.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
