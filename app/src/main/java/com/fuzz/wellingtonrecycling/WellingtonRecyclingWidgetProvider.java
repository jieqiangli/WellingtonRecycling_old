package com.fuzz.wellingtonrecycling;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by admin on 9/07/2014.
 */
public class WellingtonRecyclingWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // initializing widget layout
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),  R.layout.app_widget);

        updateWidget(context, remoteViews, appWidgetIds);
    }

    public static void updateWidget(Context context, RemoteViews remoteViews, int[] appWidgetIds) {
//        remoteViews.setTextViewText(R.id.widget_text, "" + Math.random() * 1000);

        WellingtonRecyclingWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews, appWidgetIds);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews, int[] appWidgetIds) {
        ComponentName myWidget = new ComponentName(context, WellingtonRecyclingWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
//        manager.updateAppWidget(myWidget, remoteViews);
        manager.updateAppWidget(appWidgetIds[0], remoteViews);
        Log.d("", "Should've updated");
//        manager.notifyAppWidgetViewDataChanged(appWidgetIds, appWidgetIds[0]);
    }
}
