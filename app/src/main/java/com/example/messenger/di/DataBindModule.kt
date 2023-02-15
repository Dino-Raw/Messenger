package com.example.messenger.di

import com.example.data.repository.remote.AuthRepositoryImpl
import com.example.data.repository.remote.UserImagesRepositoryImpl
import com.example.data.storage.remote.AuthStorage
import com.example.data.storage.remote.UserImagesStorage
import com.example.data.storage.remote.firebase.FirebaseAuthStorage
import com.example.data.storage.remote.firebase.FirebaseUserImagesStorage
import com.example.domain.repository.remote.AuthRepository
import com.example.domain.repository.remote.UserImagesRepository
import dagger.Binds
import dagger.Module

@Module
interface DataBindModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindAuthStorage(impl: FirebaseAuthStorage): AuthStorage

    @Binds
    fun bindUserImagesRepository(impl: UserImagesRepositoryImpl): UserImagesRepository

    @Binds
    fun bindUserImagesStorage(impl: FirebaseUserImagesStorage): UserImagesStorage
}