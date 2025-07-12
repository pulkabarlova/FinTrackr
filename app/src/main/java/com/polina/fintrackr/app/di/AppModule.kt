package com.polina.fintrackr.app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.fintrackr.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Модуль, предоставляющий зависимости
 */

@Module
class AppModule(private val application: Application, private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @Named("url")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @Named("token")
    fun provideToken(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideNetworkMonitor(): NetworkMonitor = NetworkMonitor(context)
}