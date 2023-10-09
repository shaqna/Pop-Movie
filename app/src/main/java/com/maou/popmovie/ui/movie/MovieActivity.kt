package com.maou.popmovie.ui.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkManager
import androidx.work.WorkQuery
import androidx.work.WorkQuery.Builder
import com.maou.popmovie.R
import com.maou.popmovie.databinding.ActivityMovieBinding
import com.maou.popmovie.utils.Constants
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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadKoinModules(MovieViewModel.inject())

        initAdapter()
        runBackgroundTask()
        fetchMovies()
        observeMovies()
        observeUpdateMovie()
    }

    private fun observeUpdateMovie() {
        val workManager = WorkManager.getInstance(this)

        workManager.getWorkInfosByTagLiveData(Constants.TAG_FETCH_LATEST_MOVIE)
            .observe(this) { workInfos ->
                for(workInfo in workInfos) {
                    showNotification("Pesan", "Movie telah diperbarui")
                }
            }

    }

    private fun showNotification(title: String, description: String?) {
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
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
    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }
}