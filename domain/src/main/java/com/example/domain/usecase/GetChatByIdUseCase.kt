package com.example.domain.usecase

import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatByIdUseCase(private val repository: ChatRepository) {
    suspend fun execute(chatId: String): Flow<Response<Chat>> =
        repository.getChatById(chatId = chatId)
}