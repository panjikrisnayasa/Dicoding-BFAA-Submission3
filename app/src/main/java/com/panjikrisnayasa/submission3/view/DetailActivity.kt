package com.panjikrisnayasa.submission3.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.adapter.FollowerFollowingAdapter
import com.panjikrisnayasa.submission3.model.User
import com.panjikrisnayasa.submission3.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "user"
        const val EXTRA_USERNAME = "username"
    }

    private lateinit var mViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

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
}