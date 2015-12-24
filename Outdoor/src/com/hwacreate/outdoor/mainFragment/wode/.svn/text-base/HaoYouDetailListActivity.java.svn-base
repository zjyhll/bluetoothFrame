package com.hwacreate.outdoor.mainFragment.wode;

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
import com.hwacreate.outdoor.mainFragment.youji.YoujiXiangQingActivity;
import com.hwacreate.outdoor.mainFragment.zhuzhi.JuLeBuXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequest;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequestParameter;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponse;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetUserClubActTvlAction.GetUserClubActTvlActivitylistItem;
import com.keyhua.outdoor.protocol.GetUserClubActTvlAction.GetUserClubActTvlClublistItem;
import com.keyhua.outdoor.protocol.GetUserClubActTvlAction.GetUserClubActTvlRequest;
import com.keyhua.outdoor.protocol.GetUserClubActTvlAction.GetUserClubActTvlRequestParameter;
import com.keyhua.outdoor.protocol.GetUserClubActTvlAction.GetUserClubActTvlResponse;
import com.keyhua.outdoor.protocol.GetUserClubActTvlAction.GetUserClubActTvlResponsePayload;
import com.keyhua.outdoor.protocol.GetUserClubActTvlAction.GetUserClubActTvlTravellistItem;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubRequest;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubRequestParameter;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubResponse;
import com.keyhua.outdoor.protocol.JoinClubAction.JoinClubResponsePayload;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubRequest;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubRequestParameter;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubResponse;
import com.keyhua.outdoor.protocol.LeaderJoinClubAction.LeaderJoinClubResponsePayload;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
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
 * @author LaLa 详细列表信息
 * 
 */
public class HaoYouDetailListActivity extends BaseActivity implements OnItemClickListener {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_home = null;// listView
	private MyListAdpter1 listadapter1 = null;
	private MyListAdpter2 listadapter2 = null;
	private MyListAdpter3 listadapter3 = null;
	private PtrFrameLayout mPtrFrameLayout;
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;

	private String Userid = null;
	private int UserIndex = 0;

	// 取得本地下载的游记数据库信息
	private Integer isDownLoad = 0;
	private List<YoujiXiangqing> mYoujiDatas = null;
	private YoujiXiangqing youjiXiangqing = null;
	private BaseDao<YoujiXiangqing> youjiBaseDao = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_list);
		init();
	}

	@Override
	protected void onInitData() {
		Userid = getIntent().getStringExtra("Userid");
		UserIndex = getIntent().getIntExtra("UserIndex", 0);
		initHeaderOther();
		activitylist = new ArrayList<GetUserClubActTvlActivitylistItem>();
		activitylistTemp = new ArrayList<GetUserClubActTvlActivitylistItem>();
		travellist = new ArrayList<GetUserClubActTvlTravellistItem>();
		travellistTemp = new ArrayList<GetUserClubActTvlTravellistItem>();
		clublist = new ArrayList<GetUserClubActTvlClublistItem>();
		clublistTemp = new ArrayList<GetUserClubActTvlClublistItem>();
		lv_home = (ListView) findViewById(R.id.lv_home_detailed_list);
		// 初始化取得下载游记的数据库
		youjiXiangqing = new YoujiXiangqing();
		mYoujiDatas = new ArrayList<YoujiXiangqing>();
		youjiBaseDao = new BaseDao<YoujiXiangqing>(youjiXiangqing, HaoYouDetailListActivity.this);
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
	protected void onStart() {
		super.onStart();
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {
			mYoujiDatas = youjiBaseDao.queryAll();
			index = 0;
			switch (UserIndex) {
			case 1:
				if (listadapter1 != null && listadapter1.getCount() > count) {
					count = listadapter1.getCount();
				} else {
					count = 10;
				}
				break;
			case 2:
				if (listadapter2 != null && listadapter2.getCount() > count) {
					count = listadapter2.getCount();
				} else {
					count = 10;
				}
				break;
			case 3:
				if (listadapter3 != null && listadapter3.getCount() > count) {
					count = listadapter3.getCount();
				} else {
					count = 10;
				}
				break;

			default:
				break;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendGetUserClubActTvlActionAsyn();
		}
	}

	@Override
	protected void onResload() {
		switch (UserIndex) {
		case 1:
			top_tv_title.setText("俱乐部");
			break;
		case 2:
			top_tv_title.setText("活动");
			break;
		case 3:
			top_tv_title.setText("游记");
			break;

		default:
			break;
		}
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		lv_home.setOnItemClickListener(this);
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
				if (NetUtil.isNetworkAvailable(HaoYouDetailListActivity.this)) {// 有网
					isNet = true;
					switch (UserIndex) {
					case 1:
						if (listadapter1 != null) {
							index = listadapter1.getCount();
						} else {
							index = 0;
						}
						break;
					case 2:
						if (listadapter2 != null) {
							index = listadapter2.getCount();
						} else {
							index = 0;
						}
						break;
					case 3:
						if (listadapter3 != null) {
							index = listadapter3.getCount();
						} else {
							index = 0;
						}
						break;

					default:
						break;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					sendGetUserClubActTvlActionAsyn();
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
				if (NetUtil.isNetworkAvailable(HaoYouDetailListActivity.this)) {// 有网
					mYoujiDatas = youjiBaseDao.queryAll();
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					sendGetUserClubActTvlActionAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					mYoujiDatas.clear();
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		switch (UserIndex) {
		case 1:
			bundle.putString("clubid", clublist.get(position).getClub_id());
			openActivity(JuLeBuXiangQingActivity.class, bundle);
			break;
		case 2:
			if (activitylist.get(position).getAct_state() == 3 || activitylist.get(position).getAct_state() == 4) {
				if (TextUtils.equals(activitylist.get(position).getAct_id(), App.getInstance().getHuoDongId())) {
					bundle.putInt("fromrenwu", CommonUtility.XianShiTab_RenWu);
				} else if (TextUtils.equals(activitylist.get(position).getAct_id(),
						App.getInstance().getLeaderHuoDongId())) {
					bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
				} else {
					bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
				}
			} else {
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_False);
			}
			bundle.putString("act_id", activitylist.get(position).getAct_id());
			openActivity(HuoDongXiangQingActivity.class, bundle);
			break;
		case 3:
			bundle.putInt("agreeCount",
					travellist.get(position).getAgreeCount() != null ? travellist.get(position).getAgreeCount() : 0);
			bundle.putInt("isAgree",
					travellist.get(position).getIsAgree() != null ? travellist.get(position).getIsAgree() : 0);
			if (mYoujiDatas != null) {
				for (int i = 0; i < mYoujiDatas.size(); i++) {
					if (TextUtils.equals(mYoujiDatas.get(i).getTvl_id(), travellist.get(position).getTvl_id())) {
						isDownLoad = 1;
					}
				}
			}
			bundle.putInt("isDownLoad", isDownLoad);
			bundle.putString("tvl_id",
					travellist.get(position).getTvl_id() != null ? travellist.get(position).getTvl_id() : "");
			bundle.putString("act_id",
					travellist.get(position).getAct_id() != null ? travellist.get(position).getAct_id() : "");
			if (isNet || isDownLoad == 1) {
				openActivity(YoujiXiangQingActivity.class, bundle);
				isDownLoad = 0;
				isFrist = false;
			} else {
				showToastNet();
			}
			break;

		default:
			break;
		}
		isFrist = false;
	}

	private Thread thread = null;

	// 发送请求到服务器，异步处理模式，通过重载回调函数处理各种返回
	public void sendGetUserClubActTvlActionAsyn() {
		thread = new Thread() {
			public void run() {
				getUserClubActTvlAction();
			}
		};
		thread.start();
	}

	private List<GetUserClubActTvlActivitylistItem> activitylist = null;
	private List<GetUserClubActTvlActivitylistItem> activitylistTemp = null;
	private List<GetUserClubActTvlTravellistItem> travellist = null;
	private List<GetUserClubActTvlTravellistItem> travellistTemp = null;
	private List<GetUserClubActTvlClublistItem> clublist = null;
	private List<GetUserClubActTvlClublistItem> clublistTemp = null;

	public void getUserClubActTvlAction() {
		GetUserClubActTvlRequest request = new GetUserClubActTvlRequest();
		GetUserClubActTvlRequestParameter parameter = new GetUserClubActTvlRequestParameter();
		parameter.setUserid(Userid);
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
					GetUserClubActTvlResponse response = new GetUserClubActTvlResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetUserClubActTvlResponsePayload payload = (GetUserClubActTvlResponsePayload) response.getPayload();
					this.activitylistTemp = payload.getActivitylist();
					this.travellistTemp = payload.getTravellist();
					this.clublistTemp = payload.getClublist();
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

	private String tvl_id = null;
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
	private String club_id = "";

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

	CommonAdapter<NoDataBean> listadapter = null;
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				switch (UserIndex) {
				case 1:
					if (Loadmore) {
						if (LoadmoreData) {
							clublist.clear();
						}
					} else {
						clublist.clear();
					}
					clublist.addAll(clublistTemp);
					if (clublist.size() == 0) {
						if (listadapter == null) {
							List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
							NoDataBean nodata = new NoDataBean();
							nodata.setTitle(getResources().getString(R.string.organization));
							nodatas.add(nodata);
							listadapter = new CommonAdapter<NoDataBean>(HaoYouDetailListActivity.this, nodatas,
									R.layout.item_nodata) {
								@Override
								public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
									helper.setText(R.id.news_title, item.getTitle());
								}
							};
							lv_home.setAdapter(listadapter);
						}
						listadapter1 = null;
					} else {
						if (Loadmore && listadapter1 != null) {
							listadapter1.notifyDataSetChanged();
						} else {
							listadapter = null;
							listadapter1 = new MyListAdpter1(HaoYouDetailListActivity.this, clublist);
							lv_home.setAdapter(listadapter1);
						}
					}
					break;
				case 2:
					if (Loadmore) {
						if (LoadmoreData) {
							activitylist.clear();
						}
					} else {
						activitylist.clear();
					}
					activitylist.addAll(activitylistTemp);
					if (activitylist.size() == 0) {
						if (listadapter == null) {
							List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
							NoDataBean nodata = new NoDataBean();
							nodata.setTitle(getResources().getString(R.string.home_page));
							nodatas.add(nodata);
							listadapter = new CommonAdapter<NoDataBean>(HaoYouDetailListActivity.this, nodatas,
									R.layout.item_nodata) {
								@Override
								public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
									helper.setText(R.id.news_title, item.getTitle());
								}
							};
							lv_home.setAdapter(listadapter);
						}
						listadapter2 = null;
					} else {
						if (Loadmore && listadapter2 != null) {
							listadapter2.notifyDataSetChanged();
						} else {
							listadapter = null;
							listadapter2 = new MyListAdpter2(HaoYouDetailListActivity.this, activitylist);
							lv_home.setAdapter(listadapter2);
						}
					}
					break;
				case 3:
					if (Loadmore) {
						if (LoadmoreData) {
							travellist.clear();
						}
					} else {
						travellist.clear();
					}
					travellist.addAll(travellistTemp);
					if (travellist.size() == 0) {
						if (listadapter == null) {
							List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
							NoDataBean nodata = new NoDataBean();
							nodata.setTitle(getResources().getString(R.string.travel));
							nodatas.add(nodata);
							listadapter = new CommonAdapter<NoDataBean>(HaoYouDetailListActivity.this, nodatas,
									R.layout.item_nodata) {
								@Override
								public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
									helper.setText(R.id.news_title, item.getTitle());
								}
							};
							lv_home.setAdapter(listadapter);
						}
						listadapter3 = null;
					} else {
						if (Loadmore && listadapter3 != null) {
							listadapter3.notifyDataSetChanged();
						} else {
							listadapter = null;
							listadapter3 = new MyListAdpter3(HaoYouDetailListActivity.this, travellist);
							lv_home.setAdapter(listadapter3);
						}
					}
					break;

				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK2:
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
					sendGetUserClubActTvlActionAsyn();
					break;
				case 2:
					showToast("你已经点过赞了");
					break;

				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK3:
				index = 0;
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				sendGetUserClubActTvlActionAsyn();
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
				isFrist = false;
				break;
			default:
				break;
			}
		};
	};

	public class MyListAdpter1 extends BaseAdapter {
		private Context context = null;
		private List<GetUserClubActTvlClublistItem> mList = null;

		public MyListAdpter1(Context context, List<GetUserClubActTvlClublistItem> list) {
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
				convertView = LayoutInflater.from(context).inflate(R.layout.item_julebu, null);
				holder = new ViewHolder();
				holder.julebu_list = (FrameLayout) convertView.findViewById(R.id.julebu_list);
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
			holder.icon.loadImage(imageLoader, mList.get(position).getClub_logo());
			holder.julebu_list.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
			holder.tv_clob.setText(mList.get(position).getClub_name());
			holder.tv_Province.setText(mList.get(position).getClub_city());
			holder.tv_town.setText(mList.get(position).getClub_county());
			holder.tv_chengyuan.setText(mList.get(position).getMember() + "");
			holder.tv_huodong.setText(mList.get(position).getActivity() + "");
			// if (App.getInstance().getIs_leader() == 1) {
			// holder.tv_join.setVisibility(View.VISIBLE);
			// switch (mList.get(position).getLeaderApplyState()) {
			// // 2：未申请过1：审核通过0：待审核-1为拒绝
			// case -1:
			// holder.tv_join.setText("申请");
			// holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zuzhu_join,
			// 0, 0, 0);
			// holder.tv_join.setEnabled(true);
			// break;
			// case 0:
			// holder.tv_join.setText("审核中");
			// holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
			// 0);
			// holder.tv_join.setEnabled(false);
			// break;
			// case 1:
			// holder.tv_join.setText("已加入");
			// holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
			// 0);
			// holder.tv_join.setEnabled(false);
			// break;
			// case 2:
			// holder.tv_join.setText("申请");
			// holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zuzhu_join,
			// 0, 0, 0);
			// holder.tv_join.setEnabled(true);
			// break;
			// }
			// } else {
			// if (mList.get(position).getIsJoin() == 0) {
			// holder.tv_join.setText("关注");
			// holder.tv_join.setVisibility(View.VISIBLE);
			// holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(R.drawable.zuzhu_join,
			// 0, 0, 0);
			// holder.tv_join.setEnabled(true);
			// } else if (mList.get(position).getIsJoin() == 1) {
			// holder.tv_join.setText("已关注");
			// holder.tv_join.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0,
			// 0);
			// holder.tv_join.setEnabled(false);
			// }
			// }
			// holder.tv_join.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// if (!TextUtils.isEmpty(App.getInstance().getAut())) {
			// club_id = mList.get(position).getClub_id();
			// if (App.getInstance().getIs_leader() == 1) {
			// showdialogtext("申请中...");
			// sendLeaderJoinClubActionAsyn();
			// } else {
			// sendJoinClubActionAsyn();
			// }
			// } else {
			// showToastDengLu();
			// openActivity(LoginActivity.class);
			// }
			// }
			// });
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString("clubid", mList.get(position).getClub_id());
					openActivity(JuLeBuXiangQingActivity.class, bundle);
					isFrist = false;
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private FrameLayout julebu_list;
			private CubeImageView icon;
			private TextView tv_clob, tv_Province, tv_town, tv_chengyuan, tv_huodong, tv_join;
		}
	}

	public class MyListAdpter2 extends BaseAdapter {
		private Context context = null;
		public List<GetUserClubActTvlActivitylistItem> mDatas = null;

		public MyListAdpter2(Context context, List<GetUserClubActTvlActivitylistItem> list) {
			this.context = context;
			this.mDatas = list;
		}

		@Override
		public int getCount() {
			return mDatas != null ? mDatas.size() : 0;
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
				JSONArray jsonArray = new JSONArray(mDatas.get(position).getAct_logo());
				holder.icon.loadImage(imageLoaderRectF, jsonArray.getString(0));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			holder.tv_title.setText(mDatas.get(position).getAct_title());
			holder.tv_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mDatas.get(position).getAct_start_time()));
			if (!TextUtils.isEmpty(mDatas.get(position).getClub())) {
				holder.tv_des.setText(mDatas.get(position).getClub());
				mImageLoader.displayImage(mDatas.get(position).getClub_head(), holder.iv_icon, options);
			} else if (!TextUtils.isEmpty(mDatas.get(position).getLeader())) {
				holder.tv_des.setText(mDatas.get(position).getLeader());
				mImageLoader.displayImage(mDatas.get(position).getLeader_head(), holder.iv_icon, options);
			}
			holder.tv_content.setText(mDatas.get(position).getAct_desc_intro());
			holder.tv_action.setText(mDatas.get(position).getAct_type());
			if (TextUtils.equals(mDatas.get(position).getAct_type(), "登山")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.dengshan));
			} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "徒步")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.tubu));
			} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "骑行")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.qixing));
			} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "自驾")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.zijia));
			} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "摄影")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.sheying));
			} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "休闲")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.xiuxian));
			} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "露营")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.luying));
			} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "亲子")) {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.qinzi));
			} else {
				holder.tv_action.setTextColor(context.getResources().getColor(R.color.content));
			}
			switch (mDatas.get(position).getAct_state()) {
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
			private TextView tv_title, tv_content, tv_des, tv_time, tv_action, tv_status;
		}
	}

	public class MyListAdpter3 extends BaseAdapter {
		private Context context = null;
		private List<GetUserClubActTvlTravellistItem> mList = null;

		public MyListAdpter3(Context context, List<GetUserClubActTvlTravellistItem> list) {
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
			holder.im_youji_beijing.loadImage(imageLoader, mList.get(position).getTvl_cover());
			holder.youji_list.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
			holder.tv_youji_huodong.setText(mList.get(position).getUser_nickname());
			holder.tv_youji_geyan.setText(mList.get(position).getTvl_title());
			holder.tv_youji_time
					.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getAct_start_time()));
			holder.tv_youji_dianzan.setText(mList.get(position).getAgreeCount() + "");
			holder.tv_youji_julebu.setText(mList.get(position).getClub_name());
			holder.tv_youji_dianzan.setVisibility(View.GONE);
			// if (mList.get(position).getIsAgree() == 0) {
			// holder.tv_youji_dianzan.setTextColor(getResources().getColor(R.color.white));
			// holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_n,
			// 0, 0, 0);
			// } else if (mList.get(position).getIsAgree() == 1) {
			// holder.tv_youji_dianzan.setTextColor(getResources().getColor(R.color.app_green));
			// holder.tv_youji_dianzan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dian_zan_c,
			// 0, 0, 0);
			// }
			// holder.tv_youji_dianzan.setOnClickListener(new OnClickListener()
			// {
			//
			// @Override
			// public void onClick(View v) {
			// tvl_id = mList.get(position).getTvl_id();
			// if (mList.get(position).getIsAgree() == 0) {
			// if (!TextUtils.isEmpty(App.getInstance().getAut())) {
			// sendAgreeTravelActionAsyn();
			// } else {
			// showToastDengLu();
			// }
			// }
			// }
			// });
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
}
