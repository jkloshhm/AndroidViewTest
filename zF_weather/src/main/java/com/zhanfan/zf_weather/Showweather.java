package com.zhanfan.zf_weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zhanfan.zf_weather.database.Sqlitedatabase;
import com.zhanfan.zf_weather.entity.Cityweather;
import com.zhanfan.zf_weather.entity.Dataforecast;
import com.zhanfan.zf_weather.tool.MyHttpClient;
import com.zhanfan.zf_weather.tool.ViewHolderAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Showweather extends Activity implements OnClickListener{
	private ProgressDialog pDialog;
	TextView  temp;
	TextView  locationcity; 
	TextView  textweather;
    ListView  listView;
    ImageView more;
    ImageView refresh;
    RelativeLayout relativeLayout=null;
    String woeid=null;
    String citysearch2222222;
    List<Cityweather> searchcity=null;
    int[] background = new int[] { R.drawable.clear_d, R.drawable.clear_n,
			R.drawable.cloudy_d, R.drawable.cloudy_n, R.drawable.foggy_d,
			R.drawable.foggy_n, R.drawable.rain_d, R.drawable.rain_n,
			R.drawable.snow_d, R.drawable.snow_n, R.drawable.storm_d,
			R.drawable.storm_n, R.drawable.sunny_bg, R.drawable.wind_bg,
			R.drawable.zmohubg };
    Handler handler=new Handler(){
    	public void handleMessage(android.os.Message msg) {
    		if(msg.what==0x08){
    			//sendhttpgetweatherinfo(woeid);
    			getweatherdata(woeid);
    		}else if(msg.what==0x09){
    			if(pDialog!=null){
    				pDialog.dismiss();
    				changelistview();
    			}
    			
    		}
    	};
    };
    public int getlastpictureid(String codestring) {
		int code = Integer.parseInt(codestring);
		if ((code == 0) || (code == 1) || (code == 2) || (code == 3)
				|| (code == 4) || (code == 37) || (code == 38) || (code == 39)
				|| (code == 47)) {
			return 10;
		} else if ((code == 36) || (code == 34) || (code == 32)) {
			return 12;
		} else if ((code == 23) || (code == 24) || (code == 25)) {
			return 13;
		} else if ((code == 31)) {
			return 0;
		} else if ((code == 33)) {
			return 1;
		} else if ((code == 26) || (code == 28) || (code == 30) || (code == 44)) {
			return 2;
		} else if ((code == 27) || (code == 29)) {
			return 3;
		} else if ((code == 19) || (code == 20) || (code == 21) || (code == 22)) {
			return 4;
		} else if ((code == 9) || (code == 11) || (code == 12) || (code == 10)
				|| (code == 40) || (code == 45)) {
			return 6;
		} else if ((code == 5) || (code == 6) || (code == 7) || (code == 8)
				|| (code == 13) || (code == 14) || (code == 15) || (code == 16)
				|| (code == 17) || (code == 18) || (code == 35) || (code == 41)
				|| (code == 42) || (code == 43) || (code == 46) ) {
			return 8;
		} else{
			return 0;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().hide();
		relativeLayout=(RelativeLayout) findViewById(R.id.relativelayout);
		temp=(TextView) findViewById(R.id.daytemp);
		locationcity=(TextView) findViewById(R.id.location);
		textweather=(TextView) findViewById(R.id.dayweather);
		more=(ImageView) findViewById(R.id.more);
		refresh=(ImageView) findViewById(R.id.refresh);
		refresh.setVisibility(View.INVISIBLE);
		more.setVisibility(View.INVISIBLE);
		showProgressDialog("Just a moment, please!!");
		listView=(ListView) findViewById(R.id.listView1);
		Intent intwoeid=getIntent();
		 woeid=intwoeid.getStringExtra("woeid");
		handler.sendEmptyMessage(0x08);
		
	    
	}

	public void getweatherdata(final String woeid){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				sendhttpgetweatherinfo(woeid);
			}
		}).start();
	}
	
	public void changelistview(){
		Log.i("viewdatadata", "citysearch----tttttttt  "+citysearch2222222);
		
        if((background==null)||(relativeLayout==null)||(listView==null)){//涓轰簡闃叉锛岀敤涓�浜涙潃姣掕蒋浠讹紝娓呯┖鍨冨溇鐨勬椂鍊欙紝灏嗘暟缁勬竻绌猴紝鎶ョ┖鎸囬拡寮傚父
          this.finish();
         }
         locationcity.setText(citysearch2222222);
		relativeLayout.setBackgroundResource(background[getlastpictureid(searchcity.get(0).getCode())]);
		listView.setAdapter(new ViewHolderAdapter(Showweather.this, searchcity));
	}
	private void showProgressDialog(String title)
	{
		Log.i("zy_log", "555555");
		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(true);
		pDialog.setMessage(title+"...");
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.show();
		Log.i("zy_log", "666666");
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	public void sendhttpgetweatherinfo(String woeidString) {
		String getData = null;
		Log.i("lllllllllllllllllllll", "sendhttpgetweatherinfo ");
		String stringtttt = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid="
				+ woeidString + "%20and%20u=%22c%22&format=json";

		try {

			// HttpClient httpClient = new DefaultHttpClient();
			HttpClient httpClient = MyHttpClient.getNewHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
					5000);//

			HttpGet httpGet = new HttpGet(stringtttt);

			HttpResponse httpResponse = httpClient.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				HttpEntity entity = httpResponse.getEntity();
				getData = EntityUtils.toString(entity, "utf-8");
				
				parseJSONWith(getData,woeidString);

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	
	}

	// JSon
	public List<Cityweather> parseJSONWith(String jsondata,String woeidString111) {
//		if (weatherinformation.size() != 0) {
//			Log.i("ttttzhaoyi_log", "weatherinformation.size  "
//					+ weatherinformation.size());
//			weatherinformation.clear();
//			Log.i("ttttzhaoyi_log", "weatherinformation.size222222  "
//					+ weatherinformation.size());
//		}  
		 searchcity=null;
         searchcity=new ArrayList<Cityweather>();
         if(searchcity.size()!=0){
        	 searchcity.clear();
        	 citysearch2222222=null;
         }
		try {
			JSONObject object = new JSONObject(jsondata);
			// JSONObject
			// ciji=object.getJSONObject("query").getJSONObject("results").getJSONObject("item").getJSONObject("condition");
			String tttt = (String) object.getJSONObject("query").get("created");
			JSONObject channel = object.getJSONObject("query")
					.getJSONObject("results").getJSONObject("channel");

			String string = channel.getString("title");

			citysearch2222222 = channel.getJSONObject("location").getString("city");
			Log.i("viewdatadata", "citysearch  "+citysearch2222222);
			//weatherinformation.add(city);
			Log.i("women", "city     " + citysearch2222222);
			String temp = channel.getJSONObject("item")
					.getJSONObject("condition").getString("temp");

			//weatherinformation.add(temp);
			String weather = channel.getJSONObject("item")
					.getJSONObject("condition").getString("text");

			//weatherinformation.add(weather);
			String code = channel.getJSONObject("item")
					.getJSONObject("condition").getString("code");
			//weatherinformation.add(code);
			JSONArray forecastweatherArray = channel.getJSONObject("item")
					.getJSONArray("forecast");
			 //获得到天气的能见度
			String vistbility=channel.getJSONObject("atmosphere").getString("visibility");
            //获得日出日落时间
            String sunrise=channel.getJSONObject("astronomy").getString("sunrise");
            String sunset=channel.getJSONObject("astronomy").getString("sunset");
            //打印得到的数据
            Log.i("zhaoyi_log1223","nengjiandu "+vistbility +"sunrise   "+sunrise +"sunset  "+sunset);
//			cityweathers = new ArrayList<Cityweather>();
//			if (cityweathers.size() != 0) {
//				cityweathers.clear();
//			}
           //鍒ゆ柇娣诲姞鐨勫煄甯傦紝 鏄惁鍦ㄦ暟鎹簱瀛樺湪
            if(!Sqlitedatabase.selectwoeidifexit(woeidString111)){

				String maxfid= Sqlitedatabase.Maxfid();
				if(("".equals(maxfid))||(maxfid==null)){
					maxfid="0";
				}else{
					maxfid=String.valueOf(Integer.parseInt(maxfid)+1);
				}
				Sqlitedatabase.insertwoeid(maxfid, woeid, citysearch2222222,0,temp,weather,sunrise,sunset,vistbility,code);
                Log.i("zhaoyi_log1124","鏂板姞鐨勫煄甯備互鍙婂ぉ姘斾俊鎭凡缁忓姞鍏ユ暟鎹簱");
            
			Cityweather cityweatherenty = null;
			for (int i = 0; i < 5; i++) {
				cityweatherenty = new Cityweather();
				cityweatherenty.setCode(forecastweatherArray.getJSONObject(i)
						.get("code").toString());
				cityweatherenty.setDay(forecastweatherArray.getJSONObject(i)
						.get("day").toString());
				cityweatherenty.setHightemp(forecastweatherArray
						.getJSONObject(i).get("high").toString());
				cityweatherenty.setLowtemp(forecastweatherArray
						.getJSONObject(i).get("low").toString());
				cityweatherenty.setWeathertext(forecastweatherArray
						.getJSONObject(i).get("text").toString());
				searchcity.add(cityweatherenty);
				
				String weekth=forecastweatherArray.getJSONObject(i)
						.get("day").toString();
				String forecasetemp=forecastweatherArray
						.getJSONObject(i).get("high").toString()+"℃ ~"+forecastweatherArray
						.getJSONObject(i).get("low").toString();
				String weatherdata=forecastweatherArray
						.getJSONObject(i).get("text").toString();
				String codeString=forecastweatherArray.getJSONObject(i)
						.get("code").toString();
                
		             String maxweatherid=Sqlitedatabase.Maxweatherid();
					Log.i("maxweatherid","maxweatheridtttttt  "+maxweatherid);   
					if(("".equals(maxweatherid))||(maxweatherid==null)){
						maxweatherid="0"; 
					}else{
						maxweatherid=String.valueOf(Integer.parseInt(maxweatherid)+1);
					
						Log.i("maxweatherid","maxweatherid  "+maxweatherid);
					} 
					Sqlitedatabase.insertweathdata(new Dataforecast(maxweatherid, woeid, weekth, forecasetemp, weatherdata, codeString));
                    Log.i("zhaoyi_log1124","棰勬姤澶╂皵浜斿ぉ鏁版嵁宸茬粡瀛樺叆鏁版嵁搴�");
             }  
			}

     	handler.sendEmptyMessage(0x09);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchcity;
	}
	
}
