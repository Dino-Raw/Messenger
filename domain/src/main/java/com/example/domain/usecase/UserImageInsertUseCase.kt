package com.example.domain.usecase

import com.example.domain.model.Response
import com.example.domain.repository.remote.UserImagesRepository
import kotlinx.coroutines.flow.Flow

class UserImageInsertUseCase(private val repository: UserImagesRepository) {
    suspend fun execute(imageProfilePath: String): Flow<Response<String>> =
        repository.insert(imageProfilePath = imageProfilePath)
}