package com.example.domain.usecase

import com.example.domain.model.Chat
import com.example.domain.model.Response
import com.example.domain.repository.remote.ChatRepository
import kotlinx.coroutines.flow.*

class GetChatListUseCase(private val repository: ChatRepository) {
    suspend fun execute(chatIdList: ArrayList<String>): Flow<Response<ArrayList<Chat>>> =
        repository.getChatList(chatIdList = chatIdList)
}