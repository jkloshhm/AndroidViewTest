package com.zhanfan.zf_weather;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Short2;
import android.util.Log;
import android.widget.RemoteViews;

public class Weather extends AppWidgetProvider { 
	String clickaction="com.zhanfanweather.click";
	RemoteViews remoteViews=null;
	/*
	 * AppWidgetProvider锛氱户鎵胯嚜BroadcastRecevier锛屽湪AppWidget搴旂敤update銆乪nable銆乨isable鍜宒elete鏃舵帴鏀堕�氱煡銆�
     * 鍏朵腑锛宱nUpdate銆乷nReceive鏄渶甯哥敤鍒扮殑鏂规硶锛屽畠浠帴鏀舵洿鏂伴�氱煡
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i("zhanfanzhaoyi_log", "8888888888888888");
		// TODO Auto-generated method stub
		//鍒涘缓RemoteView瀵硅薄
		 remoteViews = new RemoteViews(context.getPackageName(), R.layout.weatherwidget);
		//Updateservice.context=context;
		//Updateservice.remoteViews=remoteViews;
		//Updateservice.appwidetmanger=appWidgetManager;
		Intent intent=new Intent(context, Updateservice.class);
		context.startService(intent);
		
		
		Intent  intentclick =new Intent(clickaction);
		PendingIntent pendingIntent=PendingIntent.getBroadcast(context, 0, intentclick, 0);
		remoteViews.setOnClickPendingIntent(R.id.wholerelativelayout, pendingIntent);
		
		ComponentName componentName = new ComponentName(context, Weather.class);
		// 调用AppWidgetManager将remoteViews添加到ComponentName中
		appWidgetManager.updateAppWidget(componentName, remoteViews);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("zhanfanzhaoyi_log","111111111111");
		super.onReceive(context, intent);
		if(intent.getAction().equals(clickaction)){
			Log.i("zhanfanzhaoyi_log","2222222");

			Intent  intentclick =new Intent(context, MainActivity.class);
			intentclick.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
			context.startActivity(intentclick);
			
			
		}
	}
//	@Override
//	public void onAppWidgetOptionsChanged(Context context,
//			AppWidgetManager appWidgetManager, int appWidgetId,
//			Bundle newOptions) {
//		// TODO Auto-generated method stub
//		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
//				newOptions);
//	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(context, Updateservice.class);
		context.stopService(intent);
		super.onDeleted(context, appWidgetIds);
	}

}
