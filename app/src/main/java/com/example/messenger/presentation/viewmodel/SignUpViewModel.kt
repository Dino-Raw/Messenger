package com.example.messenger.presentation.viewmodel

import android.view.View
import androidx.lifecycle.*
import com.example.domain.model.Response
import com.example.domain.model.UserAuth
import com.example.domain.usecase.SignUpUseCase
import com.example.messenger.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
): ViewModel() {
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val confirmPassword: MutableLiveData<String> = MutableLiveData("")

    private val _isVisiblyProgressBar: MutableLiveData<Int> = MutableLiveData(View.GONE)
    var isVisiblyProgressBar: LiveData<Int> = _isVisiblyProgressBar

    private val _navigationAction: MutableLiveData<String> = MutableLiveData(null)
    var navigationAction: LiveData<String> = _navigationAction

    private val _message: MutableLiveData<String> = MutableLiveData()
    var message: LiveData<String> = _message

    val isEnabledSignUp = MediatorLiveData<Boolean>().also { mediator ->
        val getIsEnabledSignUpValue = {
            email.value?.isNotBlank()!! && password.value?.isNotBlank()!! && password.value == confirmPassword.value
        }

        mediator.addSource(email) { mediator.postValue(getIsEnabledSignUpValue.invoke()) }
        mediator.addSource(password) { mediator.postValue(getIsEnabledSignUpValue.invoke()) }
        mediator.addSource(confirmPassword) { mediator.postValue(getIsEnabledSignUpValue.invoke()) }
    }

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.execute(UserAuth(email.value!!, password.value!!)).collect { response ->
                parseSignUpResponse(response)
            }
        }
    }

    private fun parseSignUpResponse(response: Response<Boolean>) {
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

    fun navigateToSignIn() {
        _navigationAction.postValue("SignInFragment")
    }

    private fun navigateToHome() {
        _navigationAction.postValue("MessengerActivity")
    }

    fun navigationActionClear() {
        _navigationAction.value = ""
    }
}