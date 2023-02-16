package com.example.crypto_news_app.ui.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.crypto_news_app.loginmodel.LoginRepository
import com.example.crypto_news_app.loginmodel.Result
import com.example.crypto_news_app.R

class LoginViewModel(private val loginRepository: LoginRepository ) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    //buton ile de bu -->değişikliler live data
    fun signin(username: String, password: String) {
        // can be launched in a separate asynchronous job
        //retrofitten donecek sonuc
             loginRepository.signin(username, password ){ result->

            if (result is Result.Success) {
                _loginResult.value =  LoginResult(success = result.data?.email?.let { LoggedInUserView(displayName = it) })
            } else if(result is Result.Error) {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }//else unique
        }
    }

    fun login(username: String, password: String) {

        loginRepository.login(username, password ) {result->

            if (result is Result.Success) {
                _loginResult.value =  LoginResult(success = result.data?.email?.let { LoggedInUserView(displayName = it) })
            } else if(result is Result.Error) {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }//else unique
        }

    }


    fun logout(){
        loginRepository.logout( )
    }


    //activity de text değişnce bunu çağırıyor -->değişikliler live data
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }


    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }


}