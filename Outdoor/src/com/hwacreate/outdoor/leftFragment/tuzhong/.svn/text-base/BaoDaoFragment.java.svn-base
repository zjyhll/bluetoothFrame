package com.hwacreate.outdoor.leftFragment.tuzhong;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.ContactMyGuardianAcitivty;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.KeXuanHuoDongActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.GetQRPic;
import com.hwacreate.outdoor.utl.NetUtil;
import com.keyhua.outdoor.protocol.UserTeamReportAction.UserTeamReportRequest;
import com.keyhua.outdoor.protocol.UserTeamReportAction.UserTeamReportRequestParameter;
import com.keyhua.outdoor.protocol.UserTeamReportAction.UserTeamReportResponse;
import com.keyhua.outdoor.protocol.UserTeamReportAction.UserTeamReportResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

/**
 * @author 曾金叶
 * @2015-8-5 @上午10:15:40
 */
public class BaoDaoFragment extends BaseFragment {
	private ImageView erweima_iv = null;
	private TextView tv_ok = null;// 网络报到
	private TextView tv_userid = null;// 用户id
	private TextView tv_contact = null;// 关联同行宝
	//
	private String mDeviceName = null;// 蓝牙名
	private String mDeviceAddress = null;// 蓝牙地址
	private BleCommon bleCommon = null;
	// 本地数据,无网络时使用
	private HuoDongXiangQingItem huoDongXiangQingItemhuancunRead = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingItem1DaoRead = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/**
	 * 领取的活动详情
	 */
	public void initDaoRead() {
		// 选择的活动，判断有无网
		huoDongXiangQingItemhuancunRead = new HuoDongXiangQingItem();
		huoDongXiangQingItem1DaoRead = new BaseDao<HuoDongXiangQingItem>(
				huoDongXiangQingItemhuancunRead, getActivity());
		huoDongXiangQingItemhuancunRead = huoDongXiangQingItem1DaoRead
				.searchByActid(App.getInstance().getHuoDongId());

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_baodao, container, false);
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
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 队员
		mDeviceName = App.getInstance().getBleDuiYuanName();
		mDeviceAddress = App.getInstance().getBleDuiYuanAddress();

		if (!TextUtils.isEmpty(mDeviceName)) {
			initBluetooth();
			tv_contact.setText("已关联同行宝:" + mDeviceName + "\t\t点击断开");
		} else {
			tv_contact.setText("关联同行宝");
		}
		if (TextUtils.isEmpty(App.getInstance().getHuoDongId())) {
			showToast("请先领取活动");
			tv_ok.setClickable(false);
			tv_ok.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.shape_baodao_cannot));
		}
	}

	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		// 蓝牙相关
		bleCommon = BleCommon.getInstance();
		bleCommon.setCharacteristic(mDeviceAddress);
		Intent gattServiceIntent = new Intent(getActivity(),
				BluetoothLeService.class);
		getActivity().bindService(gattServiceIntent,
				bleCommon.mServiceConnection, Context.BIND_AUTO_CREATE);
		// 广播接收器
		getActivity().registerReceiver(bleCommon.mGattUpdateReceiver,
				bleCommon.makeGattUpdateIntentFilter());
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
		bleCommon.setCharacteristic(strWrite, strRead);
		if (bleCommon != null) {
			if (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress())
					&& bleCommon.mConnected) {
				bleCommon.bleSendDataTongXingBao(strSend);
			} else {
				showToast("设备已断开连接！");
				cancleContact();
			}
		} else {
			showToast("设备未连接！");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_tv_right_noicon:
			showToast("未报到");
			break;
		case R.id.tv_ok:
			if (NetUtil.isNetworkAvailable(getActivity())) {
				if (huoDongXiangQingItemhuancunRead.getAct_state() == 4) {// 只有在出行中才能报到
					sendAsyn();
				} else {
					showToast("只有出行中才可报到");
				}
			} else {
				showToast("当前没有网络");
			}
			break;
		case R.id.tv_contact:
			if (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanName())) {
				cancleContact();
			} else {
				if (!getActivity().getPackageManager().hasSystemFeature(// 设备是否支持蓝牙设备
						PackageManager.FEATURE_BLUETOOTH_LE)) {
					showToast(getActivity().getResources().getString(
							R.string.ble_not_supported));
				} else {
					Bundle bundle = new Bundle();
					bundle.putBoolean("fromTuZhong", true);
					openActivity(ContactMyGuardianAcitivty.class, bundle);
				}
			}
			break;
		case R.id.btn_pop_ok:
			popContact.dismiss();
			break;
		case R.id.btn_pop_cancle:
			popContact.dismiss();
			break;

		default:
			break;
		}
	}

	/**
	 * 取消关联
	 */
	public void cancleContact() {
		App.getInstance().setBleDuiYuanAddress("");
		App.getInstance().setBleDuiYuanName("");
		getActivity().unregisterReceiver(bleCommon.mGattUpdateReceiver);
		getActivity().unbindService(bleCommon.mServiceConnection);
		bleCommon.mBluetoothLeService.disconnect();
		bleCommon.mBluetoothLeService.close();
		bleCommon.mBluetoothLeService = null;
		bleCommon = null;
		tv_contact.setText("关联同行宝");
	}

	@Override
	protected void onInitData() {
		initDaoRead();
		initPopwindow();
		erweima_iv = (ImageView) getActivity().findViewById(R.id.erweima_iv);
		tv_ok = (TextView) getActivity().findViewById(R.id.tv_ok);
		tv_userid = (TextView) getActivity().findViewById(R.id.tv_userid);
		tv_contact = (TextView) getActivity().findViewById(R.id.tv_contact);
	}

	@Override
	protected void onResload() {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userid", App.getInstance().getUserid());
			// jsonObject.put("huoDongId", App.getInstance().getUserid());
			tv_userid.setText("用户id:" + App.getInstance().getUserid());
			erweima_iv.setImageBitmap(GetQRPic.createImage(jsonObject
					.toString()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void setMyViewClick() {

		tv_ok.setOnClickListener(this);
		tv_contact.setOnClickListener(this);
		btn_pop_ok.setOnClickListener(this);
		btn_pop_cancle.setOnClickListener(this);
	}

	@Override
	protected void headerOrFooterViewControl() {
		main_rg.setVisibility(View.GONE);
		rg_button.setVisibility(View.VISIBLE);
		// top_tv_right_noicon.setVisibility(View.VISIBLE);
		top_tv_right_noicon
				.setTextColor(getResources().getColor(R.color.white));
		top_tv_right_noicon.setText("未报到");
		initMainFooter("任务", "报到", "地图");
		rg_button.setBackgroundColor(getResources().getColor(R.color.white));
		radiobutton_select_one
				.setBackgroundResource(R.drawable.select_item_down);
		radiobutton_select_two
				.setBackgroundResource(R.drawable.select_item_down);
		radiobutton_select_three
				.setBackgroundResource(R.drawable.select_item_down);
		radiobutton_select_one.setTextColor(getResources().getColor(
				R.color.title));
		radiobutton_select_two.setTextColor(getResources().getColor(
				R.color.title));
		radiobutton_select_three.setTextColor(getResources().getColor(
				R.color.title));
	}

	/** 队员报到 ，领队扫描，不管是队员主动网络报到还是领队二维码扫描报到，都必须要在有网的情况下进行 */
	private Thread thread = null;

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

	public void Action() { // TODO
		UserTeamReportRequest request = new UserTeamReportRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		UserTeamReportRequestParameter parameter = new UserTeamReportRequestParameter();
		parameter.setAct_id(App.getInstance().getHuoDongId());
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
					UserTeamReportResponse response = new UserTeamReportResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UserTeamReportResponsePayload payload = (UserTeamReportResponsePayload) response
							.getPayload();
					result = payload.getResult();
					msgstr = payload.getMsg();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
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

	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				switch (result) {
				case 0:
					top_tv_right_noicon.setText("未报到");
					break;
				case 1:
					top_tv_right_noicon.setText("已报到");
					break;
				default:
					break;
				}
				showToast(msgstr);
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
}
