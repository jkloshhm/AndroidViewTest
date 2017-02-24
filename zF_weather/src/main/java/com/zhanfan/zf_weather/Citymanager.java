package com.zhanfan.zf_weather;

import com.zhanfan.zf_weather.database.Sqlitedatabase;
import com.zhanfan.zf_weather.entity.Defaultcity;
import com.zhanfan.zf_weather.sidedelete.SwipeListView;
import com.zhanfan.zf_weather.tool.Sidebaseadapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class Citymanager extends Activity {
	SwipeListView citylist;
	Switch citysSwitch;
	Sidebaseadapter sidebaseadapter;
	boolean setdefault;
	Defaultcity defaultcity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citymanager);
		getActionBar().hide();
		IntentFilter intentFilter = new IntentFilter(
				"com.zhanfan.defaultcitychanged");
		intentFilter.addAction("com.zhanfan.againlocation");
		registerReceiver(defaultcitychange, intentFilter);
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connectivityManager
				.getActiveNetworkInfo();
		// citylist = (ListView) findViewById(R.id.city_listView);
		citylist = (SwipeListView) findViewById(R.id.swipeListView1);
		citysSwitch = (Switch) findViewById(R.id.switch1);
		defaultcity = Sqlitedatabase.selectcitydefaultboolen();
		if (defaultcity != null) {
			setdefault = defaultcity.isSetdefault();
		}
		// setdefault=Sqlitedatabase.selectcitydefaultboolen().isSetdefault();

		// Log.i("zhaoyi_log1124",
		// "leilei  "+Sqlitedatabase.selectcitydefaultboolen().toString());
		Log.i("zhaoyi_log1124", "boolean  " + setdefault);
		citysSwitch.setChecked(!setdefault);

		if (networkInfo != null && networkInfo.isAvailable()) {
			citysSwitch.setEnabled(true);
		} else {
			citysSwitch.setEnabled(false);
			Toast.makeText(Citymanager.this, "Please check net",
					Toast.LENGTH_LONG).show();
		}
		updateadapter();
		citysSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
             
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Log.i("zhaoyi_log1124", "1213131313131313");
				if (!citysSwitch.isChecked()) {
					citysSwitch.setChecked(false);
					Log.i("zhaoyi_log1124", "guanbi");
				} else {
					citysSwitch.setChecked(true);
					Log.i("zhaoyi_log1124", "dakai"); 
					Sqlitedatabase.update2citydefault(0);
					Intent intent = new Intent();
					intent.setAction("com.zhanfan.zidonglocation");
					sendBroadcast(intent);
					updateadapter();
				}

			}
		});
		
	} 
	
	public void updateadapter(){
		if (Sqlitedatabase.selectwoeid() != null) {
			sidebaseadapter = new Sidebaseadapter(Sqlitedatabase.selectwoeid(),
					Citymanager.this);
			Log.i("zhaoyi_log1216", "cityname  "
					+ Sqlitedatabase.selectwoeid().size());
			for (int i = 0; i < Sqlitedatabase.selectwoeid().size(); i++) {
				Log.i("zhaoyi_log1216", "cityname  "
						+ Sqlitedatabase.selectwoeid().get(i).getCityname());
			}
			citylist.setAdapter(sidebaseadapter);
		}
	}
	@Override
    protected void onDestroy() {
		super.onPause();    
    	unregisterReceiver(defaultcitychange);
    }
	BroadcastReceiver defaultcitychange = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("zhaoyi_log1204", "onreciver");
			if(intent.getAction().equals("com.zhanfan.defaultcitychanged")){
				citysSwitch.setChecked(false);
				updateadapter();
				sidebaseadapter.notifyDataSetChanged();
			}else if(intent.getAction().equals("com.zhanfan.againlocation")){
				//sidebaseadapter.notifyDataSetChanged();
				updateadapter();
			}
			
		}

	};

}
