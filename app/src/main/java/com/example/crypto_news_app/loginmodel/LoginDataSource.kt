package com.example.crypto_news_app.loginmodel

import com.example.crypto_news_app.loginmodel.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {


    fun signin(username: String, password: String,auth:FirebaseAuth,callBack: (Result<FirebaseUser>)->Unit)  {

            auth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) run {
                        System.out.println("signin success")
                        val User =auth.currentUser
                        //val User = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
                        val Result = Result.Success<FirebaseUser>(User)
                        callBack(Result)
                    }
                }.addOnFailureListener { exception ->
                    val Result = Result.Error(IOException("Error logging in", exception))
                    callBack(Result)
                }
    }

    fun login(username: String, password: String,auth:FirebaseAuth,callBack: (Result<FirebaseUser>)->Unit)  {

        auth.signInWithEmailAndPassword(username,password)
            .addOnCompleteListener{task->
            if(task.isSuccessful) run{
                System.out.println("login success")
               val User =auth.currentUser
            // val User = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
               val Result = Result.Success<FirebaseUser>(User)
                callBack(Result)
            }
        }.addOnFailureListener { exception->
            val Result = Result.Error(IOException("Error logging in", exception  ))
            callBack(Result)
        }

    }
    fun logout(auth:FirebaseAuth) {
        auth.signOut()
    }


//        try {
//
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
//            return Result.Success(fakeUser)
//        } catch (e: Throwable) {
//            return Result.Error(IOException("Error logging in", e))
//        }



}