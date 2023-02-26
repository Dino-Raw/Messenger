package com.example.domain.model

data class Chat (
    var id: String? = null,
    var members: ArrayList<String>? = null, // UserId
    var recentMessage: String? = null, // MessageId
)