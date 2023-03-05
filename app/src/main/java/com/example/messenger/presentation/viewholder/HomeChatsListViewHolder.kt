package com.example.messenger.presentation.viewholder

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Chat
import com.example.messenger.presentation.model.HomeChatItem
import com.example.messenger.R
import com.example.messenger.databinding.RowChatBinding
import com.squareup.picasso.Picasso

class HomeChatsListViewHolder(private val binding: RowChatBinding, private val currentUserId: String)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(chat: Chat) {
        if (chat.imagePath?.isNotBlank() == true)
            Picasso.get().load(chat.imagePath).into(binding.chatImage)

        binding.name = chat.name
        binding.message = chat.messageBody
        binding.executePendingBindings()

        itemView.setOnClickListener {
            binding.root.findNavController().navigate (
                R.id.action_fragment_home_to_fragment_chat,
                bundleOf(
                    "chatId" to chat.id,
                    "userId" to chat.members?.filter { it != currentUserId }?.get(0)
                )
            )
        }
    }
}
