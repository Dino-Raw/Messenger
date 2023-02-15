package com.example.data.repository.remote

import com.example.data.storage.remote.AuthStorage
import com.example.domain.model.Response
import com.example.domain.model.UserAuth
import com.example.domain.repository.remote.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authStorage: AuthStorage,
): AuthRepository {
    override suspend fun signUp(userAuth: UserAuth): Flow<Response<Boolean>> =
        authStorage.signUp(userAuth = userAuth)

    override suspend fun signIn(userAuth: UserAuth): Flow<Response<Boolean>> =
        authStorage.signIn(userAuth = userAuth)

    override suspend fun signOut(): Flow<Response<Boolean>> =
        authStorage.signOut()

    override suspend fun checkSignIn(): Flow<Response<Boolean>> =
        authStorage.checkSignIn()
}