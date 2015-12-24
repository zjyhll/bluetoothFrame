package com.hwacreate.outdoor.bean;

import com.keyhua.protocol.json.JSONArray;

public class HuoDongXiangQingDongTai {
	private String arrive_time = "";
	private String rarrive_lag = "";
	private String descript = "";
	private String images = "";
	private String act_id = "";
	private String arrive_lng = "";
	private String hty_recd_id = "";

	public HuoDongXiangQingDongTai() {
	}

	public String getHty_recd_id() {
		return hty_recd_id;
	}

	public void setHty_recd_id(String hty_recd_id) {
		this.hty_recd_id = hty_recd_id;
	}

	public String getArrive_time() {
		return arrive_time;
	}

	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}

	public String getRarrive_lag() {
		return rarrive_lag;
	}

	public void setRarrive_lag(String rarrive_lag) {
		this.rarrive_lag = rarrive_lag;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getArrive_lng() {
		return arrive_lng;
	}

	public void setArrive_lng(String arrive_lng) {
		this.arrive_lng = arrive_lng;
	}

}
