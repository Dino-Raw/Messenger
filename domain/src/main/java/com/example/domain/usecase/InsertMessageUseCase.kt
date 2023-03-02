package com.example.domain.usecase

import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.repository.remote.MessageRepository
import kotlinx.coroutines.flow.Flow

class InsertMessageUseCase(private val repository: MessageRepository) {
    suspend fun execute(chatId: String, message: Message): Flow<Response<Boolean>> =
        repository.insertMessage(chatId = chatId, message = message)
}