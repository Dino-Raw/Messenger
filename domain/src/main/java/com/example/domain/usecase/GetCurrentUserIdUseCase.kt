package com.example.domain.usecase

import com.example.domain.model.Response
import com.example.domain.repository.remote.CurrentUserRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserIdUseCase(private val repository: CurrentUserRepository) {
    suspend fun execute(): Flow<Response<String>> =
        repository.getUserId()
}