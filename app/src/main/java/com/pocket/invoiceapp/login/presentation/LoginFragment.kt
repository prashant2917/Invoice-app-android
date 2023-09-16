package com.pocket.invoiceapp.login.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.databinding.FragmentLoginBinding
import com.pocket.invoiceapp.validator.ValidationViewModel


class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null

    private val validationViewModel: ValidationViewModel by activityViewModels()
    private val tag = LoginFragment::class.java.name
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()
        showToolbar()
        observeLiveData()
        addBackPressCallback()


    }

    override fun observeLiveData() {


    }

    override fun setUpListeners() {
        binding.btnSubmit.setOnClickListener {
            //hideSoftKeyboard(view)
            val loginUser = InvoiceUser()
            loginUser.apply {
                this.email = binding.etEmail.text.toString()
                this.password = binding.etPassword.text.toString()
            }

        }

        binding.btnSignup.setOnClickListener {
            navController.navigate(R.id.action_login_fragment_to_register_fragment)
        }
    }

    override fun setUpUI() {
        binding.etEmail.addTextChangedListener(emailTextWatcher)
        binding.etPassword.addTextChangedListener(passwordTextWatcher)

    }


    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.textInputEmail.isErrorEnabled) {
                binding.textInputEmail.error = null
            }
            if(binding.textInputEmail.hasFocus()) {
                val result = validationViewModel.isValidEmail(binding.etEmail.text.toString())
                if (!result.first) {
                    binding.textInputEmail.error = result.second
                    validationViewModel.isEmailValid = false
                } else {
                    validationViewModel.isEmailValid = true
                    binding.textInputEmail.error = null
                }

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
            if(binding.textInputPassword.hasFocus()) {
                val result = validationViewModel.isValidPassword(binding.etPassword.text.toString())
                if (!result.first) {
                    binding.textInputPassword.error = result.second
                    validationViewModel.isPasswordValid = false
                } else {
                    validationViewModel.isPasswordValid = true
                    binding.textInputPassword.error = null

                }

            }
            setSubmitEnabled()
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }


    private fun setSubmitEnabled() {
        binding.isSubmitButtonEnabled =
            validationViewModel.isEmailValid && validationViewModel.isPasswordValid


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}