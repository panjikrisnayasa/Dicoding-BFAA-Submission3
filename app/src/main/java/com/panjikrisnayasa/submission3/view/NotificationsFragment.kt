package com.panjikrisnayasa.submission3.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.panjikrisnayasa.submission3.R

class NotificationsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}