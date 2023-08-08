package com.pocket.invoiceapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.pocket.invoiceapp.intefaces.DialogClickListener

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
}
