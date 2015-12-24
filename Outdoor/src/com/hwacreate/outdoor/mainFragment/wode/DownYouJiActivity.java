package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.youji.YoujiXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.hwacreate.outdoor.ormlite.db.DownYouJiTapDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequest;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelRequestParameter;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponse;
import com.keyhua.outdoor.protocol.AgreeTravelAction.AgreeTravelResponsePayload;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author LaLa 下载的游记
 * 
 */

public class DownYouJiActivity extends BaseActivity {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private SwipeMenuListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;
	private List<YoujiXiangqing> mYoujiDatas = null;
	private List<YoujiXiangqing> mYoujiDatasTemp = null;
	private YoujiXiangqing youjiXiangqing = null;
	private DownYouJiTapDao youjiBaseDao = null;

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
		setContentView(R.layout.activity_detailed_list);
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
			onLoading();
		}
	}

	/**
	 * 数据的初始化
	 */
	public void initDao() {
		// 初始化取得下载游记的数据库
		youjiXiangqing = new YoujiXiangqing();
		mYoujiDatas = new ArrayList<YoujiXiangqing>();
		mYoujiDatasTemp = new ArrayList<YoujiXiangqing>();
		youjiBaseDao = new DownYouJiTapDao(youjiXiangqing, DownYouJiActivity.this);
	}

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		lv_home = (SwipeMenuListView) findViewById(R.id.lv_home_detailed_list);
		// 刷新与加载更多
		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(
				R.id.load_more_list_view_detailed_container);
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_detailed_frameLayout);
	}

	@Override
	protected void onInitData() {
		initDao();
		initHeaderOther();
		initControl();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText(getString(R.string.wode_downloadyouji));
		top_tv_right.setVisibility(View.GONE);
		showdialog();
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		lv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (mYoujiDatas != null && mYoujiDatas.size() != 0) {
					Bundle bundle = new Bundle();
					bundle.putInt("agreeCount", mYoujiDatas.get(arg2).getAgreeCount());
					bundle.putInt("isAgree", mYoujiDatas.get(arg2).getIsAgree());
					bundle.putInt("isDownLoad", 1);
					bundle.putString("tvl_id", mYoujiDatas.get(arg2).getTvl_id());
					bundle.putString("act_id", mYoujiDatas.get(arg2).getAct_id());
					openActivity(YoujiXiangQingActivity.class, bundle);
					isFrist = false;
				}
			}
		});
	}

	CommonAdapter<NoDataBean> listadapter3 = null;

	private void onLoading() {
		if (Loadmore) {
			if (LoadmoreData) {
				mYoujiDatas.clear();
			}
		} else {
			mYoujiDatas.clear();
		}
		mYoujiDatasTemp = youjiBaseDao.getList(index, count);
		mYoujiDatas.addAll(mYoujiDatasTemp);
		if (mYoujiDatas.size() == 0) {
			if (listadapter3 == null) {
				List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
				NoDataBean nodata = new NoDataBean();
				nodata.setTitle(getResources().getString(R.string.travel));
				nodatas.add(nodata);
				listadapter3 = new CommonAdapter<NoDataBean>(DownYouJiActivity.this, nodatas, R.layout.item_nodata) {
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
				listadapter = new MyListAdpter(DownYouJiActivity.this, mYoujiDatas);
				lv_home.setAdapter(listadapter);
			}
		}
		if (isshowdialog()) {
			closedialog();
		}
	}

	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:// 点赞
				switch (state) {
				case 0:
					showToast("点赞失败,请重新点赞");
					break;
				case 1:
					showdialog();
					index = 0;
					if (listadapter != null) {
						count = listadapter.getCount();
					} else {
						count = 10;
					}
					Loadmore = true;
					LoadmoreData = true;
					onLoading();
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
			default:
				break;
			}
		};
	};

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
				if (NetUtil.isNetworkAvailable(DownYouJiActivity.this)) {// 有网
					isNet = true;
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					LoadmoreData = false;
					onLoading();
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
				if (NetUtil.isNetworkAvailable(DownYouJiActivity.this)) {// 有网
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					onLoading();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					isNet = false;
					onLoading();
					mHandler.sendEmptyMessage(CommonUtility.ISNETCONNECTEDInt);
				}
			}
		});
		// auto load data
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh(true);
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
	private Thread thread = null;
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

	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private List<YoujiXiangqing> mList = null;

		public MyListAdpter(Context context, List<YoujiXiangqing> list) {
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
			mImageLoader.displayImage("file://" + mList.get(position).getTvl_cover(), holder.im_youji_beijing);
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
