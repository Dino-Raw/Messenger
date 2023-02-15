package com.example.messenger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule(val context: Context) {
    @Singleton
    @Provides
    fun provideContext() = context
}