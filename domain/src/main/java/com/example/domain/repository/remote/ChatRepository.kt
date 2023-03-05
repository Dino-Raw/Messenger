package com.example.domain.repository.remote

import com.example.domain.model.Chat
import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatById(chatId: String): Flow<Response<Chat>>
    suspend fun getChatByMember(memberId: String): Flow<Response<Chat>>
    suspend fun deleteChat(chatId: String): Flow<Response<Boolean>>
    suspend fun updateChat(chat: Chat): Flow<Response<Boolean>>
    suspend fun getChatList(): Flow<Response<ArrayList<Chat>>>
    suspend fun insertChat(toUserId: String): Flow<Response<Chat>>
}