package com.example.crypto_news_app.VM

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfilVMFactory(val application: Application, val  Firestore: FirebaseFirestore,val auth: FirebaseAuth): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfilVM::class.java)) {

            return ProfilVM(  application,Firestore ,auth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}