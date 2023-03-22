package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.Chat
import com.example.domain.model.CurrentUser
import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.usecase.GetChatListUseCase
import com.example.domain.usecase.GetCurrentUserUseCase
import com.example.domain.usecase.GetUserListByIdUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.messenger.presentation.adapter.HomeChatsListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val getUserListByIdUseCase: GetUserListByIdUseCase,
    val chatsListAdapter: HomeChatsListAdapter,
): ViewModel() {
    var user: LiveData<CurrentUser>  = liveData(Dispatchers.IO) {
        getCurrentUserUseCase.execute().collect { response ->
            if (response is Response.Success) {
                emit(response.data)
                chatsListAdapter.currentUserId = response.data.id.toString()
            }
        }
    }

    var chatList: LiveData<ArrayList<Chat>> = liveData(Dispatchers.IO) {
        getChatListUseCase.execute().collect { if (it is Response.Success) emit(it.data) }
    }

    val userIdList get() = chatList.value.let {  chats ->
        chats?.filter { chat -> !chat.group }?.map { chat ->
            chat.members?.filter { it != user.value?.id }!!.get(0)
        } as ArrayList<String>
    }

    var userList: LiveData<ArrayList<User>> = MediatorLiveData<ArrayList<User>>().apply {
        addSource(chatList) { chats ->
            viewModelScope.launch(Dispatchers.IO) {
                getUserListByIdUseCase.execute(userIdList).collect { response ->
                    if (response is Response.Success) postValue(response.data)
                }
            }
        }
    }

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

    fun setChatsListAdapter() {
        chatList.value?.forEach { chat ->
            userList.value?.forEach { user ->
                if (chat.members?.contains(user.id) == true) {
                    chat.name = user.name
                    chat.imagePath = user.imagePath?.last()
                }
            }
        }

        chatsListAdapter.differ.submitList(chatList.value)
    }
}