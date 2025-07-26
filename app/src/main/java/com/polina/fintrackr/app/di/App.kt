package com.polina.fintrackr.app.di

import android.app.Application
import android.content.SharedPreferences
import androidx.work.ExistingPeriodicWorkPolicy
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

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this, this))
            .build()

        appComponent.inject(this)

        val syncInterval = sharedPreferences.getInt("sync_interval", 1)

        val workerRequest =
            PeriodicWorkRequestBuilder<SyncWorker>(syncInterval.toLong(), TimeUnit.HOURS)
                .build()

        WorkManager.getInstance(this.applicationContext).enqueueUniquePeriodicWork(
            PERIODICAL_SYNC_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            workerRequest,
        )
    }
}

const val PERIODICAL_SYNC_WORK = "PERIODICAL_SYNC_WORK"
