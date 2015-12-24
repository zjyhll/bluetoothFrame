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
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.youji.YoujiXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.YouJiItemBean;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.ormlite.db.DownYouJiTapDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequest;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequestParameter;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponse;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetClubTravelAction.GetClubTravelRequest;
import com.keyhua.outdoor.protocol.GetClubTravelAction.GetClubTravelRequestParameter;
import com.keyhua.outdoor.protocol.GetClubTravelAction.GetClubTravelResponse;
import com.keyhua.outdoor.protocol.GetClubTravelAction.GetClubTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetClubTravelAction.GetClubTravelTravellistItem;
import com.keyhua.outdoor.protocol.GetRecentVisitTravelAction.GetRecentVisitTravelRequest;
import com.keyhua.outdoor.protocol.GetRecentVisitTravelAction.GetRecentVisitTravelRequestParameter;
import com.keyhua.outdoor.protocol.GetRecentVisitTravelAction.GetRecentVisitTravelResponse;
import com.keyhua.outdoor.protocol.GetRecentVisitTravelAction.GetRecentVisitTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetRecentVisitTravelAction.GetRecentVisitTravelTravellistItem;
import com.keyhua.outdoor.protocol.GetRecommendTravelAction.GetRecommendTravelRequest;
import com.keyhua.outdoor.protocol.GetRecommendTravelAction.GetRecommendTravelRequestParameter;
import com.keyhua.outdoor.protocol.GetRecommendTravelAction.GetRecommendTravelResponse;
import com.keyhua.outdoor.protocol.GetRecommendTravelAction.GetRecommendTravelResponsePayload;
import com.keyhua.outdoor.protocol.GetRecommendTravelAction.GetRecommendTravelTravellistItem;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
 * @author 曾金叶 游记
 * @2015-8-5 @上午10:15:40
 */
public class YouJiFragment extends BaseFragment {
	// 上拉下拉刷新
	LoadMoreListViewContainer loadMoreListViewContainer = null;
	private PtrFrameLayout mPtrFrameLayout;
	// 数据库操作
	private int isDownLoad = 0;
	private List<YoujiXiangqing> mYoujiDatas = null;
	private List<YoujiXiangqing> mYoujiDatasTemp = null;
	private YoujiXiangqing youjiXiangqing = null;
	private BaseDao<YoujiXiangqing> youjiBaseDao = null;
	private DownYouJiTapDao youjiBaseDaoList = null;
	// pop
	private PopupWindow popMenu = null;
	private RelativeLayout parent = null;// 半透明背景色
	private RelativeLayout pop_youji1 = null;
	private RelativeLayout pop_youji2 = null;
	private RelativeLayout pop_youji3 = null;
	private RelativeLayout pop_youji4 = null;
	private RelativeLayout pop_youji5 = null;
	// 主窗体中的listView
	private YouJiItemBean youJiItemBean = null;
	private List<YouJiItemBean> mDatas = null;
	private List<YouJiItemBean> mDatasTemp = null;
	// private YouJiItemBean youJiItemBean = null;//
	// 实例化一个工具类，用来存入本地数据库，以及初始化adpter
	// private YouJiTapItemDao<YouJiItemBean> youJiTapItemDao = null;//
	// 声明一个存放YouJiItemBean的数据库链接
	private ListView lv_youji = null;
	private CommonAdapter<YouJiItemBean> listadapter = null;
	private String tvl_id = null;
	private int state = 0;
	private int item = 0;
	private View parentView = null;
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

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.mainfrag_youji, container, false);
		return parentView;
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
		case R.id.top_iv_right_menu:
			popMenu.showAtLocation(parentView, Gravity.CENTER, 0, 0);
			break;
		case R.id.parent:
			popMenu.dismiss();
			break;
		case R.id.pop_youji1:
			popMenu.dismiss();
			if (isNet) {
				showdialog();
				if (isNet) {
					isDownLoad = 0;
					mYoujiDatas = youjiBaseDao.queryAll();
				} else {
					mYoujiDatas.clear();
				}
				item = 0;
				top_tv_title.setText("精彩推荐");
				listadapter3 = null;
				onrefreshAndLoadmore();
			} else {
				showToastNet();
			}
			break;
		case R.id.pop_youji2:
			popMenu.dismiss();
			if (isNet) {
				if (!TextUtils.isEmpty(App.getInstance().getAut())) {
					showdialog();
					isDownLoad = 0;
					mYoujiDatas = youjiBaseDao.queryAll();
					item = 1;
					top_tv_title.setText("最近浏览");
					listadapter3 = null;
					onrefreshAndLoadmore();
				} else {
					showToastDengLu();
					openActivity(LoginActivity.class);
					isFrist = false;
				}
			} else {
				showToastNet();
			}
			break;
		case R.id.pop_youji3:
			popMenu.dismiss();
			if (isNet) {
				if (!TextUtils.isEmpty(App.getInstance().getAut())) {
					showdialog();
					isDownLoad = 0;
					mYoujiDatas = youjiBaseDao.queryAll();
					item = 2;
					top_tv_title.setText("俱乐部");
					listadapter3 = null;
					onrefreshAndLoadmore();
				} else {
					showToastDengLu();
					openActivity(LoginActivity.class);
					isFrist = false;
				}
			} else {
				showToastNet();
			}
			break;
		case R.id.pop_youji4:
			showdialog();
			isDownLoad = 0;
			item = 3;
			top_tv_title.setText("已下载");
			popMenu.dismiss();
			listadapter3 = null;
			onrefreshAndLoadmore();
			break;
		case R.id.pop_youji5:
			popMenu.dismiss();
			if (isNet) {
				isDownLoad = 0;
				Bundle bundle = new Bundle();
				bundle.putInt("Searchid", 3);
				openActivity(AllSearchActivity.class, bundle);
				isFrist = false;
			} else {
				showToastNet();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onStart() {
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
			mYoujiDatas = youjiBaseDao.queryAll();
			switch (item) {
			case 0:
				sendGetActivityListActionAsyn();
				break;
			case 1:
				sendGetRecentVisitTraveActionAsyn();
				break;
			case 2:
				sendGetClubTravelListActionAsyn();
				break;
			case 3:
				setDownLoadData();
				break;

			default:
				break;
			}
		}
	}

	private void setDownLoadData() {
		mDatas.clear();
		if (Loadmore) {
			if (LoadmoreData) {
				mYoujiDatas.clear();
			}
		} else {
			mYoujiDatas.clear();
		}
		mYoujiDatasTemp = youjiBaseDaoList.getList(index, count);
		mYoujiDatas.addAll(mYoujiDatasTemp);
		if (mYoujiDatas != null) {
			for (int i = 0; i < mYoujiDatas.size(); i++) {
				youJiItemBean = new YouJiItemBean();
				youJiItemBean.setAct_start_time((mYoujiDatas.get(i)).getAct_start_time());
				youJiItemBean.setIsAgree((mYoujiDatas.get(i)).getIsAgree());
				youJiItemBean.setScenicsots((mYoujiDatas.get(i)).getScenicsots());
				youJiItemBean.setTvl_cover((mYoujiDatas.get(i)).getTvl_cover());
				youJiItemBean.setTvl_id((mYoujiDatas.get(i)).getTvl_id());
				youJiItemBean.setTvl_title((mYoujiDatas.get(i)).getTvl_title());
				youJiItemBean.setTvl_type((mYoujiDatas.get(i)).getTvl_type());
				youJiItemBean.setUser_nickname((mYoujiDatas.get(i)).getUser_nickname());
				youJiItemBean.setClub_name(mYoujiDatas.get(i).getClub_name());
				youJiItemBean.setAgreeCount((mYoujiDatas.get(i)).getAgreeCount());
				mDatas.add(youJiItemBean);
			}
		}
		if (mDatas.size() == 0) {
			if (listadapter3 == null) {
				List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
				NoDataBean nodata = new NoDataBean();
				try {
					nodata.setTitle(getActivity().getResources().getString(R.string.travel));
				} catch (Exception e) {
					nodata.setTitle("没有游记…");
				}
				nodatas.add(nodata);
				listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
					@Override
					public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
						helper.setText(R.id.news_title, item.getTitle());
					}
				};
				lv_youji.setAdapter(listadapter3);
			}
			listadapter = null;
		} else {
			setAdapter();
		}
		if (isshowdialog()) {
			closedialog();
		}
	}

	private void refreshAndLoadmore() {
		// 上下刷新START--------------------------------------------------------------------
		// 获取装载VIew的容器
		mPtrFrameLayout = (PtrFrameLayout) getActivity().findViewById(R.id.load_more_list_view_ptr_frame);
		// 获取view的引用
		loadMoreListViewContainer = (LoadMoreListViewContainer) getActivity()
				.findViewById(R.id.load_more_list_view_container);
		// 使用默认样式
		loadMoreListViewContainer.useDefaultHeader();
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			// loadMoreListViewContainer调用onLoadMore传入loadMoreListViewContainer自身对象
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
					isNet = true;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					switch (item) {
					case 0:
						sendGetActivityListActionAsyn();
						break;
					case 1:
						sendGetRecentVisitTraveActionAsyn();
						break;
					case 2:
						sendGetClubTravelListActionAsyn();
						break;
					case 3:
						setDownLoadData();
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
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_youji, header);
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
					mYoujiDatas = youjiBaseDao.queryAll();
					switch (item) {
					case 0:
						sendGetActivityListActionAsyn();
						break;
					case 1:
						sendGetRecentVisitTraveActionAsyn();
						break;
					case 2:
						sendGetClubTravelListActionAsyn();
						break;
					case 3:
						setDownLoadData();
						break;
					default:
						break;
					}
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					if (isshowdialog()) {
						closedialog();
					}
					if (item == 3) {
						mYoujiDatas = youjiBaseDao.queryAll();
					}
					setDownLoadData();
					isNet = false;
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
	@Override
	protected void onInitData() {
		initDao();
		lv_youji = (ListView) getActivity().findViewById(R.id.lv_youji);
		if (NetUtil.isNetworkAvailable(getActivity())) {
			top_tv_title.setText("精彩推荐");
		} else {
			item = 3;
			mYoujiDatas = youjiBaseDao.queryAll();
			top_tv_title.setText("已下载");
		}
	}

	private void initDao() {
		list = new ArrayList<GetRecommendTravelTravellistItem>();
		listTemp = new ArrayList<GetRecommendTravelTravellistItem>();
		listClubTravel = new ArrayList<GetClubTravelTravellistItem>();
		listClubTravelTemp = new ArrayList<GetClubTravelTravellistItem>();
		listGetRecentVisitTravel = new ArrayList<GetRecentVisitTravelTravellistItem>();
		listGetRecentVisitTravelTemp = new ArrayList<GetRecentVisitTravelTravellistItem>();
		mDatas = new ArrayList<YouJiItemBean>();
		mDatasTemp = new ArrayList<YouJiItemBean>();
		// 数据库操作,以及从数据库中取出不同类型的数据
		// 初始化从服务器取得的数据的容器
		mYoujiDatas = new ArrayList<YoujiXiangqing>();
		mYoujiDatasTemp = new ArrayList<YoujiXiangqing>();
		youjiXiangqing = new YoujiXiangqing();
		youjiBaseDao = new BaseDao<YoujiXiangqing>(youjiXiangqing, getActivity());
		youjiBaseDaoList = new DownYouJiTapDao(youjiXiangqing, getActivity());
		mYoujiDatas = youjiBaseDao.queryAll();
		initPop();
	}

	@Override
	protected void onResload() {
		showdialog();
		refreshAndLoadmore();
	}

	@Override
	protected void setMyViewClick() {
		top_iv_right_menu.setOnClickListener(this);
		parent.setOnClickListener(this);
		pop_youji1.setOnClickListener(this);
		pop_youji2.setOnClickListener(this);
		pop_youji3.setOnClickListener(this);
		pop_youji4.setOnClickListener(this);
		pop_youji5.setOnClickListener(this);
	}

	@Override
	protected void headerOrFooterViewControl() {
		// 主界面控件的处理
		top_iv_right_menu.setVisibility(View.VISIBLE);
		top_ctv_center_et.setVisibility(View.GONE);
		top_tv_title.setVisibility(View.VISIBLE);
		top_tv_right.setVisibility(View.GONE);

	}

	private View view = null;

	private void initPop() {
		view = getActivity().getLayoutInflater().inflate(R.layout.pop_youji_menu_list, null);
		popMenu = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popMenu.setBackgroundDrawable(new BitmapDrawable());
		popMenu.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		parent = (RelativeLayout) view.findViewById(R.id.parent);
		pop_youji1 = (RelativeLayout) view.findViewById(R.id.pop_youji1);
		pop_youji2 = (RelativeLayout) view.findViewById(R.id.pop_youji2);
		pop_youji3 = (RelativeLayout) view.findViewById(R.id.pop_youji3);
		pop_youji4 = (RelativeLayout) view.findViewById(R.id.pop_youji4);
		pop_youji5 = (RelativeLayout) view.findViewById(R.id.pop_youji5);
	}

	private Thread thread = null;

	// 取得推荐游记列表
	public void sendGetActivityListActionAsyn() {
		thread = new Thread() {
			public void run() {
				getActivityListAction();
			}
		};
		thread.start();
	}

	private List<GetRecommendTravelTravellistItem> list = null;
	private List<GetRecommendTravelTravellistItem> listTemp = null;

	public void getActivityListAction() {
		GetRecommendTravelRequest request = new GetRecommendTravelRequest();
		GetRecommendTravelRequestParameter parameter = new GetRecommendTravelRequestParameter();
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
					GetRecommendTravelResponse response = new GetRecommendTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetRecommendTravelResponsePayload payload = (GetRecommendTravelResponsePayload) response
							.getPayload();
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

	// 取得俱乐部的游记
	public void sendGetClubTravelListActionAsyn() {
		thread = new Thread() {
			public void run() {
				getGetClubTravelAction();
			}
		};
		thread.start();
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	private List<GetClubTravelTravellistItem> listClubTravel = null;
	private List<GetClubTravelTravellistItem> listClubTravelTemp = null;

	public void getGetClubTravelAction() {
		GetClubTravelRequest request = new GetClubTravelRequest();
		GetClubTravelRequestParameter parameter = new GetClubTravelRequestParameter();
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
					GetClubTravelResponse response = new GetClubTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetClubTravelResponsePayload payload = (GetClubTravelResponsePayload) response.getPayload();
					this.listClubTravelTemp = payload.getTravellistList();
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

	// 最近浏览俱乐部的游记
	public void sendGetRecentVisitTraveActionAsyn() {
		thread = new Thread() {
			public void run() {
				getGetRecentVisitTraveAction();
			}
		};
		thread.start();
	}

	private List<GetRecentVisitTravelTravellistItem> listGetRecentVisitTravel = null;
	private List<GetRecentVisitTravelTravellistItem> listGetRecentVisitTravelTemp = null;

	public void getGetRecentVisitTraveAction() {
		GetRecentVisitTravelRequest request = new GetRecentVisitTravelRequest();
		GetRecentVisitTravelRequestParameter parameter = new GetRecentVisitTravelRequestParameter();
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
					GetRecentVisitTravelResponse response = new GetRecentVisitTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetRecentVisitTravelResponsePayload payload = (GetRecentVisitTravelResponsePayload) response
							.getPayload();
					this.listGetRecentVisitTravelTemp = payload.getTravellistList();
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

	CommonAdapter<NoDataBean> listadapter3 = null;
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:// 精彩推荐
				mDatas.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						list.clear();
					}
				} else {
					list.clear();
				}
				list.addAll(listTemp);
				mDatas.addAll(changeListType(list));
				if (mDatas.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						try {
							nodata.setTitle(getActivity().getResources().getString(R.string.travel));
						} catch (Exception e) {
							nodata.setTitle("没有游记…");
						}
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_youji.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						setAdapter();
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:// 俱乐部的游记
				mDatas.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						listClubTravel.clear();
					}
				} else {
					listClubTravel.clear();
				}
				listClubTravel.addAll(listClubTravelTemp);
				mDatas.addAll(changeListType(listClubTravel));
				if (mDatas.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						try {
							nodata.setTitle(getActivity().getResources().getString(R.string.travel));
						} catch (Exception e) {
							nodata.setTitle("没有游记…");
						}
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_youji.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						setAdapter();
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK3:// 最近浏览的
				mDatas.clear();
				if (Loadmore) {
					if (LoadmoreData) {
						listGetRecentVisitTravel.clear();
					}
				} else {
					listGetRecentVisitTravel.clear();
				}
				listGetRecentVisitTravel.addAll(listGetRecentVisitTravelTemp);
				mDatas.addAll(changeListType(listGetRecentVisitTravel));
				if (mDatas.size() == 0) {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						try {
							nodata.setTitle(getActivity().getResources().getString(R.string.travel));
						} catch (Exception e) {
							nodata.setTitle("没有游记…");
						}
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(getActivity(), nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_youji.setAdapter(listadapter3);
					}
					listadapter = null;
				} else {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						setAdapter();
					}
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK4:// 点赞
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
					switch (item) {
					case 0:
						sendGetActivityListActionAsyn();
						break;
					case 1:
						sendGetRecentVisitTraveActionAsyn();
						break;
					case 2:
						sendGetClubTravelListActionAsyn();
						break;
					case 3:
						break;
					default:
						break;
					}
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
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				isFrist = false;
			default:
				break;
			}
		};
	};

	private void setAdapter() {
		listadapter3 = null;
		listadapter = new CommonAdapter<YouJiItemBean>(getActivity(), mDatas, R.layout.item_youjilist) {
			@Override
			public void convert(ViewHolderUntil helper, final YouJiItemBean item, final int position) {
				if (YouJiFragment.this.item == 3) {
					helper.getView(R.id.tv_youji_dianzan).setVisibility(View.GONE);
					helper.setCubeImageByUrlSD(R.id.im_youji_beijing, item.getTvl_cover(), mImageLoader);
				} else {
					helper.setCubeImageByUrl(R.id.im_youji_beijing, item.getTvl_cover(), imageLoader);
				}
				helper.getView(R.id.youji_list).setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						App.getInstance().getScreenHeight() / 3));
				helper.setText(R.id.tv_youji_huodong, item.getUser_nickname());
				helper.setText(R.id.tv_youji_geyan, item.getTvl_title());
				helper.setText(R.id.tv_youji_time, TimeDateUtils.formatDateFromDatabaseTime(item.getAct_start_time()));
				helper.setText(R.id.tv_youji_dianzan, item.getAgreeCount() + "");
				helper.setText(R.id.tv_youji_julebu, item.getClub_name());
				if (item.getIsAgree() != null && item.getIsAgree() == 0) {
					helper.setTextColor(R.id.tv_youji_dianzan, getResources().getColor(R.color.white));
					helper.setTextDrawables(R.id.tv_youji_dianzan, R.drawable.dian_zan_n, 0, 0, 0);
				} else if (item.getIsAgree() != null && item.getIsAgree() == 1) {
					helper.setTextColor(R.id.tv_youji_dianzan, getResources().getColor(R.color.app_green));
					helper.setTextDrawables(R.id.tv_youji_dianzan, R.drawable.dian_zan_c, 0, 0, 0);
				}
				helper.getView(R.id.tv_youji_dianzan).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						tvl_id = item.getTvl_id();
						if (item.getIsAgree() == 0) {
							if (!TextUtils.isEmpty(App.getInstance().getAut())) {
								sendAgreeTravelActionAsyn();
							} else {
								showToastDengLu();
							}
						}
					}
				});
			}

			@Override
			public void setOncliklisenter3(View v, YouJiItemBean item, int position) {
				Bundle bundle = new Bundle();
				bundle.putInt("agreeCount", item.getAgreeCount() != null ? item.getAgreeCount() : 0);
				bundle.putInt("isAgree", item.getIsAgree() != null ? item.getIsAgree() : 0);
				if (mYoujiDatas != null) {
					for (int i = 0; i < mYoujiDatas.size(); i++) {
						if (TextUtils.equals(mYoujiDatas.get(i).getTvl_id(), item.getTvl_id())) {
							isDownLoad = 1;
						}
					}
				}
				bundle.putInt("isDownLoad", isDownLoad);
				bundle.putString("tvl_id", item.getTvl_id() != null ? item.getTvl_id() : "");
				bundle.putString("act_id", item.getAct_id() != null ? item.getAct_id() : "");
				if (isNet || isDownLoad == 1) {
					openActivity(YoujiXiangQingActivity.class, bundle);
					isDownLoad = 0;
					isFrist = false;
				} else {
					showToastNet();
				}
			}
		};
		lv_youji.setAdapter(listadapter);
	}

	private <T> List<YouJiItemBean> changeListType(List<T> l) {
		mDatasTemp.clear();
		switch (item) {
		case 0:// 精彩推荐
			for (int i = 0; i < l.size(); i++) {
				youJiItemBean = new YouJiItemBean();
				youJiItemBean.setAct_start_time(((GetRecommendTravelTravellistItem) l.get(i)).getAct_start_time());
				youJiItemBean.setIsAgree(((GetRecommendTravelTravellistItem) l.get(i)).getIsAgree());
				youJiItemBean.setScenicsots(((GetRecommendTravelTravellistItem) l.get(i)).getScenicsots());
				youJiItemBean.setTvl_cover(((GetRecommendTravelTravellistItem) l.get(i)).getTvl_cover());
				youJiItemBean.setTvl_id(((GetRecommendTravelTravellistItem) l.get(i)).getTvl_id());
				youJiItemBean.setTvl_title(((GetRecommendTravelTravellistItem) l.get(i)).getTvl_title());
				youJiItemBean.setTvl_type(((GetRecommendTravelTravellistItem) l.get(i)).getTvl_type());
				youJiItemBean.setUser_nickname(((GetRecommendTravelTravellistItem) l.get(i)).getUser_nickname());
				youJiItemBean.setAgreeCount(((GetRecommendTravelTravellistItem) l.get(i)).getAgreeCount());
				youJiItemBean.setClub_name(((GetRecommendTravelTravellistItem) l.get(i)).getClub_name());
				youJiItemBean.setAct_id(((GetRecommendTravelTravellistItem) l.get(i)).getAct_id());
				mDatasTemp.add(youJiItemBean);
			}
			break;
		case 1:// 最近浏览
			for (int i = 0; i < l.size(); i++) {
				youJiItemBean = new YouJiItemBean();
				youJiItemBean.setAct_start_time(((GetRecentVisitTravelTravellistItem) l.get(i)).getAct_start_time());
				youJiItemBean.setIsAgree(((GetRecentVisitTravelTravellistItem) l.get(i)).getIsAgree());
				youJiItemBean.setScenicsots(((GetRecentVisitTravelTravellistItem) l.get(i)).getScenicsots());
				youJiItemBean.setTvl_cover(((GetRecentVisitTravelTravellistItem) l.get(i)).getTvl_cover());
				youJiItemBean.setTvl_id(((GetRecentVisitTravelTravellistItem) l.get(i)).getTvl_id());
				youJiItemBean.setTvl_title(((GetRecentVisitTravelTravellistItem) l.get(i)).getTvl_title());
				youJiItemBean.setTvl_type(((GetRecentVisitTravelTravellistItem) l.get(i)).getTvl_type());
				youJiItemBean.setUser_nickname(((GetRecentVisitTravelTravellistItem) l.get(i)).getUser_nickname());
				youJiItemBean.setAgreeCount(((GetRecentVisitTravelTravellistItem) l.get(i)).getAgreeCount());
				youJiItemBean.setClub_name(((GetRecentVisitTravelTravellistItem) l.get(i)).getClub_name());
				youJiItemBean.setAct_id(((GetRecentVisitTravelTravellistItem) l.get(i)).getAct_id());
				mDatasTemp.add(youJiItemBean);
			}
			break;
		case 2:// 俱乐部
			for (int i = 0; i < l.size(); i++) {
				youJiItemBean = new YouJiItemBean();
				youJiItemBean.setAct_start_time(((GetClubTravelTravellistItem) l.get(i)).getAct_start_time());
				youJiItemBean.setIsAgree(((GetClubTravelTravellistItem) l.get(i)).getIsAgree());
				youJiItemBean.setScenicsots(((GetClubTravelTravellistItem) l.get(i)).getScenicsots());
				youJiItemBean.setTvl_cover(((GetClubTravelTravellistItem) l.get(i)).getTvl_cover());
				youJiItemBean.setTvl_id(((GetClubTravelTravellistItem) l.get(i)).getTvl_id());
				youJiItemBean.setTvl_title(((GetClubTravelTravellistItem) l.get(i)).getTvl_title());
				youJiItemBean.setTvl_type(((GetClubTravelTravellistItem) l.get(i)).getTvl_type());
				youJiItemBean.setUser_nickname(((GetClubTravelTravellistItem) l.get(i)).getUser_nickname());
				youJiItemBean.setAgreeCount(((GetClubTravelTravellistItem) l.get(i)).getAgreeCount());
				youJiItemBean.setClub_name(((GetClubTravelTravellistItem) l.get(i)).getClub_name());
				youJiItemBean.setAct_id(((GetClubTravelTravellistItem) l.get(i)).getAct_id());
				mDatasTemp.add(youJiItemBean);
			}
			break;
		case 3:// 已下载

			break;

		default:
			break;
		}
		return mDatasTemp;
	}
}
