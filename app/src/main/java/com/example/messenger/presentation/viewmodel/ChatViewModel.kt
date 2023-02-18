package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.usecase.GetChatListenerUseCase
import com.example.domain.usecase.GetCurrentUserIdUseCase
import com.example.domain.usecase.InsertMessageUseCase
import com.example.messenger.presentation.adapter.ChatMessageListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val insertMessageUseCase: InsertMessageUseCase,
    private val getChatListenerUseCase: GetChatListenerUseCase,
    val chatMessageListAdapter: ChatMessageListAdapter,
): ViewModel() {
    var userId: LiveData<String> = initCurrentUserId()
    var messageBody: MutableLiveData<String> = MutableLiveData("")
    var toUser: MutableLiveData<User> = MutableLiveData(null)

    var chatId: LiveData<String> = initChatId()
    var messageChatListener: MutableLiveData<Message?> = MutableLiveData()
    var messageList: MutableLiveData<MutableList<Message>> = MutableLiveData(mutableListOf())

    fun setMessageListAdapter() {
        chatMessageListAdapter.differ.submitList(messageList.value)
    }

    fun sendMessage() {
        if (chatId.value?.isNotBlank() == true)
            viewModelScope.launch(Dispatchers.IO) {
                insertMessageUseCase.execute(
                    Message (
                        userId = userId.value,
                        body = messageBody.value,
                        timestamp = "0000000000",
                        chatId = chatId.value
                    )).collect { response ->
                        if (response is Response.Success)
                            messageBody.postValue("")
                        else if (response is Response.Fail)
                            println(response.e.message)
                    }
            }
    }

    private fun initChatId() : LiveData<String> =
        MediatorLiveData<String>().also { mediator ->
            val chatId = arrayListOf<String>()
            fun createChatId() =
                chatId.sorted().joinToString(prefix = "", postfix = "", separator = "")
            fun initChatId() { if (chatId.size == 2) mediator.postValue(createChatId()) }

            mediator.addSource(toUser) {
                if (it.id?.isNotBlank() == true) {
                    it.id?.let { it1 -> chatId.add(it1) }
                    initChatId()
                }
            }

            mediator.addSource(userId) {
                if (it.isNotBlank()) {
                    chatId.add(it)
                    initChatId()
                }
            }
        }

    fun initChatListener() {
        viewModelScope.launch(Dispatchers.IO) {
            getChatListenerUseCase.execute(chatId = chatId.value!!)
                .collect {
                    if (it is Response.Success) {
                        messageChatListener.postValue(it.data)
                        if (messageList.value?.contains(it.data) != true)
                            messageList.value?.add(it.data)
                    }
                }
        }
    }

    private fun initCurrentUserId() = liveData(Dispatchers.IO) {
        getCurrentUserIdUseCase.execute().collect { response ->
            if (response is Response.Success) {
                emit(response.data)
                chatMessageListAdapter.currentId = response.data
            }
        }
    }