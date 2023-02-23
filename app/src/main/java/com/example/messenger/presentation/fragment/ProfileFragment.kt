package com.example.messenger.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.model.User
import com.example.messenger.R
import com.example.messenger.app.App
import com.example.messenger.databinding.FragmentProfileBinding
import com.example.messenger.di.ViewModelFactory
import com.example.messenger.presentation.activity.MessengerActivity
import com.example.messenger.presentation.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject
import kotlin.math.abs

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MessengerActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[ProfileViewModel::class.java]
        initUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observers()

        binding.profileAppbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            println(verticalOffset)
            if (abs(verticalOffset) in 0..565)
                binding.profileBackBtn.drawable.setTint(resources.getColor(R.color.white))
            else
                binding.profileBackBtn.drawable.setTint(resources.getColor(R.color.black))

        }
    }

    private fun observers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.imagePath?.isNotBlank() == true)
                Picasso.get().load(user.imagePath).into(binding.userImage)
        }

        viewModel.navigationAction.observe(viewLifecycleOwner) { navigationAction ->
            when (navigationAction) {
                "ChatFragment" -> {
                    findNavController().navigate(
                        R.id.action_fragment_profile_to_fragment_chat,
                        bundleOf("user" to viewModel.user.value)
                    )
                    viewModel.navigationActionClear()
                }

                "Back" -> {
                    findNavController().popBackStack()
                    viewModel.navigationActionClear()
                }
            }
        }
    }

    private fun initUser() {
        viewModel.user.value =
            requireArguments().getSerializable("user") as User
    }
}