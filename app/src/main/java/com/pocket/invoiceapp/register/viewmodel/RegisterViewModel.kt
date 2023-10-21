package com.pocket.invoiceapp.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pocket.invoiceapp.base.BaseViewModel
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.base.SingleLiveEvent
import com.pocket.invoiceapp.blogger.Blogger
import com.pocket.invoiceapp.register.data.entity.RegisterUserEntity
import com.pocket.invoiceapp.register.usecase.RegisterUseCase
import com.pocket.invoiceapp.utils.InvoiceAppSecurity
import com.pocket.invoiceapp.utils.mapResponseToRegisterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
) : BaseViewModel() {
    val registerLiveData: LiveData<SingleLiveEvent<RegisterUserEntity>> get() = _registerLiveData
    private val _registerLiveData = MutableLiveData<SingleLiveEvent<RegisterUserEntity>>()


    fun registerUserInDatabase(user: InvoiceUser) {
        disposable?.dispose()
     /* disposable= useCase.registerUserInDatabase(user).subscribe { responseData  ->

            _registerLiveData.value =SingleLiveEvent(responseData.mapResponseToRegisterEntity())
        }*/

        useCase.registerUserInDatabase(user).subscribe { responseData ->

        }.dispose()

        disposable?.let { addDisposable(it) }

    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }


}