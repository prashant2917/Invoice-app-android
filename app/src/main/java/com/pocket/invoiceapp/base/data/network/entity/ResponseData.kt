package com.pocket.invoiceapp.base.data.network.entity

class ResponseData<T> {
  var data: T? = null
  var error: Boolean = false
  var errorMessage : String? = null
  var success : Boolean = false
}