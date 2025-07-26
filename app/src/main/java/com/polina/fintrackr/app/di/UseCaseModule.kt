package com.polina.fintrackr.app.di

import android.content.SharedPreferences
import com.polina.domain.repositories.AccountRepository
import com.polina.domain.repositories.CategoryRepository
import com.polina.domain.repositories.TransactionRepository
import com.polina.domain.use_case.AppInitUseCase
import com.polina.domain.use_case.DeleteTransactionByIdUseCase
import com.polina.domain.use_case.GetAndSaveAccountUseCase
import com.polina.domain.use_case.GetCategoriesUseCase
import com.polina.domain.use_case.GetExpensesStat
import com.polina.domain.use_case.GetIncomesStat
import com.polina.domain.use_case.GetJulyAcountAnalys
import com.polina.domain.use_case.GetTransactionByIdUseCase
import com.polina.domain.use_case.PostTransactionUseCase
import com.polina.domain.use_case.TransactionUseCase
import com.polina.domain.use_case.UpdateAccountUseCase
import com.polina.domain.use_case.UpdateTransactionUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideAppInitUseCase(
        accountRepository: AccountRepository,
        transactionRepository: TransactionRepository,
        getCategoriesUseCase: GetCategoriesUseCase,
    ) =
        AppInitUseCase(
            accountRepository,
            transactionRepository,
            getCategoriesUseCase,
        )

    @Provides
    fun provideGetAndSaveAccountUseCase(accountRepository: AccountRepository) =
        GetAndSaveAccountUseCase(accountRepository)

    @Provides
    fun provideTransactionUseCase(
        transactionRepository: TransactionRepository,
        sharedPreferences: SharedPreferences
    ) = TransactionUseCase(transactionRepository, sharedPreferences)

    @Provides
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository) =
        GetCategoriesUseCase(categoryRepository)

    @Provides
    fun provideUpdateAccountUseCase(accountRepository: AccountRepository) =
        UpdateAccountUseCase(accountRepository)

    @Provides
    fun providePostTransactionUseCase(transactionRepository: TransactionRepository) =
        PostTransactionUseCase(transactionRepository)

    @Provides
    fun provideGetTransactionByIdUseCase(transactionRepository: TransactionRepository) =
        GetTransactionByIdUseCase(transactionRepository)

    @Provides
    fun provideUpdateTransactionUseCase(transactionRepository: TransactionRepository) =
        UpdateTransactionUseCase(transactionRepository)

    @Provides
    fun provideDeleteTransactionByIdUseCase(transactionRepository: TransactionRepository) =
        DeleteTransactionByIdUseCase(transactionRepository)

    @Provides
    fun provideGetExpensesStat(
        transactionRepository: TransactionRepository,
        sharedPreferences: SharedPreferences,
    ) =
        GetExpensesStat(transactionRepository, sharedPreferences)

    @Provides
    fun provideGetIncomesStat(
        transactionRepository: TransactionRepository,
        sharedPreferences: SharedPreferences,
    ) =
        GetIncomesStat(transactionRepository, sharedPreferences)

    @Provides
    fun provideGetJulyAcountAnalys(
        transactionRepository: TransactionRepository,
        sharedPreferences: SharedPreferences,
    ) =
        GetJulyAcountAnalys(transactionRepository, sharedPreferences)
}
