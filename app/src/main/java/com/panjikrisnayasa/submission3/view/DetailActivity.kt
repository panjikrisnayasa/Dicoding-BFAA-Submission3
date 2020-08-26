package com.panjikrisnayasa.submission3.view

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.adapter.FollowerFollowingAdapter
import com.panjikrisnayasa.submission3.db.UserDatabaseContract
import com.panjikrisnayasa.submission3.db.UserHelper
import com.panjikrisnayasa.submission3.helper.MappingHelper
import com.panjikrisnayasa.submission3.model.User
import com.panjikrisnayasa.submission3.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USER = "user"
        const val EXTRA_USERNAME = "username"
        const val EXTRA_POSITION = "position"
        const val REQUEST_UPDATE = 100
        const val RESULT_DELETE = 101
        const val RESULT_ADD = 102
    }

    private lateinit var mViewModel: DetailViewModel
    private lateinit var mUserHelper: UserHelper
    private lateinit var mUser: User
    private var mIsFavored = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mUserHelper = UserHelper.getInstance(applicationContext)
        mUserHelper.open()

        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        floating_detail_favorite.setOnClickListener(this)

        showLoading(true)

        val extraUser = intent.getParcelableExtra<User>(EXTRA_USER)
        val extraUsername = intent.getStringExtra(EXTRA_USERNAME)

        if (extraUser != null) {
            showFollowerFollowing(extraUser.username)
            showUserDetail(extraUser)
        } else if (extraUsername != null) {
            showFollowerFollowing(extraUsername)
            mViewModel.setUserDetail(extraUsername)
            mViewModel.getUserDetail().observe(this, Observer { user ->
                if (user != null) {
                    showUserDetail(user)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mUserHelper.close()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.floating_detail_favorite -> {
                val extraPosition = intent.getIntExtra(EXTRA_POSITION, 0)
                val intent = Intent()
                mIsFavored = if (mIsFavored) {
                    mUserHelper.deleteByUsername(mUser.username)
                    Snackbar.make(
                        v,
                        getString(R.string.detail_snackbar_deleted_favorite),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    floating_detail_favorite.setImageResource(R.drawable.ic_favorite_border_24dp)
                    intent.putExtra(EXTRA_USER, mUser)
                    intent.putExtra(EXTRA_POSITION, extraPosition)
                    setResult(RESULT_DELETE, intent)
                    false
                } else {
                    val values = ContentValues()
                    values.put(UserDatabaseContract.UserColumns.USERNAME, mUser.username)
                    values.put(UserDatabaseContract.UserColumns.AVATAR, mUser.avatar)
                    values.put(UserDatabaseContract.UserColumns.AVATAR_URL, mUser.avatarUrl)
                    values.put(UserDatabaseContract.UserColumns.NAME, mUser.name)
                    values.put(
                        UserDatabaseContract.UserColumns.REPOSITORY_COUNT,
                        mUser.repositoryCount
                    )
                    values.put(UserDatabaseContract.UserColumns.FOLLOWER_COUNT, mUser.followerCount)
                    values.put(
                        UserDatabaseContract.UserColumns.FOLLOWING_COUNT,
                        mUser.followingCount
                    )
                    values.put(UserDatabaseContract.UserColumns.COMPANY, mUser.company)
                    values.put(UserDatabaseContract.UserColumns.LOCATION, mUser.location)
                    mUserHelper.insert(values)

                    Snackbar.make(
                        v,
                        getString(R.string.detail_snackbar_added_favorite),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    floating_detail_favorite.setImageResource(R.drawable.ic_favorite_24dp)
                    setResult(RESULT_ADD, intent)
                    true
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state)
            progress_detail_loading.visibility = View.VISIBLE
        else
            progress_detail_loading.visibility = View.GONE
    }

    private fun showUserDetail(user: User) {
        showLoading(false)
        supportActionBar?.title = user.name

        mUser = user
        checkUser(user.username)

        Glide.with(this)
            .load(
                if (user.avatar != 0)
                    user.avatar
                else
                    user.avatarUrl
            )
            .into(circle_image_detail_avatar)
        text_detail_name.text = user.name
        text_detail_username.text = user.username

        if (user.company == "null")
            text_detail_company.text = "-"
        else
            text_detail_company.text = user.company

        if (user.location == "null")
            text_detail_location.text = "-"
        else
            text_detail_location.text = user.location

        text_detail_repository_count.text = user.repositoryCount.toString()
        text_detail_follower_count.text = user.followerCount.toString()
        text_detail_following_count.text = user.followingCount.toString()
    }

    private fun showFollowerFollowing(username: String) {
        view_pager_detail_follower_following.adapter =
            FollowerFollowingAdapter(
                this,
                supportFragmentManager,
                username,
                FollowerListFragment(),
                FollowingListFragment()
            )
        tab_detail_follower_following.setupWithViewPager(view_pager_detail_follower_following)
    }

    private fun checkUser(username: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = mUserHelper.queryByUsername(username)
                MappingHelper.mapFavoriteUserCursorToArrayList(cursor)
            }
            val userList = deferredNotes.await()
            if (userList.size > 0) {
                floating_detail_favorite.setImageResource(R.drawable.ic_favorite_24dp)
                mIsFavored = true
                Log.d(MainActivity.TAG, "user saved in db")
            } else {
                Log.d(MainActivity.TAG, "user not saved in db")
            }
        }
    }
}