package com.panjikrisnayasa.submission3.db

import android.provider.BaseColumns

internal class UserDatabaseContract {

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "user"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
            const val AVATAR_URL = "avatar_url"
            const val NAME = "name"
            const val REPOSITORY_COUNT = "repository_count"
            const val FOLLOWER_COUNT = "follower_count"
            const val FOLLOWING_COUNT = "following_count"
            const val COMPANY = "company"
            const val LOCATION = "location"
        }
    }
}