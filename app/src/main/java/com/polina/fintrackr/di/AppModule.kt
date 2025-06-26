package com.polina.fintrackr.di

import android.content.Context
import android.content.SharedPreferences
import com.polina.fintrackr.BuildConfig
import com.polina.fintrackr.core.data.network.AccountApiService
import com.polina.fintrackr.core.data.network.CategoryApiService
import com.polina.fintrackr.core.data.network.NetworkMonitor
import com.polina.fintrackr.core.data.network.TransactionApiService
import com.polina.fintrackr.core.data.repositories.AccountRepositoryImpl
import com.polina.fintrackr.core.data.repositories.CategoryRepositoryImpl
import com.polina.fintrackr.core.data.repositories.TransactionRepositoryImpl
import com.polina.fintrackr.core.data.use_case.AppInitUseCase
import com.polina.fintrackr.core.data.use_case.GetAndSaveAccountUseCase
import com.polina.fintrackr.core.data.use_case.GetCategoriesUseCase
import com.polina.fintrackr.core.data.use_case.TransactionUseCase
import com.polina.fintrackr.core.domain.repositories.AccountRepository
import com.polina.fintrackr.core.domain.repositories.CategoryRepository
import com.polina.fintrackr.core.domain.repositories.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        networkMonitor: NetworkMonitor
    ): GetAndSaveAccountUseCase {
        return GetAndSaveAccountUseCase(accountRepository, sharedPreferences, networkMonitor)
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

    @Provides
    @Singleton
    fun provideAccountRepository(
        api: AccountApiService
    ): AccountRepository {
        return AccountRepositoryImpl(api)
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
    @Singleton
    fun provideTransactionRepository(
        api: TransactionApiService
    ): TransactionRepository {
        return TransactionRepositoryImpl(api)
    }
}