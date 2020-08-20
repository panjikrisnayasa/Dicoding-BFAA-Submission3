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

class FollowingListViewModel : ViewModel() {

    private val mFollowingData = MutableLiveData<ArrayList<User>>()
    private val mClient = AsyncHttpClient()

    fun setFollowing(username: String) {
        val uri = Uri.parse(DetailViewModel.BASE_URL_DETAIL).buildUpon()
            .appendPath(username)
            .appendPath("following")
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
                    val listFollowing = ArrayList<User>()
                    for (i in 0 until responseObject.length()) {
                        val item = responseObject.getJSONObject(i)
                        val user = User()
                        user.avatarUrl = item.getString("avatar_url")
                        user.username = item.getString("login")
                        listFollowing.add(user)
                    }
                    mFollowingData.postValue(listFollowing)
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
                    Log.d(MainActivity.TAG, "setFollowing ${error.message}")
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return mFollowingData
    }
}