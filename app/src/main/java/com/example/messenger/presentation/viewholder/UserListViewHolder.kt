package com.example.messenger.presentation.viewholder

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.User
import com.example.messenger.R
import com.example.messenger.databinding.RowUserBinding

class UserListViewHolder(private val binding: RowUserBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.name = user.name

        itemView.setOnClickListener {
            binding.root.findNavController().navigate(
                R.id.action_fragment_users_to_fragment_profile,
                bundleOf("user" to user)
            )
        }

        binding.executePendingBindings()
    }
}