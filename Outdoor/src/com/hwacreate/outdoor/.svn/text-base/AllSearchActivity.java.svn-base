package com.hwacreate.outdoor;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.mainFragment.youji.YoujiXiangQingActivity;
import com.hwacreate.outdoor.mainFragment.zhuzhi.JuLeBuXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.ActivitySearchListAction.ActivitySearchListActivitylistItem;
import com.keyhua.outdoor.protocol.ActivitySearchListAction.ActivitySearchListRequest;
import com.keyhua.outdoor.protocol.ActivitySearchListAction.ActivitySearchListRequestParameter;
import com.keyhua.outdoor.protocol.ActivitySearchListAction.ActivitySearchListResponse;
import com.keyhua.outdoor.protocol.ActivitySearchListAction.ActivitySearchListResponsePayload;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequest;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequestParameter;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponse;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponsePayload;
import com.keyhua.outdoor.protocol.ClubSearchListAction.ClubSearchListClublistItem;
import com.keyhua.outdoor.protocol.ClubSearchListAction.ClubSearchListRequest;
import com.keyhua.outdoor.protocol.ClubSearchListAction.ClubSearchListRequestParameter;
import com.keyhua.outdoor.protocol.ClubSearchListAction.ClubSearchListResponse;
import com.keyhua.outdoor.protocol.ClubSearchListAction.ClubSearchListResponsePayload;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubRequest;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubRequestParameter;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubResponse;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubResponsePayload;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubRequest;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubRequestParameter;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubResponse;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubResponsePayload;
import com.keyhua.outdoor.protocol.TvlSearchListAction.TvlSearchListRequest;
import com.keyhua.outdoor.protocol.TvlSearchListAction.TvlSearchListRequestParameter;
import com.keyhua.outdoor.protocol.TvlSearchListAction.TvlSearchListResponse;
import com.keyhua.outdoor.protocol.TvlSearchListAction.TvlSearchListResponsePayload;
import com.keyhua.outdoor.protocol.TvlSearchListAction.TvlSearchListTravellistItem;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class AllSearchActivity extends BaseActivity {
	private GridView search_gridview = null;
	private CleareditTextView search_edit = null;
	private TextView search_noresult = null;
	private TextView search = null;
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;// listView
	private PtrFrameLayout mPtrFrameLayout;
	// 类型
	private List<String> hotList = null;
	private MyList5Adpter listadapter = null;
	private MyList6Adpter listadapter1 = null;
	private MyList7Adpter listadapter2 = null;
	private String kwd = null;// 关键字，首要条件，如果为不空就按条件（目的地/活动名称/俱乐部名称）模糊查找，为空就按下列条件模糊查找
	private String act_type = "";// 活动类型
	private int index = 0;// 从哪个开始取
	private int count = 10;// 取多少个
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFirst = true;
	private int Searchid = 0;
	// 俱乐部
	private String club_id = null;
	// 点赞操作
	private String tvl_id = null;
	private int state = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allsearch);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.search:
			kwd = search_edit.getText().toString();
			act_type = "";
			onrefreshAndLoadmore();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFirst && !TextUtils.isEmpty(App.getInstance().getAut())) {
			index = 0;
			Loadmore = true;
			LoadmoreData = true;
			switch (Searchid) {
			case 1:
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				sendAsyn();
				break;
			case 2:
				if (listadapter1 != null) {
					count = listadapter1.getCount();
				} else {
					count = 10;
				}
				sendAsyn1();
				break;
			case 3:
				if (listadapter2 != null) {
					count = listadapter2.getCount();
				} else {
					count = 10;
				}
				mYoujiDatas = youjiBaseDao.queryAll();
				sendAsyn2();
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		// 数据库操作,以及从数据库中取出不同类型的数据
		// 初始化从服务器取得的数据的容器
		mYoujiDatas = new ArrayList<YoujiXiangqing>();
		youjiXiangqing = new YoujiXiangqing();
		youjiBaseDao = new BaseDao<YoujiXiangqing>(youjiXiangqing, AllSearchActivity.this);
		hotList = new ArrayList<String>();
		Searchid = getIntent().getIntExtra("Searchid", 0);
		search = (TextView) findViewById(R.id.search);
		search_noresult = (TextView) findViewById(R.id.search_noresult);
		search_gridview = (GridView) findViewById(R.id.search_gridview);
		search_edit = (CleareditTextView) findViewById(R.id.search_edit);
		list = new ArrayList<ActivitySearchListActivitylistItem>();
		listTemp = new ArrayList<ActivitySearchListActivitylistItem>();
		listclub = new ArrayList<ClubSearchListClublistItem>();
		listclubTemp = new ArrayList<ClubSearchListClublistItem>();
		listyouji = new ArrayList<TvlSearchListTravellistItem>();
		listyoujiTemp = new ArrayList<TvlSearchListTravellistItem>();
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
		switch (Searchid) {
		case 1:
			search_edit.setHint("活动名称/城市/俱乐部");
			search_gridview.setVisibility(View.VISIBLE);
			listadapter = new MyList5Adpter(AllSearchActivity.this, listTemp, imageLoader);
			lv_home.setAdapter(listadapter);
			break;
		case 2:
			search_edit.setHint("俱乐部名称/城市");
			search_gridview.setVisibility(View.GONE);
			listadapter1 = new MyList6Adpter(AllSearchActivity.this, listclubTemp, imageLoader);
			lv_home.setAdapter(listadapter1);
			break;
		case 3:
			search_edit.setHint("游记名称/城市");
			search_gridview.setVisibility(View.GONE);
			listadapter2 = new MyList7Adpter(AllSearchActivity.this, listyoujiTemp, imageLoader);
			lv_home.setAdapter(listadapter2);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("快速搜索");
		hotList.add("登山");
		hotList.add("徒步");
		hotList.add("骑行");
		hotList.add("自驾");
		hotList.add("摄影");
		hotList.add("休闲");
		hotList.add("露营");
		hotList.add("亲子");
		search_gridview.setAdapter(new HotCityAdapter(AllSearchActivity.this, hotList));
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		search.setOnClickListener(this);
		search_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				kwd = "";
				act_type = hotList.get(position);
				count = 10;
				sendAsyn();
				onrefreshAndLoadmore();
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
				if (NetUtil.isNetworkAvailable(AllSearchActivity.this)) {// 有网
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					switch (Searchid) {
					case 1:
						if (listadapter != null) {
							index = listadapter.getCount();
						} else {
							index = 0;
						}
						sendAsyn();
						break;
					case 2:
						if (listadapter1 != null) {
							index = listadapter1.getCount();
						} else {
							index = 0;
						}
						sendAsyn1();
						break;
					case 3:
						if (listadapter2 != null) {
							index = listadapter2.getCount();
						} else {
							index = 0;
						}
						sendAsyn2();
						break;

					default:
						break;
					}
					mHandler.sendEmptyMessage(CommonUtility.ISLOADMORE);
				} else {// 无网
					mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
				}
			}
		});
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
				if (NetUtil.isNetworkAvailable(AllSearchActivity.this)) {// 有网
					index = 0;
					count = 10;
					Loadmore = false;
					switch (Searchid) {
					case 1:
						sendAsyn();
						break;
					case 2:
						sendAsyn1();
						break;
					case 3:
						sendAsyn2();
						break;

					default:
						break;
					}
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
				}
			}
		});
		onrefreshAndLoadmore();
	}

	private void onrefreshAndLoadmore() {
		// load至少刷新多少1秒
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
	private class HotCityAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<String> hotCitys;

		public HotCityAdapter(Context context, List<String> hotCitys) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.hotCitys = hotCitys;
		}

		@Override
		public int getCount() {
			return hotCitys != null ? hotCitys.size() : 0;
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
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.contactlist_item_city, null);
			TextView city = (TextView) convertView.findViewById(R.id.city);
			city.setText(hotCitys.get(position));
			return convertView;
		}
	}

	private Thread thread = null;

	// 搜索活动
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				getAction();
			}
		};
		thread.start();
	}

	private List<ActivitySearchListActivitylistItem> list = null;
	private List<ActivitySearchListActivitylistItem> listTemp = null;

	public void getAction() {
		ActivitySearchListRequest request = new ActivitySearchListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		ActivitySearchListRequestParameter parameter = new ActivitySearchListRequestParameter();
		parameter.setCity(App.getInstance().getContactCity());
		parameter.setAct_type(act_type);
		parameter.setKwd(kwd);
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
					ActivitySearchListResponse response = new ActivitySearchListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					ActivitySearchListResponsePayload payload = (ActivitySearchListResponsePayload) response
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	// 搜索俱乐部
	public void sendAsyn1() {
		thread = new Thread() {
			public void run() {
				getAction1();
			}
		};
		thread.start();
	}

	private List<ClubSearchListClublistItem> listclub = null;
	private List<ClubSearchListClublistItem> listclubTemp = null;

	public void getAction1() {
		ClubSearchListRequest request = new ClubSearchListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		ClubSearchListRequestParameter parameter = new ClubSearchListRequestParameter();
		parameter.setCity(App.getInstance().getContactCity());
		parameter.setKwd(kwd);
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
					ClubSearchListResponse response = new ClubSearchListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					ClubSearchListResponsePayload payload = (ClubSearchListResponsePayload) response.getPayload();
					this.listclubTemp = payload.getClublistList();
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

	// 搜索游记
	public void sendAsyn2() {
		thread = new Thread() {
			public void run() {
				getAction2();
			}
		};
		thread.start();
	}

	private List<TvlSearchListTravellistItem> listyouji = null;
	private List<TvlSearchListTravellistItem> listyoujiTemp = null;

	public void getAction2() {
		TvlSearchListRequest request = new TvlSearchListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		TvlSearchListRequestParameter parameter = new TvlSearchListRequestParameter();
		parameter.setCity("");
		parameter.setKwd(kwd);
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
					TvlSearchListResponse response = new TvlSearchListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					TvlSearchListResponsePayload payload = (TvlSearchListResponsePayload) response.getPayload();
					this.listyoujiTemp = payload.getTravellistList();
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

	// 领队申请俱乐部
	public void sendLeaderJoinClubActionAsyn() {
		thread = new Thread() {
			public void run() {
				LeaderJoinClubAction();
			}
		};
		thread.start();
	}

	private String leadermsgerror = "";

	public void LeaderJoinClubAction() {
		LeaderJoinClubRequest request = new LeaderJoinClubRequest();
		LeaderJoinClubRequestParameter parameter = new LeaderJoinClubRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
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
				leadermsgerror = responseObject.getString("msg");
				if (ret == 0) {
					LeaderJoinClubResponse response = new LeaderJoinClubResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					LeaderJoinClubResponsePayload payload = (LeaderJoinClubResponsePayload) response.getPayload();
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

	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (Loadmore) {
					if (LoadmoreData) {
						list.clear();
					}
					list.addAll(listTemp);
				} else {
					list.clear();
					list.addAll(listTemp);
				}
				listadapter.addAll(list);
				listadapter.notifyDataSetChanged();
				if (isFirst) {
					isFirst = false;
					search_noresult.setVisibility(View.GONE);
				} else {
					if (list.size() == 0) {
						mPtrFrameLayout.setVisibility(View.GONE);
						search_noresult.setText("抱歉,暂时没有找到相关活动");
						search_noresult.setVisibility(View.VISIBLE);
						search_gridview.setVisibility(View.VISIBLE);
					} else {
						mPtrFrameLayout.setVisibility(View.VISIBLE);
						search_noresult.setVisibility(View.GONE);
						search_gridview.setVisibility(View.GONE);
					}
				}
				break;
			case CommonUtility.SERVEROK2:
				if (Loadmore) {
					if (LoadmoreData) {
						listclub.clear();
					}
					listclub.addAll(listclubTemp);
				} else {
					listclub.clear();
					listclub.addAll(listclubTemp);
				}
				listadapter1.addAll(listclub);
				listadapter1.notifyDataSetChanged();
				if (isFirst) {
					isFirst = false;
					search_noresult.setVisibility(View.GONE);
				} else {
					if (listclub.size() == 0) {
						mPtrFrameLayout.setVisibility(View.GONE);
						search_noresult.setText("抱歉,暂时没有找到相关俱乐部");
						search_noresult.setVisibility(View.VISIBLE);
						search_gridview.setVisibility(View.VISIBLE);
					} else {
						mPtrFrameLayout.setVisibility(View.VISIBLE);
						search_noresult.setVisibility(View.GONE);
						search_gridview.setVisibility(View.GONE);
					}
				}
				break;
			case CommonUtility.SERVEROK3:
				if (Loadmore) {
					if (LoadmoreData) {
						listyouji.clear();
					}
					listyouji.addAll(listyoujiTemp);
				} else {
					listyouji.clear();
					listyouji.addAll(listyoujiTemp);
				}
				listadapter2.addAll(listyouji);
				listadapter2.notifyDataSetChanged();
				if (isFirst) {
					isFirst = false;
					search_noresult.setVisibility(View.GONE);
				} else {
					if (listyouji.size() == 0) {
						mPtrFrameLayout.setVisibility(View.GONE);
						search_noresult.setText("抱歉,暂时没有找到相关游记");
						search_noresult.setVisibility(View.VISIBLE);
						search_gridview.setVisibility(View.VISIBLE);
					} else {
						mPtrFrameLayout.setVisibility(View.VISIBLE);
						search_noresult.setVisibility(View.GONE);
						search_gridview.setVisibility(View.GONE);
					}
				}
				break;
			case CommonUtility.SERVEROK4:
				index = 0;
				if (listadapter1 != null) {
					count = listadapter1.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				sendAsyn1();
				break;
			case CommonUtility.SERVEROK5:
				switch (state) {
				case 0:
					showToast("点赞失败,请重新点赞");
					break;
				case 1:
					index = 0;
					if (listadapter2 != null) {
						count = listadapter2.getCount();
					} else {
						count = 10;
					}
					Loadmore = true;
					LoadmoreData = true;
					sendAsyn2();
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
				if (!TextUtils.isEmpty(leadermsgerror)) {
					showToast(leadermsgerror);
				}
				leadermsgerror = null;
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
				break;
			default:
				break;
			}
		};
	};

	public class MyList5Adpter extends BaseAdapter {
		private Context context = null;
		public List<ActivitySearchListActivitylistItem> mLists = null;
		private ImageLoader imageLoader = null;

		public MyList5Adpter(Context context, List<ActivitySearchListActivitylistItem> list, ImageLoader imageLoader) {
			this.context = context;
			this.mLists = list;
			this.imageLoader = imageLoader;
		}

		public void addAll(List<ActivitySearchListActivitylistItem> mList) {
			this.mLists.clear();
			this.mLists.addAll(mList);
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
				JSONArray jsonArray = new JSONArray(mLists.get(position).getAct_logo());
				holder.icon.loadImage(imageLoaderRectF, jsonArray.getString(0));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			holder.tv_title.setText(mLists.get(position).getAct_title());
			holder.tv_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mLists.get(position).getAct_start_time()));
			holder.tv_content.setText(mLists.get(position).getAct_desc_intro());
			if (!TextUtils.isEmpty(mLists.get(position).getClub())) {
				holder.tv_des.setText(mLists.get(position).getClub());
				mImageLoader.displayImage(mLists.get(position).getClub_head(), holder.iv_icon, options);
			} else if (!TextUtils.isEmpty(mLists.get(position).getLeader())) {
				holder.tv_des.setText(mLists.get(position).getLeader());
				mImageLoader.displayImage(mLists.get(position).getLeader_head(), holder.iv_icon, options);
			}
			holder.tv_action.setText(mLists.get(position).getAct_type());
			if (TextUtils.equals(mLists.get(position).getAct_type(), "登山")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.dengshan));
			} else if (TextUtils.equals(mLists.get(position).getAct_type(), "徒步")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.tubu));
			} else if (TextUtils.equals(mLists.get(position).getAct_type(), "骑行")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.qixing));
			} else if (TextUtils.equals(mLists.get(position).getAct_type(), "自驾")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.zijia));
			} else if (TextUtils.equals(mLists.get(position).getAct_type(), "摄影")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.sheying));
			} else if (TextUtils.equals(mLists.get(position).getAct_type(), "休闲")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.xiuxian));
			} else if (TextUtils.equals(mLists.get(position).getAct_type(), "露营")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.luying));
			} else if (TextUtils.equals(mLists.get(position).getAct_type(), "亲子")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.qinzi));
			} else {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.content));
			}
			switch (mLists.get(position).getAct_state()) {
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
					if (mLists.get(position).getAct_state() == 3 || mLists.get(position).getAct_state() == 4) {
						if (TextUtils.equals(mLists.get(position).getAct_id(), App.getInstance().getHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
						} else if (TextUtils.equals(mLists.get(position).getAct_id(),
								App.getInstance().getLeaderHuoDongId())) {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
						} else {
							bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
						}
					} else {
						bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
					}
					bundle.putString("act_id", mLists.get(position).getAct_id());
					openActivity(HuoDongXiangQingActivity.class, bundle);
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

	public class MyList6Adpter extends BaseAdapter {
		private Context context = null;
		public List<ClubSearchListClublistItem> mLists = null;
		private ImageLoader imageLoader = null;

		public MyList6Adpter(Context context, List<ClubSearchListClublistItem> list, ImageLoader imageLoader) {
			this.context = context;
			this.mLists = list;
			this.imageLoader = imageLoader;
		}

		public void addAll(List<ClubSearchListClublistItem> mList) {
			this.mLists.clear();
			this.mLists.addAll(mList);
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
				convertView = LayoutInflater.from(context).inflate(R.layout.item_zhuzhilist, null);
				holder = new ViewHolder();
				holder.zuzhi_list = (FrameLayout) convertView.findViewById(R.id.zuzhi_list);
				holder.icon = (CubeImageView) convertView.findViewById(R.id.icon);
				holder.tv_clob = (TextView) convertView.findViewById(R.id.tv_clob);
				holder.tv_Province = (TextView) convertView.findViewById(R.id.tv_Province);
				holder.tv_town = (TextView) convertView.findViewById(R.id.tv_town);
				holder.tv_chengyuan = (TextView) convertView.findViewById(R.id.tv_chengyuan);
				holder.tv_huodong = (TextView) convertView.findViewById(R.id.tv_huodong);
				holder.tv_join = (TextView) convertView.findViewById(R.id.tv_join);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			try {
				JSONArray jsonArray = new JSONArray(mLists.get(position).getAlbum());
				holder.icon.loadImage(imageLoader, jsonArray.getString(0));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			holder.zuzhi_list.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
			holder.tv_clob.setText(mLists.get(position).getClub_name());
			holder.tv_Province.setText(mLists.get(position).getClub_city());
			holder.tv_town.setText(mLists.get(position).getClub_county());
			holder.tv_chengyuan.setText(mLists.get(position).getMember() + "");
			holder.tv_huodong.setText(mLists.get(position).getActivity() + "");
			if (App.getInstance().getIs_leader() == 1) {
				holder.tv_join.setVisibility(View.VISIBLE);
				switch (mLists.get(position).getLeaderApplyState()) {// 2：未申请过1：审核通过0：待审核-1为拒绝
				case -1:
					holder.tv_join.setText("申请");
					holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zuzhu_join, 0, 0, 0);
					holder.tv_join.setEnabled(true);
					break;
				case 0:
					holder.tv_join.setText("审核中");
					holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					holder.tv_join.setEnabled(false);
					break;
				case 1:
					holder.tv_join.setText("已加入");
					holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					holder.tv_join.setEnabled(false);
					break;
				case 2:
					holder.tv_join.setText("申请");
					holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zuzhu_join, 0, 0, 0);
					holder.tv_join.setEnabled(true);
					break;
				}
			} else {
				if (mLists.get(position).getIsJoin() == 0) {
					holder.tv_join.setText("关注");
					holder.tv_join.setVisibility(View.VISIBLE);
					holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zuzhu_join, 0, 0, 0);
					holder.tv_join.setEnabled(true);
				} else if (mLists.get(position).getIsJoin() == 1) {
					holder.tv_join.setText("已关注");
					holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					holder.tv_join.setEnabled(false);
				}
			}
			holder.tv_join.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!TextUtils.isEmpty(App.getInstance().getAut())) {
						club_id = mLists.get(position).getClub_id();
						if (App.getInstance().getIs_leader() == 1) {
							showdialogtext("申请中...");
							sendLeaderJoinClubActionAsyn();
						} else {
							sendJoinClubActionAsyn();
						}
					} else {
						showToastDengLu();
						openActivity(LoginActivity.class);
					}
				}
			});
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString("clubid", mLists.get(position).getClub_id());
					openActivity(JuLeBuXiangQingActivity.class, bundle);
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private FrameLayout zuzhi_list;
			private CubeImageView icon;
			private TextView tv_clob, tv_Province, tv_town, tv_chengyuan, tv_huodong, tv_join;
		}
	}

	// 数据库操作
	private Integer isDownLoad = 0;
	private List<YoujiXiangqing> mYoujiDatas = null;
	private YoujiXiangqing youjiXiangqing = null;
	private BaseDao<YoujiXiangqing> youjiBaseDao = null;

	public class MyList7Adpter extends BaseAdapter {
		private Context context = null;
		public List<TvlSearchListTravellistItem> mLists = null;
		private ImageLoader imageLoader = null;

		public MyList7Adpter(Context context, List<TvlSearchListTravellistItem> list, ImageLoader imageLoader) {
			this.context = context;
			this.mLists = list;
			this.imageLoader = imageLoader;
		}

		public void addAll(List<TvlSearchListTravellistItem> mList) {
			this.mLists.clear();
			this.mLists.addAll(mList);
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
			holder.im_youji_beijing.loadImage(imageLoader, mLists.get(position).getTvl_cover());
			holder.youji_list.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
			holder.tv_youji_huodong.setText(mLists.get(position).getUser_nickname());
			holder.tv_youji_geyan.setText(mLists.get(position).getTvl_title());
			holder.tv_youji_time
					.setText(TimeDateUtils.formatDateFromDatabaseTime(mLists.get(position).getAct_start_time()));
			holder.tv_youji_julebu.setText(mLists.get(position).getClub_name());
			holder.tv_youji_dianzan.setText(mLists.get(position).getAgreeCount() + "");
			if (mLists.get(position).getIsAgree() == 0) {
				holder.tv_youji_dianzan.setTextColor(getResources().getColor(R.color.white));
				holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_n, 0, 0, 0);
			} else if (mLists.get(position).getIsAgree() == 1) {
				holder.tv_youji_dianzan.setTextColor(getResources().getColor(R.color.app_green));
				holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_c, 0, 0, 0);
			}
			holder.tv_youji_dianzan.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					tvl_id = mLists.get(position).getTvl_id();
					if (mLists.get(position).getIsAgree() == 0) {
						if (!TextUtils.isEmpty(App.getInstance().getAut())) {
							sendAgreeTravelActionAsyn();
						} else {
							showToastDengLu();
						}
					}
				}
			});
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putInt("agreeCount",
							mLists.get(position).getAgreeCount() != null ? mLists.get(position).getAgreeCount() : 0);
					bundle.putInt("isAgree",
							mLists.get(position).getIsAgree() != null ? mLists.get(position).getIsAgree() : 0);
					if (mYoujiDatas != null) {
						for (int i = 0; i < mYoujiDatas.size(); i++) {
							if (TextUtils.equals(mYoujiDatas.get(i).getTvl_id(), mLists.get(position).getTvl_id())) {
								isDownLoad = 1;
							}
						}
					}
					bundle.putInt("isDownLoad", isDownLoad);
					bundle.putString("tvl_id",
							mLists.get(position).getTvl_id() != null ? mLists.get(position).getTvl_id() : "");
					bundle.putString("act_id",
							mLists.get(position).getAct_id() != null ? mLists.get(position).getAct_id() : "");
					openActivity(YoujiXiangQingActivity.class, bundle);
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private FrameLayout youji_list;
			private CubeImageView im_youji_beijing;
			private TextView tv_youji_huodong, tv_youji_geyan, tv_youji_time, tv_youji_dianzan, tv_youji_julebu;
		}
	}

	// 关注俱乐部
	public void sendJoinClubActionAsyn() {
		thread = new Thread() {
			public void run() {
				JoinClubAction();
			}
		};
		thread.start();
	}

	public void JoinClubAction() {
		JoinClubRequest request = new JoinClubRequest();
		JoinClubRequestParameter parameter = new JoinClubRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setClub_id(club_id);
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
					JoinClubResponse response = new JoinClubResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					JoinClubResponsePayload payload = (JoinClubResponsePayload) response.getPayload();
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
}
