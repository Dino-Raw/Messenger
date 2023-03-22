package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.User
import com.example.messenger.presentation.adapter.ProfileImageViewPagerAdapter
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    val profileImageViewPagerAdapter: ProfileImageViewPagerAdapter,
): ViewModel() {
    var user: MutableLiveData<User> = MutableLiveData()
    private var _navigationAction: MutableLiveData<String> = MutableLiveData("")
    val navigationAction: LiveData<String> = _navigationAction

    fun setAdapter() {
        user.value?.imagePath?.also {  list ->
            profileImageViewPagerAdapter.setData (
                if (list.size > 1) list.reversed() as ArrayList<String> else list
            )
        }
    }

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