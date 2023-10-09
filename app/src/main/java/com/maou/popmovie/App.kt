package com.maou.popmovie

import android.app.Application
import com.maou.popmovie.data.di.localDatabaseModule
import com.maou.popmovie.data.di.repositoryModule
import com.maou.popmovie.data.di.retrofitModule
import com.maou.popmovie.data.di.sourceModule
import com.maou.popmovie.data.di.workInstanceModule
import com.maou.popmovie.data.di.workerModule
import com.maou.popmovie.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.androidx.workmanager.koin.workManagerFactory

class App: Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            workManagerFactory()
            modules(
                listOf(
                    retrofitModule,
                    localDatabaseModule,
                    retrofitModule,
                    sourceModule,
                    repositoryModule,
                    workerModule,
                    workInstanceModule,
                    domainModule
                )
            )
        }
    }
}