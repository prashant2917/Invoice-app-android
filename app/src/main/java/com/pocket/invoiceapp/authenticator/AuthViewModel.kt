package com.pocket.invoiceapp.authenticator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseAuthRepository
import com.pocket.invoiceapp.utils.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: BaseAuthRepository,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = _firebaseUser

    private val LOG_TAG = AuthViewModel::class.java.name

    private val authenticatorEventsChannel = Channel<AuthenticatorEvent>()
    val authenticatorEventsFlow = authenticatorEventsChannel.receiveAsFlow()

    fun signInUser(email: String, password: String) = viewModelScope.launch {
        try {
            authenticatorEventsChannel.send(AuthenticatorEvent.Loading(true))
            authenticatorEventsChannel.send(AuthenticatorEvent.Error(""))
            val user = repository.signInWithEmailPassword(email, password)
            user?.let {
                _firebaseUser.postValue(it)
                authenticatorEventsChannel.send(
                    AuthenticatorEvent.Success(
                        stringResourcesProvider.getString(
                            R.string.user_login_success
                        ), it
                    )
                )
                authenticatorEventsChannel.send(AuthenticatorEvent.Loading(false))
            }
        } catch (e: Exception) {
            authenticatorEventsChannel.send(AuthenticatorEvent.Error(e.message.toString()))
            authenticatorEventsChannel.send(AuthenticatorEvent.Loading(false))

        }
    }


    fun signUpUser(email: String, password: String) = viewModelScope.launch {
        try {
            authenticatorEventsChannel.send(AuthenticatorEvent.Loading(true))
            authenticatorEventsChannel.send(AuthenticatorEvent.Error(""))
            val user = repository.signUpWithEmailPassword(email, password)
            user?.let {
                _firebaseUser.postValue(it)
                authenticatorEventsChannel.send(
                    AuthenticatorEvent.Success(
                        stringResourcesProvider.getString(
                            R.string.user_registration_success
                        ), it
                    )
                )
                authenticatorEventsChannel.send(AuthenticatorEvent.Loading(false))
            }
        } catch (e: Exception) {
            authenticatorEventsChannel.send(AuthenticatorEvent.Error(e.message.toString()))
            authenticatorEventsChannel.send(AuthenticatorEvent.Loading(false))

        }
    }

    fun signOut() = viewModelScope.launch {
        authenticatorEventsChannel.send(AuthenticatorEvent.Loading(true))
        try {
            val user = repository.signOut()
            user?.let {
                authenticatorEventsChannel.send(
                    AuthenticatorEvent.SignOut(
                        false,
                        stringResourcesProvider.getString(R.string.error_logout_failure)
                    )
                )
            } ?: authenticatorEventsChannel.send(
                AuthenticatorEvent.SignOut(
                    true,
                    stringResourcesProvider.getString(R.string.user_logout_success)
                )
            )

            getCurrentUser()

        } catch (e: Exception) {
            authenticatorEventsChannel.send(AuthenticatorEvent.Error(e.message.toString()))
            authenticatorEventsChannel.send(AuthenticatorEvent.Loading(false))
        }
    }


    fun getCurrentUser() = viewModelScope.launch {
        val user = repository.getCurrentUser()
        _firebaseUser.postValue(user)
    }


}