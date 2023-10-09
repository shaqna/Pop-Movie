package com.maou.popmovie.data.di

import androidx.room.Room
import androidx.work.WorkManager
import com.maou.popmovie.data.repository.PopMovieRepositoryImpl
import com.maou.popmovie.data.service.MovieUpdateWorker
import com.maou.popmovie.data.source.MovieDataSource
import com.maou.popmovie.data.source.local.LocalDataSource
import com.maou.popmovie.data.source.local.database.LocalDatabase
import com.maou.popmovie.data.source.remote.service.ApiService
import com.maou.popmovie.domain.repository.PopMovieRepository
import com.maou.popmovie.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind

val retrofitModule = module {
    single {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val localDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java,
            "PopMovie.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    factory {
        get<LocalDatabase>().movieDao()
    }
}

val sourceModule = module {
    singleOf(::LocalDataSource)
    singleOf(::MovieDataSource)
}

val repositoryModule = module {
    singleOf(::PopMovieRepositoryImpl) {
        bind<PopMovieRepository>()
    }
}

val workerModule = module {
    workerOf(::MovieUpdateWorker)
}

val workInstanceModule = module {
    single {
        WorkManager.getInstance(get())
    }
}
