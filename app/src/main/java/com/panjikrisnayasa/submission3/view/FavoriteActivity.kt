package com.panjikrisnayasa.submission3.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.adapter.UsersAdapter
import com.panjikrisnayasa.submission3.db.UserHelper
import com.panjikrisnayasa.submission3.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var mUsersAdapter: UsersAdapter
    private lateinit var mUserHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorite_text_title)
        invalidateOptionsMenu()

        mUserHelper = UserHelper.getInstance(this)
        mUserHelper.open()

        showRecycler()
        loadFavoriteUserAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        mUserHelper.close()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == DetailActivity.REQUEST_UPDATE) {
                if (resultCode == DetailActivity.RESULT_DELETE) {
                    val position = data.getIntExtra(DetailActivity.EXTRA_POSITION, 0)
                    mUsersAdapter.removeItem(position)
                    if (mUsersAdapter.mUsersData.size == 0)
                        text_favorite_no_data.visibility = View.VISIBLE
                } else if (resultCode == DetailActivity.RESULT_ADD) {
                    Log.d(MainActivity.TAG, "resultCode = result add")
                }
            }
        }
    }

    private fun showRecycler() {
        mUsersAdapter = UsersAdapter()
        mUsersAdapter.notifyDataSetChanged()
        recycler_favorite_user.setHasFixedSize(true)
        recycler_favorite_user.layoutManager = LinearLayoutManager(this)
        recycler_favorite_user.adapter = mUsersAdapter
    }

    private fun loadFavoriteUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = mUserHelper.queryAll()
                MappingHelper.mapFavoriteUserCursorToArrayList(cursor)
            }
            val userList = deferredNotes.await()
            if (userList.size > 0) {
                mUsersAdapter.setUsersData(userList)
            } else {
                text_favorite_no_data.visibility = View.VISIBLE
            }
        }
    }
}