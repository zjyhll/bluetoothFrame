package com.hwacreate.outdoor.ormlite.bean;

import java.io.Serializable;

public class TraceInfoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4917154116707009638L;
	/**
	 * 
	 */
	private String name;// 地点名称
	private String describe;// 地点描述
	private String longitude;// 经度
	private String latitude;// 纬度

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
