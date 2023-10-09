package com.maou.popmovie.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.popmovie.domain.usecase.splash.SplashUseCase
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class SplashViewModel(
    private val splashUseCase: SplashUseCase
): ViewModel() {

    var isEmpty: Boolean = false

    init {
        isLocalDatabaseEmpty()
    }

    private fun isLocalDatabaseEmpty() {
        viewModelScope.launch {
            isEmpty = splashUseCase.isLocalDatabaseEmpty()
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::SplashViewModel)
        }
    }

}