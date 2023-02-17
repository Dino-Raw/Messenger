package com.example.messenger.di

import com.example.data.repository.remote.*
import com.example.data.storage.remote.*
import com.example.data.storage.remote.firebase.*
import com.example.domain.repository.remote.*
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

    @Binds
    fun bindCurrentUserRepository(impl: CurrentUserRepositoryImpl): CurrentUserRepository

    @Binds
    fun bindCurrentUserStorage(impl: FirebaseCurrentUserStorage): CurrentUserStorage

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserStorage(impl: FirebaseUserStorage): UserStorage

    @Binds
    fun bindChatRepository(impl: ChatRepositoryImpl): ChatRepository

    @Binds
    fun bindChatStorage(impl: FirebaseChatStorage): ChatStorage
}