package com.panjikrisnayasa.submission3.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjikrisnayasa.submission3.model.User
import com.panjikrisnayasa.submission3.view.MainActivity
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    companion object {
        const val BASE_URL_SEARCH = "https://api.github.com/search/users"
        const val QUERY_PARAM = "q"
        const val AUTHORITY = "da143e8f9d993165f64e5cc2b5c52cb2039ce35e"
    }

    private val mUsersData = MutableLiveData<ArrayList<User>>()
    private val mClient = AsyncHttpClient()

    fun setUser(username: String) {
        val uri = Uri.parse(BASE_URL_SEARCH).buildUpon()
            .appendQueryParameter(QUERY_PARAM, username)
            .build()
        val url = uri.toString()
        Log.d(MainActivity.TAG, url)
        mClient.addHeader("Authorization", AUTHORITY)
        mClient.addHeader("User-Agent", "request")
        mClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    Log.d(MainActivity.TAG, "setUser onSuccess")
                    var result = ""
                    if (responseBody != null)
                        result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    val listUsers = ArrayList<User>()
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val user = User()
                        user.avatarUrl = item.getString("avatar_url")
                        user.username = item.getString("login")
                        listUsers.add(user)
                    }
                    mUsersData.postValue(listUsers)
                } catch (e: Exception) {
                    Log.d(MainActivity.TAG, e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                if (error != null)
                    Log.d(MainActivity.TAG, "setUser ${error.message}")
            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return mUsersData
    }
}