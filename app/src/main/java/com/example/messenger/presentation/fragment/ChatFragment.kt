package com.example.messenger.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.messenger.R
import com.example.messenger.app.App
import com.example.messenger.databinding.FragmentChatBinding
import com.example.messenger.di.ViewModelFactory
import com.example.messenger.presentation.activity.MessengerActivity
import com.example.messenger.presentation.util.transform
import com.example.messenger.presentation.viewmodel.ChatViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject


class ChatFragment: Fragment(R.layout.fragment_chat) {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ChatViewModel
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MessengerActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java].apply {
            requireArguments().apply {
                val userId = getString("userId")
                val chatId = getString("chatId")

                initToUser(userId as String)
                initChatListener(chatId = chatId, userId = userId)
            }

            chatMessageListAdapter.currentUserId = currentUserId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observers()
        binding.chatBackBtn.setOnClickListener { findNavController().popBackStack() }
    }

    private fun observers() {
        viewModel.toUser.observe(viewLifecycleOwner) { user ->
            if (user?.imagePath?.isNotEmpty() == true)
                Picasso.get()
                    .load(user.imagePath?.last())
                    .placeholder(R.drawable.ic_account_24)
                    .transform(transform)
                    .into(binding.toUserImage)
        }

        viewModel.messageList.observe(viewLifecycleOwner) { messageList ->
            viewModel.setMessageListAdapter()
        }

        viewModel.chatMessageListAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.messagesRecyclerview.layoutManager?.scrollToPosition(0)
            }
        })
    }
}