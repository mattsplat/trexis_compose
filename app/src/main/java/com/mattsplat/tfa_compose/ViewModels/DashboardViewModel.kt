package com.mattsplat.tfa_compose.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mattsplat.tfa_compose.ApiService
import com.mattsplat.tfa_compose.DataModels.Account
import com.mattsplat.tfa_compose.DataModels.Transaction
import com.mattsplat.tfa_compose.RequestListener

class DashboardViewModel: ViewModel() {
    private val _accounts = MutableLiveData<List<Account>>()
    val accounts: LiveData<List<Account>> get() = _accounts

    private val _apiError = MutableLiveData<String?>(null)
    val apiError: MutableLiveData<String?> get() = _apiError

    fun requestAccounts() {
        val listener = object: RequestListener {
            override fun onError(error: String?) {
                _apiError.postValue(error)
            }

            override fun onResponse(response: Any?) {
                val accountList: List<Account> = (response as List<*>).filterIsInstance<Account>()
                _accounts.postValue(accountList)
            }
        }

        val apiService = ApiService(listener = listener)
        apiService.accounts()
    }
}