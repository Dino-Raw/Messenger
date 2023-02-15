package com.example.messenger.di

import com.example.domain.repository.remote.AuthRepository
import com.example.domain.repository.remote.UserImagesRepository
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideSignUpUseCase(repository: AuthRepository) =
        SignUpUseCase(repository = repository)

    @Provides
    fun provideSignInUseCase(repository: AuthRepository) =
        SignInUseCase(repository = repository)

    @Provides
    fun provideSignOutUseCase(repository: AuthRepository) =
        SignOutUseCase(repository = repository)

    @Provides
    fun provideCheckSignInUseCase(repository: AuthRepository) =
        CheckSignInUseCase(repository = repository)

    @Provides
    fun provideUserImageInsertUseCase(repository: UserImagesRepository) =
        UserImageInsertUseCase(repository = repository)

    @Provides
    fun provideUserImageDeleteUseCase(repository: UserImagesRepository) =
        UserImageDeleteUseCase(repository = repository)
}