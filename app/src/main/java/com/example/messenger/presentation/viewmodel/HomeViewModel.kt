package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.*
import com.example.messenger.presentation.model.HomeChatItem
import com.example.domain.model.*
import com.example.domain.usecase.GetChatUseCase
import com.example.domain.usecase.GetCurrentUserUseCase
import com.example.domain.usecase.GetRecentMessageUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.messenger.presentation.adapter.HomeChatsListAdapter
import com.example.messenger.presentation.model.mutate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getChatUseCase: GetChatUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getRecentMessageUseCase: GetRecentMessageUseCase,
    val chatsListAdapter: HomeChatsListAdapter,
): ViewModel() {
    var user: LiveData<CurrentUser>  = liveData<CurrentUser>(Dispatchers.IO) {
        getCurrentUserUseCase.execute().collect { if (it is Response.Success) emit(it.data) }
    }

    private var _chatList: MutableLiveData<ArrayList<HomeChatItem>> = MutableLiveData(arrayListOf())
    val chatList: LiveData<ArrayList<HomeChatItem>> = _chatList

    private val _navigationAction: MutableLiveData<String> = MutableLiveData("")
    var navigationAction: LiveData<String> = _navigationAction

    fun navigateToCurrentUserProfile() {
        _navigationAction.value = "CurrentUserProfileFragment"
    }

    fun navigateToUserList() {
        _navigationAction.value = "UsersFragment"
    }

    fun navigationActionClear() {
        _navigationAction.value = ""
    }

    fun getChatList() {
        user.value?.chats?.forEach { chatId ->
            if (_chatList.value?.none { it.chatId == chatId } == true)
                _chatList.value?.add(HomeChatItem(chatId = chatId, toUser = null, message = null))

            viewModelScope.launch {
                getUserUseCase.execute(chatId.replace(user.value?.id.toString(), "")).collect { response ->
                    if (response is Response.Success)
                        _chatList.mutate {
                            find { it.chatId == chatId}?.also { it.toUser = response.data }
                        }
                }
            }

            viewModelScope.launch {
                getRecentMessageUseCase.execute(chatId).collect { response ->
                    if (response is Response.Success)
                        _chatList.mutate {
                            find { it.chatId == chatId}?.also { it.message = response.data }
                        }
                }
            }
        }
    }

    fun setChatsListAdapter() {
        chatsListAdapter.differ.submitList(_chatList.value)
    }
}