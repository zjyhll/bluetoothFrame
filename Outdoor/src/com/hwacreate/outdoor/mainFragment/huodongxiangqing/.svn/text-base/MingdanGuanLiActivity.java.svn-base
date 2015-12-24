package com.hwacreate.outdoor.mainFragment.huodongxiangqing;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.hwacreate.outdoor.ormlite.bean.SignUpUser;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DataCleanManager;
import com.hwacreate.outdoor.utl.DensityUtils;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CustomDialog;
import com.keyhua.outdoor.protocol.ConfirmSignUpUserAction.ConfirmSignUpUserRequest;
import com.keyhua.outdoor.protocol.ConfirmSignUpUserAction.ConfirmSignUpUserRequestParameter;
import com.keyhua.outdoor.protocol.ConfirmSignUpUserAction.ConfirmSignUpUserResponse;
import com.keyhua.outdoor.protocol.ConfirmSignUpUserAction.ConfirmSignUpUserResponsePayload;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListRequest;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListRequestParameter;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListResponse;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListResponsePayload;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListUserlistItem;
import com.keyhua.outdoor.protocol.ShotOffSignUpUserAction.ShotOffSignUpUserRequest;
import com.keyhua.outdoor.protocol.ShotOffSignUpUserAction.ShotOffSignUpUserRequestParameter;
import com.keyhua.outdoor.protocol.ShotOffSignUpUserAction.ShotOffSignUpUserResponse;
import com.keyhua.outdoor.protocol.ShotOffSignUpUserAction.ShotOffSignUpUserResponsePayload;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportRequest;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportRequestParameter;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportResponse;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

/**
 * @author houxiaodong 名单管理
 * 
 */

public class MingdanGuanLiActivity extends BaseActivity implements OnItemClickListener {
	// 界面控件
	private Context mContext = null;
	// 活动id
	private String act_id = null;
	private boolean showbtn = false;// showbtn
	private Integer tps_id = null;
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	private SwipeMenuListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;
	// 顶部提示框
	private TextView top_jihua_num = null;
	private TextView top_jihua_now = null;
	private LinearLayout top_view = null;
	private Integer act_join_num_limit = 0;// 参加活动人数限制
	private Integer act_join_num_now = 0;// 参加活动人数限制
	/** 整形，可选，获取评论的起始索引值，若无该参数或该参数小于0，取最新的数据 */
	private int index = 0;
	/** 整形，可选，获取评论的数量，若无该参数，服务器默认取20条 */
	private int count = 10;
	private boolean Loadmore = false;
	private boolean LoadmoreData = false;
	private boolean isFirst = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = MingdanGuanLiActivity.this;
		act_id = getIntent().getExtras().getString("act_id");
		showbtn = getIntent().getExtras().getBoolean("showbtn");
		setContentView(R.layout.activity_detailed_list);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.top_tv_right:// .领队重置报到 TODO
			showAlertDialog(mContext, "是否重置报到?                   ", -1);
			break;
		default:
			break;
		}
	}

	/**
	 * 数据的初始化
	 */
	public void initDao() {
		// 数据操作
		list = new ArrayList<GetSignUpUserListUserlistItem>();
		listTemp = new ArrayList<GetSignUpUserListUserlistItem>();
	}

	/**
	 * 控件的初始化
	 */
	public void initControl() {
		top_jihua_num = (TextView) findViewById(R.id.top_jihua_num);
		top_jihua_now = (TextView) findViewById(R.id.top_jihua_now);
		top_view = (LinearLayout) findViewById(R.id.top_view);
		lv_home = (SwipeMenuListView) findViewById(R.id.lv_home_detailed_list);
	}

	@Override
	protected void onInitData() {
		initDao();
		initHeaderOther();
		initControl();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText(getString(R.string.left_gongju_myguardian_actmanager_mingdanguanli));
		top_view.setVisibility(View.VISIBLE);
		if (showbtn) {
			top_tv_right.setText("重置报到");
			top_tv_right.setOnClickListener(this);
			top_tv_right.setVisibility(View.GONE);
		} else {
			top_tv_right.setVisibility(View.GONE);
		}
		showdialog();
		refreshAndLoadMore();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		lv_home.setOnItemClickListener(this);
		lv_home.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

				switch (index) {
				case 0:// 同意与踢出操作 TODO
					switch (menu.getViewType()) {
					case 0:
						// 同意
						if (NetUtil.isNetworkAvailable(MingdanGuanLiActivity.this)) {// 有网
							showdialog();
							tps_id = list.get(position).getTps_id();
							sendConfirmSignUpUserAsyn();
						} else {// 无网
							showToastNet();
						}
						break;
					case 1:
						// 踢出
						showAlertDialog(mContext, "是否踢出该队员?                   ", position);
						break;

					default:
						break;
					}
					break;
				case 1:// 电话
					if (!TextUtils.isEmpty(list.get(position).getPhonenum())) {
						Uri telToUri = Uri.parse("tel:" + list.get(position).getPhonenum());
						Intent intent = new Intent(Intent.ACTION_CALL, telToUri);
						MingdanGuanLiActivity.this.startActivity(intent);
					} else {
						showToast("电话号码有误");
					}
					break;
				case 2:// 短信
					if (!TextUtils.isEmpty(list.get(position).getPhonenum())) {
						Uri smsToUri = Uri.parse("smsto:" + list.get(position).getPhonenum());
						Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
						// mIntent.putExtra("sms_body", "The SMS text");
						MingdanGuanLiActivity.this.startActivity(mIntent);
					} else {
						showToast("电话号码有误");
					}
					break;
				}
				return false;
			}
		});

		lv_home.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

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
				showdialog();
				if (position != -1) {
					tps_id = list.get(position).getTps_id();
					sendShotOffSignUpUserAsyn();
				} else {
					sendTeamResetReportAsyn();
				}
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!isFirst && !TextUtils.isEmpty(App.getInstance().getAut())) {
			index = 0;
			if (listadapter != null && listadapter.getCount() > count) {
				count = listadapter.getCount();
			} else {
				count = 10;
			}
			Loadmore = true;
			LoadmoreData = true;
			sendGetSignUpUserListAsyn();
		}
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
				if (NetUtil.isNetworkAvailable(MingdanGuanLiActivity.this)) {// 有网
					if (listadapter != null) {
						index = listadapter.getCount();
					} else {
						index = 0;
					}
					count = 10;
					Loadmore = true;
					// sendGetSignUpUserListAsyn();
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
				if (NetUtil.isNetworkAvailable(MingdanGuanLiActivity.this)) {// 有网
					index = 0;
					count = 10;
					Loadmore = false;
					sendGetSignUpUserListAsyn();
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 跳转到队员详情界面
		Bundle bundle = new Bundle();
		bundle.putString("uid", list.get(position).getU_id());
		openActivity(DuiyuanxiangqingActivity.class, bundle);
	}

	/**
	 * 活动队伍管理接口接入
	 * 
	 */

	private Thread thread = null;
	private List<GetSignUpUserListUserlistItem> list = null;
	private List<GetSignUpUserListUserlistItem> listTemp = null;

	public void sendGetSignUpUserListAsyn() {
		thread = new Thread() {
			public void run() {
				GetSignUpUserListAction();
			}
		};
		thread.start();
	}

	public void sendShotOffSignUpUserAsyn() {
		thread = new Thread() {
			public void run() {
				ShotOffSignUpUserAction();
			}
		};
		thread.start();
	}

	public void sendConfirmSignUpUserAsyn() {
		thread = new Thread() {
			public void run() {
				ConfirmSignUpUserAction();
			}
		};
		thread.start();
	}

	/**
	 * 取得活动报名列表
	 */
	public void GetSignUpUserListAction() {
		GetSignUpUserListRequest request = new GetSignUpUserListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetSignUpUserListRequestParameter parameter = new GetSignUpUserListRequestParameter();
		parameter.setAct_id(act_id);
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					GetSignUpUserListResponse response = new GetSignUpUserListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetSignUpUserListResponsePayload payload = (GetSignUpUserListResponsePayload) response.getPayload();
					listTemp = payload.getUserlist();
					act_join_num_limit = payload.getAct_join_num_limit();
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

	private String msgString = null;

	/**
	 * 剔除队员
	 */
	public void ShotOffSignUpUserAction() {
		ShotOffSignUpUserRequest request = new ShotOffSignUpUserRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		ShotOffSignUpUserRequestParameter parameter = new ShotOffSignUpUserRequestParameter();
		parameter.setTps_id(tps_id);
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				msgString = responseObject.getString("msg");
				if (ret == 0) {
					ShotOffSignUpUserResponse response = new ShotOffSignUpUserResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					ShotOffSignUpUserResponsePayload payload = (ShotOffSignUpUserResponsePayload) response.getPayload();
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

	/**
	 * 确认参队
	 */
	public void ConfirmSignUpUserAction() {
		ConfirmSignUpUserRequest request = new ConfirmSignUpUserRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		ConfirmSignUpUserRequestParameter parameter = new ConfirmSignUpUserRequestParameter();
		parameter.setTps_id(tps_id);
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				msgString = responseObject.getString("msg");
				if (ret == 0) {
					ConfirmSignUpUserResponse response = new ConfirmSignUpUserResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}

					// 处理返回的参数，需要强制类型转换
					ConfirmSignUpUserResponsePayload payload = (ConfirmSignUpUserResponsePayload) response.getPayload();
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

	public void sendTeamResetReportAsyn() {
		thread = new Thread() {
			public void run() {
				TeamResetReportAction();
			}
		};
		thread.start();
	}

	/**
	 * 领队重置报到 TODO
	 */
	private int result = 0;

	public void TeamResetReportAction() {
		TeamResetReportRequest request = new TeamResetReportRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		TeamResetReportRequestParameter parameter = new TeamResetReportRequestParameter();
		parameter.setAct_id(act_id);
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					TeamResetReportResponse response = new TeamResetReportResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}

					// 处理返回的参数，需要强制类型转换
					TeamResetReportResponsePayload payload = (TeamResetReportResponsePayload) response.getPayload();
					result = payload.getResult();
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
						list.clear();
					}
				} else {
					list.clear();
				}
				list.addAll(listTemp);
				act_join_num_now = 0;
				if (list != null && list.size() != 0) {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getTps_state() == 1) {
							act_join_num_now++;
						}
					}
					if (Loadmore && listadapter != null) {
						listadapter.notifyDataSetChanged();
					} else {
						listadapter3 = null;
						listadapter = new MyListAdpter(mContext, list);
						lv_home.setAdapter(listadapter);
						SwipeMenuCreator creator = new SwipeMenuCreator() {
							@Override
							public void create(SwipeMenu menu) {
								switch (menu.getViewType()) {
								case 0:
									createMenu1(menu);
									break;
								case 1:
									createMenu2(menu);
									break;

								default:
									break;
								}
							}

							private void createMenu1(SwipeMenu menu) {
								SwipeMenuItem tongyi = new SwipeMenuItem(getApplicationContext());
								tongyi.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								tongyi.setWidth(DensityUtils.dp2px(MingdanGuanLiActivity.this, 45));
								tongyi.setTitle("同意");
								tongyi.setTitleSize(18);
								tongyi.setTitleColor(Color.WHITE);
								menu.addMenuItem(tongyi);

								SwipeMenuItem dianhua = new SwipeMenuItem(getApplicationContext());
								dianhua.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								dianhua.setWidth(DensityUtils.dp2px(MingdanGuanLiActivity.this, 45));
								dianhua.setTitle("电话");
								dianhua.setTitleSize(18);
								dianhua.setTitleColor(Color.WHITE);
								menu.addMenuItem(dianhua);

								SwipeMenuItem duanxin = new SwipeMenuItem(getApplicationContext());
								duanxin.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								duanxin.setWidth(DensityUtils.dp2px(MingdanGuanLiActivity.this, 45));
								duanxin.setTitle("短信");
								duanxin.setTitleSize(18);
								duanxin.setTitleColor(Color.WHITE);
								menu.addMenuItem(duanxin);
							}

							private void createMenu2(SwipeMenu menu) {
								SwipeMenuItem tichu = new SwipeMenuItem(getApplicationContext());
								tichu.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								tichu.setWidth(DensityUtils.dp2px(MingdanGuanLiActivity.this, 45));
								tichu.setTitle("踢出");
								tichu.setTitleSize(18);
								tichu.setTitleColor(Color.WHITE);
								menu.addMenuItem(tichu);

								SwipeMenuItem dianhua = new SwipeMenuItem(getApplicationContext());
								dianhua.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								dianhua.setWidth(DensityUtils.dp2px(MingdanGuanLiActivity.this, 45));
								dianhua.setTitle("电话");
								dianhua.setTitleSize(18);
								dianhua.setTitleColor(Color.WHITE);
								menu.addMenuItem(dianhua);

								SwipeMenuItem duanxin = new SwipeMenuItem(getApplicationContext());
								duanxin.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
								duanxin.setWidth(DensityUtils.dp2px(MingdanGuanLiActivity.this, 45));
								duanxin.setTitle("短信");
								duanxin.setTitleSize(18);
								duanxin.setTitleColor(Color.WHITE);
								menu.addMenuItem(duanxin);
							}
						};
						lv_home.setMenuCreator(creator);
					}
				} else {
					if (listadapter3 == null) {
						List<NoDataBean> nodatas = new ArrayList<NoDataBean>();
						NoDataBean nodata = new NoDataBean();
						nodata.setTitle(mContext.getResources().getString(R.string.members));
						nodatas.add(nodata);
						listadapter3 = new CommonAdapter<NoDataBean>(mContext, nodatas, R.layout.item_nodata) {
							@Override
							public void convert(ViewHolderUntil helper, NoDataBean item, int position) {
								helper.setText(R.id.news_title, item.getTitle());
							}
						};
						lv_home.setAdapter(listadapter3);
					}
					listadapter = null;
				}
				top_jihua_num.setText(act_join_num_limit + "人");
				top_jihua_now.setText(act_join_num_now + "人");
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVEROK2:
				act_join_num_now = 0;
				index = 0;
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				sendGetSignUpUserListAsyn();
				break;
			case CommonUtility.SERVEROK3:
				act_join_num_now = 0;
				index = 0;
				if (listadapter != null) {
					count = listadapter.getCount();
				} else {
					count = 10;
				}
				Loadmore = true;
				LoadmoreData = true;
				sendGetSignUpUserListAsyn();
				break;
			case CommonUtility.SERVEROK4:
				if (isshowdialog()) {
					closedialog();
				}
				switch (result) { // result:msg ---> 0：失败
									// ，1：成功，2：活动id为空！，3：只能重置自己组织的活动！4:你没有组织该活动！
				case 0:
					showToast("重置失败");
					break;
				case 1:
					showToast("重置成功");
					break;

				default:
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
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				if (!TextUtils.isEmpty(msgString) && !msgString.contains("操作")) {
					showToast(msgString);
					msgString = null;
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			default:
				break;
			}
		};
	};

	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private List<GetSignUpUserListUserlistItem> mList = null;

		public MyListAdpter(Context context, List<GetSignUpUserListUserlistItem> list) {
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

		@Override
		public int getItemViewType(int position) {
			int item = 0;
			switch (mList.get(position).getTps_state()) {
			case 0:
				item = 0;
				break;
			case 1:
				item = 1;
				break;

			default:
				break;
			}
			return item;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_mingdanguanli_list, null);
				holder = new ViewHolder();
				holder.icon = (CubeImageView) convertView.findViewById(R.id.icon);
				holder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
				holder.tv_user_time = (TextView) convertView.findViewById(R.id.tv_user_time);
				holder.tv_duiyuan_state = (TextView) convertView.findViewById(R.id.tv_duiyuan_state);
				holder.duiyuan_dengji = (TextView) convertView.findViewById(R.id.duiyuan_dengji);
				holder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.icon.loadImage(imageLoader, mList.get(position).getU_head());
			holder.tv_user_name.setText(mList.get(position).getU_nickname());
			holder.tv_user_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mList.get(position).getApply_time()));
			if (TextUtils.equals(mList.get(position).getSex(), "男")) {
				holder.tv_user_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.haoyou_nan, 0);
			} else if (TextUtils.equals(mList.get(position).getSex(), "女")) {
				holder.tv_user_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.haoyou_nv, 0);
			}
			int fraction = mList.get(position).getU_fraction();
			if (fraction >= 0 && fraction < 20) {
				holder.duiyuan_dengji.setText("新手");
			} else if (fraction >= 20 && fraction < 100) {
				holder.duiyuan_dengji.setText("初级队员");
			} else if (fraction >= 100 && fraction < 200) {
				holder.duiyuan_dengji.setText("中级队员");
			} else if (fraction >= 200 && fraction < 500) {
				holder.duiyuan_dengji.setText("高级队员");
			} else if (fraction >= 500) {
				holder.duiyuan_dengji.setText("大师");
			}
			switch (mList.get(position).getTps_state()) {
			case 0:
				holder.tv_duiyuan_state.setText("参队");
				break;
			case 1:
				holder.tv_duiyuan_state.setText("队员");
				break;

			default:
				break;
			}
			holder.ratingbar.setRating(mList.get(position).getU_star() != null ? mList.get(position).getU_star() : 5);
			return convertView;
		}

		private class ViewHolder {
			private CubeImageView icon;
			// 昵称，用户名，参队状态
			private TextView tv_user_name, tv_user_time, tv_duiyuan_state, duiyuan_dengji;
			// 钻级，星级
			private RatingBar ratingbar;
		}
	}

}
