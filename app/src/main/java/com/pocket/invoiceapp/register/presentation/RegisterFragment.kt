package com.pocket.invoiceapp.register.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseFragment
import com.pocket.invoiceapp.databinding.FragmentRegisterBinding
import com.pocket.invoiceapp.intefaces.EditTextWatcher
import com.pocket.invoiceapp.register.data.RegisterUser
import com.pocket.invoiceapp.register.repository.RegisterRepository
import com.pocket.invoiceapp.register.viewmodel.RegisterViewModel
import com.pocket.invoiceapp.utils.ValidatorUtil


class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val registerRepository = RegisterRepository()

    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }

    // onDestroyView.
    private val binding get() = _binding!!
    private var isAllDataValid = false

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


        binding.btnSubmit.setOnClickListener {
            val registerUser = RegisterUser()
            registerUser.apply {
                this.name = binding.etName.text.toString()
                this.email = binding.etEmail.text.toString()
                this.password = binding.etPassword.text.toString()
                this.confirmPassword = binding.etConfirmPassword.text.toString()

            }
            doValidation(registerUser)


        }
    }

    private fun doValidation(registerUser: RegisterUser) {
        if (!ValidatorUtil.isValidName(registerUser.name)) {
            binding.textInputName.error = activity?.getString(R.string.error_invalid_name)
            isAllDataValid = false
        } else if (!ValidatorUtil.isValidEmail(registerUser.email)) {
            binding.textInputEmail.error = activity?.getString(R.string.error_invalid_email)
            isAllDataValid = false
        } else if (!ValidatorUtil.isValidPassword(registerUser.password)) {
            binding.textInputPassword.error = activity?.getString(R.string.error_invalid_password)
            isAllDataValid = false
        } else if (!ValidatorUtil.isValidPassword(registerUser.confirmPassword)) {
            binding.textInputConfirmPassword.error =
                activity?.getString(R.string.error_invalid_password)
            isAllDataValid = false
        } else if (!ValidatorUtil.checkBothPasswords(
                registerUser.password,
                registerUser.confirmPassword
            )
        ) {
            binding.textInputConfirmPassword.error =
                activity?.getString(R.string.error_both_password)

        } else {
            Toast.makeText(activity?.baseContext, "Success", Toast.LENGTH_LONG).show()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}