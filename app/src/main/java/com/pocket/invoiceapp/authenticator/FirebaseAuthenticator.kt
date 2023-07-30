package com.pocket.invoiceapp.authenticator

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pocket.invoiceapp.base.BaseAuthenticator
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticator : BaseAuthenticator {
    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser
    }

    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser
    }

    override fun signOut(): FirebaseUser? {
        Firebase.auth.signOut()
        return Firebase.auth.currentUser
    }

    override fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override suspend fun sendPasswordReset(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).await()
    }
}
