package com.example.messenger.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Message
import com.example.messenger.databinding.RowChatMessageBinding
import com.example.messenger.presentation.viewholder.ChatMessageListViewHolder
import javax.inject.Inject

class ChatMessageListAdapter @Inject constructor(): RecyclerView.Adapter<ChatMessageListViewHolder>() {
    var currentId = ""

    private val differCallback =
        object : DiffUtil.ItemCallback<Message>(){
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowChatMessageBinding.inflate(layoutInflater)
        return ChatMessageListViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ChatMessageListViewHolder, position: Int) {
        holder.bind(message = differ.currentList[position], currentId = currentId)
    }
}