package com.panjikrisnayasa.consumerapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.panjikrisnayasa.consumerapp.R

/**
 * Implementation of App Widget functionality.
 */
class FavoriteUserWidget : AppWidgetProvider() {

    companion object {

        const val EXTRA_ITEM = "com.panjikrisnayasa.consumerapp.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackUserWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_user_widget)
            views.setRemoteAdapter(R.id.stack_widget, intent)
            views.setEmptyView(
                R.id.stack_widget,
                R.id.text_widget_no_data
            )

            appWidgetManager.notifyAppWidgetViewDataChanged(
                appWidgetId,
                R.id.stack_widget
            )
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}