package com.maou.popmovie.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maou.popmovie.R
import com.maou.popmovie.databinding.ActivityMovieBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

private const val TAG = "MovieActivity"

class MovieActivity : AppCompatActivity() {
    private val binding: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter()
    }

    private val viewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadKoinModules(MovieViewModel.inject())

        initAdapter()
        runBackgroundTask()
        fetchMovies()
        observeMovies()
    }

    private fun runBackgroundTask() {
        viewModel.fetchPopMoviePeriodically(getString(R.string.api_key))
    }

    private fun initAdapter() {
        binding.rvMovie.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MovieActivity)
        }
    }

    private fun fetchMovies() {
        viewModel.fetchMovies()
    }

    private fun observeMovies() {
        viewModel.movies
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { movieUiState ->
                handleMovieState(movieUiState)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleMovieState(movieUiState: MovieUiState) {
        when (movieUiState) {
            is MovieUiState.Error -> {
                Log.d(TAG, "handleMovieState: ${movieUiState.message}")
                Toast.makeText(this@MovieActivity, movieUiState.message, Toast.LENGTH_SHORT)
                    .show()
            }

            MovieUiState.Init -> Unit
            is MovieUiState.Loading -> {
                handleLoading(movieUiState.isLoading)
            }

            is MovieUiState.Success -> {
                movieAdapter.setList(movieUiState.movies)
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }

}