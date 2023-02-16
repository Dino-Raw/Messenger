package com.example.messenger.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.User
import com.example.messenger.databinding.RowUserBinding

class UserListViewHolder(private val binding: RowUserBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: User) {
        binding.name = user.name
    }
}