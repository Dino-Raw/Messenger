package com.example.messenger.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Message
import com.example.messenger.databinding.RowChatMessageBinding

class ChatMessageListViewHolder(private val binding: RowChatMessageBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message, currentId: String) {
        binding.message = message.body
        binding.isCurrentUser = currentId == message.userId
        binding.executePendingBindings()
    }
}