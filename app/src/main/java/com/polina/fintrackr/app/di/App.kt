package com.polina.fintrackr.app.di

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.polina.fintrackr.app.worker.SyncWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Класс для инициализации Dagger
 */
class App : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this, this))
            .build()

        appComponent.inject(this)

        val workerRequest =
            PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.HOURS)
                .build()

        WorkManager.getInstance(this.applicationContext).enqueueUniquePeriodicWork(
            PERIODICAL_SYNC_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            workerRequest,
        )
    }
}

const val PERIODICAL_SYNC_WORK = "PERIODICAL_SYNC_WORK"
