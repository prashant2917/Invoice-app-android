package com.pocket.invoiceapp.register.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.blogger.Blogger
import com.pocket.invoiceapp.databinding.FragmentRegisterBinding
import com.pocket.invoiceapp.register.viewmodel.RegisterViewModel
import com.pocket.invoiceapp.utils.InvoiceAppSecurity
import com.pocket.invoiceapp.validator.ValidationViewModel
import java.security.KeyStore
import java.security.KeyStoreSpi


class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val validationViewModel: ValidationViewModel by activityViewModels()
    private val registerViewModel: RegisterViewModel by activityViewModels()


    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        observeLiveData()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()



    }


    private fun observeRegistrationLiveData()  {

        registerViewModel.registerLiveData.observe(viewLifecycleOwner) { it ->
          it.getContentIfNotHandled().let {registerUserEntity ->

              binding.progressBar.visibility = View.GONE
              if (registerUserEntity?.isError == true) {
                  Blogger.error(message = "error occurred ${registerUserEntity.message}")
              } else {
                  Blogger.debug(message = "user register successfully")
                  validationViewModel.clearData()
                  navController.navigate(R.id.action_register_fragment_to_login_fragment)
              }
              registerUserEntity?.message?.let { showToast(it) }
          }


        }


    }

    override fun observeLiveData() {

        observeRegistrationLiveData()
    }

    override fun setUpListeners() {
        binding.btnSubmit.setOnClickListener {
            hideSoftKeyboard(it)
            val user = InvoiceUser()
            user.apply {
                this.name = binding.etName.text.toString()
                this.email = binding.etEmail.text.toString()
                this.password = InvoiceAppSecurity.encrypt(binding.etPassword.text.toString())
            }

           // Blogger.debug(message = "password ${InvoiceAppSecurity.decrypt(user.password)}")
           binding.progressBar.visibility = View.VISIBLE
         registerViewModel.registerUserInDatabase(user)
        }
    }

    override fun setUpUI() {
        binding.isSubmitButtonEnabled = false
        binding.etName.addTextChangedListener(nameTextWatcher)
        binding.etEmail.addTextChangedListener(emailTextWatcher)
        binding.etPassword.addTextChangedListener(passwordTextWatcher)
        binding.etConfirmPassword.addTextChangedListener(conFirmPasswordTextWatcher)
        binding.textInputEmail.error = null
        binding.textInputPassword.error = null
        binding.textInputName.error = null
        binding.textInputConfirmPassword.error = null
    }

    private val nameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.textInputName.isErrorEnabled) {
                binding.textInputName.error = null
            }
            val result = validationViewModel.isValidName(binding.etName.text.toString())
            if (!result.first) {
                binding.textInputName.error = result.second
                validationViewModel.isNameValid = false
            } else {
                validationViewModel.isNameValid = true
                binding.textInputName.error = null
            }
            setSubmitEnabled()
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }


    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.textInputEmail.isErrorEnabled) {
                binding.textInputEmail.error = null
            }
            val result = validationViewModel.isValidEmail(binding.etEmail.text.toString())
            if (!result.first) {
                binding.textInputEmail.error = result.second
                validationViewModel.isEmailValid = false
            } else {
                validationViewModel.isEmailValid = true
                binding.textInputEmail.error = null
            }
            setSubmitEnabled()
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.textInputPassword.isErrorEnabled) {
                binding.textInputPassword.error = null
            }
            val result = validationViewModel.isValidPassword(binding.etPassword.text.toString())
            if (!result.first) {
                binding.textInputPassword.error = result.second
                validationViewModel.isPasswordValid = false
            } else {
                validationViewModel.isPasswordValid = true
                binding.textInputPassword.error = null
                val passwordMatchResult = validationViewModel.checkBothPassword(
                    binding.etPassword.text.toString(),
                    binding.etConfirmPassword.text.toString()
                )
                if (validationViewModel.isConfirmPasswordValid && !passwordMatchResult.first) {
                    binding.textInputPassword.error = passwordMatchResult.second
                    validationViewModel.isPasswordMatch = false
                } else {
                    validationViewModel.isPasswordMatch = true
                }
            }
            setSubmitEnabled()
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private val conFirmPasswordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.textInputConfirmPassword.isErrorEnabled) {
                binding.textInputConfirmPassword.error = null
            }
            val result =
                validationViewModel.isValidPassword(binding.etConfirmPassword.text.toString())
            if (!result.first) {
                binding.textInputConfirmPassword.error = result.second
                validationViewModel.isConfirmPasswordValid = false
            } else {
                validationViewModel.isConfirmPasswordValid = true
                binding.textInputConfirmPassword.error = null
                val passwordMatchResult = validationViewModel.checkBothPassword(
                    binding.etPassword.text.toString(),
                    binding.etConfirmPassword.text.toString()
                )
                if (validationViewModel.isPasswordValid && !passwordMatchResult.first) {
                    binding.textInputConfirmPassword.error = passwordMatchResult.second
                    validationViewModel.isPasswordMatch = false
                } else {
                    validationViewModel.isPasswordMatch = true
                }
            }
            setSubmitEnabled()
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private fun setSubmitEnabled() {
        val isEnabled =
            validationViewModel.isNameValid && validationViewModel.isEmailValid && validationViewModel.isPasswordValid && validationViewModel.isConfirmPasswordValid
        binding.isSubmitButtonEnabled = isEnabled && validationViewModel.isPasswordMatch

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if(registerViewModel.registerLiveData.hasActiveObservers()) {
            registerViewModel.registerLiveData.removeObservers(viewLifecycleOwner)
        }
    }
}