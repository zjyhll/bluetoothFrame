package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.JuLeBuXiangQingHaoYou;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.keyhua.outdoor.protocol.GetCityFriendsListAction.GetCityFriendsListFriendsItem;
import com.keyhua.outdoor.protocol.GetCityFriendsListAction.GetCityFriendsListRequest;
import com.keyhua.outdoor.protocol.GetCityFriendsListAction.GetCityFriendsListRequestParameter;
import com.keyhua.outdoor.protocol.GetCityFriendsListAction.GetCityFriendsListResponse;
import com.keyhua.outdoor.protocol.GetCityFriendsListAction.GetCityFriendsListResponsePayload;
import com.keyhua.outdoor.protocol.GetFansListAction.GetFansListFriendsItem;
import com.keyhua.outdoor.protocol.GetFansListAction.GetFansListRequest;
import com.keyhua.outdoor.protocol.GetFansListAction.GetFansListRequestParameter;
import com.keyhua.outdoor.protocol.GetFansListAction.GetFansListResponse;
import com.keyhua.outdoor.protocol.GetFansListAction.GetFansListResponsePayload;
import com.keyhua.outdoor.protocol.GetInterestUserListAction.GetInterestUserListFriendsItem;
import com.keyhua.outdoor.protocol.GetInterestUserListAction.GetInterestUserListRequest;
import com.keyhua.outdoor.protocol.GetInterestUserListAction.GetInterestUserListRequestParameter;
import com.keyhua.outdoor.protocol.GetInterestUserListAction.GetInterestUserListResponse;
import com.keyhua.outdoor.protocol.GetInterestUserListAction.GetInterestUserListResponsePayload;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserRequest;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserRequestParameter;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserResponse;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
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
 * @author LaLa 好友
 *
 */
public class HaoYouActivity extends BaseActivity {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView mListView = null;
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout = null;
	private List<GetCityFriendsListFriendsItem> list1 = null;
	private List<GetInterestUserListFriendsItem> list2 = null;
	private List<GetFansListFriendsItem> list3 = null;
	private List<GetCityFriendsListFriendsItem> listTemp1 = null;
	private List<GetInterestUserListFriendsItem> listTemp2 = null;
	private List<GetFansListFriendsItem> listTemp3 = null;
	// 顶部提示框
	private TextView top_jihua_num = null;
	private TextView top_jihua_num_view = null;
	private TextView top_jihua_now = null;
	private TextView top_jihua_now_view = null;
	private View top_jihua_fensi_view = null;
	private TextView top_jihua_fensi = null;
	private LinearLayout top_view = null;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;
	private int tab_type = 1;
	private JuLeBuXiangQingHaoYou HaoYou = null;
	private List<JuLeBuXiangQingHaoYou> HaoYouAll = null;
	private String Userid = null;

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
		case R.id.top_jihua_num_view:
			top_tv_title.setText("同城好友");
			tab_type = 1;
			onrefreshAndLoadmore();
			break;
		case R.id.top_jihua_now_view:
			top_tv_title.setText("关注好友");
			tab_type = 2;
			onrefreshAndLoadmore();
			break;
		case R.id.top_jihua_fensi:
			top_tv_title.setText("我的粉丝");
			tab_type = 3;
			onrefreshAndLoadmore();
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
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			switch (tab_type) {
			case 1:
				sendAsyn1();
				break;
			case 2:
				sendAsyn2();
				break;
			case 3:
				sendAsyn3();
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		list1 = new ArrayList<GetCityFriendsListFriendsItem>();
		list2 = new ArrayList<GetInterestUserListFriendsItem>();
		list3 = new ArrayList<GetFansListFriendsItem>();
		listTemp1 = new ArrayList<GetCityFriendsListFriendsItem>();
		listTemp2 = new ArrayList<GetInterestUserListFriendsItem>();
		listTemp3 = new ArrayList<GetFansListFriendsItem>();
		HaoYouAll = new ArrayList<JuLeBuXiangQingHaoYou>();
		mListView = (ListView) findViewById(R.id.lv_home_detailed_list);
		top_jihua_num = (TextView) findViewById(R.id.top_jihua_num);
		top_jihua_num_view = (TextView) findViewById(R.id.top_jihua_num_view);
		top_jihua_now = (TextView) findViewById(R.id.top_jihua_now);
		top_jihua_now_view = (TextView) findViewById(R.id.top_jihua_now_view);
		top_jihua_fensi = (TextView) findViewById(R.id.top_jihua_fensi);
		top_jihua_fensi_view = (View) findViewById(R.id.top_jihua_fensi_view);
		top_view = (LinearLayout) findViewById(R.id.top_view);
	}

	@Override
	protected void onResload() {
		top_view.setVisibility(View.VISIBLE);
		top_jihua_fensi.setVisibility(View.VISIBLE);
		top_jihua_fensi_view.setVisibility(View.VISIBLE);
		top_jihua_num.setVisibility(View.GONE);
		top_jihua_now.setVisibility(View.GONE);
		top_tv_title.setText(getString(R.string.wode_haoyou));
		top_jihua_num_view.setText("同城好友");
		top_jihua_now_view.setText("关注好友");
		top_jihua_fensi.setText("我的粉丝");
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_jihua_num_view.setOnClickListener(this);
		top_jihua_now_view.setOnClickListener(this);
		top_jihua_fensi.setOnClickListener(this);
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
				if (NetUtil.isNetworkAvailable(HaoYouActivity.this)) {// 有网
					isNet = true;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					switch (tab_type) {
					case 1:
						sendAsyn1();
						break;
					case 2:
						sendAsyn2();
						break;
					case 3:
						sendAsyn3();
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
		// 容器设置异步线程检查是否可以下拉刷新，并且开始下拉刷新用户头
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
			}

			// 开始刷新容器开头
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (NetUtil.isNetworkAvailable(HaoYouActivity.this)) {// 有网
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					switch (tab_type) {
					case 1:
						sendAsyn1();
						break;
					case 2:
						sendAsyn2();
						break;
					case 3:
						sendAsyn3();
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
		onrefreshAndLoadmore();
	}

	private void onrefreshAndLoadmore() {// load至少刷新多少1秒
		mPtrFrameLayout.setLoadingMinTime(1000);
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

	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private List<JuLeBuXiangQingHaoYou> mlist = null;

		public MyListAdpter(Context context, List<JuLeBuXiangQingHaoYou> list) {
			this.context = context;
			this.mlist = list;
		}

		@Override
		public int getCount() {
			return mlist != null ? mlist.size() : 0;
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
				convertView = LayoutInflater.from(context).inflate(R.layout.item_haoyou, null);
				holder = new ViewHolder();
				holder.haoyou_icon = (CubeImageView) convertView.findViewById(R.id.haoyou_icon);
				holder.haoyou_name = (TextView) convertView.findViewById(R.id.haoyou_name);
				holder.haoyou_fensi = (TextView) convertView.findViewById(R.id.haoyou_fensi);
				holder.haoyou_tianjia = (TextView) convertView.findViewById(R.id.haoyou_tianjia);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.haoyou_icon.loadImage(imageLoader, mlist.get(position).getHead());
			holder.haoyou_name.setText(mlist.get(position).getNickname());
			if (mlist.get(position).getIs_leader() == 1) {
				holder.haoyou_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.haoyou_lingdui, 0);
			} else {
				holder.haoyou_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.haoyou_duiyuan, 0);
			}
			if (mlist.get(position).getIs_attention() != 2) {
				holder.haoyou_tianjia.setVisibility(View.VISIBLE);
				if (mlist.get(position).getIs_attention() == 0) {
					holder.haoyou_tianjia.setText("关注");
					holder.haoyou_tianjia.setTextColor(getResources().getColor(R.color.content));
					holder.haoyou_tianjia.setBackgroundResource(R.drawable.shape_tianjia);
					holder.haoyou_tianjia.setEnabled(true);
				} else if (mlist.get(position).getIs_attention() == 1) {
					holder.haoyou_tianjia.setText("已关注");
					holder.haoyou_tianjia.setTextColor(getResources().getColor(R.color.white));
					holder.haoyou_tianjia.setBackgroundResource(R.drawable.shape_yitianjia);
					holder.haoyou_tianjia.setEnabled(false);
				}
			} else {
				holder.haoyou_tianjia.setVisibility(View.GONE);
			}
			holder.haoyou_fensi.setText("" + mlist.get(position).getFanscount());
			holder.haoyou_tianjia.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Userid = mlist.get(position).getUserid();
					sendAsyn4();
				}
			});
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString("Userid", mlist.get(position).getUserid());
					openActivity(HaoYouDetailActivity.class, bundle);
					isFrist = false;
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private CubeImageView haoyou_icon;
			private TextView haoyou_name, haoyou_fensi, haoyou_tianjia;
		}
	}

	private Thread thread = null;

	// 同城好友
	public void sendAsyn1() {
		thread = new Thread() {
			public void run() {
				Action1();
			}
		};
		thread.start();
	}

	public void Action1() {
		GetCityFriendsListRequest request = new GetCityFriendsListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetCityFriendsListRequestParameter parameter = new GetCityFriendsListRequestParameter();
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
					GetCityFriendsListResponse response = new GetCityFriendsListResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetCityFriendsListResponsePayload payload = (GetCityFriendsListResponsePayload) response
							.getPayload();
					this.listTemp1 = payload.getFriendsList();
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

	// 关注的好友
	public void sendAsyn2() {
		thread = new Thread() {
			public void run() {
				Action2();
			}
		};
		thread.start();
	}

	public void Action2() {
		GetInterestUserListRequest request = new GetInterestUserListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetInterestUserListRequestParameter parameter = new GetInterestUserListRequestParameter();
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
					GetInterestUserListResponse response = new GetInterestUserListResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetInterestUserListResponsePayload payload = (GetInterestUserListResponsePayload) response
							.getPayload();
					this.listTemp2 = payload.getFriendsList();
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 我的粉丝
	public void sendAsyn3() {
		thread = new Thread() {
			public void run() {
				Action3();
			}
		};
		thread.start();
	}

	public void Action3() {
		GetFansListRequest request = new GetFansListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetFansListRequestParameter parameter = new GetFansListRequestParameter();
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
					GetFansListResponse response = new GetFansListResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetFansListResponsePayload payload = (GetFansListResponsePayload) response.getPayload();
					this.listTemp3 = payload.getFriendsList();
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 关注好友
	public void sendAsyn4() {
		thread = new Thread() {
			public void run() {
				Action4();
			}
		};
		thread.start();
	}

	public void Action4() {
		InterestUserRequest request = new InterestUserRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		InterestUserRequestParameter parameter = new InterestUserRequestParameter();
		request.setParameter(parameter);
		parameter.setU_id(Userid);
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
					InterestUserResponse response = new InterestUserResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					InterestUserResponsePayload payload = (InterestUserResponsePayload) response.getPayload();
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

	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				HaoYouAll.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						list1.clear();
					}
					list1.addAll(listTemp1);
				} else {
					list1.clear();
					list1.addAll(listTemp1);
				}
				for (int i = 0; i < list1.size(); i++) {
					HaoYou = new JuLeBuXiangQingHaoYou();
					HaoYou.setUserid(list1.get(i).getUserid());
					HaoYou.setHead(list1.get(i).getHead());
					HaoYou.setNickname(list1.get(i).getNickname());
					HaoYou.setIs_leader(list1.get(i).getIs_leader());
					HaoYou.setFanscount(list1.get(i).getFanscount());
					HaoYou.setIs_attention(list1.get(i).getIs_attention());
					HaoYouAll.add(HaoYou);
				}
				initcontrollist();
				break;
			case CommonUtility.SERVEROK2:
				HaoYouAll.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						list2.clear();
					}
					list2.addAll(listTemp2);
				} else {
					list2.clear();
					list2.addAll(listTemp2);
				}
				for (int i = 0; i < list2.size(); i++) {
					HaoYou = new JuLeBuXiangQingHaoYou();
					HaoYou.setUserid(list2.get(i).getUserid());
					HaoYou.setHead(list2.get(i).getHead());
					HaoYou.setNickname(list2.get(i).getNickname());
					HaoYou.setIs_leader(list2.get(i).getIs_leader());
					HaoYou.setFanscount(list2.get(i).getFanscount());
					HaoYou.setIs_attention(2);// 不需要显示是否关注
					HaoYouAll.add(HaoYou);
				}
				initcontrollist();
				break;
			case CommonUtility.SERVEROK3:
				HaoYouAll.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						list3.clear();
					}
					list3.addAll(listTemp3);
				} else {
					list3.clear();
					list3.addAll(listTemp3);
				}
				for (int i = 0; i < list3.size(); i++) {
					HaoYou = new JuLeBuXiangQingHaoYou();
					HaoYou.setUserid(list3.get(i).getUserid());
					HaoYou.setHead(list3.get(i).getHead());
					HaoYou.setNickname(list3.get(i).getNickname());
					HaoYou.setIs_leader(list3.get(i).getIs_leader());
					HaoYou.setFanscount(list3.get(i).getFanscount());
					HaoYou.setIs_attention(list3.get(i).getIs_attention());
					HaoYouAll.add(HaoYou);
				}
				initcontrollist();
				break;
			case CommonUtility.SERVEROK4:
				showToast("关注好友成功");
				index = 0;
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				switch (tab_type) {
				case 1:
					sendAsyn1();
					break;
				case 2:
					sendAsyn2();
					break;
				case 3:
					sendAsyn3();
					break;

				default:
					break;
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
	CommonAdapter<NoDataBean> listadapter3 = null;

	private void initcontrollist() {
		if (HaoYouAll.size() == 0) {
			if (listadapter3 == null) {
				List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
				NoDataBean nodata = new NoDataBean();
				nodata.setTitle(getResources().getString(R.string.friends));
				nodatas.add(nodata);
				listadapter3 = new CommonAdapter<NoDataBean>(HaoYouActivity.this, nodatas, R.layout.item_nodata) {
					@Override
					public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
						helper.setText(R.id.news_title, item.getTitle());
					}
				};
				mListView.setAdapter(listadapter3);
			}
			listadapter = null;
		} else {
			if (Loadmore && listadapter != null) {
				listadapter.notifyDataSetChanged();
			} else {
				listadapter3 = null;
				listadapter = new MyListAdpter(HaoYouActivity.this, HaoYouAll);
				mListView.setAdapter(listadapter);
			}
		}
	}

}
