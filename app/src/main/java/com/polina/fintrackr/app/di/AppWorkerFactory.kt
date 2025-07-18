package com.polina.fintrackr.app.di

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.polina.fintrackr.app.worker.SyncWorker
import com.polina.domain.use_case.AppInitUseCase
import javax.inject.Inject
import javax.inject.Provider

class AppWorkerFactory @Inject constructor(
    private val appInitUseCase: AppInitUseCase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name -> {
                SyncWorker(appContext, workerParameters)
            }
            else -> null
        }
    }
}