package com.hwacreate.outdoor.mainFragment.huodongxiangqing;

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
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListClublistItem;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListRequest;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListRequestParameter;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListResponse;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListResponsePayload;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class XinjianyoujijlbOrhdActivity extends BaseActivity {
	private CommonAdapter<GetMyClubListClublistItem> listadapter2 = null;
	private MyListAdpter listadapter1 = null;
	private boolean isjlb = false;
	// 类型
	private List<String> hotList = null;
	private int hoticon[] = new int[] { R.drawable.home_dengshan, R.drawable.home_tubu, R.drawable.home_qixing,
			R.drawable.home_zijia, R.drawable.home_sheying, R.drawable.home_xiuxian, R.drawable.home_luying,
			R.drawable.home_qinzi };
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;// listView
	private PtrFrameLayout mPtrFrameLayout;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_list);
		initHeaderOther();
		init();
	}

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
	protected void onInitData() {
		listMyClubListClubList = new ArrayList<GetMyClubListClublistItem>();
		listMyClubListClubListTemp = new ArrayList<GetMyClubListClublistItem>();
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
		showdialog();
		isjlb = getIntent().getBooleanExtra("isjlb", false);
		if (isjlb) {// 俱乐部列表
			top_tv_title.setText("我的俱乐部");
			sendGetMyClubListActionAsyn();
		} else {// 活动列表
			top_tv_title.setText("活动分类");
			huodongList();
		}
	}

	private void huodongList() {
		hotList = new ArrayList<String>();
		hotList.add("登山");
		hotList.add("徒步");
		hotList.add("骑行");
		hotList.add("自驾");
		hotList.add("摄影");
		hotList.add("休闲");
		hotList.add("露营");
		hotList.add("亲子");
		listadapter1 = new MyListAdpter(XinjianyoujijlbOrhdActivity.this, hotList);
		lv_home.setAdapter(listadapter1);
		if (isshowdialog()) {
			closedialog();
		}
	}

	@Override
	protected void onResload() {
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
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
				if (NetUtil.isNetworkAvailable(XinjianyoujijlbOrhdActivity.this)) {// 有网
					if (isjlb) {
						if (listadapter2 != null) {
							index = listadapter2.getCount();
						} else {
							index = 0;
						}
						count = 10;
						Loadmore = true;
						sendGetMyClubListActionAsyn();
					}
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
				if (NetUtil.isNetworkAvailable(XinjianyoujijlbOrhdActivity.this)) {// 有网
					if (isjlb) {
						index = 0;
						count = 10;
						Loadmore = false;
						sendGetMyClubListActionAsyn();
					}
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

	CommonAdapter<NoDataBean> listadapter3 = null;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (!Loadmore) {
					listMyClubListClubList.clear();
				}
				listMyClubListClubList.addAll(listMyClubListClubListTemp);
				if (listMyClubListClubList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.organization));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(XinjianyoujijlbOrhdActivity.this, nodatas,
								R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					listadapter2 = null;
				} else {
					if (Loadmore && listadapter2 != null) {
						listadapter2.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter2 = new CommonAdapter<GetMyClubListClublistItem>(XinjianyoujijlbOrhdActivity.this,
								listMyClubListClubList, R.layout.item_zhuzhilist) {

							@Override
							public void convert(ViewHolderUntil helper, final GetMyClubListClublistItem item,
									int position) {
								try {
									JSONArray jsonArray = new JSONArray(item.getAlbum());
									helper.setCubeImageByUrl(R.id.icon, jsonArray.getString(0), imageLoader);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								helper.getView(R.id.zuzhi_list).setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
								helper.setText(R.id.tv_clob, item.getClub_name());
								helper.setText(R.id.tv_Province, item.getClub_city_str());
								helper.setText(R.id.tv_town, item.getClub_county_str());
								helper.setText(R.id.tv_chengyuan, item.getMember().toString());
								helper.setText(R.id.tv_huodong, item.getActivity().toString());
								helper.getView(R.id.tv_join).setVisibility(View.GONE);
							}

							public void setOncliklisenter2(View v, GetMyClubListClublistItem item) {
								App.getInstance().setClub_id(item.getClub_id());
								App.getInstance().setClub_name(item.getClub_name());
								finish();
							};
						};
						lv_home.setAdapter(listadapter2);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
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

	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		public List<String> mLists = null;

		public MyListAdpter(Context context, List<String> mlist) {
			this.context = context;
			this.mLists = mlist;
		}

		@Override
		public int getCount() {
			return mLists != null ? mLists.size() : 0;
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
				convertView = LayoutInflater.from(context).inflate(R.layout.item_xinjianyoujihd, null);
				holder = new ViewHolder();
				holder.huodongleixing = (TextView) convertView.findViewById(R.id.huodongleixing);
				holder.huodongicon = (ImageView) convertView.findViewById(R.id.huodongicon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.huodongicon.setBackgroundResource(hoticon[position]);
			holder.huodongleixing.setText(mLists.get(position));
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					App.getInstance().setHuodongleixing(hotList.get(position));
					finish();
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private TextView huodongleixing;
			private ImageView huodongicon;
		}
	}

	private List<GetMyClubListClublistItem> listMyClubListClubList = null;
	private List<GetMyClubListClublistItem> listMyClubListClubListTemp = null;
	private Thread thread = null;

	public void sendGetMyClubListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetMyClubListAction();
			}
		};
		thread.start();
	}

	public void GetMyClubListAction() {
		GetMyClubListRequest request = new GetMyClubListRequest();
		GetMyClubListRequestParameter parameter = new GetMyClubListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setCity(App.getInstance().getContactCity());
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
					GetMyClubListResponse response = new GetMyClubListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetMyClubListResponsePayload payload = (GetMyClubListResponsePayload) response.getPayload();
					listMyClubListClubListTemp = payload.getClublistList();
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
}
