package com.example.domain.repository.remote

import com.example.domain.model.Response
import com.example.domain.model.UserAuth
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun signUp(userAuth: UserAuth): Flow<Response<Boolean>>
    suspend fun signIn(userAuth: UserAuth): Flow<Response<Boolean>>
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun checkSignIn(): Flow<Response<Boolean>>
}