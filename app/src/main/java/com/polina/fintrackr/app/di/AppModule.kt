package com.polina.fintrackr.app.di

import android.content.Context
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
import com.polina.domain.use_case.AppInitUseCase
import com.polina.domain.use_case.GetAndSaveAccountUseCase
import com.polina.domain.use_case.GetCategoriesUseCase
import com.polina.domain.use_case.PostTransactionUseCase
import com.polina.domain.use_case.TransactionUseCase
import com.polina.domain.use_case.UpdateAccountUseCase
import com.polina.fintrackr.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Класс для инициализации зависимостей
 */
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
    ): GetAndSaveAccountUseCase {
        return GetAndSaveAccountUseCase(accountRepository)
    }

    @Provides
    fun provideTransactionUseCase(
        transactionRepository: TransactionRepository,
        sharedPreferences: SharedPreferences
    ): TransactionUseCase {
        return TransactionUseCase(
            transactionRepository,
            sharedPreferences
        )
    }

    @Provides
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor {
        return NetworkMonitor(context)
    }

    @Provides
    fun provideAppUnitCase(
        accountRepository: AccountRepository,
    ): AppInitUseCase {
        return AppInitUseCase(accountRepository)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        api: AccountApiService,
        networkMonitor: NetworkMonitor,
        sharedPreferences: SharedPreferences
    ): AccountRepository {
        return AccountRepositoryImpl(api, networkMonitor, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        api: CategoryApiService
    ): CategoryRepository {
        return CategoryRepositoryImpl(api)
    }

    @Provides
    fun provideGetCategoriesUseCase(
        categoryRepository: CategoryRepository,
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }

    @Provides
    fun provideUpdateAccountUseCase(
        accountRepository: AccountRepository,
    ): UpdateAccountUseCase {
        return UpdateAccountUseCase(accountRepository)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        api: TransactionApiService,
        networkMonitor: NetworkMonitor
    ): TransactionRepository {
        return TransactionRepositoryImpl(api, networkMonitor)
    }

    @Provides
    fun providePostTransactionUseCase(
        transactionRepository: TransactionRepository
    ): PostTransactionUseCase {
        return PostTransactionUseCase(transactionRepository)
    }
}