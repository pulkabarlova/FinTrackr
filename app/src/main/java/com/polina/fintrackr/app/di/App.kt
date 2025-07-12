package com.polina.fintrackr.app.di

import android.app.Application

/**
 * Класс для инициализации Dagger
 */
class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this, this))
            .build()

        appComponent.inject(this)
    }
}