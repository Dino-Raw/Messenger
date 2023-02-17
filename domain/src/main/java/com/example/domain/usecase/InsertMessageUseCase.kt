package com.example.domain.usecase

import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow

class InsertMessageUseCase(private val repository: ChatRepository) {
    suspend fun execute(message: Message): Flow<Response<Boolean>> =
        repository.insertMessage(message = message)
}