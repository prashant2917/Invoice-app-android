package com.pocket.invoiceapp.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {
 open var disposable : Disposable? =null
 private val compositeDisposable: CompositeDisposable = CompositeDisposable()

 protected fun addDisposable(disposable: Disposable) {
  compositeDisposable.add(disposable)
 }

 private fun clearDisposables() {
  compositeDisposable.clear()
 }

 override fun onCleared() {
  super.onCleared()
  clearDisposables()
 }
}