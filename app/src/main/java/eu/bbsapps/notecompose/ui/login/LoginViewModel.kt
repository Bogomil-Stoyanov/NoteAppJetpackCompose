package eu.bbsapps.notecompose.ui.login

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.notecompose.data.remote.BasicAuthInterceptor
import eu.bbsapps.notecompose.repositories.NoteRepository
import eu.bbsapps.notecompose.util.Constants.KEY_LOGGED_IN_EMAIL
import eu.bbsapps.notecompose.util.Constants.KEY_PASSWORD
import eu.bbsapps.notecompose.util.Constants.NO_EMAIL
import eu.bbsapps.notecompose.util.Constants.NO_PASSWORD
import eu.bbsapps.notecompose.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val sharedPref: SharedPreferences,
    private val basicAuthInterceptor: BasicAuthInterceptor
) : ViewModel() {

    private var curEmail: String? = null
    private var curPassword: String? = null

    private val _usernameText = mutableStateOf("")
    val emailText: State<String> = _usernameText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _isPasswordVisible = mutableStateOf(false)
    val isPasswordVisible: State<Boolean> = _isPasswordVisible

    fun setEmailText(username: String) {
        _usernameText.value = username
    }

    fun setPasswordText(password: String) {
        _passwordText.value = password
    }

    fun setIsPasswordVisible(isVisible: Boolean) {
        _isPasswordVisible.value = isVisible
    }

    private val _loginStatus = MutableLiveData<Resource<String>>()
    var loginStatus: LiveData<Resource<String>> = _loginStatus

    fun login() {
        curEmail=emailText.value
        curPassword=passwordText.value
        println("login e $curEmail p $curPassword")

        _loginStatus.postValue(Resource.loading(null))
        if (passwordText.value.isEmpty() || emailText.value.isEmpty()) {
            _loginStatus.postValue(Resource.error("Please, fill out all the fields", null))
            return
        }
        viewModelScope.launch {
            val result = noteRepository.login(emailText.value, passwordText.value)
            _loginStatus.postValue(result)
        }
    }

     private fun authenticateApi(email: String, password: String) {
        basicAuthInterceptor.email = email
        basicAuthInterceptor.password = password
    }

    fun successfullyLoggedIn() {
        println("successfullyLoggedIn e $curEmail p $curPassword")

        sharedPref.edit().putString(KEY_LOGGED_IN_EMAIL, curEmail).apply()
        sharedPref.edit().putString(KEY_PASSWORD, curPassword).apply()

        authenticateApi(curEmail ?: "", curPassword ?: "")
    }

     fun isLoggedIn():Boolean {
        curEmail = sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL) ?: NO_EMAIL
        curPassword = sharedPref.getString(KEY_PASSWORD, NO_PASSWORD) ?: NO_PASSWORD

         println("isLoggedIn e $curEmail p $curPassword")
         if (curEmail != NO_EMAIL && curPassword != NO_PASSWORD) {
             authenticateApi(
                 sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL)!!,
                 sharedPref.getString(KEY_PASSWORD, NO_PASSWORD)!!
             )
             return true
         }
         return false
    }

}