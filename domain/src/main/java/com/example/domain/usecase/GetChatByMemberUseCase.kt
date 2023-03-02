package com.example.domain.usecase

import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatByMemberUseCase(private val repository: ChatRepository) {
    suspend fun execute(memberId: String): Flow<Response<Chat>> =
        repository.getChatByMember(memberId = memberId)
}