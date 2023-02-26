package com.example.domain.usecase

import com.example.domain.model.CurrentUser
import com.example.domain.model.Response
import com.example.domain.repository.remote.CurrentUserRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(private val repository: CurrentUserRepository) {
    suspend fun execute(): Flow<Response<CurrentUser>> =
        repository.getUser()
}