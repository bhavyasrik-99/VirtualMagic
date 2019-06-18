package com.example.virtualmagic;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {

    SharedPreferences sp;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int myId:appWidgetIds){
            sp=context.getSharedPreferences("bhavya",Context.MODE_PRIVATE);
            String title;
            title=sp.getString("titlename","No Data");
            Intent i;
            i=new Intent(context,MainActivity.class);
            PendingIntent pi;
            pi= PendingIntent.getActivity(context,1, i,0);
            RemoteViews v;
            v=new RemoteViews(context.getPackageName(),R.layout.widgetdesign);
            v.setTextViewText(R.id.herotitle,title);
            v.setOnClickPendingIntent(R.id.herotitle,pi);
            appWidgetManager.updateAppWidget(myId,v);

        }
    }
}
