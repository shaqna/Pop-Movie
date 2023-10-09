package com.maou.popmovie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.popmovie.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class MovieViewModel(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    private val _movies: MutableStateFlow<MovieUiState> = MutableStateFlow(MovieUiState.Init)
    val movies: StateFlow<MovieUiState> = _movies

    fun fetchMovies() {
        viewModelScope.launch {
            movieUseCase.getAllMovies()
                .onStart {
                    _movies.value = MovieUiState.Loading(true)
                }
                .catch { e ->
                    _movies.value = MovieUiState.Loading(false)
                    _movies.value = MovieUiState.Error(e.message.toString())
                }
                .collect { result ->
                    _movies.value = MovieUiState.Loading(false)
                    result.fold(onSuccess = { movies ->
                        _movies.value = MovieUiState.Success(movies)
                    }, onFailure = {err ->
                        _movies.value = MovieUiState.Error(err.message.toString())
                    })
                }
        }
    }

    fun fetchPopMoviePeriodically(apiKey: String) {
        viewModelScope.launch {
            movieUseCase.fetchPopMoviePeriodically(apiKey)
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::MovieViewModel)
        }
    }
}