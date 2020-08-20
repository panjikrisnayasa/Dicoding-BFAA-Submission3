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
import org.json.JSONArray

class FollowerListViewModel : ViewModel() {

    private val mFollowersData = MutableLiveData<ArrayList<User>>()
    private val mClient = AsyncHttpClient()

    fun setFollowers(username: String) {
        val uri = Uri.parse(DetailViewModel.BASE_URL_DETAIL).buildUpon()
            .appendPath(username)
            .appendPath("followers")
            .build()
        val url = uri.toString()
        Log.d(MainActivity.TAG, url)
        mClient.addHeader("Authorization", MainViewModel.AUTHORITY)
        mClient.addHeader("User-Agent", "request")
        mClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    var result = ""
                    if (responseBody != null)
                        result = String(responseBody)
                    val responseObject = JSONArray(result)
                    val listFollowers = ArrayList<User>()
                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val user = User()
                        user.avatarUrl = item.getString("avatar_url")
                        user.username = item.getString("login")
                        listFollowers.add(user)
                    }
                    mFollowersData.postValue(listFollowers)
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
                    Log.d(MainActivity.TAG, "setFollowers ${error.message}")
            }
        })
    }

    fun getFollowers(): LiveData<ArrayList<User>> {
        return mFollowersData
    }
}