package com.pocket.invoiceapp.register.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.blogger.Blogger
import com.pocket.invoiceapp.databinding.FragmentRegisterBinding
import com.pocket.invoiceapp.intefaces.EditTextWatcher
import com.pocket.invoiceapp.register.event.RegisterEvents
import com.pocket.invoiceapp.register.viewmodel.RegisterViewModel
import com.pocket.invoiceapp.validator.ValidationEvents
import com.pocket.invoiceapp.validator.ValidationViewModel


class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val validationViewModel: ValidationViewModel by activityViewModels()
    private val registerViewModel: RegisterViewModel by activityViewModels()
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
        setUpUI()
        setUpListeners()
        observeLiveData()


    }

    private fun observeValidationLiveData()  {
        validationViewModel.validationLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ValidationEvents.SendErrorMessage -> {

                    bindMessageToTextInputLayout(event.message)
                }

                is ValidationEvents.Success -> {
                    val user = event.user

                   binding.progressBar.visibility = View.VISIBLE
                    registerViewModel.registerUserInDatabase(user)
                }

            }
        }
    }



    private fun observeRegistrationLiveData()  {
        /*registerViewModel.registerLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is RegisterEvents.Loading -> {
                    Log.d(LOG_TAG, "${event.isLoading}")
                    if (event.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }

                is RegisterEvents.Success -> {
                    Log.d(LOG_TAG, "success")
                    showToast(requireContext().getString(R.string.user_registration_success))
                    navController.navigate(R.id.action_register_fragment_to_login_fragment)
                }

                is RegisterEvents.Error -> {
                    Log.d(LOG_TAG, "failure ${event.error}")
                    showToast(event.error)
                }

            }

        }
*/

        registerViewModel.registerLiveData.observe(viewLifecycleOwner) { registerUserEntity ->
            binding.progressBar.visibility = View.GONE
            if(registerUserEntity.isError) {
               Blogger.error(message = "error occurred ${registerUserEntity.message}")
           }
            else {
                Blogger.debug(message = "user register successfully")

                navController.navigate(R.id.action_register_fragment_to_login_fragment)
           }
            registerUserEntity.message?.let { showToast(it) }

        }


    }

    override fun observeLiveData() {
        observeValidationLiveData()
       // observeAuthenticatorLiveData()
        observeRegistrationLiveData()
    }

    override fun setUpListeners() {
        binding.btnSubmit.setOnClickListener {
            hideSoftKeyboard(it)
            val user = InvoiceUser()
            user.apply {
                this.name = binding.etName.text.toString()
                this.email = binding.etEmail.text.toString()
                this.password = binding.etPassword.text.toString()
                this.confirmPassword = binding.etConfirmPassword.text.toString()

            }
            validationViewModel.doRegistrationValidation(user)
        }
    }

    override fun setUpUI() {
        binding.etName.addTextChangedListener(EditTextWatcher(binding.textInputName))
        binding.etEmail.addTextChangedListener(EditTextWatcher(binding.textInputEmail))
        binding.etPassword.addTextChangedListener(EditTextWatcher(binding.textInputPassword))
        binding.etConfirmPassword.addTextChangedListener(EditTextWatcher(binding.textInputConfirmPassword))
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