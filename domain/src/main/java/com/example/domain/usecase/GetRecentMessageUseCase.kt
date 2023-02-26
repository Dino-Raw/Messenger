package com.example.domain.usecase

import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.repository.remote.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetRecentMessageUseCase(private val repository: MessageRepository) {
    suspend fun execute(chatId: String): Flow<Response<Message>> =
        repository.getRecentMessage(chatId = chatId)
}