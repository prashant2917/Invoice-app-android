package com.pocket.invoiceapp.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pocket.invoiceapp.register.repository.RegisterRepository

class RegisterViewModel(repository: RegisterRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory() {
            initializer {
                // val savedStateHandle = createSavedStateHandle()
                val registerRepository = RegisterRepository()
                RegisterViewModel(registerRepository)
            }
        }
    }


}