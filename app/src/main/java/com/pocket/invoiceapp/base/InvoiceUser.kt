package com.pocket.invoiceapp.base

import com.google.firebase.database.IgnoreExtraProperties
import com.pocket.invoiceapp.firebasedatabase.FirebaseDatabaseHelper
import com.pocket.invoiceapp.utils.Utils

@IgnoreExtraProperties
class InvoiceUser {
    var userId: String? =  Utils.getPrimaryKeyForUserTable()
    var name: String? = ""
    var email: String? = ""
    var password: String? = ""
    var confirmPassword: String? = ""

    override fun toString(): String {
        return "[ user id : $userId email :$email name:$name password :$password confirm password : $confirmPassword]"
    }

}