package com.fuzz.wellingtonrecycling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by admin on 9/07/2014.
 */
public class WellingtonRecyclingIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.fuzz.wellingtonrecycling.intent.action.UPDATE_WIDGET")) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        }
    }
}
