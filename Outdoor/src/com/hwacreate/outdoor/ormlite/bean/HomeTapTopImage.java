package com.hwacreate.outdoor.ormlite.bean;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/** 主页中的列表 */
@DatabaseTable(tableName = "tb_hometaptopimage")
public class HomeTapTopImage implements Serializable {
	private static final long serialVersionUID = 2088378480551462163L;
	@DatabaseField(generatedId = true)
	private int id;
	/**
	 * 图片地址
	 */
	@DatabaseField(columnName = "imgUrl")
	private String imgUrl = null;

	public HomeTapTopImage() {
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
