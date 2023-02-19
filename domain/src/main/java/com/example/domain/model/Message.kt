package com.example.domain.model

data class Message(
    var id: String? = null,
    var userId: String? = null,
    var body: String? = null,
    var timestamp: String? = null,
    var chatId: String? = null,
)