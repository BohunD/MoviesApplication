package com.apps.moviesapplication.presentation.moviesListScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    moviesRepository: MoviesRepository
) : ViewModel() {

    val trendingMovies: Flow<PagingData<TmdbApiService.MovieDemo>> = moviesRepository.getTrendingMovies().cachedIn(viewModelScope)

}
