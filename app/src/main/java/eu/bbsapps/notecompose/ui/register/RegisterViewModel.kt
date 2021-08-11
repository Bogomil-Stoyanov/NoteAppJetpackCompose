package eu.bbsapps.notecompose.ui.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.bbsapps.notecompose.repositories.NoteRepository
import eu.bbsapps.notecompose.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    private val _usernameText = mutableStateOf("")
    val emailText: State<String> = _usernameText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _isPasswordVisible = mutableStateOf(false)
    val isPasswordVisible: State<Boolean> = _isPasswordVisible

    private val _confirmPasswordText = mutableStateOf("")
    val confirmPasswordText: State<String> = _confirmPasswordText

    private val _isConfirmPasswordVisible = mutableStateOf(false)
    val isConfirmPasswordVisible: State<Boolean> = _isConfirmPasswordVisible

    fun setEmailText(username: String) {
        _usernameText.value = username
    }

    fun setPasswordText(password: String) {
        _passwordText.value = password
    }

    fun setIsPasswordVisible(isVisible: Boolean) {
        _isPasswordVisible.value = isVisible
    }

    fun setConfirmPasswordText(password: String) {
        _confirmPasswordText.value = password
    }

    fun setIsConfirmPasswordVisible(isVisible: Boolean) {
        _isConfirmPasswordVisible.value = isVisible
    }

    private val _registerStatus = MutableLiveData<Resource<String>>()
    var registerStatus: LiveData<Resource<String>> = _registerStatus

    fun register() {
        _registerStatus.postValue(Resource.loading(null))
        if (passwordText.value.isEmpty() || emailText.value.isEmpty() || confirmPasswordText.value.isEmpty()) {
            _registerStatus.postValue(Resource.error("Please, fill out all the fields", null))
            return
        }
        if (passwordText.value!= confirmPasswordText.value) {
            _registerStatus.postValue(Resource.error("Passwords do not match", null))
            return
        }
        viewModelScope.launch {
            val result = noteRepository.register(emailText.value, passwordText.value)
            _registerStatus.postValue(result)
        }
    }
}