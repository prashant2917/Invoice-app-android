package com.pocket.invoiceapp.validator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseViewModel
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.register.event.RegisterEvents
import com.pocket.invoiceapp.utils.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val validator: Validator,
    private val stringResourcesProvider: StringResourcesProvider
) : BaseViewModel() {
    val validationLiveData : LiveData<ValidationEvents> get() = _validationLiveData
    private val _validationLiveData   = MutableLiveData<ValidationEvents> ()
    fun doRegistrationValidation(user: InvoiceUser) = viewModelScope.launch {
        when {
            !validator.isValidName(user.name.toString()) -> {
                _validationLiveData.postValue(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_name)
                    )
                )
            }

            !validator.isValidEmail(user.email.toString()) -> {
                _validationLiveData.postValue(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_email)
                    )
                )

            }

            !validator.isValidPassword(user.password.toString()) -> {
                _validationLiveData.postValue(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_password)
                    )
                )

            }

            !validator.isValidPassword(user.confirmPassword.toString()) -> {
                _validationLiveData.postValue(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_confirm_password)
                    )
                )
            }

            !validator.checkBothPasswords(user.password.toString(), user.confirmPassword.toString()) -> {
                _validationLiveData.postValue(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_both_password)
                    )
                )

            }

            else -> {
                _validationLiveData.postValue(ValidationEvents.Success(user))
            }

        }
    }

    fun doLoginValidation(user: InvoiceUser) = viewModelScope.launch(Dispatchers.IO) {
        when {
            !validator.isValidEmail(user.email.toString()) -> {
                _validationLiveData.postValue(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_email)
                    )
                )
            }

            !validator.isValidPassword(user.password.toString()) -> {
                _validationLiveData.postValue(
                    ValidationEvents.SendErrorMessage(
                        stringResourcesProvider.getString(R.string.error_invalid_password)
                    )
                )

            }

            else -> {
                _validationLiveData.postValue(ValidationEvents.Success(user))
            }

        }
    }

}