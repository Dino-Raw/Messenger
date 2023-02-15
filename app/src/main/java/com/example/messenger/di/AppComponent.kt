package com.example.messenger.di

import com.example.messenger.presentation.fragment.ChatFragment
import com.example.messenger.presentation.fragment.HomeFragment
import com.example.messenger.presentation.fragment.SignInFragment
import com.example.messenger.presentation.fragment.SignUpFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ChatFragment)
}