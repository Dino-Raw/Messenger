package com.example.domain.model

import java.security.Timestamp

data class CurrentUser (
    var email: String? = null,
    var name: String? = null,
    var bio: String? = null,
    var timeStatus: String? = null,
    var imagePath: String? = null,
    var id: String? = null,
    var chats: ArrayList<String>? = null, // ChatId
    var createAt: String? = null,
)