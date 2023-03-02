package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.Chat
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.usecase.GetChatByMemberUseCase
import com.example.domain.usecase.GetMessagesUseCase
import com.example.domain.usecase.InsertMessageUseCase
import com.example.messenger.presentation.adapter.ChatMessageListAdapter
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class ChatViewModel @Inject constructor(
    private val insertMessageUseCase: InsertMessageUseCase,
    private val getChatByMemberUseCase: GetChatByMemberUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    val chatMessageListAdapter: ChatMessageListAdapter,
    @Named("CurrentUserId") val currentUserId: String,
): ViewModel() {
    var messageBody: MutableLiveData<String> = MutableLiveData("")
    var toUser: MutableLiveData<User> = MutableLiveData()
    var messageList: MutableLiveData<MutableList<Message>?> = MutableLiveData(mutableListOf())
    var chat: MutableLiveData<Chat?> = MutableLiveData()

    init {
        chatMessageListAdapter.currentUserId = currentUserId
    }

    fun setMessageListAdapter() {
        chatMessageListAdapter.differ.submitList(messageList.value)
    }

    fun initMessageListener() {
        viewModelScope.launch(Dispatchers.IO) {
            getMessagesUseCase.execute(chatId = chat.value?.id!!).collect { response ->
                if (response is Response.Success) messageList.postValue(response.data)
            }
        }
    }

    fun initChat() {
        viewModelScope.launch(Dispatchers.IO) {
            getChatByMemberUseCase.execute(toUser.value?.id!!).collect { response ->
                if (response is Response.Success) chat.postValue(response.data)
            }
        }
    }

    fun insertMessage() {
        if (chat.value?.id?.isNotBlank() == true && messageBody.value?.isNotBlank() == true)
            viewModelScope.launch(Dispatchers.IO) {
                insertMessageUseCase.execute(
                    chat.value?.id!!,
                    Message(
                        sender = currentUserId,
                        body = messageBody.value,
                        timestamp = Timestamp.now().seconds.toString(),
                    )
                ).collect { response ->
                    if (response is Response.Success)
                        messageBody.postValue("")
                    else if (response is Response.Fail)
                        println(response.e.message)
                }
            }
    }
}