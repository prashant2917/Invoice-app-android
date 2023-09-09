package com.pocket.invoiceapp.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.databinding.FragmentLoginBinding
import com.pocket.invoiceapp.intefaces.EditTextWatcher
import com.pocket.invoiceapp.validator.ValidationEvents
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
    ): View? {

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
        observeValidationLiveData()


    }

    override fun setUpListeners() {
        binding.btnSubmit.setOnClickListener {
            //hideSoftKeyboard(view)
            val loginUser = InvoiceUser()
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

    override fun setUpUI() {
        binding.etEmail.addTextChangedListener(EditTextWatcher(binding.textInputEmail))
        binding.etPassword.addTextChangedListener(EditTextWatcher(binding.textInputPassword))
    }

    private fun observeValidationLiveData()  {
        validationViewModel.validationLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is ValidationEvents.SendErrorMessage -> {

                    bindMessageToTextInputLayout(event.message)
                }

                is ValidationEvents.Success -> {
                    val user = event.user


                }

            }
        }
    }






    private fun bindMessageToTextInputLayout(message: String) {
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