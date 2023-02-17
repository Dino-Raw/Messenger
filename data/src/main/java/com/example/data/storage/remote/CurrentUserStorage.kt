package com.example.data.storage.remote

import com.example.domain.model.Response
import com.example.domain.model.CurrentUser
import kotlinx.coroutines.flow.Flow

interface CurrentUserStorage {
    suspend fun insert(currentUser: CurrentUser): Flow<Response<Boolean>>
    suspend fun delete(): Flow<Response<Boolean>>
    suspend fun update(currentUser: CurrentUser): Flow<Response<Boolean>>
    suspend fun getUser(): Flow<Response<CurrentUser>>
    suspend fun getUserId(): Flow<Response<String>>
}