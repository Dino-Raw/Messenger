package com.example.messenger.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.model.User
import com.example.messenger.R
import com.example.messenger.app.App
import com.example.messenger.databinding.FragmentChatBinding
import com.example.messenger.di.ViewModelFactory
import com.example.messenger.presentation.activity.MessengerActivity
import com.example.messenger.presentation.viewmodel.ChatViewModel
import javax.inject.Inject

class ChatFragment: Fragment(R.layout.fragment_chat) {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ChatViewModel
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MessengerActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ChatViewModel::class.java].apply {
            initFields(requireArguments().getSerializable("user") as User)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        viewModel.messageList.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                viewModel.setMessageListAdapter()
            }
        }
    }
}