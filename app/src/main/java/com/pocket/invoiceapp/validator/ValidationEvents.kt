package com.pocket.invoiceapp.validator

import com.pocket.invoiceapp.base.BaseUser

sealed class ValidationEvents {
 class SendErrorMessage(val message : String) :ValidationEvents()
 class Success(val user : BaseUser) : ValidationEvents()
}