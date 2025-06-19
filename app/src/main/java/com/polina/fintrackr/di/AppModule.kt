package com.polina.fintrackr.di

import android.content.Context
import android.util.Log
import com.polina.fintrackr.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Properties
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("url")
    @Singleton
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Named("token")
    @Singleton
    fun provideToken(): String {
        return BuildConfig.API_KEY
    }
}