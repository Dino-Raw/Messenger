package com.example.messenger.di

import com.example.messenger.presentation.activity.MainActivity
import com.example.messenger.presentation.fragment.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ChatFragment)
    fun inject(fragment: UsersFragment)
    fun inject(activity: MainActivity)
}