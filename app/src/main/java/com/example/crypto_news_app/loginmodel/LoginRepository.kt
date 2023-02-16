package com.example.crypto_news_app.loginmodel

import com.example.crypto_news_app.loginmodel.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.IOException

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository( val dataSource: LoginDataSource ,val auth:FirebaseAuth) {

    // in-memory cache of the loggedInUser object
    companion object{
        var user: FirebaseUser? = null
            private set
    }

    val isLoggedIn: Boolean
        get() = user != null
        //get ??

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = auth.currentUser
    }

    fun logout( ) {
        dataSource.logout( auth)
        user = null
    }



    fun signin(username: String, password: String  ,callBack: (Result<FirebaseUser>)->Unit) {
        // handle login
        if(!isLoggedIn){
        dataSource.signin(username, password,auth){result->

            if (result is Result.Success) {
                setLoggedInUser(result.data)

            }
            callBack(result)
          }
        } else{
            val result = Result.ErrorUnique("unique hata ")
            callBack(result)
        }
    }
    fun login(username: String, password: String ,callBack: (Result<FirebaseUser>)->Unit) {
        // handle login

            if(!isLoggedIn){
            dataSource.login(username, password,auth){result->
            if (result is Result.Success) {
                setLoggedInUser(result.data)
            }
            callBack(result)
          }
        }
        else{
            val result = Result.ErrorUnique("unique hata ")
            callBack(result)
            }

    }


    private fun setLoggedInUser(FirebaseUser: FirebaseUser?) {
        user= FirebaseUser

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}