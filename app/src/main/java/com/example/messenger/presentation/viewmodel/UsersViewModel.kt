package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.usecase.GetUserListUseCase
import com.example.messenger.presentation.adapter.UserListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    val userListAdapter: UserListAdapter,
): ViewModel() {
    private var _navigationAction: MutableLiveData<String> = MutableLiveData("")
    val navigationAction: LiveData<String> = _navigationAction

    private val _userList: MutableLiveData<ArrayList<User>?> = MutableLiveData()
    val userList: LiveData<ArrayList<User>?> = _userList

    private val _message: MutableLiveData<String> = MutableLiveData("")
    var message: LiveData<String> = _message

    init {
        getUserList()
    }

    fun setUserListAdapter() {
        userListAdapter.userList = _userList.value ?: ArrayList<User>()
    }

    fun messageClear() {
        _message.value = ""
    }

    private fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserListUseCase.execute().collect { response ->
                when (response) {
                    is Response.Loading -> {}
                    is Response.Fail -> {
                        println(response.e.message)
                        _message.postValue(response.e.message)
                    }
                    is Response.Success -> {
                        _userList.postValue(response.data)
                    }
                }
            }
        }
    }

    fun navigateBack() {
        _navigationAction.value = "Back"
    }

    fun navigationActionClear() {
        _navigationAction.value = ""
    }
}