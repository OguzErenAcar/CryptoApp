package com.example.crypto_news_app.loginmodel

import com.google.firebase.auth.FirebaseUser

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: FirebaseUser?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class ErrorUnique(val exception_: String) : Result<Nothing>()

    override fun toString(): String {

        return when (this) {

            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is ErrorUnique->"unique hata =$exception_"

        }

    }
}