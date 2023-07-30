package com.pocket.invoiceapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.authenticator.AuthViewModel
import com.pocket.invoiceapp.authenticator.AuthenticatorEvent
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()

    override fun registerObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.authenticatorEventsFlow.collect { event ->
                when (event) {
                    is AuthenticatorEvent.Loading -> {
                        if (event.isLoading) {
                            binding.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.progressBar.visibility = View.GONE
                        }

                    }

                    is AuthenticatorEvent.Error -> {
                        if (event.errorMessage.isEmpty()) {
                            binding.tvError.visibility = View.GONE
                        } else {
                            binding.tvError.visibility = View.VISIBLE
                            binding.tvError.text = event.errorMessage
                        }
                    }

                    is AuthenticatorEvent.Success -> {

                    }

                    is AuthenticatorEvent.SignOut -> {
                        if (event.isSignOut) {
                            navController.navigate(R.id.action_home_fragment_to_login_fragment)
                        } else {
                            binding.tvError.text = event.message
                            binding.tvError.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
        registerObservers()
    }
}