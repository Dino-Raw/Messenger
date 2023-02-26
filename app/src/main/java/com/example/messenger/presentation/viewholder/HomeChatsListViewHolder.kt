package com.example.messenger.presentation.viewholder

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.presentation.model.HomeChatItem
import com.example.messenger.R
import com.example.messenger.databinding.RowChatBinding
import com.squareup.picasso.Picasso

class HomeChatsListViewHolder(private val binding: RowChatBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(homeChatItem: HomeChatItem) {
        if (homeChatItem.toUser?.imagePath?.isNotBlank() == true)
            Picasso.get().load(homeChatItem.toUser?.imagePath).into(binding.chatImage)

        binding.name = homeChatItem.toUser?.name
        binding.message = homeChatItem.message?.body
        binding.executePendingBindings()

        itemView.setOnClickListener {
            binding.root.findNavController().navigate (
                R.id.action_fragment_home_to_fragment_chat,
                bundleOf("user" to homeChatItem.toUser)
            )
        }
    }
}
