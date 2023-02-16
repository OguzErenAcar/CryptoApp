package com.example.crypto_news_app.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.crypto_news_app.databinding.ActivityLoginBinding

import com.example.crypto_news_app.R
import com.example.crypto_news_app.view.MainActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.view.*
import java.io.Serializable

class Login_Activity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var Firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this@Login_Activity)
        auth = Firebase.auth
        Firestore = FirebaseFirestore.getInstance()



        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading
        val signin=binding.signin

        val intentSet = Intent(applicationContext, MainActivity::class.java)
        var isLogin:Boolean


        //primary const parametreler factory de tanımlanıyor
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(auth)).get(LoginViewModel::class.java)

        val logoutbool = intent.getBooleanExtra("logout",false)

        if(logoutbool){
            loginViewModel.logout()
            isLogin=false
            intentSet.putExtra("LoginButonEnabled",true)
            startActivity(intentSet)
            finish()
        }
        System.out.println("logoutbool ::$logoutbool")
        //karakter hataları var ise
        loginViewModel.loginFormState.observe(this@Login_Activity, Observer {
            val loginState = it ?: return@Observer
            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid
            signin.isEnabled=loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        //sonuclar
        loginViewModel.loginResult.observe(this@Login_Activity, Observer {
            val loginResult = it ?: return@Observer
            System.out.println("observe")


            //burası tetiklenmedi ve geriye basıldı buton ne olucak

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
                isLogin=false
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                //intent ile mainactivity
                isLogin=true
                intentSet.putExtra("LoginButonEnabled",false)
                startActivity(intentSet)

            }


            System.out.println("login act 80")

            setResult(Activity.RESULT_OK)//???

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(//data c
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {//apply birden fazla bu refe ait fonk çağırablimek için

            afterTextChanged {
                loginViewModel.loginDataChanged(//data c
                    username.text.toString(),
                    password.text.toString() )
            }

            //???
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(//login 1
                            username.text.toString(),
                            password.text.toString() )
                }
                false
            }

            signin.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.signin(
                    username.text.toString(),
                    password.text.toString())//sign
                 //   saveUserid()

            }
            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(
                    username.text.toString(),
                    password.text.toString())//login 2
            }
        }
    }

    private fun saveUserid() {
        Firestore.collection("UsersID").document("IDS").set(auth.currentUser!!.uid, SetOptions.merge())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    System.out.println("Success uuid saved")
                }
            }.addOnFailureListener {
                System.out.println(it.message.toString())
            }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName

        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()

    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }


}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })


}