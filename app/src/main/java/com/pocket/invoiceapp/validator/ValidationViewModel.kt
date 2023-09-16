package com.pocket.invoiceapp.validator

import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseViewModel
import com.pocket.invoiceapp.utils.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val validator: Validator,
    private val stringResourcesProvider: StringResourcesProvider
) : BaseViewModel() {
    var isNameValid = false
    var isEmailValid = false
    var isPasswordValid = false
    var isConfirmPasswordValid = false
    var isPasswordMatch = false

    fun isValidName(name: String): Pair<Boolean, String?> {
        return if (validator.isValidName(name)) {
            Pair(true, null)
        } else {
            Pair(false, stringResourcesProvider.getString(R.string.error_invalid_name))
        }

    }

    fun isValidEmail(email: String): Pair<Boolean, String?> {
        return if (validator.isValidEmail(email)) {
            Pair(true, null)
        } else {
            Pair(false, stringResourcesProvider.getString(R.string.error_invalid_email))
        }

    }

    fun isValidPassword(password: String): Pair<Boolean, String?> {
        return if (validator.isValidPassword(password)) {
            Pair(true, null)
        } else {
            Pair(false, stringResourcesProvider.getString(R.string.error_invalid_password))
        }

    }

    fun checkBothPassword(password: String, confirmPassword: String): Pair<Boolean, String?> {
        return if (validator.checkBothPasswords(password, confirmPassword)) {
            Pair(true, null)
        } else {
            Pair(false, stringResourcesProvider.getString(R.string.error_both_password))
        }

    }

    fun clearData() {
        isNameValid = false
        isEmailValid = false
        isConfirmPasswordValid = false
        isPasswordValid = false
        isPasswordMatch = false
    }

    override fun onCleared() {
        super.onCleared()
        clearData()
    }


}