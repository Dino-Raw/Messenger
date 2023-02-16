package com.example.messenger.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.User
import com.example.messenger.databinding.RowUserBinding
import com.example.messenger.presentation.viewholder.UserListViewHolder
import javax.inject.Inject

class UserListAdapter @Inject constructor(): RecyclerView.Adapter<UserListViewHolder>() {
    var userList = ArrayList<User>()
        @SuppressLint("NotifyDataSetChanged")
        set(userList: ArrayList<User>) {
            this.userList.clear()
            this.userList.addAll(userList)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowUserBinding.inflate(layoutInflater)
        return UserListViewHolder(binding)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}