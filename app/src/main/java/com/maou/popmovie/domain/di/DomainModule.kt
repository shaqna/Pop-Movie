package com.maou.popmovie.domain.di

import com.maou.popmovie.domain.usecase.MovieInteractor
import com.maou.popmovie.domain.usecase.MovieUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::MovieInteractor) {
        bind<MovieUseCase>()
    }
}