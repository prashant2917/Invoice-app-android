package com.pocket.invoiceapp.register.usecase

import com.pocket.invoiceapp.base.BaseUseCase
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.base.data.network.entity.ResponseData
import com.pocket.invoiceapp.register.data.entity.RegisterUserResponse
import com.pocket.invoiceapp.register.repsitory.RegisterRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) :
    BaseUseCase() {
    fun registerUserInDatabase(user: InvoiceUser): Single<ResponseData<RegisterUserResponse>> {
        return repository.registerUserInDatabase(user)
    }
}