package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.NoDataBean;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.MingdanGuanLiActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DensityUtils;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CustomDialog;
import com.keyhua.outdoor.protocol.DeleteUserMessageInfoAction.DeleteUserMessageInfoRequest;
import com.keyhua.outdoor.protocol.DeleteUserMessageInfoAction.DeleteUserMessageInfoRequestParameter;
import com.keyhua.outdoor.protocol.DeleteUserMessageInfoAction.DeleteUserMessageInfoResponse;
import com.keyhua.outdoor.protocol.DeleteUserMessageInfoAction.DeleteUserMessageInfoResponsePayload;
import com.keyhua.outdoor.protocol.GetUserMessagesListAction.GetUserMessagesListMsgListItem;
import com.keyhua.outdoor.protocol.GetUserMessagesListAction.GetUserMessagesListRequest;
import com.keyhua.outdoor.protocol.GetUserMessagesListAction.GetUserMessagesListRequestParameter;
import com.keyhua.outdoor.protocol.GetUserMessagesListAction.GetUserMessagesListResponse;
import com.keyhua.outdoor.protocol.GetUserMessagesListAction.GetUserMessagesListResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.MainActivity;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * @author LaLa 消息
 * 
 */
public class XiaoXiActivity extends BaseActivity {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private SwipeMenuListView mListView = null;
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout = null;
	public int index = 0;
	public int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFrist = true;
	private boolean isNet = true;
	private List<GetUserMessagesListMsgListItem> userMessagesList = null;
	private List<GetUserMessagesListMsgListItem> userMessagesListTemp = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_list);
		init();
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		userMessagesList = new ArrayList<GetUserMessagesListMsgListItem>();
		userMessagesListTemp = new ArrayList<GetUserMessagesListMsgListItem>();
		mListView = (SwipeMenuListView) findViewById(R.id.lv_home_detailed_list);
	}

	@Override
	public void onBackPressed() {
		if (!TextUtils.equals(getIntent().getStringExtra("mykey"), "nokey")) {
			openActivity(MainActivity.class);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			if (!TextUtils.equals(getIntent().getStringExtra("mykey"), "nokey")) {
				openActivity(MainActivity.class);
			} else {
				finish();
			}
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
	protected void onResload() {
		top_tv_title.setText(getString(R.string.wode_xiaoxi));
		showdialog();
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					Bundle bundle = new Bundle();
					bundle.putInt("Msgid", userMessagesList.get(position).getMsg_id());
					openActivity(XiaoXiDetailActivity.class, bundle);
					isFrist = false;
					break;
				case 1:
					showAlertDialog(XiaoXiActivity.this, "是否删除此条信息?                   ",
							userMessagesList.get(position).getMsg_id());
					break;
				default:
					break;
				}
				return false;
			}
		});

		mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
	}

	/** Dialog */
	public void showAlertDialog(Context context, String title, final int position) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				sendAsyn1(position);
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
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
				if (NetUtil.isNetworkAvailable(XiaoXiActivity.this)) {// 有网
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
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
			}

			// 开始刷新容器开头
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (NetUtil.isNetworkAvailable(XiaoXiActivity.this)) {// 有网
					isNet = true;
					index = 0;
					count = 10;
					Loadmore = false;
					LoadmoreData = false;
					sendAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					isNet = false;
					CommonAdapter<NoDataBean> listadapter3 = null;
					List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
					NoDataBean nodata = new NoDataBean();
					nodata.setTitle(getResources().getString(R.string.home_page));
					nodatas.add(nodata);
					listadapter3 = new CommonAdapter<NoDataBean>(XiaoXiActivity.this, nodatas, R.layout.item_nodata) {
						@Override
						public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
							helper.setText(R.id.news_title, item.getTitle());
						}
					};
					mListView.setAdapter(listadapter3);
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

	private Thread thread = null;

	// 获得用户未读消息个数
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() {
		GetUserMessagesListRequest request = new GetUserMessagesListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetUserMessagesListRequestParameter parameter = new GetUserMessagesListRequestParameter();
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
					GetUserMessagesListResponse response = new GetUserMessagesListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetUserMessagesListResponsePayload payload = (GetUserMessagesListResponsePayload) response
							.getPayload();
					userMessagesListTemp = payload.getMsgList();
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

	// 获得用户删除消息
	public void sendAsyn1(final int msgid) {
		thread = new Thread() {
			public void run() {
				Action1(msgid);
			}
		};
		thread.start();
	}

	private int state = 0; // 操作状态 1成功 0失败
	private String msg = ""; // 操作反馈消息

	public void Action1(int msgid) {
		DeleteUserMessageInfoRequest request = new DeleteUserMessageInfoRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		DeleteUserMessageInfoRequestParameter parameter = new DeleteUserMessageInfoRequestParameter();
		parameter.setMsg_id(msgid);
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
					DeleteUserMessageInfoResponse response = new DeleteUserMessageInfoResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					DeleteUserMessageInfoResponsePayload payload = (DeleteUserMessageInfoResponsePayload) response
							.getPayload();
					state = payload.getState();
					msg = payload.getMsg();
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

	CommonAdapter<NoDataBean> listadapter3 = null;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (Loadmore) {
					if (LoadmoreData) {
						userMessagesList.clear();
					}
				} else {
					userMessagesList.clear();
				}
				userMessagesList.addAll(userMessagesListTemp);
				if (userMessagesList.size() != 0) {
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new MyListAdpter(XiaoXiActivity.this, userMessagesList);
						mListView.setAdapter(listadapter);
					}
				} else {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(getResources().getString(R.string.message));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(XiaoXiActivity.this, nodatas,
								R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						mListView.setAdapter(listadapter3);
					}
					listadapter = null;
				}
				SwipeMenuCreator creator = new SwipeMenuCreator() {
					@Override
					public void create(SwipeMenu menu) {
						SwipeMenuItem bianji = new SwipeMenuItem(getApplicationContext());
						bianji.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
						bianji.setWidth(DensityUtils.dp2px(XiaoXiActivity.this, 45));
						bianji.setTitle("查看");
						bianji.setTitleSize(18);
						bianji.setTitleColor(Color.BLACK);
						menu.addMenuItem(bianji);

						SwipeMenuItem shanchu = new SwipeMenuItem(getApplicationContext());
						shanchu.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
						shanchu.setWidth(DensityUtils.dp2px(XiaoXiActivity.this, 45));
						shanchu.setTitle("删除");
						shanchu.setTitleSize(18);
						shanchu.setTitleColor(Color.BLACK);
						menu.addMenuItem(shanchu);
					}
				};
				mListView.setMenuCreator(creator);
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:
				if (state == 1) {
					showToast("删除成功");
					index = 0;
					if (listadapter != null) {
						count = listadapter.getCount();
					} else {
						count = 10;
					}
					Loadmore = true;
					LoadmoreData = true;
					sendAsyn();
				} else {
					showToast("删除失败");
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				isFrist = false;
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
		private List<GetUserMessagesListMsgListItem> mList = null;

		public MyListAdpter(Context context, List<GetUserMessagesListMsgListItem> mList) {
			this.context = context;
			this.mList = mList;
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
				convertView = LayoutInflater.from(context).inflate(R.layout.item_wode_xiaoxi, null);
				holder = new ViewHolder();
				holder.xiaoxi_name = (TextView) convertView.findViewById(R.id.xiaoxi_name);
				holder.xiaoxi_time = (TextView) convertView.findViewById(R.id.xiaoxi_time);
				holder.xiaoxi_content = (TextView) convertView.findViewById(R.id.xiaoxi_content);
				holder.xiaoxi_name_xiaoxi = (View) convertView.findViewById(R.id.xiaoxi_name_xiaoxi);
				holder.xiaoxi_view = (LinearLayout) convertView.findViewById(R.id.xiaoxi_view);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.xiaoxi_name.setText(mList.get(position).getMsg_title());
			holder.xiaoxi_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getMsg_time()));
			holder.xiaoxi_content.setText(mList.get(position).getMsg_content());
			if (mList.get(position).getIs_read() == 0) {
				holder.xiaoxi_name.setTextColor(getResources().getColor(R.color.title));
				holder.xiaoxi_content.setTextColor(getResources().getColor(R.color.title));
				holder.xiaoxi_name_xiaoxi.setVisibility(View.VISIBLE);
			} else {
				holder.xiaoxi_name.setTextColor(getResources().getColor(R.color.content));
				holder.xiaoxi_content.setTextColor(getResources().getColor(R.color.content));
				holder.xiaoxi_name_xiaoxi.setVisibility(View.GONE);
			}
			holder.xiaoxi_view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putInt("Msgid", userMessagesList.get(position).getMsg_id());
					openActivity(XiaoXiDetailActivity.class, bundle);
					isFrist = false;
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private TextView xiaoxi_name, xiaoxi_time, xiaoxi_content;
			private View xiaoxi_name_xiaoxi;
			private LinearLayout xiaoxi_view;
		}
	}

}
