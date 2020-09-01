package com.panjikrisnayasa.consumerapp.widget

import android.content.Intent
import android.widget.RemoteViewsService

class StackUserWidgetService: RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackUserRemoteViewsFactory(this.applicationContext)
    }
}