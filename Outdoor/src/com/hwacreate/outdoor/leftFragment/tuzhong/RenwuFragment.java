package com.hwacreate.outdoor.leftFragment.tuzhong;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.GpsInfo;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.service.UpGpsInfoService;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DensityUtils;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.GetCityClubListAction.GetCityClubListClublistItem;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListClublistItem;
import com.keyhua.outdoor.protocol.GetOptionalActivityListAction.GetOptionalActivityListActivitylistItem;
import com.keyhua.outdoor.protocol.GetOptionalActivityListAction.GetOptionalActivityListRequest;
import com.keyhua.outdoor.protocol.GetOptionalActivityListAction.GetOptionalActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetOptionalActivityListAction.GetOptionalActivityListResponse;
import com.keyhua.outdoor.protocol.GetOptionalActivityListAction.GetOptionalActivityListResponsePayload;
import com.keyhua.outdoor.protocol.GetRecommendClubListAction.GetRecommendClubListClublistItem;
import com.keyhua.outdoor.protocol.UploadTraceDataAction.UploadTraceDataDatalistItem;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class RenwuFragment extends BaseFragment {
	// 上拉下拉刷新容器
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	// 上拉下拉刷新布局容器
	private PtrFrameLayout mPtrFrameLayout;
	private ListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	// 选取任务
	private HuoDongXiangQingItem huoDongXiangQingItemhuancun = null;
	private List<HuoDongXiangQingItem> huoDongXiangQingItemhuancunRead = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingItemDaoRead = null;
	// 已选择活动显示
	private LinearLayout rl = null;
	private CubeImageView icon = null;
	private TextView tv_title = null;
	private TextView tv_content = null;
	private TextView tv_des = null;
	private TextView tv_time = null;
	private TextView tv_action = null;
	private TextView tv_status = null;
	private RelativeLayout home_view = null;
	private CircleImageView iv_icon = null;
	// tap 提示
	private TextView tv = null;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;

	// 头部
	private View headerLayout = null;
	private LinearLayout headerParent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.getInstance().setBottonChoice(CommonUtility.TUZHONG);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.frag_renwu, null);
		return parentView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl:// 跳入缓存到本地的活动详情中
			Bundle bundle = new Bundle();
			bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
			bundle.putString("act_id", App.getInstance().getHuoDongId());
			openActivity(HuoDongXiangQingActivity.class, bundle);
			isFrist = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!isFrist && !TextUtils.isEmpty(App.getInstance().getAut())) {
			if (isNet) {
				index = 0;
				if (listadapter != null && listadapter.getCount() > count) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				sendAsyn();
			} else {
				initDao(1);
			}
		}
	}

	/**
	 * 数据的初始化
	 */
	public void initDao(int index) {
		// 数据操作
		huoDongXiangQingItemhuancunRead = huoDongXiangQingItemDaoRead.queryAll();
		if (huoDongXiangQingItemhuancunRead != null && huoDongXiangQingItemhuancunRead.size() != 0) {
			rl.setVisibility(View.VISIBLE);
			if (index == 1) {
				tv.setText("可选任务");
			}
			try {
				JSONArray jsonArray = new JSONArray(huoDongXiangQingItemhuancunRead.get(0).getAct_logo());
				mImageLoader.displayImage("file://" + jsonArray.getJSONObject(0).getString("image"), icon,
						optionsrenwu);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			tv_title.setText(huoDongXiangQingItemhuancunRead.get(0).getAct_title());
			tv_time.setText(TimeDateUtils
					.formatDateFromDatabaseTime(huoDongXiangQingItemhuancunRead.get(0).getAct_start_time()));
			if (!TextUtils.isEmpty(huoDongXiangQingItemhuancunRead.get(0).getClub_name())) {
				tv_des.setText(huoDongXiangQingItemhuancunRead.get(0).getClub_name());
				mImageLoader.displayImage("file://" + huoDongXiangQingItemhuancunRead.get(0).getClub_logo(), iv_icon,
						options);
			} else if (!TextUtils.isEmpty(huoDongXiangQingItemhuancunRead.get(0).getLeader_name())) {
				tv_des.setText(huoDongXiangQingItemhuancunRead.get(0).getLeader_name());
				mImageLoader.displayImage("file://" + huoDongXiangQingItemhuancunRead.get(0).getLeader_head(), iv_icon,
						options);
			}
			tv_action.setText(huoDongXiangQingItemhuancunRead.get(0).getAct_type());
			tv_content.setText(huoDongXiangQingItemhuancunRead.get(0).getAct_desc_intro());
			if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "登山")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.dengshan));
			} else if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "徒步")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.tubu));
			} else if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "骑行")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.qixing));
			} else if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "自驾")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.zijia));
			} else if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "摄影")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.sheying));
			} else if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "休闲")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.xiuxian));
			} else if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "露营")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.luying));
			} else if (TextUtils.equals(huoDongXiangQingItemhuancunRead.get(0).getAct_type(), "亲子")) {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.qinzi));
			} else {
				tv_action.setTextColor(getActivity().getResources().getColor(R.color.content));
			}
			switch (huoDongXiangQingItemhuancunRead.get(0).getAct_state()) {
			case CommonUtility.BAOMINGZHONGInt:
				tv_status.setText(CommonUtility.BAOMINGZHONGStr);
				tv_status.setTextColor(getActivity().getResources().getColor(R.color.content));
				break;
			case CommonUtility.ZHENGDUIInt:
				tv_status.setText(CommonUtility.ZHENGDUIStr);
				tv_status.setTextColor(getActivity().getResources().getColor(R.color.content));
				break;
			case CommonUtility.ZHUNBEIInt:
				tv_status.setText(CommonUtility.ZHUNBEIStr);
				tv_status.setTextColor(getActivity().getResources().getColor(R.color.content));
				break;
			case CommonUtility.CHUXINGInt:
				tv_status.setText(CommonUtility.CHUXINGStr);
				tv_status.setTextColor(getActivity().getResources().getColor(R.color.content));
				break;
			case CommonUtility.DIANPINGInt:
				tv_status.setText(CommonUtility.DIANPINGStr);
				tv_status.setTextColor(getActivity().getResources().getColor(R.color.content));
				break;
			case CommonUtility.JIESHUInt:
				tv_status.setText(CommonUtility.JIESHUStr);
				tv_status.setTextColor(getActivity().getResources().getColor(R.color.content));
				break;
			}

			if (!isWorked()) {// 开启服务
				// 下载完成之后开始上传gps队员信息
				getActivity().startService(new Intent(getActivity(), UpGpsInfoService.class));
			}
		} else {
			rl.setVisibility(View.GONE);
			if (index == 1) {
				tv.setText("可选任务，请先选择活动报名");
			}
			if (isWorked()) {// 开启服务
				// 关闭服务
				Intent intent2 = new Intent(UpGpsInfoService.actionToStop);
				getActivity().sendBroadcast(intent2);
			}
		}

	}

	@Override
	protected void onInitData() {
		// 数据库操作
		huoDongXiangQingItemhuancun = new HuoDongXiangQingItem();
		huoDongXiangQingItemDaoRead = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQingItemhuancun, getActivity());
		list = new ArrayList<GetOptionalActivityListActivitylistItem>();
		listTemp = new ArrayList<GetOptionalActivityListActivitylistItem>();
		lv_home = (ListView) getActivity().findViewById(R.id.lv_home);
		initControl();
	}

	private void initControl() {
		// 头部
		headerLayout = LayoutInflater.from(getActivity()).inflate(R.layout.head_renwu, null, true);
		rl = (LinearLayout) headerLayout.findViewById(R.id.rl);
		tv = (TextView) headerLayout.findViewById(R.id.tv);
		home_view = (RelativeLayout) headerLayout.findViewById(R.id.home_view);
		iv_icon = (CircleImageView) headerLayout.findViewById(R.id.iv_icon);
		icon = (CubeImageView) headerLayout.findViewById(R.id.icon);
		tv_title = (TextView) headerLayout.findViewById(R.id.tv_title);
		tv_content = (TextView) headerLayout.findViewById(R.id.tv_content);
		tv_des = (TextView) headerLayout.findViewById(R.id.tv_des);
		tv_time = (TextView) headerLayout.findViewById(R.id.tv_time);
		tv_action = (TextView) headerLayout.findViewById(R.id.tv_action);
		tv_status = (TextView) headerLayout.findViewById(R.id.tv_status);
		home_view.setLayoutParams(new LinearLayout.LayoutParams(App.getInstance().getScreenWidth(),
				App.getInstance().getScreenHeight() / 3));
		tv.setLayoutParams(new LinearLayout.LayoutParams(App.getInstance().getScreenWidth(),
				DensityUtils.dp2px(getActivity(), 50)));
		// 获得父类的视图布局
		headerParent = new LinearLayout(getActivity());
		headerParent.addView(headerLayout);
		lv_home.addHeaderView(headerParent);
	}

	@Override
	protected void onResload() {
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		rl.setOnClickListener(this);
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
						index = 10;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					sendAsyn();
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
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					sendAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					isNet = false;
					initDao(1);
					CommonAdapter<NoDataBean> listadapter3 = null;
					List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
					NoDataBean nodata = new NoDataBean();
					try {
						nodata.setTitle(getActivity().getResources().getString(R.string.kexuan_huodong));
					} catch (Exception e) {
						nodata.setTitle("没有可选活动…");
					}
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

	// 自己发的
	final int TYPE_VIEW = 0;
	// 服务器发的
	final int TYPE_OTHER = 1;

	/**
	 * @author 曾金叶
	 * @2015-8-6 @上午9:58:49
	 * @category adapter ,写在本activity，不用分出来
	 */
	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private List<GetOptionalActivityListActivitylistItem> mList = null;

		public MyListAdpter(Context context, List<GetOptionalActivityListActivitylistItem> list) {
			this.context = context;
			this.mList = list;
		}

		@Override
		public int getCount() {
			return mList != null ? mList.size() : 0;
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
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
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.home_view.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
			holder.tv_content.setText(mList.get(position).getAct_desc_intro());
			try {
				JSONArray jsonArray = new JSONArray(mList.get(position).getAct_logo());
				holder.icon.loadImage(imageLoaderRectF, jsonArray.getString(0));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			holder.tv_title.setText(mList.get(position).getAct_title());
			holder.tv_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getAct_start_time()));
			if (!TextUtils.isEmpty(mList.get(position).getClub())) {
				holder.tv_des.setText(mList.get(position).getClub());
				mImageLoader.displayImage(mList.get(position).getClub_head(), holder.iv_icon, options);
			} else if (!TextUtils.isEmpty(mList.get(position).getLeader())) {
				holder.tv_des.setText(mList.get(position).getLeader());
				mImageLoader.displayImage(mList.get(position).getLeader_head(), holder.iv_icon, options);
			}
			holder.tv_action.setText(mList.get(position).getAct_type());
			if (TextUtils.equals(mList.get(position).getAct_type(), "登山")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.dengshan));
			} else if (TextUtils.equals(mList.get(position).getAct_type(), "徒步")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.tubu));
			} else if (TextUtils.equals(mList.get(position).getAct_type(), "骑行")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.qixing));
			} else if (TextUtils.equals(mList.get(position).getAct_type(), "自驾")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.zijia));
			} else if (TextUtils.equals(mList.get(position).getAct_type(), "摄影")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.sheying));
			} else if (TextUtils.equals(mList.get(position).getAct_type(), "休闲")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.xiuxian));
			} else if (TextUtils.equals(mList.get(position).getAct_type(), "露营")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.luying));
			} else if (TextUtils.equals(mList.get(position).getAct_type(), "亲子")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.qinzi));
			} else {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.content));
			}
			switch (mList.get(position).getAct_state()) {
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
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
					bundle.putString("act_id", mList.get(position).getAct_id());
					openActivity(HuoDongXiangQingActivity.class, bundle);
					isFrist = false;
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private RelativeLayout home_view = null;
			private CircleImageView iv_icon;
			private CubeImageView icon;
			private TextView tv_title, tv_content, tv_des, tv_time, tv_action, tv_status;
		}

	}

	private Thread thread = null;

	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() {
		GetOptionalActivityListRequest request = new GetOptionalActivityListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetOptionalActivityListRequestParameter parameter = new GetOptionalActivityListRequestParameter();
		parameter.setAct_type("");
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
					GetOptionalActivityListResponse response = new GetOptionalActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetOptionalActivityListResponsePayload payload = (GetOptionalActivityListResponsePayload) response
							.getPayload();
					this.listTemp = payload.getActivitylistList();
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

	private List<GetOptionalActivityListActivitylistItem> list = null;
	private List<GetOptionalActivityListActivitylistItem> listTemp = null;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (Loadmore) {
					if (LoadmoreData) {
						list.clear();
					}
				} else {
					list.clear();
				}
				list.addAll(listTemp);
				if (list.size() == 0) {
					tv.setText("当前没有可选任务，请先选择活动报名");
					if (huoDongXiangQingItemDaoRead.queryAll() != null) {
						huoDongXiangQingItemDaoRead.deleteAll();
					}
					App.getInstance().setHuoDongId("");
					initDao(0);
				} else {
					tv.setText("可选任务");
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter = new MyListAdpter(getActivity(), list);
						lv_home.setAdapter(listadapter);
					}
					if (!TextUtils.isEmpty(App.getInstance().getHuoDongId())) {
						huoDongXiangQingItemhuancunRead = huoDongXiangQingItemDaoRead.queryAll();
						if (huoDongXiangQingItemhuancunRead != null && huoDongXiangQingItemhuancunRead.size() != 0) {
							handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);
						}
					} else {
						if (huoDongXiangQingItemDaoRead.queryAll() != null) {
							huoDongXiangQingItemDaoRead.deleteAll();
						}
						App.getInstance().setHuoDongId("");
						initDao(0);
					}
				}
				break;
			case CommonUtility.SERVEROK2:
				boolean isUpdata = false;
				boolean isEquals = false;
				for (int i = 0; i < list.size(); i++) {
					if (TextUtils.equals(list.get(i).getAct_id(), huoDongXiangQingItemhuancunRead.get(0).getAct_id())) {
						huoDongXiangQingItemDaoRead.updateState(list.get(i).getAct_state(), list.get(i).getAct_id());
						isUpdata = true;
					}
					// 只有准备出行与出行状态才会出现在列表中
					if (TextUtils.equals(App.getInstance().getHuoDongId(), list.get(i).getAct_id())) {
						isEquals = true;
					}
				}
				if (!isEquals) {
					// 删除本地gps
					deleteGpsDao();
					// 删除本地活动
					if (huoDongXiangQingItemDaoRead.queryAll() != null) {
						huoDongXiangQingItemDaoRead.deleteAll();
					}
					App.getInstance().setHuoDongId("");
					initDao(0);
				} else if (isUpdata) {
					initDao(0);
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				isFrist = false;
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				break;
			default:
				break;
			}
		};
	};
	// 轨迹数据库操作
	private BaseDao<GpsInfo> mGpSinfoDao;
	private GpsInfo info = null;

	/**
	 * GPS数据的初始化
	 */
	public void deleteGpsDao() {
		// 轨迹信息数据库
		info = new GpsInfo();
		mGpSinfoDao = new BaseDao(info, getActivity());
		mGpSinfoDao.deleteAll();
	}

	@Override
	protected void headerOrFooterViewControl() {
		initMainFooter("任务", "报到", "地图");
		rg_button.setBackgroundColor(getResources().getColor(R.color.white));
		radiobutton_select_one.setBackgroundResource(R.drawable.select_item_down);
		radiobutton_select_one.setChecked(true);
		radiobutton_select_two.setBackgroundResource(R.drawable.select_item_downnocheck);
		radiobutton_select_three.setBackgroundResource(R.drawable.select_item_downnocheck);
		radiobutton_select_one.setTextColor(getResources().getColor(R.color.title));
		radiobutton_select_two.setTextColor(getResources().getColor(R.color.title));
		radiobutton_select_three.setTextColor(getResources().getColor(R.color.title));
	}

	/** 判断服务是否启动 */
	public boolean isWorked() {
		if (getActivity() != null) {
			ActivityManager myManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
			ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
					.getRunningServices(30);
			for (int i = 0; i < runningService.size(); i++) {
				if (runningService.get(i).service.getClassName().toString()
						.equals("com.hwacreate.outdoor.service.UpGpsInfoService")) {
					return true;
				}
			}
		}
		return false;
	}
}
