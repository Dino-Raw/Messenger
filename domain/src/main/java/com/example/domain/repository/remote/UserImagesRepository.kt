package com.example.domain.repository.remote

import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface UserImagesRepository {
    suspend fun insert(imageProfilePath: String): Flow<Response<String>>
    suspend fun delete(): Flow<Response<Boolean>>
}