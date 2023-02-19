package com.example.messenger.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Message
import com.example.messenger.databinding.RowChatMessageBinding

class ChatMessageListViewHolder(private val binding: RowChatMessageBinding, private val currentUserId: String): RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message) {
        binding.message = message.body
        binding.isCurrentUser = currentUserId == message.userId
        binding.executePendingBindings()
    }
}