package com.apps.moviesapplication.presentation.moviesListScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apps.moviesapplication.data.network.TmdbApiService.Movie
import com.apps.moviesapplication.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val trendingMovies: Flow<PagingData<Movie>> = moviesRepository.getTrendingMovies().cachedIn(viewModelScope)
}
