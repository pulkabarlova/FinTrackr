package com.polina.fintrackr.app.di

import android.content.SharedPreferences
import com.polina.data.network.api_service.AccountApiService
import com.polina.data.network.api_service.CategoryApiService
import com.polina.data.network.api_service.TransactionApiService
import com.polina.data.network.monitor.NetworkMonitor
import com.polina.data.repositories.AccountRepositoryImpl
import com.polina.data.repositories.CategoryRepositoryImpl
import com.polina.data.repositories.TransactionRepositoryImpl
import com.polina.domain.repositories.AccountRepository
import com.polina.domain.repositories.CategoryRepository
import com.polina.domain.repositories.TransactionRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface DataSourceModule {
    @Module
    class DataSourceModule {

        @Provides
        @Singleton
        fun provideAccountRepository(
            api: AccountApiService,
            networkMonitor: NetworkMonitor,
            sharedPreferences: SharedPreferences
        ): AccountRepository = AccountRepositoryImpl(api, networkMonitor, sharedPreferences)

        @Provides
        @Singleton
        fun provideTransactionRepository(
            api: TransactionApiService,
            networkMonitor: NetworkMonitor
        ): TransactionRepository = TransactionRepositoryImpl(api, networkMonitor)

        @Provides
        @Singleton
        fun provideCategoryRepository(
            api: CategoryApiService
        ): CategoryRepository = CategoryRepositoryImpl(api)
    }
}