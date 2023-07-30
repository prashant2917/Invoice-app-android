package com.pocket.invoiceapp.validator

import android.util.Patterns
import com.pocket.invoiceapp.utils.PASSWORD_PATTERN
import java.util.regex.Pattern
import javax.inject.Singleton


@Singleton
class Validator {

    fun isValidName(name: String): Boolean {
        return name.length >= 3

    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {

        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()


    }

    fun checkBothPasswords(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }


}