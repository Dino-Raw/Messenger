package com.example.domain.repository.remote

import com.example.domain.model.Message
import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getMessages(chatId: String): Flow<Response<ArrayList<Message>>>
    suspend fun getRecentMessage(chatId: String): Flow<Response<Message>>
    suspend fun insertMessage(message: Message): Flow<Response<Boolean>>
    suspend fun updateMessage(message: Message): Flow<Response<Boolean>>
    suspend fun deleteMessage(messageId: String): Flow<Response<Boolean>>
    suspend fun chatListener(chatId: String): Flow<Response<Message>>
}