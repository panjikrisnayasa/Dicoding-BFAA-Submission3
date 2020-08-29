package com.panjikrisnayasa.submission3.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.AUTHORITY
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.CONTENT_URI_USER
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.TABLE_NAME
import com.panjikrisnayasa.submission3.db.UserHelper

class UserProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USERNAME = 2
        private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var mUserHelper: UserHelper

        init {
            // content://com.panjikrisnayasa.submission3.user/username
            mUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)

            // content://com.panjikrisnayasa.submission3.user/user/username
            mUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USERNAME)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted = when (USERNAME) {
            mUriMatcher.match(uri) -> mUserHelper.deleteByUsername(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_USER, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added = when (USER) {
            mUriMatcher.match(uri) -> mUserHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_USER, null)
        return Uri.parse("$CONTENT_URI_USER/$added")
    }

    override fun onCreate(): Boolean {
        mUserHelper = UserHelper.getInstance(context as Context)
        mUserHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (mUriMatcher.match(uri)) {
            USER -> mUserHelper.queryAll()
            USERNAME -> mUserHelper.queryByUsername(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
