package com.hwacreate.outdoor.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "outdoor_tb_signupuser")
public class SignUpUser {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "link_qq")
	private String link_qq = null;// 联系qq
	@DatabaseField(columnName = "apply_time")
	private String apply_time = null;// 报名时间
	@DatabaseField(columnName = "sex")
	private String sex = null;// 性别
	@DatabaseField(columnName = "link_man")
	private String link_man = null;// 紧急联系人
	@DatabaseField(columnName = "link_phone")
	private String link_phone = null;// 紧急联系电话
	@DatabaseField(columnName = "link_email")
	private String link_email = null;// 联系邮箱
	@DatabaseField(columnName = "bloodtype")
	private String bloodtype = null;// 血型
	@DatabaseField(columnName = "act_id")
	private String act_id = null;// 活动id
	@DatabaseField(columnName = "u_id")
	private String u_id = null;// 队员id
	@DatabaseField(columnName = "tps_type")
	private Integer tps_type = null;// 人员类型：0普通队员，1领队
	@DatabaseField(columnName = "tps_state")
	private Integer tps_state = null;// 报名状态：0报名中，1为确认
	@DatabaseField(columnName = "is_report")
	private Integer is_report = null;;// 报道状态：0未报道，1为确认
	@DatabaseField(columnName = "tps_id")
	private Integer tps_id = null;// 物理id
	@DatabaseField(columnName = "phonenum")
	private String phonenum = null;// 队员手机号
	@DatabaseField(columnName = "strDeviceSN")
	private String strDeviceSN = null;// 硬件sn号码
	@DatabaseField(columnName = "uDeviceIsLDB")
	private boolean uDeviceIsLDB = false;// //是否使用领队宝
	@DatabaseField(columnName = "uDeviceReady")
	private boolean uDeviceReady = false;// 是否有同行宝设备
	@DatabaseField(columnName = "uIsAttend")
	private boolean uIsAttend = false;// 该编号成员是否参加了这次活动。0-未参加，1-参加了本次活动
	@DatabaseField(columnName = "uIsUseAPP ")
	private boolean uIsUseAPP = false;// 该编号成员是否启用APP图标. 0-未使用队员APP，1-使用了队员APP
	@DatabaseField(columnName = "u_nickname ")
	private String u_nickname = ""; // 用户昵称
	@DatabaseField(columnName = "u_head")
	private String u_head = ""; // 用户头像
	@DatabaseField(columnName = "u_star")
	private Integer u_star = 0; // 用户星级
	@DatabaseField(columnName = "u_fraction")
	private Integer u_fraction = 0; // 用户分数
	@DatabaseField(columnName = "act_join_num_limit")
	private Integer act_join_num_limit = 0;//参加活动人数限制
	
	public Integer getIs_report() {
		return is_report;
	}

	public void setIs_report(Integer is_report) {
		this.is_report = is_report;
	}

	public Integer getAct_join_num_limit() {
		return act_join_num_limit;
	}

	public void setAct_join_num_limit(Integer act_join_num_limit) {
		this.act_join_num_limit = act_join_num_limit;
	}

	public String getU_nickname() {
		return u_nickname;
	}

	public void setU_nickname(String u_nickname) {
		this.u_nickname = u_nickname;
	}

	public String getU_head() {
		return u_head;
	}

	public void setU_head(String u_head) {
		this.u_head = u_head;
	}

	public Integer getU_star() {
		return u_star;
	}

	public void setU_star(Integer u_star) {
		this.u_star = u_star;
	}

	public Integer getU_fraction() {
		return u_fraction;
	}

	public void setU_fraction(Integer u_fraction) {
		this.u_fraction = u_fraction;
	}

	public String getStrDeviceSN() {
		return strDeviceSN;
	}

	public void setStrDeviceSN(String strDeviceSN) {
		this.strDeviceSN = strDeviceSN;
	}

	public boolean getuDeviceIsLDB() {
		return uDeviceIsLDB;
	}

	public void setuDeviceIsLDB(boolean uDeviceIsLDB) {
		this.uDeviceIsLDB = uDeviceIsLDB;
	}

	public boolean getuDeviceReady() {
		return uDeviceReady;
	}

	public void setuDeviceReady(boolean uDeviceReady) {
		this.uDeviceReady = uDeviceReady;
	}

	public boolean getuIsAttend() {
		return uIsAttend;
	}

	public void setuIsAttend(boolean uIsAttend) {
		this.uIsAttend = uIsAttend;
	}

	public boolean getuIsUseAPP() {
		return uIsUseAPP;
	}

	public void setuIsUseAPP(boolean uIsUseAPP) {
		this.uIsUseAPP = uIsUseAPP;
	}

	public String getLink_qq() {
		return link_qq;
	}

	public void setLink_qq(String link_qq) {
		this.link_qq = link_qq;
	}

	public String getApply_time() {
		return apply_time;
	}

	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLink_man() {
		return link_man;
	}

	public void setLink_man(String link_man) {
		this.link_man = link_man;
	}

	public String getLink_phone() {
		return link_phone;
	}

	public void setLink_phone(String link_phone) {
		this.link_phone = link_phone;
	}

	public String getLink_email() {
		return link_email;
	}

	public void setLink_email(String link_email) {
		this.link_email = link_email;
	}

	public String getBloodtype() {
		return bloodtype;
	}

	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public Integer getTps_type() {
		return tps_type;
	}

	public void setTps_type(Integer tps_type) {
		this.tps_type = tps_type;
	}

	public Integer getTps_state() {
		return tps_state;
	}

	public void setTps_state(Integer tps_state) {
		this.tps_state = tps_state;
	}

	public Integer getTps_id() {
		return tps_id;
	}

	public void setTps_id(Integer tps_id) {
		this.tps_id = tps_id;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

}
