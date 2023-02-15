package com.example.domain.usecase

import com.example.domain.model.Response
import com.example.domain.repository.remote.UserImagesRepository
import kotlinx.coroutines.flow.Flow

class UserImageDeleteUseCase(private val repository: UserImagesRepository) {
    suspend fun execute(): Flow<Response<Boolean>> =
        repository.delete()
}