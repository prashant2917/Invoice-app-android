package com.pocket.invoiceapp.register.data.entity

data class RegisterUserEntity(
    var name: String? = null,
    var message: String? = null,
    var isError : Boolean = false
)