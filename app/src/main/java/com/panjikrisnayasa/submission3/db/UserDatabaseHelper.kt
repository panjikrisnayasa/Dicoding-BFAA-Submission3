package com.panjikrisnayasa.submission3.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.AVATAR
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.AVATAR_URL
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.COMPANY
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.FOLLOWER_COUNT
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.FOLLOWING_COUNT
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.LOCATION
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.NAME
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.REPOSITORY_COUNT
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.TABLE_NAME
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion.USERNAME
import com.panjikrisnayasa.submission3.db.UserDatabaseContract.UserColumns.Companion._ID

internal class UserDatabaseHelper(mContext: Context) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_github_user_app"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USERNAME TEXT NOT NULL," +
                " $AVATAR INTEGER NOT NULL," +
                " $AVATAR_URL TEXT NOT NULL," +
                " $NAME TEXT NOT NULL," +
                " $REPOSITORY_COUNT INTEGER NOT NULL," +
                " $FOLLOWER_COUNT INTEGER NOT NULL," +
                " $FOLLOWING_COUNT INTEGER NOT NULL," +
                " $COMPANY TEXT NOT NULL," +
                " $LOCATION TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}