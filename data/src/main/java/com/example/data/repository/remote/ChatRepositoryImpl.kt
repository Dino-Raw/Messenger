package com.example.data.repository.remote

import com.example.data.storage.remote.firebase.FirebaseChatStorage
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firebaseChatStorage: FirebaseChatStorage,
): ChatRepository {
    override suspend fun getMessages(chatId: String): Flow<Response<ArrayList<Message>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMessage(message: Message): Flow<Response<Boolean>> =
        firebaseChatStorage.insertMessage(message = message)

    override suspend fun updateMessage(message: Message): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(messageId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun chatListener(chatId: String): Flow<Response<Message>> =
        firebaseChatStorage.chatListener(chatId = chatId)

}