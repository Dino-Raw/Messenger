package com.example.domain.usecase

import com.example.domain.model.CurrentUser
import com.example.domain.repository.remote.CurrentUserRepository

class InsertNewUserUseCase(private val repository: CurrentUserRepository) {
    suspend fun execute(currentUser: CurrentUser) = repository.insert(currentUser = currentUser)
}