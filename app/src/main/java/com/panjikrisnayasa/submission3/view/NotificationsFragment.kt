package com.panjikrisnayasa.submission3.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.panjikrisnayasa.submission3.R

class NotificationsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var mKeyPrefDailyReminder: String
    private var mIsDailyReminderOn: SwitchPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        mKeyPrefDailyReminder = resources.getString(R.string.preference_key_daily_reminder)
        mIsDailyReminderOn = findPreference(mKeyPrefDailyReminder)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences != null) {
            when (key) {
                mKeyPrefDailyReminder -> {
//                    if (sharedPreferences.getBoolean(mKeyPrefDailyReminder, false)) {
//
//                    } else {
//
//                    }
                }
            }
        }
    }

}