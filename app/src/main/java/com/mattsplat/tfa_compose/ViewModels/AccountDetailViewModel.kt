package com.mattsplat.tfa_compose.ViewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mattsplat.tfa_compose.ApiService
import com.mattsplat.tfa_compose.DataModels.Transaction
import com.mattsplat.tfa_compose.RequestListener
import kotlinx.coroutines.launch

open class AccountDetailViewModel: ViewModel() {
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    private val _apiError = MutableLiveData<String?>(null)
    val apiError: MutableLiveData<String?> get() = _apiError

    fun requestTransactions(account_id: String) {
        val listener = object: RequestListener {
            override fun onError(error: String?) {
                _apiError.postValue(error)
            }

            override fun onResponse(response: Any?) {
                _transactions.postValue((response as ArrayList<Transaction>).toList())
            }
        }

        val apiService = ApiService(listener = listener)
        apiService.transactions(account_id = account_id)
    }
}