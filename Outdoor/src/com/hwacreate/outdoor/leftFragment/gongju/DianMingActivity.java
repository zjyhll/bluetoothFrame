package com.hwacreate.outdoor.leftFragment.gongju;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.afinal.simplecache.ACache;

import android.R.integer;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.bluetooth.le.MipcaActivityCapture;
import com.hwacreate.outdoor.bluetooth.le.Utile;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandAddDevice;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandException;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandSendMemberPrepare;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandUtility;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataGroupInfoAllBtApp;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberDeviceInfo;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberInfo;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberInfoApp;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberInfoBtApp;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.ContactMyGuardianAcitivty;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.bean.SignUpUser;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DataCleanManager;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.SPUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CustomDialog;
import com.hwacreate.outdoor.view.MyProgressBar;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListRequest;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListRequestParameter;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListResponse;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListResponsePayload;
import com.keyhua.outdoor.protocol.GetSignUpUserListAction.GetSignUpUserListUserlistItem;
import com.keyhua.outdoor.protocol.SetLeaderAndUserDeviceAction.SetLeaderAndUserDeviceIdsItem;
import com.keyhua.outdoor.protocol.SetLeaderAndUserDeviceAction.SetLeaderAndUserDeviceRequest;
import com.keyhua.outdoor.protocol.SetLeaderAndUserDeviceAction.SetLeaderAndUserDeviceRequestParameter;
import com.keyhua.outdoor.protocol.SetLeaderAndUserDeviceAction.SetLeaderAndUserDeviceResponse;
import com.keyhua.outdoor.protocol.SetLeaderAndUserDeviceAction.SetLeaderAndUserDeviceResponsePayload;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportRequest;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportRequestParameter;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportResponse;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportResponsePayload;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportRequest;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportRequestParameter;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportResponse;
import com.keyhua.outdoor.protocol.TeamResetReportAction.TeamResetReportResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class DianMingActivity extends BaseActivity {
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	//
	private ListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;
	// pop
	private View parentView = null;
	private PopupWindow popContact = null;
	private LinearLayout ll_popup = null;
	private RelativeLayout parent = null;// 半透明背景色
	private Button btn_pop_ok = null;// 同步编号信息到领队宝
	private Button btn_pop_network = null;// 同步编号信息到设备
	private MyProgressBar btn_pop_percent = null;// 同步百分比
	private Button btn_pop_cancle = null;// 取消
	//
	private int i = 0;
	// 蓝牙
	private String mDeviceName = null;// 蓝牙名
	private String SNStr = null; // SN号
	private String mDeviceAddress = null;// 蓝牙地址
	private TextView tv_name = null; // 领队设备名
	private TextView tv_num = null; // 当前队员数量
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private List<HuoDongXiangQingLeader> huoDongXiangQingLeaderList = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;
	// 队员
	private SignUpUser signUpUserGet = null;
	private List<SignUpUser> signUpUserListGet = null;
	private BaseDao<SignUpUser> signUpUserDaoGet = null;
	// 队员
	private SignUpUser signUpUserSet = null;
	private List<SignUpUser> signUpUserListSet = null;
	private BaseDao<SignUpUser> signUpUserDaoSet = null;

	// 添加设备或同行宝
	// private String userid = null;
	// 用于数据的缓存
	private ACache mCache = null;
	// 判断是添加设备入组还是领队报道
	private boolean isReport = false;
	private String U_id = null;

	/**
	 * 数据的本地化初始化
	 */
	public void initDao() {
		// 数据库操作
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(
				huoDongXiangQingLeader, DianMingActivity.this);
		huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
		// 获取队员数据库
		signUpUserGet = new SignUpUser();
		signUpUserDaoGet = new BaseDao<SignUpUser>(signUpUserGet,
				DianMingActivity.this);
		// 设置队员数据库
		signUpUserSet = new SignUpUser();
		signUpUserDaoSet = new BaseDao<SignUpUser>(signUpUserSet,
				DianMingActivity.this);
	}

	private void initPopwindow() {
		popContact = new PopupWindow(DianMingActivity.this);
		View view = getLayoutInflater().inflate(R.layout.pop_tianjiatongxing,
				null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		popContact.setWidth(LayoutParams.MATCH_PARENT);
		popContact.setHeight(LayoutParams.WRAP_CONTENT);
		popContact.setBackgroundDrawable(new BitmapDrawable());
		popContact.setFocusable(true);
		popContact.setOutsideTouchable(true);
		popContact.setContentView(view);
		// pop中的三个按钮
		// 此方法可防止5.0版本下面的banner挡住PopWindows
		popContact
				.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		parent = (RelativeLayout) view.findViewById(R.id.parent);
		btn_pop_ok = (Button) view.findViewById(R.id.btn_pop_ok);
		btn_pop_network = (Button) view.findViewById(R.id.btn_pop_network);
		btn_pop_percent = (MyProgressBar) view
				.findViewById(R.id.btn_pop_percent);
		btn_pop_cancle = (Button) view.findViewById(R.id.btn_pop_cancle);
		btn_pop_ok.setText("同步编号");
		btn_pop_ok.setOnClickListener(this);
		btn_pop_network.setOnClickListener(this);
		btn_pop_cancle.setOnClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_dian_ming,
				null);
		setContentView(parentView);
		mCache = ACache.get(DianMingActivity.this);
		init();
	}

	/**
	 * 取消关联
	 */
	public void cancleContact() {
		if (isReceiver) {
			unregisterReceiver(mGattUpdateReceiver);
			isReceiver = false;
			unbindService(mServiceConnection);
			if (mBluetoothLeService != null) {
				mBluetoothLeService.disconnect();
				mBluetoothLeService.close();
				mBluetoothLeService = null;
			}
		}

		// finish();
	}

	/**
	 * @param strWrite
	 *            写的特性
	 * @param strRead
	 *            读的特性
	 * @param strSend
	 *            发送的字符串
	 */
	private void sendData(String strWrite, String strRead, byte[] strSend) {
		// 对应很多不同的读写特性
		if (tempByte != null) {
			tempByte.clear();
		} else {
			tempByte = new ArrayList<byte[]>();
		}
		setCharacteristic(strWrite, strRead);

		if (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress())
				&& mConnected) {
			bleSendDataTongXingBao(strSend);
		} else {
			showToast("设备已断开连接！");
			cancleContact();
		}
	}

	/** Dialog */
	public void showAlertDialog(Context context, String title,
			final int position) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				sendTeamResetReportAsyn();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			finish();
			break;
		case R.id.top_tv_right:// 选择
			break;
		case R.id.radiobutton_select_one:// 全部清空
			showAlertDialog(DianMingActivity.this,
					"是否重置报到?                   ", -1);
			break;
		case R.id.radiobutton_select_two:// 队员报道
			isReport = true;
			// showToast("二维码扫描");
			Intent intent = new Intent();
			intent.setClass(DianMingActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, CommonUtility.SCANNIN_GREQUEST_CODE);
			break;
		case R.id.radiobutton_select_three:// 编号同步
			ll_popup.startAnimation(AnimationUtils.loadAnimation(
					DianMingActivity.this, R.anim.activity_translate_in));
			popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
			break;
		case R.id.btn_pop_ok:// 同步编号信息到所有队员

			// if (NetUtil.isNetworkAvailable(DianMingActivity.this)) {// 有网
			// ids = new ArrayList<SetLeaderAndUserDeviceIdsItem>();
			// for (int i = 0; i < signUpUserListGet.size(); i++) {
			// andUserDeviceIdsItem = new SetLeaderAndUserDeviceIdsItem();
			// andUserDeviceIdsItem.setAct_id(signUpUserListGet.get(i)
			// .getAct_id());
			// andUserDeviceIdsItem.setU_id(signUpUserListGet.get(i)
			// .getU_id());
			// andUserDeviceIdsItem.setUser_device_id(signUpUserListGet
			// .get(i).getStrDeviceSN());
			// ids.add(andUserDeviceIdsItem);
			// }
			// sendsetLeaderAndUserDeviceAsyn();
			// btn_pop_cancle.setText("取消");
			// } else {// 无网
			// showdialog();
			if (mConnected) {// 连接上了才能同步到设备
				synchronizationInformationTable();
				btn_pop_cancle.setText("正在同步中");
			} else {
				showToast("未连接设备");
				popContact.dismiss();
			}
			// }

			break;
		case R.id.btn_pop_network:// 拿到队员及领队信息上传到网络

			break;
		case R.id.btn_pop_cancle:
			popContact.dismiss();
			break;
		default:
			break;
		}
	}

	private ArrayList<SetLeaderAndUserDeviceIdsItem> ids = null;
	private SetLeaderAndUserDeviceIdsItem andUserDeviceIdsItem = null;

	// 需要同步到服务器的数据
	private String contactBleDuiYuanAddress = null;

	@Override
	protected void onStart() {
		//
		super.onStart();
		if (!TextUtils.isEmpty((String) SPUtils.get(DianMingActivity.this,
				"input_ctv", ""))) {
			userid = (String) SPUtils.get(DianMingActivity.this, "input_ctv",
					"");

			sendAsyn();
		}
		// 添加用户时的操作
		contactBleDuiYuanAddress = (String) App.getInstance()
				.getContactBleDuiYuanAddress();

		if (!TextUtils.isEmpty(contactBleDuiYuanAddress)) {// 添加设备入组，刷新adapter
															// TODO
															// U_id
			// 添加到蓝牙设备中
			addIn(contactBleDuiYuanAddress);
		}
		// 置空
		App.getInstance().setContactBleDuiYuanAddress("");
		SPUtils.put(DianMingActivity.this, "input_ctv", "");
		SPUtils.put(DianMingActivity.this, "U_id", "");

	}

	@Override
	protected void onInitData() {//

		listTemp = new ArrayList<GetSignUpUserListUserlistItem>();
		initDao();
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_num = (TextView) findViewById(R.id.tv_num);
		initHeaderOther();
		initFooterOther("重置报到", "队员报到", " 编号同步");
		rg_button.setVisibility(View.VISIBLE);
		radiobutton_select_one.setVisibility(View.VISIBLE);
		radiobutton_select_two.setVisibility(View.VISIBLE);
		radiobutton_select_three.setVisibility(View.VISIBLE);
		initPopwindow();
	}

	// 刷新end------------------------------------------------------------------
	@Override
	protected void onResload() {//
		mDeviceName = App.getInstance().getBleDuiYuanName();
		mDeviceAddress = App.getInstance().getBleDuiYuanAddress();
		if (!TextUtils.isEmpty(mDeviceName)) {
			SNStr = mDeviceName.substring(3);
			signUpUserDaoSet.updateUSN(SNStr, huoDongXiangQingLeaderList.get(0)
					.getLeader_id());
		}

		//
		top_tv_title.setText("同行队伍");
		top_tv_right.setText("选择");
		top_tv_right.setVisibility(View.GONE);
		lv_home = (ListView) findViewById(R.id.lv_home);
		listadapter = new MyListAdpter(DianMingActivity.this);
		lv_home.setAdapter(listadapter);
		// 设备名必须不为空才能跳入该界面，并且连接未断开
		if (!TextUtils.isEmpty(mDeviceAddress)) {
			initBluetooth();
		} else {
			// showToast("设备已断开连接");
			App.getInstance().setBleDuiYuanAddress("");
			App.getInstance().setBleDuiYuanName("");
			// finish();
		}
		tv_name.setText(mDeviceName);
		refreshAndLoadMore();

	}

	private void refreshAndLoadMore() {
		// 上下刷新START--------------------------------------------------------------------
		// 获取装载VIew的容器
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.load_more_list_view_ptr_frame);
		// 获取view的引用
		loadMoreListViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
		// 使用默认样式
		loadMoreListViewContainer.useDefaultHeader();
		// 加载更多数据，当列表滑动到最底部的时候，触发加载更多操作，
		// 这是需要从网络加载数据，或者是从数据库去读取数据
		// 给View 设置加载更多的Handler 去异步加载View需要显示的数据和VIew
		loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
			// loadMoreListViewContainer调用onLoadMore传入loadMoreListViewContainer自身对象
			@Override
			public void onLoadMore(LoadMoreContainer loadMoreContainer) {
				if (NetUtil.isNetworkAvailable(DianMingActivity.this)) {// 有网
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
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				// here check list view, not content.
				return PtrDefaultHandler.checkContentCanBePulledDown(frame,
						lv_home, header);
			}

			// 开始刷新容器开头
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				if (NetUtil.isNetworkAvailable(DianMingActivity.this)) {// 有网
					// 获取报名列表
					sendGetSignUpUserListAsyn();
					mHandler.sendEmptyMessage(CommonUtility.ISREFRESH);
				} else {// 无网
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
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

	private final int PROGRESS = 4;
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
			case PROGRESS:
				// btn_pop_percent.setProgress(i);
				// if (i == 100) {
				btn_pop_cancle.setText("成功");
				popContact.dismiss();
				// }
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void setMyViewClick() {

		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
	}

	/**
	 * @author 曾金叶
	 * @2015-8-6 @上午9:58:49
	 * @category adapter ,写在本activity，不用分出来
	 */
	public class MyListAdpter extends BaseAdapter {
		private Context context = null;

		public MyListAdpter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return signUpUserListGet != null ? signUpUserListGet.size() : 0;
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_tongxingduiwu, null);
				holder = new ViewHolder();
				holder.id_icon = (CircleImageView) convertView
						.findViewById(R.id.id_icon);
				holder.tv_num = (TextView) convertView
						.findViewById(R.id.tv_num);
				holder.tv_bianhao = (TextView) convertView
						.findViewById(R.id.tv_bianhao);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_num.setText(position + 1 + "");
			// holder.id_icon.
			mImageLoader.displayImage(
					"file:///" + signUpUserListGet.get(position).getU_head(),
					holder.id_icon);
			holder.tv_name.setText(signUpUserListGet.get(position)
					.getU_nickname());
			if (signUpUserListGet.get(position).getIs_report() == 1) {// 已报到
				holder.tv_name.setTextColor(getResources().getColor(
						R.color.app_green));
			} else {// 未报到
				holder.tv_name.setTextColor(getResources().getColor(
						R.color.content));
			}
			// 如果没有关联同行宝
			if (TextUtils.isEmpty(signUpUserListGet.get(position)
					.getStrDeviceSN())) {
				holder.tv_bianhao.setText("SN:点击添加入组");
			} else {
				holder.tv_bianhao.setText("SN:"
						+ signUpUserListGet.get(position).getStrDeviceSN());
			}
			holder.tv_bianhao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 获取到输入或者扫描的值时用这个值来更新对应数据库
					U_id = signUpUserListGet.get(position).getU_id();
					SPUtils.put(DianMingActivity.this, "dianmingU_id", U_id);
					isReport = false;
					Bundle bundle = new Bundle();
					bundle.putBoolean("guanlianshebei", true);
					openActivity(ContactMyGuardianAcitivty.class, bundle);
				}
			});
			holder.id_icon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!TextUtils.isEmpty(contactBleDuiYuanAddress)) {
						// U_id = (String) SPUtils.get(DianMingActivity.this,
						// "dianmingU_id", "");
						U_id = signUpUserListGet.get(position).getU_id();
						signUpUserDaoSet.updateUSN(contactBleDuiYuanAddress,
								U_id);
						contactBleDuiYuanAddress = null;
						// 再设置适配器
						handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
					} else {
						showToast("请先添加设备");
					}

				}
			});
			convertView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					showAlertDialogIsClean(signUpUserListGet.get(position).getU_id());
					return false;
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private CircleImageView id_icon;// 右边的icon
			private TextView tv_num, tv_bianhao, tv_name;// 排名，编号，名字
		}
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

	/**
	 * 取得活动报名列表
	 */
	public void GetSignUpUserListAction() {
		GetSignUpUserListRequest request = new GetSignUpUserListRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetSignUpUserListRequestParameter parameter = new GetSignUpUserListRequestParameter();
		parameter.setAct_id(huoDongXiangQingLeaderList.get(0).getAct_id());
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender
					.send(new JSONObject(request.toJSONString()));
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
					GetSignUpUserListResponsePayload payload = (GetSignUpUserListResponsePayload) response
							.getPayload();
					listTemp = payload.getUserlist();
					if (listTemp.size() != 0) {
						handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
					} else {
						handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
					}

				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist
							.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
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

	private String ordercode = null;
	private String userid = null;// 用户id 二维码扫描获取

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CommonUtility.SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				/** 跳到相应的界面中 */
				ordercode = data.getStringExtra("result");
				if (!TextUtils.isEmpty(ordercode)) {
					// 拿到值后展示在对应的队员设备号中

					try {
						JSONObject jsonObject = new JSONObject(ordercode);
						userid = jsonObject.getString("userid");
					} catch (JSONException e) {
						//
						e.printStackTrace();
					}
					if (isReport) {// 队员报道
						sendAsyn();
					} else {// 添加设备入组
						showToast("添加设备入组");
					}
				} else {
					showToast("二维码不正确，请重新扫描");
				}
				System.out.println("ordercode:" + ordercode);
			}
			break;
		}
	}

	/** 队员报到 ，领队扫描，不管是队员主动网络报到还是领队二维码扫描报到，都必须要在有网的情况下进行 */

	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	private Integer result = null;
	private String msgstr = null;

	public void Action() { //
		TeamReportRequest request = new TeamReportRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		TeamReportRequestParameter parameter = new TeamReportRequestParameter();
		parameter.setAct_id(huoDongXiangQingLeaderList.get(0).getAct_id());
		parameter.setUser_id(userid);
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
					TeamReportResponse response = new TeamReportResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					TeamReportResponsePayload payload = (TeamReportResponsePayload) response
							.getPayload();
					result = payload.getResult();
					msgstr = payload.getMsg();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist
							.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
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
	 * 领队重置报到
	 */
	// private int result = 0;

	public void TeamResetReportAction() {
		TeamResetReportRequest request = new TeamResetReportRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		TeamResetReportRequestParameter parameter = new TeamResetReportRequestParameter();
		parameter.setAct_id(huoDongXiangQingLeaderList.get(0).getAct_id());
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender
					.send(new JSONObject(request.toJSONString()));
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
					TeamResetReportResponsePayload payload = (TeamResetReportResponsePayload) response
							.getPayload();
					result = payload.getResult();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK4);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist
							.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
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

	public void sendsetLeaderAndUserDeviceAsyn() {
		thread = new Thread() {
			public void run() {
				setLeaderAndUserDeviceAction();
			}
		};
		thread.start();
	}

	/**
	 * 领队网络同步编号
	 */
	// private int result = 0;
	private String setLeaderAndUserDeviceActionStr = null;

	public void setLeaderAndUserDeviceAction() {
		SetLeaderAndUserDeviceRequest request = new SetLeaderAndUserDeviceRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		SetLeaderAndUserDeviceRequestParameter parameter = new SetLeaderAndUserDeviceRequestParameter();
		parameter.setIds(ids);
		parameter.setIs_leader(1);
		parameter.setLeader_id(App.getInstance().getUserid());
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender
					.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					SetLeaderAndUserDeviceResponse response = new SetLeaderAndUserDeviceResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}

					// 处理返回的参数，需要强制类型转换
					SetLeaderAndUserDeviceResponsePayload payload = (SetLeaderAndUserDeviceResponsePayload) response
							.getPayload();
					// 成功设置的领队个数
					payload.getLeader_count();
					// 成功设置的队员个数
					payload.getUser_count();
					result = payload.getState();
					setLeaderAndUserDeviceActionStr = payload.getMsg();

					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK5);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist
							.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
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

	// new一个list在存放设备号
	private Handler handlerlist = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// 设置领队宝模式

				// errorNumList.g
				String str = "";
				for (int i = 0; i < errorNumList.size(); i++) {
					str += errorNumList.get(i) + "号";
				}
				if (TextUtils.isEmpty(str)) {
					showToast("所有队员信息同步成功！");
				} else {
					showAlertDialog("队员：" + str + "未同步成功                   ");
				}
				// 发送之后判断是否成功
				mHandler.sendEmptyMessage(PROGRESS);
				break;
			case 2:// 添加入组 TODO
				showToast("点击队员头像关联");
				break;
			case 4:// 添加入组
				showToast("添加入组失败");
				break;
			case CommonUtility.SERVEROK1:
				if (signUpUserDaoGet.queryAll() != null
						&& signUpUserDaoGet.queryAll().size() != listTemp
								.size() + 1) {// 领队的为另外加的,理论上只保存一次
					signUpUserDaoSet.deleteAll();

					new Thread(new Runnable() {

						@Override
						public void run() {
							// 领队的
							signUpUserSet = new SignUpUser();
							signUpUserSet
									.setU_id(App.getInstance().getUserid());
							signUpUserSet.setU_nickname(App.getInstance()
									.getNickname());
							signUpUserSet.setIs_report(1);
							signUpUserSet.setTps_state(1);
							signUpUserSet.setAct_id(App.getInstance()
									.getLeaderHuoDongId());
							signUpUserSet.setU_head(ImageControl
									.saveImageToGallery(DianMingActivity.this,
											ImageControl
													.getHttpBitmap(App
															.getInstance()
															.getHeadurl()), 4));
							// SNStr
							signUpUserSet.setStrDeviceSN(SNStr);
							System.out
									.println("SNStr:---------------------------:"
											+ SNStr);
							signUpUserDaoSet.add(signUpUserSet);
							// 其他队员的
							for (int i = 0; i < listTemp.size(); i++) {
								final int temp = i;
								signUpUserSet = new SignUpUser();
								signUpUserSet
										.setU_id(listTemp.get(i).getU_id());
								signUpUserSet.setU_nickname(listTemp.get(i)
										.getU_nickname());
								signUpUserSet.setIs_report(listTemp.get(i)
										.getIs_report());
								signUpUserSet.setTps_state(listTemp.get(i)
										.getTps_state());
								signUpUserSet.setAct_id(listTemp.get(i)
										.getAct_id());
								signUpUserSet.setU_head(ImageControl
										.saveImageToGallery(
												DianMingActivity.this,
												ImageControl
														.getHttpBitmap(listTemp
																.get(temp)
																.getU_head()),
												4));
								signUpUserDaoSet.add(signUpUserSet);

							}
							//
							handlerlist
									.sendEmptyMessage(CommonUtility.SERVEROK3);
						}
					}).start();
				} else {// 更新指定队员的报道参数
					for (int i = 0; i < listTemp.size(); i++) {
						signUpUserDaoSet.updateUState(listTemp.get(i)
								.getIs_report(), listTemp.get(i).getU_id());
					}
					//
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
				}

				break;
			case CommonUtility.SERVEROK2:// 二维码结果
				switch (result) {
				case 0:// 失败msgstr
					showToast(msgstr);
					break;
				case 1:// 成功
					showToast(msgstr);
					break;
				default:
					break;
				}
				if (NetUtil.isNetworkAvailable(DianMingActivity.this)) {// 有网
					sendGetSignUpUserListAsyn();
				} else {// 没网络时直接从数据库取
					getLocalData();
				}
				// 无论成功与否都要清空
				SPUtils.put(DianMingActivity.this, "input_ctv", "");

				break;
			case CommonUtility.SERVEROK3:// 刷新listview
				getLocalData();
				break;
			case CommonUtility.SERVEROK4:// 重置报到
				switch (result) { // result:msg ---> 0：失败
				// ，1：成功，2：活动id为空！，3：只能重置自己组织的活动！4:你没有组织该活动！
				case 0:
					showToast("重置失败");
					break;
				case 1:

					if (NetUtil.isNetworkAvailable(DianMingActivity.this)) {// 有网
						sendGetSignUpUserListAsyn();
						showToast("重置成功");
					} else {// 没网络时直接从数据库取
						showToast("请检查网络连接");
					}
					break;

				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK5:// 同步编号结果
				if (result == 1) {
					btn_pop_cancle.setText(setLeaderAndUserDeviceActionStr);
				} else {
					btn_pop_cancle.setText(setLeaderAndUserDeviceActionStr);
				}
				if (mConnected) {// 连接上了才能同步到设备
					synchronizationInformationTable();
					btn_pop_cancle.setText("取消");
				} else {
					showToast("未连接设备");
					popContact.dismiss();
				}
				showToast(setLeaderAndUserDeviceActionStr);
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				break;
			case CommonUtility.SERVERERROR:
				getLocalData();
				break;
			case CommonUtility.KONG:
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 从本地数据库拿取数据
	 */
	private void getLocalData() {
		//
		// 拿取数据
		signUpUserListGet = signUpUserDaoGet.queryAll();
		// 再设置适配器
		listadapter.notifyDataSetChanged();
		tv_num.setText(signUpUserListGet.size() + "人");
	}

	// ------------------------------------from------------------------
	// 同行卫士或北斗设备的UUID，根据具体的动作动态更改
	private String UUID_KEY_DATA_WRITE = null;
	private String UUID_KEY_DATA_READ = null;
	public boolean mConnected = false;
	// 服务
	public BluetoothLeService mBluetoothLeService;
	// 读写特性
	private BluetoothGattCharacteristic mNotifyCharacteristicWrite;
	private BluetoothGattCharacteristic mNotifyCharacteristicRead;

	/**
	 * @param mDeviceAddress
	 *            蓝牙设备地址
	 */
	public void setCharacteristic(String uUID_KEY_DATA_WRITE,
			String uUID_KEY_DATA_READ) {
		UUID_KEY_DATA_WRITE = uUID_KEY_DATA_WRITE;
		UUID_KEY_DATA_READ = uUID_KEY_DATA_READ;
		if (mBluetoothLeService != null) {
			displayGattServices(mBluetoothLeService.getSupportedGattServices());
		}
	}

	/**
	 * @param mDeviceAddress
	 *            蓝牙设备地址
	 */
	public void setCharacteristic(String mDeviceAddress) {
		this.mDeviceAddress = mDeviceAddress;
	}

	/**
	 * @param gattServices
	 *            找到收与发数据的特性
	 */
	private void displayGattServices(List<BluetoothGattService> gattServices) {
		if (gattServices == null)
			return;
		// -----Service的字段信息-----//
		for (BluetoothGattService gattService : gattServices) {
			// -----Characteristics的字段信息-----//
			List<BluetoothGattCharacteristic> gattCharacteristics = gattService
					.getCharacteristics();
			// 获取接收数据的特性
			for (final BluetoothGattCharacteristic gattCharacteristic2 : gattCharacteristics) {
				if (gattCharacteristic2.getUuid().toString()
						.equals(UUID_KEY_DATA_READ)) {
					mNotifyCharacteristicRead = gattCharacteristic2;
					// loghxd.e(gattCharacteristic2.getUuid().toString());
				}
			}
			// 获取写入蓝牙设备的特性
			for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
				if (gattCharacteristic.getUuid().toString()
						.equals(UUID_KEY_DATA_WRITE)) {
					mNotifyCharacteristicWrite = gattCharacteristic;
					// loghxd.e(gattCharacteristic.getUuid().toString());
				}
			}
		}
	}

	public final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize()) {
				// Log.e(TAG, "Unable to initialize Bluetooth");
				// finish();
			}
			if (!TextUtils.isEmpty(mDeviceAddress)) {
				mBluetoothLeService.connect(mDeviceAddress);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService = null;
		}
	};

	/** 发送大于20字节或小于20字节数据方法 */
	public void bleSendDataTongXingBao(final byte[] writeByte) {
		// byte[] writeByte = null;
		// try {
		// writeByte = str.getBytes("gb2312");
		// } catch (UnsupportedEncodingException e) {
		// //
		// e.printStackTrace();
		// }
		if (mBluetoothLeService != null) {
			/** 返回数据的特性监听，必须放在往设备写数据之前 */
			if (mNotifyCharacteristicRead != null) {
				mBluetoothLeService.setCharacteristicNotification(
						mNotifyCharacteristicRead, true);
			}
			/** 发送数据命令 */
			new Handler().postDelayed(new Runnable() {
				public void run() {
					if (mBluetoothLeService != null) {
						mBluetoothLeService.write(mNotifyCharacteristicWrite,
								writeByte);
					}
				}
			}, 500);// 需要延时操作

			/** 接收数据命令 测试用 */
			// new Handler().postDelayed(new Runnable() {
			// public void run() {
			// byte[] readByte = Utile.hexStr2Bytes("36363636");
			// mBluetoothLeService.write(mNotifyCharacteristicWrite,
			// readByte);
			// }
			// }, 500);// 需要延时操作
		}

	}

	private IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED_AGIAN);
		intentFilter.addAction(BluetoothLeService.SENDOVER);
		return intentFilter;
	}

	// ------------------------------------end------------------------
	/** 准备同步队员信息表 */
	HwtxCommandSendMemberPrepare commandSynchronizationInformationTable = null;
	private byte[] tagSynchronizationInformationTable = null;

	// private byte[] informationTableByte = null;

	public void synchronizationInformationTable() {
		// 需要发送的数据
		// informationTableByte = InformationTable();
		// if (informationTableByte != null) {
		if (commandSynchronizationInformationTable == null) {
			commandSynchronizationInformationTable = new HwtxCommandSendMemberPrepare();
		}
		if (tagSynchronizationInformationTable == null) {
			tagSynchronizationInformationTable = commandSynchronizationInformationTable
					.getCommandTagRaw();
		}

		try {
			commandSynchronizationInformationTable
					.setMemberDataLength(InformationTableSise());
		} catch (HwtxCommandException e) {
			//
			e.printStackTrace();
		}
		// 正在同步数据
		Utile.needContinue = true;
		sendData(
				commandSynchronizationInformationTable
						.getBluetoothPropertyUuidSend(),
				commandSynchronizationInformationTable
						.getBluetoothPropertyUuidRead(),
				commandSynchronizationInformationTable.toBytes());
		//
		// final byte[] informationTableByteTemp = InformationTable();
		// 告诉设备需要传的长度之后，再讲设备参数表发送过去,延时几秒
		// new Handler().postDelayed(new Runnable() {//
		//
		// @Override
		// public void run() {
		// commandSynchronizationInformationTable = new
		// HwtxCommandSendMemberPrepare();
		// tagSynchronizationInformationTable =
		// commandSynchronizationInformationTable
		// .getCommandTagRaw();
		// sendData(commandSynchronizationInformationTable
		// .getBluetoothPropertyUuidSend(),
		// commandSynchronizationInformationTable
		// .getBluetoothPropertyUuidRead(),
		// InformationTable());
		// // 发送之后判断是否成功
		// mHandler.sendEmptyMessage(PROGRESS);
		// }
		// }, 1000);
	}

	// }

	// 5循环加入以上多个队员
	private HwtxDataGroupInfoAllBtApp groupInfoAllBtApp1 = null;

	/**
	 * 算出所有数据大小
	 */
	public Short InformationTableAll() {
		byte[] temp = InformationTable2();
		System.out.println("InformationTableAll:\n"
				+ HwtxCommandUtility.bytesToHexText(temp));
		// 本数据结构，除了wCrcGrpInfoAllBtApp之外所有数据的CRC值。(CRC计算：包括从dwSizeInByte开始，到最后一个成员的HWTX_MemberInfo_BtAPP内容）
		return HwtxCommandUtility.crc16(HwtxCommandUtility
				.extractBytesFromBytes(temp, 2, temp.length - 2),
				temp.length - 2);
	}

	/** 队员详细信息表 应该是该方法运行了多次导致数据变多 */
	public byte[] InformationTable() {
		// 5循环加入以上多个队员
		groupInfoAllBtApp1 = new HwtxDataGroupInfoAllBtApp();
		// 整个有效表的实际总大小：包括wCrcGrpInfoAllBtApp,dwSizeInByte~到最后一个成员的HWTX_MemberInfo_BtAPP内容。
		// InformationTableAll();
		short tempShort = InformationTableAll();
		groupInfoAllBtApp1.setCrcGrpInfoAllBtAppShort(tempShort);
		// groupInfoAllBtApp1.setCrcGrpInfoAllBtAppShort((short) 0x1234);
		groupInfoAllBtApp1.setSizeInByteShort((short) InformationTableSise());
		// 本结构的数据类型. 应该是: HT_TYPE_GIA
		groupInfoAllBtApp1.setbDataType((byte) 0x63);
		// 时间戳。由领队APP每次修改本表时，写入当前系统时间。每次修改表元素，都需要更新为不同的时间戳。----重要，底层FW根据它来判断是否更新了新元素。
		// int time = (int) (System.currentTimeMillis());
		groupInfoAllBtApp1.setRtcTimeInteger(time);
		// 小组领队宝同行宝总数。（设备总数：包含有领队宝设备、同行宝设备的所有成员。不含无设备的成员或未参加的成员）
		int grpCountDevice = 0;// 直接从数据库查询当前关联设备号个数
		for (int i = 0; i < signUpUserListGet.size(); i++) {
			if (!TextUtils.isEmpty(signUpUserListGet.get(i).getStrDeviceSN())) {
				grpCountDevice++;
			}
		}
		groupInfoAllBtApp1.setGrpCountDeviceByte(Utile
				.int2OneByte(grpCountDevice));
		// 实际参加的总人数:含有设备的，无设备；即报道人数
		int grpCountAttend = 0;
		for (int i = 0; i < signUpUserListGet.size(); i++) {
			if (signUpUserListGet.get(i).getIs_report() == 1
					|| !TextUtils.isEmpty(signUpUserListGet.get(i)
							.getStrDeviceSN())) {
				grpCountAttend++;
			}
		}
		groupInfoAllBtApp1.setGrpCountAttendByte(Utile
				.int2OneByte(grpCountAttend));
		// 报名总人数。小组总人数：包括有领队宝设备的、有同行宝设备的、无同行宝但参加了本次活动的队员、报名但未参加的队员。
		int grpCountTotal = signUpUserListGet.size();
		groupInfoAllBtApp1.setGrpCountTotalByte(Utile
				.int2OneByte(grpCountTotal));
		// for循环遍历[1234]，加入5中
		for (int i = 0; i < signUpUserListGet.size(); i++) {
			// if (signUpUserListGet.get(i).getIs_report() == 1
			// || !TextUtils.isEmpty(signUpUserListGet.get(i)
			// .getStrDeviceSN())) {
			/** 组内编号 */
			int devNumInGrou = i + 1;
			/** 设备编号 */
			String deviceSnString = signUpUserListGet.get(i).getStrDeviceSN();
			/** 昵称，最大10字节字母或5个汉字 */
			String nickNameString = signUpUserListGet.get(i).getU_nickname();
			/** 本设备是否领队宝 */
			boolean tDeviceIsLdb = false;
			if (i == 0) {// 第一个为领队
				tDeviceIsLdb = true;
			} else {
				tDeviceIsLdb = false;
			}

			/** 是否有同行宝设备 */
			boolean deviceReady = false;
			if (!TextUtils.isEmpty(signUpUserListGet.get(i).getStrDeviceSN())) {// SN不为空说明有设备
				deviceReady = true;
			} else {
				deviceReady = false;
			}
			/** 否参加了这次活动 */
			boolean isAttend = true;
			/** 是否启用APP图标 */
			boolean isUseAP = true;
			// 1
			HwtxDataMemberDeviceInfo deviceInfo1 = new HwtxDataMemberDeviceInfo();
			// 2
			HwtxDataMemberInfo memberInfo1 = new HwtxDataMemberInfo();
			try {
				if (devNumInGrou != 0) {
					// 该设备队员的组内编号，范围[1,30]
					deviceInfo1.setDevNumInGroup(devNumInGrou);
				}
				// 本设备是否领队宝（APP确保一个队伍只有一个领队）,0-TXB同行宝（队员）,1-LDB领队宝(领队)；
				deviceInfo1.setDeviceIsLdb(tDeviceIsLdb);
				// 是否有同行宝设备。由领队APP设定：根据strDeviceSN是否为非零来判断，0-无同行宝；1-有同行宝
				deviceInfo1.setDeviceReady(deviceReady);
				memberInfo1.setDevInfo(deviceInfo1);
				// 唯一的设备SN串号，固定6字节的数字，由设备量产时写入。有非0值，则表示该队员有同行宝；如果为0值，则表示该队员无同行宝硬件。
				if (!TextUtils.isEmpty(deviceSnString)) {
					memberInfo1.setDeviceSnString(deviceSnString);
				}

			} catch (HwtxCommandException e) {
				e.printStackTrace();
			}
			// 3
			HwtxDataMemberInfoApp memberInfoApp = new HwtxDataMemberInfoApp();
			// 该编号成员是否参加了这次活动。0-未参加，1-参加了本次活动
			memberInfoApp.setIsAttend(isAttend);
			// 该编号成员是否启用APP图标. 0-未使用队员APP，1-使用了队员APP
			memberInfoApp.setIsUseAP(isUseAP);
			HwtxDataMemberInfoBtApp memberInfoBtApp = new HwtxDataMemberInfoBtApp();
			memberInfoBtApp.setMemberInfo(memberInfo1);
			// 昵称，最大10字节字母或5个汉字
			memberInfoBtApp.setNickNameString(nickNameString);
			memberInfoBtApp.setMemberInfoApp(memberInfoApp);
			groupInfoAllBtApp1.addIndexMemberInfoArray(memberInfoBtApp);
			// }
		}
		System.out.println("groupInfoAllBtApp1:\n"
				+ HwtxCommandUtility.bytesToHexText(groupInfoAllBtApp1
						.toBytes()));
		return groupInfoAllBtApp1.toBytes();
	}

	private HwtxDataGroupInfoAllBtApp groupInfoAllBtApp2 = null;
	private int time = 0;

	/** 队员详细信息表 */
	public byte[] InformationTable2() {
		// 5循环加入以上多个队员
		groupInfoAllBtApp2 = new HwtxDataGroupInfoAllBtApp();
		// 整个有效表的实际总大小：包括wCrcGrpInfoAllBtApp,dwSizeInByte~到最后一个成员的HWTX_MemberInfo_BtAPP内容。
		// groupInfoAllBtApp2.setCrcGrpInfoAllBtAppShort((short) 0x1234);
		groupInfoAllBtApp2.setSizeInByteShort((short) InformationTableSise());
		// 本结构的数据类型. 应该是: HT_TYPE_GIA
		groupInfoAllBtApp2.setbDataType((byte) 0x63);
		// 时间戳。由领队APP每次修改本表时，写入当前系统时间。每次修改表元素，都需要更新为不同的时间戳。----重要，底层FW根据它来判断是否更新了新元素。
		time = (int) (System.currentTimeMillis());
		groupInfoAllBtApp2.setRtcTimeInteger(time);
		// 小组领队宝同行宝总数。（设备总数：包含有领队宝设备、同行宝设备的所有成员。不含无设备的成员或未参加的成员）
		int grpCountDevice = 0;// 直接从数据库查询当前关联设备号个数
		for (int i = 0; i < signUpUserListGet.size(); i++) {
			if (!TextUtils.isEmpty(signUpUserListGet.get(i).getStrDeviceSN())) {
				grpCountDevice++;
			}
		}
		groupInfoAllBtApp2.setGrpCountDeviceByte(Utile
				.int2OneByte(grpCountDevice));
		// 实际参加的总人数:含有设备的，无设备；即报道人数
		int grpCountAttend = 0;
		for (int i = 0; i < signUpUserListGet.size(); i++) {
			if (signUpUserListGet.get(i).getIs_report() == 1
					|| !TextUtils.isEmpty(signUpUserListGet.get(i)
							.getStrDeviceSN())) {
				grpCountAttend++;
			}
		}
		groupInfoAllBtApp2.setGrpCountAttendByte(Utile
				.int2OneByte(grpCountAttend));
		// 报名总人数。小组总人数：包括有领队宝设备的、有同行宝设备的、无同行宝但参加了本次活动的队员、报名但未参加的队员。
		int grpCountTotal = signUpUserListGet.size();
		groupInfoAllBtApp2.setGrpCountTotalByte(Utile
				.int2OneByte(grpCountTotal));
		// for循环遍历[1234]，加入5中
		for (int i = 0; i < signUpUserListGet.size(); i++) {
			// if (signUpUserListGet.get(i).getIs_report() == 1
			// || !TextUtils.isEmpty(signUpUserListGet.get(i)
			// .getStrDeviceSN())) {
			/** 组内编号 */
			int devNumInGrou = i + 1;
			/** 设备编号 */
			String deviceSnString = signUpUserListGet.get(i).getStrDeviceSN();
			/** 昵称，最大10字节字母或5个汉字 */
			String nickNameString = signUpUserListGet.get(i).getU_nickname();
			/** 本设备是否领队宝 */
			boolean tDeviceIsLdb = false;
			if (i == 0) {// 第一个为领队
				tDeviceIsLdb = true;
			} else {
				tDeviceIsLdb = false;
			}

			/** 是否有同行宝设备 */
			boolean deviceReady = false;
			if (!TextUtils.isEmpty(signUpUserListGet.get(i).getStrDeviceSN())) {// SN不为空说明有设备
				deviceReady = true;
			} else {
				deviceReady = false;
			}
			/** 否参加了这次活动 */
			boolean isAttend = true;
			/** 是否启用APP图标 */
			boolean isUseAP = true;
			// 1
			HwtxDataMemberDeviceInfo deviceInfo1 = new HwtxDataMemberDeviceInfo();
			// 2
			HwtxDataMemberInfo memberInfo1 = new HwtxDataMemberInfo();
			try {
				if (devNumInGrou != 0) {
					// 该设备队员的组内编号，范围[1,30]
					deviceInfo1.setDevNumInGroup(devNumInGrou);
				}
				// 本设备是否领队宝（APP确保一个队伍只有一个领队）,0-TXB同行宝（队员）,1-LDB领队宝(领队)；
				deviceInfo1.setDeviceIsLdb(tDeviceIsLdb);
				// 是否有同行宝设备。由领队APP设定：根据strDeviceSN是否为非零来判断，0-无同行宝；1-有同行宝
				deviceInfo1.setDeviceReady(deviceReady);
				memberInfo1.setDevInfo(deviceInfo1);
				// 唯一的设备SN串号，固定6字节的数字，由设备量产时写入。有非0值，则表示该队员有同行宝；如果为0值，则表示该队员无同行宝硬件。
				if (!TextUtils.isEmpty(deviceSnString)) {
					memberInfo1.setDeviceSnString(deviceSnString);
				}

			} catch (HwtxCommandException e) {
				e.printStackTrace();
			}
			// 3
			HwtxDataMemberInfoApp memberInfoApp = new HwtxDataMemberInfoApp();
			// 该编号成员是否参加了这次活动。0-未参加，1-参加了本次活动
			memberInfoApp.setIsAttend(isAttend);
			// 该编号成员是否启用APP图标. 0-未使用队员APP，1-使用了队员APP
			memberInfoApp.setIsUseAP(isUseAP);
			HwtxDataMemberInfoBtApp memberInfoBtApp = new HwtxDataMemberInfoBtApp();
			memberInfoBtApp.setMemberInfo(memberInfo1);
			// 昵称，最大10字节字母或5个汉字
			memberInfoBtApp.setNickNameString(nickNameString);
			memberInfoBtApp.setMemberInfoApp(memberInfoApp);
			groupInfoAllBtApp2.addIndexMemberInfoArray(memberInfoBtApp);
			// }
		}

		return groupInfoAllBtApp2.toBytes();
	}

	/**
	 * @return 返回大小
	 */
	public int InformationTableSise() {
		HwtxDataGroupInfoAllBtApp groupInfoAllBtAppSize = new HwtxDataGroupInfoAllBtApp();
		int tempNum = 0;
		for (int i = 0; i < signUpUserListGet.size(); i++) {
			// if (signUpUserListGet.get(i).getIs_report() == 1
			// || !TextUtils.isEmpty(signUpUserListGet.get(i)
			// .getStrDeviceSN())) {
			tempNum++;
			// }
		}
		HwtxDataMemberInfoBtApp memberInfoBtApp = new HwtxDataMemberInfoBtApp();

		return groupInfoAllBtAppSize.toBytes().length
				+ memberInfoBtApp.toBytes().length * tempNum;

	}

	/**
	 * 添加入组 手动与扫描TODO 0xYYYYYYYY 00：入组失败，0xYYYYYYYY 01：入组成功
	 */
	private HwtxCommandAddDevice commandAddIn = null;
	private byte[] tagAddIn = null;

	private void addIn(String deviceSn) {
		if (commandAddIn == null) {
			commandAddIn = new HwtxCommandAddDevice();
		}
		if (tagAddIn == null) {
			tagAddIn = commandAddIn.getCommandTagRaw();
		}

		try {
			commandAddIn.setDeviceSn(deviceSn);
		} catch (HwtxCommandException e) {
			//
			e.printStackTrace();
		}
		sendData(commandAddIn.getBluetoothPropertyUuidSend(),
				commandAddIn.getBluetoothPropertyUuidRead(),
				commandAddIn.toBytes());
	}

	/** Dialog */
	public void showAlertDialog(String title) {
		CustomDialog.Builder builder = new CustomDialog.Builder(
				DianMingActivity.this);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	/** Dialog 是否删除当前SN TODO*/
	public void showAlertDialogIsClean(String uid) {
		CustomDialog.Builder builder = new CustomDialog.Builder(
				DianMingActivity.this);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage("是否删除该队员SN号                   ");
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				signUpUserDaoSet.updateUSN("",
						U_id);
				// 再设置适配器
				handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private boolean isReceiver = false; // 是否注册

	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		// 蓝牙相关
		Utile.needContinue = false;
		tempByte = new ArrayList<byte[]>();
		mDeviceAddress = App.getInstance().getBleDuiYuanAddress();
		mDeviceName = App.getInstance().getBleDuiYuanName();
		setCharacteristic(mDeviceAddress);

		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		isReceiver = true;
		// 当前界面的广播接收器
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
	}

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Message message = Message.obtain();
			message.obj = intent;
			handler.sendMessageDelayed(message, 500);// 经过多次测试，我发现只需要在广播接收器中做个延时操作就能保证数据返回成功
														// 2015/12/10/0：50
		}
	};
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Intent intent = (Intent) msg.obj;

			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {// 连接成功，步骤1连接2获取读写特性，所以这步不能做发送数据操作
				/**
				 * 3) 查询模式：向UUID为0x0011特性写数据0xYYYYYYYY 48575458 02
				 * 000000000000发送查询模式命令
				 */
				// tempByte.clear();
				// bleSendDataTongXingBao("队员");
				mConnected = true;
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED// 未关联成功
					.equals(action)) {
				showToast("设备已断开连接！");
				cancleContact();
				mConnected = false;
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {// 返回的值
				displayData(intent
						.getByteArrayExtra(BluetoothLeService.EXTRA_DATA));
			} else if (BluetoothLeService.ACTION_GATT_CONNECTED_AGIAN
					.equals(action)) {// 已连接，试图重新连接
				/**
				 * 3) 查询模式：向UUID为0x0011特性写数据0xYYYYYYYY 48575458 02
				 * 000000000000发送查询模式命令
				 */
				mConnected = true;
				// tempByte.clear();
				// bleSendDataTongXingBao("队员");
			} else if (BluetoothLeService.SENDOVER.equals(action)) {// 每一个请求对应一个参数，来区分完成的是哪个
				// 不需要再继续返回这里
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// 不需要再继续返回这里
						Utile.needContinue = false;
						sendData(commandSynchronizationInformationTable
								.getBluetoothPropertyUuidSend(),
								commandSynchronizationInformationTable
										.getBluetoothPropertyUuidRead(),
								InformationTable());

					}
				}, 500);
			}
		}
	};
	/**
	 * @param data
	 *            获取需要展示的值 。拿到所有的数据之后再进行其他操作，这里需要一个值来判断是否接收完成
	 */
	private List<Integer> errorNumList = null;// 未同步成功的队员
	private List<byte[]> tempByte = null;

	private void displayData(byte[] bs) {
		// tempByte.add(bs);
		// byte[] newByte = ByteUtil.sysCopy(tempByte);
		System.out.println("RETURN:\n" + HwtxCommandUtility.bytesToHexText(bs));
		byte[] newByte = bs;
		String byteRead = null;
		try {
			byteRead = new String(newByte, "GB2312");
			logzjy.d("data:----" + byteRead);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 返回的tag值
		byte[] tagByte = HwtxCommandUtility
				.extractBytesFromBytes(newByte, 0, 4);
		if (Arrays.equals(tagByte, tagSynchronizationInformationTable)) {// 同步队员信息表返回值
			// 设备返回的byte[] //
			byte[] returenByte = HwtxCommandUtility.extractBytesFromBytes(
					newByte, 4, newByte.length - 4);
			// 计算当前crc计算错误需要的byte
			byte[] tempByte = new byte[newByte.length - 4];
			for (int i = 0; i < tempByte.length; i++) {
				tempByte[i] = 0;
			}
			if (Arrays.equals(returenByte, tempByte)) {// crc值计算错误，需重新发数据
			// if (mConnected) {// 连接上了才能同步到设备
			// synchronizationInformationTable();//TODO
			// btn_pop_cancle.setText("正在同步中");
			// } else {
			// showToast("未连接设备");
			// popContact.dismiss();
			// }
				showToast("数据同步失败，请重试");
			} else {
				int tempNum = 0;
				for (int i = 0; i < signUpUserListGet.size(); i++) {
					if (!TextUtils.isEmpty(signUpUserListGet.get(i)
							.getStrDeviceSN())) {// 同行宝和领队宝的总数
						tempNum++;
					}
				}
				// 我自己需要的byte[]
				byte[] needByte = HwtxCommandUtility.extractBitsFromBytes(
						returenByte, 0, tempNum);//
				errorNumList = new ArrayList<Integer>();
				// 拿到所有bit为0（未成功的）的队员位置
				if (needByte != null) {
					for (int i = 0; i < tempNum; i++) {// 根据同行宝和领队宝的总数来计算
						if (HwtxCommandUtility.extractBitFromBytes(needByte, i) == 0)// 1表示同步成功，0表示同步失败
						{
							errorNumList.add(i + 1);
						}
					}
				}
				handlerlist.sendEmptyMessage(1);
			}

		} else if (Arrays.equals(tagByte, tagAddIn)) {// 添加入组返回值
			byte[] mode = HwtxCommandUtility.extractBytesFromBytes(newByte, 4,
					1);
			byte[] byt0 = { 0 };
			byte[] byt1 = { 1 };
			if (Arrays.equals(mode, byt0)) {// 入组失败
				handlerlist.sendEmptyMessage(4);
			} else if (Arrays.equals(mode, byt1)) {// 入组成功
				handlerlist.sendEmptyMessage(2);
			}

		}
	}

}
