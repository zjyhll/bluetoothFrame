package com.hwacreate.outdoor.mainFragment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.MyListAdpter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.bean.HuoDongImages;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.contactlist.ContactListActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.home.DetailedListActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.HomeTapDangjiBean;
import com.hwacreate.outdoor.ormlite.bean.HomeTapTopImage;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DataCleanManager;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.MyViewPager;
import com.hwacreate.outdoor.view.MyViewPager.OnSingleTouchListener;
import com.keyhua.outdoor.protocol.GetClubActivityList.GetClubActivityListRequest;
import com.keyhua.outdoor.protocol.GetClubActivityList.GetClubActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetClubActivityList.GetClubActivityListResponse;
import com.keyhua.outdoor.protocol.GetClubActivityList.GetClubActivityListResponsePayload;
import com.keyhua.outdoor.protocol.GetClubActivityList.GetClubActivityListResponsePayloadListItem;
import com.keyhua.outdoor.protocol.GetCollectActivityList.GetCollectActivityListRequest;
import com.keyhua.outdoor.protocol.GetCollectActivityList.GetCollectActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetCollectActivityList.GetCollectActivityListResponse;
import com.keyhua.outdoor.protocol.GetCollectActivityList.GetCollectActivityListResponsePayload;
import com.keyhua.outdoor.protocol.GetCollectActivityList.GetCollectActivityListResponsePayloadListItem;
import com.keyhua.outdoor.protocol.GetHotActivityList.GetHotActivityListRequest;
import com.keyhua.outdoor.protocol.GetHotActivityList.GetHotActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetHotActivityList.GetHotActivityListResponse;
import com.keyhua.outdoor.protocol.GetHotActivityList.GetHotActivityListResponsePayload;
import com.keyhua.outdoor.protocol.GetHotActivityList.GetHotActivityListResponsePayloadListItem;
import com.keyhua.outdoor.protocol.GetTopBannerImageListAction.GetTopBannerImageListListItem;
import com.keyhua.outdoor.protocol.GetTopBannerImageListAction.GetTopBannerImageListRequest;
import com.keyhua.outdoor.protocol.GetTopBannerImageListAction.GetTopBannerImageListRequestParameter;
import com.keyhua.outdoor.protocol.GetTopBannerImageListAction.GetTopBannerImageListResponse;
import com.keyhua.outdoor.protocol.GetTopBannerImageListAction.GetTopBannerImageListResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author 曾金叶 主页
 * @2015-8-5 @上午10:15:40
 */
public class HuoDongFragment extends BaseFragment implements OnPageChangeListener, OnItemClickListener {
	// pager
	private MyViewPager viewPager = null;
	private PagerAdapter pagerAdapter = null;
	private LinearLayout indicatorLayout = null;
	// pager上的数据
	private ArrayList<View> views = null;
	private ImageView[] indicators = null;
	private ImageHandler handler = new ImageHandler(new WeakReference<HuoDongFragment>(this));
	//
	private TextView tab1 = null;// 登山
	private TextView tab2 = null;// 徒步
	private TextView tab3 = null;// 骑行
	private TextView tab4 = null;// 自驾
	private TextView tab5 = null;// 游轮
	private TextView tab6 = null;// 摄影
	private TextView tab7 = null;// 腐败
	private TextView tab8 = null;// 休闲
	private TextView tab9 = null;// 露营
	private TextView tab10 = null;// 亲子
	// 热门。俱乐部。收藏
	private LinearLayout huodong_remen = null;
	private LinearLayout huodong_julebu = null;
	private LinearLayout huodong_shoucang = null;
	private View huodong_remen_view = null;
	private View huodong_julebu_view = null;
	private View huodong_shoucang_view = null;
	// 头部
	private View headerLayout = null;
	private LinearLayout headerParent = null;
	// 上拉下拉刷新
	LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;
	// 列表中数据
	private List<HomeTapDangjiBean> mDatas = null;
	private List<HomeTapDangjiBean> mDatasTemp = null;
	private HomeTapDangjiBean homeTapDangjiBean = null;
	// 顶部的图片
	private HomeTapTopImage topImage = null;
	private List<HomeTapTopImage> topImageDatas = null;
	private BaseDao<HomeTapTopImage> topImageDao = null;
	// 临时保存图片
	private List<HuoDongImages> HuoDongImagesBeans = null;
	private HuoDongImages HuoDongImages = null;
	// json
	private Gson gson = null;
	public int index = 0;
	public int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isFristDing = true;
	private boolean isNet = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mainfrag_home, null);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.tab1:// 登山
			bundle.putString("type", "登山");
			bundle.putString("Name", getString(R.string.homefrg_tap1));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab2:// 徒步
			bundle.putString("type", "徒步");
			bundle.putString("Name", getString(R.string.homefrg_tap2));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab3:// 骑行
			bundle.putString("type", "骑行");
			bundle.putString("Name", getString(R.string.homefrg_tap3));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab4:// 自驾
			bundle.putString("type", "自驾");
			bundle.putString("Name", getString(R.string.homefrg_tap4));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab5:// 游轮
			bundle.putString("type", "游轮");
			bundle.putString("Name", getString(R.string.homefrg_tap5));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab6:// 摄影
			bundle.putString("type", "摄影");
			bundle.putString("Name", getString(R.string.homefrg_tap6));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab7:// 腐败
			bundle.putString("type", "腐败");
			bundle.putString("Name", getString(R.string.homefrg_tap7));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab8:// 休闲
			bundle.putString("type", "休闲");
			bundle.putString("Name", getString(R.string.homefrg_tap8));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab9:// 露营
			bundle.putString("type", "露营");
			bundle.putString("Name", getString(R.string.homefrg_tap9));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.tab10:// 亲子
			bundle.putString("type", "亲子");
			bundle.putString("Name", getString(R.string.homefrg_tap10));
			openActivity(DetailedListActivity.class, bundle);
			isFrist = false;
			break;
		case R.id.top_tv_right:
			openActivity(ContactListActivity.class);
			isFrist = false;
			break;
		case R.id.huodong_remen:
			if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
				showdialog();
				isNet = true;
				type_tap = 1;
				index = 0;
				count = 10;
				Loadmore = false;
				LoadmoreData = false;
				listadapter3 = null;
				sendGetActivityListActionAsyn();
				huodong_remen_view.setVisibility(View.VISIBLE);
				huodong_julebu_view.setVisibility(View.GONE);
				huodong_shoucang_view.setVisibility(View.GONE);
			} else {
				isNet = false;
				showToastNet();
			}
			break;
		case R.id.huodong_julebu:
			if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
				if (!TextUtils.isEmpty(App.getInstance().getAut())) {
					showdialog();
					type_tap = 2;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					listadapter3 = null;
					sendClubActivityListActionAsyn();
					huodong_remen_view.setVisibility(View.GONE);
					huodong_julebu_view.setVisibility(View.VISIBLE);
					huodong_shoucang_view.setVisibility(View.GONE);
				} else {
					showToastDengLu();
					openActivity(LoginActivity.class);
				}
			} else {
				isNet = false;
				showToastNet();
			}
			break;
		case R.id.huodong_shoucang:
			if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
				if (!TextUtils.isEmpty(App.getInstance().getAut())) {
					showdialog();
					type_tap = 3;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					listadapter3 = null;
					sendCollectActivityListAsyn();
					huodong_remen_view.setVisibility(View.GONE);
					huodong_julebu_view.setVisibility(View.GONE);
					huodong_shoucang_view.setVisibility(View.VISIBLE);
				} else {
					showToastDengLu();
					openActivity(LoginActivity.class);
				}
			} else {
				isNet = false;
				showToastNet();
			}
			break;
		default:
			break;
		}
	}

	private int type_tap = 1;

	/**
	 * 数据的初始化
	 */
	public void initDao() {
		// 数据操作
		gson = new Gson();
		mDatas = new ArrayList<HomeTapDangjiBean>();
		mDatasTemp = new ArrayList<HomeTapDangjiBean>();
		HuoDongImagesBeans = new ArrayList<HuoDongImages>();
		topImage = new HomeTapTopImage();
		topImageDao = new BaseDao<HomeTapTopImage>(topImage, getActivity());
		topImageDatas = new ArrayList<HomeTapTopImage>();
	}

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		changeTap();
		radiobutton_select_huodong.setChecked(true);
		list = new ArrayList<GetHotActivityListResponsePayloadListItem>();
		listTemp = new ArrayList<GetHotActivityListResponsePayloadListItem>();
		listClubActivityList = new ArrayList<GetClubActivityListResponsePayloadListItem>();
		listClubActivityListTemp = new ArrayList<GetClubActivityListResponsePayloadListItem>();
		listCollectActivityList = new ArrayList<GetCollectActivityListResponsePayloadListItem>();
		listCollectActivityListTemp = new ArrayList<GetCollectActivityListResponsePayloadListItem>();
		// 实例化视图控件
		views = new ArrayList<View>();
		// 头部
		headerLayout = LayoutInflater.from(getActivity()).inflate(R.layout.head_homefrg, null, true);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		headerLayout.setLayoutParams(params);
		viewPager = (MyViewPager) headerLayout.findViewById(R.id.vp_home);
		viewPager.setLayoutParams(
				new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
		indicatorLayout = (LinearLayout) headerLayout.findViewById(R.id.indicator);
		// 10个按钮
		tab1 = (TextView) headerLayout.findViewById(R.id.tab1);
		tab2 = (TextView) headerLayout.findViewById(R.id.tab2);
		tab3 = (TextView) headerLayout.findViewById(R.id.tab3);
		tab4 = (TextView) headerLayout.findViewById(R.id.tab4);
		tab5 = (TextView) headerLayout.findViewById(R.id.tab5);
		tab6 = (TextView) headerLayout.findViewById(R.id.tab6);
		tab7 = (TextView) headerLayout.findViewById(R.id.tab7);
		tab8 = (TextView) headerLayout.findViewById(R.id.tab8);
		tab9 = (TextView) headerLayout.findViewById(R.id.tab9);
		tab10 = (TextView) headerLayout.findViewById(R.id.tab10);
		huodong_remen = (LinearLayout) headerLayout.findViewById(R.id.huodong_remen);
		huodong_julebu = (LinearLayout) headerLayout.findViewById(R.id.huodong_julebu);
		huodong_shoucang = (LinearLayout) headerLayout.findViewById(R.id.huodong_shoucang);
		huodong_remen_view = (View) headerLayout.findViewById(R.id.huodong_remen_view);
		huodong_julebu_view = (View) headerLayout.findViewById(R.id.huodong_julebu_view);
		huodong_shoucang_view = (View) headerLayout.findViewById(R.id.huodong_shoucang_view);
		lv_home = (ListView) getActivity().findViewById(R.id.lv_home);
		//
		headerParent = new LinearLayout(getActivity());
		headerParent.addView(headerLayout);
		lv_home.addHeaderView(headerParent);
		lv_home.setOnItemClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		isFrist = false;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
			isNet = true;
		} else {
			isNet = false;
		}
		if (isFristDing) {
			getNowLocation();
			isFristDing = false;
		} else if (TextUtils.equals(App.getInstance().getContactCity(), "定位失败")) {
			getNowLocation();
		} else if (!isNet) {
			top_tv_right.setText("定位失败");
		} else if (!TextUtils.isEmpty(App.getInstance().getContactCity())) {
			top_tv_right.setText(App.getInstance().getContactCity());
		}
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {

		}
		if (App.getInstance().isPush() || isLoadMore()) {
			App.getInstance().setPush(false);
			showdialog();
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			switch (type_tap) {
			case 1:
				sendGetActivityListActionAsyn();
				break;
			case 2:
				sendClubActivityListActionAsyn();
				break;
			case 3:
				sendCollectActivityListAsyn();
				break;

			default:
				break;
			}
		}
	}

	private boolean isLoadMore() {
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {
			return true;
		}
		return false;
	}

	/**
	 * 百度地图Start
	 * ----------------------------------------------------------------
	 */
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	public MyLocationListener mMyLocationListener;

	/**
	 * 获取当前城市
	 */
	public void getNowLocation() {
		// 百度地图
		mLocationClient = new LocationClient(getActivity().getApplicationContext());
		// 放于MyLocationListener()之前
		InitLocation();
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			App.getInstance().setContactCity(location.getCity() != null
					? location.getCity().substring(0, location.getCity().length() - 1) : "定位失败");
			top_tv_right.setText(App.getInstance().getContactCity());
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
	}

	/**
	 * 百度地图END
	 * ------------------------------------------------------------------
	 */
	@Override
	protected void onInitData() {
		initDao();
		initControl();
		showdialog();
		refreshAndLoadMore();
	}

	private void refreshAndLoadMore() {
		// 上下刷新START--------------------------------------------------------------------
		// 获取装载VIew的容器
		mPtrFrameLayout = (PtrFrameLayout) getActivity().findViewById(R.id.load_more_list_view_ptr_frame);
		// 获取view的引用
		loadMoreListViewContainer = (LoadMoreListViewContainer) getActivity()
				.findViewById(R.id.load_more_list_view_container);
		// 使用默认样式
		loadMoreListViewContainer.useDefaultHeader();
		// 加载更多数据，当列表滑动到最底部的时候，触发加载更多操作，
		// 这是需要从网络加载数据，或者是从数据库去读取数据
		// 给View 设置加载更多的Handler 去异步加载View需要显示的数据和VIew
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			// loadMoreListViewContainer调用onLoadMore传入loadMoreListViewContainer自身对象
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
					isNet = true;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					switch (type_tap) {
					case 1:
						sendGetActivityListActionAsyn();
						break;
					case 2:
						sendClubActivityListActionAsyn();
						break;
					case 3:
						sendCollectActivityListAsyn();
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
				if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
					try {// 刷新的时候把轮播停了
						handler.sendEmptyMessage(ImageHandler.MSG_BREAK_SILENT);
					} catch (Exception e) {
					}
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					switch (type_tap) {
					case 1:
						sendGetActivityListActionAsyn();
						break;
					case 2:
						sendClubActivityListActionAsyn();
						break;
					case 3:
						sendCollectActivityListAsyn();
						break;
					default:
						break;
					}
					sendTopBannerImageListAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					if (isshowdialog()) {
						closedialog();
					}
					isNet = false;
					initTopImage();
					CommonAdapter<NoDataBean> listadapter3 = null;
					List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
					NoDataBean nodata = new NoDataBean();
					nodata.setTitle(getActivity().getResources().getString(R.string.home_page));
					nodatas.add(nodata);
					listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
						@Override
						public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
							helper.setText(R.id.news_title, item.getTitle());
						}
					};
					lv_home.setAdapter(listadapter3);
					mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
				}
			}
		});
		// auto load data
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					mPtrFrameLayout.autoRefresh(true);
					// mHandler.sendEmptyMessage(1);
				} catch (Exception e) {
				}
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

	public void initTopImage() {
		if (views != null || indicatorLayout != null) {
			views.clear();
			indicatorLayout.removeAllViews();
		}
		if (isNet) {
			if (listTopBannerImageList != null && listTopBannerImageList.size() != 0) {
				indicators = new ImageView[listTopBannerImageList.size()]; // 定义指示器数组大小
				for (int i = 0; i < listTopBannerImageList.size(); i++) {
					// 循环加入图片
					if (getActivity() == null) {
						return;
					} else {
						try {
							JSONArray jsonArray = new JSONArray(listTopBannerImageList.get(i).getAct_logo());
							ImageView imageView = new ImageView(getActivity());
							mImageLoader.displayImage(jsonArray.getString(0), imageView, options);
							views.add(imageView);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						// 循环加入指示器
						indicators[i] = new ImageView(getActivity());
						if (i == 0) {
							indicators[i].setBackgroundResource(R.drawable.point_n);
						} else {
							indicators[i].setBackgroundResource(R.drawable.point_s);
						}
						indicatorLayout.addView(indicators[i]);
					}
				}
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					File appDir = new File(Environment.getExternalStorageDirectory(), "outdoortopimage");
					if (appDir.exists()) {
						DataCleanManager.deleteFile(appDir);
					}
				} else {
					File appDir = new File(getActivity().getFilesDir(), "outdoortopimage");
					if (appDir.exists()) {
						DataCleanManager.deleteFile(appDir);
					}
				}
				// 存数据库
				new Thread(new Runnable() {
					@Override
					public void run() {
						HuoDongImagesBeans.clear();
						topImage = new HomeTapTopImage();
						for (int i = 0; i < listTopBannerImageList.size(); i++) {
							HuoDongImages = new HuoDongImages();
							try {
								JSONArray jsonArray = new JSONArray(listTopBannerImageList.get(i).getAct_logo());
								HuoDongImages.setImage(ImageControl.saveImageToGallery(getActivity(),
										ImageControl.getHttpBitmap(jsonArray.getString(0)), 2));
							} catch (Exception e) {
								HuoDongImages.setImage("");
							}
							HuoDongImagesBeans.add(HuoDongImages);
						}
						topImage.setImgUrl(gson.toJson(HuoDongImagesBeans));
						topImageDao.deleteAll();
						topImageDao.add(topImage);
					}
				}).start();
			}
		} else {
			topImageDatas.clear();
			topImageDatas = topImageDao.queryAll();
			if (topImageDatas != null && topImageDatas.size() != 0) {
				try {
					JSONArray jsonArray = new JSONArray(topImageDatas.get(0).getImgUrl());
					indicators = new ImageView[jsonArray.length()]; // 定义指示器数组大小
					for (int i = 0; i < jsonArray.length(); i++) {
						// 循环加入图片
						if (getActivity() == null) {
							return;
						} else {
							ImageView imageView = new ImageView(getActivity());
							try {
								mImageLoader.displayImage("file://" + jsonArray.getJSONObject(i).getString("image"),
										imageView, options);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							views.add(imageView);
							// 循环加入指示器
							indicators[i] = new ImageView(getActivity());
							if (i == 0) {
								indicators[i].setBackgroundResource(R.drawable.point_n);
							} else {
								indicators[i].setBackgroundResource(R.drawable.point_s);
							}
							indicatorLayout.addView(indicators[i]);
						}
					}
				} catch (JSONException e1) {
				}
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

	@Override
	protected void onResload() {

	}

	@Override
	protected void setMyViewClick() {
		// 七个按钮
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);
		tab5.setOnClickListener(this);
		tab6.setOnClickListener(this);
		tab7.setOnClickListener(this);
		tab8.setOnClickListener(this);
		tab9.setOnClickListener(this);
		tab10.setOnClickListener(this);
		huodong_remen.setOnClickListener(this);
		huodong_julebu.setOnClickListener(this);
		huodong_shoucang.setOnClickListener(this);
		viewPager.setOnSingleTouchListener(new OnSingleTouchListener() {

			@Override
			public void onSingleTouch() {
				if (isNet) {
					Bundle bundle = new Bundle();
					if (TextUtils.equals(listTopBannerImageList.get(downid).getAct_id(),
							App.getInstance().getHuoDongId())) {
						bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
					} else if (TextUtils.equals(listTopBannerImageList.get(downid).getAct_id(),
							App.getInstance().getLeaderHuoDongId())) {
						bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
					} else {
						bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
					}
					bundle.putString("act_id", listTopBannerImageList.get(downid).getAct_id());
					openActivity(HuoDongXiangQingActivity.class, bundle);
					isFrist = false;
				} else {
					showToastNet();
				}
			}
		});
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
		private WeakReference<HuoDongFragment> weakReference;
		private int currentItem = 0;

		protected ImageHandler(WeakReference<HuoDongFragment> wk) {
			weakReference = wk;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			HuoDongFragment activity = weakReference.get();
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

	@Override
	protected void headerOrFooterViewControl() {
		// 主界面控件的处理
		top_ctv_center_et.setText("活动名称/城市/俱乐部");
		top_ctv_center_et.setVisibility(View.VISIBLE);
		top_tv_title.setVisibility(View.GONE);
		top_tv_right.setVisibility(View.VISIBLE);
		top_tv_right.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		// 因为加了header，所以position从1开始

		switch (type_tap) {
		case 1:
			if (list != null && list.size() != 0) {
				try {
					if (list.get(position - 1).getAct_state() == 3 || list.get(position - 1).getAct_state() == 4) {
						if (TextUtils.equals(list.get(position - 1).getAct_id(), App.getInstance().getHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
						} else if (TextUtils.equals(list.get(position - 1).getAct_id(),
								App.getInstance().getLeaderHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
						} else {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
						}
					} else {
						bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
					}
					bundle.putString("act_id", list.get(position - 1).getAct_id());
					openActivity(HuoDongXiangQingActivity.class, bundle);
					isFrist = false;
				} catch (Exception e) {
				}
			}
			break;
		case 2:
			if (listClubActivityList != null && listClubActivityList.size() != 0) {
				try {
					if (listClubActivityList.get(position - 1).getAct_state() == 3
							|| listClubActivityList.get(position - 1).getAct_state() == 4) {
						if (TextUtils.equals(listClubActivityList.get(position - 1).getAct_id(),
								App.getInstance().getHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
						} else if (TextUtils.equals(listClubActivityList.get(position - 1).getAct_id(),
								App.getInstance().getLeaderHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
						} else {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
						}
					} else {
						bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
					}
					bundle.putString("act_id", listClubActivityList.get(position - 1).getAct_id());
					openActivity(HuoDongXiangQingActivity.class, bundle);
					isFrist = false;
				} catch (Exception e) {
				}
			}
			break;
		case 3:
			if (listCollectActivityList != null && listCollectActivityList.size() != 0) {
				try {
					if (listCollectActivityList.get(position - 1).getAct_state() == 3
							|| listCollectActivityList.get(position - 1).getAct_state() == 4) {
						if (TextUtils.equals(listCollectActivityList.get(position - 1).getAct_id(),
								App.getInstance().getHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
						} else if (TextUtils.equals(listCollectActivityList.get(position - 1).getAct_id(),
								App.getInstance().getLeaderHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
						} else {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
						}
					} else {
						bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
					}
					bundle.putString("act_id", listCollectActivityList.get(position - 1).getAct_id());
					openActivity(HuoDongXiangQingActivity.class, bundle);
					isFrist = false;
				} catch (Exception e) {
				}
			}
			break;
		}
	}

	private Thread thread = null;

	// 当季热门
	public void sendGetActivityListActionAsyn() {
		thread = new Thread() {
			public void run() {
				getActivityListAction();
			}
		};
		thread.start();
	}

	private List<GetHotActivityListResponsePayloadListItem> list = null;
	private List<GetHotActivityListResponsePayloadListItem> listTemp = null;

	public void getActivityListAction() {
		GetHotActivityListRequest request = new GetHotActivityListRequest();
		GetHotActivityListRequestParameter parameter = new GetHotActivityListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setCity(App.getInstance().getContactCity());
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
					GetHotActivityListResponse response = new GetHotActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetHotActivityListResponsePayload payload = (GetHotActivityListResponsePayload) response
							.getPayload();
					this.listTemp = payload.getActivitylist();
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

	// 俱乐部活动
	public void sendClubActivityListActionAsyn() {
		thread = new Thread() {
			public void run() {
				getClubActivityListAction();
			}
		};
		thread.start();
	}

	private List<GetClubActivityListResponsePayloadListItem> listClubActivityList = null;
	private List<GetClubActivityListResponsePayloadListItem> listClubActivityListTemp = null;

	public void getClubActivityListAction() {
		GetClubActivityListRequest request = new GetClubActivityListRequest();
		GetClubActivityListRequestParameter parameter = new GetClubActivityListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setCity(App.getInstance().getContactCity());
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
					GetClubActivityListResponse response = new GetClubActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetClubActivityListResponsePayload payload = (GetClubActivityListResponsePayload) response
							.getPayload();
					this.listClubActivityListTemp = payload.getActivitylist();
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

	// 收藏的活动
	public void sendCollectActivityListAsyn() {
		thread = new Thread() {
			public void run() {
				getCollectActivityListAction();
			}
		};
		thread.start();
	}

	private List<GetCollectActivityListResponsePayloadListItem> listCollectActivityList = null;
	private List<GetCollectActivityListResponsePayloadListItem> listCollectActivityListTemp = null;

	public void getCollectActivityListAction() {
		GetCollectActivityListRequest request = new GetCollectActivityListRequest();
		GetCollectActivityListRequestParameter parameter = new GetCollectActivityListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setCity(App.getInstance().getContactCity());
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
					GetCollectActivityListResponse response = new GetCollectActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetCollectActivityListResponsePayload payload = (GetCollectActivityListResponsePayload) response
							.getPayload();
					this.listCollectActivityListTemp = payload.getActivitylist();
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

	// 获取banner图片
	public void sendTopBannerImageListAsyn() {
		thread = new Thread() {
			public void run() {
				getTopBannerImageListAction();
			}
		};
		thread.start();
	}

	private List<GetTopBannerImageListListItem> listTopBannerImageList = null;

	public void getTopBannerImageListAction() {
		GetTopBannerImageListRequest request = new GetTopBannerImageListRequest();
		GetTopBannerImageListRequestParameter parameter = new GetTopBannerImageListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setIndex(0);
		parameter.setCount(5);
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
					GetTopBannerImageListResponse response = new GetTopBannerImageListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetTopBannerImageListResponsePayload payload = (GetTopBannerImageListResponsePayload) response
							.getPayload();
					this.listTopBannerImageList = payload.getList();
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

	CommonAdapter<NoDataBean> listadapter3 = null;
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:// 当季热门
				mDatas.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						list.clear();
					}
				} else {
					list.clear();
				}
				list.addAll(listTemp);
				mDatas.addAll(changeListType(list));
				if (mDatas.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						try {
							nodata.setTitle(getActivity().getResources().getString(R.string.home_page));
						} catch (Exception e) {
							nodata.setTitle("没有活动…");
						}
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
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
						if (listadapter != null) {
							listadapter.notifyDataSetChanged();
						} else {
							listadapter3 = null;
							listadapter = new MyListAdpter(getActivity(), mDatas, imageLoaderRectF, mImageLoader,
									options);
							lv_home.setAdapter(listadapter);
						}
					} else {
						listadapter = new MyListAdpter(getActivity(), mDatas, imageLoaderRectF, mImageLoader, options);
						lv_home.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:// 俱乐部
				mDatas.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						listClubActivityList.clear();
					}
				} else {
					listClubActivityList.clear();
				}
				listClubActivityList.addAll(listClubActivityListTemp);
				mDatas.addAll(changeListType(listClubActivityList));
				if (mDatas.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getActivity().getResources().getString(R.string.home_page_organization));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
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
						listadapter = new MyListAdpter(getActivity(), mDatas, imageLoaderRectF, mImageLoader, options);
						lv_home.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK3:// 收藏.
				mDatas.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						listCollectActivityList.clear();
					}
				} else {
					listCollectActivityList.clear();
				}
				listCollectActivityList.addAll(listCollectActivityListTemp);
				mDatas.addAll(changeListType(listCollectActivityList));
				if (mDatas.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getActivity().getResources().getString(R.string.home_page_collection));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
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
						listadapter = new MyListAdpter(getActivity(), mDatas, imageLoaderRectF, mImageLoader, options);
						lv_home.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK4:// 顶部banner
				initTopImage();
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				isFrist = false;
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @param 集合类型转换繁杂
	 *            ，放外边
	 * @return
	 * 
	 */
	private <T> List<HomeTapDangjiBean> changeListType(List<T> l) {
		mDatasTemp.clear();
		switch (type_tap) {
		case 1:
			try {
				for (int i = 0; i < l.size(); i++) {
					homeTapDangjiBean = new HomeTapDangjiBean();
					homeTapDangjiBean
							.setClub_head(((GetHotActivityListResponsePayloadListItem) l.get(i)).getClub_head());
					homeTapDangjiBean
							.setLeader_head(((GetHotActivityListResponsePayloadListItem) l.get(i)).getLeader_head());
					homeTapDangjiBean.setAct_start_time(
							((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_start_time());
					homeTapDangjiBean.setAct_id(((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_id());
					homeTapDangjiBean
							.setAct_level(((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_level());
					homeTapDangjiBean.setAct_logo(((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_logo());
					homeTapDangjiBean
							.setAct_state(((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_state());
					homeTapDangjiBean
							.setAct_title(((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_title());
					homeTapDangjiBean.setAct_type(((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_type());
					homeTapDangjiBean.setClub(((GetHotActivityListResponsePayloadListItem) l.get(i)).getClub());
					homeTapDangjiBean.setLeader(((GetHotActivityListResponsePayloadListItem) l.get(i)).getLeader());
					homeTapDangjiBean.setAct_desc_intro(
							((GetHotActivityListResponsePayloadListItem) l.get(i)).getAct_desc_intro());
					homeTapDangjiBean.setIssignup(((GetHotActivityListResponsePayloadListItem) l.get(i)).getIssignup());
					mDatasTemp.add(homeTapDangjiBean);
				}
			} catch (Exception e) {
			}
			break;
		case 2:
			try {
				for (int i = 0; i < l.size(); i++) {
					homeTapDangjiBean = new HomeTapDangjiBean();
					homeTapDangjiBean
							.setClub_head(((GetClubActivityListResponsePayloadListItem) l.get(i)).getClub_head());
					homeTapDangjiBean
							.setLeader_head(((GetClubActivityListResponsePayloadListItem) l.get(i)).getLeader_head());
					homeTapDangjiBean.setAct_start_time(
							((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_start_time());
					homeTapDangjiBean.setAct_id(((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_id());
					homeTapDangjiBean
							.setAct_level(((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_level());
					homeTapDangjiBean
							.setAct_logo(((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_logo());
					homeTapDangjiBean
							.setAct_state(((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_state());
					homeTapDangjiBean
							.setAct_title(((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_title());
					homeTapDangjiBean
							.setAct_type(((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_type());
					homeTapDangjiBean.setClub(((GetClubActivityListResponsePayloadListItem) l.get(i)).getClub());
					homeTapDangjiBean.setLeader(((GetClubActivityListResponsePayloadListItem) l.get(i)).getLeader());
					homeTapDangjiBean.setAct_desc_intro(
							((GetClubActivityListResponsePayloadListItem) l.get(i)).getAct_desc_intro());
					homeTapDangjiBean
							.setIssignup(((GetClubActivityListResponsePayloadListItem) l.get(i)).getIssignup());
					mDatasTemp.add(homeTapDangjiBean);
				}
			} catch (Exception e) {
			}
			break;
		case 3:
			try {
				for (int i = 0; i < l.size(); i++) {
					homeTapDangjiBean = new HomeTapDangjiBean();
					homeTapDangjiBean
							.setClub_head(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getClub_head());
					homeTapDangjiBean.setLeader_head(
							((GetCollectActivityListResponsePayloadListItem) l.get(i)).getLeader_head());
					homeTapDangjiBean.setAct_start_time(
							((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_start_time());
					homeTapDangjiBean.setAct_id(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_id());
					homeTapDangjiBean
							.setAct_level(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_level());
					homeTapDangjiBean
							.setAct_logo(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_logo());
					homeTapDangjiBean
							.setAct_state(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_state());
					homeTapDangjiBean
							.setAct_title(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_title());
					homeTapDangjiBean
							.setAct_type(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_type());
					homeTapDangjiBean.setClub(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getClub());
					homeTapDangjiBean.setLeader(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getLeader());
					homeTapDangjiBean.setAct_desc_intro(
							((GetCollectActivityListResponsePayloadListItem) l.get(i)).getAct_desc_intro());
					homeTapDangjiBean
							.setIssignup(((GetCollectActivityListResponsePayloadListItem) l.get(i)).getIssignup());
					mDatasTemp.add(homeTapDangjiBean);
				}
			} catch (Exception e) {
			}
			break;
		default:
			break;
		}
		return mDatasTemp;
	}
}
