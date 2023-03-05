package com.example.messenger.presentation.model

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.mutate(mutator: T.() -> Unit) {
    this.postValue(this.value?.apply(mutator))
}