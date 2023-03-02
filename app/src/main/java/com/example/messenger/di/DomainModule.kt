package com.example.messenger.di

import com.example.domain.model.CurrentUser
import com.example.domain.model.UserAuth
import com.example.domain.repository.remote.*
import com.example.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideUserAuth() =
        UserAuth()

    @Provides
    fun provideUser() =
        CurrentUser()

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

    @Provides
    fun provideInsertNewUserUseCase(repository: CurrentUserRepository) =
        InsertNewUserUseCase(repository = repository)

    @Provides
    fun provideGetCurrentUserUseCase(repository: CurrentUserRepository) =
        GetCurrentUserUseCase(repository = repository)

    @Provides
    fun provideGetUserListUseCase(repository: UserRepository) =
        GetUserListUseCase(repository = repository)

    @Provides
    fun provideGetUserUseCase(repository: UserRepository) =
        GetUserUseCase(repository = repository)

    @Provides
    fun provideInsertMessageUseCase(repository: MessageRepository) =
        InsertMessageUseCase(repository = repository)

    @Provides
    fun provideGetMessagesUseCase(repository: MessageRepository) =
        GetMessagesUseCase(repository = repository)

    @Provides
    fun provideGetChatByIdUseCase(repository: ChatRepository) =
        GetChatByIdUseCase(repository = repository)

    @Provides
    fun provideGetChatByMemberUseCase(repository: ChatRepository) =
        GetChatByMemberUseCase(repository = repository)

    @Provides
    fun provideGetChatListUseCase(repository: ChatRepository) =
        GetChatListUseCase(repository = repository)
}