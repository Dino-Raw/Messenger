package com.example.messenger.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.RowProfileImageBinding
import com.example.messenger.presentation.viewholder.ProfileImageViewPagerViewHolder
import javax.inject.Inject

class ProfileImageViewPagerAdapter @Inject constructor(): RecyclerView.Adapter<ProfileImageViewPagerViewHolder>(){
    var imageUrlList: ArrayList<String> = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(imageUrlList: ArrayList<String>) {
        this.imageUrlList.clear()
        this.imageUrlList.addAll(imageUrlList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProfileImageViewPagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowProfileImageBinding.inflate(layoutInflater, parent, false)
        return ProfileImageViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int = imageUrlList.size

    override fun onBindViewHolder(holder: ProfileImageViewPagerViewHolder, position: Int) {
        holder.bind(imageUrl = imageUrlList[position])
    }
}