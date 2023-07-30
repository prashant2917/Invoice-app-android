package com.pocket.invoiceapp.register.presentation

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
import com.pocket.invoiceapp.databinding.FragmentRegisterBinding
import com.pocket.invoiceapp.intefaces.EditTextWatcher
import com.pocket.invoiceapp.register.data.RegisterUser

import com.pocket.invoiceapp.validator.ValidationEvents
import com.pocket.invoiceapp.validator.ValidationViewModel
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val viewModel: AuthViewModel by activityViewModels()
    private val validationViewModel: ValidationViewModel by activityViewModels()
    private val LOG_TAG = RegisterFragment::class.java.name


    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.addTextChangedListener(EditTextWatcher(binding.textInputName))
        binding.etEmail.addTextChangedListener(EditTextWatcher(binding.textInputEmail))
        binding.etPassword.addTextChangedListener(EditTextWatcher(binding.textInputPassword))
        binding.etConfirmPassword.addTextChangedListener(EditTextWatcher(binding.textInputConfirmPassword))
        listenToValidatorChannel()
        listenToAuthenticatorChannel()

        binding.btnSubmit.setOnClickListener {
            hideSoftKeyboard(it)
            val registerUser = RegisterUser()
            registerUser.apply {
                this.name = binding.etName.text.toString()
                this.email = binding.etEmail.text.toString()
                this.password = binding.etPassword.text.toString()
                this.confirmPassword = binding.etConfirmPassword.text.toString()

            }
            validationViewModel.doRegistrationValidation(registerUser)


        }
    }

    private fun listenToValidatorChannel() {
        viewLifecycleOwner.lifecycleScope.launch {
            validationViewModel.validationEventsFlow.collect { event ->
                when (event) {
                    is ValidationEvents.SendErrorMessage -> {

                        bindMessageToTextInputLayout(event.message)
                    }

                    is ValidationEvents.Success -> {
                        val user = event.user as RegisterUser

                        viewModel.signUpUser(user.email, user.password)
                    }

                }
            }


        }
    }

    private fun listenToAuthenticatorChannel() {
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
                        showToast(event.message)
                        navController.navigate(R.id.action_register_fragment_to_login_fragment)
                    }

                    is AuthenticatorEvent.SignOut -> {


                    }
                }
            }
        }
    }

    override fun registerObservers() {
        TODO("Not yet implemented")
    }

    private fun bindMessageToTextInputLayout(message: String) {
        when (message) {
            activity?.getString(R.string.error_invalid_name) -> {
                binding.textInputName.error = activity?.getString(R.string.error_invalid_name)
            }

            activity?.getString(R.string.error_invalid_email) -> {
                binding.textInputEmail.error = activity?.getString(R.string.error_invalid_email)
            }

            activity?.getString(R.string.error_invalid_password) -> {
                binding.textInputPassword.error =
                    activity?.getString(R.string.error_invalid_password)
            }

            activity?.getString(R.string.error_invalid_confirm_password) -> {
                binding.textInputConfirmPassword.error =
                    activity?.getString(R.string.error_invalid_password)
            }

            activity?.getString(R.string.error_both_password) -> {
                binding.textInputConfirmPassword.error =
                    activity?.getString(R.string.error_both_password)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}