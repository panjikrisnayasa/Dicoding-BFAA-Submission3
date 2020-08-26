package com.panjikrisnayasa.submission3.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.model.User
import com.panjikrisnayasa.submission3.view.DetailActivity
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

    fun addItem(user: User) {
        mUsersData.add(user)
        notifyItemInserted(mUsersData.size - 1)
    }

    fun updateItem(position: Int, user: User) {
        mUsersData[position] = user
        notifyItemChanged(position, user)
    }

    fun removeItem(position: Int) {
        this.mUsersData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mUsersData.size)
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(context)
                    .load(
                        if (user.avatar != 0)
                            user.avatar
                        else
                            user.avatarUrl
                    )
                    .into(circle_image_item_user_avatar)
                text_item_user_name.text = user.name
                text_item_user_username.text = user.username
                text_item_user_repository_count.text = user.repositoryCount.toString()
                text_item_user_follower_count.text = user.followerCount.toString()
                text_item_user_following_count.text = user.followingCount.toString()

                this.setOnClickListener {
                    val intentDetail = Intent(context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_USER, user)
                    intentDetail.putExtra(DetailActivity.EXTRA_POSITION, adapterPosition)
                    (context as Activity).startActivityForResult(
                        intentDetail,
                        DetailActivity.REQUEST_UPDATE
                    )
                }
            }
        }
    }
}