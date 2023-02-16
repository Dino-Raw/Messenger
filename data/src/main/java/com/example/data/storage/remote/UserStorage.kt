package com.example.data.storage.remote

import com.example.domain.model.Response
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserStorage {
    suspend fun getUser(userId: String): Flow<Response<User>>
    suspend fun getUserList(): Flow<Response<ArrayList<User>>>
}