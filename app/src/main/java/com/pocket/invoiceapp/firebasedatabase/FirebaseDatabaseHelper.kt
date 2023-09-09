package com.pocket.invoiceapp.firebasedatabase

import android.util.Log
import com.google.android.gms.common.api.Response
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pocket.invoiceapp.R
import com.pocket.invoiceapp.base.BaseRepository
import com.pocket.invoiceapp.base.InvoiceUser
import com.pocket.invoiceapp.base.data.network.entity.ResponseData
import com.pocket.invoiceapp.blogger.Blogger
import com.pocket.invoiceapp.register.data.entity.RegisterUserResponse
import com.pocket.invoiceapp.register.event.RegisterEvents
import com.pocket.invoiceapp.utils.Constants
import com.pocket.invoiceapp.utils.StringResourcesProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FirebaseDatabaseHelper @Inject constructor(private val stringResourcesProvider: StringResourcesProvider) {

    fun registerUserInDatabase(user: InvoiceUser): Single<ResponseData<RegisterUserResponse>> {
        val databaseReference = Firebase.database.getReference(Constants.Database.TABLE_USERS)
        var responseData = ResponseData<RegisterUserResponse>()
        user.userId = databaseReference.push().key
        databaseReference.child(user.userId.toString())
            .setValue(
                user
            ) { error, reference ->
                responseData = ResponseData()
                val registerUserResponse = RegisterUserResponse()
                if (error == null) {
                    responseData.success = true
                    registerUserResponse.name = reference.child(Constants.Database.TABLE_USERS)
                        .child(user.userId.toString()).child("name").toString()
                    registerUserResponse.successMessage = stringResourcesProvider.getString(R.string.user_registration_success)


                } else {
                    responseData.error = true
                    registerUserResponse.errorMessage = error.message

                }
                responseData.data = registerUserResponse


            }
        return Single.just(responseData)
    }

}

