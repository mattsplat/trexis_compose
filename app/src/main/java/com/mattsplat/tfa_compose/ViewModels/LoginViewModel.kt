package com.mattsplat.tfa_compose.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mattsplat.tfa_compose.ApiService
import com.mattsplat.tfa_compose.DataModels.Transaction
import com.mattsplat.tfa_compose.RequestListener

class LoginViewModel: ViewModel() {
    private val _username = MutableLiveData("")
    val username: MutableLiveData<String> get() = _username

    private val _password = MutableLiveData("")
    val password: MutableLiveData<String> get() = _password

    private val _apiError = MutableLiveData<String?>(null)
    val apiError: MutableLiveData<String?> get() = _apiError

    private val _loginSuccessful = MutableLiveData(false)
    val loginSuccessful: MutableLiveData<Boolean> get() = _loginSuccessful

    fun submitLogin() {
        if(username.value.isNullOrEmpty() or password.value.isNullOrEmpty()) {
            return
        }

        val listener = object: RequestListener {
            override fun onError(error: String?) {
                _apiError.postValue(error)
            }

            override fun onResponse(response: Any?) {
                _loginSuccessful.postValue(true)
            }
        }

        val apiService = ApiService(listener = listener)
        apiService.login(username = username.value!!, password = password.value!!)
    }

    fun setUsername(username: String) {
        _username.postValue(username)
    }

    fun setPassword(password: String) {
        _password.postValue(password)
    }
}