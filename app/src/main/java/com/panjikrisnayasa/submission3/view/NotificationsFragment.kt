package com.panjikrisnayasa.submission3.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.util.AlarmReceiver

class NotificationsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var mKeyPrefDailyReminder: String
    private lateinit var mAlarmReceiver: AlarmReceiver
    private var mIsDailyReminderOn: SwitchPreference? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        mKeyPrefDailyReminder = resources.getString(R.string.preference_key_daily_reminder)
        mIsDailyReminderOn = findPreference(mKeyPrefDailyReminder)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAlarmReceiver = AlarmReceiver()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (sharedPreferences != null) {
            when (key) {
                mKeyPrefDailyReminder -> {
                    if (sharedPreferences.getBoolean(mKeyPrefDailyReminder, false)) {
                        mAlarmReceiver.setRepeatingAlarm(context)
                        Toast.makeText(context, "Daily reminder turned on", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        mAlarmReceiver.cancelAlarm(context)
                        Toast.makeText(context, "Daily reminder turned off", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}