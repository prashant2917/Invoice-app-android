package com.pocket.invoiceapp.register.event

import com.pocket.invoiceapp.base.InvoiceUser

sealed class RegisterEvents {
    data class Loading(val isLoading: Boolean) : RegisterEvents()
    data class Success(val user: InvoiceUser) : RegisterEvents()
    data class Error(val error: String) : RegisterEvents()
}