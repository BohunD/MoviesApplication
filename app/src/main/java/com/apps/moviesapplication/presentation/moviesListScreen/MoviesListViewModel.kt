package com.apps.moviesapplication.presentation.moviesListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apps.moviesapplication.data.models.MovieDemo
import com.apps.moviesapplication.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

abstract class IMoviesListViewModel : ViewModel(){
    abstract val trendingMovies: Flow<PagingData<MovieDemo>>
}
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    moviesRepository: MoviesRepository
) : IMoviesListViewModel() {

    override val trendingMovies: Flow<PagingData<MovieDemo>> = moviesRepository.getTrendingMovies().cachedIn(viewModelScope)

}
