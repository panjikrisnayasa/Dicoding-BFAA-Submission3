package com.panjikrisnayasa.submission3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.model.User
import com.panjikrisnayasa.submission3.view.DetailActivity
import kotlinx.android.synthetic.main.item_user_search.view.*

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
        holder.bind(mUserSearchData[position])
    }

    fun setUserSearchData(users: ArrayList<User>) {
        mUserSearchData.clear()
        mUserSearchData.addAll(users)
        notifyDataSetChanged()
    }

    class UserSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(context)
                    .load(user.avatarUrl)
                    .into(circle_image_item_user_search_avatar)
                text_item_user_search_username.text = user.username

                this.setOnClickListener {
                    val intentDetail = Intent(context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                    context.startActivity(intentDetail)
                }
            }
        }
    }
}