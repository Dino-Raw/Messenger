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
import com.example.messenger.di.ViewModelFactory
import com.example.messenger.R
import com.example.messenger.app.App
import com.example.messenger.databinding.FragmentSignUpBinding
import com.example.messenger.presentation.activity.MainActivity
import com.example.messenger.presentation.activity.MessengerActivity
import com.example.messenger.presentation.viewmodel.SignUpViewModel
import javax.inject.Inject

class SignUpFragment: Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SignUpViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MainActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater)
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
                "SignInFragment" -> {
                    findNavController().navigate(R.id.action_fragment_sign_up_to_fragment_sign_in)
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

        viewModel._currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) viewModel.insertNewUser()
        }
    }
}
