package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(): ViewModel() {
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
}