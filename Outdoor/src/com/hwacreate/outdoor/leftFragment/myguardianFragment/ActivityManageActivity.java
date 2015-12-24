package com.hwacreate.outdoor.leftFragment.myguardianFragment;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListActivitylistItem;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListRequest;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListResponse;
import com.keyhua.outdoor.protocol.GetLeaderActivityListAction.GetLeaderActivityListResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author 曾金叶 活动管理 @2015-8-12 可选活动
 * @下午4:41:11
 */
public class ActivityManageActivity extends BaseActivity {
	// 上下文对象
	private Context context = null;
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	// 头部
	private View headerLayout = null;
	private LinearLayout headerParent = null;
	private PtrFrameLayout mPtrFrameLayout;
	public String club_id = null;
	// listview初始化
	private ListView lv_huodongguanli = null;// listView
	private List<GetLeaderActivityListActivitylistItem> list = null;
	private List<GetLeaderActivityListActivitylistItem> listTemp = null;
	private CommonAdapter<GetLeaderActivityListActivitylistItem> listadapter = null;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	// 是否加载更多
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFirst = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leftfrag_zhengduichuxing_actmanage);
		context = ActivityManageActivity.this;
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFirst && !TextUtils.isEmpty(App.getInstance().getAut())) {
			showdialog();
			index = 0;
			Loadmore = true;
			LoadmoreData = true;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			sendGetLeaderActivityListAsyn();
		}
	}

	@Override
	protected void onInitData() {
		list = new ArrayList<GetLeaderActivityListActivitylistItem>();
		listTemp = new ArrayList<GetLeaderActivityListActivitylistItem>();
		lv_huodongguanli = (ListView) findViewById(R.id.lv_huodongguanli_list);
		initHeaderOther();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("我组织的活动");
		showdialog();
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
	}

	private void refreshAndLoadMore() {
		// 上下刷新START--------------------------------------------------------------------
		// 获取装载VIew的容器
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);
		// 获取view的引用
		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
		// 使用默认样式
		loadMoreListViewContainer.useDefaultHeader();
		// 加载更多数据，当列表滑动到最底部的时候，触发加载更多操作，
		// 这是需要从网络加载数据，或者是从数据库去读取数据
		// 给View 设置加载更多的Handler 去异步加载View需要显示的数据和VIew
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			// loadMoreListViewContainer调用onLoadMore传入loadMoreListViewContainer自身对象
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (NetUtil.isNetworkAvailable(ActivityManageActivity.this)) {// 有网
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					sendGetLeaderActivityListAsyn();
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
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_huodongguanli, header);
			}

			// 开始刷新容器开头
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (NetUtil.isNetworkAvailable(ActivityManageActivity.this)) {// 有网
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
					index = 0;
					count = 10;
					Loadmore = false;
					sendGetLeaderActivityListAsyn();
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
	/**
	 * 活动队伍管理接口接入
	 * 
	 */

	private Thread thread = null;

	public void sendGetLeaderActivityListAsyn() {
		thread = new Thread() {
			public void run() {
				GetLeaderActivityListAction();
			}
		};
		thread.start();
	}

	public void GetLeaderActivityListAction() {
		GetLeaderActivityListRequest request = new GetLeaderActivityListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetLeaderActivityListRequestParameter parameter = new GetLeaderActivityListRequestParameter();
		parameter.setIndex(index);
		parameter.setCount(count);
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
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
					// 处理返回的参数，需要强制类型转换
					GetLeaderActivityListResponsePayload payload = (GetLeaderActivityListResponsePayload) response
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

	CommonAdapter<NoDataBean> listadapter3 = null;
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
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.home_page));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(ActivityManageActivity.this, nodatas,
								R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_huodongguanli.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new CommonAdapter<GetLeaderActivityListActivitylistItem>(context, list,
								R.layout.item_homelist) {
							@Override
							public void convert(ViewHolderUntil helper, GetLeaderActivityListActivitylistItem item,
									int position) {
								try {
									JSONArray jsonArray = new JSONArray(mDatas.get(position).getAct_logo());
									helper.setCubeImageByUrl(R.id.icon, jsonArray.getString(0), imageLoaderRectF);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								helper.getView(R.id.home_view).setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
								helper.setText(R.id.tv_title, item.getAct_title());
								helper.setText(R.id.tv_content, item.getAct_desc_intro());
								if (!TextUtils.isEmpty(mDatas.get(position).getClub())) {
									helper.setText(R.id.tv_des, item.getClub());
									helper.setCubeImageByUrlLoader(R.id.iv_icon, item.getClub_head(), mImageLoader);
								} else if (!TextUtils.isEmpty(mDatas.get(position).getLeader())) {
									helper.setText(R.id.tv_des, item.getLeader());
									helper.setCubeImageByUrlLoader(R.id.iv_icon, item.getLeader_head(), mImageLoader);
								}
								helper.setText(R.id.tv_time,
										TimeDateUtils.formatDateFromDatabaseTime(item.getAct_start_time()));
								helper.setText(R.id.tv_action, item.getAct_type());
								if (TextUtils.equals(item.getAct_type(), "登山")) {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.dengshan));
								} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "徒步")) {
									helper.setTextColor(R.id.tv_action, mContext.getResources().getColor(R.color.tubu));
								} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "骑行")) {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.qixing));
								} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "自驾")) {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.zijia));
								} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "摄影")) {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.sheying));
								} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "休闲")) {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.xiuxian));
								} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "露营")) {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.luying));
								} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "亲子")) {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.qinzi));
								} else {
									helper.setTextColor(R.id.tv_action,
											mContext.getResources().getColor(R.color.content));
								}
								switch (item.getVerify()) {// 审核状态：-1审核不通过，0待审核，1审核通过
								case -1:
									helper.setText(R.id.tv_status, CommonUtility.SHENHESHIBAIStr);
									helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.red));
									break;
								case 0:
									helper.setText(R.id.tv_status, CommonUtility.SHENHEZHONGStr);
									helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.red));
									break;
								case 1:
									switch (item.getAct_state()) {
									// case CommonUtility.SHENHESHIBAIInt:
									// helper.setText(R.id.tv_status,
									// CommonUtility.SHENHESHIBAIStr);
									// helper.setTextColor(R.id.tv_status,
									// mContext.getResources().getColor(R.color.red));
									// break;
									// case CommonUtility.SHENHEZHONGInt:
									// helper.setText(R.id.tv_status,
									// CommonUtility.SHENHEZHONGStr);
									// helper.setTextColor(R.id.tv_status,
									// mContext.getResources().getColor(R.color.content));
									// break;
									case CommonUtility.BAOMINGZHONGInt:
										helper.setText(R.id.tv_status, CommonUtility.BAOMINGZHONGStr);
										helper.setTextColor(R.id.tv_status,
												mContext.getResources().getColor(R.color.content));
										break;
									case CommonUtility.ZHENGDUIInt:
										helper.setText(R.id.tv_status, CommonUtility.ZHENGDUIStr);
										helper.setTextColor(R.id.tv_status,
												mContext.getResources().getColor(R.color.content));
										break;
									case CommonUtility.ZHUNBEIInt:
										helper.setText(R.id.tv_status, CommonUtility.ZHUNBEIStr);
										helper.setTextColor(R.id.tv_status,
												mContext.getResources().getColor(R.color.content));
										break;
									case CommonUtility.CHUXINGInt:
										helper.setText(R.id.tv_status, CommonUtility.CHUXINGStr);
										helper.setTextColor(R.id.tv_status,
												mContext.getResources().getColor(R.color.content));
										break;
									case CommonUtility.DIANPINGInt:
										helper.setText(R.id.tv_status, CommonUtility.DIANPINGStr);
										helper.setTextColor(R.id.tv_status,
												mContext.getResources().getColor(R.color.content));
										break;
									case CommonUtility.JIESHUInt:
										helper.setText(R.id.tv_status, CommonUtility.JIESHUStr);
										helper.setTextColor(R.id.tv_status,
												mContext.getResources().getColor(R.color.content));
										break;
									}
									break;
								case 2:
									helper.setText(R.id.tv_status, CommonUtility.SHENHEZHONGStr);
									helper.setTextColor(R.id.tv_status, mContext.getResources().getColor(R.color.red));
									break;
								}
							}

							public void setOncliklisenter2(View v, GetLeaderActivityListActivitylistItem item) {
								Bundle bundle = new Bundle();
								bundle.putInt("fromrenwu", CommonUtility.XianShiTab_SheZhi);
								bundle.putString("act_id", item.getAct_id());
								openActivity(HuoDongXiangQingActivity.class, bundle);
								isFirst = false;
							};
						};
						lv_huodongguanli.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
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
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				break;
			default:
				break;
			}
		};
	};

}
