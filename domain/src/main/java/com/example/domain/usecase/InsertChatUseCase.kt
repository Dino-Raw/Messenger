package com.example.domain.usecase

import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow

class InsertChatUseCase(private val repository: ChatRepository) {
    suspend fun execute(toUserId: String): Flow<Response<Chat>> =
        repository.insertChat(toUserId = toUserId)
}