package com.hwacreate.outdoor.mainFragment.zhuzhi;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.MainActivity;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.mainFragment.wode.HaoYouDetailActivity;
import com.hwacreate.outdoor.mainFragment.youji.YoujiXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CustomDialog;
import com.hwacreate.outdoor.view.MyViewPager;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequest;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequestParameter;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponse;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetClubActsByIDAction.GetClubActsByIDActivitylistItem;
import com.keyhua.outdoor.protocol.GetClubActsByIDAction.GetClubActsByIDRequest;
import com.keyhua.outdoor.protocol.GetClubActsByIDAction.GetClubActsByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetClubActsByIDAction.GetClubActsByIDResponse;
import com.keyhua.outdoor.protocol.GetClubActsByIDAction.GetClubActsByIDResponsePayload;
import com.keyhua.outdoor.protocol.GetClubInfoByIDAction.GetClubInfoByIDRequest;
import com.keyhua.outdoor.protocol.GetClubInfoByIDAction.GetClubInfoByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetClubInfoByIDAction.GetClubInfoByIDResponse;
import com.keyhua.outdoor.protocol.GetClubInfoByIDAction.GetClubInfoByIDResponsePayload;
import com.keyhua.outdoor.protocol.GetClubLeadersByIDAction.GetClubLeadersByIDLeaderlistItem;
import com.keyhua.outdoor.protocol.GetClubLeadersByIDAction.GetClubLeadersByIDRequest;
import com.keyhua.outdoor.protocol.GetClubLeadersByIDAction.GetClubLeadersByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetClubLeadersByIDAction.GetClubLeadersByIDResponse;
import com.keyhua.outdoor.protocol.GetClubLeadersByIDAction.GetClubLeadersByIDResponsePayload;
import com.keyhua.outdoor.protocol.GetClubMemdersByIDAction.GetClubMemdersByIDMemberDetailItem;
import com.keyhua.outdoor.protocol.GetClubMemdersByIDAction.GetClubMemdersByIDRequest;
import com.keyhua.outdoor.protocol.GetClubMemdersByIDAction.GetClubMemdersByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetClubMemdersByIDAction.GetClubMemdersByIDResponse;
import com.keyhua.outdoor.protocol.GetClubMemdersByIDAction.GetClubMemdersByIDResponsePayload;
import com.keyhua.outdoor.protocol.GetClubTvlsByIDAction.GetClubTvlsByIDRequest;
import com.keyhua.outdoor.protocol.GetClubTvlsByIDAction.GetClubTvlsByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetClubTvlsByIDAction.GetClubTvlsByIDResponse;
import com.keyhua.outdoor.protocol.GetClubTvlsByIDAction.GetClubTvlsByIDResponsePayload;
import com.keyhua.outdoor.protocol.GetClubTvlsByIDAction.GetClubTvlsByIDTravellistItem;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserRequest;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserRequestParameter;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserResponse;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserResponsePayload;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubRequest;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubRequestParameter;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubResponse;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubResponsePayload;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubRequest;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubRequestParameter;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubResponse;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubResponsePayload;
import com.keyhua.outdoor.protocol.OutClubAction.OutClubRequest;
import com.keyhua.outdoor.protocol.OutClubAction.OutClubRequestParameter;
import com.keyhua.outdoor.protocol.OutClubAction.OutClubResponse;
import com.keyhua.outdoor.protocol.OutClubAction.OutClubResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class JuLeBuXiangQingActivity extends BaseActivity implements OnPageChangeListener, OnItemClickListener {
	private Context mContext = null;
	// 头部
	private View headerLayout = null;
	private LinearLayout headerParent = null;
	// 上拉下拉刷新
	LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;
	public int index = 0;
	public int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;
	// 图片轮播
	private MyViewPager viewPager = null;
	private PagerAdapter pagerAdapter = null;
	private LinearLayout indicatorLayout = null;
	private ArrayList<View> views = null;
	private ImageView[] indicators = null;
	private ImageHandler handler = new ImageHandler(new WeakReference<JuLeBuXiangQingActivity>(this));
	// 显示的控件
	private ImageView julebu_icon = null;
	private TextView julebu_title = null;
	private TextView julebu_dizhi = null;
	private TextView julebu_chuang = null;
	private TextView julebu_phone = null;
	private TextView julebu_qq = null;
	private TextView julebu_mail = null;
	private TextView julebu_des = null;
	private TextView julebu_huodong = null;
	private TextView julebu_dengji = null;
	private TextView julebu_chengyuan = null;
	private LinearLayout julebu_huodong_layout = null;
	private LinearLayout julebu_julebu_layout = null;
	private LinearLayout julebu_lingdui_layout = null;
	private LinearLayout julebu_chengyuan_layout = null;
	private View julebu_huodong_view = null;
	private View julebu_julebu_view = null;
	private View julebu_lingdui_view = null;
	private View julebu_chengyuan_view = null;
	// 通过俱乐部ID获取下属成员信息
	List<GetClubMemdersByIDMemberDetailItem> memberDetailsList = null;
	List<GetClubMemdersByIDMemberDetailItem> memberDetailsListTemp = null;
	// 通过俱乐部ID获取下属领队列表信息
	List<GetClubLeadersByIDLeaderlistItem> clubLeadersByIDLeaderlist = null;
	List<GetClubLeadersByIDLeaderlistItem> clubLeadersByIDLeaderlistTemp = null;
	// 通过俱乐部ID获取活动
	List<GetClubActsByIDActivitylistItem> clubActsByIDActivityList = null;
	List<GetClubActsByIDActivitylistItem> clubActsByIDActivityListTemp = null;
	// 通过俱乐部ID获取下属游记列表信息
	List<GetClubTvlsByIDTravellistItem> clubTvlsByIDTravellist = null;
	List<GetClubTvlsByIDTravellistItem> clubTvlsByIDTravellistTemp = null;
	private String tvl_id; // 游记id
	private int state = 0;
	// 俱乐部信息
	private String club_id; // 俱乐部id
	private String club_imges; // 俱乐部顶部图片
	private String club_name; // 俱乐部名称
	private String club_city; // 俱乐部所在城市
	private String club_logo; // 俱乐部头像
	private String club_desc;// 俱乐部简介
	private String club_chuang;// 俱乐部创建时间
	private String club_phone;// 俱乐部联系电话
	private String club_qq;// 俱乐部QQ
	private String club_mail;// 俱乐部邮箱
	private int club_huodong = 1; // 成员人数
	private int club_dengji = 1; // 成员人数
	private int club_chengyuan = 1; // 活动数量
	private int issignup = 1;// 是否报名，0为未加入，1为已加入
	private String Userid = null;// 用户id
	private int leaderApplyState = 3;// 2：未申请过 1：审核通过 0：待审核 -1为拒绝
	private String reason = null;// 审核原因
	// 数据库操作
	private Integer isDownLoad = 0;
	private List<YoujiXiangqing> mYoujiDatas = null;
	private YoujiXiangqing youjiXiangqing = null;
	private BaseDao<YoujiXiangqing> youjiBaseDao = null;
	private int listStat = 0;
	// pop sos按钮弹出
	private View parentView = null;
	private PopupWindow popContact = null;
	private RelativeLayout pop_huodong_image = null;// 半透明背景色
	private ImageView pop_huodong_close_shibai = null;// 取消
	private LinearLayout pop_huodong_view_shibai = null;
	private TextView pop_huodong_shibai_content = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_detailed_list, null);
		setContentView(parentView);
		init();
	}

	/** Dialog */
	public void showAlertDialog(String title) {
		CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				sendOutClubActionAsyn();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private View view = null;

	private void initPopwindow() {
		view = getLayoutInflater().inflate(R.layout.pop_huodongxiangqing, null);
		popContact = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popContact.setBackgroundDrawable(new BitmapDrawable());
		pop_huodong_image = (RelativeLayout) view.findViewById(R.id.pop_huodong_image);
		pop_huodong_close_shibai = (ImageView) view.findViewById(R.id.pop_huodong_close_shibai);
		pop_huodong_view_shibai = (LinearLayout) view.findViewById(R.id.pop_huodong_view_shibai);
		pop_huodong_shibai_content = (TextView) view.findViewById(R.id.pop_huodong_shibai_content);
		pop_huodong_view_shibai.setOnClickListener(this);
		pop_huodong_image.setOnClickListener(this);
		pop_huodong_close_shibai.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		if (!TextUtils.isEmpty(getIntent().getStringExtra("mykey"))) {
			openActivity(MainActivity.class);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			if (!TextUtils.isEmpty(getIntent().getStringExtra("mykey"))) {
				openActivity(MainActivity.class);
			} else {
				finish();
			}
			break;
		case R.id.top_tv_right:
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (App.getInstance().getIs_leader() == 1) {
					switch (leaderApplyState) {// 2：未申请过 1：审核通过 0：待审核 -1为拒绝
					case -1:
						pop_huodong_view_shibai.setVisibility(View.VISIBLE);
						popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
						break;
					case 2:
						showdialogtext("申请中...");
						sendLeaderJoinClubActionAsyn();
						break;
					}
				} else {
					if (issignup == 0) {
						sendJoinClubActionAsyn();
					} else if (issignup == 1) {
						showAlertDialog("是否取消关注该俱乐部?                   ");
					}
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
				isFrist = false;
			}
			break;
		case R.id.julebu_huodong_layout:
			showdialog();
			type_tap = 1;
			index = 0;
			count = 10;
			Loadmore = false;
			LoadmoreData = false;
			listadapter = null;
			julebu_huodong_view.setVisibility(View.VISIBLE);
			julebu_julebu_view.setVisibility(View.GONE);
			julebu_lingdui_view.setVisibility(View.GONE);
			julebu_chengyuan_view.setVisibility(View.GONE);
			sendGetClubInfoByIDActionAsyn();
			break;
		case R.id.julebu_julebu_layout:
			showdialog();
			type_tap = 2;
			index = 0;
			count = 10;
			Loadmore = false;
			LoadmoreData = false;
			listadapter = null;
			listadapter3 = null;
			julebu_huodong_view.setVisibility(View.GONE);
			julebu_julebu_view.setVisibility(View.VISIBLE);
			julebu_lingdui_view.setVisibility(View.GONE);
			julebu_chengyuan_view.setVisibility(View.GONE);
			mYoujiDatas = youjiBaseDao.queryAll();
			sendGetClubInfoByIDActionAsyn();
			break;
		case R.id.julebu_lingdui_layout:
			showdialog();
			type_tap = 3;
			index = 0;
			count = 10;
			Loadmore = false;
			LoadmoreData = false;
			listadapter = null;
			listadapter3 = null;
			julebu_huodong_view.setVisibility(View.GONE);
			julebu_julebu_view.setVisibility(View.GONE);
			julebu_lingdui_view.setVisibility(View.VISIBLE);
			julebu_chengyuan_view.setVisibility(View.GONE);
			sendGetClubInfoByIDActionAsyn();
			break;
		case R.id.julebu_chengyuan_layout:
			showdialog();
			type_tap = 4;
			index = 0;
			count = 10;
			Loadmore = false;
			LoadmoreData = false;
			listadapter = null;
			listadapter3 = null;
			julebu_huodong_view.setVisibility(View.GONE);
			julebu_julebu_view.setVisibility(View.GONE);
			julebu_lingdui_view.setVisibility(View.GONE);
			julebu_chengyuan_view.setVisibility(View.VISIBLE);
			sendGetClubInfoByIDActionAsyn();
			break;
		case R.id.pop_huodong_view_shibai:
			break;
		case R.id.pop_huodong_image:
			popContact.dismiss();
			break;
		case R.id.pop_huodong_close_shibai:
			popContact.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {
			showdialog();
			index = 0;
			Loadmore = true;
			LoadmoreData = true;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			sendGetClubInfoByIDActionAsyn();
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		initPopwindow();
		mContext = JuLeBuXiangQingActivity.this;
		clubActsByIDActivityList = new ArrayList<GetClubActsByIDActivitylistItem>();
		clubActsByIDActivityListTemp = new ArrayList<GetClubActsByIDActivitylistItem>();
		clubTvlsByIDTravellist = new ArrayList<GetClubTvlsByIDTravellistItem>();
		clubTvlsByIDTravellistTemp = new ArrayList<GetClubTvlsByIDTravellistItem>();
		clubLeadersByIDLeaderlist = new ArrayList<GetClubLeadersByIDLeaderlistItem>();
		clubLeadersByIDLeaderlistTemp = new ArrayList<GetClubLeadersByIDLeaderlistItem>();
		memberDetailsList = new ArrayList<GetClubMemdersByIDMemberDetailItem>();
		memberDetailsListTemp = new ArrayList<GetClubMemdersByIDMemberDetailItem>();
		top_tv_title.setText("俱乐部详情");
		club_id = getIntent().getExtras().getString("clubid");
		// 数据库操作,以及从数据库中取出不同类型的数据
		// 初始化从服务器取得的数据的容器
		mYoujiDatas = new ArrayList<YoujiXiangqing>();
		youjiXiangqing = new YoujiXiangqing();
		youjiBaseDao = new BaseDao<YoujiXiangqing>(youjiXiangqing, mContext);
		initControl();
	}

	@Override
	protected void onResload() {
		showdialog();
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		lv_home.setOnItemClickListener(this);
		julebu_huodong_layout.setOnClickListener(this);
		julebu_julebu_layout.setOnClickListener(this);
		julebu_chengyuan_layout.setOnClickListener(this);
		julebu_lingdui_layout.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		// 因为加了header，所以position从1开始
		switch (type_tap) {
		case 1:
			if (clubActsByIDActivityList.get(position - 1).getAct_state() == 3
					|| clubActsByIDActivityList.get(position - 1).getAct_state() == 4) {
				if (TextUtils.equals(clubActsByIDActivityList.get(position - 1).getAct_id(),
						App.getInstance().getHuoDongId())) {
					bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
				} else if (TextUtils.equals(clubActsByIDActivityList.get(position - 1).getAct_id(),
						App.getInstance().getLeaderHuoDongId())) {
					bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
				} else {
					bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
				}
			} else {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
			}
			bundle.putString("act_id", clubActsByIDActivityList.get(position - 1).getAct_id());
			openActivity(HuoDongXiangQingActivity.class, bundle);
			break;
		case 2:
			bundle.putInt("agreeCount", clubTvlsByIDTravellist.get(position - 1).getAgreeCount() != null
					? clubTvlsByIDTravellist.get(position - 1).getAgreeCount() : 0);
			bundle.putInt("isAgree", clubTvlsByIDTravellist.get(position - 1).getIsAgree() != null
					? clubTvlsByIDTravellist.get(position - 1).getIsAgree() : 0);
			if (mYoujiDatas != null) {
				for (int i = 0; i < mYoujiDatas.size(); i++) {
					if (TextUtils.equals(mYoujiDatas.get(i).getTvl_id(),
							clubTvlsByIDTravellist.get(position - 1).getTvl_id())) {
						isDownLoad = 1;
					}
				}
			}
			bundle.putInt("isDownLoad", isDownLoad);
			bundle.putString("tvl_id", clubTvlsByIDTravellist.get(position - 1).getTvl_id() != null
					? clubTvlsByIDTravellist.get(position - 1).getTvl_id() : "");
			bundle.putString("act_id", clubTvlsByIDTravellist.get(position - 1).getAct_id() != null
					? clubTvlsByIDTravellist.get(position - 1).getAct_id() : "");
			openActivity(YoujiXiangQingActivity.class, bundle);
			isDownLoad = 0;
			break;
		case 3:
			bundle.putString("Userid", clubLeadersByIDLeaderlist.get(position - 1).getUserid());
			openActivity(HaoYouDetailActivity.class, bundle);
			break;
		case 4:
			bundle.putString("Userid", memberDetailsList.get(position - 1).getUserid());
			openActivity(HaoYouDetailActivity.class, bundle);
			break;

		default:
			break;
		}
		isFrist = false;
	}

	private int type_tap = 1;

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		// 实例化视图控件
		views = new ArrayList<View>();
		// 头部
		headerLayout = LayoutInflater.from(mContext).inflate(R.layout.head_julebuxiangqing, null, true);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		headerLayout.setLayoutParams(params);
		viewPager = (MyViewPager) headerLayout.findViewById(R.id.julebu_mvp);
		viewPager.setLayoutParams(
				new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
		indicatorLayout = (LinearLayout) headerLayout.findViewById(R.id.indicator);
		julebu_icon = (ImageView) headerLayout.findViewById(R.id.julebu_icon);
		julebu_title = (TextView) headerLayout.findViewById(R.id.julebu_title);
		julebu_dizhi = (TextView) headerLayout.findViewById(R.id.julebu_dizhi);
		julebu_chuang = (TextView) headerLayout.findViewById(R.id.julebu_chuang);
		julebu_phone = (TextView) headerLayout.findViewById(R.id.julebu_phone);
		julebu_qq = (TextView) headerLayout.findViewById(R.id.julebu_qq);
		julebu_mail = (TextView) headerLayout.findViewById(R.id.julebu_mail);
		julebu_des = (TextView) headerLayout.findViewById(R.id.julebu_des);
		julebu_huodong = (TextView) headerLayout.findViewById(R.id.julebu_huodong);
		julebu_dengji = (TextView) headerLayout.findViewById(R.id.julebu_dengji);
		julebu_chengyuan = (TextView) headerLayout.findViewById(R.id.julebu_chengyuan);
		julebu_huodong_layout = (LinearLayout) headerLayout.findViewById(R.id.julebu_huodong_layout);
		julebu_julebu_layout = (LinearLayout) headerLayout.findViewById(R.id.julebu_julebu_layout);
		julebu_lingdui_layout = (LinearLayout) headerLayout.findViewById(R.id.julebu_lingdui_layout);
		julebu_chengyuan_layout = (LinearLayout) headerLayout.findViewById(R.id.julebu_chengyuan_layout);
		julebu_huodong_view = (View) headerLayout.findViewById(R.id.julebu_huodong_view);
		julebu_julebu_view = (View) headerLayout.findViewById(R.id.julebu_julebu_view);
		julebu_lingdui_view = (View) headerLayout.findViewById(R.id.julebu_lingdui_view);
		julebu_chengyuan_view = (View) headerLayout.findViewById(R.id.julebu_chengyuan_view);
		headerParent = new LinearLayout(mContext);
		headerParent.addView(headerLayout);
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
		lv_home.addHeaderView(headerParent);
	}

	private void refreshAndLoadMore() {
		// 上下刷新START--------------------------------------------------------------------
		// 获取装载VIew的容器
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_detailed_frameLayout);
		// 获取view的引用
		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(
				R.id.load_more_list_view_detailed_container);
		// 使用默认样式
		loadMoreListViewContainer.useDefaultHeader();
		// 加载更多数据，当列表滑动到最底部的时候，触发加载更多操作，
		// 这是需要从网络加载数据，或者是从数据库去读取数据
		// 给View 设置加载更多的Handler 去异步加载View需要显示的数据和VIew
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			// loadMoreListViewContainer调用onLoadMore传入loadMoreListViewContainer自身对象
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (NetUtil.isNetworkAvailable(mContext)) {// 有网
					isNet = true;
					count = 10;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					Loadmore = true;
					LoadmoreData = false;
					switch (type_tap) {
					case 1:
						sendGetClubActsByIDActionAsyn();
						break;
					case 2:
						sendGetClubTvlsByIDActionAsyn();
						break;
					case 3:
						sendGetClubLeadersByIDActionAsyn();
						break;
					case 4:
						sendGetClubMemdersByIDActionAsyn();
						break;
					default:
						break;
					}
					mHandler.sendEmptyMessage(CommonUtility.ISLOADMORE);
				} else {// 无网
					isNet = false;
					mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
				}
			}
		});
		// load至少刷新多少1秒
		mPtrFrameLayout.setLoadingMinTime(1000);
		// 容器设置异步线程检查是否可以下拉刷新，并且开始下拉刷新用户头
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_home, header);
			}

			// 开始刷新容器开头
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (NetUtil.isNetworkAvailable(mContext)) {// 有网
					try {// 刷新的时候把轮播停了
						handler.sendEmptyMessage(ImageHandler.MSG_BREAK_SILENT);
					} catch (Exception e) {
					}
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					sendGetClubInfoByIDActionAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					isNet = false;
					mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
				}
			}
		});
		// auto load data
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh(true);
				// mHandler.sendEmptyMessage(1);
			}
		}, 150);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 进行判断，是否存在数据
			// loadMoreListViewContainer.loadMoreFinish(mDataModel.getListPageInfo().isEmpty(),
			// mDataModel.getListPageInfo().hasMore());
			loadMoreListViewContainer.loadMoreFinish(false, true);
			switch (msg.what) {
			case CommonUtility.ISREFRESH:// 刷新
				mPtrFrameLayout.refreshComplete();
				break;
			case CommonUtility.ISLOADMORE:// 加载更多
				mPtrFrameLayout.refreshComplete();
				break;
			case CommonUtility.ISNETCONNECTEDInt:// 下拉刷新无网络时
				mPtrFrameLayout.refreshComplete();
				showToastNet();
				break;
			default:
				break;
			}
		}
	};
	// 刷新end------------------------------------------------------------------

	private Thread thread = null;

	// 获取俱乐部领队
	public void sendGetClubLeadersByIDActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetClubLeadersByIDAction();
			}
		};
		thread.start();
	}

	public void GetClubLeadersByIDAction() {
		GetClubLeadersByIDRequest request = new GetClubLeadersByIDRequest();
		GetClubLeadersByIDRequestParameter parameter = new GetClubLeadersByIDRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
		parameter.setCount(count);
		parameter.setIndex(index);
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
					GetClubLeadersByIDResponse response = new GetClubLeadersByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetClubLeadersByIDResponsePayload payload = (GetClubLeadersByIDResponsePayload) response
							.getPayload();
					clubLeadersByIDLeaderlistTemp = payload.getLeaderlistList();
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 获取俱乐部成员
	public void sendGetClubMemdersByIDActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetClubMemdersByIDAction();
			}
		};
		thread.start();
	}

	public void GetClubMemdersByIDAction() {
		GetClubMemdersByIDRequest request = new GetClubMemdersByIDRequest();
		GetClubMemdersByIDRequestParameter parameter = new GetClubMemdersByIDRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
		parameter.setCount(count);
		parameter.setIndex(index);
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
					GetClubMemdersByIDResponse response = new GetClubMemdersByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetClubMemdersByIDResponsePayload payload = (GetClubMemdersByIDResponsePayload) response
							.getPayload();
					memberDetailsListTemp = payload.getMemberDetailList();
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 获得俱乐部游记
	public void sendGetClubTvlsByIDActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetClubTvlsByIDAction();
			}
		};
		thread.start();
	}

	public void GetClubTvlsByIDAction() {
		GetClubTvlsByIDRequest request = new GetClubTvlsByIDRequest();
		GetClubTvlsByIDRequestParameter parameter = new GetClubTvlsByIDRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
		parameter.setCount(count);
		parameter.setIndex(index);
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
					GetClubTvlsByIDResponse response = new GetClubTvlsByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetClubTvlsByIDResponsePayload payload = (GetClubTvlsByIDResponsePayload) response.getPayload();
					clubTvlsByIDTravellistTemp = payload.getTravellistList();
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

	// 获取俱乐部活动
	public void sendGetClubActsByIDActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetClubActsByIDAction();
			}
		};
		thread.start();
	}

	public void GetClubActsByIDAction() {
		GetClubActsByIDRequest request = new GetClubActsByIDRequest();
		GetClubActsByIDRequestParameter parameter = new GetClubActsByIDRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
		parameter.setCount(count);
		parameter.setIndex(index);
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
					GetClubActsByIDResponse response = new GetClubActsByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetClubActsByIDResponsePayload payload = (GetClubActsByIDResponsePayload) response.getPayload();
					clubActsByIDActivityListTemp = payload.getActivitylistList();
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 俱乐部详情
	public void sendGetClubInfoByIDActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetClubInfoByIDAction();
			}
		};
		thread.start();
	}

	public void GetClubInfoByIDAction() {
		GetClubInfoByIDRequest request = new GetClubInfoByIDRequest();
		GetClubInfoByIDRequestParameter parameter = new GetClubInfoByIDRequestParameter();
		parameter.setClub_id(club_id);
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
					GetClubInfoByIDResponse response = new GetClubInfoByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetClubInfoByIDResponsePayload payload = (GetClubInfoByIDResponsePayload) response.getPayload();
					club_imges = payload.getAlbum(); // 俱乐部多个图片
					club_name = payload.getClub_name(); // 俱乐部名称
					club_city = payload.getClub_address(); // 俱乐部所在城市
					club_logo = payload.getClub_logo(); // 俱乐部头像
					club_desc = payload.getClub_desc();// 俱乐部简介
					club_chuang = payload.getCreate_time();// 俱乐部创建时间
					club_phone = payload.getClub_linkphone();// 俱乐部联系电话
					club_qq = payload.getClub_qq();// 俱乐部QQ
					club_mail = payload.getClub_email();// 俱乐部邮箱
					club_huodong = payload.getActivity() != null ? payload.getActivity() : 0; // 成员人数
					club_dengji = payload.getClub_level() != null ? payload.getClub_level() : 0; // 成员人数
					club_chengyuan = payload.getMember() != null ? payload.getMember() : 0; // 活动数量
					issignup = payload.getIssignup();// 是否报名，0为未加入，1为已加入
					leaderApplyState = payload.getLeaderApplyState();// 2：未申请过1：审核通过0：待审核-1为拒绝
					reason = payload.getReason();
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 退出俱乐部
	public void sendOutClubActionAsyn() {
		thread = new Thread() {
			public void run() {
				OutClubAction();
			}
		};
		thread.start();
	}

	public void OutClubAction() {
		OutClubRequest request = new OutClubRequest();
		OutClubRequestParameter parameter = new OutClubRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
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
					OutClubResponse response = new OutClubResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					OutClubResponsePayload payload = (OutClubResponsePayload) response.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK6);
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

	// 加入俱乐部
	public void sendJoinClubActionAsyn() {
		thread = new Thread() {
			public void run() {
				JoinClubAction();
			}
		};
		thread.start();
	}

	public void JoinClubAction() {
		JoinClubRequest request = new JoinClubRequest();
		JoinClubRequestParameter parameter = new JoinClubRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
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
					JoinClubResponse response = new JoinClubResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					JoinClubResponsePayload payload = (JoinClubResponsePayload) response.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK7);
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

	// 领队申请俱乐部
	public void sendLeaderJoinClubActionAsyn() {
		thread = new Thread() {
			public void run() {
				LeaderJoinClubAction();
			}
		};
		thread.start();
	}

	private String leadermsgerror = "";

	public void LeaderJoinClubAction() {
		LeaderJoinClubRequest request = new LeaderJoinClubRequest();
		LeaderJoinClubRequestParameter parameter = new LeaderJoinClubRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
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
				leadermsgerror = responseObject.getString("msg");
				if (ret == 0) {
					LeaderJoinClubResponse response = new LeaderJoinClubResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					LeaderJoinClubResponsePayload payload = (LeaderJoinClubResponsePayload) response.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK10);
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

	// 点赞
	public void sendAgreeTravelActionAsyn() {
		thread = new Thread() {
			public void run() {
				AgreeTravelAction();
			}
		};
		thread.start();
	}

	public void AgreeTravelAction() {
		AgreeTravelRequest request = new AgreeTravelRequest();
		AgreeTravelRequestParameter parameter = new AgreeTravelRequestParameter();
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
					AgreeTravelResponse response = new AgreeTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					AgreeTravelResponsePayload payload = (AgreeTravelResponsePayload) response.getPayload();
					state = payload.getState();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK8);
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

	// 关注好友
	public void sendAsyn4() {
		thread = new Thread() {
			public void run() {
				Action4();
			}
		};
		thread.start();
	}

	public void Action4() {
		InterestUserRequest request = new InterestUserRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		InterestUserRequestParameter parameter = new InterestUserRequestParameter();
		request.setParameter(parameter);
		parameter.setU_id(Userid);
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
					InterestUserResponse response = new InterestUserResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					InterestUserResponsePayload payload = (InterestUserResponsePayload) response.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK9);
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

	CommonAdapter<NoDataBean> listadapter3 = null;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (App.getInstance().getIs_leader() == 1) {
					switch (leaderApplyState) {// 2：未申请过 1：审核通过 0：待审核 -1为拒绝
					case -1:
						top_tv_right.setText("查看原因");
						pop_huodong_shibai_content.setText(reason);
						break;
					case 0:
						top_tv_right.setText("审核中");
						break;
					case 1:
						top_tv_right.setText("已加入");
						break;
					case 2:
						top_tv_right.setText("申请");
						break;

					default:
						break;
					}
				} else {
					if (issignup == 0) {
						top_tv_right.setText("关注");
					} else if (issignup == 1) {
						top_tv_right.setText("取消关注");
					}
				}
				if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
					mImageLoader.displayImage(club_logo, julebu_icon, options);
				} else {
					mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, julebu_icon, options);
				}
				julebu_title.setText(club_name);
				julebu_dizhi.setText(club_city);
				julebu_chuang.setText(club_chuang);
				julebu_phone.setText(club_phone);
				julebu_qq.setText(club_qq);
				julebu_mail.setText(club_mail);
				julebu_des.setText(club_desc);
				julebu_huodong.setText(club_huodong + "");
				julebu_dengji.setText(club_dengji + "");
				julebu_chengyuan.setText(club_chengyuan + "");
				initTopImage();
				switch (type_tap) {
				case 1:
					sendGetClubActsByIDActionAsyn();
					break;
				case 2:
					mYoujiDatas = youjiBaseDao.queryAll();
					sendGetClubTvlsByIDActionAsyn();
					break;
				case 3:
					sendGetClubLeadersByIDActionAsyn();
					break;
				case 4:
					sendGetClubMemdersByIDActionAsyn();
					break;

				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK2:
				if (Loadmore) {
					if (LoadmoreData) {
						memberDetailsList.clear();
					}
				} else {
					memberDetailsList.clear();
				}
				memberDetailsList.addAll(memberDetailsListTemp);
				clubActsByIDActivityList.clear();
				clubTvlsByIDTravellist.clear();
				clubLeadersByIDLeaderlist.clear();
				listStat = 4;
				if (memberDetailsList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.members));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(mContext, nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new MyListAdpter(mContext, clubActsByIDActivityList, clubTvlsByIDTravellist,
								clubLeadersByIDLeaderlist, memberDetailsList, listStat);
						lv_home.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK3:
				if (Loadmore) {
					if (LoadmoreData) {
						clubActsByIDActivityList.clear();
					}
				} else {
					clubActsByIDActivityList.clear();
				}
				clubActsByIDActivityList.addAll(clubActsByIDActivityListTemp);
				clubTvlsByIDTravellist.clear();
				clubLeadersByIDLeaderlist.clear();
				memberDetailsList.clear();
				listStat = 1;
				if (clubActsByIDActivityList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.home_page));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(mContext, nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new MyListAdpter(mContext, clubActsByIDActivityList, clubTvlsByIDTravellist,
								clubLeadersByIDLeaderlist, memberDetailsList, listStat);
						lv_home.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK4:
				if (Loadmore) {
					if (LoadmoreData) {
						clubTvlsByIDTravellist.clear();
					}
				} else {
					clubTvlsByIDTravellist.clear();
				}
				clubTvlsByIDTravellist.addAll(clubTvlsByIDTravellistTemp);
				clubActsByIDActivityList.clear();
				clubLeadersByIDLeaderlist.clear();
				memberDetailsList.clear();
				listStat = 2;
				if (clubTvlsByIDTravellist.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.travel));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(mContext, nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new MyListAdpter(mContext, clubActsByIDActivityList, clubTvlsByIDTravellist,
								clubLeadersByIDLeaderlist, memberDetailsList, listStat);
						lv_home.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK5:
				if (Loadmore) {
					if (LoadmoreData) {
						clubLeadersByIDLeaderlist.clear();
					}
				} else {
					clubLeadersByIDLeaderlist.clear();
				}
				clubLeadersByIDLeaderlist.addAll(clubLeadersByIDLeaderlistTemp);
				clubActsByIDActivityList.clear();
				clubTvlsByIDTravellist.clear();
				memberDetailsList.clear();
				listStat = 3;
				if (clubLeadersByIDLeaderlist.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.lingdui));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(mContext, nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new MyListAdpter(mContext, clubActsByIDActivityList, clubTvlsByIDTravellist,
								clubLeadersByIDLeaderlist, memberDetailsList, listStat);
						lv_home.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK6:
				if (isshowdialog()) {
					closedialog();
				}
				finish();
				break;
			case CommonUtility.SERVEROK7:
				index = 0;
				count = 10;
				Loadmore = true;
				LoadmoreData = true;
				sendGetClubInfoByIDActionAsyn();
				break;
			case CommonUtility.SERVEROK10:
				sendGetClubInfoByIDActionAsyn();
				break;
			case CommonUtility.SERVEROK8:
				switch (state) {
				case 0:
					showToast("点赞失败,请重新点赞");
					break;
				case 1:
					index = 0;
					if (listadapter != null) {
						count = listadapter.getCount();
					} else {
						count = 10;
					}
					Loadmore = true;
					LoadmoreData = true;
					sendGetClubTvlsByIDActionAsyn();
					break;
				case 2:
					showToast("你已经点过赞了");
					break;

				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK9:
				showToast("关注好友成功");
				index = 0;
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				if (type_tap == 3) {
					sendGetClubLeadersByIDActionAsyn();
				} else if (type_tap == 4) {
					sendGetClubMemdersByIDActionAsyn();
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				if (isshowdialog()) {
					closedialog();
				}
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				isFrist = false;
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				if (!TextUtils.isEmpty(leadermsgerror)) {
					showToast(leadermsgerror);
				}
				leadermsgerror = null;
				if (isshowdialog()) {
					closedialog();
				}
				break;
			default:
				break;
			}
		};
	};

	public void initTopImage() {
		// 针对viwpager中的图片，每次刷新需清空这三项
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(club_imges);
		} catch (JSONException e) {
		}
		if (jsonArray != null) {
			if (views != null || indicatorLayout != null) {
				views.clear();
				indicatorLayout.removeAllViews();
			}
			indicators = new ImageView[jsonArray.length()]; // 定义指示器数组大小
			for (int i = 0; i < jsonArray.length(); i++) {
				// 循环加入图片
				if (mContext == null) {
					return;
				} else {
					ImageView imageView = new ImageView(mContext);
					try {
						if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
							mImageLoader.displayImage(jsonArray.getString(i), imageView, options);
						} else {
							mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, imageView, options);
						}
					} catch (JSONException e) {
					}
					views.add(imageView);
					// 循环加入指示器
					indicators[i] = new ImageView(mContext);
					if (i == 0) {
						indicators[i].setBackgroundResource(R.drawable.point_n);
					} else {
						indicators[i].setBackgroundResource(R.drawable.point_s);
					}
					indicatorLayout.addView(indicators[i]);
				}
			}
			pagerAdapter = new BasePagerAdapter(views);
			// 设置适配器
			viewPager.setAdapter(pagerAdapter);
			viewPager.setOnPageChangeListener(this);
			// 可左右滑动,这行会让图片显示变慢
			// viewPager.setCurrentItem(views.size() * 6);
			// 开始轮播效果
			handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
		}
	}

	// 配合Adapter的currentItem字段进行设置。
	@Override
	public void onPageSelected(int arg0) {
		handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	// 覆写该方法实现轮播效果的暂停和恢复
	@Override
	public void onPageScrollStateChanged(int arg0) {
		switch (arg0) {
		case ViewPager.SCROLL_STATE_DRAGGING:
			handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
			break;
		case ViewPager.SCROLL_STATE_IDLE:
			handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
			break;
		default:
			break;
		}
	}

	// 引导页使用的pageview适配器
	public class BasePagerAdapter extends PagerAdapter {
		private List<View> views = new ArrayList<View>();

		public BasePagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return views.size() <= 1 ? views.size() : Integer.MAX_VALUE;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// ((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// ((ViewPager) container).addView(views.get(position));
			// return views.get(position);
			// 对ViewPager页号求模取出View列表中要显示的项
			position %= views.size();
			if (position < 0) {
				position = views.size() + position;
			}
			ImageView view = (ImageView) views.get(position);
			view.setScaleType(ScaleType.CENTER_CROP);
			// 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
			ViewParent vp = view.getParent();
			if (vp != null) {
				ViewGroup parent = (ViewGroup) vp;
				parent.removeView(view);
			}
			((ViewGroup) container).addView(view);

			return view;
		}
	}

	private int downid = 0;

	private class ImageHandler extends Handler {

		/**
		 * 请求更新显示的View。
		 */
		protected static final int MSG_UPDATE_IMAGE = 1;
		/**
		 * 请求暂停轮播。
		 */
		protected static final int MSG_KEEP_SILENT = 2;
		/**
		 * 停止轮播
		 */
		protected static final int MSG_BREAK_SILENT = 3;
		/**
		 * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
		 * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
		 * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
		 */
		protected static final int MSG_PAGE_CHANGED = 4;

		// 轮播间隔时间
		protected static final long MSG_DELAY = 3000;

		// 使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
		private WeakReference<JuLeBuXiangQingActivity> weakReference;
		private int currentItem = 0;

		protected ImageHandler(WeakReference<JuLeBuXiangQingActivity> wk) {
			weakReference = wk;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			JuLeBuXiangQingActivity activity = weakReference.get();
			if (activity == null) {
				// Activity已经回收，无需再处理UI了
				return;
			}
			// 检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
			if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)) {
				activity.handler.removeMessages(MSG_UPDATE_IMAGE);
			}
			switch (msg.what) {
			case MSG_UPDATE_IMAGE:
				currentItem++;
				activity.viewPager.setCurrentItem(currentItem);
				// 准备下次播放
				activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
				break;
			case MSG_KEEP_SILENT:
				// 只要不发送消息就暂停了
				break;
			case MSG_BREAK_SILENT:
				currentItem = 0;
				break;
			case MSG_PAGE_CHANGED:
				// 记录当前的页号，避免播放的时候页面显示不正确。
				currentItem = msg.arg1;
				int currentItemTem = currentItem;
				currentItemTem %= indicators.length;
				if (currentItemTem < 0) {
					currentItemTem = indicators.length + currentItemTem;
				}
				downid = currentItemTem;
				for (int i = 0; i < indicators.length; i++) {
					indicators[currentItemTem].setBackgroundResource(R.drawable.point_n);
					if (currentItemTem != i) {
						indicators[i].setBackgroundResource(R.drawable.point_s);
					}
				}
				break;
			default:
				break;
			}
		}
	}

	// 俱乐部活动
	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private List<GetClubActsByIDActivitylistItem> mList1 = null;
		private List<GetClubTvlsByIDTravellistItem> mList2 = null;
		List<GetClubLeadersByIDLeaderlistItem> mList3 = null;
		List<GetClubMemdersByIDMemberDetailItem> mList4 = null;
		private int mStat = 0;

		public MyListAdpter(Context context, List<GetClubActsByIDActivitylistItem> List1,
				List<GetClubTvlsByIDTravellistItem> List2, List<GetClubLeadersByIDLeaderlistItem> List3,
				List<GetClubMemdersByIDMemberDetailItem> List4, int stat) {
			this.context = context;
			this.mList1 = List1;
			this.mList2 = List2;
			this.mList3 = List3;
			this.mList4 = List4;
			this.mStat = stat;
		}

		@Override
		public int getCount() {
			switch (mStat) {
			case 1:
				return mList1 != null ? mList1.size() : 0;
			case 2:
				return mList2 != null ? mList2.size() : 0;
			case 3:
				return mList3 != null ? mList3.size() : 0;
			case 4:
				return mList4 != null ? mList4.size() : 0;
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			switch (mStat) {
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 3;
			case 4:
				return 4;
			}
			return 0;
		}

		@Override
		public int getViewTypeCount() {
			return 5;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				switch (mStat) {
				case 1:
					convertView = LayoutInflater.from(context).inflate(R.layout.item_homelist, null);
					holder = new ViewHolder();
					holder.home_view = (RelativeLayout) convertView.findViewById(R.id.home_view);
					holder.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
					holder.icon = (CubeImageView) convertView.findViewById(R.id.icon);
					holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
					holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
					holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
					holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
					holder.tv_action = (TextView) convertView.findViewById(R.id.tv_action);
					holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
					holder.tv_groupin = (TextView) convertView.findViewById(R.id.tv_groupin);
					break;
				case 2:
					convertView = LayoutInflater.from(context).inflate(R.layout.item_youjilist, null);
					holder = new ViewHolder();
					holder.youji_list = (FrameLayout) convertView.findViewById(R.id.youji_list);
					holder.im_youji_beijing = (CubeImageView) convertView.findViewById(R.id.im_youji_beijing);
					holder.tv_youji_huodong = (TextView) convertView.findViewById(R.id.tv_youji_huodong);
					holder.tv_youji_geyan = (TextView) convertView.findViewById(R.id.tv_youji_geyan);
					holder.tv_youji_time = (TextView) convertView.findViewById(R.id.tv_youji_time);
					holder.tv_youji_dianzan = (TextView) convertView.findViewById(R.id.tv_youji_dianzan);
					holder.tv_youji_julebu = (TextView) convertView.findViewById(R.id.tv_youji_julebu);
					break;
				case 3:
					convertView = LayoutInflater.from(context).inflate(R.layout.item_haoyou, null);
					holder = new ViewHolder();
					holder.haoyou_icon = (CubeImageView) convertView.findViewById(R.id.haoyou_icon);
					holder.haoyou_name = (TextView) convertView.findViewById(R.id.haoyou_name);
					holder.haoyou_fensi = (TextView) convertView.findViewById(R.id.haoyou_fensi);
					holder.haoyou_tianjia = (TextView) convertView.findViewById(R.id.haoyou_tianjia);
					break;
				case 4:
					convertView = LayoutInflater.from(context).inflate(R.layout.item_haoyou, null);
					holder = new ViewHolder();
					holder.haoyou_icon = (CubeImageView) convertView.findViewById(R.id.haoyou_icon);
					holder.haoyou_name = (TextView) convertView.findViewById(R.id.haoyou_name);
					holder.haoyou_fensi = (TextView) convertView.findViewById(R.id.haoyou_fensi);
					holder.haoyou_tianjia = (TextView) convertView.findViewById(R.id.haoyou_tianjia);
					break;

				default:
					break;
				}
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			switch (mStat) {
			case 1:
				if (mList1 != null && mList1.size() != 0) {
					holder.home_view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							App.getInstance().getScreenHeight() / 3));
					if (mList1.get(position).getIssignup() == 1) {
						holder.tv_groupin.setVisibility(View.VISIBLE);
					} else {
						holder.tv_groupin.setVisibility(View.GONE);
					}
					try {
						JSONArray jsonArray = new JSONArray(mList1.get(position).getAct_logo());
						if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
							holder.icon.loadImage(imageLoaderRectF, jsonArray.getString(0));
						} else {
							mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, holder.icon, options);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					holder.tv_title.setText(mList1.get(position).getAct_title());
					holder.tv_time.setText(
							TimeDateUtils.formatDateFromDatabaseTime(mList1.get(position).getAct_start_time()));
					if (!TextUtils.isEmpty(mList1.get(position).getClub())) {
						holder.tv_des.setText(mList1.get(position).getClub());
						mImageLoader.displayImage(mList1.get(position).getClub_head(), holder.iv_icon, options);
					} else if (!TextUtils.isEmpty(mList1.get(position).getLeader())) {
						holder.tv_des.setText(mList1.get(position).getLeader());
						mImageLoader.displayImage(mList1.get(position).getLeader_head(), holder.iv_icon, options);
					}
					holder.tv_content.setText(mList1.get(position).getAct_desc_intro());
					holder.tv_action.setText(mList1.get(position).getAct_type());
					if (TextUtils.equals(mList1.get(position).getAct_type(), "登山")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.dengshan));
					} else if (TextUtils.equals(mList1.get(position).getAct_type(), "徒步")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.tubu));
					} else if (TextUtils.equals(mList1.get(position).getAct_type(), "骑行")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.qixing));
					} else if (TextUtils.equals(mList1.get(position).getAct_type(), "自驾")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.zijia));
					} else if (TextUtils.equals(mList1.get(position).getAct_type(), "摄影")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.sheying));
					} else if (TextUtils.equals(mList1.get(position).getAct_type(), "休闲")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.xiuxian));
					} else if (TextUtils.equals(mList1.get(position).getAct_type(), "露营")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.luying));
					} else if (TextUtils.equals(mList1.get(position).getAct_type(), "亲子")) {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.qinzi));
					} else {
						holder.tv_action.setTextColor(context.getResources().getColor(R.color.content));
					}
					switch (mList1.get(position).getAct_state()) {
					case CommonUtility.BAOMINGZHONGInt:
						holder.tv_status.setText(CommonUtility.BAOMINGZHONGStr);
						holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
						break;
					case CommonUtility.ZHENGDUIInt:
						holder.tv_status.setText(CommonUtility.ZHENGDUIStr);
						holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
						break;
					case CommonUtility.ZHUNBEIInt:
						holder.tv_status.setText(CommonUtility.ZHUNBEIStr);
						holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
						break;
					case CommonUtility.CHUXINGInt:
						holder.tv_status.setText(CommonUtility.CHUXINGStr);
						holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
						break;
					case CommonUtility.DIANPINGInt:
						holder.tv_status.setText(CommonUtility.DIANPINGStr);
						holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
						break;
					case CommonUtility.JIESHUInt:
						holder.tv_status.setText(CommonUtility.JIESHUStr);
						holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
						break;
					}
				}
				break;
			case 2:
				if (mList2 != null && mList2.size() != 0) {
					if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
						holder.im_youji_beijing.loadImage(imageLoader, mList2.get(position).getTvl_cover());
					} else {
						mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, holder.im_youji_beijing,
								options);
					}
					holder.youji_list.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
							App.getInstance().getScreenHeight() / 3));
					holder.tv_youji_huodong.setText(mList2.get(position).getUser_nickname());
					holder.tv_youji_geyan.setText(mList2.get(position).getTvl_title());
					holder.tv_youji_time.setText(
							TimeDateUtils.formatDateFromDatabaseTime(mList2.get(position).getAct_start_time()));
					holder.tv_youji_dianzan.setText(mList2.get(position).getAgreeCount() + "");
					holder.tv_youji_julebu.setVisibility(View.GONE);
					if (mList2.get(position).getIsAgree() == 0) {
						holder.tv_youji_dianzan.setTextColor(getResources().getColor(R.color.white));
						holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_n, 0, 0, 0);
					} else if (mList2.get(position).getIsAgree() == 1) {
						holder.tv_youji_dianzan.setTextColor(getResources().getColor(R.color.app_green));
						holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_c, 0, 0, 0);
					}
					holder.tv_youji_dianzan.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							tvl_id = mList2.get(position).getTvl_id();
							if (mList2.get(position).getIsAgree() == 0) {
								if (!TextUtils.isEmpty(App.getInstance().getAut())) {
									sendAgreeTravelActionAsyn();
								} else {
									showToastDengLu();
									openActivity(LoginActivity.class);
								}
							}
						}
					});
				}
				break;
			case 3:
				if (mList3 != null && mList3.size() != 0) {
					if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
						holder.haoyou_icon.loadImage(imageLoader, mList3.get(position).getHead());
					} else {
						mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, holder.haoyou_icon, options);
					}
					holder.haoyou_name.setText(mList3.get(position).getRealname());
					holder.haoyou_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.haoyou_lingdui, 0);
					holder.haoyou_fensi.setText("" + mList3.get(position).getFanscount());
					if (!TextUtils.equals(mList3.get(position).getUserid(), App.getInstance().getUserid())) {
						if (mList3.get(position).getIs_attention() == 0) {
							holder.haoyou_tianjia.setText("关注");
							holder.haoyou_tianjia.setTextColor(getResources().getColor(R.color.content));
							holder.haoyou_tianjia.setBackgroundResource(R.drawable.shape_tianjia);
							holder.haoyou_tianjia.setEnabled(true);
						} else if (mList3.get(position).getIs_attention() == 1) {
							holder.haoyou_tianjia.setText("已关注");
							holder.haoyou_tianjia.setTextColor(getResources().getColor(R.color.white));
							holder.haoyou_tianjia.setBackgroundResource(R.drawable.shape_yitianjia);
							holder.haoyou_tianjia.setEnabled(false);
						}
						holder.haoyou_tianjia.setVisibility(View.VISIBLE);
					} else {
						holder.haoyou_tianjia.setVisibility(View.GONE);
					}
					holder.haoyou_tianjia.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (!TextUtils.isEmpty(App.getInstance().getAut())) {
								Userid = mList3.get(position).getUserid();
								sendAsyn4();
							} else {
								showToastDengLu();
								openActivity(LoginActivity.class);
							}
						}
					});
				}
				break;
			case 4:
				if (mList4 != null && mList4.size() != 0) {
					if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
						holder.haoyou_icon.loadImage(imageLoader, mList4.get(position).getHead());
					} else {
						mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, holder.haoyou_icon, options);
					}
					holder.haoyou_name.setText(mList4.get(position).getNickname());
					holder.haoyou_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.haoyou_duiyuan, 0);
					holder.haoyou_fensi.setText("" + mList4.get(position).getFanscount());
					holder.haoyou_tianjia.setVisibility(View.VISIBLE);
					if (!TextUtils.equals(mList4.get(position).getUserid(), App.getInstance().getUserid())) {
						if (mList4.get(position).getIs_attention() == 0) {
							holder.haoyou_tianjia.setText("关注");
							holder.haoyou_tianjia.setTextColor(getResources().getColor(R.color.content));
							holder.haoyou_tianjia.setBackgroundResource(R.drawable.shape_tianjia);
							holder.haoyou_tianjia.setEnabled(true);
						} else if (mList4.get(position).getIs_attention() == 1) {
							holder.haoyou_tianjia.setText("已关注");
							holder.haoyou_tianjia.setTextColor(getResources().getColor(R.color.white));
							holder.haoyou_tianjia.setBackgroundResource(R.drawable.shape_yitianjia);
							holder.haoyou_tianjia.setEnabled(false);
						}
					} else { // TODO
						if (App.getInstance().getIs_leader() == 1) {
							if (leaderApplyState == -1 || leaderApplyState == 2) {
								holder.haoyou_tianjia.setText("申请");
								holder.haoyou_tianjia.setTextColor(getResources().getColor(R.color.content));
								holder.haoyou_tianjia.setBackgroundResource(R.drawable.shape_tianjia);
								holder.haoyou_tianjia.setEnabled(true);
							} else {
								holder.haoyou_tianjia.setVisibility(View.GONE);
							}
						} else {
							holder.haoyou_tianjia.setVisibility(View.GONE);
						}
					}
					holder.haoyou_tianjia.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (!TextUtils.isEmpty(App.getInstance().getAut())) {
								if (!TextUtils.equals(mList4.get(position).getUserid(),
										App.getInstance().getUserid())) {
									Userid = mList4.get(position).getUserid();
									sendAsyn4();
								} else {
									showdialogtext("申请中...");
									sendLeaderJoinClubActionAsyn();
								}
							} else {
								showToastDengLu();
								openActivity(LoginActivity.class);
							}
						}
					});
				}
				break;

			default:
				break;
			}
			return convertView;
		}

		private class ViewHolder {
			private FrameLayout youji_list;
			private RelativeLayout home_view = null;
			private CircleImageView iv_icon;
			private CubeImageView icon;
			private TextView tv_groupin, tv_title, tv_content, tv_des, tv_time, tv_action, tv_status;
			private CubeImageView im_youji_beijing;
			private TextView tv_youji_huodong;
			private TextView tv_youji_geyan;
			private TextView tv_youji_time;
			private TextView tv_youji_dianzan;
			private TextView tv_youji_julebu;
			private CubeImageView haoyou_icon;
			private TextView haoyou_name, haoyou_fensi, haoyou_tianjia;
		}
	}
}
