package com.example.messenger.presentation.fragment

import android.content.Intent
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
import com.example.messenger.databinding.FragmentSignInBinding
import com.example.messenger.di.ViewModelFactory
import com.example.messenger.presentation.activity.MainActivity
import com.example.messenger.presentation.activity.MessengerActivity
import com.example.messenger.presentation.viewmodel.SignInViewModel
import javax.inject.Inject

class SignInFragment: Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SignInViewModel
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MainActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[SignInViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
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
            when(navigationAction) {
                "SignUpFragment" -> {
                    findNavController().navigate(R.id.action_fragment_sign_in_to_fragment_sign_up)
                    viewModel.navigationActionClear()
                }
                "MessengerActivity" -> {
                    startActivity(Intent(requireContext(), MessengerActivity::class.java))
                    viewModel.navigationActionClear()
                }
                else -> {}
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
}