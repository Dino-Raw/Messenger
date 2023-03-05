package com.example.domain.usecase

import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow

class UpdateChatUseCase(private val repository: ChatRepository) {
    suspend fun execute(chat: Chat): Flow<Response<Boolean>> =
        repository.updateChat(chat = chat)
}