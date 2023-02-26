package com.example.data.repository.remote

import com.example.data.storage.remote.ChatStorage
import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatStorage: ChatStorage,
): ChatRepository {
    override suspend fun getChat(chatId: String): Flow<Response<Chat>> =
        chatStorage.getChat(chatId = chatId)

    override suspend fun deleteChat(chatId: String): Flow<Response<Boolean>> =
        chatStorage.deleteChat(chatId = chatId)

    override suspend fun updateChat(chat: Chat): Flow<Response<Boolean>> =
        chatStorage.updateChat(chat = chat)

    override suspend fun insertChat(chat: Chat): Flow<Response<Boolean>> =
        chatStorage.insertChat(chat = chat)
}