package com.example.messenger.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.presentation.model.HomeChatItem
import com.example.messenger.databinding.RowChatBinding
import com.example.messenger.presentation.viewholder.HomeChatsListViewHolder
import javax.inject.Inject

class HomeChatsListAdapter@Inject constructor(

): RecyclerView.Adapter<HomeChatsListViewHolder>() {
    private val differCallback =
        object : DiffUtil.ItemCallback<HomeChatItem>(){
            override fun areItemsTheSame(oldItem: HomeChatItem, newItem: HomeChatItem): Boolean =
                oldItem.chatId == newItem.chatId

            override fun areContentsTheSame(oldItem: HomeChatItem, newItem: HomeChatItem): Boolean =
                oldItem == newItem
        }

    val differ =
        object : AsyncListDiffer<HomeChatItem>(this, differCallback) {
            override fun submitList(newList: MutableList<HomeChatItem>?) {
                super.submitList(newList)
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChatsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowChatBinding.inflate(layoutInflater)
        return  HomeChatsListViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HomeChatsListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}