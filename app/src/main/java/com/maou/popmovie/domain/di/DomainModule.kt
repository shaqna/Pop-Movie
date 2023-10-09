package com.maou.popmovie.domain.di

import com.maou.popmovie.domain.usecase.MovieInteractor
import com.maou.popmovie.domain.usecase.MovieUseCase
import com.maou.popmovie.domain.usecase.splash.SplashInteractor
import com.maou.popmovie.domain.usecase.splash.SplashUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::MovieInteractor) {
        bind<MovieUseCase>()
    }

    singleOf(::SplashInteractor) {
        bind<SplashUseCase>()
    }
}