package com.example.messenger.presentation.model

import com.example.domain.model.Message
import com.example.domain.model.User

data class HomeChatItem (
    var chatId: String? = null,
    var message: Message? = null,
    var toUser: User? = null,
)