package com.pocket.invoiceapp.utils

import android.util.Patterns
import java.util.regex.Pattern


class ValidatorUtil {
    companion object {
        private const val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$"

        fun isValidName(name: String): Boolean {
            return name.length > 4

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

}