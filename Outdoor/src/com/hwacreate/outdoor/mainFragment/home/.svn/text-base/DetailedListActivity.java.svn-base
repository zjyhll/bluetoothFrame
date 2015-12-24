package com.hwacreate.outdoor.mainFragment.home;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.MyLisActivitytAdpter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.HomeTapDangjiBean;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.keyhua.outdoor.protocol.GetActivityList.GetActivityListRequest;
import com.keyhua.outdoor.protocol.GetActivityList.GetActivityListRequestParameter;
import com.keyhua.outdoor.protocol.GetActivityList.GetActivityListResponse;
import com.keyhua.outdoor.protocol.GetActivityList.GetActivityListResponsePayload;
import com.keyhua.outdoor.protocol.GetActivityList.GetActivityListResponsePayloadListItem;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author LaLa 详细列表信息
 * 
 */
public class DetailedListActivity extends BaseActivity implements OnItemClickListener {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;// listView
	private MyLisActivitytAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;
	private String name = null;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;
	/**
	 * 活动类型，规定主界面有11个类型，主界面列表类型为11，默认11
	 */
	private String type = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_list);
		init();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendGetActivityListActionAsyn();
		}
	}

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		list = new ArrayList<GetActivityListResponsePayloadListItem>();
		listTemp = new ArrayList<GetActivityListResponsePayloadListItem>();
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
		lv_home.setOnItemClickListener(this);
	}

	@Override
	protected void onInitData() {
		type = getIntent().getStringExtra("type");
		initHeaderOther();
		initControl();
		refreshAndLoadMore();
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
				if (NetUtil.isNetworkAvailable(DetailedListActivity.this)) {// 有网
					isNet = true;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					sendGetActivityListActionAsyn();
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
				if (NetUtil.isNetworkAvailable(DetailedListActivity.this)) {// 有网
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					sendGetActivityListActionAsyn();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResload() {
		Bundle bundle = getIntent().getExtras();
		name = bundle.getString("Name");
		top_tv_title.setText(name);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		if (list.get(position).getAct_state() == 3 || list.get(position).getAct_state() == 4) {
			if (TextUtils.equals(list.get(position).getAct_id(), App.getInstance().getHuoDongId())) {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
			} else if (TextUtils.equals(list.get(position).getAct_id(), App.getInstance().getLeaderHuoDongId())) {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
			} else {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
			}
		} else {
			bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
		}
		bundle.putString("act_id", list.get(position).getAct_id());
		openActivity(HuoDongXiangQingActivity.class, bundle);
		isFrist = false;
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

	private List<GetActivityListResponsePayloadListItem> list = null;
	private List<GetActivityListResponsePayloadListItem> listTemp = null;

	public void getActivityListAction() {
		GetActivityListRequest request = new GetActivityListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetActivityListRequestParameter parameter = new GetActivityListRequestParameter();
		parameter.setAct_type(type);
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
					GetActivityListResponse response = new GetActivityListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetActivityListResponsePayload payload = (GetActivityListResponsePayload) response.getPayload();
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
						nodata.setTitle(getResources().getString(R.string.home_page));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(DetailedListActivity.this, nodatas,
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
						listadapter = new MyLisActivitytAdpter(DetailedListActivity.this, list, imageLoaderRectF,
								mImageLoader, options);
						lv_home.setAdapter(listadapter);
					}
				}
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				break;
			case CommonUtility.SERVERERRORLOGIN:
				break;
			default:
				break;
			}
		};
	};
}
