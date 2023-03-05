package com.example.domain.model

data class Chat (
    var id: String? = null,
    var name: String? = null,
    var imagePath: String? = null,
    var group: Boolean = false,
    var members: ArrayList<String>? = null,
    var messageId: String? = null,
    var messageBody: String? = null,
    var messageSender: String? = null,
    var messageTimestamp: String? = null,
    var read: Boolean = false,
): java.io.Serializable