package com.mattsplat.tfa_compose

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mattsplat.tfa_compose.DataModels.Account
import com.mattsplat.tfa_compose.DataModels.Transaction
import org.json.JSONArray

interface RequestListener {
    fun onError(error: String?)
    fun onResponse(response: Any?)
}

interface ResponseTransformer {
    fun transform(response: Any?)
}

class ApiService(val listener: RequestListener) {
    val base_url = BuildConfig.BASE_URL
    var transformer: ResponseTransformer? = null

    fun login(username: String, password: String) {
        val params = mapOf("username" to username, "password" to password)

        val url = base_url + "login"
        post(url = url, params = params)
    }

    fun accounts() {
        transformer = object : ResponseTransformer {
            override fun transform(response: Any?) {

                val jsonArray = JSONArray(response.toString().trim())
                val list = mutableListOf<Account>()
                for (x in 1..jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(x - 1)
                    val account = Account(
                        id = jsonObject.getString("id"),
                        name = jsonObject.getString("name"),
                        balance = jsonObject.getString("balance")
                    )
                    list.add(account)
                }

                listener.onResponse(list.toList())

            }
        }

        get(url = base_url + "accounts")
    }

    fun transactions(account_id: String) {
        transformer = object : ResponseTransformer {
            override fun transform(response: Any?) {
                val jsonArray = JSONArray(response.toString().trim())
                val list = mutableListOf<Transaction>()
                for (x in 1..jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(x - 1)
                    val account = Transaction(
                        id = jsonObject.getString("id"),
                        title = jsonObject.getString("title"),
                        balance = jsonObject.getString("balance")
                    )
                    list.add(account)
                }

                listener.onResponse(list.toList())
            }
        }

        get(base_url + "transactions?accountId=${account_id}")
    }

    fun post(url: String, params: Map<String, String>? = null) {
        makeRequest(url, Request.Method.POST, params)
    }

    fun get(url: String) {
        makeRequest(url, Request.Method.GET)
    }

    private fun makeRequest(url: String, method: Int, params: Map<String, String>? = null) {

        val req = object : StringRequest(method, url, { res ->
            if (res.toString().isNotEmpty() && transformer !== null) {
                transformer!!.transform(res)
            } else {
                listener.onResponse(null)
            }
        }, { volleyError ->
            volleyError.message?.let { listener.onError(it) } ?: listener.onError(null)
        }) {

            override fun getParams(): Map<String, String>? {
                return params
            }
        }

        requestQueue.add(req)
    }

    companion object {
        val requestQueue: RequestQueue by lazy {
            Volley.newRequestQueue(MainApplication.applicationContext())
        }
    }

}