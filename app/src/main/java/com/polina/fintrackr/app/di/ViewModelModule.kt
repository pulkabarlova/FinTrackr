package com.polina.fintrackr.app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polina.articles.ui.ArticlesViewModel
import com.polina.count.ui.CountViewModel
import com.polina.expenses.ui.ExpensesViewModel
import com.polina.income.ui.IncomesViewModel
import com.polina.settings.ui.SettingsViewModel
import com.polina.splash.ui.SplashViewModel
import com.polina.transaction_action.ui.ExpensesAnalysViewModel
import com.polina.transaction_action.ui.IncomesAnalysViewModel
import com.polina.transaction_action.ui.TransactionAddViewModel
import com.polina.transaction_action.ui.TransactionEditViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CountViewModel::class)
    fun bindCountViewModel(vm: CountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesViewModel::class)
    fun bindArticlesViewModel(vm: ArticlesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesViewModel::class)
    fun bindIncomesViewModel(vm: IncomesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    fun bindExpensesViewModel(vm: ExpensesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun bindSplashViewModel(vm: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionAddViewModel::class)
    fun bindTransactionAddViewModel(vm: TransactionAddViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionEditViewModel::class)
    fun bindTransactionEditViewModel(vm: TransactionEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesAnalysViewModel::class)
    fun bindExpensesAnalysViewModel(vm: ExpensesAnalysViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesAnalysViewModel::class)
    fun bindIncomesAnalysViewModel(vm: IncomesAnalysViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(vm: SettingsViewModel): ViewModel

    @Binds
    fun bindFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)