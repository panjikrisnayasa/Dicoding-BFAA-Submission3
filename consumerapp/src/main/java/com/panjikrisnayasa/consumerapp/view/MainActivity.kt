package com.panjikrisnayasa.consumerapp.view

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.consumerapp.R
import com.panjikrisnayasa.consumerapp.adapter.UsersAdapter
import com.panjikrisnayasa.consumerapp.db.UserDatabaseContract.UserColumns.Companion.CONTENT_URI_USER
import com.panjikrisnayasa.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mUsersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showRecyclerUsers()
        contentProviderExec()
        loadFavoredMoviesAsync()
    }

    private fun showRecyclerUsers() {
        recycler_main_user.setHasFixedSize(true)
        recycler_main_user.layoutManager = LinearLayoutManager(this)
        mUsersAdapter = UsersAdapter()
        recycler_main_user.adapter = mUsersAdapter
    }

    private fun contentProviderExec() {
        val handlerThread = HandlerThread("DataMoviesObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)

                loadFavoredMoviesAsync()
            }
        }

        contentResolver.registerContentObserver(
            CONTENT_URI_USER,
            true,
            myObserver
        )
    }

    private fun loadFavoredMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor =
                    contentResolver.query(
                        CONTENT_URI_USER,
                        null,
                        null,
                        null,
                        null
                    )
                MappingHelper.mapFavoriteUserCursorToArrayList(cursor)
            }
            val userList = deferredNotes.await()
            if (userList.size > 0) {
                text_main_no_data.visibility = View.GONE
                mUsersAdapter.setUsersData(userList)
            } else {
                mUsersAdapter.setUsersData(arrayListOf())
                text_main_no_data.visibility = View.VISIBLE
            }
        }
    }
}