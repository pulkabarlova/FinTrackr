package com.polina.fintrackr.app.di

import android.app.Application
import com.polina.data.network.NetworkModule
import com.polina.ui.navigation.ViewModelFactoryProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Компонент, предоставляющий зависимости
 */

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun activityComponent(): ActivityComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }
}