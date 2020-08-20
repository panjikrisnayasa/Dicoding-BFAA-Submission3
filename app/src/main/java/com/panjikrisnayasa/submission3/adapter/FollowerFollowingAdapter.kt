package com.panjikrisnayasa.submission3.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.view.DetailActivity
import com.panjikrisnayasa.submission3.view.FollowerListFragment
import com.panjikrisnayasa.submission3.view.FollowingListFragment

class FollowerFollowingAdapter(
    private val mContext: Context,
    mFragmentManager: FragmentManager,
    private val mUsername: String,
    private val mFollowerFragment: FollowerListFragment,
    private val mFollowingFragment: FollowingListFragment
) :
    FragmentPagerAdapter(mFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mTabTitles =
        intArrayOf(R.string.item_user_text_follower, R.string.item_user_text_following)

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString(DetailActivity.EXTRA_USERNAME, mUsername)
        mFollowerFragment.arguments = bundle
        mFollowingFragment.arguments = bundle

        when (position) {
            0 -> return mFollowerFragment
            1 -> return mFollowingFragment
        }
        return mFollowerFragment
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(mTabTitles[position])
    }
}