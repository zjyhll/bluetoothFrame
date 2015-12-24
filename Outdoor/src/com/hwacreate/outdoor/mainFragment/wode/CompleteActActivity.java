package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListActivitylistItem;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListRequest;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListResponse;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListResponsePayload;
import com.keyhua.outdoor.protocol.GetUserCompleteActivityListAction.GetUserCompleteActivityListActivitylistItem;
import com.keyhua.outdoor.protocol.GetUserCompleteActivityListAction.GetUserCompleteActivityListRequest;
import com.keyhua.outdoor.protocol.GetUserCompleteActivityListAction.GetUserCompleteActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetUserCompleteActivityListAction.GetUserCompleteActivityListResponse;
import com.keyhua.outdoor.protocol.GetUserCompleteActivityListAction.GetUserCompleteActivityListResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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

/**
 * @author LaLa 我完成的活动
 * 
 */

public class CompleteActActivity extends BaseActivity implements OnItemClickListener {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;// listView
	private MyCompleteListAdpter mlistAdpter1 = null;
	private MyCreateListAdpter mlistAdpter2 = null;
	private PtrFrameLayout mPtrFrameLayout;

	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean isFrist = true;
	private boolean isNet = true;
	private List<GetUserCompleteActivityListActivitylistItem> listComplete = null;
	private List<GetUserCompleteActivityListActivitylistItem> listCompleteTemp = null;
	private List<GetLeaderActivityListActivitylistItem> listCreate = null;
	private List<GetLeaderActivityListActivitylistItem> listCreateState = null;
	private List<GetLeaderActivityListActivitylistItem> listCreateTemp = null;
	// 头部
	private View headerLayout = null;
	private LinearLayout headerParent = null;
	private TextView tv_wode_joinActivity = null;
	private TextView tv_wode_createActivity = null;
	private View wode_join_view = null;
	private View wode_create_view = null;
	// 标志类型
	private int type_tap = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_list);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.tv_wode_joinActivity:
			showdialog();
			type_tap = 1;
			index = 0;
			count = 10;
			Loadmore = false;
			tv_wode_joinActivity.setTextColor(getResources().getColor(R.color.app_green));
			tv_wode_createActivity.setTextColor(getResources().getColor(R.color.title));
			sendGetUserCompleteActivityListActionAsyn();
			wode_join_view.setVisibility(View.VISIBLE);
			wode_create_view.setVisibility(View.GONE);
			break;
		case R.id.tv_wode_createActivity:
			showdialog();
			type_tap = 2;
			index = 0;
			count = 50;
			Loadmore = false;
			sendGetLeaderActivityListActionAsyn();
			tv_wode_joinActivity.setTextColor(getResources().getColor(R.color.title));
			tv_wode_createActivity.setTextColor(getResources().getColor(R.color.app_green));
			wode_join_view.setVisibility(View.GONE);
			wode_create_view.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {
			index = 0;
			Loadmore = false;
			switch (type_tap) {
			case 1:
				if (mlistAdpter1 != null && mlistAdpter1.getCount() > count) {
					count = mlistAdpter1.getCount();
				} else {
					count = 10;
				}
				sendGetUserCompleteActivityListActionAsyn();
				break;
			case 2:
				if (mlistAdpter2 != null && mlistAdpter2.getCount() > count) {
					count = mlistAdpter2.getCount();
				} else {
					count = 50;
				}
				sendGetLeaderActivityListActionAsyn();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		// 头部
		headerLayout = getLayoutInflater().inflate(R.layout.head_completeac, null);
		tv_wode_joinActivity = (TextView) headerLayout.findViewById(R.id.tv_wode_joinActivity);
		tv_wode_createActivity = (TextView) headerLayout.findViewById(R.id.tv_wode_createActivity);
		wode_join_view = headerLayout.findViewById(R.id.wode_join_view);
		wode_create_view = headerLayout.findViewById(R.id.wode_create_view);
		// // 获得父类的视图布局
		headerParent = new LinearLayout(CompleteActActivity.this);
		headerParent.addView(headerLayout);
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
		lv_home.addHeaderView(headerParent);
		lv_home.setOnItemClickListener(this);
	}

	@Override
	protected void onInitData() {
		listComplete = new ArrayList<GetUserCompleteActivityListActivitylistItem>();
		listCompleteTemp = new ArrayList<GetUserCompleteActivityListActivitylistItem>();
		listCreate = new ArrayList<GetLeaderActivityListActivitylistItem>();
		listCreateState = new ArrayList<GetLeaderActivityListActivitylistItem>();
		listCreateTemp = new ArrayList<GetLeaderActivityListActivitylistItem>();
		initHeaderOther();
		initControl();
		refreshAndLoadMore();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText(getString(R.string.wode_wanchengdehuodong));
		tv_wode_joinActivity.setTextColor(getResources().getColor(R.color.app_green));
		tv_wode_createActivity.setTextColor(getResources().getColor(R.color.title));
		wode_join_view.setVisibility(View.VISIBLE);
		wode_create_view.setVisibility(View.GONE);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		tv_wode_joinActivity.setOnClickListener(this);
		tv_wode_createActivity.setOnClickListener(this);
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
				if (NetUtil.isNetworkAvailable(CompleteActActivity.this)) {// 有网
					isNet = true;
					Loadmore = true;
					switch (type_tap) {
					case 1:
						count = 10;
						if (mlistAdpter1 != null) {
							index = mlistAdpter1.getCount();
						} else {
							index = 0;
						}
						sendGetUserCompleteActivityListActionAsyn();
						break;
					case 2:
						count = 50;
						if (mlistAdpter2 != null) {
							index = mlistAdpter2.getCount();
						} else {
							index = 0;
						}
						sendGetLeaderActivityListActionAsyn();
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
				if (NetUtil.isNetworkAvailable(CompleteActActivity.this)) {// 有网
					isNet = true;
					index = 0;
					Loadmore = false;
					switch (type_tap) {
					case 1:
						count = 10;
						sendGetUserCompleteActivityListActionAsyn();
						break;
					case 2:
						count = 50;
						sendGetLeaderActivityListActionAsyn();
						break;
					default:
						break;
					}
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
		switch (type_tap) {
		case 1:
			if (listComplete != null) {
				bundle.putString("act_id", listComplete.get(position - 1).getAct_id());
			}
			break;
		case 2:
			if (listCreate != null) {
				bundle.putString("act_id", listCreate.get(position - 1).getAct_id());
			}
			break;
		default:
			break;
		}
		openActivity(XinJianYouJizjyActivity.class, bundle);
		isFrist = false;
	}

	public class MyCompleteListAdpter extends BaseAdapter {
		private Context context = null;
		private List<GetUserCompleteActivityListActivitylistItem> mList = null;

		public MyCompleteListAdpter(Context context, List<GetUserCompleteActivityListActivitylistItem> list) {
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
			try {
				JSONArray jsonArray = new JSONArray(mList.get(position).getAct_logo());
				holder.icon.loadImage(imageLoaderRectF, jsonArray.getString(0));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			holder.tv_title.setText(mList.get(position).getAct_title());
			holder.tv_content.setText(mList.get(position).getAct_desc_intro());
			if (!TextUtils.isEmpty(mList.get(position).getClub())) {
				holder.tv_des.setText(mList.get(position).getClub());
				mImageLoader.displayImage(mList.get(position).getClub_head(), holder.iv_icon, options);
			} else if (!TextUtils.isEmpty(mList.get(position).getLeader())) {
				holder.tv_des.setText(mList.get(position).getLeader());
				mImageLoader.displayImage(mList.get(position).getLeader_head(), holder.iv_icon, options);
			}
			holder.tv_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getAct_start_time()));
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
			return convertView;
		}

		private class ViewHolder {
			private RelativeLayout home_view = null;
			private CircleImageView iv_icon;
			private CubeImageView icon;
			private TextView tv_title;
			private TextView tv_content;
			private TextView tv_des;
			private TextView tv_time;
			private TextView tv_action;
			private TextView tv_status;

		}
	}

	public class MyCreateListAdpter extends BaseAdapter {
		private Context context = null;
		private List<GetLeaderActivityListActivitylistItem> mList = null;

		public MyCreateListAdpter(Context context, List<GetLeaderActivityListActivitylistItem> listCreate) {
			this.context = context;
			this.mList = listCreate;
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
			try {
				JSONArray jsonArray = new JSONArray(mList.get(position).getAct_logo());
				holder.icon.loadImage(imageLoaderRectF, jsonArray.getString(0));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			holder.tv_title.setText(mList.get(position).getAct_title());
			holder.tv_content.setText(mList.get(position).getAct_desc_intro());
			if (!TextUtils.isEmpty(mList.get(position).getClub())) {
				holder.tv_des.setText(mList.get(position).getClub());
				mImageLoader.displayImage(mList.get(position).getClub_head(), holder.iv_icon, options);
			} else if (!TextUtils.isEmpty(mList.get(position).getLeader())) {
				holder.tv_des.setText(mList.get(position).getLeader());
				mImageLoader.displayImage(mList.get(position).getLeader_head(), holder.iv_icon, options);
			}
			holder.tv_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getAct_start_time()));
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
			return convertView;
		}

		private class ViewHolder {
			private RelativeLayout home_view = null;
			private CircleImageView iv_icon;
			private CubeImageView icon;
			private TextView tv_title;
			private TextView tv_content;
			private TextView tv_des;
			private TextView tv_time;
			private TextView tv_action;
			private TextView tv_status;

		}
	}

	private Thread thread = null;

	// 我完成的活动
	public void sendGetUserCompleteActivityListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetUserCompleteActivityListAction();
			}
		};
		thread.start();
	}

	// 我创建的活动
	public void sendGetLeaderActivityListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetLeaderActivityListAction();
			}
		};
		thread.start();
	}

	public void GetLeaderActivityListAction() {
		GetLeaderActivityListRequest request = new GetLeaderActivityListRequest();
		GetLeaderActivityListRequestParameter parameter = new GetLeaderActivityListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					GetLeaderActivityListResponse response = new GetLeaderActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetLeaderActivityListResponsePayload payload = (GetLeaderActivityListResponsePayload) response
							.getPayload();
					this.listCreateTemp = payload.getActivitylistList();
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

	public void GetUserCompleteActivityListAction() {
		GetUserCompleteActivityListRequest request = new GetUserCompleteActivityListRequest();
		GetUserCompleteActivityListRequestParameter parameter = new GetUserCompleteActivityListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
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
					GetUserCompleteActivityListResponse response = new GetUserCompleteActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetUserCompleteActivityListResponsePayload payload = (GetUserCompleteActivityListResponsePayload) response
							.getPayload();
					this.listCompleteTemp = payload.getActivitylistList();
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

	CommonAdapter<NoDataBean> listadapter3 = null;
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:// 我的完成的活动
				if (!Loadmore) {
					listComplete.clear();
				}
				listComplete.addAll(listCompleteTemp);
				if (listComplete.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.home_page));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(CompleteActActivity.this, nodatas,
								R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					mlistAdpter1 = null;
				} else {
					if (Loadmore) {
						mlistAdpter1.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						mlistAdpter1 = new MyCompleteListAdpter(CompleteActActivity.this, listComplete);
						lv_home.setAdapter(mlistAdpter1);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:// 我的创建的活动
				if (!Loadmore) {
					listCreate.clear();
				}
				listCreate.addAll(listCreateTemp);
				listCreateState.clear();
				for (int i = 0; i < listCreate.size(); i++) {
					if (listCreate.get(i).getAct_state() != 6) {
						listCreateState.add(listCreate.get(i));
					}
				}
				for (int j = 0; j < listCreateState.size(); j++) {
					listCreate.remove(listCreateState.get(j));
				}
				if (listCreate.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.home_page));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(CompleteActActivity.this, nodatas,
								R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					mlistAdpter2 = null;
				} else {
					if (Loadmore) {
						mlistAdpter2.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						mlistAdpter2 = new MyCreateListAdpter(CompleteActActivity.this, listCreate);
						lv_home.setAdapter(mlistAdpter2);
					}
				}
				if (isshowdialog()) {
					closedialog();
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
