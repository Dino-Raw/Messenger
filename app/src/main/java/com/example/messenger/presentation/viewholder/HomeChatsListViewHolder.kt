package com.example.messenger.presentation.viewholder

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Chat
import com.example.messenger.R
import com.example.messenger.databinding.RowChatBinding
import com.example.messenger.presentation.util.transform
import com.google.firebase.Timestamp
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class HomeChatsListViewHolder(
    private val binding: RowChatBinding,
    private val currentUserId: String,
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SimpleDateFormat")
    fun bind(chat: Chat) {
        if (chat.imagePath?.isNotBlank() == true)
            Picasso.get()
                .load(chat.imagePath)
                .placeholder(R.drawable.ic_account_24)
                .transform(transform)
                .into(binding.chatImage)

        binding.name = chat.name
        binding.message = chat.messageBody
        binding.time = getTimeDifference(chat.messageTimestamp?.toLong())
        binding.executePendingBindings()

        itemView.setOnClickListener {
            binding.root.findNavController().navigate(
                R.id.action_fragment_home_to_fragment_chat,
                bundleOf(
                    "chatId" to chat.id,
                    "userId" to chat.members?.filter { it != currentUserId }?.get(0)
                )
            )
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeDifference(time: Long?): String {
        if (time == null) return ""
        val diff = Timestamp.now().seconds - time
        return when (diff) {
            in 0..59 -> { "$diff sec" } // количество секунд
            in 60..3599 -> { (diff / 60).toString() + " min" } // количество минут
            in 3600..86399 -> { (diff / 3600).toString() + " hours" } // количество часов
            in 86400..31535999 -> { (diff / 86400).toString() + " days" } // количество дней
            else -> { (diff / 31536000).toString() + " years" }
        }
    }
}
