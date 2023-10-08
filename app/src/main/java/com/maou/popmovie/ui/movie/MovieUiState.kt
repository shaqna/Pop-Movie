package com.maou.popmovie.ui.movie

import com.maou.popmovie.domain.model.Movie

sealed class MovieUiState {
    data object Init: MovieUiState()
    data class Loading(val isLoading: Boolean): MovieUiState()
    data class Error(val message: String): MovieUiState()
    data class Success(val movies: List<Movie>): MovieUiState()
}
