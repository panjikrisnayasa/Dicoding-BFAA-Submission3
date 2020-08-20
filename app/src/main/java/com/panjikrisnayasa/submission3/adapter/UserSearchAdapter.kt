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

class UserSearchAdapter : RecyclerView.Adapter<UserSearchAdapter.UserSearchViewHolder>() {

    private val mUserSearchData = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_search, parent, false)
        return UserSearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUserSearchData.size
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val user = mUserSearchData[position]

        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.imageAvatar)
        holder.textUsername.text = user.username

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    fun setUserSearchData(users: ArrayList<User>) {
        mUserSearchData.clear()
        mUserSearchData.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView =
            itemView.findViewById(R.id.circle_image_item_user_search_avatar)
        var textUsername: TextView = itemView.findViewById(R.id.text_item_user_search_username)
    }
}