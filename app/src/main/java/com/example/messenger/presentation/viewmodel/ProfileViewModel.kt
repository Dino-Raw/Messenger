package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.User
import javax.inject.Inject

class ProfileViewModel @Inject constructor(): ViewModel() {
    var user: MutableLiveData<User> = MutableLiveData()
    private var _navigationAction: MutableLiveData<String> = MutableLiveData("")
    val navigationAction: LiveData<String> = _navigationAction

    fun navigateToChat() {
        _navigationAction.value = "ChatFragment"
    }

    fun navigateBack() {
        _navigationAction.value = "Back"
    }

    fun navigationActionClear() {
        _navigationAction.value = ""
    }
}