package com.pocket.invoiceapp.utils


import android.annotation.SuppressLint
import androidx.datastore.core.DataStore
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import java.util.Base64


object InvoiceAppSecurity {
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    @SuppressLint("NewApi")
    fun encrypt(data: String) :String {

        return  Base64.getEncoder().encodeToString(data.toByteArray())
    }

    @SuppressLint("NewApi")
    fun decrypt(data: String?) :String {
        return   String(Base64.getDecoder().decode(data))
    }

}