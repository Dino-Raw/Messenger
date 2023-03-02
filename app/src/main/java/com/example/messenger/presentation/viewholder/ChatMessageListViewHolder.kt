package com.example.messenger.presentation.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Message
import com.example.messenger.databinding.RowChatMessageBinding
import java.text.SimpleDateFormat
import java.util.Date

class ChatMessageListViewHolder(private val binding: RowChatMessageBinding, private val currentUserId: String)
: RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SimpleDateFormat")
    fun bind(message: Message) {
        binding.message = message.body.toString()
        binding.isCurrentUser = currentUserId.toString() == message.sender.toString()
        binding.time = SimpleDateFormat("HH:mm").format(Date(message.timestamp?.toLong()!! * 1000))
        binding.executePendingBindings()
    }
}