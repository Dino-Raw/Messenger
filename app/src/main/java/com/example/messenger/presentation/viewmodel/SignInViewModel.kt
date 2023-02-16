package com.example.messenger.presentation.viewmodel

import android.view.View
import androidx.lifecycle.*
import com.example.domain.model.Response
import com.example.domain.model.UserAuth
import com.example.domain.usecase.CheckSignInUseCase
import com.example.domain.usecase.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val checkSignInUseCase: CheckSignInUseCase,
    private val signInUseCase: SignInUseCase
): ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")

    private val _isVisiblyProgressBar: MutableLiveData<Int> = MutableLiveData(View.GONE)
    var isVisiblyProgressBar: LiveData<Int> = _isVisiblyProgressBar

    private val _navigationAction: MutableLiveData<String> = MutableLiveData("")
    var navigationAction: LiveData<String> = _navigationAction

    private val _message: MutableLiveData<String> = MutableLiveData()
    var message: LiveData<String> = _message

    val isEnabledSignIn = MediatorLiveData<Boolean>().also { mediator ->
        val getIsEnabledSignUpValue = {
            email.value?.isNotBlank()!! && password.value?.isNotBlank()!!
        }

        mediator.addSource(email) { mediator.postValue(getIsEnabledSignUpValue.invoke()) }
        mediator.addSource(password) { mediator.postValue(getIsEnabledSignUpValue.invoke()) }
    }

    fun signIn() {
        viewModelScope.launch(Dispatchers.IO) {
            signInUseCase.execute(UserAuth(email.value!!, password.value!!)).collect { response ->
                parseSignInResponse(response)
            }
        }
    }

    private fun parseSignInResponse(response: Response<Boolean>) {
        when (response) {
            is Response.Loading ->
                _isVisiblyProgressBar.postValue(View.VISIBLE)
            is Response.Fail -> {
                _isVisiblyProgressBar.postValue(View.GONE)
                if (response.e.message?.isNotBlank() == true)
                    _message.postValue(response.e.message)
            }
            is Response.Success -> {
                _isVisiblyProgressBar.postValue(View.GONE)
                navigateToHome()
            }
        }
    }

    fun navigateToSignUp() {
        _navigationAction.postValue("SignUpFragment")
    }

    private fun navigateToHome() {
        _navigationAction.postValue("MessengerActivity")
    }

    fun navigationActionClear() {
        _navigationAction.value = ""
    }
}