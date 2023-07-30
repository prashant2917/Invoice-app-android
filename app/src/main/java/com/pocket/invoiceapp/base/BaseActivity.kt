package com.pocket.invoiceapp.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActivity : AppCompatActivity() {
    abstract fun registerObservers()

    fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }
}