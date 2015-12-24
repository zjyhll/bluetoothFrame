package com.hwacreate.outdoor.mainFragment;

import java.util.ArrayList;
import java.util.List;

import com.hwacreate.outdoor.AllSearchActivity;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.contactlist.ContactListActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.zhuzhi.JuLeBuXiangQingActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.GetCityClubListAction.GetCityClubListClublistItem;
import com.keyhua.outdoor.protocol.GetCityClubListAction.GetCityClubListRequest;
import com.keyhua.outdoor.protocol.GetCityClubListAction.GetCityClubListRequestParameter;
import com.keyhua.outdoor.protocol.GetCityClubListAction.GetCityClubListResponse;
import com.keyhua.outdoor.protocol.GetCityClubListAction.GetCityClubListResponsePayload;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListClublistItem;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListRequest;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListRequestParameter;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListResponse;
import com.keyhua.outdoor.protocol.GetMyClubListAction.GetMyClubListResponsePayload;
import com.keyhua.outdoor.protocol.GetRecommendClubListAction.GetRecommendClubListClublistItem;
import com.keyhua.outdoor.protocol.GetRecommendClubListAction.GetRecommendClubListRequest;
import com.keyhua.outdoor.protocol.GetRecommendClubListAction.GetRecommendClubListRequestParameter;
import com.keyhua.outdoor.protocol.GetRecommendClubListAction.GetRecommendClubListResponse;
import com.keyhua.outdoor.protocol.GetRecommendClubListAction.GetRecommendClubListResponsePayload;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author 曾金叶 组织
 * @2015-8-5 @上午10:15:40
 */
public class ZhuzhiFragment extends BaseFragment {
	// 控件
	private CleareditTextView et1 = null;// 搜索
	private TextView tab1 = null;// 我的
	private TextView tab2 = null;// 同城
	private TextView tab3 = null;// 推荐
	private TextView join_My = null;// 关注_我的
	// 获取服务器信息需要传入的参数
	public int index = 0;
	public int count = 10;
	public String club_id = null;
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private ListView lv_zhuzhi = null;// listView
	private PtrFrameLayout mPtrFrameLayout;
	// 头部
	private View headerLayout = null;
	private LinearLayout headerParent = null;
	/**
	 * 数据库传入的类型，一共有三个，分别是我的，同城，推荐
	 */
	private int type = 1;
	private CommonAdapter<GetRecommendClubListClublistItem> listadapter = null;
	private CommonAdapter<GetCityClubListClublistItem> listadapter1 = null;
	private CommonAdapter<GetMyClubListClublistItem> listadapter2 = null;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mainfrag_zuzhi, null);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_zhuzhi_tab1:// 推荐
			showdialog();
			tab1.setTextColor(getResources().getColor(R.color.app_green));
			tab2.setTextColor(getResources().getColor(R.color.content));
			tab3.setTextColor(getResources().getColor(R.color.content));
			type = 1;
			index = 0;
			count = 10;
			Loadmore = false;
			listadapter3 = null;
			sendGetRecommendClubListActionAsyn();
			break;
		case R.id.tv_zhuzhi_tab2:// 同城
			showdialog();
			tab1.setTextColor(getResources().getColor(R.color.content));
			tab2.setTextColor(getResources().getColor(R.color.app_green));
			tab3.setTextColor(getResources().getColor(R.color.content));
			type = 2;
			index = 0;
			count = 10;
			Loadmore = false;
			listadapter3 = null;
			sendGetCityClubListActionAsyn();
			break;
		case R.id.tv_zhuzhi_tab3:// 我的
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				showdialog();
				tab1.setTextColor(getResources().getColor(R.color.content));
				tab2.setTextColor(getResources().getColor(R.color.content));
				tab3.setTextColor(getResources().getColor(R.color.app_green));
				type = 3;
				index = 0;
				count = 10;
				Loadmore = false;
				listadapter3 = null;
				sendGetMyClubListActionAsyn();
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
			}
			break;
		case R.id.top_tv_right:
			openActivity(ContactListActivity.class);
			isFrist = false;
			break;
		case R.id.et_input_key:
			if (isNet) {
				Bundle bundle = new Bundle();
				bundle.putInt("Searchid", 2);
				openActivity(AllSearchActivity.class, bundle);
				isFrist = false;
			} else {
				showToastNet();
			}
			break;
		}

	}

	@Override
	public void onStart() {
		super.onStart();
		if (!TextUtils.isEmpty(App.getInstance().getContactCity())) {
			top_tv_right.setText(App.getInstance().getContactCity());
		}
		if (!isFrist && isNet && !TextUtils.isEmpty(App.getInstance().getAut())) {
			showdialog();
			index = 0;
			Loadmore = true;
			LoadmoreData = true;
			switch (type) {
			case 1:
				if (listadapter != null && listadapter.getCount() > count) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				sendGetRecommendClubListActionAsyn();
				break;
			case 2:
				if (listadapter1 != null && listadapter1.getCount() > count) {
					count = listadapter1.getCount();
				} else {
					count = 10;
				}
				sendGetCityClubListActionAsyn();
				break;
			case 3:
				if (listadapter2 != null && listadapter2.getCount() > count) {
					count = listadapter2.getCount();
				} else {
					count = 10;
				}
				sendGetMyClubListActionAsyn();
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onInitData() {
		initControl();
		showdialog();
		refreshAndLoadMore();
	}

	private void refreshAndLoadMore() {
		// 上下刷新START--------------------------------------------------------------------
		// 获取装载VIew的容器
		mPtrFrameLayout = (PtrFrameLayout) getActivity().findViewById(R.id.load_more_list_view_ptr_frame);
		// 获取view的引用
		loadMoreListViewContainer = (LoadMoreListViewContainer) getActivity()
				.findViewById(R.id.load_more_list_view_container);
		// 使用默认样式
		loadMoreListViewContainer.useDefaultHeader();
		// 加载更多数据，当列表滑动到最底部的时候，触发加载更多操作，
		// 这是需要从网络加载数据，或者是从数据库去读取数据
		// 给View 设置加载更多的Handler 去异步加载View需要显示的数据和VIew
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			// loadMoreListViewContainer调用onLoadMore传入loadMoreListViewContainer自身对象
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (NetUtil.isNetworkAvailable(getActivity())) {// 有网count = 10;
					isNet = true;
					Loadmore = true;
					LoadmoreData = false;
					switch (type) {
					case 1:
						if (listadapter != null) {
							index = listadapter.getCount();
						} else {
							index = 0;
						}
						sendGetRecommendClubListActionAsyn();
						break;
					case 2:
						if (listadapter1 != null) {
							index = listadapter1.getCount();
						} else {
							index = 0;
						}
						sendGetCityClubListActionAsyn();
						break;
					case 3:
						if (listadapter2 != null) {
							index = listadapter2.getCount();
						} else {
							index = 0;
						}
						sendGetMyClubListActionAsyn();
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
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_zhuzhi, header);
			}

			// 开始刷新容器开头
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					switch (type) {
					case 1:
						sendGetRecommendClubListActionAsyn();
						break;
					case 2:
						sendGetCityClubListActionAsyn();
						break;
					case 3:
						sendGetMyClubListActionAsyn();
						break;
					default:
						break;
					}
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					if (isshowdialog()) {
						closedialog();
					}
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
				listadapter = new CommonAdapter<GetRecommendClubListClublistItem>(getActivity(), null,
						R.layout.item_zhuzhilist) {

					@Override
					public void convert(ViewHolderUntil helper, GetRecommendClubListClublistItem item, int position) {
					}
				};
				lv_zhuzhi.setAdapter(listadapter);
				mPtrFrameLayout.refreshComplete();
				showToastNet();
				break;
			default:
				break;
			}
		}
	};

	// 刷新end------------------------------------------------------------------

	private void initControl() {
		// 头部
		headerLayout = LayoutInflater.from(getActivity()).inflate(R.layout.head_zhuzhi, null, true);
		// 3个按钮
		tab1 = (TextView) headerLayout.findViewById(R.id.tv_zhuzhi_tab1);
		tab2 = (TextView) headerLayout.findViewById(R.id.tv_zhuzhi_tab2);
		tab3 = (TextView) headerLayout.findViewById(R.id.tv_zhuzhi_tab3);
		tab1.setTextColor(getResources().getColor(R.color.app_green));
		tab2.setTextColor(getResources().getColor(R.color.content));
		tab3.setTextColor(getResources().getColor(R.color.content));
		et1 = (CleareditTextView) headerLayout.findViewById(R.id.et_input_key);
		// 获得listview
		lv_zhuzhi = (ListView) getActivity().findViewById(R.id.lv_zhuzhi);
		// 获得父类的视图布局
		headerParent = new LinearLayout(getActivity());
		headerParent.addView(headerLayout);
		lv_zhuzhi.addHeaderView(headerParent);
		// 用来设置listview的数据适配
		listMyClubListClubList = new ArrayList<GetMyClubListClublistItem>();
		listCityClubListClubList = new ArrayList<GetCityClubListClublistItem>();
		listRecommendClubListClubList = new ArrayList<GetRecommendClubListClublistItem>();
		listMyClubListClubListTemp = new ArrayList<GetMyClubListClublistItem>();
		listCityClubListClubListTemp = new ArrayList<GetCityClubListClublistItem>();
		listRecommendClubListClubListTemp = new ArrayList<GetRecommendClubListClublistItem>();
	}

	@Override
	protected void onResload() {

	}

	@Override
	protected void setMyViewClick() {
		// 三个按钮
		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		et1.setOnClickListener(this);

	}

	@Override
	protected void headerOrFooterViewControl() {
		// 主界面控件的处理
		top_tv_title.setText("组织");
		top_ctv_center_et.setVisibility(View.GONE);
		top_tv_title.setVisibility(View.VISIBLE);
		top_tv_right.setVisibility(View.VISIBLE);
		top_tv_right.setOnClickListener(this);
	}

	private Thread thread = null;
	private List<GetMyClubListClublistItem> listMyClubListClubList = null;
	private List<GetCityClubListClublistItem> listCityClubListClubList = null;
	private List<GetRecommendClubListClublistItem> listRecommendClubListClubList = null;
	private List<GetMyClubListClublistItem> listMyClubListClubListTemp = null;
	private List<GetCityClubListClublistItem> listCityClubListClubListTemp = null;
	private List<GetRecommendClubListClublistItem> listRecommendClubListClubListTemp = null;

	public void sendGetMyClubListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetMyClubListAction();
			}
		};
		thread.start();
	}

	public void sendGetCityClubListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetCityClubListAction();
			}
		};
		thread.start();
	}

	public void sendGetRecommendClubListActionAsyn() {
		thread = new Thread() {
			public void run() {
				GetRecommendClubListAction();
			}
		};
		thread.start();
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

	public void GetMyClubListAction() {
		GetMyClubListRequest request = new GetMyClubListRequest();
		GetMyClubListRequestParameter parameter = new GetMyClubListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setCity(App.getInstance().getContactCity());
		parameter.setIndex(index);
		parameter.setCount(count);
		parameter.setCity(App.getInstance().getContactCity());
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

	public void GetCityClubListAction() {
		GetCityClubListRequest request = new GetCityClubListRequest();
		GetCityClubListRequestParameter parameter = new GetCityClubListRequestParameter();
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
					GetCityClubListResponse response = new GetCityClubListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetCityClubListResponsePayload payload = (GetCityClubListResponsePayload) response.getPayload();
					listCityClubListClubListTemp = payload.getClublistList();
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

	public void GetRecommendClubListAction() {
		GetRecommendClubListRequest request = new GetRecommendClubListRequest();
		GetRecommendClubListRequestParameter parameter = new GetRecommendClubListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setCity(App.getInstance().getContactCity());
		parameter.setIndex(index);
		parameter.setCount(count);
		parameter.setCity(App.getInstance().getContactCity());
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
					GetRecommendClubListResponse response = new GetRecommendClubListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetRecommendClubListResponsePayload payload = (GetRecommendClubListResponsePayload) response
							.getPayload();
					listRecommendClubListClubListTemp = payload.getClublistList();
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

	CommonAdapter<NoDataBean> listadapter3 = null;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (Loadmore) {
					if (LoadmoreData) {
						listRecommendClubListClubList.clear();
					}
				} else {
					listRecommendClubListClubList.clear();
				}
				listRecommendClubListClubList.addAll(listRecommendClubListClubListTemp);
				if (listRecommendClubListClubList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						try {
							nodata.setTitle(getActivity().getResources().getString(R.string.organization));
						} catch (Exception e) {
							nodata.setTitle("没有俱乐部…");
						}
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_zhuzhi.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new CommonAdapter<GetRecommendClubListClublistItem>(getActivity(),
								listRecommendClubListClubList, R.layout.item_zhuzhilist) {
							@Override
							public void convert(ViewHolderUntil helper, final GetRecommendClubListClublistItem item,
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
								helper.setText(R.id.tv_chengyuan, item.getMember() + "");
								helper.setText(R.id.tv_huodong, item.getActivity() + "");
								if (App.getInstance().getIs_leader() == 1) {
									helper.getView(R.id.tv_join).setVisibility(View.VISIBLE);
									switch (item.getLeaderApplyState()) {// 2：未申请过1：审核通过0：待审核-1为拒绝
									case -1:
										helper.setText(R.id.tv_join, "申请");
										helper.setTextDrawables(R.id.tv_join, R.drawable.zuzhu_join, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(true);
										break;
									case 0:
										helper.setText(R.id.tv_join, "审核中");
										helper.setTextDrawables(R.id.tv_join, 0, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(false);
										break;
									case 1:
										helper.setText(R.id.tv_join, "已加入");
										helper.setTextDrawables(R.id.tv_join, 0, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(false);
										break;
									case 2:
										helper.setText(R.id.tv_join, "申请");
										helper.setTextDrawables(R.id.tv_join, R.drawable.zuzhu_join, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(true);
										break;
									}
								} else {
									if (item.getIsJoin() == 0) {
										helper.setText(R.id.tv_join, "关注");
										helper.getView(R.id.tv_join).setVisibility(View.VISIBLE);
										helper.setTextDrawables(R.id.tv_join, R.drawable.zuzhu_join, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(true);
									} else if (item.getIsJoin() == 1) {
										helper.setText(R.id.tv_join, "已关注");
										helper.setTextDrawables(R.id.tv_join, 0, 0, 0, 0);
										helper.getView(R.id.tv_join).setVisibility(View.VISIBLE);
										helper.getView(R.id.tv_join).setEnabled(false);
									}
								}
								helper.getView(R.id.tv_join).setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										if (!TextUtils.isEmpty(App.getInstance().getAut())) {
											club_id = item.getClub_id();
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
							}

							public void setOncliklisenter2(View v, GetRecommendClubListClublistItem item) {
								Bundle bundle = new Bundle();
								bundle.putString("clubid", item.getClub_id());
								openActivity(JuLeBuXiangQingActivity.class, bundle);
								isFrist = false;
							};
						};
						lv_zhuzhi.setAdapter(listadapter);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:
				if (Loadmore) {
					if (LoadmoreData) {
						listCityClubListClubList.clear();
					}
				} else {
					listCityClubListClubList.clear();
				}
				listCityClubListClubList.addAll(listCityClubListClubListTemp);
				if (listCityClubListClubList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(

								getActivity().getResources().getString(R.string.organization_city));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_zhuzhi.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter1.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter1 = new CommonAdapter<GetCityClubListClublistItem>(getActivity(),
								listCityClubListClubList, R.layout.item_zhuzhilist) {
							@Override
							public void convert(ViewHolderUntil helper, final GetCityClubListClublistItem item,
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
								if (App.getInstance().getIs_leader() == 1) {
									helper.getView(R.id.tv_join).setVisibility(View.VISIBLE);
									switch (item.getLeaderApplyState()) {// 2：未申请过1：审核通过0：待审核-1为拒绝
									case -1:
										helper.setText(R.id.tv_join, "申请");
										helper.setTextDrawables(R.id.tv_join, R.drawable.zuzhu_join, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(true);
										break;
									case 0:
										helper.setText(R.id.tv_join, "审核中");
										helper.setTextDrawables(R.id.tv_join, 0, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(false);
										break;
									case 1:
										helper.setText(R.id.tv_join, "已加入");
										helper.setTextDrawables(R.id.tv_join, 0, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(false);
										break;
									case 2:
										helper.setText(R.id.tv_join, "申请");
										helper.setTextDrawables(R.id.tv_join, R.drawable.zuzhu_join, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(true);
										break;
									}
								} else {
									if (item.getIsJoin() == 0) {
										helper.setText(R.id.tv_join, "关注");
										helper.getView(R.id.tv_join).setVisibility(View.VISIBLE);
										helper.setTextDrawables(R.id.tv_join, R.drawable.zuzhu_join, 0, 0, 0);
										helper.getView(R.id.tv_join).setEnabled(true);
									} else if (item.getIsJoin() == 1) {
										helper.setText(R.id.tv_join, "已关注");
										helper.setTextDrawables(R.id.tv_join, 0, 0, 0, 0);
										helper.getView(R.id.tv_join).setVisibility(View.VISIBLE);
										helper.getView(R.id.tv_join).setEnabled(false);
									}
								}
								helper.getView(R.id.tv_join).setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										if (!TextUtils.isEmpty(App.getInstance().getAut())) {
											club_id = item.getClub_id();
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
							}

							public void setOncliklisenter2(View v, GetCityClubListClublistItem item) {
								Bundle bundle = new Bundle();
								bundle.putString("clubid", item.getClub_id());
								openActivity(JuLeBuXiangQingActivity.class, bundle);
								isFrist = false;
							};
						};
						lv_zhuzhi.setAdapter(listadapter1);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK3:
				if (Loadmore) {
					if (LoadmoreData) {
						listMyClubListClubList.clear();
					}
				} else {
					listMyClubListClubList.clear();
				}
				listMyClubListClubList.addAll(listMyClubListClubListTemp);
				if (listMyClubListClubList.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						try {
							nodata.setTitle(getActivity().getResources().getString(R.string.organization_my));
						} catch (Exception e) {
							nodata.setTitle("没有我关注的俱乐部…");
						}
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_zhuzhi.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter2.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter2 = new CommonAdapter<GetMyClubListClublistItem>(getActivity(),
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
								Bundle bundle = new Bundle();
								bundle.putString("clubid", item.getClub_id());
								openActivity(JuLeBuXiangQingActivity.class, bundle);
								isFrist = false;
							};

						};
						lv_zhuzhi.setAdapter(listadapter2);
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK4:
				index = 0;
				Loadmore = true;
				LoadmoreData = true;
				switch (type) {
				case 1:
					if (listadapter != null) {
						count = listadapter.getCount();
					} else {
						count = 10;
					}
					sendGetRecommendClubListActionAsyn();
					break;
				case 2:
					if (listadapter1 != null) {
						count = listadapter1.getCount();
					} else {
						count = 10;
					}
					sendGetCityClubListActionAsyn();
					break;
				case 3:
					if (listadapter2 != null) {
						count = listadapter2.getCount();
					} else {
						count = 10;
					}
					sendGetMyClubListActionAsyn();
					break;
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
			default:
				break;
			}
		};
	};

}
