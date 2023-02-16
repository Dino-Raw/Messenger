package com.example.domain.model

data class Chat (
    var id: String? = null,
    var createdAt: String? = null,
    var createdBy: String? = null, // UserId
    var name: String? = null,
    var imagePath: String? = null,
    var members: List<String>? = null, // UserId
    var recentMessage: String? = null, // MessageId
)