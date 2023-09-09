package com.pocket.invoiceapp.utils
object Constants {
    const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$"
    const val SPLASH_SCREEN_TIMER = 2000L

    object Database {
        const val TABLE_USERS = "users"

    }
}