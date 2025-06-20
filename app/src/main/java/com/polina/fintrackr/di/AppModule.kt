package com.polina.fintrackr.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.polina.fintrackr.BuildConfig
import com.polina.fintrackr.core.data.network.AccountApiService
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.data.repositories.AccountRepository
import com.polina.fintrackr.core.data.repositories.TransactionRepository
import com.polina.fintrackr.core.data.use_case.AppInitUseCase
import com.polina.fintrackr.core.data.use_case.GetAndSaveAccountUseCase
import com.polina.fintrackr.core.data.use_case.TransactionUseCase
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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): android.content.SharedPreferences {
        return context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideGetAndSaveAccountUseCase(
        accountRepository: AccountRepository,
        sharedPreferences: SharedPreferences,
    ): GetAndSaveAccountUseCase {
        return GetAndSaveAccountUseCase(accountRepository, sharedPreferences)
    }

    @Provides
    fun provideTransactionUseCase(
        transactionRepository: TransactionRepository,
        sharedPreferences: SharedPreferences
    ): TransactionUseCase {
        return TransactionUseCase(transactionRepository, sharedPreferences)
    }
    @Provides
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return NetworkMonitor(context)
    }
    @Provides
    fun provideAppUnitCase(
        accountRepository: AccountRepository,
        sharedPreferences: SharedPreferences,
        networkMonitor: NetworkMonitor
    ): AppInitUseCase {
        return AppInitUseCase(accountRepository, sharedPreferences, networkMonitor)}
}