package com.pocket.invoiceapp.blogger

import android.os.Build
import android.util.Log

object Blogger {
    private const val LOG_TAG = "invoice app"

   fun debug( tag : String = LOG_TAG ,  message : String) {
       Log.d(tag ,message)
   }

    fun error( tag : String = LOG_TAG ,  message : String) {
        Log.e(tag ,message)
    }

    fun error( tag : String = LOG_TAG ,  message : String, throwable: Throwable) {
        Log.e(tag ,message, throwable)
    }

    fun info( tag : String = LOG_TAG ,  message : String) {
        Log.e(tag ,message)
    }

    fun warn( tag : String = LOG_TAG ,  message : String) {
        Log.w(tag ,message)
    }
}