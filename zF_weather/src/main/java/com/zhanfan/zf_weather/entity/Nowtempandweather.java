package com.zhanfan.zf_weather.entity;

public class Nowtempandweather {
	private String temString;
	private String weatherinforString;
	private String sunrise;
	private String sunset;
	private String visibility;
    private String nowcode;
	
   
	public Nowtempandweather(String temString, String weatherinforString,
			String sunrise, String sunset, String visibility, String nowcode) {
		super();
		this.temString = temString;
		this.weatherinforString = weatherinforString;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.visibility = visibility;
		this.nowcode = nowcode;
	}

	public String getNowcode() {
		return nowcode;
	}

	public void setNowcode(String nowcode) {
		this.nowcode = nowcode;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getTemString() {
		return temString;
	}

	public void setTemString(String temString) {
		this.temString = temString;
	}

	public String getWeatherinforString() {
		return weatherinforString;
	}

	public void setWeatherinforString(String weatherinforString) {
		this.weatherinforString = weatherinforString;
	}

}
