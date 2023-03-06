package com.example.messenger.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Chat
import com.example.messenger.databinding.RowChatBinding
import com.example.messenger.presentation.viewholder.HomeChatsListViewHolder
import javax.inject.Inject

class HomeChatsListAdapter@Inject constructor(
): RecyclerView.Adapter<HomeChatsListViewHolder>() {
    lateinit var currentUserId: String
    private val differCallback =
        object : DiffUtil.ItemCallback<Chat>(){
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean =
                oldItem == newItem
        }

    val differ =
        object : AsyncListDiffer<Chat>(this, differCallback) {
            override fun submitList(newList: MutableList<Chat>?) {
                super.submitList(newList)
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChatsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowChatBinding.inflate(layoutInflater, parent, false)
        return  HomeChatsListViewHolder(binding, currentUserId)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HomeChatsListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}