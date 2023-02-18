package com.example.domain.usecase

import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatListenerUseCase(private val repository: ChatRepository) {
    suspend fun execute(chatId: String): Flow<Response<Message>> =
        repository.chatListener(chatId = chatId)
}