package com.example.messenger.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.app.App
import com.example.messenger.databinding.FragmentUsersBinding
import com.example.messenger.di.ViewModelFactory
import com.example.messenger.presentation.activity.MessengerActivity
import com.example.messenger.presentation.viewmodel.UsersViewModel
import javax.inject.Inject

class UsersFragment : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: UsersViewModel
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MessengerActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[UsersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observers()
    }

    private fun observers() {
        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            viewModel.setUserListAdapter()
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message != "") {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.messageClear()
            }
        }

        viewModel.navigationAction.observe(viewLifecycleOwner) { navigationAction ->
            when (navigationAction) {
                "Back" -> {
                    findNavController().popBackStack()
                    viewModel.navigationActionClear()
                }
            }
        }

    }
}