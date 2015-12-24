package com.hwacreate.outdoor.ormlite.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_gpsinfo")
public class GpsInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8862478006529831254L;
	@DatabaseField(generatedId = true)
	private int id;
	/***轨迹数据内容模板：
	[
	    {
	        "name": "",//地点名称
	        "describe": "",地点描述
	        "longitude": "",//经度
	        "latitude": ""//纬度
	    }
	]
	*/
	@DatabaseField(columnName = "locationInfo")
	private String locationInfo;// 轨迹信息
	@DatabaseField(columnName = "name")
	private String name; // 轨迹名称必填
	@DatabaseField(columnName = "start_time")
	private String start_time; //开始时间
	@DatabaseField(columnName = "end_time")
	private String end_time; ///结束时间

	public GpsInfo() {

	}
	public String getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

}
