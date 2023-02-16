package com.example.domain.repository.remote

import com.example.domain.model.Response
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(userId: String): Flow<Response<User>>
    suspend fun getUserList(): Flow<Response<ArrayList<User>>>
}