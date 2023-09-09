package com.pocket.invoiceapp.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pocket.invoiceapp.base.BaseViewModel
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.base.SingleLiveEvent
import com.pocket.invoiceapp.register.data.entity.RegisterUserEntity
import com.pocket.invoiceapp.register.usecase.RegisterUseCase
import com.pocket.invoiceapp.utils.mapResponseToRegisterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
) : BaseViewModel() {
    val registerLiveData: LiveData<RegisterUserEntity> get() = _registerLiveData
    private val _registerLiveData = MutableLiveData<RegisterUserEntity>()


    fun registerUserInDatabase(user: InvoiceUser)  {
        disposable?.dispose()
        disposable = useCase.registerUserInDatabase(user).subscribe { responseData  ->
            _registerLiveData.postValue(responseData.mapResponseToRegisterEntity())
        }
        disposable?.let { addDisposable(it) }
    }


}