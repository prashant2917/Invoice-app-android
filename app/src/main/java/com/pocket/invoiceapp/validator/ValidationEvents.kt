package com.pocket.invoiceapp.validator

import com.pocket.invoiceapp.base.InvoiceUser

sealed class ValidationEvents {
 class SendErrorMessage(val message : String) :ValidationEvents()
 class Success(val user : InvoiceUser) : ValidationEvents()
}