package com.pocket.invoiceapp.register.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pocket.invoiceapp.databinding.FragmentRegisterBinding
import com.pocket.invoiceapp.intefaces.EditTextWatcher
import com.pocket.invoiceapp.register.viewmodel.RegisterViewModel


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }

    // This property is only valid between onCreateView and
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

        binding.btnSubmit.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}