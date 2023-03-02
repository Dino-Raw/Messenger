package com.example.domain.usecase

import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.repository.remote.MessageRepository
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(private val repository: MessageRepository) {
    suspend fun execute(chatId: String): Flow<Response<ArrayList<Message>>> =
        repository.getMessages(chatId = chatId)
}