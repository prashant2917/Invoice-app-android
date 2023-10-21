package com.pocket.invoiceapp.register.repsitory

import com.pocket.invoiceapp.base.BaseRepository
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.base.data.network.entity.ResponseData
import com.pocket.invoiceapp.firebasedatabase.FirebaseDatabaseHelper
import com.pocket.invoiceapp.register.data.entity.RegisterUserResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RegisterRepositoryImpl@Inject constructor(private val databaseHelper: FirebaseDatabaseHelper): RegisterRepository(){

    override fun registerUserInDatabase(user: InvoiceUser): Single<ResponseData<RegisterUserResponse>> {
       return databaseHelper.registerUserInDatabase(user).subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
    }

}