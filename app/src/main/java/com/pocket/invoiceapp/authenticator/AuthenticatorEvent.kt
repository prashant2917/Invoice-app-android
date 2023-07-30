package com.pocket.invoiceapp.authenticator

import com.google.firebase.auth.FirebaseUser

sealed class AuthenticatorEvent {
    class Loading(val isLoading: Boolean) : AuthenticatorEvent()
    class Success(val message: String, user: FirebaseUser) : AuthenticatorEvent()
    class Error(val errorMessage: String) : AuthenticatorEvent()
    class SignOut(val isSignOut: Boolean, val message: String) : AuthenticatorEvent()

}