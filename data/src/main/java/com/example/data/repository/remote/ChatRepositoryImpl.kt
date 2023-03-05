package com.example.data.repository.remote

import com.example.data.storage.remote.ChatStorage
import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import com.example.domain.repository.remote.MessageRepository
import com.example.domain.repository.remote.UserRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Named

class ChatRepositoryImpl @Inject constructor(
    private val chatStorage: ChatStorage,
): ChatRepository {
    override suspend fun getChatById(chatId: String): Flow<Response<Chat>> =
        chatStorage.getChatById(chatId = chatId)

    override suspend fun getChatByMember(memberId: String): Flow<Response<Chat>> =
        chatStorage.getChatByMember(memberId = memberId)

    override suspend fun deleteChat(chatId: String): Flow<Response<Boolean>> =
        chatStorage.deleteChat(chatId = chatId)

    override suspend fun updateChat(chat: Chat): Flow<Response<Boolean>> =
        chatStorage.updateChat(chat = chat)

    override suspend fun getChatList(): Flow<Response<ArrayList<Chat>>> =
        chatStorage.getChatList()

    override suspend fun insertChat(toUserId: String): Flow<Response<Chat>> =
        chatStorage.insertChat(toUserId = toUserId)
}