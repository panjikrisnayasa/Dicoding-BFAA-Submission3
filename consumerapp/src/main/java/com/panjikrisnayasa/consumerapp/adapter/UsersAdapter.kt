package com.panjikrisnayasa.consumerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.consumerapp.R
import com.panjikrisnayasa.consumerapp.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    var mUsersData = ArrayList<User>()
        set(mUsersData) {
            if (mUsersData.size > 0) {
                this.mUsersData.clear()
            }
            this.mUsersData.addAll(mUsersData)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsersData.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(mUsersData[position])
    }

    fun setUsersData(users: ArrayList<User>) {
        mUsersData.clear()
        mUsersData.addAll(users)
        notifyDataSetChanged()
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(context)
                    .load(
                        if (user.avatar != 0) {
                            user.avatar
                        } else {
                            user.avatarUrl
                        }
                    )
                    .into(circle_image_item_user_avatar)
                text_item_user_name.text = user.name
                text_item_user_username.text = user.username
                text_item_user_repository_count.text = user.repositoryCount.toString()
                text_item_user_follower_count.text = user.followerCount.toString()
                text_item_user_following_count.text = user.followingCount.toString()
            }
        }
    }
}