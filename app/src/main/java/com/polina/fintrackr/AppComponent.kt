package com.polina.fintrackr

import dagger.Component

@Component(modules =[AppModule::class])
interface AppComponent {
    fun inject(app: App)
}
