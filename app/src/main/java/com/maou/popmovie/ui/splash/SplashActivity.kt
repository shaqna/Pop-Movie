package com.maou.popmovie.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.maou.popmovie.R
import com.maou.popmovie.ui.movie.MovieActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    private val alertDialogBuilder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadKoinModules(SplashViewModel.inject())

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            observeConnectionAndLocalDatabase()
        }, 3000)

    }

    private fun observeConnectionAndLocalDatabase() {
        if (!checkForInternet(this)) {
            showDialog("Tidak ada koneksi internet")
        } else {
            Intent(this@SplashActivity, MovieActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun showDialog(message: String) {
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setTitle("Pesan")
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton("Keluar") { dialog, which ->
            finish()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}