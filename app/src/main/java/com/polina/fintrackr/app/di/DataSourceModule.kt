package com.polina.fintrackr.app.di

import android.content.SharedPreferences
import com.polina.data.db.AccountDao
import com.polina.data.db.CategoryDao
import com.polina.data.db.TransactionDao
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
            sharedPreferences: SharedPreferences,
            accountDao: AccountDao
        ): AccountRepository =
            AccountRepositoryImpl(api, networkMonitor, sharedPreferences, accountDao)

        @Provides
        @Singleton
        fun provideTransactionRepository(
            api: TransactionApiService,
            networkMonitor: NetworkMonitor,
            transactionDao: TransactionDao,
            categoryDao: CategoryDao,
            accountDao: AccountDao
        ): TransactionRepository =
            TransactionRepositoryImpl(api, networkMonitor, transactionDao, categoryDao, accountDao)

        @Provides
        @Singleton
        fun provideCategoryRepository(
            api: CategoryApiService,
            categoryDao: CategoryDao,
            networkMonitor: NetworkMonitor,
        ): CategoryRepository = CategoryRepositoryImpl(api, categoryDao, networkMonitor)
    }
}