package com.panjikrisnayasa.submission3.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.`object`.UserData
import com.panjikrisnayasa.submission3.adapter.UserSearchAdapter
import com.panjikrisnayasa.submission3.adapter.UsersAdapter
import com.panjikrisnayasa.submission3.model.User
import com.panjikrisnayasa.submission3.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "hyperLoop"
    }

    private var mListUserData = arrayListOf<User>()
    private lateinit var mViewModel: MainViewModel
    private lateinit var mUsersAdapter: UsersAdapter
    private lateinit var mUserSearchAdapter: UserSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        recycler_main_user.setHasFixedSize(true)
        recycler_main_user.layoutManager = LinearLayoutManager(this)
        mListUserData.addAll(UserData.listUserData)
        showRecyclerUsers()

        search_main_user.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    showLoading(true)
                    showRecyclerUserSearch()
                    mViewModel.setUser(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    showRecyclerUsers()
                    mUsersAdapter.setUsersData(mListUserData)
                }
                return false
            }
        })

        mViewModel.getUser().observe(this, Observer { users ->
            if (users != null) {
                Log.d(TAG, "getUser observe users != null")
                showLoading(false)
                mUserSearchAdapter.setUserSearchData(users)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_favorite -> {
                val favoriteIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
            }
            R.id.menu_main_notifications -> {
                val notificationsIntent = Intent(this, NotificationsActivity::class.java)
                startActivity(notificationsIntent)
            }
            R.id.menu_main_settings -> {
                val settingsIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(settingsIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerUsers() {
        mUsersAdapter = UsersAdapter()
        mUsersAdapter.setUsersData(mListUserData)
        mUsersAdapter.notifyDataSetChanged()
        recycler_main_user.adapter = mUsersAdapter
    }

    private fun showRecyclerUserSearch() {
        mUserSearchAdapter = UserSearchAdapter()
        mUserSearchAdapter.notifyDataSetChanged()
        recycler_main_user.adapter = mUserSearchAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state)
            progress_main_loading.visibility = View.VISIBLE
        else
            progress_main_loading.visibility = View.GONE
    }
}