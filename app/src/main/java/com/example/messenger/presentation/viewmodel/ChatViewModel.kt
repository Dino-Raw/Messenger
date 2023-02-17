package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Message
import com.example.domain.model.Response
import com.example.domain.model.User
import com.example.domain.usecase.GetCurrentUserIdUseCase
import com.example.domain.usecase.GetRandomStringUseCase
import com.example.domain.usecase.InsertMessageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val getRandomStringUseCase: GetRandomStringUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val insertMessageUseCase: InsertMessageUseCase,
): ViewModel() {
    var messageBody: MutableLiveData<String> = MutableLiveData("")
    var toUser: MutableLiveData<User> = MutableLiveData()
    private val userId: MutableLiveData<String?> = MutableLiveData()
    init {
        getCurrentUserId()
    }

    private fun getCurrentUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrentUserIdUseCase.execute().collect { response ->
                if (response is Response.Success) userId.postValue(response.data)
            }
        }
    }

    fun sendMessage() {
        viewModelScope.launch(Dispatchers.IO) {
            insertMessageUseCase.execute(
                Message (
                    id = getRandomStringUseCase.execute(),
                    userId = userId.value,
                    body = messageBody.value,
                    timestamp = "0000000000",
                    chatId = listOf(userId.value.toString(), toUser.value?.id.toString())
                        .sorted()
                        .joinToString (prefix = "", postfix = "", separator = "")
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