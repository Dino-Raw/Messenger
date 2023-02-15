package com.example.messenger.app

import android.app.Application
import com.example.messenger.di.AppComponent
import com.example.messenger.di.AppModule
import com.example.messenger.di.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}