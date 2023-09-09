package com.pocket.invoiceapp.utils

import com.pocket.invoiceapp.base.data.network.entity.ResponseData
import com.pocket.invoiceapp.register.data.entity.RegisterUserEntity
import com.pocket.invoiceapp.register.data.entity.RegisterUserResponse
import io.reactivex.rxjava3.core.Single

fun ResponseData<RegisterUserResponse>.mapResponseToRegisterEntity(): RegisterUserEntity {
    return RegisterUserEntity(
        name = if (this.success) this.data?.name else null,
        message = if (this.success) this.data?.successMessage else this.errorMessage,
        isError = this.error

    )
}