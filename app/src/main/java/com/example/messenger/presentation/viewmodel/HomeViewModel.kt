package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.*
import com.example.messenger.presentation.model.HomeChatItem
import com.example.domain.model.*
import com.example.domain.usecase.*
import com.example.messenger.presentation.adapter.HomeChatsListAdapter
import com.example.messenger.presentation.model.mutate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
//    private val getChatUseCase: GetChatUseCase,
//    private val getUserUseCase: GetUserUseCase,
//    private val getRecentMessageUseCase: GetRecentMessageUseCase,
//    private val getChatListUseCase: GetChatListUseCase,
    val chatsListAdapter: HomeChatsListAdapter,
): ViewModel() {
    var user: LiveData<CurrentUser>  = liveData<CurrentUser>(Dispatchers.IO) {
        getCurrentUserUseCase.execute().collect { if (it is Response.Success) emit(it.data) }
    }

    private var _chatList: MutableLiveData<ArrayList<Chat>> = MutableLiveData(arrayListOf())
    val chatList: LiveData<ArrayList<Chat>> = _chatList

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
//        user.value?.chats?.forEach { chatId ->
//            if (_chatList.value?.none { it.id == chatId } == true)
//                _chatList.value?.add(Chat(id = chatId, toUser = null, recentMessage = null))
//
//            viewModelScope.launch {
//                getUserUseCase.execute(chatId.replace(user.value?.id.toString(), "")).collect { response ->
//                    if (response is Response.Success)
//                        _chatList.mutate {
//                            find { it.id == chatId}?.also { it.toUser = response.data }
//                        }
//                }
//            }
//
//            viewModelScope.launch {
//                getRecentMessageUseCase.execute(chatId).collect { response ->
//                    if (response is Response.Success)
//                        _chatList.mutate {
//                            find { it.id == chatId}?.also { it.recentMessage = response.data }
//                        }
//                }
//            }
//        }
    }

//    fun getChatList() {
//        viewModelScope.launch {
//            getChatListUseCase.execute(user.value?.chats!!).collect { response ->
//                if (response is Response.Success) _chatList.value = response.data!!
//            }
//        }
//    }

    fun setChatsListAdapter() {
        chatsListAdapter.differ.submitList(_chatList.value)
    }
}