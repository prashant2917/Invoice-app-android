package com.pocket.invoiceapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pocket.invoiceapp.firebasedatabase.FirebaseDatabaseHelper
import com.pocket.invoiceapp.intefaces.DialogClickListener
import java.util.UUID

object Utils {
    fun createAlertDialog(
        context: Context,
        title: String,
        positiveButton: String,
        negativeButton: String,
        dialogClickListener: DialogClickListener
    ) {
        val alertDialog: AlertDialog = context.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(title)
                setPositiveButton(
                    positiveButton
                ) { dialog, _ ->
                    // User clicked OK button

                    dialog.cancel()
                    dialogClickListener.onPositiveClick()

                }
                setNegativeButton(
                    negativeButton
                ) { dialog, _ ->
                    dialog.cancel()
                    dialogClickListener.onNegativeClick()
                }
            }


            // Create the AlertDialog
            builder.create()
        }
        alertDialog.show()

    }

    fun getPrimaryKeyForUserTable() : String? {
        return Firebase.database.getReference(Constants.Database.TABLE_USERS).push().key
    }
}
