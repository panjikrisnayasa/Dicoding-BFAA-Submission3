package com.panjikrisnayasa.submission3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.model.User
import com.panjikrisnayasa.submission3.view.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private val mUsersData = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsersData.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = mUsersData[position]

        Glide.with(holder.itemView.context)
            .load(
                if (user.avatar != 0)
                    user.avatar
                else
                    user.avatarUrl
            )
            .into(holder.imageAvatar)
        holder.textName.text = user.name
        holder.textUsername.text = user.username
        holder.textRepositoryCount.text = user.repositoryCount.toString()
        holder.textFollowerCount.text = user.followerCount.toString()
        holder.textFollowingCount.text = user.followingCount.toString()

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USER, user)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    fun setUsersData(users: ArrayList<User>) {
        mUsersData.clear()
        mUsersData.addAll(users)
        notifyDataSetChanged()
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView = itemView.findViewById(R.id.circle_image_item_user_avatar)
        var textName: TextView = itemView.findViewById(R.id.text_item_user_name)
        var textUsername: TextView = itemView.findViewById(R.id.text_item_user_username)
        var textRepositoryCount: TextView =
            itemView.findViewById(R.id.text_item_user_repository_count)
        var textFollowerCount: TextView = itemView.findViewById(R.id.text_item_user_follower_count)
        var textFollowingCount: TextView =
            itemView.findViewById(R.id.text_item_user_following_count)
    }
}