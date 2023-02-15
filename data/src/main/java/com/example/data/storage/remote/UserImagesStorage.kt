package com.example.data.storage.remote

import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface UserImagesStorage {
    suspend fun insert(imageProfilePath: String): Flow<Response<String>>
    suspend fun delete(): Flow<Response<Boolean>>
}