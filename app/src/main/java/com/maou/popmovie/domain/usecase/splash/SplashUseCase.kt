package com.maou.popmovie.domain.usecase.splash

interface SplashUseCase {
    fun isLocalDatabaseEmpty(): Boolean
}