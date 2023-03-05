package com.example.messenger.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.app.App
import com.example.messenger.databinding.FragmentHomeBinding
import com.example.messenger.di.ViewModelFactory
import com.example.messenger.presentation.activity.MessengerActivity
import com.example.messenger.presentation.viewmodel.HomeViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class HomeFragment: Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MessengerActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observers()
    }

    private fun observers() {
        viewModel.navigationAction.observe(viewLifecycleOwner) { navigationAction ->
            when (navigationAction) {
                "UsersFragment" -> {
                    findNavController().navigate(R.id.action_fragment_home_to_fragment_users)
                    viewModel.navigationActionClear()
                }
                else -> {}
            }
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.imagePath?.isNotBlank() == true)
                Picasso.get().load(user.imagePath).into(binding.userBtn)
        }

        viewModel.userList.observe(viewLifecycleOwner) {
            viewModel.setChatsListAdapter()
        }
    }
}