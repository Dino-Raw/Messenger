package com.example.data.repository.remote

import com.example.data.storage.remote.MessageStorage
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.repository.remote.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageStorage: MessageStorage,
): MessageRepository {
    override suspend fun getMessages(chatId: String): Flow<Response<ArrayList<Message>>> =
        messageStorage.getMessages(chatId = chatId)

    override suspend fun insertMessage(chatId: String, message: Message): Flow<Response<Boolean>> =
        messageStorage.insertMessage(chatId = chatId, message = message)

    override suspend fun updateMessage(message: Message): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(messageId: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}