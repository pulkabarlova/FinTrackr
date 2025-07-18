package com.polina.fintrackr.app.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.polina.domain.use_case.AppInitUseCase
import com.polina.fintrackr.app.di.App
import javax.inject.Inject

class SyncWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var appInitUseCase: AppInitUseCase

    override suspend fun doWork(): Result {
        val application = context.applicationContext as App
        application.appComponent.injectWorker(this)
        Log.d("SyncWorker", "Worker inizialized")
        return try {
            Log.d("SyncWorker", "Worker started")
            appInitUseCase.loadAllCategories()
            appInitUseCase.ensureAccountInitializedWithRetries()
            appInitUseCase.loadAllTransactions()
            Result.success()
        } catch (e: Exception) {
            Log.d("SyncWorker", e.toString())
            Result.retry()
        }
    }
}