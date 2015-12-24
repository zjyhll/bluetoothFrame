package com.hwacreate.outdoor.mainFragment.wode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.areacity.AreaInfoActivity;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.HuoDongDuiYuan;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.XinjianyoujijlbOrhdActivity;
import com.hwacreate.outdoor.uploadwithprogress.http.HttpMultipartPost;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.utl.TimeUtil;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.view.CustomDialog;
import com.hwacreate.outdoor.view.MyGridView;
import com.hwacreate.outdoor.view.MyListView;
import com.hwacreate.outdoor.view.PickerView;
import com.hwacreate.outdoor.view.PickerView.onSelectListener;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelRequest;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelRequestParameter;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelResponse;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetActivityInfoByID.GetActivityInfoByIDRequest;
import com.keyhua.outdoor.protocol.GetActivityInfoByID.GetActivityInfoByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetActivityInfoByID.GetActivityInfoByIDResponse;
import com.keyhua.outdoor.protocol.GetActivityInfoByID.GetActivityInfoByIDResponsePayload;
import com.keyhua.outdoor.protocol.GetActivityTeamMemberAction.GetActivityTeamMemberRequest;
import com.keyhua.outdoor.protocol.GetActivityTeamMemberAction.GetActivityTeamMemberRequestParameter;
import com.keyhua.outdoor.protocol.GetActivityTeamMemberAction.GetActivityTeamMemberResponse;
import com.keyhua.outdoor.protocol.GetActivityTeamMemberAction.GetActivityTeamMemberResponsePayload;
import com.keyhua.outdoor.protocol.GetActivityTeamMemberAction.GetActivityTeamMemberResponsePayloadItem;
import com.keyhua.outdoor.protocol.GetTraceInfoByID.GetTraceInfoByIDRequest;
import com.keyhua.outdoor.protocol.GetTraceInfoByID.GetTraceInfoByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetTraceInfoByID.GetTraceInfoByIDResponse;
import com.keyhua.outdoor.protocol.GetTraceInfoByID.GetTraceInfoByIDResponsePayload;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDRequest;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDResponse;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity_YoujiFengmian;

public class XinJianYouJizjyActivity extends BaseActivity {
	private CircleImageView civ_yj = null; // 头像
	private RelativeLayout xinjian_view = null; // 封面高度
	private ImageView iv_fm = null; // 封面
	private TextView tv_time = null; // 时间不能选择
	private CleareditTextView cet_yjbt = null; // 游记标题
	private TextView tv_scfm = null; // 上传封面
	private CleareditTextView cet_rjf = null; // 人均费
	private TextView iv_dizhi = null; // 弹出省市区选择
	private TextView iv_jlb = null; // 弹出俱乐部列表
	private TextView tv_hdlxing = null; // 弹出游记类型列表
	private TextView tv_starttime = null; // 开始时间
	private TextView tv_endtime = null; // 结束时间
	private CleareditTextView ctv_hddj = null; // 游记等级
	private CleareditTextView ctv_hdlx = null; // 游记路线
	private CleareditTextView ctv_yjlc = null; // 游记里程
	private TextView tv_gj = null; // 添加轨迹行程
	private TextView tv_xc = null; // 添加行程
	// 上传
	private HttpMultipartPost post;
	private String result = null;

	// pop选择栏选择
	private PopupWindow popUser = null;
	private TextView pickerviewtext = null;
	private TextView geren_qu = null;
	private TextView geren_wan = null;
	private LinearLayout geren_pop_view = null;
	private RelativeLayout geren_pop_image = null;
	private View parentView = null;
	// 开始
	private PickerView pickerviewleft = null;
	private PickerView pickerviewright = null;
	private PickerView pickerviewright_right = null;
	// 存放的数据
	private List<String> datanian = null;
	private List<String> datayue = null;
	private List<String> datari = null;
	// 显示的数据
	private String nian = null;
	private String yue = null;
	private String ri = null;
	private boolean isRuiNian = false;
	public static JSONArray footprintArrayUptoNet = null;// 上传时使用的脚印数据
	public static JSONArray trackArray = null;
	private MyFootprintAdpter footprintAdpter = null;
	private MyTrackAdpter myTrackAdpter = null;
	private MyListView lv = null;
	private MyListView lv_guiji = null;
	// 新加入的控件
	private TextView tv_fb = null;// 发布按钮
	private CleareditTextView ctv_hdjj = null;// 游记简介
	private LinearLayout ll_hddr = null;// 如果是关联游记直接导入，显示该控件
	// private CleareditTextView ctv_xcfy = null;// 行程费用
	private CleareditTextView ctv_yjms = null;// 游记描述
	private CleareditTextView ctv_cxzb = null;// 出行装备
	private CleareditTextView ctv_bmxz = null;// 报名须知
	private CleareditTextView ctv_qt = null;// 其他
	private TextView ctv_jhdw = null;// 计划队伍
	private TextView ctv_yqrdy = null;// 已确认队员
	private TextView ctv_ld = null;// 领队
	private TextView ctv_fd = null;// 副队
	private GridView gv_dy = null;// 队员
	private TextView tv_wxh = null;// 微信号
	private TextView tv_nan = null;// 男
	private TextView tv_nv = null;// 女
	private String act_id = "";
	// 数据库中自增id
	private boolean isPick = false;
	private boolean isFrist = true;
	// 加入标志位判断是添加还是更新
	private Integer sign = null; // "sign":0;标志位，0为保存（设置状态-4），1为提交（设置状态为0）
	private String tvl_id = null;
	// json
	private Gson gson = null;

	/**
	 * 游记id不为空时初始化这里面的控件，如果是直接关联游记的话就直接将所有数据存入数据库，这边在拿数据来用就行了
	 */
	private void initkongjian() {
		// ctv_xcfy = (CleareditTextView) findViewById(R.id.ctv_xcfy);
		ctv_cxzb = (CleareditTextView) findViewById(R.id.ctv_cxzb);
		ctv_yjms = (CleareditTextView) findViewById(R.id.ctv_yjms);
		ctv_bmxz = (CleareditTextView) findViewById(R.id.ctv_bmxz);
		ctv_yjlc = (CleareditTextView) findViewById(R.id.ctv_yjlc);
		ctv_qt = (CleareditTextView) findViewById(R.id.ctv_qt);
		ctv_jhdw = (TextView) findViewById(R.id.ctv_jhdw);
		ctv_yqrdy = (TextView) findViewById(R.id.ctv_yqrdy);
		ctv_ld = (TextView) findViewById(R.id.ctv_ld);
		ctv_fd = (TextView) findViewById(R.id.ctv_fd);
		gv_dy = (GridView) findViewById(R.id.gv_dy);
		tv_wxh = (TextView) findViewById(R.id.tv_wxh);
		tv_nan = (TextView) findViewById(R.id.tv_nan);
		tv_nv = (TextView) findViewById(R.id.tv_nv);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_xin_jian_you_jizjy, null);
		setContentView(parentView);
		init();
	}

	private void getDataFromServer(GetTravelInfoByIDResponsePayload payload) {
		act_title = payload.getTvl_title();
		act_type = payload.getTvl_type();
		club_name = payload.getClub_name();// 俱乐部名字
		rel_speed = payload.getRel_speed();
		act_venue = payload.getAct_venue();
		act_venue_time = payload.getAct_venue_time();
		club_id = payload.getClub_id();
		trace_data = payload.getTrace_data() != null ? payload.getTrace_data() : "";
		footprint_data = payload.getFootprint_data() != null ? payload.getFootprint_data() : "";
		act_start_time = payload.getAct_start_time();
		act_level = payload.getAct_level() != null ? payload.getAct_level() : 0;
		act_end_time = payload.getAct_end_time();
		anchor_longitude = payload.getAnchor_longitude();
		anchor_latitude = payload.getAnchor_latitude();
		city = payload.getCity();
		leader_id = payload.getLeader_id();
		leader_name = payload.getLeader_name();
		tvl_cover = payload.getTvl_cover() != null ? payload.getTvl_cover() : "";
		logistics = payload.getLogistics(); // 后勤
		act_join_num_limit = payload.getAct_join_num_limit() != null ? payload.getAct_join_num_limit() : 0;// 参加游记人数限制
		act_entry_free = payload.getAct_entry_fee(); // 参加游记费用
		act_weixin = payload.getAct_weixin(); // 游记微信号
		act_line = payload.getAct_line();
		tvl_create_time = payload.getTvl_create_time();
		tvl_update_time = payload.getTvl_update_time();
		act_desc = payload.getTvl_desc();
		act_desc_intro = payload.getAct_desc_intro();
		act_base_equip = payload.getAct_most_equip();
		confirm_member = payload.getConfirmed_member();
		man = payload.getMalecount();
		female = payload.getFemalecount();
		act_else = payload.getTvl_else();
		distance = payload.getDistance() != null ? payload.getDistance() : 0;
		team_memberList.clear();
		team_memberList = gson.fromJson(payload.getTeam_member(),
				new TypeToken<List<GetActivityTeamMemberResponsePayloadItem>>() {
				}.getType()); // 队员：存储格式： 队员1,队员2,...
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFrist && NetUtil.isNetworkAvailable(XinJianYouJizjyActivity.this)
				&& !TextUtils.isEmpty(App.getInstance().getAut())) {
			if (myTrackAdpter != null && myTrackAdpter.getCount() != 0) {
				myTrackAdpter.notifyDataSetChanged();
			}
			if (footprintAdpter != null && footprintAdpter.getCount() != 0) {
				footprintAdpter.notifyDataSetChanged();
			}
			if (!TextUtils.isEmpty(App.getInstance().getSelectPathYouJiFenmian())) {
				mImageLoader.displayImage("file://" + App.getInstance().getSelectPathYouJiFenmian(), iv_fm, options);
			}
			if (!TextUtils.isEmpty(App.getInstance().getHuodongleixing())) {
				if (!TextUtils.equals(App.getInstance().getHuodongleixing(), act_type)) {
					tv_hdlxing.setText(App.getInstance().getHuodongleixing());
				}
			}
			if (!TextUtils.isEmpty(App.getInstance().getClub_name())) {
				if (!TextUtils.equals(App.getInstance().getClub_name(), club_name)) {
					iv_jlb.setText(App.getInstance().getClub_name());
					club_id = App.getInstance().getClub_id();
				}
			}
		}
		if (!TextUtils.isEmpty(App.getInstance().getPrivincename())
				&& !TextUtils.isEmpty(App.getInstance().getCityname())) {
			iv_dizhi.setText(App.getInstance().getPrivincename() + "-" + App.getInstance().getCityname());
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		App.getInstance().setHuodongleixing("");
		App.getInstance().setClub_name("");
		App.getInstance().setClub_id("");
		App.getInstance().setSelectPathYouJiFenmian("");
		App.getInstance().setPrivincename("");
		App.getInstance().setCityname("");
		try {
			footprintArrayUptoNet = new JSONArray("[]");
			trackArray = new JSONArray("[]");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.top_itv_back:
			App.getInstance().setHuodongleixing("");
			App.getInstance().setClub_name("");
			App.getInstance().setClub_id("");
			App.getInstance().setSelectPathYouJiFenmian("");
			App.getInstance().setPrivincename("");
			App.getInstance().setCityname("");
			try {
				footprintArrayUptoNet = new JSONArray("[]");
				trackArray = new JSONArray("[]");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			finish();
			break;
		case R.id.tv_fb:// 发布
			if (TextUtils.isEmpty(cet_yjbt.getText().toString())) {
				showToast("请输入游记标题");
			} else if (TextUtils.equals(tv_hdlxing.getText().toString(), "未选择")) {
				showToast("请选择游记类型");
			} else if (TextUtils.isEmpty(ctv_hdjj.getText().toString())) {
				showToast("请输入游记简介");
			} else {
				showdialogtext("正在发布中...");
				sign = 1;
				sendAsyn();
			}
			break;
		case R.id.top_tv_right:// 保存时第一次是1存入数据库或2更新,保存时必须从控件上拿去，因为随时可能更改
			if (TextUtils.isEmpty(cet_yjbt.getText().toString())) {
				showToast("请输入游记标题");
			} else if (TextUtils.equals(tv_hdlxing.getText().toString(), "未选择")) {
				showToast("请选择游记类型");
			} else if (TextUtils.isEmpty(ctv_hdjj.getText().toString())) {
				showToast("请输入游记简介");
			} else {
				showdialogtext("正在保存中...");
				sign = 0;
				sendAsyn();
			}
			break;
		case R.id.tv_time:// 弹出时间选择
			onPopdataShengri();
			break;
		case R.id.tv_scfm:// 上传封面
			Intent intent = new Intent(XinJianYouJizjyActivity.this, MultiImageSelectorActivity_YoujiFengmian.class);
			// 是否显示拍摄图片
			intent.putExtra(MultiImageSelectorActivity_YoujiFengmian.EXTRA_SHOW_CAMERA, true);
			// 最大可选择图片数量
			intent.putExtra(MultiImageSelectorActivity_YoujiFengmian.EXTRA_SELECT_COUNT, 1);
			// 选择模式
			intent.putExtra(MultiImageSelectorActivity_YoujiFengmian.EXTRA_SELECT_MODE,
					MultiImageSelectorActivity_YoujiFengmian.MODE_SINGLE);
			startActivityForResult(intent, REQUEST_IMAGE);
			isFrist = false;
			break;
		case R.id.iv_jlb:// 弹出俱乐部
			bundle.putBoolean("isjlb", true);
			openActivity(XinjianyoujijlbOrhdActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tv_hdlxing:// 弹出游记列表
			bundle.putBoolean("isjlb", false);
			openActivity(XinjianyoujijlbOrhdActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tv_gj:// 添加轨迹行程
			openActivity(GuiJiListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tv_xc:// 添加普通行程
			bundle.putString("footprint", "");
			bundle.putBoolean("tianjia", true);
			openActivity(EditYoujiActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tv_starttime:
			isPick = true;
			onPopdataShengri();
			break;
		case R.id.tv_endtime:
			isPick = false;
			onPopdataShengri();
			break;
		case R.id.geren_pop_view:
			break;
		case R.id.geren_pop_image:
			popUser.dismiss();
			break;
		case R.id.geren_qu:
			popUser.dismiss();
			break;
		case R.id.geren_wan:
			popUser.dismiss();
			String mString = "";
			if (isPick) {
				if (nian != null && yue != null && ri != null) {
					mString = nian + yue + ri;
				} else if (nian != null && yue != null && ri == null) {
					mString = nian + yue + datari.get(0);
				} else if (nian != null && yue == null && ri == null) {
					mString = nian + datayue.get(0) + datari.get(0);
				} else if (nian == null && yue != null && ri == null) {
					mString = datanian.get(0) + yue + datari.get(0);
				} else if (nian == null && yue != null && ri != null) {
					mString = datanian.get(0) + yue + ri;
				} else if (nian == null && yue == null && ri != null) {
					mString = datanian.get(0) + datayue.get(0) + ri;
				} else if (nian != null && yue == null && ri != null) {
					mString = nian + datayue.get(0) + ri;
				} else if (nian == null && yue == null && ri == null) {
					mString = datanian.get(0) + datayue.get(0) + datari.get(0);
				}
				tv_starttime.setText(mString.replace("年", "-").replace("月", "-").replace("日", ""));
			} else {
				if (nian != null && yue != null && ri != null) {
					mString = nian + yue + ri;
				} else if (nian != null && yue != null && ri == null) {
					mString = nian + yue + datari.get(0);
				} else if (nian != null && yue == null && ri == null) {
					mString = nian + datayue.get(0) + datari.get(0);
				} else if (nian == null && yue != null && ri == null) {
					mString = datanian.get(0) + yue + datari.get(0);
				} else if (nian == null && yue != null && ri != null) {
					mString = datanian.get(0) + yue + ri;
				} else if (nian == null && yue == null && ri != null) {
					mString = datanian.get(0) + datayue.get(0) + ri;
				} else if (nian != null && yue == null && ri != null) {
					mString = nian + datayue.get(0) + ri;
				} else if (nian == null && yue == null && ri == null) {
					mString = datanian.get(0) + datayue.get(0) + datari.get(0);
				}
				tv_endtime.setText(mString.replace("年", "-").replace("月", "-").replace("日", ""));
			}
			break;
		case R.id.iv_dizhi:
			App.getInstance().setAreaInfo(0);
			openActivity(AreaInfoActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		initDate();
		initkongjian();
		onPopwindownbie();
		gson = new Gson();
		trackArray = new JSONArray();
		footprintArrayUptoNet = new JSONArray();
		team_memberList = new ArrayList<GetActivityTeamMemberResponsePayloadItem>();
		xinjian_view = (RelativeLayout) findViewById(R.id.xinjian_view);
		ll_hddr = (LinearLayout) findViewById(R.id.ll_hddr);
		ctv_hdjj = (CleareditTextView) findViewById(R.id.ctv_hdjj);
		tv_fb = (TextView) findViewById(R.id.tv_fb);
		tv_fb.setOnClickListener(this);
		lv = (MyListView) findViewById(R.id.lv_first);
		lv_guiji = (MyListView) findViewById(R.id.lv_guiji);
		civ_yj = (CircleImageView) findViewById(R.id.civ_yj);
		iv_fm = (ImageView) findViewById(R.id.iv_fm);
		tv_time = (TextView) findViewById(R.id.tv_time);
		cet_yjbt = (CleareditTextView) findViewById(R.id.cet_yjbt);
		tv_scfm = (TextView) findViewById(R.id.tv_scfm);
		cet_rjf = (CleareditTextView) findViewById(R.id.cet_rjf);
		iv_dizhi = (TextView) findViewById(R.id.iv_dizhi);
		iv_jlb = (TextView) findViewById(R.id.iv_jlb);
		tv_hdlxing = (TextView) findViewById(R.id.tv_hdlxing);
		tv_starttime = (TextView) findViewById(R.id.tv_starttime);
		tv_endtime = (TextView) findViewById(R.id.tv_endtime);
		ctv_hddj = (CleareditTextView) findViewById(R.id.ctv_hddj);
		ctv_hdlx = (CleareditTextView) findViewById(R.id.ctv_hdlx);
		tv_gj = (TextView) findViewById(R.id.tv_gj);
		tv_xc = (TextView) findViewById(R.id.tv_xc);
	}

	// 初始化数据
	private void initDate() {
		datanian = new ArrayList<String>();
		datayue = new ArrayList<String>();
		datari = new ArrayList<String>();
		for (int i = 2015; i < 2050; i++) {
			datanian.add(i + "年");
		}
		for (int i = 1949; i < 2015; i++) {
			datanian.add(i + "年");
		}
		for (int i = 1; i < 13; i++) {
			datayue.add(i + "月");
		}
		for (int i = 1; i < 32; i++) {
			datari.add(i + "日");
		}
	}

	private boolean isAct_id = false;// 是否从游记取轨迹id
	private boolean isTvl_id = false;// 是否从游记取轨迹id

	@Override
	protected void onResload() {
		showdialog();
		top_tv_title.setText("新建游记");
		top_tv_right.setText("保存");
		top_tv_right.setVisibility(View.VISIBLE);
		xinjian_view.setLayoutParams(
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
		footprintAdpter = new MyFootprintAdpter(XinJianYouJizjyActivity.this, footprintArrayUptoNet);
		lv.setAdapter(footprintAdpter);
		myTrackAdpter = new MyTrackAdpter(XinJianYouJizjyActivity.this, trackArray);
		lv_guiji.setAdapter(myTrackAdpter);
		tvl_id = getIntent().getStringExtra("tvl_id");
		act_id = getIntent().getStringExtra("act_id");
		if (!TextUtils.isEmpty(tvl_id) && !TextUtils.isEmpty(act_id)) {
			isTvl_id = true;
			isAct_id = true;
			ll_hddr.setVisibility(View.VISIBLE);
			sendGetActivityListActionAsyn();
		} else if (TextUtils.isEmpty(tvl_id) && !TextUtils.isEmpty(act_id)) {
			isTvl_id = false;
			isAct_id = true;
			ll_hddr.setVisibility(View.VISIBLE);
			sendGetActivityListActionAsyn();
		} else if (!TextUtils.isEmpty(tvl_id) && TextUtils.isEmpty(act_id)) {
			isTvl_id = true;
			isAct_id = false;
			ll_hddr.setVisibility(View.GONE);
			sendGetTravelInfoByIDActionAsyn();
		} else if (TextUtils.isEmpty(tvl_id) && TextUtils.isEmpty(act_id)) {
			isTvl_id = false;
			isAct_id = false;
			ll_hddr.setVisibility(View.GONE);
			tv_time.setText(TimeUtil.getDatetime_SimpleDateFormat1());
			// 头像
			mImageLoader.displayImage(App.getInstance().getHeadurl(), civ_yj, options);
			if (isshowdialog()) {
				closedialog();
			}
		}
	}

	@Override
	protected void setMyViewClick() {
		top_tv_right.setOnClickListener(this);
		top_itv_back.setOnClickListener(this);
		tv_scfm.setOnClickListener(this);
		iv_jlb.setOnClickListener(this);
		tv_gj.setOnClickListener(this);
		tv_xc.setOnClickListener(this);
		tv_hdlxing.setOnClickListener(this);
		geren_qu.setOnClickListener(this);
		geren_wan.setOnClickListener(this);
		iv_dizhi.setOnClickListener(this);
		tv_starttime.setOnClickListener(this);
		tv_endtime.setOnClickListener(this);
		geren_pop_view.setOnClickListener(this);
		geren_pop_image.setOnClickListener(this);
	}

	/** 得到选择的照片 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			handlerlist.sendEmptyMessage(CommonUtility.UPLOADING);
		}
	}

	// 开始时间
	private void onPopdataShengri() {
		popUser.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		pickerviewright.setVisibility(View.VISIBLE);
		pickerviewright_right.setVisibility(View.VISIBLE);
		pickerviewleft.setData(datanian);
		pickerviewright.setData(datayue);
		pickerviewright_right.setData(datari);
		pickerviewleft.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				nian = text;
				String string = nian.subSequence(0, 4).toString();
				int year = Integer.valueOf(string).intValue();
				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
					// 是闰年
					isRuiNian = true;
					if (yue != null) {
						if (yue.equals("2月")) {
							datari.clear();
							for (int i = 1; i < 30; i++) {
								datari.add(i + "日");
							}
							pickerviewright_right.setData(datari);
						}
					}
				} else {
					// 不是闰年
					isRuiNian = false;
					if (yue != null) {
						if (yue.equals("2月")) {
							datari.clear();
							for (int i = 1; i < 29; i++) {
								datari.add(i + "日");
							}
							pickerviewright_right.setData(datari);
						}
					}
				}
			}
		});
		pickerviewright.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				yue = text;
				if (yue.equals("2月") && !isRuiNian) {
					datari.clear();
					for (int i = 1; i < 29; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("2月") && isRuiNian) {
					datari.clear();
					for (int i = 1; i < 30; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("4月") || yue.equals("6月") || yue.equals("9月") || yue.equals("11月")) {
					datari.clear();
					for (int i = 1; i < 31; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("1月") || yue.equals("3月") || yue.equals("5月") || yue.equals("7月")
						|| yue.equals("8月") || yue.equals("10月") || yue.equals("12月")) {
					datari.clear();
					for (int i = 1; i < 32; i++) {
						datari.add(i + "日");
					}
				}
				pickerviewright_right.setData(datari);
			}
		});
		pickerviewright_right.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				ri = text;
			}
		});
	}

	private void onPopwindownbie() {
		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件
		View view = inflater.inflate(R.layout.popwindow_usershezhi, null);
		pickerviewtext = (TextView) view.findViewById(R.id.pickerviewtext);
		// 初始化pop中的组件
		pickerviewleft = (PickerView) view.findViewById(R.id.pickerviewleft);
		pickerviewright = (PickerView) view.findViewById(R.id.pickerviewright);
		pickerviewright_right = (PickerView) view.findViewById(R.id.pickerviewright_right);
		geren_qu = (TextView) view.findViewById(R.id.geren_qu);
		geren_wan = (TextView) view.findViewById(R.id.geren_wan);
		geren_pop_view = (LinearLayout) view.findViewById(R.id.geren_pop_view);
		geren_pop_image = (RelativeLayout) view.findViewById(R.id.geren_pop_image);
		// 创建PopupWindow对象
		popUser = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popUser.setBackgroundDrawable(new BitmapDrawable());
	}

	/** 弹出清除对话框Dialog */
	public void showAlertDialog(Context context, String title, final int position) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				footprintArrayUptoNet.remove(position);
				if (footprintAdpter != null) {
					footprintAdpter.notifyDataSetChanged();
				}
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public class MyFootprintAdpter extends BaseAdapter {
		private Context context = null;
		private LayoutInflater mInflater = null;
		private JSONArray mDatas = null;

		public MyFootprintAdpter(Context context, JSONArray footprintArrayUptoNet) {
			this.context = context;
			this.mDatas = footprintArrayUptoNet;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mDatas != null ? mDatas.length() : 0;
		}

		@Override
		public Object getItem(int position) {
			try {
				return mDatas.get(position);
			} catch (JSONException e) {
			}
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// final YouJiXiangQingJiaoYin news = mDatas.get(position); //
			// 获取当前项数据
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_xieyouji_typefootprints_item, null);
				holder.gr_time_line = (MyGridView) convertView.findViewById(R.id.gr_time_line);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.title_jiaoyin = (TextView) convertView.findViewById(R.id.title_jiaoyin);
				holder.desc_jiaoyin = (TextView) convertView.findViewById(R.id.desc_jiaoyin);
				holder.tv_bianji = (TextView) convertView.findViewById(R.id.tv_bianji);
				holder.tv_shanchu = (TextView) convertView.findViewById(R.id.tv_shanchu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			try {
				holder.desc_jiaoyin.setText(mDatas.getJSONObject(position).getString("footprint_desc"));
				holder.time.setText(mDatas.getJSONObject(position).getString("footprint_time"));
				holder.title_jiaoyin.setText(mDatas.getJSONObject(position).getString("footprint_title"));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			/** 对脚印编辑 */
			holder.tv_bianji.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					try {
						bundle.putString("footprint", mDatas.getJSONObject(position).toString());
						bundle.putInt("footprintPotion", position);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					openActivity(EditYoujiActivity.class, bundle);
				}
			});
			/** 对脚印删除 */
			holder.tv_shanchu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showAlertDialog(context, "是否删除此条脚印?                   ", position);
				}
			});
			holder.gr_time_line.setAdapter(new BaseAdapter() {

				@Override
				public View getView(int positionitem, View convertView, ViewGroup parent) {
					ViewHolder holder = null;
					if (null == convertView) {
						holder = new ViewHolder();
						convertView = mInflater.inflate(R.layout.item_youjixiangqing_image, null);
						holder.image = (ImageView) convertView.findViewById(R.id.image);
						convertView.setTag(holder);
					} else {
						holder = (ViewHolder) convertView.getTag();
					}
					holder.image.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							App.getInstance().getScreenHeight() / 3));
					if (NetUtil.isNetworkAvailable(context)) {
						try {
							mImageLoader.displayImage(
									mDatas.getJSONObject(position).getJSONArray("imgs").get(positionitem).toString(),
									holder.image, options);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else {
						try {
							mImageLoader.displayImage(
									"file://" + mDatas.getJSONObject(position).getJSONArray("imgs").get(positionitem),
									holder.image, options);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					return convertView;
				}

				@Override
				public long getItemId(int position) {
					return 0;
				}

				@Override
				public Object getItem(int position) {
					return null;
				}

				@Override
				public int getCount() {
					try {
						return mDatas != null ? mDatas.getJSONObject(position).getJSONArray("imgs").length() : 0;
					} catch (JSONException e) {
						return 0;
					}
				}

			});
			if (mDatas.length() >= 5) {
				tv_xc.setVisibility(View.GONE);
			} else {
				tv_xc.setVisibility(View.VISIBLE);
			}
			return convertView;
		}

		private final class ViewHolder {
			MyGridView gr_time_line = null;
			TextView time = null;
			TextView tip = null;
			ImageView image = null;
			TextView title_jiaoyin = null;
			TextView desc_jiaoyin = null;
			TextView tv_bianji = null;// 编辑
			TextView tv_shanchu = null;// 删除
		}

	}

	public class MyTrackAdpter extends BaseAdapter {
		private Context context = null;
		private LayoutInflater mInflater = null;
		private JSONArray mDatas = null;

		public MyTrackAdpter(Context context, JSONArray mDatas) {
			this.context = context;
			this.mDatas = mDatas;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {

			return mDatas != null ? mDatas.length() : 0;
		}

		@Override
		public Object getItem(int position) {

			try {
				return mDatas.get(position);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return position;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			StringBuilder lujin = new StringBuilder();
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_typetrack_item, null);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.shanchu = (TextView) convertView.findViewById(R.id.shanchu);
				holder.guiji_lujin = (TextView) convertView.findViewById(R.id.guiji_lujin);
				holder.CB_image = (ImageView) convertView.findViewById(R.id.CB_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.CB_image.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
			try {
				holder.name.setText(mDatas.getJSONObject(position).getString("name"));
				String start = mDatas.getJSONObject(position).getString("act_real_start_time");
				String end = mDatas.getJSONObject(position).getString("act_real_end_time");
				if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
					holder.time
							.setText(start.replace("-", ".") + "-" + end.substring(5, end.length()).replace("-", "."));
				}
			} catch (JSONException e1) {
			}
			try {
				mImageLoader.displayImage(mDatas.getJSONObject(position).getString("picture_url"), holder.CB_image,
						options);
			} catch (JSONException e1) {
			}
			try {
				for (int i = 0; i < mDatas.getJSONObject(position).getJSONArray("trace_data").length(); i++) {
					if (!TextUtils.isEmpty(lujin.append(mDatas.getJSONObject(position).getJSONArray("trace_data")
							.getJSONObject(i).getString("name")))) {
						if (i == mDatas.getJSONObject(position).getJSONArray("trace_data").length() - 1) {
							lujin.append(mDatas.getJSONObject(position).getJSONArray("trace_data").getJSONObject(i)
									.getString("name"));
						} else {
							lujin.append(mDatas.getJSONObject(position).getJSONArray("trace_data").getJSONObject(i)
									.getString("name") + "-");
						}
					}
				}
			} catch (JSONException e) {
			}
			holder.guiji_lujin.setText(lujin);
			/** 对轨迹删除 */
			holder.shanchu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					trackArray.remove(position);
					mDatas = trackArray;
					myTrackAdpter.notifyDataSetChanged();
				}
			});
			if (mDatas.length() >= 5) {
				tv_gj.setVisibility(View.GONE);
			} else {
				tv_gj.setVisibility(View.VISIBLE);
			}

			return convertView;
		}

		private final class ViewHolder {
			TextView name = null;
			TextView shanchu = null;
			ImageView CB_image = null;
			TextView time = null;
			TextView guiji_lujin = null;
		}

	}

	private Thread thread = null;

	// 游记详情
	public void sendGetActivityListActionAsyn() {
		thread = new Thread() {
			public void run() {
				getActivityListAction();
			}
		};
		thread.start();
	}

	// 取得游记详情的字段
	private String tvl_cover;// 游记封面logo
	private String footprint_data;// 游记足印（脚印数据app）
	private double rel_speed = 0.0;// 平均速度，手填，均速
	private String act_title = "";// 游记标题
	private String act_desc = "";// 游记描述
	private String act_desc_intro = "";// 游记描述
	private Integer act_level = null;// 游记级别
	private String act_type = "";// 游记类型：如登山
	private String club_name = "";// 游记所属俱乐部
	private String leader_name = "";// 游记所属领队
	private String tvl_create_time = "";// 游记创建时间
	private String tvl_update_time = "";
	private String act_start_time = "";// 游记开始时间
	private String act_end_time = "";// 游记结束时间
	private Integer act_join_num_limit = null;// 参加人数限制
	private String act_entry_free = "";// 游记费用包含
	private String act_weixin = "";// 游记微信号
	private String act_base_equip = "";// 参加游记准备基本装备
	private String logistics = "";// 后勤
	private Integer trace_file_id = null;// 轨迹文件id
	private Integer confirm_member = null;// 确认队员人数
	private Integer man = null;// 男性人数
	private Integer female = null;// ,女性人数
	private String act_venue_time = "";
	private String act_venue = "";
	private String act_else = "";
	private String club_id = "";// 所属俱乐部
	private String leader_id = "";// 所属领队id
	private String anchor_longitude = "";// 锚点经度
	private String trace_data = "";
	private String trace_dataact = "";
	private String anchor_latitude = "";// 锚点纬度
	private String city = "";// 离线地图包城市
	private String act_line = "";// 游记路线
	private Integer distance = 0;// 里程

	public void getActivityListAction() {
		GetActivityInfoByIDRequest request = new GetActivityInfoByIDRequest();
		GetActivityInfoByIDRequestParameter parameter = new GetActivityInfoByIDRequestParameter();
		parameter.setAct_id(act_id);
		request.setParameter(parameter);
		request.setAuthenticationToken(App.getInstance().getAut());
		String requestString = null;
		try {
			requestString = request.toJSONString();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(requestString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					GetActivityInfoByIDResponse response = new GetActivityInfoByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetActivityInfoByIDResponsePayload payload = (GetActivityInfoByIDResponsePayload) response
							.getPayload();
					act_title = payload.getAct_title();
					act_level = payload.getAct_level();
					tvl_create_time = payload.getAct_venue_time();
					act_start_time = payload.getAct_start_time();
					act_end_time = payload.getAct_end_time();
					act_entry_free = payload.getAct_budget() + "";
					leader_id = payload.getLeader_id();
					club_id = payload.getClub_id();
					act_venue = payload.getAct_venue();
					act_venue_time = payload.getAct_venue_time();
					trace_file_id = payload.getTrace_file_id();
					act_type = payload.getAct_type();
					club_name = payload.getClub_name();
					leader_name = payload.getLeader_name();
					act_desc = payload.getAct_desc();
					act_desc_intro = payload.getAct_desc_intro();
					act_join_num_limit = payload.getAct_join_num_limit();
					act_weixin = payload.getAct_weixin();
					act_base_equip = payload.getAct_base_equip();
					logistics = payload.getLogistics();
					confirm_member = payload.getConfirm_member();
					man = payload.getMan();
					female = payload.getFemale();
					act_else = payload.getAct_else();
					city = payload.getAct_depart_place();
					tvl_cover = payload.getAct_logo();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	// 取得游记详情
	public void sendGetTravelInfoByIDActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetTravelInfoByIDAction();
			}
		};
		thread.start();
	}

	public void GetTravelInfoByIDAction() {
		GetTravelInfoByIDRequest request = new GetTravelInfoByIDRequest();
		GetTravelInfoByIDRequestParameter parameter = new GetTravelInfoByIDRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setTvl_id(tvl_id);
		request.setParameter(parameter);
		String requestString = null;
		try {
			requestString = request.toJSONString();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(requestString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					GetTravelInfoByIDResponse response = new GetTravelInfoByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetTravelInfoByIDResponsePayload payload = (GetTravelInfoByIDResponsePayload) response.getPayload();
					getDataFromServer(payload);
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK5);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	// 根据轨迹id取得轨迹数据
	public void sendGetTracelInfoByIDAsyn() {
		thread = new Thread() {
			public void run() {
				getTraceInfoByIDAction();
			}
		};
		thread.start();
	}

	public void getTraceInfoByIDAction() {
		GetTraceInfoByIDRequest request = new GetTraceInfoByIDRequest();
		GetTraceInfoByIDRequestParameter parameter = new GetTraceInfoByIDRequestParameter();
		parameter.setTrace_id(String.valueOf(trace_file_id));
		request.setParameter(parameter);
		request.setAuthenticationToken(App.getInstance().getAut());
		String requestString = null;
		try {
			requestString = request.toJSONString();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(requestString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					GetTraceInfoByIDResponse response = new GetTraceInfoByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetTraceInfoByIDResponsePayload payload = (GetTraceInfoByIDResponsePayload) response.getPayload();
					anchor_longitude = payload.getAnchor_longitude();
					trace_dataact = payload.getTrace_data();
					anchor_latitude = payload.getAnchor_latitude();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
				}
			} catch (JSONException e1) {

				e1.printStackTrace();
			}
		} else {
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	private ArrayList<GetActivityTeamMemberResponsePayloadItem> team_memberList = null;

	// 取得游记下的所有队员名称
	public void sendGetActivityTeamMemberAsyn() {
		thread = new Thread() {
			public void run() {
				getActivityTeamMemberAction();
			}
		};
		thread.start();
	}

	public void getActivityTeamMemberAction() {
		GetActivityTeamMemberRequest request = new GetActivityTeamMemberRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetActivityTeamMemberRequestParameter parameter = new GetActivityTeamMemberRequestParameter();
		parameter.setAct_id(act_id);
		request.setParameter(parameter);
		String requestString = null;
		try {
			requestString = request.toJSONString();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(requestString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					GetActivityTeamMemberResponse response = new GetActivityTeamMemberResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetActivityTeamMemberResponsePayload payload = (GetActivityTeamMemberResponsePayload) response
							.getPayload();
					team_memberList = payload.getTeam_memberList();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
				}
			} catch (JSONException e1) {

				e1.printStackTrace();
			}
		} else {
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	/**
	 * 发布游记
	 */
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() {
		AddTravelRequest request = new AddTravelRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		AddTravelRequestParameter parameter = new AddTravelRequestParameter();
		parameter.setSign(sign);// 标志位，0为保存（设置状态-4），1为提交（设置状态为0）
		parameter.setAct_id(act_id);
		parameter.setTvl_id(tvl_id);
		// 关联的俱乐部id
		parameter.setClub_id(club_id);
		// 游记封面logo
		parameter.setTvl_cover(tvl_cover);
		// 游记标题
		parameter.setTvl_title(cet_yjbt.getText().toString());
		// 简介
		parameter.setTvl_desc(ctv_yjms.getText().toString());
		parameter.setTvl_desc_web(ctv_yjms.getText().toString());
		parameter.setAct_desc_intro(ctv_hdjj.getText().toString());
		// 开始时间
		parameter.setAct_start_time(tv_starttime.getText().toString());
		// 结束时间
		parameter.setAct_end_time(tv_endtime.getText().toString());
		// 集合时间
		parameter.setAct_venue_time(act_venue_time);
		// 集合地点
		parameter.setAct_venue(act_venue);
		// 游记等级
		parameter.setAct_level(Integer
				.parseInt(!TextUtils.isEmpty(ctv_hddj.getText().toString()) ? ctv_hddj.getText().toString() : "0"));
		// 领队名称
		parameter.setLeader_name(leader_name);
		parameter.setLeader_id(leader_id);
		// 地域（城市）
		parameter.setCity(iv_dizhi.getText().toString());
		// 实际距离
		parameter.setDistance(Integer
				.valueOf(TextUtils.isEmpty(ctv_yjlc.getText().toString()) ? "0" : ctv_yjlc.getText().toString()));
		// 轨迹数据
		parameter.setTrace_data(trackArray.toString());
		// 锚点纬度
		parameter.setAnchor_latitude(anchor_latitude);
		// 锚点经度
		parameter.setAnchor_longitude(anchor_longitude);
		// 游记类型(同游记类型)
		parameter.setTvl_type(tv_hdlxing.getText().toString());
		// 游记描述
		parameter.setTvl_desc(ctv_yjms.getText().toString());
		parameter.setTvl_desc_web(ctv_yjms.getText().toString());
		parameter.setAct_desc_intro(ctv_hdjj.getText().toString());
		// 游记足印(脚印数据web)
		parameter.setFootprint_data_web(footprintArrayUptoNet.toString());
		parameter.setFootprint_data(footprintArrayUptoNet.toString());
		// 费用
		parameter.setAct_entry_fee(cet_rjf.getText().toString());
		// 装备
		parameter.setAct_most_equip(ctv_cxzb.getText().toString());
		// 报名须知
		parameter.setLogistics(ctv_bmxz.getText().toString());
		// 游记队员（头像，id json串）
		parameter.setTeam_member(gson.toJson(team_memberList));
		// 是否分享(是否私有)
		parameter.setIs_share(0);
		// 队伍人数
		parameter.setTeam_number(Integer
				.parseInt(!TextUtils.isEmpty(ctv_yqrdy.getText().toString()) ? ctv_yqrdy.getText().toString() : "0"));
		// 计划人数
		parameter.setAct_join_num_limit(Integer
				.parseInt(!TextUtils.isEmpty(ctv_jhdw.getText().toString()) ? ctv_jhdw.getText().toString() : "0"));
		// 微信号
		parameter.setAct_weixin(tv_wxh.getText().toString());
		// 平均速度，手填，均速
		parameter.setRel_speed(rel_speed);
		// 已确认队员人数
		parameter.setConfirmed_member(Integer
				.parseInt(!TextUtils.isEmpty(ctv_yqrdy.getText().toString()) ? ctv_yqrdy.getText().toString() : "0"));
		// 副队人数
		parameter.setViceleadercount(0);
		parameter.setFemalecount(
				Integer.parseInt(!TextUtils.isEmpty(tv_nv.getText().toString()) ? tv_nv.getText().toString() : "0"));
		parameter.setMalecount(
				Integer.parseInt(!TextUtils.isEmpty(tv_nan.getText().toString()) ? tv_nan.getText().toString() : "0"));
		// 其他
		parameter.setTvl_else(ctv_qt.getText().toString());
		// 路线
		parameter.setAct_line(ctv_hdlx.getText().toString());
		parameter.setTvl_create_time(tvl_create_time);
		parameter.setTvl_update_time(tvl_update_time);
		request.setParameter(parameter);
		String requestString = null;
		try {
			requestString = request.toJSONString();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(requestString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					AddTravelResponse response = new AddTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					AddTravelResponsePayload payload = (AddTravelResponsePayload) response.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK4);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 队员列表
	private List<HuoDongDuiYuan> HuoDongDuiYuanBeans = null;
	private HuoDongDuiYuan HuoDongDuiYuanBean = null;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:// 游记详情
				if (isAct_id) {
					sendGetTracelInfoByIDAsyn();
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);
				}
				break;
			case CommonUtility.SERVEROK2:// 轨迹
				sendGetActivityTeamMemberAsyn();
				break;
			case CommonUtility.SERVEROK3:// 队员信息 往界面上放数据
				if (isTvl_id) {
					sendGetTravelInfoByIDActionAsyn();
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK5);
				}
				break;
			case CommonUtility.SERVEROK4:// 发布游记
				App.getInstance().setHuodongleixing("");
				App.getInstance().setClub_name("");
				App.getInstance().setClub_id("");
				App.getInstance().setSelectPathYouJiFenmian("");
				try {
					footprintArrayUptoNet = new JSONArray("[]");
					trackArray = new JSONArray("[]");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (isshowdialog()) {
					closedialog();
				}
				finish();
				openActivity(XieYouJiActivity.class);
				break;
			case CommonUtility.SERVEROK5:// 编辑游记
				// 头像
				mImageLoader.displayImage(App.getInstance().getHeadurl(), civ_yj, options);
				// 封面
				if (isAct_id && !isTvl_id && !TextUtils.isEmpty(tvl_cover)) {
					try {
						JSONArray array = new JSONArray(tvl_cover);
						mImageLoader.displayImage(array.get(0).toString(), iv_fm, options);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					mImageLoader.displayImage(tvl_cover, iv_fm, options);
				}
				// 创建时间
				tv_time.setText(TimeDateUtils.formatDateFromDatabaseTime(tvl_create_time));
				// 标题
				cet_yjbt.setText(act_title);
				// 费用
				cet_rjf.setText(act_entry_free + "");
				iv_dizhi.setText(city);
				// 俱乐部
				iv_jlb.setText(club_name);
				// 游记
				tv_hdlxing.setText(act_type);
				// 游记等级
				ctv_hddj.setText(act_level + "");
				// 游记简介
				ctv_hdjj.setText(act_desc_intro);
				// 游记里程
				ctv_yjlc.setText(distance + "");
				if (!TextUtils.isEmpty(act_start_time)) {
					tv_starttime.setText(TimeDateUtils.formatDateFromDatabaseTime(act_start_time));
				}
				if (!TextUtils.isEmpty(act_end_time)) {
					tv_endtime.setText(TimeDateUtils.formatDateFromDatabaseTime(act_end_time));
				}
				if (isTvl_id) {
					try {
						for (int i = 0; i < new JSONArray(trace_data).length(); i++) {
							trackArray.put(new JSONArray(trace_data).get(i));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					// 游记路线
					ctv_hdlx.setText(act_line);
				} else if (isAct_id && !isTvl_id) {
					// 游记路线
					ctv_hdlx.setText("");
					try {
						JSONArray array = new JSONArray(trace_dataact);
						if (array.length() != 0) {
							for (int i = 0; i < array.length(); i++) {
								// LatLng latlng = new
								// LatLng(array.getJSONObject(i).getDouble("latitude"),
								// array.getJSONObject(i).getDouble("longitude"));
								// LatLnglist.add(latlng);
								if (i == array.length() - 1) {
									ctv_hdlx.append(array.getJSONObject(i).getString("name"));
								} else {
									ctv_hdlx.append(array.getJSONObject(i).getString("name") + "-");
								}
							}
						}
						// if (LatLnglist.size() != 0) {
						// for (int j = 0; j < LatLnglist.size(); j++) {
						// if (j != LatLnglist.size() - 1) {
						// licheng +=
						// DistanceUtil.getDistance(LatLnglist.get(j),
						// LatLnglist.get(j + 1));
						// }
						// }
						// }ParseOject.StringToDouble(licheng / 1000) + "km"
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
				if (footprint_data != null) {
					try {
						for (int i = 0; i < new JSONArray(footprint_data).length(); i++) {
							footprintArrayUptoNet.put(new JSONArray(footprint_data).get(i));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (myTrackAdpter != null && myTrackAdpter.getCount() != 0) {
					myTrackAdpter.notifyDataSetChanged();
				}
				if (footprintAdpter != null && footprintAdpter.getCount() != 0) {
					footprintAdpter.notifyDataSetChanged();
				}
				if (trackArray.length() >= 5) {
					tv_gj.setVisibility(View.GONE);
				} else {
					tv_gj.setVisibility(View.VISIBLE);
				}
				if (footprintArrayUptoNet.length() >= 5) {
					tv_xc.setVisibility(View.GONE);
				} else {
					tv_xc.setVisibility(View.VISIBLE);
				}
				if (isAct_id) {
					// 行程费用
					// ctv_xcfy.setText(act_entry_free + "");
					// 游记描述
					ctv_yjms.setText(act_desc);
					// 出行装备
					ctv_cxzb.setText(act_base_equip);
					// 报名须知
					ctv_bmxz.setText(logistics);
					// 其他
					ctv_qt.setText(act_else);
					// 计划队伍
					ctv_jhdw.setText(act_join_num_limit + "");
					// 已确认队员人数
					ctv_yqrdy.setText(confirm_member + "");
					// 领队
					ctv_ld.setText(leader_name);
					// 副队
					ctv_fd.setText("0");
					// 微信号
					tv_wxh.setText(act_weixin);
					// 男
					tv_nan.setText(man + "");
					// 女
					tv_nv.setText(female + "");
					// 队员
					HuoDongDuiYuanBeans = new ArrayList<HuoDongDuiYuan>();
					for (int i = 0; i < team_memberList.size(); i++) {
						HuoDongDuiYuanBean = new HuoDongDuiYuan();
						HuoDongDuiYuanBean.setHead(team_memberList.get(i).getHead());
						HuoDongDuiYuanBean.setUserid(team_memberList.get(i).getUserid());
						HuoDongDuiYuanBeans.add(HuoDongDuiYuanBean);
					}
					gv_dy.setAdapter(new CommonAdapter<HuoDongDuiYuan>(XinJianYouJizjyActivity.this,
							HuoDongDuiYuanBeans, R.layout.item_duiyuan) {
						@Override
						public void convert(ViewHolderUntil helper, final HuoDongDuiYuan item, int position) {
							helper.setCubeImageByUrl(R.id.haoyou_icon, item.getHead(), imageLoader);
							helper.getView(R.id.haoyou_icon).setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Bundle bundle = new Bundle();
									bundle.putString("Userid", item.getUserid());
									openActivity(HaoYouDetailActivity.class, bundle);
								}
							});
						}
					});
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				break;
			case CommonUtility.UPLOADING:
				if (!TextUtils.isEmpty(App.getInstance().getSelectPathYouJiFenmian())) {
					File file = new File(App.getInstance().getSelectPathYouJiFenmian());
					if (file.exists()) {
						post = new HttpMultipartPost(XinJianYouJizjyActivity.this,
								App.getInstance().getSelectPathYouJiFenmian());
						try {
							result = post.execute().get();
							try {
								JSONObject jsonObject = new JSONObject(result);
								tvl_cover = jsonObject.getString("fullurl");
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case CommonUtility.UPLOADINGCANCLE:
				if (post != null) {
					if (!post.isCancelled()) {
						post.cancel(true);
					}
				}
				break;
			default:
				break;
			}
		};
	};
}