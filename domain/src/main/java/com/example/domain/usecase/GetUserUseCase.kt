package com.example.domain.usecase

import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.repository.remote.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(private val repository: UserRepository) {
    suspend fun execute(userId: String): Flow<Response<User>> =
        repository.getUser(userId = userId)
}