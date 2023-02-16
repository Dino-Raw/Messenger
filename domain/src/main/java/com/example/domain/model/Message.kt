package com.example.domain.model

data class Message (
    var id: String? = null,
    var userId: String? = null, // UserId
    var message: String? = null,
    var timestamp: String? = null,
)