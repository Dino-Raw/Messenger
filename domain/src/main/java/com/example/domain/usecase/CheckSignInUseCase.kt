package com.example.domain.usecase

import com.example.domain.model.Response
import com.example.domain.repository.remote.AuthRepository
import kotlinx.coroutines.flow.Flow

class CheckSignInUseCase(private val repository: AuthRepository) {
    suspend fun execute(): Flow<Response<Boolean>> =
        repository.checkSignIn()
}