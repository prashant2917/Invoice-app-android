package com.pocket.invoiceapp.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class RegisterViewModel() : ViewModel() {


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // val savedStateHandle = createSavedStateHandle()
                //val myRepository = (this[APPLICATION_KEY] as InvoiceApplication).myRepository
                RegisterViewModel()
            }
        }
    }
}