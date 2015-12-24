package com.hwacreate.outdoor.ormlite.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/** 组织中的列表 */
@DatabaseTable(tableName = "tb_zhuzhitapitem")
public class ZhuZhiTapItemBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4020760629698161272L;
	@DatabaseField(generatedId = true)
	private int id;
	/**
	 * 图片地址
	 */
	@DatabaseField(columnName = "imgUrl")
	private String imgUrl = null;
	/**
	 * 俱乐部名字
	 */
	@DatabaseField(columnName = "tv_clob")
	private String tv_clob;
	/**
	 * 在哪个省
	 */
	@DatabaseField(columnName = "tv_Province")
	private String tv_Province;
	/**
	 * 在哪个城市
	 */
	@DatabaseField(columnName = "tv_town")
	private String tv_town;
	/**
	 * 成员个数
	 */
	@DatabaseField(columnName = "tv_chengyuan")
	private String tv_chengyuan;
	/**
	 * 参加活动人数
	 */
	@DatabaseField(columnName = "tv_huodong")
	private String tv_huodong;
	/**
	 * 是否参加或者是加入
	 */
	@DatabaseField(columnName = "tv_join")
	private String tv_join;
	/**
	 * 数据库传入的类型，一共有三个，分别是我的，同城，推荐
	 */
	@DatabaseField(columnName = "type")
	private Integer type = null;

	public static List<ZhuZhiTapItemBean> zhuZhiTapWoDeBeanList = new ArrayList<ZhuZhiTapItemBean>();

	static {
		zhuZhiTapWoDeBeanList.add(new ZhuZhiTapItemBean(
				"http://www.33.la/uploads/zmbz/fengguang/sjjzrfggqz_45863.jpg",
				"上海光棍俱乐部", "上海市", "黄浦区", "520", "1314", "加入", 1));
		zhuZhiTapWoDeBeanList.add(new ZhuZhiTapItemBean(
				"http://www.33.la/uploads/zmbz/fengguang/sjjzrfggqz_45864.jpg",
				"上海光棍俱乐部", "上海市", "黄浦区", "520", "1314", "加入", 3));
		zhuZhiTapWoDeBeanList
				.add(new ZhuZhiTapItemBean(
						"http://d.hiphotos.bdimg.com/album/pic/item/dc54564e9258d10970e37c8cd358ccbf6c814d88.jpg",
						"上海光棍俱乐部", "上海市", "黄浦区", "520", "1314", "加入", 2));
		zhuZhiTapWoDeBeanList
				.add(new ZhuZhiTapItemBean(
						"http://www.ytxww.com/upload/image/201205/20120517104607730507.jpg",
						"上海光棍俱乐部", "上海市", "黄浦区", "520", "1314", "加入", 2));
		zhuZhiTapWoDeBeanList.add(new ZhuZhiTapItemBean(
				"http://p7.qhimg.com/t015339dd7cca3f0db5.jpg", "上海光棍俱乐部",
				"上海市", "黄浦区", "520", "1314", "加入", 1));
		zhuZhiTapWoDeBeanList.add(new ZhuZhiTapItemBean(
				"http://img1.3lian.com/img013/v3/24/d/1.jpg", "上海光棍俱乐部", "上海市",
				"黄浦区", "520", "1314", "加入", 3));
		zhuZhiTapWoDeBeanList.add(new ZhuZhiTapItemBean(
				"http://img3.3lian.com/2013/s1/15/d/46.jpg", "上海光棍俱乐部", "上海市",
				"黄浦区", "520", "1314", "加入", 3));
		zhuZhiTapWoDeBeanList
				.add(new ZhuZhiTapItemBean(
						"http://d.hiphotos.bdimg.com/album/pic/item/38dbb6fd5266d016f2124b16952bd40735fa3581.jpg",
						"上海光棍俱乐部", "上海市", "黄浦区", "520", "1314", "加入", 1));
		zhuZhiTapWoDeBeanList
				.add(new ZhuZhiTapItemBean(
						"http://www.xinhuaphoto.cn/bbs/attachment/Mon_1212/26_24882_6e4f1a0f9b8cd82.jpg",
						"上海光棍俱乐部", "上海市", "黄浦区", "520", "1314", "加入", 2));
		zhuZhiTapWoDeBeanList.add(new ZhuZhiTapItemBean(
				"http://www.33.la/uploads/zmbz/fengguang/sjjzrfggqz_45865.jpg",
				"上海光棍俱乐部", "上海市", "黄浦区", "520", "1314", "加入", 1));
	}

	public ZhuZhiTapItemBean() {
		// TODO Auto-generated constructor stub
	}

	public ZhuZhiTapItemBean(String imgUrl, String tv_clob, String tv_Province,
			String tv_town, String tv_chengyuan, String tv_huodong,
			String tv_join, Integer type) {
		this.imgUrl = imgUrl;
		this.tv_clob = tv_clob;
		this.tv_Province = tv_Province;
		this.tv_town = tv_town;
		this.tv_chengyuan = tv_chengyuan;
		this.tv_huodong = tv_huodong;
		this.tv_join = tv_join;
		this.type = type;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTv_clob() {
		return tv_clob;
	}

	public void setTv_clob(String tv_clob) {
		this.tv_clob = tv_clob;
	}

	public String getTv_Province() {
		return tv_Province;
	}

	public void setTv_Province(String tv_Province) {
		this.tv_Province = tv_Province;
	}

	public String getTv_town() {
		return tv_town;
	}

	public void setTv_town(String tv_town) {
		this.tv_town = tv_town;
	}

	public String getTv_chengyuan() {
		return tv_chengyuan;
	}

	public void setTv_chengyuan(String tv_chengyuan) {
		this.tv_chengyuan = tv_chengyuan;
	}

	public String getTv_huodong() {
		return tv_huodong;
	}

	public void setTv_huodong(String tv_huodong) {
		this.tv_huodong = tv_huodong;
	}

	public String getTv_join() {
		return tv_join;
	}

	public void setTv_join(String tv_join) {
		this.tv_join = tv_join;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
