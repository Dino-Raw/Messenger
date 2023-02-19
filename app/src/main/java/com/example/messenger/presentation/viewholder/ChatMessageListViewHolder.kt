package com.example.messenger.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Message
import com.example.messenger.databinding.RowChatMessageBinding
import java.sql.Timestamp

class ChatMessageListViewHolder(private val binding: RowChatMessageBinding, private val currentUserId: String): RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message) {
        binding.message = message.body
        binding.isCurrentUser = currentUserId == message.userId
        binding.time = message.timestamp
        binding.executePendingBindings()
    }
}