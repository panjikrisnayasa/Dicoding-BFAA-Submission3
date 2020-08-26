package com.panjikrisnayasa.submission3.helper

import android.database.Cursor
import com.panjikrisnayasa.submission3.db.UserDatabaseContract
import com.panjikrisnayasa.submission3.model.User

object MappingHelper {

    fun mapFavoriteUserCursorToArrayList(userCursor: Cursor?): ArrayList<User> {
        val userList = ArrayList<User>()
        userCursor?.moveToFirst()
        if (userCursor != null) {
            if (userCursor.isFirst) {
                do {
                    val username = userCursor.getString(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.USERNAME)
                    )
                    val avatar = userCursor.getInt(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.AVATAR)
                    )
                    val avatarUrl = userCursor.getString(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.AVATAR_URL)
                    )
                    val name = userCursor.getString(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.NAME)
                    )
                    val repositoryCount = userCursor.getInt(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.REPOSITORY_COUNT)
                    )
                    val followerCount = userCursor.getInt(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.FOLLOWER_COUNT)
                    )
                    val followingCount = userCursor.getInt(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.FOLLOWING_COUNT)
                    )
                    val company = userCursor.getString(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.COMPANY)
                    )
                    val location = userCursor.getString(
                        userCursor.getColumnIndexOrThrow(UserDatabaseContract.UserColumns.LOCATION)
                    )
                    userList.add(
                        User(
                            username,
                            name,
                            avatar,
                            avatarUrl,
                            company,
                            location,
                            repositoryCount,
                            followerCount,
                            followingCount
                        )
                    )
                } while (userCursor.moveToNext())
            }
        }
        return userList
    }
}