package com.pocket.invoiceapp.register.repsitory

import com.pocket.invoiceapp.base.BaseRepository
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.base.data.network.entity.ResponseData
import com.pocket.invoiceapp.register.data.entity.RegisterUserResponse
import io.reactivex.rxjava3.core.Single

abstract class RegisterRepository  : BaseRepository() {

    abstract fun registerUserInDatabase(user: InvoiceUser): Single<ResponseData<RegisterUserResponse>>

}