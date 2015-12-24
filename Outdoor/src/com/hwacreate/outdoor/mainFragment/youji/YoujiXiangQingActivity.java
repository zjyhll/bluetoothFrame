package com.hwacreate.outdoor.mainFragment.youji;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.HuoDongDuiYuan;
import com.hwacreate.outdoor.bean.TypeTrackItemBean;
import com.hwacreate.outdoor.bean.YouJiXiangQingJiaoYin;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.wode.HaoYouDetailActivity;
import com.hwacreate.outdoor.mainFragment.zhuzhi.JuLeBuXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
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
import com.keyhua.outdoor.protocol.CollectTravelAction.CollectTravelRequest;
import com.keyhua.outdoor.protocol.CollectTravelAction.CollectTravelRequestParameter;
import com.keyhua.outdoor.protocol.CollectTravelAction.CollectTravelResponse;
import com.keyhua.outdoor.protocol.CollectTravelAction.CollectTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetActivityTeamMemberAction.GetActivityTeamMemberResponsePayloadItem;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListRequest;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListRequestParameter;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListResponse;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListResponsePayload;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDRequest;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDResponse;
import com.keyhua.outdoor.protocol.GetTravelInfoByIDAction.GetTravelInfoByIDResponsePayload;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionRequest;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionRequestParameter;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionResponse;
import com.keyhua.outdoor.protocol.ShareOptionAction.ShareOptionResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import android.widget.LinearLayout;
import android.widget.TextView;

public class YoujiXiangQingActivity extends BaseActivity {

	// 获得当前上下文对象
	private Context mContext = null;
	private int agreeCount = 0;
	private int isDownLoad = 0;
	private int isAgree = 0; // 0 未点赞，1 点
	// listview
	private MyListView lv = null;// 展示详细信息
	private MyListAdpter myListAdpter = null;
	private MyFootprintAdpter footprintAdpter = null;
	// 浮动显示textview
	private TextView type = null;
	// 整个游记详情数据库操作
	private List<YoujiXiangqing> mDatas = null;
	private YoujiXiangqing youjiXiangqing = null;
	private BaseDao<YoujiXiangqing> youjiBaseDao = null;
	private List<YoujiXiangqing> youJiXiangqinglist = null;
	// list加载数据临时对象
	private YoujiXiangqing youjiXiangqingTemp = null;
	// 队员列表
	private List<HuoDongDuiYuan> HuoDongDuiYuanBeans = null;
	private HuoDongDuiYuan HuoDongDuiYuanBean = null;
	// header
	private View headerLayout = null;
	private LinearLayout headerParent = null;
	// 形成描述
	private final int TYPE_Trip_description = 0;
	private final String TYPE_Trip_description_Str = "游记简介";
	// 足迹
	private final int TYPE_track = 1;
	private final String TYPE_track_Str = "行程轨迹";
	// 足迹
	private final int TYPE_43_footprints = 2;
	private final String TYPE_43_footprints_Str = "行程脚印";
	// 游记描述
	private final int TYPE_Cost = 3;
	private final String TYPE_Cost_Str = "游记描述";
	// 装备
	private final int TYPE_Equipment = 4;
	private final String TYPE_Equipment_Str = "出行装备";
	// 后勤
	private final int TYPE_Logistics = 5;
	private final String TYPE_Logistics_Str = "报名须知";
	// 其他
	private final int TYPE_Other = 6;
	private final String TYPE_Other_Str = "其    他";
	// 队伍状况
	private final int TYPE_Status = 7;
	private final String TYPE_Status_Str = "队伍状况";

	private String isMywhrite = null;
	private boolean isActid = false;
	// json
	private Gson gson = null;

	private ArrayList<GetActivityTeamMemberResponsePayloadItem> team_memberList = null;
	// 点击浏览大图
	private View huodong_viewpager_view = null;
	private MyViewPager huodong_viewpager = null;
	private LinearLayout huodong_indicator = null;
	private AdapterCycle_huo adapterCycle_huo = null;

	private List<TypeTrackItemBean> topImageDatas_ViewPager = null;
	private TypeTrackItemBean topImage_ViewPager = null;
	private List<TypeTrackItemBean> typeTrackItemBeans = null;
	private TypeTrackItemBean bean = null;
	private List<YouJiXiangQingJiaoYin> youjijiaoYinsbeans = null;
	private YouJiXiangQingJiaoYin XiangQingJiaoYin = null;
	private int downid_viewpager = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = YoujiXiangQingActivity.this;
		setContentView(R.layout.activity_you_ji_xiang_qing);
		init();
	}

	@Override
	public void onBackPressed() {
		if (huodong_viewpager_view.getVisibility() == View.VISIBLE) {
			huodong_viewpager_view.setVisibility(View.GONE);
		} else {
			super.onBackPressed();
		}
	}

	private void showViewPage(List<TypeTrackItemBean> imageslist, List<String> imagesjiaoyin, String images,
			int index) {
		showdialog();
		// 0HuoDongImages格式 1fullurljson格式 2json格式(有字段名) 3String 4json格式
		topImageDatas_ViewPager.clear();
		switch (index) {
		case 0:
			topImageDatas_ViewPager.addAll(imageslist);
			break;
		case 1:
			for (int i = 0; i < imagesjiaoyin.size(); i++) {
				topImage_ViewPager = new TypeTrackItemBean();
				topImage_ViewPager.setImageurl(imagesjiaoyin.get(i));
				topImageDatas_ViewPager.add(topImage_ViewPager);
			}
			break;
		case 2:
			topImage_ViewPager = new TypeTrackItemBean();
			topImage_ViewPager.setImageurl(images);
			topImageDatas_ViewPager.add(topImage_ViewPager);
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
		private List<TypeTrackItemBean> mList;
		private LinearLayout indicatorLayout = null;
		private ImageView[] indicators = null;
		private int currentItem = 0;

		public AdapterCycle_huo(Context context, MyViewPager viewPager, List<TypeTrackItemBean> list,
				LinearLayout layout) {
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
					PhotoView view = new PhotoView(YoujiXiangQingActivity.this);
					if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
						if (NetUtil.isNetworkAvailable(mContext) && isDownLoad == 0) {
							mImageLoader.displayImage(mList.get(i).getImageurl(), view, options);
						} else {
							mImageLoader.displayImage("file://" + mList.get(i).getImageurl(), view, options);
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
	protected void onInitData() {
		initHeaderOther();
		agreeCount = getIntent().getIntExtra("agreeCount", 0);
		isDownLoad = getIntent().getIntExtra("isDownLoad", 0);
		isAgree = getIntent().getIntExtra("isAgree", 0);
		tvl_id = getIntent().getStringExtra("tvl_id");
		act_id = getIntent().getStringExtra("act_id");
		if (!TextUtils.isEmpty(act_id)) {
			isActid = true;
		} else {
			isActid = false;
		}
		isMywhrite = getIntent().getStringExtra("isMywhrite");
		type = (TextView) findViewById(R.id.type);
		// 主窗体中的listView
		lv = (MyListView) findViewById(R.id.lv);
		youjijiaoYinsbeans = new ArrayList<YouJiXiangQingJiaoYin>();
		typeTrackItemBeans = new ArrayList<TypeTrackItemBean>();
		topImageDatas_ViewPager = new ArrayList<TypeTrackItemBean>();
		initDao();
		initializeHeaderAndFooter();
		sendShareOptionActionAsyn();
	}

	private void initDao() {
		gson = new Gson();
		mDatas = new ArrayList<YoujiXiangqing>();
		youjiXiangqing = new YoujiXiangqing();
		youjiBaseDao = new BaseDao<YoujiXiangqing>(youjiXiangqing, mContext);
		youJiXiangqinglist = new ArrayList<YoujiXiangqing>();
		team_memberList = new ArrayList<GetActivityTeamMemberResponsePayloadItem>();
		HuoDongDuiYuanBeans = new ArrayList<HuoDongDuiYuan>();
	}

	@Override
	protected void onResload() {
		showdialog();
		initFooterOther("", "", "");
		if (TextUtils.isEmpty(isMywhrite)) {
			top_tv_title.setText("游记详情");
			rg_button.setVisibility(View.VISIBLE);
		} else {
			top_tv_title.setText("游记预览");
			rg_button.setVisibility(View.GONE);
		}
		myListAdpter = new MyListAdpter(YoujiXiangQingActivity.this, youJiXiangqinglist);
		lv.setAdapter(myListAdpter);
		if (NetUtil.isNetworkAvailable(mContext) && isDownLoad == 0) {
			sendGetTravelInfoByIDActionAsyn();
		} else {
			getDao();
		}
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		ll_julebu.setOnClickListener(this);
		iv.setOnClickListener(this);
		pep_view.setOnClickListener(this);
		huodong_viewpager.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (huodong_viewpager_view.getVisibility() == View.VISIBLE) {
					huodong_viewpager_view.setVisibility(View.GONE);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			finish();
			break;
		case R.id.radiobutton_select_one:// 分享
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
		case R.id.radiobutton_select_two:// 下载
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				switch (isDownLoad) {
				case 0:
					if (NetUtil.isNetworkAvailable(mContext)) {
						if (!TextUtils.isEmpty(tvl_id)) {
							showdialogtext("正在下载中...");
							if (getCustomProgressDialog() != null) {
								getCustomProgressDialog().setOnCancelListener(new OnCancelListener() {

									@Override
									public void onCancel(DialogInterface dialog) {
										showAlertDialog(mContext, "是否取消下载?                   ", 3);
									}
								});
							}
							setDao();
						} else {
							showToast("下载失败");
						}
					} else {
						showToastNet();
					}
					break;
				case 1:
					showAlertDialog(mContext, "是否清除该条游记？                        ", 1);
					break;

				default:
					break;
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
			}
			break;
		case R.id.radiobutton_select_three:// 收藏
			if (NetUtil.isNetworkAvailable(mContext)) {
				if (!TextUtils.isEmpty(App.getInstance().getAut())) {
					switch (is_collect) {// 是否收藏
					case 0:
						sendCollectionActivityAsyn();
						break;
					case 1:
						showAlertDialog(mContext, "是否取消收藏该条游记？                        ", 2);
						break;

					}
				} else {
					showToastDengLu();
					openActivity(LoginActivity.class);
				}
			} else {
				showToastNet();
			}
			break;
		case R.id.ll_julebu:
			bundle.putString("clubid", club_id);
			openActivity(JuLeBuXiangQingActivity.class, bundle);
			break;
		case R.id.iv:
			downid_viewpager = 0;
			if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
				if (NetUtil.isNetworkAvailable(mContext) && isDownLoad == 0) {
					showViewPage(null, null, tvl_cover, 2);
				} else {
					showViewPage(null, null, "file://" + youjiXiangqing.getTvl_cover(), 2);
				}
			} else {
				showViewPage(null, null, "drawable://" + R.drawable.loadingnot, 2);
			}
			break;
		case R.id.pep_view:
			bundle.putString("Userid", userid);
			openActivity(HaoYouDetailActivity.class, bundle);
			break;
		default:
			break;
		}
	}

	/** 弹出清除对话框Dialog */
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
					youjiBaseDao.deletebytvlid(tvl_id);
					showToast("清除成功");
					isDownLoad = 0;
					radiobutton_select_two.setText("下载");
					radiobutton_select_two.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_xiazai, 0,
							0);
					finish();
					break;
				case 2:
					sendCollectionActivityAsyn();
					break;
				case 3:
					File appDir = new File(Environment.getExternalStorageDirectory(), "outdooryouji");
					if (appDir.exists()) {
						DataCleanManager.deleteFile(appDir);
					}
					if (youjiBaseDao.searchByTvl_id(tvl_id) != null) {// 当数据库中数据不为空时删除数据
						youjiBaseDao.deletebytvlid(tvl_id);
					}
					if (isshowdialog()) {
						closedialog();
					}
					finish();
					break;

				default:
					break;
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

	// listview的head字段
	private ImageView iv = null;// 地图截图
	private CircleImageView juleb_icon = null;// 俱乐部头像
	private CircleImageView pep_icon = null;// 游记作者头像
	private LinearLayout pep_view = null;// 游记作者头像
	private TextView tv11 = null;// 游记名称
	private TextView tv22 = null;// 活动时间
	private TextView tv31 = null;// 人均费用
	private TextView tv32 = null;// 活动等级
	private TextView tv33 = null;// 活动地区
	private TextView tv41 = null;// 活动类型
	private TextView tv42 = null;// 活动里程
	private TextView juleb_name = null;// 俱乐部名字
	private TextView youji_zuoze_name = null;// 游记的作者名字
	private LinearLayout ll_julebu = null;// 俱乐部的布局
	private TextView youji_write_time = null;// 游记的创建时间

	private void initializeHeaderAndFooter() {
		headerLayout = LayoutInflater.from(this).inflate(R.layout.head_youjixiangqing, null, true);
		iv = (ImageView) headerLayout.findViewById(R.id.iv);
		iv.setLayoutParams(
				new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
		juleb_icon = (CircleImageView) headerLayout.findViewById(R.id.juleb_icon);
		pep_icon = (CircleImageView) headerLayout.findViewById(R.id.pep_icon);
		pep_view = (LinearLayout) headerLayout.findViewById(R.id.pep_view);
		tv11 = (TextView) headerLayout.findViewById(R.id.tv11);
		tv22 = (TextView) headerLayout.findViewById(R.id.tv22);
		tv31 = (TextView) headerLayout.findViewById(R.id.tv31);
		tv32 = (TextView) headerLayout.findViewById(R.id.tv32);
		tv33 = (TextView) headerLayout.findViewById(R.id.tv33);
		tv41 = (TextView) headerLayout.findViewById(R.id.tv41);
		tv42 = (TextView) headerLayout.findViewById(R.id.tv42);
		juleb_name = (TextView) headerLayout.findViewById(R.id.juleb_name);
		youji_zuoze_name = (TextView) headerLayout.findViewById(R.id.youji_zuoze_name);
		ll_julebu = (LinearLayout) headerLayout.findViewById(R.id.ll_julebu);
		youji_write_time = (TextView) headerLayout.findViewById(R.id.youji_write_time);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		headerLayout.setLayoutParams(params);
		headerParent = new LinearLayout(this);
		headerParent.addView(headerLayout);
		lv.addHeaderView(headerParent);
		// ViewPager
		huodong_viewpager_view = findViewById(R.id.huodong_viewpager_view);
		huodong_viewpager = (MyViewPager) findViewById(R.id.huodong_viewpager);
		huodong_indicator = (LinearLayout) findViewById(R.id.huodong_indicator);
		huodong_viewpager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
	}

	/**
	 * @author 曾金叶
	 * @2015-8-6 @上午9:58:49
	 * @category adapter ,写在本activity，不用分出来
	 */
	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private LayoutInflater mInflater = null;
		private List<YoujiXiangqing> mDatas = null;

		public MyListAdpter(Context context, List<YoujiXiangqing> Datas) {
			this.context = context;
			this.mDatas = Datas;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mDatas != null ? mDatas.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// 每个convert view都会调用此方法，获得当前所需要的view样式
		@Override
		public int getItemViewType(int position) {
			if (isActid) {
				switch (mDatas.get(position).getType()) {
				case TYPE_Trip_description:
					return 0;
				case TYPE_track:
					return 1;
				case TYPE_43_footprints:
					return 2;
				case TYPE_Cost:
					return 3;
				case TYPE_Equipment:
					return 4;
				case TYPE_Logistics:
					return 5;
				case TYPE_Other:
					return 6;
				case TYPE_Status:
					return 7;
				}
			} else {
				switch (mDatas.get(position).getType()) {
				case TYPE_Trip_description:
					return 0;
				case TYPE_track:
					return 1;
				case TYPE_43_footprints:
					return 2;
				}
			}
			return -1;
		}

		@Override
		public int getViewTypeCount() {
			if (isActid) {
				return 8;
			} else {
				return 3;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			YoujiXiangqing news = mDatas.get(position); // 获取当前项数据
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				switch (news.getType()) {
				case TYPE_Trip_description:
					convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
					holder.content = (TextView) convertView.findViewById(R.id.content);
					holder.type = (TextView) convertView.findViewById(R.id.type);
					break;
				case TYPE_track:
					convertView = mInflater.inflate(R.layout.item_youjixiangqing_typetrack, null);
					holder.content = (TextView) convertView.findViewById(R.id.content);
					holder.type = (TextView) convertView.findViewById(R.id.type);
					holder.lv = (MyListView) convertView.findViewById(R.id.lv);
					break;
				case TYPE_43_footprints://
					convertView = mInflater.inflate(R.layout.item_youjixiangqing_typefootprints, null);
					holder.lv = (MyListView) convertView.findViewById(R.id.lv_first);
					holder.type = (TextView) convertView.findViewById(R.id.type);
					holder.content = (TextView) convertView.findViewById(R.id.content);
					holder.imagetemp = (ImageView) convertView.findViewById(R.id.imagetemp);
					break;
				case TYPE_Cost:
					convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
					holder.content = (TextView) convertView.findViewById(R.id.content);
					holder.type = (TextView) convertView.findViewById(R.id.type);
					break;
				case TYPE_Equipment:
					convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
					holder.content = (TextView) convertView.findViewById(R.id.content);
					holder.type = (TextView) convertView.findViewById(R.id.type);
					break;
				case TYPE_Logistics:
					convertView = mInflater.inflate(R.layout.item_huodongxiangqing_typecost, null);
					holder.content = (TextView) convertView.findViewById(R.id.content);
					holder.type = (TextView) convertView.findViewById(R.id.type);
					break;
				case TYPE_Other:
					convertView = mInflater.inflate(R.layout.item_huodongxiangqing_other, null);
					holder.type = (TextView) convertView.findViewById(R.id.type);
					holder.content = (TextView) convertView.findViewById(R.id.content);
					break;
				case TYPE_Status:
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
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (null != news) {
				switch (news.getType()) {
				case TYPE_Trip_description:
					holder.type.setText(TYPE_Trip_description_Str);
					if (!TextUtils.isEmpty(news.getTvl_desc_intro())) {
						holder.content.setText(news.getTvl_desc_intro());
					} else {
						holder.content.setText("无");
					}
					break;
				case TYPE_track:
					typeTrackItemBeans.clear();
					holder.type.setText(TYPE_track_Str);
					StringBuilder lujin = null;
					try {
						if (!TextUtils.isEmpty(news.getTrace_data())) {
							JSONArray array = new JSONArray(news.getTrace_data());
							if (array.length() != 0) {
								for (int i = 0; i < array.length(); i++) {
									bean = new TypeTrackItemBean();
									lujin = new StringBuilder();
									bean.setTitle(array.getJSONObject(i).getString("name"));
									JSONArray jsonArray = null;
									if (isDownLoad == 0) {
										jsonArray = array.getJSONObject(i).getJSONArray("trace_data");
									} else if (isDownLoad == 1) {
										jsonArray = new JSONArray(
												array.getJSONObject(i).getJSONArray("trace_data").toString());
									}
									if (jsonArray != null) {
										for (int j = 0; j < jsonArray.length(); j++) {
											if (j == jsonArray.length() - 1) {
												lujin.append(jsonArray.getJSONObject(j).getString("name"));
											} else {
												if (!TextUtils.isEmpty(jsonArray.getJSONObject(j).getString("name"))) {
													lujin.append(jsonArray.getJSONObject(j).getString("name") + "-");
												}
											}
										}
									}
									bean.setLujin(lujin.toString());
									bean.setImageurl(array.getJSONObject(i).getString("picture_url"));
									String start = TimeDateUtils.formatDateFromDatabaseTime(
											array.getJSONObject(i).getString("act_real_start_time"));
									String end = TimeDateUtils.formatDateFromDatabaseTime(
											array.getJSONObject(i).getString("act_real_end_time"));
									if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
										bean.setTime(start.replace("-", ".") + "-"
												+ end.substring(5, end.length()).replace("-", "."));
									}
									typeTrackItemBeans.add(bean);
								}
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					holder.lv.setAdapter(new CommonAdapter<TypeTrackItemBean>(context, typeTrackItemBeans,
							R.layout.item_youjixiangqing_typetrack_item) {
						@Override
						public void convert(ViewHolderUntil helper, TypeTrackItemBean item, final int position) {
							if (position == 0) {
								helper.getView(R.id.tip).setVisibility(View.VISIBLE);
							} else {
								helper.getView(R.id.tip).setVisibility(View.GONE);
							}
							helper.getView(R.id.CB_image).setLayoutParams(new LinearLayout.LayoutParams(
									LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
							if (NetUtil.isNetworkAvailable(mContext) && isDownLoad == 0) {
								helper.setCubeImageByUrlXQ(R.id.CB_image, item.getImageurl(), imageLoader,
										mImageLoader);
							} else {
								helper.setCubeImageByUrlSDXQ(R.id.CB_image, item.getImageurl(), mImageLoader);
							}
							helper.setText(R.id.title_zuji, item.getTitle());
							helper.setText(R.id.time, item.getTime());
							helper.setText(R.id.guiji_lujin, item.getLujin());
							helper.getView(R.id.CB_image).setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									downid_viewpager = position;
									showViewPage(typeTrackItemBeans, null, null, 0);
								}
							});
						}
					});
					if (typeTrackItemBeans == null || typeTrackItemBeans.size() == 0) {
						holder.content.setText("无");
						holder.content.setVisibility(View.VISIBLE);
					}
					break;
				case TYPE_43_footprints:
					// 解析存储带有的图片和脚印数据
					String footprint_time = "";// 一个脚印时间
					String footprint_desc = "";// 一个脚印描述
					String footprint_title = "";// 一个脚印标题
					JSONArray imgs = new JSONArray();// 一天的一个脚印数据的图片的json数组，不超过五张图片
					List<String> imageurList = null;
					if (news.getFootprint_data() != null) {
						youjijiaoYinsbeans.clear();
						try {
							JSONArray array = new JSONArray(footprint_data);
							for (int j = 0; j < array.length(); j++) {
								XiangQingJiaoYin = new YouJiXiangQingJiaoYin();
								footprint_time = array.getJSONObject(j).getString("footprint_time");
								try {
									footprint_desc = array.getJSONObject(j).getString("footprint_desc");
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								try {
									footprint_title = array.getJSONObject(j).getString("footprint_title");
								} catch (Exception e) {
									e.printStackTrace();
								}
								imgs = array.getJSONObject(j).getJSONArray("imgs");
								imageurList = new ArrayList<String>();
								for (int k = 0; k < imgs.length(); k++) {
									imageurList.add(imgs.getString(k));
								}
								XiangQingJiaoYin.setImageurl(imageurList);
								XiangQingJiaoYin.setTv_youji_timeline_time(footprint_time);
								XiangQingJiaoYin.setTv_youji_footprint_desc(footprint_desc);
								XiangQingJiaoYin.setTv_youji_timeline_title(footprint_title);
								youjijiaoYinsbeans.add(XiangQingJiaoYin);
							}

						} catch (JSONException e) {
							e.printStackTrace();
							youjijiaoYinsbeans = null;
						}
					}
					holder.type.setText(TYPE_43_footprints_Str);
					if (youjijiaoYinsbeans != null && youjijiaoYinsbeans.size() != 0) {
						footprintAdpter = new MyFootprintAdpter(YoujiXiangQingActivity.this, youjijiaoYinsbeans);
						holder.lv.setAdapter(footprintAdpter);
					} else {
						holder.content.setText("无");
						holder.content.setVisibility(View.VISIBLE);
					}
					if (isActid) {
						holder.imagetemp.setVisibility(View.GONE);
					} else {
						holder.imagetemp.setVisibility(View.VISIBLE);
					}
					break;
				case TYPE_Cost:
					holder.type.setText(TYPE_Cost_Str);
					if (!TextUtils.isEmpty(news.getTvl_desc())) {
						holder.content.setText("" + news.getTvl_desc());
					} else {
						holder.content.setText("无");
					}
					break;
				case TYPE_Equipment:
					holder.type.setText(TYPE_Equipment_Str);
					if (!TextUtils.isEmpty(news.getAct_most_equip())) {
						holder.content.setText("" + news.getAct_most_equip());
					} else {
						holder.content.setText("无");
					}
					break;
				case TYPE_Logistics://
					holder.type.setText(TYPE_Logistics_Str);
					if (!TextUtils.isEmpty(news.getLogistics())) {
						holder.content.setText(news.getLogistics());
					} else {
						holder.content.setText("无");
					}
					break;
				case TYPE_Other:
					holder.type.setText(TYPE_Other_Str);
					if (!TextUtils.isEmpty(news.getTvl_else())) {
						holder.content.setText(news.getTvl_else());
					} else {
						holder.content.setText("无");
					}
					break;
				case TYPE_Status:
					holder.type.setText(TYPE_Status_Str);
					holder.tv_1.setText("" + news.getAct_join_num_limit());
					holder.tv_2.setText("" + news.getConfirmed_member());
					holder.tv_3.setText("0");
					holder.tv_4.setText(news.getMalecount() + "");
					holder.tv_7.setVisibility(View.INVISIBLE);
					holder.tv_5.setText(news.getFemalecount() + "");
					holder.tv_6.setText("" + news.getAct_weixin());
					holder.tv_8.setText("" + news.getLeader_name());
					if (news.getTeam_memberList() != null && news.getTeam_memberList().size() != 0) {

						holder.tv_9.setAdapter(new CommonAdapter<HuoDongDuiYuan>(YoujiXiangQingActivity.this,
								news.getTeam_memberList(), R.layout.item_duiyuan) {
							@Override
							public void convert(ViewHolderUntil helper, final HuoDongDuiYuan item, int position) {
								if (NetUtil.isNetworkAvailable(mContext) && isDownLoad == 0) {
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
			return convertView;
		}

		private final class ViewHolder {
			TextView content;// 内容
			TextView type;// 类型，显示在每一个item的最上面
			ImageView imagetemp;
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
			MyViewPager viewPager = null;
		}

	}

	public class MyFootprintAdpter extends BaseAdapter {
		private Context context = null;
		private List<YouJiXiangQingJiaoYin> mLists = null;

		public MyFootprintAdpter(Context context, List<YouJiXiangQingJiaoYin> mList) {
			this.context = context;
			this.mLists = mList;
		}

		@Override
		public int getCount() {
			return mLists != null ? mLists.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final YouJiXiangQingJiaoYin news = mLists.get(position); // 获取当前项数据
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_youjixiangqing_typefootprints_item,
						null);
				holder.gr_time_line = (MyListView) convertView.findViewById(R.id.gr_time_line);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.tip = (TextView) convertView.findViewById(R.id.tip);
				holder.title_jiaoyin = (TextView) convertView.findViewById(R.id.title_jiaoyin);
				holder.desc_jiaoyin = (TextView) convertView.findViewById(R.id.desc_jiaoyin);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (null != news) {
				if (position == 0) {
					holder.tip.setVisibility(View.VISIBLE);
				} else {
					holder.tip.setVisibility(View.GONE);
				}
				holder.desc_jiaoyin.setText(news.getTv_youji_footprint_desc());
				holder.time.setText(news.getTv_youji_timeline_time());
				holder.title_jiaoyin.setText(news.getTv_youji_timeline_title());
				holder.gr_time_line.setAdapter(
						new CommonAdapter<String>(context, news.getImageurl(), R.layout.item_youjixiangqing_image) {
							@Override
							public void convert(ViewHolderUntil helper, String item, final int position) {
								helper.getView(R.id.image).setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
								if (NetUtil.isNetworkAvailable(mContext) && isDownLoad == 0) {
									helper.setCubeImageByUrlXQ(R.id.image, item, imageLoader, mImageLoader);
								} else {
									helper.setCubeImageByUrlSDXQ(R.id.image, item, mImageLoader);
								}
								helper.getView(R.id.image).setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										downid_viewpager = position;
										showViewPage(null, news.getImageurl(), null, 1);
									}
								});
							}
						});
			}
			return convertView;
		}

		private final class ViewHolder {
			MyListView gr_time_line = null;
			TextView time = null;
			TextView tip = null;
			TextView title_jiaoyin = null;
			TextView desc_jiaoyin = null;
		}

	}

	private Thread thread = null;

	// 取得游记详情
	public void sendGetTravelInfoByIDActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetTravelInfoByIDAction();
			}
		};
		thread.start();
	}

	// 游记详情的字段
	private String tvl_id;// 游记id
	private String act_id;// 活动id
	private String u_id;// 用户id
	private String club_id;// 关联的俱乐部id
	private String tvl_cover;// 游记封面logo
	private String tvl_title;// 游记标题
	private String act_start_time;// 开始时间
	private String act_end_time;// 结束时间
	private Integer act_level;// 活动等级
	private String leader_name;// 领队名称
	private String leader_id;// 领队id
	private String city;// 地域（城市）
	private Integer distance;// 实际距离
	private String picture_url;// 截图路径
	private String trace_data;// 轨迹数据
	private String anchor_longitude;// 锚点经度
	private String anchor_latitude;// 锚点纬度
	private String tvl_type;// 游记类型(同活动类型)
	private String tvl_desc;// 游记描述
	private String footprint_data;// 游记足印（脚印数据app）
	private Integer team_number;// 队伍人数
	private String scenicsots;// 游记地区
	private String tvl_create_time;// 游记创建时间
	private String tvl_else;// 其他
	private Integer is_collect = 0;
	// 取得活动详情的字段
	private String act_strategy = null;// 攻略
	private String logistics; // 报名须知
	private int act_join_num_limit; // 参加活动人数限制
	private int confirmed_member = 0; // 已确认队员人数
	private int viceleadercount = 0; // 副队人数
	private int malecount = 0; // 男队人数
	private int femalecount = 0; // 女队人数
	private String act_entry_fee; // 参加活动费用
	private String tvl_desc_intro; // 游记简介
	private String act_weixin; // 活动微信号
	private String act_most_equip; // 参加活动准备基本装备
	private String head;// 用户头像
	private String club_name = null;// 俱乐部名字
	private String club_logo = null;// 俱乐部头像
	private String user_nickname = null;
	private String userid = null;
	// 轨迹的字段
	private Integer index = 0;
	private Integer count = 10;

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

	private void getDataFromServer(GetTravelInfoByIDResponsePayload payload) {
		head = payload.getHead();// 用户头像
		userid = payload.getU_id();
		user_nickname = payload.getNickname();// 名字
		club_name = payload.getClub_name();// 俱乐部名字
		club_logo = payload.getClub_logo();// 俱乐部头像
		tvl_else = payload.getTvl_else();
		tvl_create_time = payload.getTvl_create_time();
		tvl_id = payload.getTvl_id();
		club_id = payload.getClub_id();
		trace_data = payload.getTrace_data();
		footprint_data = payload.getFootprint_data();
		act_start_time = payload.getAct_start_time();
		act_level = payload.getAct_level() != null ? payload.getAct_level() : 0;
		act_end_time = payload.getAct_end_time();
		tvl_title = payload.getTvl_title();
		picture_url = payload.getPicture_url();
		tvl_desc = payload.getTvl_desc();
		anchor_longitude = payload.getAnchor_longitude();
		anchor_latitude = payload.getAnchor_latitude();
		u_id = payload.getU_id();
		city = payload.getCity();
		scenicsots = payload.getScenicsots();
		is_collect = payload.getIs_collect() != null ? payload.getIs_collect() : 0;
		distance = payload.getDistance() != null ? payload.getDistance() : 0;
		leader_id = payload.getLeader_id();
		team_number = payload.getTeam_number() != null ? payload.getTeam_number() : 0;
		leader_name = payload.getLeader_name();
		tvl_cover = payload.getTvl_cover() != null ? payload.getTvl_cover() : "";
		tvl_type = payload.getTvl_type();
		logistics = payload.getLogistics(); // 后勤
		act_join_num_limit = payload.getAct_join_num_limit() != null ? payload.getAct_join_num_limit() : 0;// 参加活动人数限制
		confirmed_member = payload.getConfirmed_member() != null ? payload.getConfirmed_member() : 0; // 已确认队员人数
		viceleadercount = payload.getViceleadercount() != null ? payload.getViceleadercount() : 0; // 副队人数
		malecount = payload.getMalecount() != null ? payload.getMalecount() : 0; // 男队人数
		femalecount = payload.getFemalecount() != null ? payload.getFemalecount() : 0; // 女队人数
		act_entry_fee = payload.getAct_entry_fee(); // 参加活动费用
		tvl_desc_intro = payload.getAct_desc_intro(); // 游记简介
		act_weixin = payload.getAct_weixin(); // 活动微信号
		act_most_equip = payload.getAct_most_equip(); // 参加活动准备基本装备
		team_memberList.clear();
		if (!TextUtils.isEmpty(payload.getTeam_member())) {
			team_memberList = gson.fromJson(payload.getTeam_member(),
					new TypeToken<List<GetActivityTeamMemberResponsePayloadItem>>() {
					}.getType()); // 队员：存储格式： 队员1,队员2,...
		}
	}

	/**
	 * 更新数据到数据库
	 */
	public void UpDatacollet(int collet) {
		youjiBaseDao.updatacollet(collet, tvl_id);
	}

	private void setDao() {
		// 删除保存活动图片的文件夹
		File appDir = new File(Environment.getExternalStorageDirectory(), "outdooryouji");
		if (appDir.exists()) {
			DataCleanManager.deleteFile(appDir);
		}
		youjiXiangqing.setTvl_create_time(tvl_create_time);
		youjiXiangqing.setAct_start_time(act_start_time);
		youjiXiangqing.setAct_end_time(act_end_time);
		youjiXiangqing.setTvl_title(tvl_title);
		youjiXiangqing.setAct_level(act_level);
		youjiXiangqing.setTvl_desc(tvl_desc);
		youjiXiangqing.setTvl_desc_intro(tvl_desc_intro);
		youjiXiangqing.setAnchor_longitude(anchor_longitude);
		youjiXiangqing.setAnchor_latitude(anchor_latitude);
		youjiXiangqing.setAct_id(act_id);
		youjiXiangqing.setU_id(u_id);
		youjiXiangqing.setCity(city);
		youjiXiangqing.setIs_collect(is_collect);
		youjiXiangqing.setDistance(distance);
		youjiXiangqing.setLeader_id(leader_id);
		youjiXiangqing.setTeam_number(team_number);
		youjiXiangqing.setAct_type(tvl_type);
		youjiXiangqing.setLeader_name(leader_name);
		youjiXiangqing.setScenicsots(scenicsots);

		youjiXiangqing.setTvl_type(tvl_type);
		youjiXiangqing.setTvl_id(tvl_id);
		youjiXiangqing.setAgreeCount(agreeCount);
		youjiXiangqing.setIsAgree(isAgree);
		youjiXiangqing.setUser_nickname(user_nickname);
		youjiXiangqing.setClub_name(club_name);
		// 活动详情的字段
		youjiXiangqing.setAct_strategy(act_strategy);
		youjiXiangqing.setLogistics(logistics);
		youjiXiangqing.setAct_join_num_limit(act_join_num_limit);
		youjiXiangqing.setConfirmed_member(confirmed_member);
		youjiXiangqing.setViceleadercount(viceleadercount);
		youjiXiangqing.setMalecount(malecount);
		youjiXiangqing.setFemalecount(femalecount);
		youjiXiangqing.setAct_entry_fee(act_entry_fee);
		youjiXiangqing.setAct_weixin(act_weixin);
		youjiXiangqing.setAct_most_equip(act_most_equip);
		youjiXiangqing.setTvl_else(tvl_else);
		new Thread(new Runnable() {
			// 解析存储带有的图片和脚印数据
			JSONArray footprint;// 一天的所有脚印数据的json数组，不超过五个脚印
			JSONArray imgs;// 一天的一个脚印数据的图片的json数组，不超过五张图片
			JSONObject oneFootprint = null;// 一个脚印的json对象
			// 轨迹数据
			JSONArray traces;
			JSONObject trace;
			JSONObject trace_trace_data;
			JSONArray trace_trace_datas;

			@Override
			public void run() {
				if (!TextUtils.isEmpty(club_logo)) {
					youjiXiangqing.setClub_logo(ImageControl.saveImageToGallery(YoujiXiangQingActivity.this,
							ImageControl.getHttpBitmap(club_logo), 3));
				} else {
					youjiXiangqing.setClub_logo("");
				}
				if (!TextUtils.isEmpty(head)) {
					youjiXiangqing.setHead(ImageControl.saveImageToGallery(YoujiXiangQingActivity.this,
							ImageControl.getHttpBitmap(head), 3));
				} else {
					youjiXiangqing.setHead("");
				}
				if (!TextUtils.isEmpty(picture_url)) {
					youjiXiangqing.setPicture_url(ImageControl.saveImageToGallery(YoujiXiangQingActivity.this,
							ImageControl.getHttpBitmap(picture_url), 3));
				} else {
					youjiXiangqing.setPicture_url("");
				}
				if (!TextUtils.isEmpty(tvl_cover)) {
					youjiXiangqing.setTvl_cover(ImageControl.saveImageToGallery(YoujiXiangQingActivity.this,
							ImageControl.getHttpBitmap(tvl_cover), 3));
				} else {
					youjiXiangqing.setTvl_cover("");
				}
				// 轨迹数据
				try {
					JSONArray array = new JSONArray(trace_data);
					traces = new JSONArray();
					for (int i = 0; i < array.length(); i++) {
						trace = new JSONObject();
						if (!TextUtils.isEmpty(picture_url)) {
							trace.put("picture_url", ImageControl.saveImageToGallery(YoujiXiangQingActivity.this,
									ImageControl.getHttpBitmap(array.getJSONObject(i).getString("picture_url")), 3));
						} else {
							trace.put("picture_url", "");
						}
						trace.put("act_real_end_time", array.getJSONObject(i).getString("act_real_end_time"));
						trace.put("act_real_start_time", array.getJSONObject(i).getString("act_real_start_time"));
						trace.put("name", array.getJSONObject(i).getString("name"));
						JSONArray msgString = array.getJSONObject(i).getJSONArray("trace_data");
						trace_trace_datas = new JSONArray();
						for (int j = 0; j < msgString.length(); j++) {
							trace_trace_data = new JSONObject();
							try {
								if (!TextUtils.isEmpty(msgString.toString())) {
									trace_trace_data.put("longitude",
											msgString.getJSONObject(j).getString("longitude"));
									trace_trace_data.put("description",
											msgString.getJSONObject(j).getString("description"));
									trace_trace_data.put("latitude", msgString.getJSONObject(j).getString("latitude"));
									trace_trace_data.put("name", msgString.getJSONObject(j).getString("name"));
									trace_trace_datas.put(trace_trace_data);
								} else {
									trace_trace_datas.put("[]");
								}
							} catch (Exception e) {
								trace_trace_datas.put("[]");
							}
						}
						trace.put("trace_data", trace_trace_datas);
						traces.put(trace);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				youjiXiangqing.setTrace_data(traces.toString().replace("\\", ""));

				// 脚印数据
				try {
					JSONArray array = new JSONArray(footprint_data);
					footprint = new JSONArray();
					for (int i = 0; i < array.length(); i++) {
						oneFootprint = new JSONObject();
						imgs = new JSONArray();
						for (int j = 0; j < array.getJSONObject(i).getJSONArray("imgs").length(); j++) {
							String imagesString = array.getJSONObject(i).getJSONArray("imgs").get(j).toString();
							if (!TextUtils.isEmpty(imagesString)) {
								imgs.put(ImageControl.saveImageToGallery(YoujiXiangQingActivity.this,
										ImageControl.getHttpBitmap(imagesString), 3));
							} else {
								imgs.put("");
							}
						}
						oneFootprint.put("imgs", imgs);
						oneFootprint.put("footprint_time", array.getJSONObject(i).getString("footprint_time"));
						oneFootprint.put("footprint_desc", array.getJSONObject(i).getString("footprint_desc"));
						oneFootprint.put("footprint_title", array.getJSONObject(i).getString("footprint_title"));
						footprint.put(oneFootprint);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				youjiXiangqing.setFootprint_data(footprint.toString().replace("\\", ""));

				List<HuoDongDuiYuan> HuoDongDuiYuanBeansTemp = new ArrayList<HuoDongDuiYuan>();
				if (HuoDongDuiYuanBeans != null && HuoDongDuiYuanBeans.size() != 0) {
					for (int i = 0; i < HuoDongDuiYuanBeans.size(); i++) {
						HuoDongDuiYuanBean = new HuoDongDuiYuan();
						HuoDongDuiYuanBean.setUserid(HuoDongDuiYuanBeans.get(i).getHead());
						HuoDongDuiYuanBean.setHead(ImageControl.saveImageToGallery(YoujiXiangQingActivity.this,
								ImageControl.getHttpBitmap(HuoDongDuiYuanBeans.get(i).getHead()), 3));
						HuoDongDuiYuanBeansTemp.add(HuoDongDuiYuanBean);
					}
				}
				youjiXiangqing.setTeam_member(gson.toJson(HuoDongDuiYuanBeansTemp));
				if (youjiBaseDao.searchByTvl_id(tvl_id) != null) {// 当数据库中数据不为空时只做更新操作，反之保存操作
					youjiBaseDao.deletebytvlid(tvl_id);
					youjiBaseDao.update(youjiXiangqing);
				} else {
					youjiBaseDao.add(youjiXiangqing);
				}
				if (isshowdialog()) {
					closedialog();
				}
				isDownLoad = 1;
				handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
			}
		}).start();

	}

	private void getDao() {
		youjiXiangqing = youjiBaseDao.searchByTvl_id(tvl_id);
		if (youjiXiangqing != null) {
			footprint_data = youjiXiangqing.getFootprint_data();
			act_start_time = youjiXiangqing.getAct_start_time();
			act_end_time = youjiXiangqing.getAct_end_time();
			act_level = youjiXiangqing.getAct_level();
			tvl_title = youjiXiangqing.getTvl_title();
			picture_url = youjiXiangqing.getPicture_url();
			tvl_desc = youjiXiangqing.getTvl_desc();
			tvl_desc_intro = youjiXiangqing.getTvl_desc_intro();
			anchor_longitude = youjiXiangqing.getAnchor_longitude();
			u_id = youjiXiangqing.getU_id();
			city = youjiXiangqing.getCity();
			is_collect = youjiXiangqing.getIs_collect() != null ? youjiXiangqing.getIs_collect() : 0;
			distance = youjiXiangqing.getDistance() != null ? youjiXiangqing.getDistance() : 0;
			leader_id = youjiXiangqing.getLeader_id();
			team_number = youjiXiangqing.getTeam_number() != null ? youjiXiangqing.getTeam_number() : 0;
			tvl_type = youjiXiangqing.getAct_type();
			leader_name = youjiXiangqing.getLeader_name();
			scenicsots = youjiXiangqing.getScenicsots();
			tvl_cover = youjiXiangqing.getTvl_cover();
			tvl_type = youjiXiangqing.getTvl_type();
			tvl_id = youjiXiangqing.getTvl_id();
			trace_data = youjiXiangqing.getTrace_data();
			agreeCount = youjiXiangqing.getAgreeCount() != null ? youjiXiangqing.getAgreeCount() : 0;
			isAgree = youjiXiangqing.getIsAgree() != null ? youjiXiangqing.getIsAgree() : 0;
			user_nickname = youjiXiangqing.getUser_nickname();
			club_name = youjiXiangqing.getClub_name();
			// =活动详情的字段
			act_strategy = youjiXiangqing.getAct_strategy();
			logistics = youjiXiangqing.getLogistics();
			act_join_num_limit = youjiXiangqing.getAct_join_num_limit() != null ? youjiXiangqing.getAct_join_num_limit()
					: 0;
			confirmed_member = youjiXiangqing.getConfirmed_member();
			viceleadercount = youjiXiangqing.getViceleadercount();
			malecount = youjiXiangqing.getMalecount();
			femalecount = youjiXiangqing.getFemalecount();
			act_entry_fee = youjiXiangqing.getAct_entry_fee();
			act_weixin = youjiXiangqing.getAct_weixin();
			act_most_equip = youjiXiangqing.getAct_most_equip();
			tvl_else = youjiXiangqing.getTvl_else();
			tvl_create_time = youjiXiangqing.getTvl_create_time();
			head = youjiXiangqing.getHead();
			club_logo = youjiXiangqing.getClub_logo();
			team_memberList.clear();
			if (!TextUtils.isEmpty(youjiXiangqing.getTeam_member())) {
				team_memberList = gson.fromJson(youjiXiangqing.getTeam_member(),
						new TypeToken<List<GetActivityTeamMemberResponsePayloadItem>>() {
						}.getType()); // 队员：存储格式： 队员1,队员2,...
			}
			handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
		} else {
			showToastNet();
		}
	}

	// 取得轨迹列表
	public void sendGetTraceDataListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetTraceDataListAction();
			}
		};
		thread.start();
	}

	// 收藏与清除游记
	public void sendCollectionActivityAsyn() {
		thread = new Thread() {
			public void run() {
				getCollectionActivityAction();
			}
		};
		thread.start();
	}

	public void GetTraceDataListAction() {
		GetTraceDataListRequest request = new GetTraceDataListRequest();
		GetTraceDataListRequestParameter parameter = new GetTraceDataListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setIndex(index);
		parameter.setCount(count);

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
					GetTraceDataListResponse response = new GetTraceDataListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetTraceDataListResponsePayload payload = (GetTraceDataListResponsePayload) response.getPayload();
					// handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);
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

	public void getCollectionActivityAction() {
		CollectTravelRequest request = new CollectTravelRequest();
		CollectTravelRequestParameter parameter = new CollectTravelRequestParameter();
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
					CollectTravelResponse response = new CollectTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					CollectTravelResponsePayload payload = (CollectTravelResponsePayload) response.getPayload();
					// state = payload.getState();
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
		parameter.setId(tvl_id);
		parameter.setType(2);
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
			case CommonUtility.SERVEROK1://
				radiobutton_select_one.setText("分享");
				radiobutton_select_three.setText("收藏");
				radiobutton_select_one.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_fenxiang2, 0, 0);
				radiobutton_select_three.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_shoucang2, 0,
						0);
				switch (isDownLoad) {// 是否下载
				case 0:
					radiobutton_select_two.setText("下载");
					radiobutton_select_two.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_xiazai, 0,
							0);
					break;
				case 1:
					radiobutton_select_two.setText("清除");
					radiobutton_select_two.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_shanchu, 0,
							0);
					break;
				}
				switch (is_collect) {// 是否收藏
				case 0:
					radiobutton_select_three.setText("收藏");
					radiobutton_select_three.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_shoucang2,
							0, 0);
					break;
				case 1:
					radiobutton_select_three.setText("已收藏");
					radiobutton_select_three.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shoucang_d, 0, 0);
					break;
				}
				if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
					if (NetUtil.isNetworkAvailable(mContext) && isDownLoad == 0) {
						mImageLoader.displayImage(tvl_cover, iv, options);
						mImageLoader.displayImage(club_logo, juleb_icon, options);
						mImageLoader.displayImage(head, pep_icon, options);
					} else {
						mImageLoader.displayImage("file://" + youjiXiangqing.getTvl_cover(), iv, options);
						mImageLoader.displayImage("file://" + youjiXiangqing.getHead(), pep_icon, options);
						mImageLoader.displayImage("file://" + youjiXiangqing.getClub_logo(), juleb_icon, options);
					}
				} else {
					mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, iv, options);
					mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, pep_icon, options);
					mImageLoader.displayImage("drawable://" + R.drawable.loadingnot, juleb_icon, options);
				}
				// 的head字段
				if (!TextUtils.isEmpty(user_nickname)) {
					youji_zuoze_name.setText(user_nickname);// 游记的作者名字
				} else {
					youji_zuoze_name.setText("没有昵称");// 游记的作者名字
				}
				youji_write_time.setText(TimeDateUtils.formatDateFromDatabaseTimeSF(tvl_create_time));// 游记的创建时间
				tv11.setText(tvl_title);
				String start = TimeDateUtils.formatDateFromDatabaseTime(act_start_time);
				String end = TimeDateUtils.formatDateFromDatabaseTime(act_end_time);
				if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
					tv22.setText(start.replace("-", ".") + "-" + end.substring(5, end.length()).replace("-", "."));
				}
				tv31.setText(act_entry_fee + "元");// 人均费用
				tv32.setText(act_level + "级");// 活动等级
				tv33.setText(city);// 活动地区
				tv41.setText(tvl_type);// 活动类型

				if (!TextUtils.isEmpty(club_name)) {
					juleb_name.setText(club_name);// 俱乐部名字
				} else {
					ll_julebu.setVisibility(View.GONE);
				}
				// Double licheng = 0.0;
				// ArrayList<LatLng> LatLnglist = new ArrayList<LatLng>();
				// if (trace_data != null) {
				// try {
				// JSONArray array = new JSONArray(trace_data);
				// if (array.length() != 0) {
				// for (int i = 0; i < array.length(); i++) {
				// for (int j = 0; j <
				// array.getJSONObject(i).getJSONArray("trace_data").length();
				// j++) {
				// LatLng latlng = new LatLng(
				// array.getJSONObject(i).getJSONArray("trace_data").getJSONObject(j)
				// .getDouble("latitude"),
				// array.getJSONObject(i).getJSONArray("trace_data").getJSONObject(j)
				// .getDouble("longitude"));
				// LatLnglist.add(latlng);
				// }
				// }
				// }
				// if (LatLnglist.size() != 0) {
				// for (int j = 0; j < LatLnglist.size(); j++) {
				// if (j != LatLnglist.size() - 1) {
				// licheng += Math
				// .abs(DistanceUtil.getDistance(LatLnglist.get(j),
				// LatLnglist.get(j + 1)));
				// }
				// }
				// }
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
				// }
				// if (licheng > 1000) {
				// tv42.setText(ParseOject.StringToDouble(licheng / 1000) +
				// "km");
				// } else {
				// tv42.setText(ParseOject.StringToDouble(licheng) + "m");
				// }
				tv42.setText(distance / 1000 + "km");
				// 给listview添加数据，用于适配listadapter
				youJiXiangqinglist.clear();
				// 游记简介的数据
				youjiXiangqingTemp = new YoujiXiangqing();
				youjiXiangqingTemp.setType(TYPE_Trip_description);
				youjiXiangqingTemp.setTvl_desc_intro(tvl_desc_intro);
				youJiXiangqinglist.add(youjiXiangqingTemp);
				// 轨迹的数据
				youjiXiangqingTemp = new YoujiXiangqing();
				youjiXiangqingTemp.setType(TYPE_track);
				youjiXiangqingTemp.setTrace_data(trace_data);
				youJiXiangqinglist.add(youjiXiangqingTemp);
				// 足迹的数据
				youjiXiangqingTemp = new YoujiXiangqing();
				youjiXiangqingTemp.setType(TYPE_43_footprints);
				youjiXiangqingTemp.setFootprint_data(footprint_data);
				youJiXiangqinglist.add(youjiXiangqingTemp);
				if (isActid) {
					// 游记的数据
					youjiXiangqingTemp = new YoujiXiangqing();
					youjiXiangqingTemp.setTvl_desc(tvl_desc);
					youjiXiangqingTemp.setType(TYPE_Cost);
					youJiXiangqinglist.add(youjiXiangqingTemp);
					// 装备的数据
					youjiXiangqingTemp = new YoujiXiangqing();
					youjiXiangqingTemp.setAct_most_equip(act_most_equip);
					youjiXiangqingTemp.setType(TYPE_Equipment);
					youJiXiangqinglist.add(youjiXiangqingTemp);
					// 后勤的数据
					youjiXiangqingTemp = new YoujiXiangqing();
					youjiXiangqingTemp.setLogistics(logistics);
					youjiXiangqingTemp.setType(TYPE_Logistics);
					youJiXiangqinglist.add(youjiXiangqingTemp);
					// 其他的数据
					youjiXiangqingTemp = new YoujiXiangqing();
					youjiXiangqingTemp.setTvl_else(tvl_else);
					youjiXiangqingTemp.setType(TYPE_Other);
					youJiXiangqinglist.add(youjiXiangqingTemp);
					// 队伍状况的数据
					youjiXiangqingTemp = new YoujiXiangqing();
					youjiXiangqingTemp.setConfirmed_member(confirmed_member);
					youjiXiangqingTemp.setMalecount(malecount);
					youjiXiangqingTemp.setFemalecount(femalecount);
					youjiXiangqingTemp.setType(TYPE_Status);
					youjiXiangqingTemp.setAct_join_num_limit(act_join_num_limit);
					youjiXiangqingTemp.setAct_weixin(act_weixin);
					youjiXiangqingTemp.setLeader_name(leader_name);
					HuoDongDuiYuanBeans.clear();
					if (team_memberList != null && team_memberList.size() != 0) {
						for (int i = 0; i < team_memberList.size(); i++) {
							HuoDongDuiYuanBean = new HuoDongDuiYuan();
							HuoDongDuiYuanBean.setHead(team_memberList.get(i).getHead());
							HuoDongDuiYuanBean.setUserid(team_memberList.get(i).getUserid());
							HuoDongDuiYuanBeans.add(HuoDongDuiYuanBean);
						}
						youjiXiangqingTemp.setTeam_memberList(HuoDongDuiYuanBeans);
					}
					youJiXiangqinglist.add(youjiXiangqingTemp);
				}
				// 加入适配器中并刷新
				myListAdpter.notifyDataSetChanged();
				// 设置listiew的属性
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
						if (youJiXiangqinglist != null && youJiXiangqinglist.size() != 0) {
							youjiXiangqingTemp = (YoujiXiangqing) myListAdpter.getItem(postion);
							switch (youjiXiangqingTemp.getType()) {
							case TYPE_Trip_description:
								type.setVisibility(View.GONE);
								if (firstVisibleItem == 1) {
									type.setVisibility(View.VISIBLE);
								}
								type.setText(TYPE_Trip_description_Str);
								break;
							case TYPE_track:
								type.setVisibility(View.VISIBLE);
								type.setText(TYPE_track_Str);
								break;
							case TYPE_43_footprints:
								type.setVisibility(View.VISIBLE);
								type.setText(TYPE_43_footprints_Str);
								break;
							// case TYPE_Cost:
							// type.setVisibility(View.VISIBLE);
							// type.setText(TYPE_Cost_Str);
							// break;
							case TYPE_Equipment:
								type.setVisibility(View.VISIBLE);
								type.setText(TYPE_Equipment_Str);
								break;
							case TYPE_Logistics:
								type.setVisibility(View.VISIBLE);
								type.setText(TYPE_Logistics_Str);
								break;
							case TYPE_Other:
								type.setVisibility(View.VISIBLE);
								type.setText(TYPE_Other_Str);
								break;
							case TYPE_Status:
								type.setVisibility(View.VISIBLE);
								type.setText(TYPE_Status_Str);
								break;
							default:
								break;
							}
						}
					}
				});
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:
				switch (is_collect) {// 是否收藏
				case 0:
					showToast("收藏成功");
					radiobutton_select_three.setText("已收藏");
					radiobutton_select_three.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.shoucang_d, 0, 0);
					is_collect = 1;
					UpDatacollet(1);
					break;
				case 1:
					showToast("取消收藏");
					radiobutton_select_three.setText("收藏");
					radiobutton_select_three.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_shoucang2,
							0, 0);
					is_collect = 0;
					UpDatacollet(0);
					break;
				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK3:
				switch (isDownLoad) {// 是否下载成功
				case 0:
					showToast("清除成功");
					radiobutton_select_two.setText("下载");
					radiobutton_select_two.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_xiazai, 0,
							0);
					break;
				case 1:
					showToast("下载成功");
					radiobutton_select_two.setText("清除");
					radiobutton_select_two.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.select_bt_shanchu, 0,
							0);
					break;
				}
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
			default:
				break;
			}
		};
	};

}
