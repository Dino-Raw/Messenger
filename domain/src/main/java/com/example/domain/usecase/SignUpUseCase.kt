package com.example.domain.usecase

import com.example.domain.model.Response
import com.example.domain.model.UserAuth
import com.example.domain.repository.remote.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignUpUseCase(private val repository: AuthRepository) {
    suspend fun execute(userAuth: UserAuth): Flow<Response<Boolean>> =
        repository.signUp(userAuth = userAuth)
}