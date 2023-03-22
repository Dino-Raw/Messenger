package com.example.messenger.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.RowProfileImageBinding
import com.squareup.picasso.Picasso

class ProfileImageViewPagerViewHolder(private val binding: RowProfileImageBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .into(binding.profileImageBackground)

        binding.executePendingBindings()
    }
}