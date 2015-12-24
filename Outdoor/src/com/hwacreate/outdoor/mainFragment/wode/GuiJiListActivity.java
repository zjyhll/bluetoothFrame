package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListDatalistItem;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListRequest;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListRequestParameter;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListResponse;
import com.keyhua.outdoor.protocol.GetTraceDataListAction.GetTraceDataListResponsePayload;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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

public class GuiJiListActivity extends BaseActivity {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;

	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	// 取得轨迹数据列表
	private ArrayList<GetTraceDataListDatalistItem> traceDataLists = null;
	private ArrayList<GetTraceDataListDatalistItem> traceDataList = null;
	private ArrayList<GetTraceDataListDatalistItem> traceDataListTemp = null;
	// 添加的数据
	private String name = null;
	private JSONArray trace_data = null;
	private String picture_url = null;
	private String act_real_start_time = null;
	private String act_real_end_time = null;
	public JSONArray trackArray = null;

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
		case R.id.top_tv_right:
			for (int i = 0; i < trackArray.length(); i++) {
				try {
					XinJianYouJizjyActivity.trackArray.put(trackArray.getJSONObject(i));
				} catch (JSONException e) {
				}
			}
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		traceDataList = new ArrayList<GetTraceDataListDatalistItem>();
		traceDataLists = new ArrayList<GetTraceDataListDatalistItem>();
		traceDataListTemp = new ArrayList<GetTraceDataListDatalistItem>();
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
	}

	@Override
	protected void onInitData() {
		trackArray = new JSONArray();
		initHeaderOther();
		initControl();
		refreshAndLoadMore();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText(getString(R.string.wode_guijilist));
		top_tv_right.setText("确定");
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
				if (NetUtil.isNetworkAvailable(GuiJiListActivity.this)) {// 有网
					index = listadapter.getCount();
					count = 10;
					Loadmore = true;
					sendGetTraceDataListActionAsyn();
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
				if (NetUtil.isNetworkAvailable(GuiJiListActivity.this)) {// 有网
					index = 0;
					count = 10;
					Loadmore = false;
					sendGetTraceDataListActionAsyn();
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
		private List<GetTraceDataListDatalistItem> mList = null;

		public MyListAdpter(Context context, List<GetTraceDataListDatalistItem> list) {
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
				convertView = LayoutInflater.from(context).inflate(R.layout.item_guiji_list, null);
				holder = new ViewHolder();
				holder.icon_guiji = (CubeImageView) convertView.findViewById(R.id.icon_guiji);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				holder.tv_start_time = (TextView) convertView.findViewById(R.id.tv_start_time);
				holder.tv_end_time = (TextView) convertView.findViewById(R.id.tv_end_time);
				holder.ch_choose = (TextView) convertView.findViewById(R.id.ch_choose);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.icon_guiji.loadImage(imageLoader, mList.get(position).getPicture_url());
			holder.tv_name.setText(mList.get(position).getName());
			holder.tv_start_time.setText("开始时间：" + mList.get(position).getStart_time());
			holder.tv_end_time.setText("结束时间：" + mList.get(position).getEnd_time());
			boolean isCheck = false;
			if (trackArray != null && trackArray.length() != 0) {
				for (int i = 0; i < trackArray.length(); i++) {
					try {
						if (TextUtils.equals(trackArray.getJSONObject(i).getString("picture_url"),
								mList.get(position).getPicture_url())) {
							isCheck = true;
						}
					} catch (JSONException e) {
					}
				}
			}
			if (isCheck) {
				holder.ch_choose.setBackgroundResource(R.drawable.shape_cicle_soild);
			} else {
				holder.ch_choose.setBackgroundResource(R.drawable.shape_cicle);
			}
			final ViewHolder holdertemp = holder;
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					boolean isCheck = false;
					int isDown = 0;
					int index = XinJianYouJizjyActivity.trackArray.length() + trackArray.length();
					for (int i = 0; i < trackArray.length(); i++) {
						try {
							if (TextUtils.equals(trackArray.getJSONObject(i).getString("picture_url"),
									mList.get(position).getPicture_url())) {
								isCheck = true;
								isDown = i;
							}
						} catch (JSONException e) {
						}
					}
					if (isCheck) {
						trackArray.remove(isDown);
						holdertemp.ch_choose.setBackgroundResource(R.drawable.shape_cicle);
					} else {
						if (index < 5) {
							for (int i = 0; i < trackArray.length(); i++) {
								try {
									if (TextUtils.equals(trackArray.getJSONObject(i).getString("picture_url"),
											mList.get(position).getPicture_url())) {
										isCheck = true;
										isDown = i;
									}
								} catch (JSONException e) {
								}
							}
							if (isCheck && isDown != 0) {
								trackArray.remove(isDown);
								holdertemp.ch_choose.setBackgroundResource(R.drawable.shape_cicle);
							} else {
								JSONObject jsonObject = new JSONObject();
								name = mList.get(position).getName();
								act_real_start_time = mList.get(position).getStart_time();
								act_real_end_time = mList.get(position).getEnd_time();
								picture_url = mList.get(position).getPicture_url();
								try {
									trace_data = new JSONArray(mList.get(position).getTrace_data());
								} catch (JSONException e) {
								}
								try {
									jsonObject.put("name", name);
									jsonObject.put("act_real_start_time", act_real_start_time);
									jsonObject.put("act_real_end_time", act_real_end_time);
									jsonObject.put("picture_url", picture_url);
									jsonObject.put("trace_data", trace_data);
									trackArray.put(jsonObject);
									holdertemp.ch_choose.setBackgroundResource(R.drawable.shape_cicle_soild);
								} catch (JSONException e1) {
								}
							}
						} else {
							showToast("最多添加5个轨迹哦");
						}
					}
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private CubeImageView icon_guiji;
			private TextView tv_name;
			private TextView tv_start_time;
			private TextView tv_end_time;
			private TextView ch_choose;
		}
	}

	private Thread thread = null;

	// 取得轨迹列表
	public void sendGetTraceDataListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetTraceDataListAction();
			}
		};
		thread.start();
	}

	public void GetTraceDataListAction() {
		GetTraceDataListRequest request = new GetTraceDataListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetTraceDataListRequestParameter parameter = new GetTraceDataListRequestParameter();
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
					GetTraceDataListResponse response = new GetTraceDataListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetTraceDataListResponsePayload payload = (GetTraceDataListResponsePayload) response.getPayload();
					traceDataListTemp = payload.getDatalist();
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
					traceDataList.clear();
				}
				traceDataList.addAll(traceDataListTemp);
				if (XinJianYouJizjyActivity.trackArray != null && XinJianYouJizjyActivity.trackArray.length() != 0) {
					traceDataLists.clear();
					for (int i = 0; i < traceDataList.size(); i++) {
						for (int j = 0; j < XinJianYouJizjyActivity.trackArray.length(); j++) {
							try {
								if (TextUtils.equals(traceDataList.get(i).getPicture_url(),
										XinJianYouJizjyActivity.trackArray.getJSONObject(j).getString("picture_url"))) {
									traceDataLists.add(traceDataList.get(i));
								}
							} catch (JSONException e) {
							}
						}
					}
					for (int j = 0; j < traceDataLists.size(); j++) {
						traceDataList.remove(traceDataLists.get(j));
					}
				}
				if (traceDataList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.guiji));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(GuiJiListActivity.this, nodatas,
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
						listadapter = new MyListAdpter(GuiJiListActivity.this, traceDataList);
						lv_home.setAdapter(listadapter);
					}
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
