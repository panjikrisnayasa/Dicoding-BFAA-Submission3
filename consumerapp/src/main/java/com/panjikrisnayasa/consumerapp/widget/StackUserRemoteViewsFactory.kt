package com.panjikrisnayasa.consumerapp.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.panjikrisnayasa.consumerapp.R
import com.panjikrisnayasa.consumerapp.db.UserDatabaseContract.UserColumns.Companion.CONTENT_URI_USER
import com.panjikrisnayasa.consumerapp.helper.MappingHelper
import com.panjikrisnayasa.consumerapp.model.User
import com.panjikrisnayasa.consumerapp.widget.FavoriteUserWidget.Companion.EXTRA_ITEM

class StackUserRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var mUserList = ArrayList<User>()

    override fun onCreate() {}

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        mUserList.clear()

        val cursor = mContext.contentResolver.query(
            CONTENT_URI_USER,
            null,
            null,
            null,
            null
        )
        val userList = MappingHelper.mapFavoriteUserCursorToArrayList(cursor)
        mUserList.addAll(userList)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.item_widget_favorite_user)

        val bitmap =
            Glide.with(this.mContext).asBitmap().load(
                if (mUserList[position].avatarUrl != "")
                    mUserList[position].avatarUrl
                else
                    mUserList[position].avatar
            )
                .submit(512, 512).get()
        remoteViews.setImageViewBitmap(R.id.image_item_widget_favorite_user, bitmap)

        val extras = bundleOf(EXTRA_ITEM to position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.image_item_widget_favorite_user, fillInIntent)
        return remoteViews
    }

    override fun getCount(): Int {
        return mUserList.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {}
}