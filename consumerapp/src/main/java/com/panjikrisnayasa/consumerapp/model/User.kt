package com.panjikrisnayasa.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var username: String = "",
    var name: String = "",
    var avatar: Int = 0,
    var avatarUrl: String = "",
    var company: String = "",
    var location: String = "",
    var repositoryCount: Int = 0,
    var followerCount: Int = 0,
    var followingCount: Int = 0
) : Parcelable