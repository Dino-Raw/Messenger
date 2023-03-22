package com.example.domain.model


data class CurrentUser (
    var email: String? = null,
    var name: String? = null,
    var bio: String? = null,
    var timeStatus: String? = null,
    var imagePath: ArrayList<String>? = null,
    var id: String? = null,
    var chats: ArrayList<String>? = null, // ChatId
    var createAt: String? = null,
)