package com.example.data.storage.remote

import com.example.domain.model.Chat
import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ChatStorage {
    suspend fun getChatById(chatId: String): Flow<Response<Chat>>
    suspend fun deleteChat(chatId: String): Flow<Response<Boolean>>
    suspend fun updateChat(chat: Chat): Flow<Response<Boolean>>
    suspend fun insertChat(chat: Chat): Flow<Response<Boolean>>
    suspend fun getChatByMember(memberId: String): Flow<Response<Chat>>
}