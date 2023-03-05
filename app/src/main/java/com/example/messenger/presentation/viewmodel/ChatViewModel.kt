package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.Chat
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.usecase.*
import com.example.messenger.presentation.adapter.ChatMessageListAdapter
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class ChatViewModel @Inject constructor(
    private val insertMessageUseCase: InsertMessageUseCase,
    private val getChatByMemberUseCase: GetChatByMemberUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val updateChatUseCase: UpdateChatUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getChatByIdUseCase: GetChatByIdUseCase,
    private val insertChatUseCase: InsertChatUseCase,
    val chatMessageListAdapter: ChatMessageListAdapter,
    @Named("CurrentUserId") val currentUserId: String,
): ViewModel() {
    var messageBody: MutableLiveData<String> = MutableLiveData("")
    var toUser: MutableLiveData<User?> = MutableLiveData()

    private var chat: MutableLiveData<Chat?> = MutableLiveData()

    val messageList: MutableLiveData<ArrayList<Message>?> =
        MediatorLiveData<ArrayList<Message>?>().apply { addSource(chat) { initMessageListener() } }

    fun initToUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase.execute(userId = userId).collect { response ->
                if (response is Response.Success) toUser.postValue(response.data)
            }
        }
    }

    fun insertMessage() {
        if (messageBody.value?.isNotBlank() == true)
            viewModelScope.launch(Dispatchers.IO) {
                if (chat.value == null)
                    withContext(Dispatchers.Main) { insertChat() }

                if (chat.value != null)
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

    fun setMessageListAdapter() {
        chatMessageListAdapter.differ.submitList(messageList.value)
    }

    fun initChatListenerByChatId(chatId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getChatByIdUseCase.execute(chatId = chatId).collect { response ->
                if (response is Response.Success) chat.postValue(response.data)
            }
        }
    }

    fun initChatListenerByUserId(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getChatByMemberUseCase.execute(userId).collect { response ->
                if (response is Response.Success) chat.postValue(response.data)
            }
        }
    }

    private fun updateChat(message: Message) {
        viewModelScope.launch(Dispatchers.IO) {
            updateChatUseCase.execute(chat = chat.value.apply {
                this?.messageId = message.id
                this?.messageBody = message.body
                this?.messageSender = message.sender
                this?.messageTimestamp = message.timestamp
            } as Chat).collect()
        }
    }

    private fun initMessageListener() {
        viewModelScope.launch(Dispatchers.IO) {
            getMessagesUseCase.execute(chatId = chat.value?.id!!).collect { response ->
                if (response is Response.Success) {
                    messageList.postValue(response.data)
                    updateChat(response.data.first())
                }
            }
        }
    }

    private fun insertChat() {
        viewModelScope.launch(Dispatchers.IO) {
            insertChatUseCase.execute(toUser.value?.id!!).collect { response ->
                if (response is Response.Success) {
                    chat.postValue(response.data)
                    insertMessage()
                }
            }
        }
    }
}