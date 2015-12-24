package com.hwacreate.outdoor.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "outdoor_tb_xieyoujixiangqing")
public class XieYoujiXiangqing {
	@DatabaseField(generatedId = true)
	private Integer id;
	@DatabaseField(columnName = "verify")
	// 审核状态//审核状态0为待审核，1为通过，2为不通过,-1编辑中
	private Integer verify;
	@DatabaseField(columnName = "sign")
	private Integer sign = null; // "sign":0;标志位，0为保存（设置状态-4），1为提交（设置状态为0）
	@DatabaseField(columnName = "isEdit")
	private String isEdit;// 是否编辑 1为编辑 0为其他 点保存并确认时值为1，其他情况为0
	@DatabaseField(columnName = "footprint_data")
	private String footprint_data;// 脚印数据
	@DatabaseField(columnName = "act_end_time")
	private String act_end_time;// 结束时间
	@DatabaseField(columnName = "tvl_title")
	private String tvl_title;// 行程(游记标题)
	@DatabaseField(columnName = "picture_url")
	private String picture_url;// 截图路径
	@DatabaseField(columnName = "tvl_desc")
	private String tvl_desc;// 游记描述
	@DatabaseField(columnName = "anchor_longitude")
	private String anchor_longitude;// 锚点经度
	@DatabaseField(columnName = "act_id")
	private String act_id;// 活动id
	@DatabaseField(columnName = "anchor_latitude")
	private String anchor_latitude;// 锚点纬度
	@DatabaseField(columnName = "team_ember")
	private String team_ember;// 队员
	@DatabaseField(columnName = "u_id")
	private String u_id;// 用户id
	@DatabaseField(columnName = "act_start_time")
	private String act_start_time;// 开始时间
	@DatabaseField(columnName = "city")
	private String city;// 离线地图包城市
	@DatabaseField(columnName = "scenicsots")
	private String scenicsots;// 游记(活动)景区
	@DatabaseField(columnName = "is_collect")
	private Integer is_collect = 0;// 是否收藏(0为否，1为是)
	@DatabaseField(columnName = "distance")
	private Integer distance;// 全程距离
	@DatabaseField(columnName = "leader_id")
	private String leader_id;// 领队id
	@DatabaseField(columnName = "team_number")
	private Integer team_number;// 队伍人数
	@DatabaseField(columnName = "act_type")
	private String act_type;// 活动类型
	@DatabaseField(columnName = "leader_name")
	private String leader_name;// 领队名称
	@DatabaseField(columnName = "tvl_cover")
	private String tvl_cover;// 游记封面图片
	@DatabaseField(columnName = "tvl_type")
	private String tvl_type;// 游记类型(同活动类型)
	@DatabaseField(columnName = "tvl_id")
	private String tvl_id;// 游记id
	@DatabaseField(columnName = "trace_data")
	private String trace_data;// 轨迹数据内容
	@DatabaseField(columnName = "type")
	private Integer type;// 时光轴的类型
	@DatabaseField(columnName = "isDownLoad")
	private Integer isDownLoad;// 时光轴的类型
	@DatabaseField(columnName = "user_nickname")
	private String user_nickname;// 用户昵称
	@DatabaseField(columnName = "club_name")
	private String club_name;// 游记所属俱乐部名称
	@DatabaseField(columnName = "agreeCount")
	private Integer agreeCount;// 点赞数量
	@DatabaseField(columnName = "isAgree")
	private Integer isAgree;// 是否点赞
	@DatabaseField(columnName = "team_member")
	private String team_member; // 队员：存储格式： 队员1,队员2,...
	@DatabaseField(columnName = "act_strategy")
	private String act_strategy = null;// 攻略
	@DatabaseField(columnName = "logistics")
	private String logistics; // 后勤 报名须知
	@DatabaseField(columnName = "act_join_num_limit")
	private Integer act_join_num_limit; // 参加活动人数限制
	@DatabaseField(columnName = "confirmed_member")
	private Integer confirmed_member = 0; // 已确认队员人数
	@DatabaseField(columnName = "viceleadercount")
	private Integer viceleadercount = 0; // 副队人数
	@DatabaseField(columnName = "malecount")
	private Integer malecount = 0; // 男队人数
	@DatabaseField(columnName = "femalecount")
	private Integer femalecount = 0; // 女队人数
	@DatabaseField(columnName = "act_entry_fee")
	private String act_entry_fee; // 参加活动费用
	@DatabaseField(columnName = "act_weixin")
	private String act_weixin; // 活动微信号
	@DatabaseField(columnName = "act_most_equip")
	private String act_most_equip; // 参加活动准备基本装备
	@DatabaseField(columnName = "act_level")
	private Integer act_level;// 活动等级
	@DatabaseField(columnName = "tvl_else")
	private String tvl_else;// 游记其他的
	@DatabaseField(columnName = "club_logo")
	private String club_logo;// 俱乐部logo
	@DatabaseField(columnName = "head")
	private String head;// 创建游记作者头像
	@DatabaseField(columnName = "tvl_create_time")
	private String tvl_create_time;// 创建游记的时间
	@DatabaseField(columnName = "club_id")
	private String club_id;// 俱乐部id
	@DatabaseField(columnName = "act_2d_code")
	private String act_2d_code;// 二维码图片
	@DatabaseField(columnName = "act_desc_intro")
	private String act_desc_intro;// 游记简介
	@DatabaseField(columnName = "act_venue_time")
	private String act_venue_time;// 集合时间
	@DatabaseField(columnName = "act_venue")
	private String act_venue;// 集合地点
	@DatabaseField(columnName = "footprint_data_web")
	private String footprint_data_web;// 游记足印web
	@DatabaseField(columnName = "is_share")
	private Integer is_share;// 是否分享
	@DatabaseField(columnName = "rel_speed")
	private Double rel_speed;// 平均速度
	@DatabaseField(columnName = "footprintArrayUptoNet")
	private String footprintArrayUptoNet;// 需要上传的脚印
	@DatabaseField(columnName = "fengmianStrUptoNet")
	private String fengmianStrUptoNet;// 需要上传的封面

	public String getFootprintArrayUptoNet() {
		return footprintArrayUptoNet;
	}

	public void setFootprintArrayUptoNet(String footprintArrayUptoNet) {
		this.footprintArrayUptoNet = footprintArrayUptoNet;
	}

	public String getFengmianStrUptoNet() {
		return fengmianStrUptoNet;
	}

	public void setFengmianStrUptoNet(String fengmianStrUptoNet) {
		this.fengmianStrUptoNet = fengmianStrUptoNet;
	}

	public String getAct_2d_code() {
		return act_2d_code;
	}

	public String getAct_desc_intro() {
		return act_desc_intro;
	}

	public void setAct_desc_intro(String act_desc_intro) {
		this.act_desc_intro = act_desc_intro;
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

	public String getFootprint_data_web() {
		return footprint_data_web;
	}

	public void setFootprint_data_web(String footprint_data_web) {
		this.footprint_data_web = footprint_data_web;
	}

	public Integer getIs_share() {
		return is_share;
	}

	public void setIs_share(Integer is_share) {
		this.is_share = is_share;
	}

	public Double getRel_speed() {
		return rel_speed;
	}

	public void setRel_speed(Double rel_speed) {
		this.rel_speed = rel_speed;
	}

	public void setAct_2d_code(String act_2d_code) {
		this.act_2d_code = act_2d_code;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public String getClub_id() {
		return club_id;
	}

	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	public XieYoujiXiangqing() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTvl_else() {
		return tvl_else;
	}

	public String getClub_logo() {
		return club_logo;
	}

	public void setClub_logo(String club_logo) {
		this.club_logo = club_logo;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getTvl_create_time() {
		return tvl_create_time;
	}

	public void setTvl_create_time(String tvl_create_time) {
		this.tvl_create_time = tvl_create_time;
	}

	public void setTvl_else(String tvl_else) {
		this.tvl_else = tvl_else;
	}

	public Integer getAct_level() {
		return act_level;
	}

	public void setAct_level(Integer act_level) {
		this.act_level = act_level;
	}

	public String getTeam_member() {
		return team_member;
	}

	public void setTeam_member(String team_member) {
		this.team_member = team_member;
	}

	public String getAct_strategy() {
		return act_strategy;
	}

	public void setAct_strategy(String act_strategy) {
		this.act_strategy = act_strategy;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}

	public Integer getAct_join_num_limit() {
		return act_join_num_limit;
	}

	public void setAct_join_num_limit(Integer act_join_num_limit) {
		this.act_join_num_limit = act_join_num_limit;
	}

	public Integer getConfirmed_member() {
		return confirmed_member;
	}

	public void setConfirmed_member(Integer confirmed_member) {
		this.confirmed_member = confirmed_member;
	}

	public Integer getViceleadercount() {
		return viceleadercount;
	}

	public void setViceleadercount(Integer viceleadercount) {
		this.viceleadercount = viceleadercount;
	}

	public Integer getMalecount() {
		return malecount;
	}

	public void setMalecount(Integer malecount) {
		this.malecount = malecount;
	}

	public Integer getFemalecount() {
		return femalecount;
	}

	public void setFemalecount(Integer femalecount) {
		this.femalecount = femalecount;
	}

	public String getAct_entry_fee() {
		return act_entry_fee;
	}

	public void setAct_entry_fee(String act_entry_fee) {
		this.act_entry_fee = act_entry_fee;
	}

	public String getAct_weixin() {
		return act_weixin;
	}

	public void setAct_weixin(String act_weixin) {
		this.act_weixin = act_weixin;
	}

	public String getAct_most_equip() {
		return act_most_equip;
	}

	public void setAct_most_equip(String act_most_equip) {
		this.act_most_equip = act_most_equip;
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsDownLoad() {
		return isDownLoad;
	}

	public void setIsDownLoad(Integer isDownLoad) {
		this.isDownLoad = isDownLoad;
	}

	public String getAct_end_time() {
		return act_end_time;
	}

	public void setAct_end_time(String act_end_time) {
		this.act_end_time = act_end_time;
	}

	public String getTvl_title() {
		return tvl_title;
	}

	public void setTvl_title(String tvl_title) {
		this.tvl_title = tvl_title;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}

	public String getTvl_desc() {
		return tvl_desc;
	}

	public void setTvl_desc(String tvl_desc) {
		this.tvl_desc = tvl_desc;
	}

	public String getAnchor_longitude() {
		return anchor_longitude;
	}

	public void setAnchor_longitude(String anchor_longitude) {
		this.anchor_longitude = anchor_longitude;
	}

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getAnchor_latitude() {
		return anchor_latitude;
	}

	public void setAnchor_latitude(String anchor_latitude) {
		this.anchor_latitude = anchor_latitude;
	}

	public String getTeam_ember() {
		return team_ember;
	}

	public void setTeam_ember(String team_ember) {
		this.team_ember = team_ember;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getAct_start_time() {
		return act_start_time;
	}

	public void setAct_start_time(String act_start_time) {
		this.act_start_time = act_start_time;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getScenicsots() {
		return scenicsots;
	}

	public void setScenicsots(String scenicsots) {
		this.scenicsots = scenicsots;
	}

	public Integer getIs_collect() {
		return is_collect;
	}

	public void setIs_collect(Integer is_collect) {
		this.is_collect = is_collect;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getLeader_id() {
		return leader_id;
	}

	public void setLeader_id(String leader_id) {
		this.leader_id = leader_id;
	}

	public Integer getTeam_number() {
		return team_number;
	}

	public void setTeam_number(Integer team_number) {
		this.team_number = team_number;
	}

	public String getAct_type() {
		return act_type;
	}

	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}

	public String getLeader_name() {
		return leader_name;
	}

	public void setLeader_name(String leader_name) {
		this.leader_name = leader_name;
	}

	public String getTvl_cover() {
		return tvl_cover;
	}

	public void setTvl_cover(String tvl_cover) {
		this.tvl_cover = tvl_cover;
	}

	public String getTvl_type() {
		return tvl_type;
	}

	public void setTvl_type(String tvl_type) {
		this.tvl_type = tvl_type;
	}

	public String getTvl_id() {
		return tvl_id;
	}

	public void setTvl_id(String tvl_id) {
		this.tvl_id = tvl_id;
	}

	public String getTrace_data() {
		return trace_data;
	}

	public void setTrace_data(String trace_data) {
		this.trace_data = trace_data;
	}

	public String getFootprint_data() {
		return footprint_data;
	}

	public void setFootprint_data(String footprint_data) {
		this.footprint_data = footprint_data;
	}

	public XieYoujiXiangqing(String footprint_data, String act_end_time,
			String tvl_title, String picture_url, String tvl_desc,
			String anchor_longitude, String act_id, String anchor_latitude,
			String team_ember, String u_id, String act_start_time, String city,
			String scenicsots, Integer is_collect, Integer distance,
			String leader_id, Integer team_number, String act_type,
			String leader_name, String tvl_cover, String tvl_type,
			String tvl_id, String trace_data, Integer type, Integer isDownLoad,
			String user_nickname, String club_name, Integer agreeCount,
			Integer isAgree, String team_member, String act_strategy,
			String logistics, Integer act_join_num_limit,
			Integer confirmed_member, Integer viceleadercount,
			Integer malecount, Integer femalecount, String act_entry_fee,
			String act_weixin, String act_most_equip, Integer act_level,
			String tvl_else, String club_logo, String head,
			String tvl_create_time) {
		super();
		this.footprint_data = footprint_data;
		this.act_end_time = act_end_time;
		this.tvl_title = tvl_title;
		this.picture_url = picture_url;
		this.tvl_desc = tvl_desc;
		this.anchor_longitude = anchor_longitude;
		this.act_id = act_id;
		this.anchor_latitude = anchor_latitude;
		this.team_ember = team_ember;
		this.u_id = u_id;
		this.act_start_time = act_start_time;
		this.city = city;
		this.scenicsots = scenicsots;
		this.is_collect = is_collect;
		this.distance = distance;
		this.leader_id = leader_id;
		this.team_number = team_number;
		this.act_type = act_type;
		this.leader_name = leader_name;
		this.tvl_cover = tvl_cover;
		this.tvl_type = tvl_type;
		this.tvl_id = tvl_id;
		this.trace_data = trace_data;
		this.type = type;
		this.isDownLoad = isDownLoad;
		this.user_nickname = user_nickname;
		this.club_name = club_name;
		this.agreeCount = agreeCount;
		this.isAgree = isAgree;
		this.team_member = team_member;
		this.act_strategy = act_strategy;
		this.logistics = logistics;
		this.act_join_num_limit = act_join_num_limit;
		this.confirmed_member = confirmed_member;
		this.viceleadercount = viceleadercount;
		this.malecount = malecount;
		this.femalecount = femalecount;
		this.act_entry_fee = act_entry_fee;
		this.act_weixin = act_weixin;
		this.act_most_equip = act_most_equip;
		this.act_level = act_level;
		this.tvl_else = tvl_else;
		this.club_logo = club_logo;
		this.head = head;
		this.tvl_create_time = tvl_create_time;
	}

}
