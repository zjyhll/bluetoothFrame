package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.youji.YoujiXiangQingActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DensityUtils;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CustomDialog;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelRequest;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelRequestParameter;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelResponse;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelResponsePayload;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequest;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequestParameter;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponse;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponsePayload;
import com.keyhua.outdoor.protocol.CancelTravelVerifyAction.CancelTravelVerifyRequest;
import com.keyhua.outdoor.protocol.CancelTravelVerifyAction.CancelTravelVerifyRequestParameter;
import com.keyhua.outdoor.protocol.CancelTravelVerifyAction.CancelTravelVerifyResponse;
import com.keyhua.outdoor.protocol.CancelTravelVerifyAction.CancelTravelVerifyResponsePayload;
import com.keyhua.outdoor.protocol.DeleteTravelAction.DeleteTravelRequest;
import com.keyhua.outdoor.protocol.DeleteTravelAction.DeleteTravelRequestParameter;
import com.keyhua.outdoor.protocol.DeleteTravelAction.DeleteTravelResponse;
import com.keyhua.outdoor.protocol.DeleteTravelAction.DeleteTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetMyTravelAction.GetMyTravelRequest;
import com.keyhua.outdoor.protocol.GetMyTravelAction.GetMyTravelRequestParameter;
import com.keyhua.outdoor.protocol.GetMyTravelAction.GetMyTravelResponse;
import com.keyhua.outdoor.protocol.GetMyTravelAction.GetMyTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetMyTravelAction.GetMyTravelTravellistItem;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

/**
 * @author LaLa 我写的游记
 * 
 */

public class XieYouJiActivity extends BaseActivity implements OnItemClickListener {
	View parentView = null;
	// pop
	private PopupWindow popMenu = null;
	private RelativeLayout parent = null;// 半透明背景色
	private RelativeLayout pop_youji1 = null;
	private RelativeLayout pop_youji2 = null;
	private RelativeLayout pop_youji3 = null;
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private SwipeMenuListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;

	// 取得本地写的游记数据库信息
	// 是否编辑中
	Integer verify = -3;
	Integer iSediting = 0;
	Integer deleteState = 0;
	Integer returnState = 0;
	Integer TabId = -1;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_detailed_list, null);
		setContentView(parentView);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.top_tv_right:
			popMenu.showAtLocation(parentView, Gravity.CENTER, 0, 0);
			break;
		case R.id.pop_youji1:
			openActivity(CompleteActActivity.class);
			popMenu.dismiss();
			isFrist = false;
			break;
		case R.id.pop_youji2:
			openActivity(XinJianYouJizjyActivity.class);
			popMenu.dismiss();
			isFrist = false;
			break;
		case R.id.pop_youji3:
			popMenu.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Bundle bundle = new Bundle();
		bundle.putInt("isDownLoad", 0);
		bundle.putInt("agreeCount", list.get(arg2).getAgreeCount() != null ? list.get(arg2).getAgreeCount() : 0);
		bundle.putInt("isAgree", 0);
		bundle.putString("tvl_id", list.get(arg2).getTvl_id() != null ? list.get(arg2).getTvl_id() : "");
		bundle.putString("act_id", list.get(arg2).getAct_id() != null ? list.get(arg2).getAct_id() : "");
		bundle.putString("isMywhrite", "isMywhrite");
		openActivity(YoujiXiangQingActivity.class, bundle);
		isFrist = false;
	}

	/**
	 * 数据的初始化
	 */
	public void initDao() {
		// 初始化取得本地写的游记的数据库
		list = new ArrayList<GetMyTravelTravellistItem>();
		listTemp = new ArrayList<GetMyTravelTravellistItem>();
	}

	private void getgetMyTravelTraveData() {
		// xieYoujiXiagetMyTravelTravellistItem标志位，0为保存（设置状态-4），1为提交（设置状态为0）
		act_id = getMyTravelTravellistItem.getAct_id();
		tvl_create_time = getMyTravelTravellistItem.getTvl_create_time();
		tvl_update_time = getMyTravelTravellistItem.getTvl_update_time();
		tvl_id = getMyTravelTravellistItem.getTvl_id() != null ? getMyTravelTravellistItem.getTvl_id() : "";
		tvl_cover = getMyTravelTravellistItem.getTvl_cover();// 游记封面logo
		tvl_title = getMyTravelTravellistItem.getTvl_title();// 游记标题
		act_start_time = getMyTravelTravellistItem.getAct_start_time();// 开始时间
		club_id = getMyTravelTravellistItem.getClub_id();// 关联的俱乐部id
		tvl_desc = getMyTravelTravellistItem.getTvl_desc();// 游记简介
		act_desc_intro = getMyTravelTravellistItem.getAct_desc_intro();// 游记简介
		act_end_time = getMyTravelTravellistItem.getAct_end_time();// 结束时间
		act_venue_time = getMyTravelTravellistItem.getAct_venue_time();// 集合时间
		act_venue = getMyTravelTravellistItem.getAct_venue();// 集合地点
		act_level = getMyTravelTravellistItem.getAct_level();// 活动等级
		leader_name = getMyTravelTravellistItem.getLeader_name();// 领队名称
		leader_id = getMyTravelTravellistItem.getLeader_id();// 领队id
		city = getMyTravelTravellistItem.getCity();// 地域（城市）
		distance = getMyTravelTravellistItem.getDistance();// 实际距离
		trace_data = getMyTravelTravellistItem.getTrace_data();// 轨迹数据
		anchor_longitude = getMyTravelTravellistItem.getAnchor_longitude();// 锚点经度
		anchor_latitude = getMyTravelTravellistItem.getAnchor_latitude();// 锚点纬度
		tvl_type = getMyTravelTravellistItem.getTvl_type();// 游记类型(同活动类型)
		footprint_data = getMyTravelTravellistItem.getFootprint_data();// 游记足印（脚印数据app）
		act_entry_fee = getMyTravelTravellistItem.getAct_entry_fee();// 实际费用
		act_most_equip = getMyTravelTravellistItem.getAct_most_equip();// 装备要求
		logistics = getMyTravelTravellistItem.getLogistics();// 报名须知
		team_number = getMyTravelTravellistItem.getTeam_number();// 队伍人数
		is_share = getMyTravelTravellistItem.getIs_share();// 是否分享(是否私有)
		team_member = getMyTravelTravellistItem.getTeam_member();// 活动队员（头像，id
		act_weixin = getMyTravelTravellistItem.getAct_weixin();// 活动微信号
		act_join_num_limit = getMyTravelTravellistItem.getAct_join_num_limit();// 活动人数
		rel_speed = getMyTravelTravellistItem.getRel_speed();// 平均速度，手填，均速
		confirmed_member = getMyTravelTravellistItem.getConfirmed_member();// 已确认队员人数
		viceleadercount = getMyTravelTravellistItem.getViceleadercount();// 副队人数
		malecount = getMyTravelTravellistItem.getMalecount();// 男队人数
		femalecount = getMyTravelTravellistItem.getFemalecount();// 女队人数
		tvl_else = getMyTravelTravellistItem.getTvl_else();// 其他
		act_line = getMyTravelTravellistItem.getAct_line();
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
					if (!TextUtils.isEmpty(tvl_id)) {
						sign = 1;
						sendAddTravelActionAsyn();
					} else {
						sign = 0;
						sendAddTravelActionAsyn();
					}
					break;
				case 2:
					if (!TextUtils.isEmpty(tvl_id)) {
						sendCancelTravelVerifyActionAsyn();
					}
					break;
				case 3:
					if (!TextUtils.isEmpty(tvl_id)) {
						sendDeleteTravelActionAsyn();
					}
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

	private View view = null;

	private void initPop() {
		view = getLayoutInflater().inflate(R.layout.pop_wode_xieyouji_xinjian_list, null);
		popMenu = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popMenu.setBackgroundDrawable(new BitmapDrawable());
		popMenu.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		parent = (RelativeLayout) view.findViewById(R.id.parent);
		pop_youji1 = (RelativeLayout) view.findViewById(R.id.pop_youji1);
		pop_youji2 = (RelativeLayout) view.findViewById(R.id.pop_youji2);
		pop_youji3 = (RelativeLayout) view.findViewById(R.id.pop_youji3);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {
			showdialog();
			index = 0;
			if (listadapter != null && listadapter.getCount() >= count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendGetMyTravelActionAsyn();
		}
	}

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		list = new ArrayList<GetMyTravelTravellistItem>();
		listTemp = new ArrayList<GetMyTravelTravellistItem>();
		lv_home = (SwipeMenuListView) findViewById(R.id.lv_home_detailed_list);
		initPop();
	}

	@Override
	protected void onInitData() {
		initDao();
		initHeaderOther();
		initControl();
		showdialog();
		refreshAndLoadMore();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onResload() {
		top_tv_title.setText(getString(R.string.wode_xieyouji));
		top_tv_right.setText("新建");
		top_tv_right.setVisibility(View.VISIBLE);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		parent.setOnClickListener(this);
		pop_youji1.setOnClickListener(this);
		pop_youji2.setOnClickListener(this);
		pop_youji3.setOnClickListener(this);
		lv_home.setOnItemClickListener(this);
		lv_home.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				Bundle bundle = null;
				if (NetUtil.isNetworkAvailable(XieYouJiActivity.this)) {
					getMyTravelTravellistItem = list.get(position);
					getgetMyTravelTraveData();
				}
				verify = menu.getViewType();
				if (NetUtil.isNetworkAvailable(XieYouJiActivity.this)) {
					tvl_id = list.get(position).getTvl_id();
				}
				switch (index) {
				case 0:// 提交和撤销和编辑
					switch (menu.getViewType()) {
					case -2:
						// 撤销
						showAlertDialog(XieYouJiActivity.this, "撤销本游记的发布申请                              ", 2);
						break;
					case -1:
						// 提交
						showAlertDialog(XieYouJiActivity.this, "确定要将本游记提交审核后发布              ", 1);
						break;
					case 0:
						// 撤销
						showAlertDialog(XieYouJiActivity.this, "撤销本游记的发布申请                              ", 2);
						break;
					case 1:
						// 编辑
						bundle = new Bundle();
						bundle.putString("tvl_id", tvl_id);
						bundle.putString("act_id", act_id);
						openActivity(XinJianYouJizjyActivity.class, bundle);
						isFrist = false;
						break;
					case 2:
						// 提交
						showAlertDialog(XieYouJiActivity.this, "确定要将本游记提交审核后发布              ", 1);
						break;
					}
					break;
				case 1:// 编辑和删除
					switch (menu.getViewType()) {
					case -2:
						// 删除
						showAlertDialog(XieYouJiActivity.this, "确定删除本游记                              ", 3);
						break;
					case -1:
						// 编辑
						bundle = new Bundle();
						bundle.putString("tvl_id", tvl_id);
						bundle.putString("act_id", act_id);
						openActivity(XinJianYouJizjyActivity.class, bundle);
						isFrist = false;
						break;
					case 0:
						// 删除
						showAlertDialog(XieYouJiActivity.this, "确定删除本游记                              ", 3);
						break;
					case 1:
						// 删除
						showAlertDialog(XieYouJiActivity.this, "确定删除本游记                              ", 3);
						break;
					case 2:
						// 编辑
						bundle = new Bundle();
						bundle.putString("tvl_id", tvl_id);
						bundle.putString("act_id", act_id);
						openActivity(XinJianYouJizjyActivity.class, bundle);
						isFrist = false;
						break;
					}
					break;
				case 2:
					// 删除
					showAlertDialog(XieYouJiActivity.this, "确定删除本游记                              ", 3);
					break;
				}
				return false;
			}
		});

		lv_home.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
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
				if (NetUtil.isNetworkAvailable(XieYouJiActivity.this)) {// 有网
					isNet = true;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					sendGetMyTravelActionAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISLOADMORE);
				} else {// 无网
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
				if (NetUtil.isNetworkAvailable(XieYouJiActivity.this)) {// 有网
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					sendGetMyTravelActionAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
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

	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private List<GetMyTravelTravellistItem> mList = null;

		public MyListAdpter(Context context, List<GetMyTravelTravellistItem> list) {
			this.context = context;
			this.mList = list;
		}

		@Override
		public int getCount() {
			return mList != null ? mList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			int item = -3;
			switch (mList.get(position).getVerify()) {
			case -2:
				item = -2;
				break;
			case -1:
				item = -1;
				break;
			case 0:
				item = 0;
				break;
			case 1:
				item = 1;
				break;
			case 2:
				item = 2;
				break;
			}
			return item;
		}

		@Override
		public int getViewTypeCount() {
			return 6;
		}

		@SuppressLint("ResourceAsColor")
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_youjilist, null);
				holder = new ViewHolder();
				holder.youji_list = (FrameLayout) convertView.findViewById(R.id.youji_list);
				holder.im_youji_beijing = (CubeImageView) convertView.findViewById(R.id.im_youji_beijing);
				holder.tv_youji_huodong = (TextView) convertView.findViewById(R.id.tv_youji_huodong);
				holder.tv_youji_geyan = (TextView) convertView.findViewById(R.id.tv_youji_geyan);
				holder.tv_youji_time = (TextView) convertView.findViewById(R.id.tv_youji_time);
				holder.tv_youji_dianzan = (TextView) convertView.findViewById(R.id.tv_youji_dianzan);
				holder.tv_youji_julebu = (TextView) convertView.findViewById(R.id.tv_youji_julebu);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (NetUtil.isNetworkAvailable(XieYouJiActivity.this)) {
				holder.im_youji_beijing.loadImage(imageLoader, mList.get(position).getTvl_cover());
			}
			holder.youji_list.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
			holder.tv_youji_huodong.setText(mList.get(position).getUser_nickname());
			holder.tv_youji_geyan.setText(mList.get(position).getTvl_title());
			if (!TextUtils.isEmpty(mList.get(position).getTvl_update_time())) {
				holder.tv_youji_time
						.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getTvl_update_time()));
			} else {
				holder.tv_youji_time
						.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getTvl_create_time()));
			}
			if (mList.get(position).getAgreeCount() == 0) {
				holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_n, 0, 0, 0);
			} else {
				holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_c, 0, 0, 0);
			}
			holder.tv_youji_dianzan.setText(mList.get(position).getAgreeCount() + "");
			if (mList.get(position).getVerify() == -2) {
				holder.tv_youji_julebu.setText("审核中");
				holder.tv_youji_julebu.setTextColor(getResources().getColor(R.color.title));
			} else if (mList.get(position).getVerify() == -1) {
				holder.tv_youji_julebu.setText("编辑中");
				holder.tv_youji_julebu.setTextColor(getResources().getColor(R.color.content));
			} else if (mList.get(position).getVerify() == 0) {
				holder.tv_youji_julebu.setText("审核中");
				holder.tv_youji_julebu.setTextColor(getResources().getColor(R.color.title));
			} else if (mList.get(position).getVerify() == 1) {
				holder.tv_youji_julebu.setText("已发布");
				holder.tv_youji_julebu.setTextColor(getResources().getColor(R.color.app_green));
				holder.tv_youji_dianzan.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						tvl_id = mList.get(position).getTvl_id();
						if (mList.get(position).getAgreeCount() == 0) {
							if (!TextUtils.isEmpty(App.getInstance().getAut())) {
								sendAgreeTravelActionAsyn();
							} else {
								showToastDengLu();
							}
						}
					}
				});
			} else if (mList.get(position).getVerify() == 2) {
				holder.tv_youji_julebu.setText("审核不通过");
				holder.tv_youji_julebu.setTextColor(getResources().getColor(R.color.red));
			}
			return convertView;
		}

		private class ViewHolder {
			private FrameLayout youji_list;
			private CubeImageView im_youji_beijing;
			private TextView tv_youji_huodong;
			private TextView tv_youji_geyan;
			private TextView tv_youji_time;
			private TextView tv_youji_dianzan;
			private TextView tv_youji_julebu;

		}
	}

	private Thread thread = null;

	// 删除自己写的游记
	public void sendDeleteTravelActionAsyn() {
		thread = new Thread() {
			public void run() {
				DeleteTravelAction();
			}
		};
		thread.start();
	}

	public void DeleteTravelAction() {
		DeleteTravelRequest request = new DeleteTravelRequest();
		DeleteTravelRequestParameter parameter = new DeleteTravelRequestParameter();
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
					DeleteTravelResponse response = new DeleteTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					DeleteTravelResponsePayload payload = (DeleteTravelResponsePayload) response.getPayload();
					deleteState = payload.getState();
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

	private Integer sign = null; // "sign":0;标志位，0为保存（设置状态-4），1为提交（设置状态为0）
	private String tvl_id = null;
	private String act_id = null;
	private String tvl_create_time = null;
	private String tvl_update_time = null;
	private String club_id;// 关联的俱乐部id
	private String tvl_cover;// 游记封面logo
	private String tvl_title;// 游记标题
	private String act_start_time;// 开始时间
	private String act_end_time;// 结束时间
	private String act_venue_time;// 集合时间
	private String act_venue;// 集合地点
	private Integer act_level;// 活动等级
	private String leader_name;// 领队名称
	private String leader_id;// 领队id
	private String city;// 地域（城市）
	private Integer distance;// 实际距离
	private String trace_data;// 轨迹数据
	private String anchor_longitude;// 锚点经度
	private String anchor_latitude;// 锚点纬度
	private String tvl_type;// 游记类型(同活动类型)
	private String tvl_desc;// 游记描述
	private String act_desc_intro;// 游记简介
	private String footprint_data;// 游记足印（脚印数据app）
	private String act_entry_fee;// 实际费用
	private String act_most_equip;// 装备要求
	private String logistics;// 报名须知
	private Integer team_number;// 队伍人数
	private Integer is_share;// 是否分享(是否私有)
	private String team_member;// 活动队员（头像，id json串）
	private String act_weixin;// 活动微信号
	private Integer act_join_num_limit;// 活动人数
	private Double rel_speed;// 平均速度，手填，均速
	private Integer confirmed_member;// 已确认队员人数
	private Integer viceleadercount;// 副队人数
	private Integer malecount;// 男队人数
	private Integer femalecount;// 女队人数
	private String tvl_else;// 其他
	private String act_line;// 路线

	// 添加游记提交审核
	public void sendAddTravelActionAsyn() {
		thread = new Thread() {
			public void run() {
				AddTravelAction();
			}
		};
		thread.start();
	}

	public void AddTravelAction() {
		AddTravelRequest request = new AddTravelRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		AddTravelRequestParameter parameter = new AddTravelRequestParameter();
		parameter.setSign(sign);// 标志位，0为保存（设置状态-4），1为提交（设置状态为0）
		parameter.setAct_id(act_id);
		parameter.setTvl_id(tvl_id);
		parameter.setClub_id(club_id);// 关联的俱乐部id
		parameter.setTvl_cover(tvl_cover);// 游记封面logo
		parameter.setTvl_title(tvl_title);// 游记标题
		parameter.setAct_start_time(act_start_time);// 开始时间
		parameter.setAct_end_time(act_end_time);// 结束时间
		parameter.setAct_venue_time(act_venue_time);// 集合时间
		parameter.setAct_venue(act_venue);// 集合地点
		parameter.setAct_level(act_level);// 活动等级
		parameter.setLeader_name(leader_name);// 领队名称
		parameter.setLeader_id(leader_id);// 领队id
		parameter.setCity(city);// 地域（城市）
		parameter.setDistance(distance);// 实际距离
		parameter.setTrace_data(trace_data);// 轨迹数据
		parameter.setAnchor_longitude(anchor_longitude);// 锚点经度
		parameter.setAnchor_latitude(anchor_latitude);// 锚点纬度
		parameter.setTvl_type(tvl_type);// 游记类型(同活动类型)
		parameter.setTvl_desc(tvl_desc);
		parameter.setTvl_desc_web(tvl_desc);
		parameter.setAct_desc_intro(act_desc_intro);
		parameter.setFootprint_data_web(footprint_data);// 游记足印（脚印数据web）
		parameter.setFootprint_data(footprint_data);// 游记足印（脚印数据app）
		parameter.setAct_entry_fee(act_entry_fee);// 实际费用
		parameter.setAct_most_equip(act_most_equip);// 装备要求
		parameter.setLogistics(logistics);// 报名须知
		parameter.setTeam_number(team_number);// 队伍人数
		parameter.setIs_share(is_share);// 是否分享(是否私有)
		parameter.setTeam_member(team_member);// 活动队员（头像，id json串）
		parameter.setAct_weixin(act_weixin);// 活动微信号
		parameter.setAct_join_num_limit(act_join_num_limit);// 活动人数
		parameter.setRel_speed(rel_speed);// 平均速度，手填，均速
		parameter.setConfirmed_member(confirmed_member);// 已确认队员人数
		parameter.setViceleadercount(viceleadercount);// 副队人数
		parameter.setMalecount(malecount);// 男队人数
		parameter.setFemalecount(femalecount);// 女队人数
		parameter.setTvl_else(tvl_else);// 其他
		parameter.setAct_line(act_line);
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

	private String msgString = null;

	// 取消我写的游记的审核状态
	public void sendCancelTravelVerifyActionAsyn() {
		thread = new Thread() {
			public void run() {
				CancelTravelVerifyAction();
			}
		};
		thread.start();
	}

	public void CancelTravelVerifyAction() {
		CancelTravelVerifyRequest request = new CancelTravelVerifyRequest();
		CancelTravelVerifyRequestParameter parameter = new CancelTravelVerifyRequestParameter();
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
				msgString = responseObject.getString("msg");
				if (ret == 0) {
					CancelTravelVerifyResponse response = new CancelTravelVerifyResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					CancelTravelVerifyResponsePayload payload = (CancelTravelVerifyResponsePayload) response
							.getPayload();
					returnState = payload.getState();
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

	// 得到我写的游记
	private List<GetMyTravelTravellistItem> list = null;
	private List<GetMyTravelTravellistItem> listTemp = null;
	private GetMyTravelTravellistItem getMyTravelTravellistItem = null;

	// 取得我写的游记
	public void sendGetMyTravelActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetMyTravelAction();
			}
		};
		thread.start();
	}

	public void GetMyTravelAction() {
		GetMyTravelRequest request = new GetMyTravelRequest();
		GetMyTravelRequestParameter parameter = new GetMyTravelRequestParameter();
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
					GetMyTravelResponse response = new GetMyTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetMyTravelResponsePayload payload = (GetMyTravelResponsePayload) response.getPayload();
					this.listTemp = payload.getTravellistList();
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

	private int state = 0;

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

	CommonAdapter<NoDataBean> listadapter3 = null;
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
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.travel));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(XieYouJiActivity.this, nodatas,
								R.layout.item_nodata) {
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
						listadapter = new MyListAdpter(XieYouJiActivity.this, list);
						lv_home.setAdapter(listadapter);
						SwipeMenuCreator creator = new SwipeMenuCreator() {
							@Override
							public void create(SwipeMenu menu) {
								switch (menu.getViewType()) {
								case -2:
									createMenu2(menu);
									break;
								case -1:
									createMenu1(menu);
									break;
								case 0:
									createMenu2(menu);
									break;
								case 1:
									createMenu3(menu);
									break;
								case 2:
									createMenu3(menu);
									break;
								}
							}

							private void createMenu1(SwipeMenu menu) {
								SwipeMenuItem tijiao = new SwipeMenuItem(getApplicationContext());
								tijiao.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
								tijiao.setWidth(DensityUtils.dp2px(XieYouJiActivity.this, 45));
								tijiao.setTitle("提交");
								tijiao.setTitleSize(15);
								tijiao.setTitleColor(Color.WHITE);
								menu.addMenuItem(tijiao);

								SwipeMenuItem bianji = new SwipeMenuItem(getApplicationContext());
								bianji.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								bianji.setWidth(DensityUtils.dp2px(XieYouJiActivity.this, 45));
								bianji.setTitle("编辑");
								bianji.setTitleSize(15);
								bianji.setTitleColor(Color.WHITE);
								menu.addMenuItem(bianji);

								SwipeMenuItem shanchu = new SwipeMenuItem(getApplicationContext());
								shanchu.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								shanchu.setWidth(DensityUtils.dp2px(XieYouJiActivity.this, 45));
								shanchu.setTitle("删除");
								shanchu.setTitleSize(15);
								shanchu.setTitleColor(Color.WHITE);
								menu.addMenuItem(shanchu);
							}

							private void createMenu2(SwipeMenu menu) {
								SwipeMenuItem chexiao = new SwipeMenuItem(getApplicationContext());
								chexiao.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
								chexiao.setWidth(DensityUtils.dp2px(XieYouJiActivity.this, 45));
								chexiao.setTitle("撤销");
								chexiao.setTitleSize(15);
								chexiao.setTitleColor(Color.WHITE);
								menu.addMenuItem(chexiao);

								SwipeMenuItem shanchu = new SwipeMenuItem(getApplicationContext());
								shanchu.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								shanchu.setWidth(DensityUtils.dp2px(XieYouJiActivity.this, 45));
								shanchu.setTitle("删除");
								shanchu.setTitleSize(15);
								shanchu.setTitleColor(Color.WHITE);
								menu.addMenuItem(shanchu);
							}

							private void createMenu3(SwipeMenu menu) {
								SwipeMenuItem bianji = new SwipeMenuItem(getApplicationContext());
								bianji.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
								bianji.setWidth(DensityUtils.dp2px(XieYouJiActivity.this, 45));
								bianji.setTitle("编辑");
								bianji.setTitleSize(15);
								bianji.setTitleColor(Color.WHITE);
								menu.addMenuItem(bianji);

								SwipeMenuItem shanchu = new SwipeMenuItem(getApplicationContext());
								shanchu.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								shanchu.setWidth(DensityUtils.dp2px(XieYouJiActivity.this, 45));
								shanchu.setTitle("删除");
								shanchu.setTitleSize(15);
								shanchu.setTitleColor(Color.WHITE);
								menu.addMenuItem(shanchu);
							}
						};
						lv_home.setMenuCreator(creator);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:
				showdialog();
				index = 0;
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				sendGetMyTravelActionAsyn();
				break;
			case CommonUtility.SERVEROK3:
				if (returnState == 1) {
					showdialog();
					index = 0;
					if (listadapter != null) {
						count = listadapter.getCount();
					} else {
						count = 10;
					}
					Loadmore = true;
					LoadmoreData = true;
					sendGetMyTravelActionAsyn();
				}
				returnState = 0;
				break;
			case CommonUtility.SERVEROK4:
				showdialog();
				index = 0;
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				sendGetMyTravelActionAsyn();
				break;
			case CommonUtility.SERVEROK5:// 点赞
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
					sendGetMyTravelActionAsyn();
					break;
				case 2:
					showToast("你已经点过赞了");
					break;

				default:
					break;
				}
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				if (!TextUtils.isEmpty(msgString) && !msgString.contains("操作")) {
					showToast(msgString);
				}
				msgString = null;
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
			default:
				break;
			}
		};
	};
}
