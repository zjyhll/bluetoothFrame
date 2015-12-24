package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.MyList3Adpter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.keyhua.outdoor.protocol.GetMyOrderActivityList.GetMyOrderActivityListActivitylistItem;
import com.keyhua.outdoor.protocol.GetMyOrderActivityList.GetMyOrderActivityListRequest;
import com.keyhua.outdoor.protocol.GetMyOrderActivityList.GetMyOrderActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetMyOrderActivityList.GetMyOrderActivityListResponse;
import com.keyhua.outdoor.protocol.GetMyOrderActivityList.GetMyOrderActivityListResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author LaLa 活动订单
 * 
 */
public class HuoDongDingDanActivity extends BaseActivity implements OnItemClickListener {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;// listView
	private PtrFrameLayout mPtrFrameLayout;
	// 列表中数据
	private MyList3Adpter listadapter = null;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;
	// pop sos按钮弹出
	private PopupWindow popContact = null;
	private RelativeLayout parent = null;// 半透明背景色
	private LinearLayout pop_dingdan_image = null;// 半透明背景色
	private LinearLayout dingdan_all = null;
	private LinearLayout dingdan1 = null;
	private LinearLayout dingdan2 = null;
	private LinearLayout dingdan3 = null;
	private LinearLayout dingdan4 = null;
	private LinearLayout dingdan5 = null;
	private LinearLayout dingdan6 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_list);
		init();
	}

	private View view = null;

	private void initPopwindow() {
		view = getLayoutInflater().inflate(R.layout.pop_huodongdingdan, null);
		popContact = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popContact.setBackgroundDrawable(new BitmapDrawable());
		parent = (RelativeLayout) view.findViewById(R.id.parent);
		pop_dingdan_image = (LinearLayout) view.findViewById(R.id.pop_dingdan_image);
		dingdan_all = (LinearLayout) view.findViewById(R.id.dingdan_all);
		dingdan1 = (LinearLayout) view.findViewById(R.id.dingdan1);
		dingdan2 = (LinearLayout) view.findViewById(R.id.dingdan2);
		dingdan3 = (LinearLayout) view.findViewById(R.id.dingdan3);
		dingdan4 = (LinearLayout) view.findViewById(R.id.dingdan4);
		dingdan5 = (LinearLayout) view.findViewById(R.id.dingdan5);
		dingdan6 = (LinearLayout) view.findViewById(R.id.dingdan6);
		parent.setOnClickListener(this);
		pop_dingdan_image.setOnClickListener(this);
		dingdan_all.setOnClickListener(this);
		dingdan1.setOnClickListener(this);
		dingdan2.setOnClickListener(this);
		dingdan3.setOnClickListener(this);
		dingdan4.setOnClickListener(this);
		dingdan5.setOnClickListener(this);
		dingdan6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.top_tv_right:
			popContact.showAsDropDown(top_tv_right, 0, 0);
			break;
		case R.id.parent:
			popContact.dismiss();
			break;
		case R.id.pop_dingdan_image:
			break;
		case R.id.dingdan_all:
			popContact.dismiss();
			showdialog();
			listadapter = null;
			top_tv_right.setText("全部");
			act_state = 7;
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
			break;
		case R.id.dingdan1:
			popContact.dismiss();
			showdialog();
			listadapter = null;
			top_tv_right.setText("报名中");
			act_state = 1;
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
			break;
		case R.id.dingdan2:
			popContact.dismiss();
			showdialog();
			listadapter = null;
			top_tv_right.setText("整队中");
			act_state = 2;
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
			break;
		case R.id.dingdan3:
			popContact.dismiss();
			showdialog();
			listadapter = null;
			top_tv_right.setText("准备出行");
			act_state = 3;
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
			break;
		case R.id.dingdan4:
			popContact.dismiss();
			showdialog();
			listadapter = null;
			top_tv_right.setText("出行中");
			act_state = 4;
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
			break;
		case R.id.dingdan5:
			popContact.dismiss();
			showdialog();
			listadapter = null;
			top_tv_right.setText("收队中");
			act_state = 5;
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
			break;
		case R.id.dingdan6:
			popContact.dismiss();
			showdialog();
			listadapter = null;
			top_tv_right.setText("活动结束");
			act_state = 6;
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
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
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendAsyn();
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		initPopwindow();
		listMyOrderActivityList = new ArrayList<GetMyOrderActivityListActivitylistItem>();
		listMyOrderActivityListTemp = new ArrayList<GetMyOrderActivityListActivitylistItem>();
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
		// 获取数据，并放置在adapter中，同时应该存到数据库中
		lv_home.setOnItemClickListener(this);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText(getString(R.string.wode_huodong));
		top_tv_right.setText("全部");
		top_tv_right.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chengshi, 0);
		showdialog();
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
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
				if (NetUtil.isNetworkAvailable(HuoDongDingDanActivity.this)) {// 有网
					isNet = true;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
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
				if (NetUtil.isNetworkAvailable(HuoDongDingDanActivity.this)) {// 有网
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					sendAsyn();
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
		if (listMyOrderActivityList.get(position).getAct_state() == 3
				|| listMyOrderActivityList.get(position).getAct_state() == 4) {
			if (TextUtils.equals(listMyOrderActivityList.get(position).getAct_id(), App.getInstance().getHuoDongId())) {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
			} else if (TextUtils.equals(listMyOrderActivityList.get(position).getAct_id(),
					App.getInstance().getLeaderHuoDongId())) {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
			} else {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
			}
		} else {
			bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
		}
		// 因为加了header，所以position从1开始
		bundle.putString("act_id", listMyOrderActivityList.get(position).getAct_id());
		openActivity(HuoDongXiangQingActivity.class, bundle);
		isFrist = false;
	}

	private Thread thread = null;

	// 收藏的活动
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				getAction();
			}
		};
		thread.start();
	}

	private List<GetMyOrderActivityListActivitylistItem> listMyOrderActivityList = null;
	private List<GetMyOrderActivityListActivitylistItem> listMyOrderActivityListTemp = null;
	private int act_state = 7;// 7 全部 1-6状态

	public void getAction() {
		GetMyOrderActivityListRequest request = new GetMyOrderActivityListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetMyOrderActivityListRequestParameter parameter = new GetMyOrderActivityListRequestParameter();
		parameter.setCount(count);
		parameter.setIndex(index);
		parameter.setAct_state(act_state);
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
					GetMyOrderActivityListResponse response = new GetMyOrderActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetMyOrderActivityListResponsePayload payload = (GetMyOrderActivityListResponsePayload) response
							.getPayload();
					this.listMyOrderActivityListTemp = payload.getActivitylistList();
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
			case CommonUtility.SERVEROK1:
				if (Loadmore) {
					if (LoadmoreData) {
						listMyOrderActivityList.clear();
					}
				} else {
					listMyOrderActivityList.clear();
				}
				listMyOrderActivityList.addAll(listMyOrderActivityListTemp);
				if (listMyOrderActivityList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.home_page));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(HuoDongDingDanActivity.this, nodatas,
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
						listadapter = new MyList3Adpter(HuoDongDingDanActivity.this, listMyOrderActivityList,
								imageLoaderRectF, mImageLoader, options);
						lv_home.setAdapter(listadapter);
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
				if (isshowdialog()) {
					closedialog();
				}
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
			default:
				break;
			}
		};
	};
}
