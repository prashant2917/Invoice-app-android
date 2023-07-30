package com.pocket.invoiceapp.login.presentation

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
import com.pocket.invoiceapp.databinding.FragmentLoginBinding
import com.pocket.invoiceapp.intefaces.EditTextWatcher
import com.pocket.invoiceapp.login.data.LoginUser
import com.pocket.invoiceapp.validator.ValidationEvents
import com.pocket.invoiceapp.validator.ValidationViewModel
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null

    private val viewModel : AuthViewModel by activityViewModels()
    private val validationViewModel : ValidationViewModel by  activityViewModels ()
    private val  LOG_TAG = LoginFragment::class.java.name
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etEmail.addTextChangedListener(EditTextWatcher(binding.textInputEmail))
        binding.etPassword.addTextChangedListener(EditTextWatcher(binding.textInputPassword))
       // viewModel.getCurrentUser()
        listenToValidatorChannel()
         listenToAuthenticatorChannel()
        registerObservers()
        binding.btnSubmit.setOnClickListener {
          //hideSoftKeyboard(view)
            val loginUser = LoginUser()
            loginUser.apply {
                this.email = binding.etEmail.text.toString()
                this.password = binding.etPassword.text.toString()
            }
            validationViewModel.doLoginValidation(loginUser)
        }

        binding.btnSignup.setOnClickListener {

            navController.navigate(R.id.action_login_fragment_to_register_fragment)
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
                        val user = event.user as LoginUser
                        viewModel.signInUser(user.email, user.password)
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
                        showToast(getString(R.string.user_login_success))
                        navController.navigate(R.id.action_login_fragment_to_home_fragment)
                    }

                    is AuthenticatorEvent.SignOut -> {


                    }
                }
            }
        }
    }



    override fun registerObservers() {
     /* viewModel.currentUser.observe(viewLifecycleOwner) { firebaseUser ->
          if (firebaseUser == null) {
                showToast("User is not login")
          }
          else {
              showToast("User is  login")
          }
      }*/
    }
    private fun bindMessageToTextInputLayout(message :String) {
        when (message) {

            activity?.getString(R.string.error_invalid_email) -> {
                binding.textInputEmail.error = activity?.getString(R.string.error_invalid_email)
            }

            activity?.getString(R.string.error_invalid_password) -> {
                binding.textInputPassword.error =
                    activity?.getString(R.string.error_invalid_password)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}