package com.hwacreate.outdoor.ormlite.bean;

import java.util.List;

import com.hwacreate.outdoor.bean.HuoDongDuiYuan;
import com.hwacreate.outdoor.bean.HuoDongXiangQingDongTai;
import com.hwacreate.outdoor.bean.HuoDongXiangQingGongNue;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "outdoor_tb_huodongxiangqing")
public class HuoDongXiangQingItem {

	@DatabaseField(generatedId = true)
	private Integer id = null;
	@DatabaseField(columnName = "act_id")
	private String act_id = null;// 活动id
	@DatabaseField(columnName = "type")
	private Integer type = null;// 活动id
	@DatabaseField(columnName = "act_sponsor_id")
	private String act_sponsor_id = null;// 创建人id
	@DatabaseField(columnName = "act_title")
	private String act_title = null;// 活动标题
	@DatabaseField(columnName = "act_desc")
	private String act_desc = null;// 活动描述
	@DatabaseField(columnName = "act_desc_web")
	private String act_desc_web = null;// 活动描述
	@DatabaseField(columnName = "act_logo")
	private String act_logo = null;// 活动logo图
	@DatabaseField(columnName = "act_level")
	private Integer act_level = null;// 活动级别
	@DatabaseField(columnName = "act_state")
	private Integer act_state = null;// 活动状态：活动状态：0为审核中，1为报名中，2为整队中，3为出行中，4为待点评，5为结束，-1为取消
	@DatabaseField(columnName = "verify")
	private Integer verify = null;// 审核状态：-1审核不通过，0待审核，1审核通过
	@DatabaseField(columnName = "act_type")
	private String act_type = null;// 活动类型：如登山
	@DatabaseField(columnName = "club_name")
	private String club_name = null;// 活动所属俱乐部
	@DatabaseField(columnName = "leader_name")
	private String leader_name = null;// 活动所属领队
	@DatabaseField(columnName = "create_time")
	private String create_time = null;// 活动创建时间
	@DatabaseField(columnName = "act_start_time")
	private String act_start_time = null;// 活动开始时间
	@DatabaseField(columnName = "act_end_time")
	private String act_end_time = null;// 活动结束时间
	@DatabaseField(columnName = "act_join_num_limit")
	private Integer act_join_num_limit = null;// 参加人数限制
	@DatabaseField(columnName = "act_fee_include")
	private String act_fee_include = null;// 活动费用包含
	@DatabaseField(columnName = "act_weixin")
	private String act_weixin = null;// 活动微信号
	@DatabaseField(columnName = "act_base_equip")
	private String act_base_equip = null;// 参加活动准备基本装备
	@DatabaseField(columnName = "logistics")
	private String logistics = null;// 后勤
	@DatabaseField(columnName = "act_linkman")
	private String act_linkman = null;// 活动联系人
	@DatabaseField(columnName = "act_linkphone")
	private String act_linkphone = null;// 活动联系电话
	@DatabaseField(columnName = "act_scenicspots")
	private String act_scenicspots = null;// 景点
	@DatabaseField(columnName = "epilogue")
	private String epilogue = null;// 结语点评
	@DatabaseField(columnName = "trace_file_id")
	private Integer trace_file_id = null;// 轨迹文件id
	@DatabaseField(columnName = "confirm_member")
	private Integer confirm_member = null;// 确认队员人数
	@DatabaseField(columnName = "man")
	private Integer man = null;// 男性人数
	@DatabaseField(columnName = "female")
	private Integer female = null;// ,女性人数
	@DatabaseField(columnName = "issignup")
	private Integer issignup = null;// ,是否报名，0为未报名，1为已报名，备注：情况一，如果用户未登录情况下，为0，如果用户登录状态下则查询队伍表看是否报名
	@DatabaseField(columnName = "act_budget") // 预算费用
	private double act_budget = 0;
	@DatabaseField(columnName = "isReport")
	private Integer isReport = 0;
	@DatabaseField(columnName = "act_venue_time")
	private String act_venue_time = "";
	@DatabaseField(columnName = "act_venue")
	private String act_venue = "";
	@DatabaseField(columnName = "isCollect")
	private Integer isCollect = null;
	@DatabaseField(columnName = "act_else")
	private String act_else = null;
	@DatabaseField(columnName = "picture_url")
	private String picture_url = null;// 截图路径
	@DatabaseField(columnName = "describe")
	private String describe = null;// 对轨迹描述
	@DatabaseField(columnName = "club_id")
	private String club_id = null;// 所属俱乐部
	@DatabaseField(columnName = "club_logo")
	private String club_logo = null;// 所属俱乐部log
	@DatabaseField(columnName = "leader_id")
	private String leader_id = null;// 所属领队id
	@DatabaseField(columnName = "trace_id")
	private String trace_id = null;// 轨迹id
	@DatabaseField(columnName = "anchor_longitude")
	private String anchor_longitude = null;// 锚点经度
	@DatabaseField(columnName = "name")
	private String name = null;// 轨迹名称
	@DatabaseField(columnName = "trace_data")
	private String trace_data = null;
	@DatabaseField(columnName = "anchor_latitude")
	private String anchor_latitude = null;// 锚点纬度
	@DatabaseField(columnName = "city")
	private String city = null;// 离线地图包城市
	@DatabaseField(columnName = "act_2d_code")
	private String act_2d_code = "";// 二维码
	@DatabaseField(columnName = "act_schedule_app")
	private String act_schedule_app = null;// 攻略
	@DatabaseField(columnName = "history")
	private String history = "";// 历史动态
	@DatabaseField(columnName = "team_member")
	private String team_member = "";// 队员列表
	@DatabaseField(columnName = "act_desc_intro")
	private String act_desc_intro = null;// 活动简介
	@DatabaseField(columnName = "leader_head")
	private String leader_head = null;// 领队头像

	public String getLeader_head() {
		return leader_head;
	}

	public void setLeader_head(String leader_head) {
		this.leader_head = leader_head;
	}

	private List<HuoDongDuiYuan> team_memberList = null;
	private List<HuoDongXiangQingDongTai> historyList = null;
	private List<HuoDongXiangQingGongNue> act_schedule_apps = null;

	public List<HuoDongXiangQingGongNue> getAct_schedule_apps() {
		return act_schedule_apps;
	}

	public void setAct_schedule_apps(List<HuoDongXiangQingGongNue> act_schedule_apps) {
		this.act_schedule_apps = act_schedule_apps;
	}

	public List<HuoDongXiangQingDongTai> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<HuoDongXiangQingDongTai> historyList) {
		this.historyList = historyList;
	}

	public List<HuoDongDuiYuan> getTeam_memberList() {
		return team_memberList;
	}

	public void setTeam_memberList(List<HuoDongDuiYuan> team_memberList) {
		this.team_memberList = team_memberList;
	}

	public String getAct_schedule_app() {
		return act_schedule_app;
	}

	public void setAct_schedule_app(String act_schedule_app) {
		this.act_schedule_app = act_schedule_app;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getTeam_member() {
		return team_member;
	}

	public void setTeam_member(String team_member) {
		this.team_member = team_member;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAct_sponsor_id() {
		return act_sponsor_id;
	}

	public void setAct_sponsor_id(String act_sponsor_id) {
		this.act_sponsor_id = act_sponsor_id;
	}

	public String getAct_title() {
		return act_title;
	}

	public void setAct_title(String act_title) {
		this.act_title = act_title;
	}

	public String getAct_desc() {
		return act_desc;
	}

	public void setAct_desc(String act_desc) {
		this.act_desc = act_desc;
	}

	public String getAct_desc_web() {
		return act_desc_web;
	}

	public void setAct_desc_web(String act_desc_web) {
		this.act_desc_web = act_desc_web;
	}

	public String getAct_logo() {
		return act_logo;
	}

	public void setAct_logo(String act_logo) {
		this.act_logo = act_logo;
	}

	public Integer getAct_level() {
		return act_level;
	}

	public void setAct_level(Integer act_level) {
		this.act_level = act_level;
	}

	public Integer getAct_state() {
		return act_state;
	}

	public void setAct_state(Integer act_state) {
		this.act_state = act_state;
	}

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	public String getAct_type() {
		return act_type;
	}

	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public String getLeader_name() {
		return leader_name;
	}

	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getAct_start_time() {
		return act_start_time;
	}

	public void setAct_start_time(String act_start_time) {
		this.act_start_time = act_start_time;
	}

	public String getAct_end_time() {
		return act_end_time;
	}

	public void setAct_end_time(String act_end_time) {
		this.act_end_time = act_end_time;
	}

	public Integer getAct_join_num_limit() {
		return act_join_num_limit;
	}

	public void setAct_join_num_limit(Integer act_join_num_limit) {
		this.act_join_num_limit = act_join_num_limit;
	}

	public String getAct_fee_include() {
		return act_fee_include;
	}

	public void setAct_fee_include(String act_fee_include) {
		this.act_fee_include = act_fee_include;
	}

	public String getAct_weixin() {
		return act_weixin;
	}

	public void setAct_weixin(String act_weixin) {
		this.act_weixin = act_weixin;
	}

	public String getAct_base_equip() {
		return act_base_equip;
	}

	public void setAct_base_equip(String act_base_equip) {
		this.act_base_equip = act_base_equip;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public String getAct_linkman() {
		return act_linkman;
	}

	public void setAct_linkman(String act_linkman) {
		this.act_linkman = act_linkman;
	}

	public String getAct_linkphone() {
		return act_linkphone;
	}

	public void setAct_linkphone(String act_linkphone) {
		this.act_linkphone = act_linkphone;
	}

	public String getAct_scenicspots() {
		return act_scenicspots;
	}

	public void setAct_scenicspots(String act_scenicspots) {
		this.act_scenicspots = act_scenicspots;
	}

	public String getEpilogue() {
		return epilogue;
	}

	public void setEpilogue(String epilogue) {
		this.epilogue = epilogue;
	}

	public Integer getTrace_file_id() {
		return trace_file_id;
	}

	public void setTrace_file_id(Integer trace_file_id) {
		this.trace_file_id = trace_file_id;
	}

	public Integer getConfirm_member() {
		return confirm_member;
	}

	public void setConfirm_member(Integer confirm_member) {
		this.confirm_member = confirm_member;
	}

	public Integer getMan() {
		return man;
	}

	public void setMan(Integer man) {
		this.man = man;
	}

	public Integer getFemale() {
		return female;
	}

	public void setFemale(Integer female) {
		this.female = female;
	}

	public Integer getIssignup() {
		return issignup;
	}

	public void setIssignup(Integer issignup) {
		this.issignup = issignup;
	}

	public double getAct_budget() {
		return act_budget;
	}

	public void setAct_budget(double act_budget) {
		this.act_budget = act_budget;
	}

	public Integer getIsReport() {
		return isReport;
	}

	public void setIsReport(Integer isReport) {
		this.isReport = isReport;
	}

	public String getAct_venue_time() {
		return act_venue_time;
	}

	public void setAct_venue_time(String act_venue_time) {
		this.act_venue_time = act_venue_time;
	}

	public String getAct_venue() {
		return act_venue;
	}

	public void setAct_venue(String act_venue) {
		this.act_venue = act_venue;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public String getAct_else() {
		return act_else;
	}

	public void setAct_else(String act_else) {
		this.act_else = act_else;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getClub_id() {
		return club_id;
	}

	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}

	public String getClub_logo() {
		return club_logo;
	}

	public void setClub_logo(String club_logo) {
		this.club_logo = club_logo;
	}

	public String getLeader_id() {
		return leader_id;
	}

	public void setLeader_id(String leader_id) {
		this.leader_id = leader_id;
	}

	public String getTrace_id() {
		return trace_id;
	}

	public void setTrace_id(String trace_id) {
		this.trace_id = trace_id;
	}

	public String getAnchor_longitude() {
		return anchor_longitude;
	}

	public void setAnchor_longitude(String anchor_longitude) {
		this.anchor_longitude = anchor_longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrace_data() {
		return trace_data;
	}

	public void setTrace_data(String trace_data) {
		this.trace_data = trace_data;
	}

	public String getAnchor_latitude() {
		return anchor_latitude;
	}

	public void setAnchor_latitude(String anchor_latitude) {
		this.anchor_latitude = anchor_latitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAct_2d_code() {
		return act_2d_code;
	}

	public void setAct_2d_code(String act_2d_code) {
		this.act_2d_code = act_2d_code;
	}

	public String getAct_desc_intro() {
		return act_desc_intro;
	}

	public void setAct_desc_intro(String act_desc_intro) {
		this.act_desc_intro = act_desc_intro;
	}

}
