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

class DetailViewModel : ViewModel() {

    companion object {
        const val BASE_URL_DETAIL = "https://api.github.com/users"
    }

    private val mUserDetailData = MutableLiveData<User>()
    private val mClient = AsyncHttpClient()

    fun setUserDetail(username: String) {
        val uri = Uri.parse(BASE_URL_DETAIL).buildUpon()
            .appendPath(username)
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
                    Log.d(MainActivity.TAG, "setUserDetail onSuccess")
                    var result = ""
                    if (responseBody != null)
                        result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val user = User()
                    user.name = responseObject.getString("name")
                    user.username = responseObject.getString("login")
                    user.avatarUrl = responseObject.getString("avatar_url")
                    user.company = responseObject.getString("company")
                    user.location = responseObject.getString("location")
                    user.repositoryCount =
                        responseObject.getString("public_repos").toInt()
                    user.followerCount =
                        responseObject.getString("followers").toInt()
                    user.followingCount =
                        responseObject.getString("following").toInt()
                    mUserDetailData.postValue(user)
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
                    Log.d(MainActivity.TAG, "setUserDetail ${error.message}")
            }
        })
    }

    fun getUserDetail(): LiveData<User> {
        return mUserDetailData
    }
}