package com.example.domain.repository.remote

import com.example.domain.model.Chat
import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChat(chatId: String): Flow<Response<Chat>>
    suspend fun deleteChat(chatId: String): Flow<Response<Boolean>>
    suspend fun updateChat(chat: Chat): Flow<Response<Boolean>>
    suspend fun insertChat(chat: Chat): Flow<Response<Boolean>>
}