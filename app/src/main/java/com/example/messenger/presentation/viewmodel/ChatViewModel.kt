package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.usecase.GetChatListenerUseCase
import com.example.domain.usecase.InsertMessageUseCase
import com.example.messenger.presentation.adapter.ChatMessageListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class ChatViewModel @Inject constructor(
    private val insertMessageUseCase: InsertMessageUseCase,
    private val getChatListenerUseCase: GetChatListenerUseCase,
    val chatMessageListAdapter: ChatMessageListAdapter,
    @Named("CurrentUserId") val currentUserId: String,
): ViewModel() {
    var messageBody: MutableLiveData<String> = MutableLiveData("")
    var toUser: MutableLiveData<User> = MutableLiveData()
    var chatId: MutableLiveData<String> = MutableLiveData()
    var messageList: MutableLiveData<MutableList<Message>> = MutableLiveData(mutableListOf())

    fun initFields(user: User) {
        toUser.value = user

        chatId.value = listOf(currentUserId, toUser.value?.id.toString())
            .sorted()
            .joinToString(prefix = "", postfix = "", separator = "")

        chatMessageListAdapter.currentUserId = currentUserId

        viewModelScope.launch(Dispatchers.IO) {
            getChatListenerUseCase.execute(chatId = chatId.value!!).collect {
                if (it is Response.Success && messageList.value?.contains(it.data) != true) {
                    messageList.postValue(messageList.value?.apply { add(0, it.data) })
                }
            }
        }
    }

    fun setMessageListAdapter() {
        chatMessageListAdapter.differ.submitList(messageList.value)
    }

    fun insertMessage() {
        if (chatId.value?.isNotBlank() == true && messageBody.value?.isNotBlank() == true)
            viewModelScope.launch(Dispatchers.IO) {
                insertMessageUseCase.execute(
                    Message(
                        userId = currentUserId,
                        body = messageBody.value,
                        timestamp = "0000000000",
                        chatId = chatId.value
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