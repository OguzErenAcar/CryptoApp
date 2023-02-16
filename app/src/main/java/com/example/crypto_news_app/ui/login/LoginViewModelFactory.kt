package com.example.crypto_news_app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crypto_news_app.loginmodel.LoginDataSource
import com.example.crypto_news_app.loginmodel.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(val auth:FirebaseAuth): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource(),auth
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}