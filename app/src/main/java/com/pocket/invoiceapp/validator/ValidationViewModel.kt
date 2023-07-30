package com.pocket.invoiceapp.validator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.login.data.LoginUser
import com.pocket.invoiceapp.register.data.RegisterUser
import com.pocket.invoiceapp.utils.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val validator: Validator,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    private val validationEventsChannel = Channel<ValidationEvents>()
    val validationEventsFlow = validationEventsChannel.receiveAsFlow()
    fun doRegistrationValidation(registerUser: RegisterUser) = viewModelScope.launch {
        when {
            !validator.isValidName(registerUser.name) -> {
                validationEventsChannel.trySend(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_name)
                    )
                )
            }

            !validator.isValidEmail(registerUser.email) -> {
                validationEventsChannel.send(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_email)
                    )
                )

            }

            !validator.isValidPassword(registerUser.password) -> {
                validationEventsChannel.send(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_password)
                    )
                )

            }

            !validator.isValidPassword(registerUser.confirmPassword) -> {
                validationEventsChannel.send(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_confirm_password)
                    )
                )
            }

            !validator.checkBothPasswords(registerUser.password, registerUser.confirmPassword) -> {
                validationEventsChannel.send(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_both_password)
                    )
                )

            }

            else -> {
                validationEventsChannel.send(ValidationEvents.Success(registerUser))
            }

        }
    }

    fun doLoginValidation(loginUser: LoginUser) = viewModelScope.launch {
        when {
            !validator.isValidEmail(loginUser.email) -> {
                validationEventsChannel.send(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_email)
                    )
                )

            }

            !validator.isValidPassword(loginUser.password) -> {
                validationEventsChannel.send(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_password)
                    )
                )

            }

            else -> {
                validationEventsChannel.send(ValidationEvents.Success(loginUser))
            }

        }
    }

}