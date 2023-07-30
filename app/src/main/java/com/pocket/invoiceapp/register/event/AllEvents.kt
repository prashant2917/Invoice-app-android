package com.pocket.invoiceapp.register.event

sealed class RegisterEvents {
        data class Loading(val isLoading : Boolean) :RegisterEvents()
        data class Success(val message : String) : RegisterEvents()
        data class ErrorCode(val code : Int):RegisterEvents()
        data class Error(val error : String) : RegisterEvents()
    }