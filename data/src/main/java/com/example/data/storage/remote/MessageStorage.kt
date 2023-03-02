package com.example.data.storage.remote

import com.example.domain.model.Message
import com.example.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MessageStorage {
    suspend fun getMessages(chatId: String): Flow<Response<ArrayList<Message>>>
    suspend fun insertMessage(chatId: String, message: Message): Flow<Response<Boolean>>
    suspend fun updateMessage(message: Message): Flow<Response<Boolean>>
    suspend fun deleteMessage(messageId: String): Flow<Response<Boolean>>
}