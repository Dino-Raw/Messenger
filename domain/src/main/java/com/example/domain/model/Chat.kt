package com.example.domain.model

data class Chat (
    var id: String? = null,
    var members: ArrayList<String>? = null,
    var recentMessage: Message? = null,
): java.io.Serializable