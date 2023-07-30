package com.pocket.invoiceapp.login.data

import com.pocket.invoiceapp.base.BaseUser

class LoginUser : BaseUser() {
    override var name: String = ""
    override var password: String = ""
    var email: String = ""


}