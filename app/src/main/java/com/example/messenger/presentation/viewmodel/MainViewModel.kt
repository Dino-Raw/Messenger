package com.example.messenger.presentation.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Response
import com.example.domain.usecase.CheckSignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val checkSignInUseCase: CheckSignInUseCase
): ViewModel() {

    private val _isSignedIn: MutableLiveData<Boolean> = MutableLiveData(false)
    var isSignedIn: MutableLiveData<Boolean> = _isSignedIn

    init {
        checkSignIn()
    }

    private fun checkSignIn() {
        viewModelScope.launch(Dispatchers.IO) {
            checkSignInUseCase.execute().collect { response ->
                if (response is Response.Success) _isSignedIn.postValue(true)
            }
        }
    }
}