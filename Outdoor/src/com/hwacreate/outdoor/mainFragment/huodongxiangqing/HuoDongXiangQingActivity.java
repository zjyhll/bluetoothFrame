package com.hwacreate.outdoor.mainFragment.huodongxiangqing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwacreate.outdoor.MainActivity;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.HuoDongDuiYuan;
import com.hwacreate.outdoor.bean.HuoDongImages;
import com.hwacreate.outdoor.bean.HuoDongXiangQingDongTai;
import com.hwacreate.outdoor.bean.HuoDongXiangQingGongNue;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.leftFragment.gongju.ShouDuiDianPingActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.wode.GenRenZiLiaoActivity;
import com.hwacreate.outdoor.mainFragment.wode.HaoYouDetailActivity;
import com.hwacreate.outdoor.mainFragment.wode.XinJianYouJizjyActivity;
import com.hwacreate.outdoor.mainFragment.zhuzhi.JuLeBuXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.GpsInfo;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DataCleanManager;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CustomDialog;
import com.hwacreate.outdoor.view.MyListView;
import com.hwacreate.outdoor.view.MyViewPager;
import com.hwacreate.outdoor.view.PhotoView;
import com.keyhua.outdoor.protocol.CancelCollectionActivity.CancelCollectionActivityRequest;
import com.keyhua.outdoor.protocol.CancelCollectionActivity.CancelCollectionActivityRequestParameter;
import com.keyhua.outdoor.protocol.CancelCollectionActivity.CancelCollectionActivityResponse;
import com.keyhua.outdoor.protocol.CancelCollectionActivity.CancelCollectionActivityResponsePayload;
import com.keyhua.outdoor.protocol.CancelEnrollActivity.CancelEnrollActivityRequest;
import com.keyhua.outdoor.protocol.CancelEnrollActivity.CancelEnrollActivityRequestParameter;
import com.keyhua.outdoor.protocol.CancelEnrollActivity.CancelEnrollActivityResponse;
import com.keyhua.outdoor.protocol.CancelEnrollActivity.CancelEnrollActivityResponsePayload;
import com.keyhua.outdoor.protocol.CollectionActivity.CollectionActivityRequest;
import com.keyhua.outdoor.protocol.CollectionActivity.CollectionActivityRequestParameter;
import com.keyhua.outdoor.protocol.CollectionActivity.CollectionActivityResponse;
import com.keyhua.outdoor.protocol.CollectionActivity.CollectionActivityResponsePayload;
import com.keyhua.outdoor.protocol.DeleteMyTroopActivityAction.DeleteMyTroopActivityRequest;
import com.keyhua.outdoor.protocol.DeleteMyTroopActivityAction.DeleteMyTroopActivityRequestParameter;
import com.keyhua.outdoor.protocol.DeleteMyTroopActivityAction.DeleteMyTroopActivityResponse;
import com.keyhua.outdoor.protocol.DeleteMyTroopActivityAction.DeleteMyTroopActivityResponsePayload;
import com.keyhua.outdoor.protocol.EnrollActivity.EnrollActivityRequest;
import com.keyhua.outdoor.protocol.EnrollActivity.EnrollActivityRequestParameter;
import com.keyhua.outdoor.protocol.EnrollActivity.EnrollActivityResponse;
import com.keyhua.outdoor.protocol.EnrollActivity.EnrollActivityResponsePayload;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListHistorylistItem;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListRequest;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListRequestParameter;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListResponse;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListResponsePayload;
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
import com.keyhua.outdoor.protocol.LeaderDeleteActivityAction.LeaderDeleteActivityRequest;
import com.keyhua.outdoor.protocol.LeaderDeleteActivityAction.LeaderDeleteActivityRequestParameter;
import com.keyhua.outdoor.protocol.LeaderDeleteActivityAction.LeaderDeleteActivityResponse;
import com.keyhua.outdoor.protocol.LeaderDeleteActivityAction.LeaderDeleteActivityResponsePayload;
import com.keyhua.outdoor.protocol.SetActivitystateAction.SetActivitystateRequest;
import com.keyhua.outdoor.protocol.SetActivitystateAction.SetActivitystateRequestParameter;
import com.keyhua.outdoor.protocol.SetActivitystateAction.SetActivitystateResponse;
import com.keyhua.outdoor.protocol.SetActivitystateAction.SetActivitystateResponsePayload;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionRequest;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionRequestParameter;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionResponse;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionResponsePayload;
import com.keyhua.outdoor.protocol.UploadTraceDataAction.UploadTraceDataDatalistItem;
import com.keyhua.outdoor.protocol.UploadTraceDataAction.UploadTraceDataRequest;
import com.keyhua.outdoor.protocol.UploadTraceDataAction.UploadTraceDataRequestParameter;
import com.keyhua.outdoor.protocol.UploadTraceDataAction.UploadTraceDataResponse;
import com.keyhua.outdoor.protocol.UploadTraceDataAction.UploadTraceDataResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HuoDongXiangQingActivity extends BaseActivity {
	// pop sos按钮弹出
	private View parentView = null;
	private PopupWindow popContact = null;
	private LinearLayout pop_huodong_view = null;
	private RelativeLayout pop_huodong_image = null;// 半透明背景色
	private ImageView pop_huodong_close = null;// 取消
	private ImageView pop_huodong_close_shibai = null;// 取消
	private TextView pop_huodong_ok = null;// 选择
	private ImageView pop_huodong_jian = null;// 减号
	private TextView pop_huodong_geshu = null;// 显示个数
	private ImageView pop_huodong_jia = null;// 加号
	private ImageView pop_huodong_check1 = null;//
	private ImageView pop_huodong_check2 = null;//
	private ImageView pop_huodong_check3 = null;//
	private ImageView pop_huodong_check4 = null;//
	private TextView pop_huodong_check1_text = null;//
	private TextView pop_huodong_check2_text = null;//
	private TextView pop_huodong_check3_text = null;//
	private TextView pop_huodong_check4_text = null;//
	private FrameLayout pop_huodong_check1_view = null;//
	private FrameLayout pop_huodong_check2_view = null;//
	private FrameLayout pop_huodong_check3_view = null;//
	private FrameLayout pop_huodong_check4_view = null;//
	private LinearLayout pop_huodong_view_shibai = null;
	private TextView pop_huodong_shibai_content = null;
	private int renshu = 1;
	private int zhuangbei = 3;
	private int jingyan = 3;
	private int showBotton = 0;// 显示底部信息
	private MyListView lv = null;// 展示详细信息
	private MyListAdpter myListAdpter = null;
	// 浮动的textview
	private TextView type = null;
	private LinearLayout type_view = null;
	// 数据库操作2 // 将选择活动存入数据库
	private Context mContext = null;
	private HuoDongXiangQingItem mDataItem = null;
	private List<HuoDongXiangQingItem> mDatas = null;
	// 选取任务(队员)
	private HuoDongXiangQingItem huoDongXiangQing = null;
	private List<HuoDongXiangQingItem> huoDongXiangQingDuiyuanList = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingDuiyuanDao = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private List<HuoDongXiangQingLeader> huoDongXiangQingLeaderList = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;
	// header
	private View header_Layout = null;
	private LinearLayout header_Parent = null;
	// 顶部的图片
	private List<HuoDongImages> topImageDatas = null;
	private HuoDongImages topImage = null;
	private List<HuoDongImages> topImageDatas_ViewPager = null;
	private HuoDongImages topImage_ViewPager = null;
	private int downid_viewpager = 0;
	// 点击浏览大图
	private View huodong_viewpager_view = null;
	private MyViewPager huodong_viewpager = null;
	private LinearLayout huodong_indicator = null;
	private AdapterCycle_huo adapterCycle_huo = null;
	// 活动动态
	private List<HuoDongXiangQingDongTai> HuoDongXiangQingDongTaiBeans = null;
	private HuoDongXiangQingDongTai HuoDongXiangQingDongTaiBean = null;
	// 活动安排
	private List<HuoDongXiangQingGongNue> HuoDongXiangQingItemBeans = null;
	private HuoDongXiangQingGongNue HuoDongXiangQingItemBean = null;
	private String schedu_title = null;
	private String schedu_time = null;
	private String schedu_desc = null;
	private String imgs = null;
	// 队员列表
	private List<HuoDongDuiYuan> HuoDongDuiYuanBeans = null;
	private HuoDongDuiYuan HuoDongDuiYuanBean = null;
	// 临时保存图片
	private List<HuoDongImages> HuoDongImagesBeans = null;
	private HuoDongImages HuoDongImages = null;
	// json
	private Gson gson = null;
	// 网络true = 有网
	private boolean isFirst = true;
	private View view = null;

	private void initPopwindow() {
		try {
			view = getLayoutInflater().inflate(R.layout.pop_huodongxiangqing, null);
			popContact = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
			popContact.setBackgroundDrawable(new BitmapDrawable());
			pop_huodong_view = (LinearLayout) view.findViewById(R.id.pop_huodong_view);
			pop_huodong_image = (RelativeLayout) view.findViewById(R.id.pop_huodong_image);
			pop_huodong_close = (ImageView) view.findViewById(R.id.pop_huodong_close);
			pop_huodong_close_shibai = (ImageView) view.findViewById(R.id.pop_huodong_close_shibai);
			pop_huodong_ok = (TextView) view.findViewById(R.id.pop_huodong_ok);
			pop_huodong_jian = (ImageView) view.findViewById(R.id.pop_huodong_jian);
			pop_huodong_geshu = (TextView) view.findViewById(R.id.pop_huodong_geshu);
			pop_huodong_jia = (ImageView) view.findViewById(R.id.pop_huodong_jia);
			pop_huodong_check1 = (ImageView) view.findViewById(R.id.pop_huodong_check1);
			pop_huodong_check2 = (ImageView) view.findViewById(R.id.pop_huodong_check2);
			pop_huodong_check3 = (ImageView) view.findViewById(R.id.pop_huodong_check3);
			pop_huodong_check4 = (ImageView) view.findViewById(R.id.pop_huodong_check4);
			pop_huodong_check1_text = (TextView) view.findViewById(R.id.pop_huodong_check1_text);
			pop_huodong_check2_text = (TextView) view.findViewById(R.id.pop_huodong_check2_text);
			pop_huodong_check3_text = (TextView) view.findViewById(R.id.pop_huodong_check3_text);
			pop_huodong_check4_text = (TextView) view.findViewById(R.id.pop_huodong_check4_text);
			pop_huodong_check1_view = (FrameLayout) view.findViewById(R.id.pop_huodong_check1_view);
			pop_huodong_check2_view = (FrameLayout) view.findViewById(R.id.pop_huodong_check2_view);
			pop_huodong_check3_view = (FrameLayout) view.findViewById(R.id.pop_huodong_check3_view);
			pop_huodong_check4_view = (FrameLayout) view.findViewById(R.id.pop_huodong_check4_view);
			pop_huodong_view_shibai = (LinearLayout) view.findViewById(R.id.pop_huodong_view_shibai);
			pop_huodong_shibai_content = (TextView) view.findViewById(R.id.pop_huodong_shibai_content);
			pop_huodong_jian.setOnClickListener(this);
			pop_huodong_jia.setOnClickListener(this);
			pop_huodong_check1_view.setOnClickListener(this);
			pop_huodong_check2_view.setOnClickListener(this);
			pop_huodong_check3_view.setOnClickListener(this);
			pop_huodong_check4_view.setOnClickListener(this);
			pop_huodong_view.setOnClickListener(this);
			pop_huodong_view_shibai.setOnClickListener(this);
			pop_huodong_image.setOnClickListener(this);
			pop_huodong_close.setOnClickListener(this);
			pop_huodong_close_shibai.setOnClickListener(this);
			pop_huodong_ok.setOnClickListener(this);
		} catch (Exception e) {
		}
	}

	private TextView tv11 = null;// 活动名称
	private TextView tv12 = null;// 价格
	private TextView tv13 = null;// 活动类型
	private TextView tv14 = null;// 活动时间
	private CircleImageView tv15 = null;// 俱乐部头像
	private TextView tv16 = null;// 俱乐部名称
	private TextView tv17 = null;// 领队人
	private TextView tv18 = null;// 集合时间
	private TextView tv19 = null;// 集合地点
	private TextView tv20 = null;// 活动等级
	private TextView tv21 = null;// 活动路线
	private ImageView iv_map = null;// 地图

	private void initializeHeaderAndFooter() {
		lv = (MyListView) findViewById(R.id.lv);
		header_Layout = LayoutInflater.from(this).inflate(R.layout.head_huodongxiangqing, null, true);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		tv11 = (TextView) header_Layout.findViewById(R.id.tv11);
		tv12 = (TextView) header_Layout.findViewById(R.id.tv12);
		tv13 = (TextView) header_Layout.findViewById(R.id.tv13);
		tv14 = (TextView) header_Layout.findViewById(R.id.tv14);
		tv15 = (CircleImageView) header_Layout.findViewById(R.id.tv15);
		tv16 = (TextView) header_Layout.findViewById(R.id.tv16);
		tv17 = (TextView) header_Layout.findViewById(R.id.tv17);
		tv18 = (TextView) header_Layout.findViewById(R.id.tv18);
		tv19 = (TextView) header_Layout.findViewById(R.id.tv19);
		tv20 = (TextView) header_Layout.findViewById(R.id.tv20);
		tv21 = (TextView) header_Layout.findViewById(R.id.tv21);
		iv_map = (ImageView) header_Layout.findViewById(R.id.iv_map);
		iv_map.setLayoutParams(
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
		header_Layout.setLayoutParams(params);
		header_Parent = new LinearLayout(this);
		header_Parent.addView(header_Layout);
		lv.addHeaderView(header_Parent);
		// ViewPager
		huodong_viewpager_view = findViewById(R.id.huodong_viewpager_view);
		huodong_viewpager = (MyViewPager) findViewById(R.id.huodong_viewpager);
		huodong_indicator = (LinearLayout) findViewById(R.id.huodong_indicator);
		huodong_viewpager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
	}

	private void showViewPage(List<HuoDongImages> imageslist, String imagesjson, String images, int index) {
		showdialog();
		// 0HuoDongImages格式 1fullurljson格式 2json格式(有字段名) 3String 4json格式
		topImageDatas_ViewPager.clear();
		switch (index) {
		case 0:
			topImageDatas_ViewPager.addAll(imageslist);
			break;
		case 1:
			try {
				JSONArray json = new JSONArray(imagesjson);
				if (json != null) {
					for (int i = 0; i < json.length(); i++) {
						topImage_ViewPager = new HuoDongImages();
						if (NetUtil.isNetworkAvailable(mContext)) {
							topImage_ViewPager.setImage(json.getJSONObject(i).getString("fullurl"));
						} else {
							topImage_ViewPager.setImage(json.getJSONObject(i).getString("image"));
						}
						topImageDatas_ViewPager.add(topImage_ViewPager);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			topImage_ViewPager = new HuoDongImages();
			topImage_ViewPager.setImage(images);
			topImageDatas_ViewPager.add(topImage_ViewPager);
			break;
		case 4:
			try {
				JSONArray json = new JSONArray(imagesjson);
				if (json != null) {
					for (int i = 0; i < json.length(); i++) {
						topImage_ViewPager = new HuoDongImages();
						if (NetUtil.isNetworkAvailable(mContext)) {
							topImage_ViewPager.setImage(json.getString(i));
						} else {
							topImage_ViewPager.setImage(json.getJSONObject(i).getString("image"));
						}
						topImageDatas_ViewPager.add(topImage_ViewPager);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		huodong_viewpager_view.setVisibility(View.VISIBLE);
		adapterCycle_huo = new AdapterCycle_huo(mContext, huodong_viewpager, topImageDatas_ViewPager,
				huodong_indicator);
		huodong_viewpager.setAdapter(adapterCycle_huo);
		huodong_viewpager.setOnPageChangeListener(adapterCycle_huo);
		huodong_viewpager.setCurrentItem(downid_viewpager);
		if (isshowdialog()) {
			closedialog();
		}
	}

	public class AdapterCycle_huo extends PagerAdapter implements ViewPager.OnPageChangeListener {
		private ArrayList<View> mViews = null;
		private List<HuoDongImages> mList;
		private LinearLayout indicatorLayout = null;
		private ImageView[] indicators = null;
		private int currentItem = 0;

		public AdapterCycle_huo(Context context, MyViewPager viewPager, List<HuoDongImages> list, LinearLayout layout) {
			this.mList = list;
			this.indicatorLayout = layout;
			mViews = new ArrayList<View>();
			// 针对viwpager中的图片，每次刷新需清空这三项
			if (indicatorLayout != null) {
				indicatorLayout.removeAllViews();
			}
			if (mList != null) {
				indicators = new ImageView[mList.size()]; // 定义指示器数组大小
				for (int i = 0; i < mList.size(); i++) {
					PhotoView view = new PhotoView(HuoDongXiangQingActivity.this);
					if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
						if (NetUtil.isNetworkAvailable(mContext)) {
							mImageLoader.displayImage(mList.get(i).getImage(), view, options);
						} else {
							mImageLoader.displayImage("file://" + mList.get(i).getImage(), view, options);
						}
					} else {
						mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, view, options);
					}
					mViews.add(view);
					// 循环加入指示器
					indicators[i] = new ImageView(getApplicationContext());
					indicators[i].setBackgroundResource(R.drawable.point_s);
					if (i == 0) {
						indicators[i].setBackgroundResource(R.drawable.point_n);
					}
					indicatorLayout.addView(indicators[i]);
				}
			}
		}

		@Override
		public int getCount() {
			return mList.size() <= 1 ? mList.size() : Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			position %= mViews.size();
			if (position < 0) {
				position = mViews.size() + position;
			}
			PhotoView view = (PhotoView) mViews.get(position);
			view.enable();
			ViewParent vp = view.getParent();
			if (vp != null) {
				ViewGroup parent = (ViewGroup) vp;
				parent.removeView(view);
			}
			((ViewGroup) container).addView(view);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (huodong_viewpager_view.getVisibility() == View.VISIBLE) {
						huodong_viewpager_view.setVisibility(View.GONE);
					}
				}
			});
			return view;
		}

		// 实现ViewPager.OnPageChangeListener接口
		@Override
		public void onPageSelected(int position) {
			// 记录当前的页号，避免播放的时候页面显示不正确。
			currentItem = position;
			int currentItemTem = currentItem;
			currentItemTem %= indicators.length;
			if (currentItemTem < 0) {
				currentItemTem = indicators.length + currentItemTem;
			}
			for (int i = 0; i < indicators.length; i++) {
				indicators[currentItemTem].setBackgroundResource(R.drawable.point_n);
				if (currentItemTem != i) {
					indicators[i].setBackgroundResource(R.drawable.point_s);
				}
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = HuoDongXiangQingActivity.this;
		parentView = getLayoutInflater().inflate(R.layout.activity_huodongxiangqing, null);
		setContentView(parentView);
		init();
	}

	private boolean sc = true;
	private boolean bm = true;

	@Override
	public void onBackPressed() {
		if (huodong_viewpager_view.getVisibility() == View.VISIBLE) {
			huodong_viewpager_view.setVisibility(View.GONE);
		} else {
			App.getInstance().setGuiJi(false);
			App.getInstance().setDown(false);
			if (!TextUtils.isEmpty(getIntent().getStringExtra("mykey"))) {
				openActivity(MainActivity.class);
			} else {
				super.onBackPressed();
			}
		}
	}

	/** Dialog */
	public void showAlertDialog(Context context, String title, final int index) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				switch (index) {
				case 1:
					sendCancelCollectionActivityAsyn();
					break;
				case 2:
					sendCancelBaoMingActionAsyn();
					break;
				case 3:
					sendSetActivitystateActionAsyn();
					break;
				case 4:
					File appDir = new File(Environment.getExternalStorageDirectory(), "outdoorhuodong");
					if (appDir.exists()) {
						DataCleanManager.deleteFile(appDir);
					}
					if (huoDongXiangQingDuiyuanDao.queryAll() != null) {// 当数据库中数据不为空时删除数据
						huoDongXiangQingDuiyuanDao.deleteAll();
					}
					if (isshowdialog()) {
						closedialog();
					}
					App.getInstance().setHuoDongId("");
					finish();
					break;
				case 5:
					sendShanChuAsyn();
					break;
				case 6:
					sendSetActivitystateActionAsyn();
					break;
				case 7:
					showdialogtext("正在下载中...");
					if (getCustomProgressDialog() != null) {
						getCustomProgressDialog().setOnCancelListener(new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								showAlertDialog(mContext, "是否取消下载?                   ", 4);
							}
						});
					}
					DownDao();
					break;
				case 8: // 上传轨迹文件
					showdialogtext("正在上传中...");
					initGpsDao();
					break;
				case 9:
					sendShanChuDuiYuanAsyn();
					break;
				case 10:
					File appDir1 = new File(Environment.getExternalStorageDirectory(), "outdoorhuodongleader");
					if (appDir1.exists()) {
						DataCleanManager.deleteFile(appDir1);
					}
					if (huoDongXiangQingLeaderDao.queryAll() != null) {// 当数据库中数据不为空时删除数据
						huoDongXiangQingLeaderDao.deleteAll();
					}
					App.getInstance().setLeaderHuoDongId("");
					if (isshowdialog()) {
						closedialog();
					}
					finish();
					break;
				case 12:
					showdialogtext("正在下载中...");
					if (getCustomProgressDialog() != null) {
						getCustomProgressDialog().setOnCancelListener(new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								showAlertDialog(mContext, "是否取消下载?                   ", 10);
							}
						});
					}
					DownDaoLeader();
					break;
				default:
					break;
				}
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				switch (index) {
				case 8:
					deleteGpsDao();
					break;
				default:
					break;
				}
			}
		});
		builder.create().show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			App.getInstance().setGuiJi(false);
			App.getInstance().setDown(false);
			if (!TextUtils.isEmpty(getIntent().getStringExtra("mykey"))) {
				openActivity(MainActivity.class);
			} else {
				finish();
			}
			break;
		case R.id.huodong_select_one:// 没有操作
			break;
		case R.id.huodong_select_two:// 操作
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (showBotton == CommonUtility.XianShiTab_SheZhi) {
					Bundle bundle = new Bundle();
					switch (act_state) {
					case -1:// 审核失败弹出失败原因
						pop_huodong_view.setVisibility(View.GONE);
						pop_huodong_view_shibai.setVisibility(View.VISIBLE);
						popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
						break;
					case 0:
						// 审核中不做操作
						break;
					case 1:
						// showToast("查看名单");
						bundle.putString("act_id", act_id);
						openActivity(MingdanGuanLiActivity.class, bundle);
						isFirst = false;
						break;
					case 2:
						// 审核名单
						bundle.putString("act_id", act_id);
						bundle.putBoolean("showbtn", true);
						openActivity(MingdanGuanLiActivity.class, bundle);
						isFirst = false;
						break;
					case 3:
						// showToast("出行");
						showAlertDialog(mContext, "是否出行?                               ", 6);
						break;
					case 4: //
						// showToast("收队");
						showAlertDialog(mContext, "是否上传轨迹文件?                            ", 8);
						break;
					case 5:
						// showToast("点评活动");
						bundle.putInt("isLeader", 1);
						bundle.putString("act_id", act_id);
						bundle.putString("tv11", tv11.getText().toString());
						bundle.putString("tv12", tv12.getText().toString());
						bundle.putString("tv13", tv13.getText().toString());
						bundle.putString("tv14", tv14.getText().toString());
						bundle.putString("tv15", club_logo);
						bundle.putString("tv16", tv16.getText().toString());
						bundle.putString("tv17", tv17.getText().toString());
						bundle.putString("tv18", tv18.getText().toString());
						bundle.putString("tv19", tv19.getText().toString());
						bundle.putString("tv20", tv20.getText().toString());
						bundle.putString("tv21", tv21.getText().toString());
						openActivity(ShouDuiDianPingActivity.class, bundle);
						isFirst = false;
						break;
					case 6:
						AddYouji();
						break;

					default:
						break;
					}
				} else if (showBotton == CommonUtility.XianShiTab_RenWu) {
					huoDongXiangQingDuiyuanList = huoDongXiangQingDuiyuanDao.queryAll();
					if (huoDongXiangQingDuiyuanList != null && huoDongXiangQingDuiyuanList.size() != 0// 数据库中有值并且为当前活动时显示更换，否则显示领取
							&& TextUtils.equals(huoDongXiangQingDuiyuanList.get(0).getAct_id(), act_id)) {
						showAlertDialog(mContext, "是否更换当前活动?                   ", 4);
					} else {
						if (TextUtils.isEmpty(App.getInstance().getLeaderHuoDongId())) {
							if (huoDongXiangQingDuiyuanList != null && huoDongXiangQingDuiyuanList.size() != 0) {
								showAlertDialog(mContext, "领取当前活动将覆盖上一个活动，是否继续?             ", 7);
							} else {
								if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
									showdialogtext("正在领取中...");
									if (getCustomProgressDialog() != null) {
										getCustomProgressDialog().setOnCancelListener(new OnCancelListener() {

											@Override
											public void onCancel(DialogInterface dialog) {
												showAlertDialog(mContext, "是否取消领取?                   ", 4);
											}
										});
									}
									DownDao();
								} else {
									showAlertDialog(mContext, "当前已开启仅在WIFI下展示详情图片，是否继续下载?            ", 7);
								}
							}
						} else {
							showToast("您已经在”领队工具”中领取任务了，不能在”途中”领取任务了哦");
						}
					}
				} else if (showBotton == CommonUtility.XianShiTab_Leader) {
					huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
					if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0// 数据库中有值并且为当前活动时显示更换，否则显示领取
							&& TextUtils.equals(huoDongXiangQingLeaderList.get(0).getAct_id(), act_id)) {
						if (isReport == 0) {
							showAlertDialog(mContext, "是否更换当前活动?                   ", 10);
						} else {
							showToast("当前已有队员报到，不可更改活动！");
						}
					} else {
						if (TextUtils.isEmpty(App.getInstance().getHuoDongId())) {
							if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0) {
								if (isReport == 0) {
									showAlertDialog(mContext, "领取当前活动将覆盖上一个活动，是否继续?             ", 12);
								} else {
									showToast("当前已有队员报到，不可更改活动！");
								}
							} else {
								if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
									showdialogtext("正在领取中...");
									if (getCustomProgressDialog() != null) {
										getCustomProgressDialog().setOnCancelListener(new OnCancelListener() {

											@Override
											public void onCancel(DialogInterface dialog) {
												showAlertDialog(mContext, "是否取消领取?                   ", 10);
											}
										});
									}
									DownDaoLeader();
								} else {
									showAlertDialog(mContext, "当前已开启仅在WIFI下展示详情图片，是否继续领取?                   ", 12);
								}
							}
						} else {
							showToast("您已经在”途中”中领取任务了，不能在”领队工具”领取任务了哦");
						}
					}

				} else if (showBotton == CommonUtility.XianShiTab_Leader_NOW) {
					huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
					if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0// 数据库中有值并且为当前活动时显示更换，否则显示领取
							&& TextUtils.equals(huoDongXiangQingLeaderList.get(0).getAct_id(), act_id)) {
						if (isReport == 0) {
							showAlertDialog(mContext, "是否更换当前活动?                   ", 10);
						} else {
							showToast("当前已有队员报到，不可更改活动！");
						}
					} else {
						if (TextUtils.isEmpty(App.getInstance().getHuoDongId())) {
							if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0) {
								if (isReport == 0) {
									showAlertDialog(mContext, "领取当前活动将覆盖上一个活动，是否继续?             ", 12);
								} else {
									showToast("当前已有队员报到，不可更改活动！");
								}
							} else {
								if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
									showdialogtext("正在领取中...");
									if (getCustomProgressDialog() != null) {
										getCustomProgressDialog().setOnCancelListener(new OnCancelListener() {

											@Override
											public void onCancel(DialogInterface dialog) {
												showAlertDialog(mContext, "是否取消领取?                   ", 10);
											}
										});
									}
									DownDaoLeader();
								} else {
									showAlertDialog(mContext, "当前已开启仅在WIFI下展示详情图片，是否继续领取?                   ", 12);
								}
							}
						} else {
							showToast("您已经在”途中”中领取任务了，不能在”领队工具”领取任务了哦");
						}
					}
				} else if (showBotton == CommonUtility.XianShiTab_False) {
					switch (act_state) {
					case 1:
						if (bm) {
							pop_huodong_view.setVisibility(View.VISIBLE);
							pop_huodong_view_shibai.setVisibility(View.GONE);
							popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
						} else {
							showAlertDialog(mContext, "是否取消报名?                   ", 2);
						}
						break;
					case 2:
						if (bm) {
							pop_huodong_view.setVisibility(View.VISIBLE);
							pop_huodong_view_shibai.setVisibility(View.GONE);
							popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
						} else {
							showAlertDialog(mContext, "是否取消报名?                   ", 2);
						}
						break;
					case 3:
						if (issignup == 1) {
							if (isConfirmed == 1) {
								huoDongXiangQingDuiyuanList = huoDongXiangQingDuiyuanDao.queryAll();
								if (huoDongXiangQingDuiyuanList != null && huoDongXiangQingDuiyuanList.size() != 0// 数据库中有值并且为当前活动时显示更换，否则显示领取
										&& TextUtils.equals(huoDongXiangQingDuiyuanList.get(0).getAct_id(), act_id)) {
									showAlertDialog(mContext, "是否更换当前活动?                   ", 4);
								} else {
									if (TextUtils.isEmpty(App.getInstance().getLeaderHuoDongId())) {
										if (huoDongXiangQingDuiyuanList != null
												&& huoDongXiangQingDuiyuanList.size() != 0) {
											showAlertDialog(mContext, "下载当前活动将覆盖上一个活动，是否继续?             ", 7);
										} else {
											if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
												showdialogtext("正在下载中...");
												if (getCustomProgressDialog() != null) {
													getCustomProgressDialog()
															.setOnCancelListener(new OnCancelListener() {

																@Override
																public void onCancel(DialogInterface dialog) {
																	showAlertDialog(mContext,
																			"是否取消下载?                   ", 4);
																}
															});
												}
												DownDao();
											} else {
												showAlertDialog(mContext, "当前已开启仅在WIFI下展示详情图片，是否继续下载?            ", 7);
											}
										}
									} else {
										showToast("您已经在”领队工具”中领取任务了，不能在”途中”领取任务了哦");
									}
								}
							} else {
								showToast("抱歉，请耐心等待领队审核");
							}
						} else {
							showToast("抱歉，您未参加次活动");
						}
						break;
					case 4:
						if (issignup == 1) {
							if (isConfirmed == 1) {
								huoDongXiangQingDuiyuanList = huoDongXiangQingDuiyuanDao.queryAll();
								if (huoDongXiangQingDuiyuanList != null && huoDongXiangQingDuiyuanList.size() != 0// 数据库中有值并且为当前活动时显示更换，否则显示领取
										&& TextUtils.equals(huoDongXiangQingDuiyuanList.get(0).getAct_id(), act_id)) {
									showAlertDialog(mContext, "是否更换当前活动?                   ", 4);
								} else {
									if (TextUtils.isEmpty(App.getInstance().getLeaderHuoDongId())) {
										if (huoDongXiangQingDuiyuanList != null
												&& huoDongXiangQingDuiyuanList.size() != 0) {
											showAlertDialog(mContext, "下载当前活动将覆盖上一个活动，是否继续?             ", 7);
										} else {
											if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
												showdialogtext("正在下载中...");
												if (getCustomProgressDialog() != null) {
													getCustomProgressDialog()
															.setOnCancelListener(new OnCancelListener() {

																@Override
																public void onCancel(DialogInterface dialog) {
																	showAlertDialog(mContext,
																			"是否取消下载?                   ", 4);
																}
															});
												}
												DownDao();
											} else {
												showAlertDialog(mContext, "当前已开启仅在WIFI下展示详情图片，是否继续下载?            ", 7);
											}
										}
									} else {
										showToast("您已经在”领队工具”中领取任务了，不能在”途中”领取任务了哦");
									}
								}
							} else {
								showToast("抱歉，请耐心等待领队审核");
							}
						} else {
							showToast("抱歉，您未参加次活动");
						}
						break;
					case 5:// 点评游记
						if (issignup == 1) {
							if (isEvaluateAct == 0) {
								Bundle bundle = new Bundle();
								bundle.putInt("isLeader", 2);
								bundle.putString("act_id", act_id);
								bundle.putString("tv11", tv11.getText().toString());
								bundle.putString("tv12", tv12.getText().toString());
								bundle.putString("tv13", tv13.getText().toString());
								bundle.putString("tv14", tv14.getText().toString());
								bundle.putString("tv15", club_logo);
								bundle.putString("tv16", tv16.getText().toString());
								bundle.putString("tv17", tv17.getText().toString());
								bundle.putString("tv18", tv18.getText().toString());
								bundle.putString("tv19", tv19.getText().toString());
								bundle.putString("tv20", tv20.getText().toString());
								bundle.putString("tv21", tv21.getText().toString());
								openActivity(ShouDuiDianPingActivity.class, bundle);
								isFirst = false;
							} else if (isEvaluateAct == 1) {
								showToast("您已经点评啦~");
							}
						} else {
							showToast("抱歉，您未参加次活动");
						}
						break;
					case 6:
						if (issignup == 1) {
							if (isEvaluateAct == 1) {
								AddYouji();
							} else {
								if (isEvaluateAct == 0) {
									Bundle bundle = new Bundle();
									bundle.putInt("isLeader", 2);
									bundle.putString("act_id", act_id);
									bundle.putString("tv11", tv11.getText().toString());
									bundle.putString("tv12", tv12.getText().toString());
									bundle.putString("tv13", tv13.getText().toString());
									bundle.putString("tv14", tv14.getText().toString());
									bundle.putString("tv15", club_logo);
									bundle.putString("tv16", tv16.getText().toString());
									bundle.putString("tv17", tv17.getText().toString());
									bundle.putString("tv18", tv18.getText().toString());
									bundle.putString("tv19", tv19.getText().toString());
									bundle.putString("tv20", tv20.getText().toString());
									bundle.putString("tv21", tv21.getText().toString());
									openActivity(ShouDuiDianPingActivity.class, bundle);
									isFirst = false;
								} else if (isEvaluateAct == 1) {
									showToast("您已经点评啦~");
								}
							}
						} else {
							showToast("抱歉，您未参加次活动");
						}
						break;
					default:
						break;
					}
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
				isFirst = false;
			}
			break;
		case R.id.huodong_select_three:
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (showBotton == CommonUtility.XianShiTab_SheZhi) {
					if (act_state == 1) {// 报名截止
						showAlertDialog(mContext, "是否确定报名截止?                   ", 3);
					} else if (act_state == 2) {// 下载数据
						App.getInstance().setDown(true);
						showToast("下载成功");
						// DownDao();
					} else if (act_state == 6) {
						showAlertDialog(mContext, "是否删除记录?                   ", 5);
					}
				} else if (showBotton == CommonUtility.XianShiTab_False) {
					if (issignup == 1) {
						if (isEvaluateAct == 1) {
							showAlertDialog(mContext, "是否删除记录?                   ", 9);
						} else {
							AddYouji();
						}
					} else {
						showToast("抱歉，您未参加次活动");
					}
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
				isFirst = false;
			}
			break;
		case R.id.top_tv_right:
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (showBotton == CommonUtility.XianShiTab_False) {
					if (issignup == 1) {
						showAlertDialog(mContext, "是否删除记录?                   ", 9);
					} else {
						showToast("抱歉，您未参加次活动");
					}
				} else if (showBotton == CommonUtility.XianShiTab_SheZhi) {
					if (App.getInstance().getDown()) {
						if (!TextUtils.isEmpty(trace_data) && !TextUtils.isEmpty(anchor_longitude)
								&& !TextUtils.isEmpty(anchor_latitude)) {
							Bundle bundle = new Bundle();
							bundle.putString("trace_data", trace_data);
							bundle.putString("anchor_longitude", anchor_longitude);
							bundle.putString("anchor_latitude", anchor_latitude);
							openActivity(MyTrackActivity.class, bundle);
							isFirst = false;
						}
					} else {
						showToast("请先下载数据");
					}
				} else if (showBotton == CommonUtility.XianShiTab_RenWu) {
					showAlertDialog(mContext, "是否上传轨迹文件?                   ", 8);
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
				isFirst = false;
			}
			break;
		case R.id.iv_map:// 点击截图
			downid_viewpager = 0;
			showViewPage(null, null, picture_url, 2);
			break;
		case R.id.pop_huodong_view:
			break;
		case R.id.pop_huodong_view_shibai:
			break;
		case R.id.pop_huodong_image:
			popContact.dismiss();
			break;
		case R.id.pop_huodong_close:
			popContact.dismiss();
			break;
		case R.id.pop_huodong_close_shibai:
			popContact.dismiss();
			break;
		case R.id.pop_huodong_ok:
			if (bm) {// 报名操作
				if (renshu <= 0) {
					showToast("至少一人参加");
				} else if (zhuangbei == 3) {
					showToast("请选择有无装备");
				} else if (jingyan == 3) {
					showToast("请选择有无经验");
				} else {
					showdialogtext("报名中...");
					sendBaoMingActionAsyn();
				}
			}
			break;
		case R.id.pop_huodong_jian:
			if (renshu > 0) {
				--renshu;
				pop_huodong_geshu.setText(renshu + "");
			} else {
				showToast("至少选择一个人");
			}
			break;
		case R.id.pop_huodong_jia:
			++renshu;
			pop_huodong_geshu.setText(renshu + "");
			break;
		case R.id.pop_huodong_check1_view:
			zhuangbei = 1;
			pop_huodong_check1.setVisibility(View.VISIBLE);
			pop_huodong_check2.setVisibility(View.GONE);
			pop_huodong_check1_text.setBackgroundResource(R.drawable.duihuan_pop_ground);
			pop_huodong_check2_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
			break;
		case R.id.pop_huodong_check2_view:
			zhuangbei = 0;
			pop_huodong_check1.setVisibility(View.GONE);
			pop_huodong_check2.setVisibility(View.VISIBLE);
			pop_huodong_check1_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
			pop_huodong_check2_text.setBackgroundResource(R.drawable.duihuan_pop_ground);
			break;
		case R.id.pop_huodong_check3_view:
			jingyan = 1;
			pop_huodong_check3.setVisibility(View.VISIBLE);
			pop_huodong_check4.setVisibility(View.GONE);
			pop_huodong_check3_text.setBackgroundResource(R.drawable.duihuan_pop_ground);
			pop_huodong_check4_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
			break;
		case R.id.pop_huodong_check4_view:
			jingyan = 0;
			pop_huodong_check3.setVisibility(View.GONE);
			pop_huodong_check4.setVisibility(View.VISIBLE);
			pop_huodong_check3_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
			pop_huodong_check4_text.setBackgroundResource(R.drawable.duihuan_pop_ground);
			break;
		case R.id.top_tv_shoucang:// 收藏
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (sc) {// 收藏操作
					sendCollectionActivityAsyn();
				} else {// 取消收藏操作
					showAlertDialog(mContext, "是否取消收藏?                   ", 1);
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
				isFirst = false;
			}
			break;
		case R.id.top_tv_fenxiang:// 分享
			if (!TextUtils.isEmpty(ShareUrl)) {
				try {
					showShareContent(ShareUrl);
				} catch (Exception e) {
					showToast("分享地址错误");
				}
			} else {
				showToast("分享失败，请重新分享");
			}
			break;
		case R.id.tv15:// 打开俱乐部详情
			Bundle bundle = new Bundle();
			bundle.putString("clubid", club_id);
			openActivity(JuLeBuXiangQingActivity.class, bundle);
			isFirst = true;
			break;
		case R.id.tv16:// 打开俱乐部详情
			Bundle bundle1 = new Bundle();
			bundle1.putString("clubid", club_id);
			openActivity(JuLeBuXiangQingActivity.class, bundle1);
			isFirst = true;
			break;
		default:
			break;
		}
	}

	/**
	 * 添加游记
	 */
	public void AddYouji() {
		Bundle bundle = new Bundle();
		bundle.putString("act_id", act_id);
		openActivity(XinJianYouJizjyActivity.class, bundle);
		isFirst = true;
	}

	/**
	 * 下载数据到数据库
	 */
	public void DownDao() {
		// 删除保存活动图片的文件夹
		File appDir = new File(Environment.getExternalStorageDirectory(), "outdoorhuodong");
		if (appDir.exists()) {
			DataCleanManager.deleteFile(appDir);
		}
		// 需要使用到 act_id，不然无法查找
		huoDongXiangQing = new HuoDongXiangQingItem();
		huoDongXiangQing.setAct_id(act_id);
		huoDongXiangQing.setAct_sponsor_id(act_sponsor_id);
		huoDongXiangQing.setAct_title(act_title);
		huoDongXiangQing.setAct_desc(act_desc);
		huoDongXiangQing.setAct_desc_web(act_desc_web);
		huoDongXiangQing.setAct_desc_intro(act_desc_intro);
		huoDongXiangQing.setAct_level(act_level);
		huoDongXiangQing.setVerify(verify);
		huoDongXiangQing.setAct_type(act_type);
		huoDongXiangQing.setCreate_time(create_time);
		huoDongXiangQing.setAct_start_time(act_start_time);
		huoDongXiangQing.setAct_end_time(act_end_time);
		huoDongXiangQing.setAct_join_num_limit(act_join_num_limit);
		huoDongXiangQing.setAct_fee_include(act_fee_include);
		huoDongXiangQing.setAct_weixin(act_weixin);
		huoDongXiangQing.setAct_base_equip(act_base_equip);
		huoDongXiangQing.setAct_scenicspots(act_scenicspots);
		huoDongXiangQing.setTrace_file_id(trace_file_id);
		huoDongXiangQing.setConfirm_member(confirm_member);
		huoDongXiangQing.setMan(man);
		huoDongXiangQing.setFemale(female);
		huoDongXiangQing.setIssignup(issignup);
		huoDongXiangQing.setDescribe(describe);
		huoDongXiangQing.setClub_id(club_id);
		huoDongXiangQing.setLeader_id(leader_id);
		huoDongXiangQing.setTrace_id(trace_id);
		huoDongXiangQing.setAnchor_longitude(anchor_longitude);
		huoDongXiangQing.setTrace_data(trace_data);
		huoDongXiangQing.setAct_state(act_state);
		huoDongXiangQing.setAnchor_latitude(anchor_latitude);
		huoDongXiangQing.setCity(city);
		huoDongXiangQing.setEpilogue(epilogue);
		huoDongXiangQing.setLogistics(logistics);
		huoDongXiangQing.setClub_name(club_name);
		huoDongXiangQing.setLeader_name(leader_name);
		huoDongXiangQing.setAct_budget(act_budget);
		huoDongXiangQing.setIsReport(isReport);
		huoDongXiangQing.setAct_venue_time(act_venue_time);
		huoDongXiangQing.setAct_venue(act_venue);
		huoDongXiangQing.setIsCollect(isCollect);
		huoDongXiangQing.setAct_else(act_else);
		huoDongXiangQing.setName(name);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!TextUtils.isEmpty(leader_head)) {
					huoDongXiangQing.setLeader_head(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(leader_head), 1));
				} else {
					huoDongXiangQing.setLeader_head("");
				}
				if (!TextUtils.isEmpty(picture_url)) {
					huoDongXiangQing.setPicture_url(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(picture_url), 1));
				} else {
					huoDongXiangQing.setPicture_url("");
				}
				if (!TextUtils.isEmpty(club_logo)) {
					huoDongXiangQing.setClub_logo(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(club_logo), 1));
				} else {
					huoDongXiangQing.setClub_logo("");
				}
				if (!TextUtils.isEmpty(act_2d_code)) {
					huoDongXiangQing.setAct_2d_code(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(act_2d_code), 1));
				} else {
					huoDongXiangQing.setAct_2d_code("");
				}
				List<HuoDongImages> topImageDatasTemp = new ArrayList<HuoDongImages>();
				if (topImageDatas != null && topImageDatas.size() != 0) {
					for (int i = 0; i < topImageDatas.size(); i++) {
						topImage = new HuoDongImages();
						topImage.setImage(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
								ImageControl.getHttpBitmap(topImageDatas.get(i).getImage()), 1));
						topImageDatasTemp.add(topImage);
					}
				}
				huoDongXiangQing.setAct_logo(gson.toJson(topImageDatasTemp));

				List<HuoDongXiangQingDongTai> HuoDongXiangQingDongTaiBeansTemp = new ArrayList<HuoDongXiangQingDongTai>();
				if (HuoDongXiangQingDongTaiBeans != null && HuoDongXiangQingDongTaiBeans.size() != 0) {
					for (int i = 0; i < HuoDongXiangQingDongTaiBeans.size(); i++) {
						HuoDongXiangQingDongTaiBean = new HuoDongXiangQingDongTai();
						HuoDongXiangQingDongTaiBean.setAct_id(HuoDongXiangQingDongTaiBeans.get(i).getAct_id());
						HuoDongXiangQingDongTaiBean.setArrive_lng(HuoDongXiangQingDongTaiBeans.get(i).getArrive_lng());
						HuoDongXiangQingDongTaiBean
								.setArrive_time(HuoDongXiangQingDongTaiBeans.get(i).getArrive_time());
						HuoDongXiangQingDongTaiBean.setDescript(HuoDongXiangQingDongTaiBeans.get(i).getDescript());
						HuoDongXiangQingDongTaiBean
								.setRarrive_lag(HuoDongXiangQingDongTaiBeans.get(i).getRarrive_lag());
						try {
							JSONArray jsonArray = new JSONArray(HuoDongXiangQingDongTaiBeans.get(i).getImages());
							if (jsonArray != null && jsonArray.length() != 0) {
								HuoDongImagesBeans = new ArrayList<HuoDongImages>();
								for (int j = 0; j < jsonArray.length(); j++) {
									HuoDongImages = new HuoDongImages();
									HuoDongImages
											.setImage(
													ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
															ImageControl.getHttpBitmap(
																	jsonArray.getJSONObject(0).getString("fullurl")),
													1));
									HuoDongImagesBeans.add(HuoDongImages);
								}
								HuoDongXiangQingDongTaiBean.setImages(gson.toJson(HuoDongImagesBeans));
							}
						} catch (JSONException e) {
							HuoDongXiangQingDongTaiBean.setImages(null);
						}
						HuoDongXiangQingDongTaiBeansTemp.add(HuoDongXiangQingDongTaiBean);
					}
				}
				huoDongXiangQing.setHistory(gson.toJson(HuoDongXiangQingDongTaiBeansTemp));

				List<HuoDongXiangQingGongNue> HuoDongXiangQingItemBeansTemp = new ArrayList<HuoDongXiangQingGongNue>();
				if (HuoDongXiangQingItemBeans != null && HuoDongXiangQingItemBeans.size() != 0) {
					for (int i = 0; i < HuoDongXiangQingItemBeans.size(); i++) {
						HuoDongXiangQingItemBean = new HuoDongXiangQingGongNue();
						HuoDongXiangQingItemBean.setSchedu_desc(HuoDongXiangQingItemBeans.get(i).getSchedu_desc());
						HuoDongXiangQingItemBean.setSchedu_time(HuoDongXiangQingItemBeans.get(i).getSchedu_time());
						HuoDongXiangQingItemBean.setSchedu_title(HuoDongXiangQingItemBeans.get(i).getSchedu_title());
						try {
							JSONArray jsonArray = new JSONArray(HuoDongXiangQingItemBeans.get(i).getImgs());
							if (jsonArray != null && jsonArray.length() != 0) {
								HuoDongImagesBeans = new ArrayList<HuoDongImages>();
								for (int j = 0; j < jsonArray.length(); j++) {
									HuoDongImages = new HuoDongImages();
									HuoDongImages
											.setImage(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
													ImageControl.getHttpBitmap(jsonArray.getString(0)), 1));
									HuoDongImagesBeans.add(HuoDongImages);
								}
								HuoDongXiangQingItemBean.setImgs(gson.toJson(HuoDongImagesBeans));
							}
						} catch (JSONException e) {
							HuoDongXiangQingItemBean.setImgs(null);
						}
						HuoDongXiangQingItemBeansTemp.add(HuoDongXiangQingItemBean);
					}
				}
				huoDongXiangQing.setAct_schedule_app(gson.toJson(HuoDongXiangQingItemBeansTemp));

				List<HuoDongDuiYuan> HuoDongDuiYuanBeansTemp = new ArrayList<HuoDongDuiYuan>();
				if (HuoDongDuiYuanBeans != null && HuoDongDuiYuanBeans.size() != 0) {
					for (int i = 0; i < HuoDongDuiYuanBeans.size(); i++) {
						HuoDongDuiYuanBean = new HuoDongDuiYuan();
						HuoDongDuiYuanBean.setUserid(HuoDongDuiYuanBeans.get(i).getHead());
						HuoDongDuiYuanBean.setHead(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
								ImageControl.getHttpBitmap(HuoDongDuiYuanBeans.get(i).getHead()), 1));
						HuoDongDuiYuanBeansTemp.add(HuoDongDuiYuanBean);
					}
				}
				huoDongXiangQing.setTeam_member(gson.toJson(HuoDongDuiYuanBeansTemp));

				if (huoDongXiangQingDuiyuanDao.queryAll() != null) {// 当数据库中数据不为空时删除数据
					huoDongXiangQingDuiyuanDao.deleteAll();
				}
				huoDongXiangQingDuiyuanDao.add(huoDongXiangQing);
				App.getInstance().setHuoDongId(act_id);
				App.getInstance().setDown(true);
				if (isshowdialog()) {
					closedialog();
				}
				handlerlist.sendEmptyMessage(CommonUtility.SERVEROK11);
			}
		}).start();
	}

	/**
	 * 数据库获取数据
	 */
	public void GetDao() {
		// 需要使用到 act_id，不然无法查找
		huoDongXiangQingDuiyuanList.clear();
		huoDongXiangQingDuiyuanList = huoDongXiangQingDuiyuanDao.queryAll();
		if (huoDongXiangQingDuiyuanList != null && huoDongXiangQingDuiyuanList.size() != 0) {
			act_id = huoDongXiangQingDuiyuanList.get(0).getAct_id();
			act_sponsor_id = huoDongXiangQingDuiyuanList.get(0).getAct_sponsor_id();
			act_title = huoDongXiangQingDuiyuanList.get(0).getAct_title();
			act_desc = huoDongXiangQingDuiyuanList.get(0).getAct_desc();
			act_desc_web = huoDongXiangQingDuiyuanList.get(0).getAct_desc_web();
			act_desc_intro = huoDongXiangQingDuiyuanList.get(0).getAct_desc_intro();
			act_level = huoDongXiangQingDuiyuanList.get(0).getAct_level();
			verify = huoDongXiangQingDuiyuanList.get(0).getVerify();
			act_type = huoDongXiangQingDuiyuanList.get(0).getAct_type();
			create_time = huoDongXiangQingDuiyuanList.get(0).getCreate_time();
			act_start_time = huoDongXiangQingDuiyuanList.get(0).getAct_start_time();
			act_end_time = huoDongXiangQingDuiyuanList.get(0).getAct_end_time();
			act_join_num_limit = huoDongXiangQingDuiyuanList.get(0).getAct_join_num_limit();
			act_fee_include = huoDongXiangQingDuiyuanList.get(0).getAct_fee_include();
			act_weixin = huoDongXiangQingDuiyuanList.get(0).getAct_weixin();
			act_base_equip = huoDongXiangQingDuiyuanList.get(0).getAct_base_equip();
			act_scenicspots = huoDongXiangQingDuiyuanList.get(0).getAct_scenicspots();
			trace_file_id = huoDongXiangQingDuiyuanList.get(0).getTrace_file_id();
			confirm_member = huoDongXiangQingDuiyuanList.get(0).getConfirm_member();
			man = huoDongXiangQingDuiyuanList.get(0).getMan();
			female = huoDongXiangQingDuiyuanList.get(0).getFemale();
			issignup = huoDongXiangQingDuiyuanList.get(0).getIssignup();
			describe = huoDongXiangQingDuiyuanList.get(0).getDescribe();
			club_id = huoDongXiangQingDuiyuanList.get(0).getClub_id();
			leader_id = huoDongXiangQingDuiyuanList.get(0).getLeader_id();
			trace_id = huoDongXiangQingDuiyuanList.get(0).getTrace_id();
			anchor_longitude = huoDongXiangQingDuiyuanList.get(0).getAnchor_longitude();
			trace_data = huoDongXiangQingDuiyuanList.get(0).getTrace_data();
			act_state = huoDongXiangQingDuiyuanList.get(0).getAct_state();
			anchor_latitude = huoDongXiangQingDuiyuanList.get(0).getAnchor_latitude();
			city = huoDongXiangQingDuiyuanList.get(0).getCity();
			epilogue = huoDongXiangQingDuiyuanList.get(0).getEpilogue();
			logistics = huoDongXiangQingDuiyuanList.get(0).getLogistics();
			club_name = huoDongXiangQingDuiyuanList.get(0).getClub_name();
			leader_name = huoDongXiangQingDuiyuanList.get(0).getLeader_name();
			act_budget = huoDongXiangQingDuiyuanList.get(0).getAct_budget();
			isReport = huoDongXiangQingDuiyuanList.get(0).getIsReport();
			act_venue_time = huoDongXiangQingDuiyuanList.get(0).getAct_venue_time();
			act_venue = huoDongXiangQingDuiyuanList.get(0).getAct_venue();
			isCollect = huoDongXiangQingDuiyuanList.get(0).getIsCollect();
			act_else = huoDongXiangQingDuiyuanList.get(0).getAct_else();
			name = huoDongXiangQingDuiyuanList.get(0).getName();
			picture_url = huoDongXiangQingDuiyuanList.get(0).getPicture_url();
			club_logo = huoDongXiangQingDuiyuanList.get(0).getClub_logo();
			act_2d_code = huoDongXiangQingDuiyuanList.get(0).getAct_2d_code();
			topImageDatas.clear();
			HuoDongXiangQingDongTaiBeans.clear();
			HuoDongXiangQingItemBeans.clear();
			HuoDongDuiYuanBeans.clear();
			if (!TextUtils.isEmpty(huoDongXiangQingDuiyuanList.get(0).getAct_logo())) {
				topImageDatas = gson.fromJson(huoDongXiangQingDuiyuanList.get(0).getAct_logo(),
						new TypeToken<List<HuoDongImages>>() {
						}.getType());
			}
			if (!TextUtils.isEmpty(huoDongXiangQingDuiyuanList.get(0).getHistory())) {
				HuoDongXiangQingDongTaiBeans = gson.fromJson(huoDongXiangQingDuiyuanList.get(0).getHistory(),
						new TypeToken<List<HuoDongXiangQingDongTai>>() {
						}.getType());
			}
			if (!TextUtils.isEmpty(huoDongXiangQingDuiyuanList.get(0).getAct_schedule_app())) {
				HuoDongXiangQingItemBeans = gson.fromJson(huoDongXiangQingDuiyuanList.get(0).getAct_schedule_app(),
						new TypeToken<List<HuoDongXiangQingGongNue>>() {
						}.getType());
			}
			if (!TextUtils.isEmpty(huoDongXiangQingDuiyuanList.get(0).getTeam_member())) {
				HuoDongDuiYuanBeans = gson.fromJson(huoDongXiangQingDuiyuanList.get(0).getTeam_member(),
						new TypeToken<List<HuoDongDuiYuan>>() {
						}.getType());
			}
			handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
		}
	}

	/**
	 * 更新数据到数据库
	 */
	public void DownDaoLeaderHistory() {
		List<HuoDongXiangQingDongTai> HuoDongXiangQingDongTaiBeansTemp = new ArrayList<HuoDongXiangQingDongTai>();
		if (HuoDongXiangQingDongTaiBeans != null && HuoDongXiangQingDongTaiBeans.size() != 0) {
			for (int i = 0; i < HuoDongXiangQingDongTaiBeans.size(); i++) {
				HuoDongXiangQingDongTaiBean = new HuoDongXiangQingDongTai();
				HuoDongXiangQingDongTaiBean.setAct_id(HuoDongXiangQingDongTaiBeans.get(i).getAct_id());
				HuoDongXiangQingDongTaiBean.setArrive_lng(HuoDongXiangQingDongTaiBeans.get(i).getArrive_lng());
				HuoDongXiangQingDongTaiBean.setArrive_time(HuoDongXiangQingDongTaiBeans.get(i).getArrive_time());
				HuoDongXiangQingDongTaiBean.setDescript(HuoDongXiangQingDongTaiBeans.get(i).getDescript());
				HuoDongXiangQingDongTaiBean.setRarrive_lag(HuoDongXiangQingDongTaiBeans.get(i).getRarrive_lag());
				try {
					JSONArray jsonArray = new JSONArray(HuoDongXiangQingDongTaiBeans.get(i).getImages());
					if (jsonArray != null && jsonArray.length() != 0) {
						HuoDongImagesBeans = new ArrayList<HuoDongImages>();
						for (int j = 0; j < jsonArray.length(); j++) {
							HuoDongImages = new HuoDongImages();
							HuoDongImages.setImage(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
									ImageControl.getHttpBitmap(jsonArray.getJSONObject(0).getString("fullurl")), 5));
							HuoDongImagesBeans.add(HuoDongImages);
						}
						HuoDongXiangQingDongTaiBean.setImages(gson.toJson(HuoDongImagesBeans));
					}
				} catch (JSONException e) {
					HuoDongXiangQingDongTaiBean.setImages(null);
				}
				HuoDongXiangQingDongTaiBeansTemp.add(HuoDongXiangQingDongTaiBean);
			}
		}
		huoDongXiangQingLeaderDao.updateStatehistory(gson.toJson(HuoDongXiangQingDongTaiBeansTemp), act_id);
		if (isshowdialog()) {
			closedialog();
		}
	}

	/**
	 * 下载数据到数据库
	 */
	public void DownDaoLeader() {
		// 删除保存活动图片的文件夹
		File appDir = new File(Environment.getExternalStorageDirectory(), "outdoorhuodongleader");
		if (appDir.exists()) {
			DataCleanManager.deleteFile(appDir);
		}
		// 需要使用到 act_id，不然无法查找
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeader.setAct_id(act_id);
		huoDongXiangQingLeader.setAct_sponsor_id(act_sponsor_id);
		huoDongXiangQingLeader.setAct_title(act_title);
		huoDongXiangQingLeader.setAct_desc(act_desc);
		huoDongXiangQingLeader.setAct_desc_web(act_desc_web);
		huoDongXiangQingLeader.setAct_desc_intro(act_desc_intro);
		huoDongXiangQingLeader.setAct_level(act_level);
		huoDongXiangQingLeader.setVerify(verify);
		huoDongXiangQingLeader.setAct_type(act_type);
		huoDongXiangQingLeader.setCreate_time(create_time);
		huoDongXiangQingLeader.setAct_start_time(act_start_time);
		huoDongXiangQingLeader.setAct_end_time(act_end_time);
		huoDongXiangQingLeader.setAct_join_num_limit(act_join_num_limit);
		huoDongXiangQingLeader.setAct_fee_include(act_fee_include);
		huoDongXiangQingLeader.setAct_weixin(act_weixin);
		huoDongXiangQingLeader.setAct_base_equip(act_base_equip);
		huoDongXiangQingLeader.setAct_scenicspots(act_scenicspots);
		huoDongXiangQingLeader.setTrace_file_id(trace_file_id);
		huoDongXiangQingLeader.setConfirm_member(confirm_member);
		huoDongXiangQingLeader.setMan(man);
		huoDongXiangQingLeader.setFemale(female);
		huoDongXiangQingLeader.setIssignup(issignup);
		huoDongXiangQingLeader.setDescribe(describe);
		huoDongXiangQingLeader.setClub_id(club_id);
		huoDongXiangQingLeader.setLeader_id(leader_id);
		huoDongXiangQingLeader.setTrace_id(trace_id);
		huoDongXiangQingLeader.setAnchor_longitude(anchor_longitude);
		huoDongXiangQingLeader.setTrace_data(trace_data);
		huoDongXiangQingLeader.setAct_state(act_state);
		huoDongXiangQingLeader.setAnchor_latitude(anchor_latitude);
		huoDongXiangQingLeader.setCity(city);
		huoDongXiangQingLeader.setEpilogue(epilogue);
		huoDongXiangQingLeader.setLogistics(logistics);
		huoDongXiangQingLeader.setClub_name(club_name);
		huoDongXiangQingLeader.setLeader_name(leader_name);
		huoDongXiangQingLeader.setAct_budget(act_budget);
		huoDongXiangQingLeader.setIsReport(isReport);
		huoDongXiangQingLeader.setAct_venue_time(act_venue_time);
		huoDongXiangQingLeader.setAct_venue(act_venue);
		huoDongXiangQingLeader.setIsCollect(isCollect);
		huoDongXiangQingLeader.setAct_else(act_else);
		huoDongXiangQingLeader.setName(name);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!TextUtils.isEmpty(leader_head)) {
					huoDongXiangQing.setLeader_head(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(leader_head), 5));
				} else {
					huoDongXiangQing.setLeader_head("");
				}
				if (!TextUtils.isEmpty(picture_url)) {
					huoDongXiangQingLeader.setPicture_url(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(picture_url), 5));
				} else {
					huoDongXiangQingLeader.setPicture_url("");
				}
				if (!TextUtils.isEmpty(club_logo)) {
					huoDongXiangQingLeader.setClub_logo(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(club_logo), 5));
				} else {
					huoDongXiangQingLeader.setClub_logo("");
				}
				if (!TextUtils.isEmpty(act_2d_code)) {
					huoDongXiangQingLeader.setAct_2d_code(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
							ImageControl.getHttpBitmap(act_2d_code), 5));
				} else {
					huoDongXiangQingLeader.setAct_2d_code("");
				}
				List<HuoDongImages> topImageDatasTemp = new ArrayList<HuoDongImages>();
				if (topImageDatas != null && topImageDatas.size() != 0) {
					for (int i = 0; i < topImageDatas.size(); i++) {
						topImage = new HuoDongImages();
						topImage.setImage(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
								ImageControl.getHttpBitmap(topImageDatas.get(i).getImage()), 5));
						topImageDatasTemp.add(topImage);
					}
				}
				huoDongXiangQingLeader.setAct_logo(gson.toJson(topImageDatasTemp));

				List<HuoDongXiangQingDongTai> HuoDongXiangQingDongTaiBeansTemp = new ArrayList<HuoDongXiangQingDongTai>();
				if (HuoDongXiangQingDongTaiBeans != null && HuoDongXiangQingDongTaiBeans.size() != 0) {
					for (int i = 0; i < HuoDongXiangQingDongTaiBeans.size(); i++) {
						HuoDongXiangQingDongTaiBean = new HuoDongXiangQingDongTai();
						HuoDongXiangQingDongTaiBean.setAct_id(HuoDongXiangQingDongTaiBeans.get(i).getAct_id());
						HuoDongXiangQingDongTaiBean.setArrive_lng(HuoDongXiangQingDongTaiBeans.get(i).getArrive_lng());
						HuoDongXiangQingDongTaiBean
								.setArrive_time(HuoDongXiangQingDongTaiBeans.get(i).getArrive_time());
						HuoDongXiangQingDongTaiBean.setDescript(HuoDongXiangQingDongTaiBeans.get(i).getDescript());
						HuoDongXiangQingDongTaiBean
								.setRarrive_lag(HuoDongXiangQingDongTaiBeans.get(i).getRarrive_lag());
						try {
							JSONArray jsonArray = new JSONArray(HuoDongXiangQingDongTaiBeans.get(i).getImages());
							if (jsonArray != null && jsonArray.length() != 0) {
								HuoDongImagesBeans = new ArrayList<HuoDongImages>();
								for (int j = 0; j < jsonArray.length(); j++) {
									HuoDongImages = new HuoDongImages();
									HuoDongImages
											.setImage(
													ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
															ImageControl.getHttpBitmap(
																	jsonArray.getJSONObject(0).getString("fullurl")),
													5));
									HuoDongImagesBeans.add(HuoDongImages);
								}
								HuoDongXiangQingDongTaiBean.setImages(gson.toJson(HuoDongImagesBeans));
							}
						} catch (JSONException e) {
							HuoDongXiangQingDongTaiBean.setImages(null);
						}
						HuoDongXiangQingDongTaiBeansTemp.add(HuoDongXiangQingDongTaiBean);
					}
				}
				huoDongXiangQingLeader.setHistory(gson.toJson(HuoDongXiangQingDongTaiBeansTemp));

				List<HuoDongXiangQingGongNue> HuoDongXiangQingItemBeansTemp = new ArrayList<HuoDongXiangQingGongNue>();
				if (HuoDongXiangQingItemBeans != null && HuoDongXiangQingItemBeans.size() != 0) {
					for (int i = 0; i < HuoDongXiangQingItemBeans.size(); i++) {
						HuoDongXiangQingItemBean = new HuoDongXiangQingGongNue();
						HuoDongXiangQingItemBean.setSchedu_desc(HuoDongXiangQingItemBeans.get(i).getSchedu_desc());
						HuoDongXiangQingItemBean.setSchedu_time(HuoDongXiangQingItemBeans.get(i).getSchedu_time());
						HuoDongXiangQingItemBean.setSchedu_title(HuoDongXiangQingItemBeans.get(i).getSchedu_title());
						try {
							JSONArray jsonArray = new JSONArray(HuoDongXiangQingItemBeans.get(i).getImgs());
							if (jsonArray != null && jsonArray.length() != 0) {
								HuoDongImagesBeans = new ArrayList<HuoDongImages>();
								for (int j = 0; j < jsonArray.length(); j++) {
									HuoDongImages = new HuoDongImages();
									HuoDongImages
											.setImage(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
													ImageControl.getHttpBitmap(jsonArray.getString(0)), 5));
									HuoDongImagesBeans.add(HuoDongImages);
								}
								HuoDongXiangQingItemBean.setImgs(gson.toJson(HuoDongImagesBeans));
							}
						} catch (JSONException e) {
							HuoDongXiangQingItemBean.setImgs(null);
						}
						HuoDongXiangQingItemBeansTemp.add(HuoDongXiangQingItemBean);
					}
				}
				huoDongXiangQingLeader.setAct_schedule_app(gson.toJson(HuoDongXiangQingItemBeansTemp));

				List<HuoDongDuiYuan> HuoDongDuiYuanBeansTemp = new ArrayList<HuoDongDuiYuan>();
				if (HuoDongDuiYuanBeans != null && HuoDongDuiYuanBeans.size() != 0) {
					for (int i = 0; i < HuoDongDuiYuanBeans.size(); i++) {
						HuoDongDuiYuanBean = new HuoDongDuiYuan();
						HuoDongDuiYuanBean.setUserid(HuoDongDuiYuanBeans.get(i).getHead());
						HuoDongDuiYuanBean.setHead(ImageControl.saveImageToGallery(HuoDongXiangQingActivity.this,
								ImageControl.getHttpBitmap(HuoDongDuiYuanBeans.get(i).getHead()), 5));
						HuoDongDuiYuanBeansTemp.add(HuoDongDuiYuanBean);
					}
				}
				huoDongXiangQingLeader.setTeam_member(gson.toJson(HuoDongDuiYuanBeansTemp));
				if (!isFinishing()) {
					if (huoDongXiangQingLeaderDao.queryAll() != null) {// 当数据库中数据不为空时删除数据
						huoDongXiangQingLeaderDao.deleteAll();
					}
					huoDongXiangQingLeaderDao.add(huoDongXiangQingLeader);
					App.getInstance().setLeaderHuoDongId(act_id);
					App.getInstance().setDown(true);
					if (isshowdialog()) {
						closedialog();
					}
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK14);
				}
			}
		}).start();
	}

	/**
	 * 数据库获取数据
	 */
	public void GetDaoLeader() {
		// 需要使用到 act_id，不然无法查找
		huoDongXiangQingLeaderList.clear();
		huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
		if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0) {
			act_id = huoDongXiangQingLeaderList.get(0).getAct_id();
			act_sponsor_id = huoDongXiangQingLeaderList.get(0).getAct_sponsor_id();
			act_title = huoDongXiangQingLeaderList.get(0).getAct_title();
			act_desc = huoDongXiangQingLeaderList.get(0).getAct_desc();
			act_desc_web = huoDongXiangQingLeaderList.get(0).getAct_desc_web();
			act_desc_intro = huoDongXiangQingLeaderList.get(0).getAct_desc_intro();
			act_level = huoDongXiangQingLeaderList.get(0).getAct_level();
			verify = huoDongXiangQingLeaderList.get(0).getVerify();
			act_type = huoDongXiangQingLeaderList.get(0).getAct_type();
			create_time = huoDongXiangQingLeaderList.get(0).getCreate_time();
			act_start_time = huoDongXiangQingLeaderList.get(0).getAct_start_time();
			act_end_time = huoDongXiangQingLeaderList.get(0).getAct_end_time();
			act_join_num_limit = huoDongXiangQingLeaderList.get(0).getAct_join_num_limit();
			act_fee_include = huoDongXiangQingLeaderList.get(0).getAct_fee_include();
			act_weixin = huoDongXiangQingLeaderList.get(0).getAct_weixin();
			act_base_equip = huoDongXiangQingLeaderList.get(0).getAct_base_equip();
			act_scenicspots = huoDongXiangQingLeaderList.get(0).getAct_scenicspots();
			trace_file_id = huoDongXiangQingLeaderList.get(0).getTrace_file_id();
			confirm_member = huoDongXiangQingLeaderList.get(0).getConfirm_member();
			man = huoDongXiangQingLeaderList.get(0).getMan();
			female = huoDongXiangQingLeaderList.get(0).getFemale();
			issignup = huoDongXiangQingLeaderList.get(0).getIssignup();
			describe = huoDongXiangQingLeaderList.get(0).getDescribe();
			club_id = huoDongXiangQingLeaderList.get(0).getClub_id();
			leader_id = huoDongXiangQingLeaderList.get(0).getLeader_id();
			trace_id = huoDongXiangQingLeaderList.get(0).getTrace_id();
			anchor_longitude = huoDongXiangQingLeaderList.get(0).getAnchor_longitude();
			trace_data = huoDongXiangQingLeaderList.get(0).getTrace_data();
			act_state = huoDongXiangQingLeaderList.get(0).getAct_state();
			anchor_latitude = huoDongXiangQingLeaderList.get(0).getAnchor_latitude();
			city = huoDongXiangQingLeaderList.get(0).getCity();
			epilogue = huoDongXiangQingLeaderList.get(0).getEpilogue();
			logistics = huoDongXiangQingLeaderList.get(0).getLogistics();
			club_name = huoDongXiangQingLeaderList.get(0).getClub_name();
			leader_name = huoDongXiangQingLeaderList.get(0).getLeader_name();
			act_budget = huoDongXiangQingLeaderList.get(0).getAct_budget();
			isReport = huoDongXiangQingLeaderList.get(0).getIsReport();
			act_venue_time = huoDongXiangQingLeaderList.get(0).getAct_venue_time();
			act_venue = huoDongXiangQingLeaderList.get(0).getAct_venue();
			isCollect = huoDongXiangQingLeaderList.get(0).getIsCollect();
			act_else = huoDongXiangQingLeaderList.get(0).getAct_else();
			name = huoDongXiangQingLeaderList.get(0).getName();
			picture_url = huoDongXiangQingLeaderList.get(0).getPicture_url();
			club_logo = huoDongXiangQingLeaderList.get(0).getClub_logo();
			act_2d_code = huoDongXiangQingLeaderList.get(0).getAct_2d_code();
			topImageDatas.clear();
			HuoDongXiangQingDongTaiBeans.clear();
			HuoDongXiangQingItemBeans.clear();
			HuoDongDuiYuanBeans.clear();
			if (!TextUtils.isEmpty(huoDongXiangQingLeaderList.get(0).getAct_logo())) {
				topImageDatas = gson.fromJson(huoDongXiangQingLeaderList.get(0).getAct_logo(),
						new TypeToken<List<HuoDongImages>>() {
						}.getType());
			}
			if (!TextUtils.isEmpty(huoDongXiangQingLeaderList.get(0).getAct_logo())) {
				HuoDongXiangQingDongTaiBeans = gson.fromJson(huoDongXiangQingLeaderList.get(0).getHistory(),
						new TypeToken<List<HuoDongXiangQingDongTai>>() {
						}.getType());
			}
			if (!TextUtils.isEmpty(huoDongXiangQingLeaderList.get(0).getAct_logo())) {
				HuoDongXiangQingItemBeans = gson.fromJson(huoDongXiangQingLeaderList.get(0).getAct_schedule_app(),
						new TypeToken<List<HuoDongXiangQingGongNue>>() {
						}.getType());
			}
			if (!TextUtils.isEmpty(huoDongXiangQingLeaderList.get(0).getAct_logo())) {
				HuoDongDuiYuanBeans = gson.fromJson(huoDongXiangQingLeaderList.get(0).getTeam_member(),
						new TypeToken<List<HuoDongDuiYuan>>() {
						}.getType());
			}
			handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
		}
	}

	/**
	 * 数据的初始化
	 */
	public void initDao() {
		gson = new Gson();
		mDatas = new ArrayList<HuoDongXiangQingItem>();
		topImageDatas = new ArrayList<HuoDongImages>();
		HuoDongXiangQingDongTaiBeans = new ArrayList<HuoDongXiangQingDongTai>();
		HuoDongXiangQingItemBeans = new ArrayList<HuoDongXiangQingGongNue>();
		HuoDongDuiYuanBeans = new ArrayList<HuoDongDuiYuan>();
		HistoryList = new ArrayList<GetActHistoryListHistorylistItem>();
		huoDongXiangQingDuiyuanList = new ArrayList<HuoDongXiangQingItem>();
		huoDongXiangQingLeaderList = new ArrayList<HuoDongXiangQingLeader>();
		topImageDatas_ViewPager = new ArrayList<HuoDongImages>();
		huoDongXiangQing = new HuoDongXiangQingItem();
		huoDongXiangQingDuiyuanDao = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQing, mContext);
		huoDongXiangQingDuiyuanList = huoDongXiangQingDuiyuanDao.queryAll();
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader, mContext);
		huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
	}

	@Override
	protected void onInitData() {
		act_id = getIntent().getExtras().getString("act_id", "");
		initDao();
		initHeaderOtherHuoDong();
		initPopwindow();
		initializeHeaderAndFooter();
		type = (TextView) findViewById(R.id.type);
		type_view = (LinearLayout) findViewById(R.id.type_view);
		showBotton = getIntent().getIntExtra("fromrenwu", 0);
		showdialog();
		if (!TextUtils.isEmpty(act_id)) {
			if (NetUtil.isNetworkAvailable(mContext)) {
				sendShareOptionActionAsyn();
				sendGetActivityListActionAsyn();
			} else {
				if (showBotton == CommonUtility.XianShiTab_RenWu) {
					GetDao();
				} else if (showBotton == CommonUtility.XianShiTab_Leader_NOW) {
					GetDaoLeader();
				}
			}
		}
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("活动详情");
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFirst && !TextUtils.isEmpty(App.getInstance().getAut()) && NetUtil.isNetworkAvailable(mContext)) {
			if (showBotton == CommonUtility.XianShiTab_SheZhi) {
				// 验证地图成功
				showdialog();
				if (act_state == 2) {
					if (App.getInstance().getGuiJi()) {
						sendSetActivitystateActionAsyn();
					} else {
						sendGetActivityListActionAsyn();
					}
				} else if (act_state == 5) {
					sendGetActivityListActionAsyn();
				} else {
					sendGetActivityListActionAsyn();
				}
			} else if (showBotton == CommonUtility.XianShiTab_False) {
				showdialog();
				sendGetActivityListActionAsyn();
			}
		}
	}

	@Override
	protected void setMyViewClick() {
		iv_map.setOnClickListener(this);
		tv15.setOnClickListener(this);
		tv16.setOnClickListener(this);
		huodong_viewpager.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (huodong_viewpager_view.getVisibility() == View.VISIBLE) {
					huodong_viewpager_view.setVisibility(View.GONE);
				}
			}
		});
	}

	// 活动介绍
	private final String TYPE_DongTai_Str = "实时动态";
	private final String TYPE_LiShiDongTai_Str = "历史动态";
	// 活动介绍
	private final String TYPE_Des_Str = "活动介绍";
	// 最新动态
	private final String TYPE_Raiders_Str = "行程安排";
	// 活动费用
	private final String TYPE_Cost_Str = "活动费用";
	// 装备要求
	private final String TYPE_Equipment_Str = "装备要求";
	// 报名须知
	private final String TYPE_Logistics_Str = "报名须知";
	// 报名须知
	private final String TYPE_QiTa_Str = "其他";
	// 队伍状况
	private final String TYPE_Status_Str = "队伍状况";

	/**
	 * @author 曾金叶
	 * @2015-8-6 @上午9:58:49
	 * @category adapter ,写在本activity，不用分出来
	 */
	public class MyListAdpter extends BaseAdapter {
		private LayoutInflater mInflater = null;
		private List<HuoDongXiangQingItem> mDatas = null;
		// pager上的数据
		private AdapterCycle adapterCycle = null;
		private Context context = null;
		private int mstate = 0;

		public MyListAdpter(Context context, List<HuoDongXiangQingItem> mData, int state) {
			this.mDatas = mData;
			this.context = context;
			this.mInflater = LayoutInflater.from(context);
			this.mstate = state;
		}

		@Override
		public int getCount() {
			return mDatas.size() != 0 ? mDatas.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// 每个convert
		// view都会调用此方法，获得当前所需要的view样式,初始化的时候会调用，初始化不同的convertView视图，
		// 方便调用不同的视图的不同的控件，不然的话，只会默认初始化第一个convertView保存第一个convertView
		@Override
		public int getItemViewType(int position) {
			if (mstate == 4 || mstate == 5 || mstate == 6) {
				switch (mDatas.get(position).getType()) {
				case 0:
					return 0;
				case 1:
					return 1;
				case 2:
					return 2;
				case 3:
					return 3;
				case 4:
					return 4;
				case 5:
					return 5;
				case 6:
					return 6;
				case 7:
					return 7;
				}
				return -1;
			} else {
				switch (mDatas.get(position).getType()) {
				case 0:
					return 0;
				case 1:
					return 1;
				case 2:
					return 2;
				case 3:
					return 3;
				case 4:
					return 4;
				case 5:
					return 5;
				case 6:
					return 6;
				}
				return -1;
			}
		}

		@Override
		public int getViewTypeCount() {
			if (mstate == 4 || mstate == 5 || mstate == 6) {
				return 9;
			} else {
				return 8;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HuoDongXiangQingItem news = mDatas.get(position); // 获取当前项数据
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				if (mstate == 4 || mstate == 5 || mstate == 6) {
					switch (news.getType()) {
					case 0:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typeraiders, null);
						holder.typeraiders_listview = (MyListView) convertView.findViewById(R.id.typeraiders_listview);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 1:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typepic, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						// 头部
						holder.viewPager = (MyViewPager) convertView.findViewById(R.id.vp_home);
						holder.viewPager.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
								App.getInstance().getScreenHeight() / 3));
						holder.indicatorLayout = (LinearLayout) convertView.findViewById(R.id.indicator);
						break;
					case 2:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typeraiders, null);
						holder.typeraiders_listview = (MyListView) convertView.findViewById(R.id.typeraiders_listview);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 3:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 4:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 5:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 6:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 7:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typestatus, null);
						holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
						holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
						holder.tv_3 = (TextView) convertView.findViewById(R.id.tv_3);
						holder.tv_4 = (TextView) convertView.findViewById(R.id.tv_4);
						holder.tv_5 = (TextView) convertView.findViewById(R.id.tv_5);
						holder.tv_6 = (TextView) convertView.findViewById(R.id.tv_6);
						holder.tv_7 = (ImageView) convertView.findViewById(R.id.tv_7);
						holder.tv_8 = (TextView) convertView.findViewById(R.id.tv_8);
						holder.tv_9 = (GridView) convertView.findViewById(R.id.tv_9);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					default:
						break;
					}
				} else {
					switch (news.getType()) {
					case 0:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typepic, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						// 头部
						holder.viewPager = (MyViewPager) convertView.findViewById(R.id.vp_home);
						holder.viewPager.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
								App.getInstance().getScreenHeight() / 3));
						holder.indicatorLayout = (LinearLayout) convertView.findViewById(R.id.indicator);
						break;
					case 1:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typeraiders, null);
						holder.typeraiders_listview = (MyListView) convertView.findViewById(R.id.typeraiders_listview);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 2:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 3:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 4:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 5:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
						holder.content = (TextView) convertView.findViewById(R.id.content);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					case 6:
						convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typestatus, null);
						holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
						holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
						holder.tv_3 = (TextView) convertView.findViewById(R.id.tv_3);
						holder.tv_4 = (TextView) convertView.findViewById(R.id.tv_4);
						holder.tv_5 = (TextView) convertView.findViewById(R.id.tv_5);
						holder.tv_6 = (TextView) convertView.findViewById(R.id.tv_6);
						holder.tv_7 = (ImageView) convertView.findViewById(R.id.tv_7);
						holder.tv_8 = (TextView) convertView.findViewById(R.id.tv_8);
						holder.tv_9 = (GridView) convertView.findViewById(R.id.tv_9);
						holder.type = (TextView) convertView.findViewById(R.id.type);
						break;
					default:
						break;
					}
				}
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (null != news) {
				if (mstate == 4 || mstate == 5 || mstate == 6) {
					switch (news.getType()) {
					case 0:
						if (act_state == 4) {
							holder.type.setText(TYPE_DongTai_Str);
						} else {
							holder.type.setText(TYPE_LiShiDongTai_Str);
						}
						if (news.getHistoryList() != null && news.getHistoryList().size() != 0) {
							holder.typeraiders_listview.setAdapter(new CommonAdapter<HuoDongXiangQingDongTai>(context,
									news.getHistoryList(), R.layout.item_huodong_dongtai) {

								@Override
								public void convert(ViewHolderUntil helper, final HuoDongXiangQingDongTai item,
										int position) {
									helper.setText(R.id.dongtai_time,
											TimeDateUtils.formatDateFromDatabaseTimeSF(item.getArrive_time()));
									if (position == 0) {
										helper.getView(R.id.dongtai_zuixin).setVisibility(View.VISIBLE);
									} else {
										helper.getView(R.id.dongtai_zuixin).setVisibility(View.GONE);
									}
									helper.setText(R.id.dongtai_des, item.getDescript());
									try {
										JSONArray jsonArray = new JSONArray(item.getImages());
										if (jsonArray != null) {
											switch (jsonArray.length()) {
											case 1:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("fullurl"),
															imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon2).setVisibility(View.INVISIBLE);
												helper.getView(R.id.icon3).setVisibility(View.INVISIBLE);
												helper.getView(R.id.icon4).setVisibility(View.INVISIBLE);
												helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
												break;
											case 2:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("fullurl"),
															imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon3).setVisibility(View.INVISIBLE);
												helper.getView(R.id.icon4).setVisibility(View.INVISIBLE);
												helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
												break;
											case 3:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon3,
															jsonArray.getJSONObject(2).getString("fullurl"),
															imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon4).setVisibility(View.INVISIBLE);
												helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
												break;
											case 4:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon3,
															jsonArray.getJSONObject(2).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon4,
															jsonArray.getJSONObject(3).getString("fullurl"),
															imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon4,
															jsonArray.getJSONObject(3).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon4).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
												break;
											case 5:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon3,
															jsonArray.getJSONObject(2).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon4,
															jsonArray.getJSONObject(3).getString("fullurl"),
															imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.icon5,
															jsonArray.getJSONObject(4).getString("fullurl"),
															imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon4,
															jsonArray.getJSONObject(3).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.icon5,
															jsonArray.getJSONObject(4).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon4).setVisibility(View.VISIBLE);
												helper.getView(R.id.icon5).setVisibility(View.VISIBLE);
												break;

											default:
												break;
											}
										}
									} catch (JSONException e) {
									}
									helper.getView(R.id.icon1).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 0;
											showViewPage(null, item.getImages(), "", 1);
										}
									});
									helper.getView(R.id.icon2).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 1;
											showViewPage(null, item.getImages(), "", 1);
										}
									});
									helper.getView(R.id.icon3).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 2;
											showViewPage(null, item.getImages(), "", 1);
										}
									});
									helper.getView(R.id.icon4).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 3;
											showViewPage(null, item.getImages(), "", 1);
										}
									});
									helper.getView(R.id.icon5).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 4;
											showViewPage(null, item.getImages(), "", 1);
										}
									});
								}
							});
						} else {
							holder.content.setVisibility(View.VISIBLE);
							holder.content.setText("无");
						}
						break;
					case 1:
						holder.type.setText(TYPE_Des_Str);
						holder.content.setText(Html.fromHtml(news.getAct_desc_web()));
						adapterCycle = new AdapterCycle(topImageDatas, holder.indicatorLayout);
						holder.viewPager.setAdapter(adapterCycle);
						// holder.viewPager.setCurrentItem(topImageDatas.size()
						// * 6);
						holder.viewPager.setOnPageChangeListener(adapterCycle);
						break;
					case 2:
						holder.type.setText(TYPE_Raiders_Str);
						if (news.getAct_schedule_apps() != null && news.getAct_schedule_apps().size() != 0) {
							holder.typeraiders_listview.setAdapter(new CommonAdapter<HuoDongXiangQingGongNue>(context,
									news.getAct_schedule_apps(), R.layout.item_huodong_gongnue) {
								@Override
								public void convert(ViewHolderUntil helper, final HuoDongXiangQingGongNue item,
										int position) {
									helper.setText(R.id.schedu_time, item.getSchedu_time());
									helper.setText(R.id.schedu_title, item.getSchedu_title());
									helper.setTexthtml(R.id.schedu_desc, item.getSchedu_desc());
									try {
										JSONArray jsonArray = new JSONArray(item.getImgs());
										if (jsonArray != null) {
											switch (jsonArray.length()) {
											case 1:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 2:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 3:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon3,
															jsonArray.getString(2), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 4:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon3,
															jsonArray.getString(2), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon4,
															jsonArray.getString(3), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon4,
															jsonArray.getJSONObject(3).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon4).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 5:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon3,
															jsonArray.getString(2), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon4,
															jsonArray.getString(3), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon5,
															jsonArray.getString(4), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon4,
															jsonArray.getJSONObject(3).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon5,
															jsonArray.getJSONObject(4).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon4).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon5).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.VISIBLE);
												break;

											default:
												break;
											}
										}
									} catch (Exception e) {
									}
									helper.getView(R.id.schedu_icon1).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 0;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon2).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 1;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon3).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 2;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon4).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 3;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon5).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 4;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
								}
							});
						} else {
							holder.content.setVisibility(View.VISIBLE);
							holder.content.setText("无");
						}
						break;
					case 3:
						holder.type.setText(TYPE_Cost_Str);
						holder.content.setText(news.getAct_fee_include());
						break;
					case 4:
						holder.type.setText(TYPE_Equipment_Str);
						holder.content.setText(news.getAct_base_equip());
						break;
					case 5://
						holder.type.setText(TYPE_Logistics_Str);
						holder.content.setText(news.getLogistics());
						break;
					case 6:
						holder.type.setText(TYPE_QiTa_Str);
						holder.content.setText(news.getAct_else());
						break;
					case 7:
						holder.type.setText(TYPE_Status_Str);
						holder.tv_1.setText(news.getAct_join_num_limit() + "");
						holder.tv_2.setText(news.getConfirm_member() + "");
						holder.tv_3.setText("");
						holder.tv_4.setText(news.getMan() + "");
						holder.tv_5.setText(news.getFemale() + "");
						holder.tv_6.setText(news.getAct_weixin());
						if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
							if (NetUtil.isNetworkAvailable(mContext)) {
								mImageLoader.displayImage(news.getAct_2d_code(), holder.tv_7, options);
							} else {
								mImageLoader.displayImage("file://" + news.getAct_2d_code(), holder.tv_7, options);
							}
						} else {
							mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, holder.tv_7, options);
						}
						holder.tv_8.setText(news.getLeader_name());
						if (news.getTeam_memberList() != null && news.getTeam_memberList().size() != 0) {

							holder.tv_9.setAdapter(new CommonAdapter<HuoDongDuiYuan>(HuoDongXiangQingActivity.this,
									news.getTeam_memberList(), R.layout.item_duiyuan) {
								@Override
								public void convert(ViewHolderUntil helper, final HuoDongDuiYuan item, int position) {
									if (NetUtil.isNetworkAvailable(mContext)) {
										helper.setCubeImageByUrlXQ(R.id.haoyou_icon, item.getHead(), imageLoader,
												mImageLoader);
									} else {
										helper.setCubeImageByUrlSDXQ(R.id.haoyou_icon, item.getHead(), mImageLoader);
									}
									helper.getView(R.id.haoyou_icon).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											if (NetUtil.isNetworkAvailable(mContext)) {
												Bundle bundle = new Bundle();
												bundle.putString("Userid", item.getUserid());
												openActivity(HaoYouDetailActivity.class, bundle);
												isFirst = true;
											} else {
												showToastNet();
											}
										}
									});
								}
							});
						}
						break;
					default:
						break;
					}
				} else {
					switch (news.getType()) {
					case 0:
						holder.type.setText(TYPE_Des_Str);
						holder.content.setText(Html.fromHtml(news.getAct_desc_web()));
						adapterCycle = new AdapterCycle(topImageDatas, holder.indicatorLayout);
						holder.viewPager.setAdapter(adapterCycle);
						// holder.viewPager.setCurrentItem(topImageDatas.size()
						// * 6);
						holder.viewPager.setOnPageChangeListener(adapterCycle);
						break;
					case 1:
						holder.type.setText(TYPE_Raiders_Str);
						if (news.getAct_schedule_apps() != null && news.getAct_schedule_apps().size() != 0) {
							holder.typeraiders_listview.setAdapter(new CommonAdapter<HuoDongXiangQingGongNue>(context,
									news.getAct_schedule_apps(), R.layout.item_huodong_gongnue) {
								@Override
								public void convert(ViewHolderUntil helper, final HuoDongXiangQingGongNue item,
										int position) {
									helper.setText(R.id.schedu_time, item.getSchedu_time());
									helper.setText(R.id.schedu_title, item.getSchedu_title());
									helper.setTexthtml(R.id.schedu_desc, item.getSchedu_desc());
									try {
										JSONArray jsonArray = new JSONArray(item.getImgs());
										if (jsonArray != null) {
											switch (jsonArray.length()) {
											case 1:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 2:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 3:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon3,
															jsonArray.getString(2), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.GONE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 4:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon3,
															jsonArray.getString(2), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon4,
															jsonArray.getString(3), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon4,
															jsonArray.getJSONObject(3).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon4).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.GONE);
												break;
											case 5:
												if (NetUtil.isNetworkAvailable(mContext)) {
													helper.setCubeImageByUrlXQ(R.id.schedu_icon1,
															jsonArray.getString(0), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon2,
															jsonArray.getString(1), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon3,
															jsonArray.getString(2), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon4,
															jsonArray.getString(3), imageLoader, mImageLoader);
													helper.setCubeImageByUrlXQ(R.id.schedu_icon5,
															jsonArray.getString(4), imageLoader, mImageLoader);
												} else {
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon1,
															jsonArray.getJSONObject(0).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon2,
															jsonArray.getJSONObject(1).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon3,
															jsonArray.getJSONObject(2).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon4,
															jsonArray.getJSONObject(3).getString("image"),
															mImageLoader);
													helper.setCubeImageByUrlSDXQ(R.id.schedu_icon5,
															jsonArray.getJSONObject(4).getString("image"),
															mImageLoader);
												}
												helper.getView(R.id.schedu_icon1).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon2).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon3).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon4).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon5).setLayoutParams(
														new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
																App.getInstance().getScreenHeight() / 3));
												helper.getView(R.id.schedu_icon1).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon2).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon3).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon4).setVisibility(View.VISIBLE);
												helper.getView(R.id.schedu_icon5).setVisibility(View.VISIBLE);
												break;

											default:
												break;
											}
										}
									} catch (Exception e) {
									}
									helper.getView(R.id.schedu_icon1).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 0;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon2).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 1;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon3).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 2;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon4).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 3;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
									helper.getView(R.id.schedu_icon5).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											downid_viewpager = 4;
											showViewPage(null, item.getImgs(), "", 4);
										}
									});
								}
							});
						} else {
							holder.content.setVisibility(View.VISIBLE);
							holder.content.setText("无");
						}
						break;
					case 2:
						holder.type.setText(TYPE_Cost_Str);
						holder.content.setText(news.getAct_fee_include());
						break;
					case 3:
						holder.type.setText(TYPE_Equipment_Str);
						holder.content.setText(news.getAct_base_equip());
						break;
					case 4://
						holder.type.setText(TYPE_Logistics_Str);
						holder.content.setText(news.getLogistics());
						break;
					case 5:
						holder.type.setText(TYPE_QiTa_Str);
						holder.content.setText(news.getAct_else());
						break;
					case 6:
						holder.type.setText(TYPE_Status_Str);
						holder.tv_1.setText(news.getAct_join_num_limit() + "");
						holder.tv_2.setText(news.getConfirm_member() + "");
						holder.tv_3.setText("");
						holder.tv_4.setText(news.getMan() + "");
						holder.tv_5.setText(news.getFemale() + "");
						holder.tv_6.setText(news.getAct_weixin());
						if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
							if (NetUtil.isNetworkAvailable(mContext)) {
								mImageLoader.displayImage(news.getAct_2d_code(), holder.tv_7, options);
							} else {
								mImageLoader.displayImage("file://" + news.getAct_2d_code(), holder.tv_7, options);
							}
						} else {
							mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, holder.tv_7, options);
						}
						holder.tv_8.setText(news.getLeader_name());
						if (news.getTeam_memberList() != null && news.getTeam_memberList().size() != 0) {
							holder.tv_9.setAdapter(new CommonAdapter<HuoDongDuiYuan>(HuoDongXiangQingActivity.this,
									news.getTeam_memberList(), R.layout.item_duiyuan) {
								@Override
								public void convert(ViewHolderUntil helper, final HuoDongDuiYuan item, int position) {
									if (NetUtil.isNetworkAvailable(mContext)) {
										helper.setCubeImageByUrlXQ(R.id.haoyou_icon, item.getHead(), imageLoader,
												mImageLoader);
									} else {
										helper.setCubeImageByUrlSDXQ(R.id.haoyou_icon, item.getHead(), mImageLoader);
									}
									helper.getView(R.id.haoyou_icon).setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											if (NetUtil.isNetworkAvailable(mContext)) {
												Bundle bundle = new Bundle();
												bundle.putString("Userid", item.getUserid());
												openActivity(HaoYouDetailActivity.class, bundle);
												isFirst = true;
											} else {
												showToastNet();
											}
										}
									});
								}
							});
						}
						break;
					default:
						break;
					}
				}
			}
			return convertView;
		}

		public class ViewHolder {
			TextView content;// 内容
			TextView type;// 类型，显示在每一个item的最上面
			ImageView image;
			MyListView lv;// 时间轴
			// 队伍状况
			TextView tv_1;
			TextView tv_2;
			TextView tv_3;
			TextView tv_4;
			TextView tv_5;
			TextView tv_6;
			ImageView tv_7;
			TextView tv_8;
			GridView tv_9;
			// pager
			PagerAdapter pagerAdapter = null;
			MyViewPager viewPager = null;
			LinearLayout indicatorLayout = null;
			MyListView typeraiders_listview = null;
		}

		public class AdapterCycle extends PagerAdapter implements ViewPager.OnPageChangeListener {
			private ArrayList<View> mViews = null;
			private List<HuoDongImages> mList;
			private LinearLayout indicatorLayout = null;
			private ImageView[] indicators = null;
			private int currentItem = 0;

			public AdapterCycle(List<HuoDongImages> list, LinearLayout layout) {
				this.mList = list;
				this.indicatorLayout = layout;
				mViews = new ArrayList<View>();
				// 针对viwpager中的图片，每次刷新需清空这三项
				if (indicatorLayout != null) {
					indicatorLayout.removeAllViews();
				}
				if (mList != null && mList.size() != 0) {
					indicators = new ImageView[mList.size()]; // 定义指示器数组大小
					for (int i = 0; i < mList.size(); i++) {
						ImageView imageView = new ImageView(getApplicationContext());
						if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
							if (NetUtil.isNetworkAvailable(mContext)) {
								mImageLoader.displayImage(mList.get(i).getImage(), imageView, options);
							} else {
								mImageLoader.displayImage("file://" + mList.get(i).getImage(), imageView, options);
							}
						} else {
							mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, imageView, options);
						}
						mViews.add(imageView);
						// 循环加入指示器
						indicators[i] = new ImageView(getApplicationContext());
						indicators[i].setBackgroundResource(R.drawable.point_s);
						if (i == 0) {
							indicators[i].setBackgroundResource(R.drawable.point_n);
						}
						indicatorLayout.addView(indicators[i]);
					}
				}
			}

			@Override
			public int getCount() {
				return mList.size() <= 1 ? mList.size() : Integer.MAX_VALUE;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
			}

			@Override
			public Object instantiateItem(ViewGroup container, final int position) {
				int index = position;
				index %= mViews.size();
				if (index < 0) {
					index = mViews.size() + index;
				}
				ImageView view = (ImageView) mViews.get(index);
				view.setScaleType(ScaleType.CENTER_CROP);
				ViewParent vp = view.getParent();
				if (vp != null) {
					ViewGroup parent = (ViewGroup) vp;
					parent.removeView(view);
				}
				((ViewGroup) container).addView(view);
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						downid_viewpager = position;
						showViewPage(topImageDatas, null, "", 0);
					}
				});
				return view;
			}

			// 实现ViewPager.OnPageChangeListener接口
			@Override
			public void onPageSelected(int position) {
				// 记录当前的页号，避免播放的时候页面显示不正确。
				currentItem = position;
				int currentItemTem = currentItem;
				currentItemTem %= indicators.length;
				if (currentItemTem < 0) {
					currentItemTem = indicators.length + currentItemTem;
				}
				for (int i = 0; i < indicators.length; i++) {
					indicators[currentItemTem].setBackgroundResource(R.drawable.point_n);
					if (currentItemTem != i) {
						indicators[i].setBackgroundResource(R.drawable.point_s);
					}
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		}
	}

	public void getTopImageData() {
		topImageDatas.clear();
		try {
			JSONArray array = new JSONArray(act_logo);
			for (int i = 0; i < array.length(); i++) {
				topImage = new HuoDongImages();
				topImage.setImage(array.get(i).toString());
				topImageDatas.add(topImage);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private Thread thread = null;

	// 发送请求到服务器，异步处理模式，通过重载回调函数处理各种返回
	public void sendGetActivityListActionAsyn() {
		thread = new Thread() {
			public void run() {
				getActivityListAction();
			}
		};
		thread.start();
	}

	private String act_id = null;// 活动id
	private String act_sponsor_id = null;// 创建人id
	private String act_title = null;// 活动标题
	private String act_desc = null;// 活动描述
	private String act_desc_web = null;// 活动描述
	private String act_desc_intro = null;// 活动简介
	private Integer act_level = null;// 活动级别
	private Integer act_state = 0;// 活动状态：活动状态：0为审核中，1为报名中，2为整队中，3为出行中，4为待点评，5为结束，-1为取消
	private Integer verify = null;// 审核状态：-1审核不通过，0待审核，1审核通过
	private String act_type = null;// 活动类型：如登山
	private String club_name = null;// 活动所属俱乐部
	private String leader_name = null;// 活动所属领队
	private String create_time = null;// 活动创建时间
	private String act_start_time = null;// 活动开始时间
	private String act_end_time = null;// 活动结束时间
	private Integer act_join_num_limit = null;// 参加人数限制
	private String act_fee_include = null;// 活动费用包含
	private String act_weixin = null;// 活动微信号
	private String act_base_equip = null;// 参加活动准备基本装备
	private String logistics = null;// 后勤
	private String act_scenicspots = null;// 景点
	private String epilogue = null;// 结语点评
	private Integer trace_file_id = null;// 轨迹文件id
	private Integer confirm_member = null;// 确认队员人数
	private Integer man = null;// 男性人数
	private Integer female = null;// ,女性人数
	private Integer issignup = null;// ,是否报名，0为未报名，1为已报名，备注：情况一，如果用户未登录情况下，为0，如果用户登录状态下则查询队伍表看是否报名
	private double act_budget = 0;
	private Integer isReport = 0;
	private String act_venue_time = "";
	private String act_venue = "";
	private Integer isCollect = null;
	private String act_else = null;
	private String picture_url = null;// 截图路径
	private String describe = null;// 对轨迹描述
	private String club_id = null;// 所属俱乐部
	private String club_logo = null;// 所属俱乐部log
	private String leader_id = null;// 所属领队id
	private String trace_id = null;// 轨迹id
	private String anchor_longitude = null;// 锚点经度
	private String name = null;// 轨迹名称
	private String trace_data = null;
	private String anchor_latitude = null;// 锚点纬度
	private String city = null;// 离线地图包城市
	private String act_2d_code = "";// 二维码
	private String act_schedule_app = null;// 攻略
	private String act_logo = null;// 活动logo图
	private String reason = null;// 审核失败原因
	private int isEvaluateAct = 0;// 是否点评 0 否 1 是
	private int isConfirmed = 0;// 是否被领队确认 0 否 1 是
	private String getString = null;
	private String leader_head = null;// 领队头像

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
				getString = responseObject.getString("msg");
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
					isReport = payload.getIsReport();
					act_sponsor_id = payload.getAct_sponsor_id();
					act_title = payload.getAct_title();
					act_desc = payload.getAct_desc();
					act_desc_web = payload.getAct_desc_web();
					act_desc_intro = payload.getAct_desc_intro();
					act_logo = payload.getAct_logo();
					act_level = payload.getAct_level();
					act_state = payload.getAct_state();
					verify = payload.getVerify();
					act_type = payload.getAct_type();
					club_name = payload.getClub_name();
					leader_name = payload.getLeader_name();
					create_time = payload.getCreate_time();
					act_start_time = payload.getAct_start_time();
					act_end_time = payload.getAct_end_time();
					act_join_num_limit = payload.getAct_join_num_limit();
					act_fee_include = payload.getAct_fee_include();
					act_weixin = payload.getAct_weixin();
					act_base_equip = payload.getAct_base_equip();
					logistics = payload.getLogistics();
					act_scenicspots = payload.getAct_scenicspots();
					epilogue = payload.getEpilogue();
					trace_file_id = payload.getTrace_file_id();
					confirm_member = payload.getConfirm_member();
					leader_id = payload.getLeader_id();
					man = payload.getMan();
					female = payload.getFemale();
					isCollect = payload.getIsCollect();
					issignup = payload.getIssignup();
					act_2d_code = payload.getAct_2d_code();
					act_venue = payload.getAct_venue();
					act_venue_time = payload.getAct_venue_time();
					isReport = payload.getIsReport();
					act_budget = payload.getAct_budget();
					act_schedule_app = payload.getAct_schedule_web();
					act_logo = payload.getAct_logo();
					club_logo = payload.getClub_logo();
					act_else = payload.getAct_else();
					reason = payload.getReason();
					leader_head = payload.getLeader_head();
					isEvaluateAct = payload.getIsEvaluateAct() != null ? payload.getIsEvaluateAct() : 0;
					isConfirmed = payload.getIsConfirmed() != null ? payload.getIsConfirmed() : 0;
					club_id = payload.getClub_id();
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

	// 活动报名
	public void sendBaoMingActionAsyn() {
		thread = new Thread() {
			public void run() {
				getEnrollActivityAction();
			}
		};
		thread.start();
	}

	private String msgg = null;
	private int state = 0;

	public void getEnrollActivityAction() {
		EnrollActivityRequest request = new EnrollActivityRequest();
		EnrollActivityRequestParameter parameter = new EnrollActivityRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setAct_id(act_id);
		parameter.setIs_equipment(zhuangbei);
		parameter.setIs_experience(jingyan);
		parameter.setMember_count(renshu);
		parameter.setRemark("");
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
					EnrollActivityResponse response = new EnrollActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					EnrollActivityResponsePayload payload = (EnrollActivityResponsePayload) response.getPayload();
					msgg = payload.getMsg();
					state = payload.getState();
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

	// 取消活动报名
	public void sendCancelBaoMingActionAsyn() {
		thread = new Thread() {
			public void run() {
				getCancelEnrollActivityAction();
			}
		};
		thread.start();
	}

	// 设置活动状态
	public void sendSetActivitystateActionAsyn() {
		thread = new Thread() {
			public void run() {
				SetActivitystateAction();
			}
		};
		thread.start();
	}

	public void SetActivitystateAction() {
		SetActivitystateRequest request = new SetActivitystateRequest();
		SetActivitystateRequestParameter parameter = new SetActivitystateRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setAct_id(act_id);
		parameter.setState(act_state + 1);
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
				getString = responseObject.getString("msg");
				if (ret == 0) {
					SetActivitystateResponse response = new SetActivitystateResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					SetActivitystateResponsePayload payload = (SetActivitystateResponsePayload) response.getPayload();
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	public void sendShanChuAsyn() {
		thread = new Thread() {
			public void run() {
				shanChuAction();
			}
		};
		thread.start();
	}

	public void shanChuAction() {
		LeaderDeleteActivityRequest request = new LeaderDeleteActivityRequest();
		LeaderDeleteActivityRequestParameter parameter = new LeaderDeleteActivityRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					LeaderDeleteActivityResponse response = new LeaderDeleteActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					LeaderDeleteActivityResponsePayload payload = (LeaderDeleteActivityResponsePayload) response
							.getPayload();
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

	public void sendShanChuDuiYuanAsyn() {
		thread = new Thread() {
			public void run() {
				shanChuDuiYuanAction();
			}
		};
		thread.start();
	}

	public void shanChuDuiYuanAction() {
		DeleteMyTroopActivityRequest request = new DeleteMyTroopActivityRequest();
		DeleteMyTroopActivityRequestParameter parameter = new DeleteMyTroopActivityRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					DeleteMyTroopActivityResponse response = new DeleteMyTroopActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					DeleteMyTroopActivityResponsePayload payload = (DeleteMyTroopActivityResponsePayload) response
							.getPayload();
					state = payload.getState();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK13);
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

	public void getCancelEnrollActivityAction() {
		CancelEnrollActivityRequest request = new CancelEnrollActivityRequest();
		CancelEnrollActivityRequestParameter parameter = new CancelEnrollActivityRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					CancelEnrollActivityResponse response = new CancelEnrollActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					CancelEnrollActivityResponsePayload payload = (CancelEnrollActivityResponsePayload) response
							.getPayload();
					state = payload.getState() != null ? payload.getState() : 1;
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

	// 收藏活动
	public void sendCollectionActivityAsyn() {
		thread = new Thread() {
			public void run() {
				getCollectionActivityAction();
			}
		};
		thread.start();
	}

	public void getCollectionActivityAction() {
		CollectionActivityRequest request = new CollectionActivityRequest();
		CollectionActivityRequestParameter parameter = new CollectionActivityRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					CollectionActivityResponse response = new CollectionActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					CollectionActivityResponsePayload payload = (CollectionActivityResponsePayload) response
							.getPayload();
					state = payload.getState();
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

	// 取消收藏活动
	public void sendCancelCollectionActivityAsyn() {
		thread = new Thread() {
			public void run() {
				getCancelCollectionActivityAction();
			}
		};
		thread.start();
	}

	public void getCancelCollectionActivityAction() {
		CancelCollectionActivityRequest request = new CancelCollectionActivityRequest();
		CancelCollectionActivityRequestParameter parameter = new CancelCollectionActivityRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					CancelCollectionActivityResponse response = new CancelCollectionActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					CancelCollectionActivityResponsePayload payload = (CancelCollectionActivityResponsePayload) response
							.getPayload();
					state = payload.getState();
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	private List<GetActHistoryListHistorylistItem> HistoryList = null;

	// 取得历史活动轨迹
	public void sendActHistoryListAsyn() {
		thread = new Thread() {
			public void run() {
				getActHistoryListAction();
			}
		};
		thread.start();
	}

	public void getActHistoryListAction() {
		GetActHistoryListRequest request = new GetActHistoryListRequest();
		GetActHistoryListRequestParameter parameter = new GetActHistoryListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					GetActHistoryListResponse response = new GetActHistoryListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetActHistoryListResponsePayload payload = (GetActHistoryListResponsePayload) response.getPayload();
					this.HistoryList = payload.getHistorylistList();
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	// 根据轨迹id取得轨迹数据
	public void sendGetTravelInfoByIDAsyn() {
		thread = new Thread() {
			public void run() {
				getGetTravelInfoByIDAction();
			}
		};
		thread.start();
	}

	public void getGetTravelInfoByIDAction() {
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
					picture_url = payload.getPicture_url();
					describe = payload.getDescribe();
					trace_id = payload.getTrace_id();
					anchor_longitude = payload.getAnchor_longitude();
					name = payload.getName();
					trace_data = payload.getTrace_data();
					anchor_latitude = payload.getAnchor_latitude();
					city = payload.getCity();
					state = payload.getState();
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	private ArrayList<GetActivityTeamMemberResponsePayloadItem> team_memberList = null;

	// 取得活动下的所有队员名称
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	private String ShareUrl = null;

	// 获取分享链接
	public void sendShareOptionActionAsyn() {
		thread = new Thread() {
			public void run() {
				ShareOptionAction();
			}
		};
		thread.start();
	}

	public void ShareOptionAction() {
		ShareOptionRequest request = new ShareOptionRequest();
		ShareOptionRequestParameter parameter = new ShareOptionRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setId(act_id);
		parameter.setType(1);
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
					ShareOptionResponse response = new ShareOptionResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					ShareOptionResponsePayload payload = (ShareOptionResponsePayload) response.getPayload();
					ShareUrl = payload.getShare_url();
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

	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				// 判断是否是自己创建的活动
				if (!TextUtils.isEmpty(leader_id) && TextUtils.equals(leader_id, App.getInstance().getUserid())
						&& showBotton != CommonUtility.XianShiTab_RenWu && showBotton != CommonUtility.XianShiTab_Leader
						&& showBotton != CommonUtility.XianShiTab_Leader_NOW) {
					showBotton = CommonUtility.XianShiTab_SheZhi;
				}
				if (NetUtil.isNetworkAvailable(mContext)) {
					// 图片数据先下载下来
					getTopImageData();
					sendGetTravelInfoByIDAsyn();
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK6);
				}
				break;
			case CommonUtility.SERVEROK2:// 报名
				showToast(msgg);
				if (isshowdialog()) {
					closedialog();
				}
				popContact.dismiss();
				if (state == 5) {
					openActivity(GenRenZiLiaoActivity.class);
					renshu = 1;
					zhuangbei = 3;
					jingyan = 3;
					pop_huodong_check1.setVisibility(View.GONE);
					pop_huodong_check2.setVisibility(View.GONE);
					pop_huodong_check3.setVisibility(View.GONE);
					pop_huodong_check4.setVisibility(View.GONE);
					pop_huodong_check1_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					pop_huodong_check2_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					pop_huodong_check3_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					pop_huodong_check4_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					isFirst = true;
				} else if (state == 1) {
					huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_baomingzhong, 0, 0,
							0);
					huodong_select_one.setText("已报名");
					huodong_select_two.setText("取消报名");
					bm = false;
				}
				break;
			case CommonUtility.SERVEROK3:// 收藏
				switch (state) {
				case 0:
					showToast("收藏失败");
				case 1:
					top_tv_shoucang.setBackgroundResource(R.drawable.bt_shoucang_d);
					showToast("收藏成功");
					sc = false;
					break;
				case 2:
					showToast("已经收藏");
					break;
				}
				break;
			case CommonUtility.SERVEROK4:// 取消收藏
				switch (state) {
				case 0:
					showToast("取消收藏失败");
					break;
				case 1:
					showToast("取消收藏成功");
					top_tv_shoucang.setBackgroundResource(R.drawable.select_bt_shoucang);
					sc = true;
					break;
				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK5:// 取消报名
				switch (state) {
				case 0:
					showToast("取消报名失败");
					break;
				case 1:
					bm = true;
					renshu = 1;
					zhuangbei = 3;
					jingyan = 3;
					pop_huodong_geshu.setText(renshu + "");
					pop_huodong_check1.setVisibility(View.GONE);
					pop_huodong_check2.setVisibility(View.GONE);
					pop_huodong_check3.setVisibility(View.GONE);
					pop_huodong_check4.setVisibility(View.GONE);
					pop_huodong_check1_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					pop_huodong_check2_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					pop_huodong_check3_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					pop_huodong_check4_text.setBackgroundResource(R.drawable.duihuan_pop_ground_n);
					huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_baomingzhong, 0, 0,
							0);
					huodong_select_one.setText("报名中");
					huodong_select_two.setText("报名参加");
					showToast("取消报名成功");
					break;
				default:
					showToast("取消报名失败");
					break;
				}
				break;
			case CommonUtility.SERVEROK7:// 设置活动状态
				// 重新获取活动信息
				sendGetActivityListActionAsyn();
				break;
			case CommonUtility.SERVEROK6:// 根据轨迹id取得轨迹数据
				switch (showBotton) {
				case CommonUtility.XianShiTab_False:
					switch (act_state) {
					case 1:
						top_tv_right.setVisibility(View.GONE);
						switch (issignup) {// 是否报名 0否 1报名
						case 0:
							initFooterOtherHuoDong("报名中", "报名参加", "", false, 1);
							top_tv_view.setVisibility(View.VISIBLE);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_baomingzhong,
									0, 0, 0);
							bm = true;
							break;
						case 1:
							initFooterOtherHuoDong("已报名", "取消报名", "", false, 1);
							top_tv_view.setVisibility(View.VISIBLE);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_baomingzhong,
									0, 0, 0);
							bm = false;
							break;

						default:
							break;
						}
						break;
					case 2:
						top_tv_right.setVisibility(View.GONE);
						switch (issignup) {// 是否报名 0否 1报名
						case 0:
							initFooterOtherHuoDong("整队中", "报名参加", "", false, 1);
							top_tv_view.setVisibility(View.VISIBLE);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_zhengduizhong,
									0, 0, 0);
							bm = true;
							break;
						case 1:
							initFooterOtherHuoDong("整队中", "取消报名", "", false, 1);
							top_tv_view.setVisibility(View.VISIBLE);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_zhengduizhong,
									0, 0, 0);
							bm = false;
							break;

						default:
							break;
						}
						break;
					case 3:
						top_tv_right.setVisibility(View.GONE);
						initFooterOtherHuoDong("准备出行", "下载数据", "", false, 3);
						top_tv_view.setVisibility(View.VISIBLE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_zhunbei, 0, 0, 0);
						break;
					case 4:
						top_tv_right.setVisibility(View.GONE);
						initFooterOtherHuoDong("出行中", "下载数据", "", false, 4);
						top_tv_view.setVisibility(View.VISIBLE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_chuxingzhong, 0,
								0, 0);
						break;
					case 5:
						top_tv_right.setVisibility(View.GONE);
						if (isEvaluateAct == 0) {
							initFooterOtherHuoDong("收队中", "点评活动", "", false, 5);
						} else if (isEvaluateAct == 1) {
							initFooterOtherHuoDong("收队中", "已点评", "", false, 5);
						}
						top_tv_view.setVisibility(View.VISIBLE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_shoudui, 0, 0, 0);
						break;
					case 6:
						if (isEvaluateAct == 0 && issignup == 1 && !TextUtils.isEmpty(App.getInstance().getAut())) {
							top_tv_right.setText("删除记录");
							top_tv_right.setVisibility(View.VISIBLE);
							initFooterOtherHuoDong("活动结束", "点评活动", "添加游记", true, 1);
							top_tv_view.setVisibility(View.GONE);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_jieshu, 0, 0,
									0);
						} else {
							top_tv_right.setVisibility(View.GONE);
							top_tv_view.setVisibility(View.VISIBLE);
							initFooterOtherHuoDong("活动结束", "添加游记", "删除记录", true, 1);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_jieshu, 0, 0,
									0);
						}
						break;
					default:
						break;
					}
					switch (isCollect) {// 是否收藏 n 0否 1 收藏
					case 0:
						top_tv_shoucang.setBackgroundResource(R.drawable.select_bt_shoucang);
						sc = true;
						break;
					case 1:
						top_tv_shoucang.setBackgroundResource(R.drawable.bt_shoucang_d);
						sc = false;
						break;

					default:
						break;
					}
					huodong_view.setVisibility(View.VISIBLE);
					break;
				case CommonUtility.XianShiTab_SheZhi:
					if (verify == 0 && verify == 2) {
						act_state = 0;
						top_tv_right.setVisibility(View.GONE);
						initFooterOtherHuoDong("审核中", "正在审核", "", false, 0);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_baomingzhong, 0,
								0, 0);
					} else {
						switch (act_state) {
						case -1:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("审核失败", "查看原因", "", false, 0);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_jieshu, 0, 0,
									0);
							break;
						case 0:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("审核中", "正在审核", "", false, 0);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_baomingzhong,
									0, 0, 0);
							break;
						case 1:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("报名中", "查看名单", "报名截止", true, 1);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_baomingzhong,
									0, 0, 0);
							break;
						case 2:
							top_tv_right.setText("验证数据");
							top_tv_right.setVisibility(View.VISIBLE);
							initFooterOtherHuoDong("整队中", "名单审核", "下载数据", true, 1);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_zhengduizhong,
									0, 0, 0);
							break;
						case 3:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("准备出行", "出    行", "", false, 3);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_zhunbei, 0, 0,
									0);
							break;
						case 4:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("出行中", "收    队", "", false, 4);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_chuxingzhong,
									0, 0, 0);
							break;
						case 5:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("收队中", "点评活动", "", false, 5);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_shoudui, 0, 0,
									0);
							break;
						case 6:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("活动结束", "添加游记", "删除记录", true, 6);
							huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_jieshu, 0, 0,
									0);
							break;

						default:
							top_tv_right.setVisibility(View.GONE);
							initFooterOtherHuoDong("", "", "", false, 1);
							break;
						}
					}
					huodong_view.setVisibility(View.VISIBLE);
					break;
				case CommonUtility.XianShiTab_RenWu://
					// 从数据库取回数据
					if (huoDongXiangQingDuiyuanList != null && huoDongXiangQingDuiyuanList.size() != 0
							&& huoDongXiangQingDuiyuanList.get(0).getAct_id().equals(act_id)) {
						initFooterOtherHuoDong("", "更换活动", "", false, 1);
						top_tv_view.setVisibility(View.GONE);
						top_tv_right.setText("上传轨迹");
						top_tv_right.setVisibility(View.VISIBLE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_yilingqu, 0, 0,
								0);
						huodong_select_one.setText("可更换");
					} else {
						initFooterOtherHuoDong("", "领取活动", "", false, 1);
						top_tv_view.setVisibility(View.GONE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_lingqu, 0, 0, 0);
						huodong_select_one.setText("可领取");
					}
					huodong_view.setVisibility(View.VISIBLE);
					break;
				case CommonUtility.XianShiTab_Leader:
					// 从数据库取回数据
					if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0
							&& huoDongXiangQingLeaderList.get(0).getAct_id().equals(act_id)) {
						initFooterOtherHuoDong("", "更换活动", "", false, 1);
						top_tv_view.setVisibility(View.GONE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_yilingqu, 0, 0,
								0);
						huodong_select_one.setText("可更换");
					} else {
						initFooterOtherHuoDong("", "领取活动", "", false, 1);
						top_tv_view.setVisibility(View.GONE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_lingqu, 0, 0, 0);
						huodong_select_one.setText("可领取");
					}
					huodong_view.setVisibility(View.VISIBLE);
					break;
				case CommonUtility.XianShiTab_Leader_NOW:
					// 从数据库取回数据
					if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0
							&& huoDongXiangQingLeaderList.get(0).getAct_id().equals(act_id)) {
						initFooterOtherHuoDong("", "更换活动", "", false, 1);
						top_tv_view.setVisibility(View.GONE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_yilingqu, 0, 0,
								0);
						huodong_select_one.setText("可更换");
					} else {
						initFooterOtherHuoDong("", "领取活动", "", false, 1);
						top_tv_view.setVisibility(View.GONE);
						huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_lingqu, 0, 0, 0);
						huodong_select_one.setText("可领取");
					}
					huodong_view.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
				if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
					if (NetUtil.isNetworkAvailable(mContext)) {
						mImageLoader.displayImage(picture_url, iv_map, options);
					} else {
						mImageLoader.displayImage("file://" + picture_url, iv_map, options);
					}
				} else {
					mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, iv_map, options);
				}
				tv11.setText(act_title);
				tv12.setText("￥" + act_budget);
				tv13.setText(act_type);
				String start = TimeDateUtils.formatDateFromDatabaseTime(act_start_time);
				String end = TimeDateUtils.formatDateFromDatabaseTime(act_end_time);
				tv14.setText(start.replace("-", ".") + "-" + end.substring(5, end.length()).replace("-", "."));
				if (!TextUtils.isEmpty(club_logo)) {
					if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
						if (NetUtil.isNetworkAvailable(mContext)) {
							mImageLoader.displayImage(club_logo, tv15, options);
						} else {
							mImageLoader.displayImage("file://" + club_logo, tv15, options);
						}
					} else {
						mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, tv15, options);
					}
					tv15.setVisibility(View.VISIBLE);
				} else {
					tv15.setVisibility(View.GONE);
				}
				if (!TextUtils.isEmpty(club_name)) {
					tv16.setText(club_name);
					tv16.setVisibility(View.VISIBLE);
				} else {
					tv16.setVisibility(View.GONE);
				}
				if (!TextUtils.isEmpty(leader_name)) {
					tv17.setText(leader_name);
					tv17.setVisibility(View.VISIBLE);
				} else {
					tv17.setVisibility(View.GONE);
				}
				tv18.setText(TimeDateUtils.formatDateFromDatabaseTimeSF(act_venue_time));
				tv19.setText(act_venue);
				tv20.setText(act_level + "级");
				tv21.setText("");
				if (!TextUtils.isEmpty(reason)) {
					pop_huodong_shibai_content.setText(reason);
				}
				// Double licheng = 0.0;
				// ArrayList<LatLng> LatLnglist = new ArrayList<LatLng>();
				try {
					JSONArray array = new JSONArray(trace_data);
					if (array.length() != 0) {
						for (int i = 0; i < array.length(); i++) {
							// LatLng latlng = new
							// LatLng(array.getJSONObject(i).getDouble("latitude"),
							// array.getJSONObject(i).getDouble("longitude"));
							// LatLnglist.add(latlng);
							if (i == array.length() - 1) {
								tv21.append(array.getJSONObject(i).getString("name"));
							} else {
								tv21.append(array.getJSONObject(i).getString("name") + "-");
							}
						}
					}
					// if (LatLnglist.size() != 0) {
					// for (int j = 0; j < LatLnglist.size(); j++) {
					// if (j != LatLnglist.size() - 1) {
					// licheng += DistanceUtil.getDistance(LatLnglist.get(j),
					// LatLnglist.get(j + 1));
					// }
					// }
					// }ParseOject.StringToDouble(licheng / 1000) + "km"
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (NetUtil.isNetworkAvailable(mContext)) {
					sendGetActivityTeamMemberAsyn();
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK10);
				}
				break;
			case CommonUtility.SERVEROK8:// 领队删除活动
				switch (state) {
				case 0:// 失败
					showToast("删除失败");
					break;
				case 1:// 成功
					showToast("删除成功");
					if (huoDongXiangQingDuiyuanDao.queryAll() != null) {// 当数据库中数据不为空时删除数据
						huoDongXiangQingDuiyuanDao.deleteAll();
					}
					App.getInstance().setLeaderHuoDongId("");
					finish();
					break;
				case 2:// 领队只能删除自己的活动
					showToast("领队只能删除自己的活动");
					break;
				case 3:// 只能删除已经结束的活动
					showToast("只能删除已经结束的活动");
					break;
				case 4:// 你不是领队
					showToast("你不是领队");
					break;
				}
				break;
			case CommonUtility.SERVEROK9:
				int index = 0;
				mDatas.clear();
				if (act_state == 4 || act_state == 5 || act_state == 6) {
					index = 1;
					if (NetUtil.isNetworkAvailable(mContext)) {
						HuoDongXiangQingDongTaiBeans.clear();
						for (int i = 0; i < HistoryList.size(); i++) {
							HuoDongXiangQingDongTaiBean = new HuoDongXiangQingDongTai();
							HuoDongXiangQingDongTaiBean.setAct_id(HistoryList.get(i).getAct_id());
							HuoDongXiangQingDongTaiBean.setArrive_lng(HistoryList.get(i).getArrive_lng());
							HuoDongXiangQingDongTaiBean.setArrive_time(HistoryList.get(i).getArrive_time());
							HuoDongXiangQingDongTaiBean.setDescript(HistoryList.get(i).getDescript());
							HuoDongXiangQingDongTaiBean.setImages(HistoryList.get(i).getImages());
							HuoDongXiangQingDongTaiBean.setRarrive_lag(HistoryList.get(i).getRarrive_lag());
							HuoDongXiangQingDongTaiBeans.add(HuoDongXiangQingDongTaiBean);
						}
					}
					// 实时动态
					mDataItem = new HuoDongXiangQingItem();
					mDataItem.setHistoryList(HuoDongXiangQingDongTaiBeans);
					mDataItem.setType(0);
					mDatas.add(mDataItem);
				} else {
					index = 0;
				}
				// 活动介绍
				mDataItem = new HuoDongXiangQingItem();
				mDataItem.setAct_desc(act_desc);
				mDataItem.setAct_desc_web(act_desc_web);
				mDataItem.setType(0 + index);
				mDatas.add(mDataItem);
				// 行程安排
				if (NetUtil.isNetworkAvailable(mContext)) {
					try {
						JSONArray array = new JSONArray(act_schedule_app);
						HuoDongXiangQingItemBeans.clear();
						for (int i = 0; i < array.length(); i++) {
							HuoDongXiangQingItemBean = new HuoDongXiangQingGongNue();
							try {
								schedu_title = array.getJSONObject(i).getString("schedu_title");
							} catch (Exception e) {
								schedu_title = "";
							}
							try {
								schedu_time = array.getJSONObject(i).getString("schedu_time");
							} catch (Exception e) {
								schedu_time = "";
							}
							try {
								schedu_desc = array.getJSONObject(i).getString("schedu_desc");
							} catch (Exception e) {
								schedu_desc = "";
							}
							try {
								imgs = array.getJSONObject(i).get("imgs").toString();
							} catch (Exception e) {
								imgs = null;
							}
							HuoDongXiangQingItemBean.setSchedu_title(schedu_title);
							HuoDongXiangQingItemBean
									.setSchedu_time(TimeDateUtils.formatDateFromDatabaseTime(schedu_time));
							HuoDongXiangQingItemBean.setSchedu_desc(schedu_desc);
							HuoDongXiangQingItemBean.setImgs(imgs);
							HuoDongXiangQingItemBeans.add(HuoDongXiangQingItemBean);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						HuoDongXiangQingItemBeans = null;
					}
				}
				mDataItem = new HuoDongXiangQingItem();
				mDataItem.setAct_schedule_apps(HuoDongXiangQingItemBeans);
				mDataItem.setType(1 + index);
				mDatas.add(mDataItem);
				// 活动费用
				mDataItem = new HuoDongXiangQingItem();
				mDataItem.setAct_fee_include(act_fee_include);
				mDataItem.setType(2 + index);
				mDatas.add(mDataItem);
				// 装备要求
				mDataItem = new HuoDongXiangQingItem();
				mDataItem.setAct_base_equip(act_base_equip);
				mDataItem.setType(3 + index);
				mDatas.add(mDataItem);
				// 报名须知
				mDataItem = new HuoDongXiangQingItem();
				mDataItem.setLogistics(logistics);
				mDataItem.setType(4 + index);
				mDatas.add(mDataItem);
				// 其他
				mDataItem = new HuoDongXiangQingItem();
				mDataItem.setAct_else(act_else);
				mDataItem.setType(5 + index);
				mDatas.add(mDataItem);
				// 队伍状况
				mDataItem = new HuoDongXiangQingItem();
				mDataItem.setConfirm_member(confirm_member);
				mDataItem.setLeader_name(leader_name);
				mDataItem.setMan(man);
				mDataItem.setFemale(female);
				mDataItem.setType(6 + index);
				mDataItem.setAct_join_num_limit(act_join_num_limit);
				mDataItem.setAct_weixin(act_weixin);
				mDataItem.setAct_2d_code(act_2d_code);
				if (NetUtil.isNetworkAvailable(mContext)) {
					HuoDongDuiYuanBeans.clear();
					for (int i = 0; i < team_memberList.size(); i++) {
						HuoDongDuiYuanBean = new HuoDongDuiYuan();
						HuoDongDuiYuanBean.setHead(team_memberList.get(i).getHead());
						HuoDongDuiYuanBean.setUserid(team_memberList.get(i).getUserid());
						HuoDongDuiYuanBeans.add(HuoDongDuiYuanBean);
					}
				}
				mDataItem.setTeam_memberList(HuoDongDuiYuanBeans);
				mDatas.add(mDataItem);
				myListAdpter = new MyListAdpter(HuoDongXiangQingActivity.this, mDatas, act_state);
				lv.setAdapter(myListAdpter);

				lv.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						int postion = 0;
						// firstVisibleItem 当前第一个可见的item
						// visibleItemCount 当前可见的item个数
						if (firstVisibleItem > 0) {
							postion = firstVisibleItem - 1;
						}
						mDataItem = (HuoDongXiangQingItem) myListAdpter.getItem(postion);
						if (act_state == 4 || act_state == 5 || act_state == 6) {
							switch (mDataItem.getType()) {
							case 0:
								type_view.setVisibility(View.GONE);
								if (firstVisibleItem == 1) {
									type_view.setVisibility(View.VISIBLE);
								}
								if (act_state == 4) {
									type.setText(TYPE_DongTai_Str);
								} else {
									type.setText(TYPE_LiShiDongTai_Str);
								}
								break;
							case 1:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Des_Str);
								break;
							case 2:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Raiders_Str);
								break;
							case 3:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Cost_Str);
								break;
							case 4:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Equipment_Str);
								break;
							case 5:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Logistics_Str);
								break;
							case 6:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_QiTa_Str);
								break;
							case 7:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Status_Str);
								break;
							default:
								break;
							}
						} else {
							switch (mDataItem.getType()) {
							case 0:
								type_view.setVisibility(View.GONE);
								if (firstVisibleItem == 1) {
									type_view.setVisibility(View.VISIBLE);
								}
								type.setText(TYPE_Des_Str);
								break;
							case 1:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Raiders_Str);
								break;
							case 2:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Cost_Str);
								break;
							case 3:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Equipment_Str);
								break;
							case 4:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Logistics_Str);
								break;
							case 5:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_QiTa_Str);
								break;
							case 6:
								type_view.setVisibility(View.VISIBLE);
								type.setText(TYPE_Status_Str);
								break;
							default:
								break;
							}
						}
					}
				});
				if (CommonUtility.XianShiTab_Leader_NOW == showBotton) {
					if (NetUtil.isNetworkAvailable(mContext)) {
						if (isshowdialog()) {
							closedialog();
						}
						showdialogtext("正在更新数据中...");
						DownDaoLeaderHistory();
					}
				} else {
					if (isshowdialog()) {
						closedialog();
					}
				}
				break;
			case CommonUtility.SERVEROK10:
				if (NetUtil.isNetworkAvailable(mContext)) {
					if (act_state == 4 || act_state == 5 || act_state == 6) {
						sendActHistoryListAsyn();
					} else {
						handlerlist.sendEmptyMessage(CommonUtility.SERVEROK9);
					}
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK9);
				}
				break;
			case CommonUtility.SERVEROK11:
				showToast("领取成功");
				huodong_select_one.setText("可更换");
				huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_yilingqu, 0, 0, 0);
				huodong_select_two.setText("更换活动");
				top_tv_view.setVisibility(View.GONE);
				top_tv_right.setText("上传轨迹");
				top_tv_right.setVisibility(View.VISIBLE);
				huoDongXiangQingDuiyuanList = huoDongXiangQingDuiyuanDao.queryAll();
				break;
			case CommonUtility.SERVEROK14:
				showToast("领取成功");
				huodong_select_one.setText("可更换");
				huodong_select_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.huodong_yilingqu, 0, 0, 0);
				huodong_select_two.setText("更换活动");
				top_tv_view.setVisibility(View.GONE);
				top_tv_right.setText("上传轨迹");
				top_tv_right.setVisibility(View.VISIBLE);
				huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
				break;
			case CommonUtility.SERVEROK12:// 上传轨迹文件
				if (!TextUtils.isEmpty(App.getInstance().getHuoDongId())) {
					if (isshowdialog()) {
						closedialog();
					}
					showToast("上传成功");
				} else if (!TextUtils.isEmpty(App.getInstance().getLeaderHuoDongId())) {
					if (huoDongXiangQingLeaderDao.queryAll() != null) {// 当数据库中数据不为空时删除数据
						huoDongXiangQingLeaderDao.deleteAll();
					}
					// 上传完成之后删除轨迹文件
					deleteGpsDao();
					App.getInstance().setLeaderHuoDongId("");
					sendSetActivitystateActionAsyn();
				} else {
					// 上传完成之后删除轨迹文件
					deleteGpsDao();
					sendSetActivitystateActionAsyn();
				}
				break;
			case CommonUtility.SERVEROK13:
				if (state == 1) {
					showToast("删除成功");
					finish();
				} else {
					showToast("删除失败");
				}
				break;
			case CommonUtility.KONG:
				if (!TextUtils.isEmpty(getString) && !TextUtils.equals(getString, "操作执行成功")) {
					showToast(getString);
				}
				getString = null;
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.SERVERERRORLOGIN:
				if (isshowdialog()) {
					closedialog();
				}
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				isFirst = false;
				break;
			default:
				break;
			}
		};
	};
	private List<GpsInfo> infos;
	// 轨迹数据库操作
	private BaseDao<GpsInfo> mGpSinfoDao;
	private GpsInfo info = null;
	private UploadTraceDataDatalistItem dataDatalistItem = null;

	/**
	 * GPS数据的初始化
	 */
	public void initGpsDao() {
		// 轨迹信息数据库
		info = new GpsInfo();
		datalist = new ArrayList<UploadTraceDataDatalistItem>();
		mGpSinfoDao = new BaseDao(info, HuoDongXiangQingActivity.this);
		infos = mGpSinfoDao.queryAll();
		for (int i = 0; i < infos.size(); i++) {
			dataDatalistItem = new UploadTraceDataDatalistItem();
			dataDatalistItem.setEnd_time(infos.get(i).getEnd_time());
			dataDatalistItem.setName(infos.get(i).getName());
			dataDatalistItem.setStart_time(infos.get(i).getStart_time());
			dataDatalistItem.setTrace_data(infos.get(i).getLocationInfo());
			datalist.add(dataDatalistItem);
		}
		sendAsyn();
	}

	/**
	 * GPS数据的初始化
	 */
	public void deleteGpsDao() {
		// 轨迹信息数据库
		info = new GpsInfo();
		datalist = new ArrayList<UploadTraceDataDatalistItem>();
		mGpSinfoDao = new BaseDao(info, HuoDongXiangQingActivity.this);
		mGpSinfoDao.deleteAll();
	}

	// 用户上传轨迹文件
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	private ArrayList<UploadTraceDataDatalistItem> datalist = null;

	public void Action() {
		UploadTraceDataRequest request = new UploadTraceDataRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		UploadTraceDataRequestParameter parameter = new UploadTraceDataRequestParameter();
		parameter.setDatalist(datalist);
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
					UploadTraceDataResponse response = new UploadTraceDataResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UploadTraceDataResponsePayload payload = (UploadTraceDataResponsePayload) response.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK12);
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

}
