package com.hwacreate.outdoor.leftFragment.gongju;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.bluetooth.le.Utile;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandException;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandLeaderSetting;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandQueryMode;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveSetting;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandUtility;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataFirmwareConfig;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.bean.SignUpUser;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CustomDialog;
import com.hwacreate.outdoor.view.MyProgressBar;
import com.keyhua.outdoor.protocol.AddActConfigAction.AddActConfigRequest;
import com.keyhua.outdoor.protocol.AddActConfigAction.AddActConfigRequestParameter;
import com.keyhua.outdoor.protocol.AddActConfigAction.AddActConfigResponse;
import com.keyhua.outdoor.protocol.AddActConfigAction.AddActConfigResponsePayload;
import com.keyhua.outdoor.protocol.SetLeaderAndUserDeviceAction.SetLeaderAndUserDeviceIdsItem;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SetActivity extends BaseActivity {
	/** 同行宝编号 */
	private TextView tv_num = null;
	/** GPS工作间隔[1,60] */
	private EditText et2 = null;
	/** GPS工作间隔[1,60] */
	private Integer bGpsInterval = null;
	/** 广播时间间隔[5,60] */
	private EditText et3 = null;
	/** 广播时间间隔[5,60] */
	private Integer bHktAtInterval = null;
	/** 失联次数 [1-10] */
	private EditText et5 = null;
	/** 失联次数 [1-10] */
	private Integer bLostContactNum = null;
	/** 黄色警告距离 范围[10, 5000] */
	private EditText et6 = null;
	/** 黄色警告距离 范围[10, 5000] */
	private Integer wWarningDistance1 = null;
	/** 红色警告距离 */
	private EditText et61 = null;
	/** 红色警告距离 */
	private Integer wWarningDistance2 = null;
	/** 失联警告距离 */
	private EditText et62 = null;
	/** 失联警告距离 */
	private Integer wWarningDistance3 = null;
	/** 电量告警 */
	private EditText et7 = null;
	/** 电量告警 */
	private Integer bWarningBatteryPercent = null;

	// 设备模式
	private String mDeviceName = null;// 蓝牙名
	private String mDeviceAddress = null;// 蓝牙地址
	// 同步参数
	private boolean isConnect = false;
	private JSONObject hwtxCommandjsonObject = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_set, null);
		setContentView(parentView);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			CommonUtility.gone_keyboard(this);
			finish();
			break;
		case R.id.top_tv_right:// 保存
			/**
			 * 2) 设备参数表：APP向UUID为0x0025特性写数据发送设备参数表，设备参数表数据格式参看hwtx_ext.
			 * h中的结构体HWTX_FW_Config，直接传输数据块
			 */
			bGpsInterval = Integer.valueOf(et2.getText().toString());
			bHktAtInterval = Integer.valueOf(et3.getText().toString());
			bLostContactNum = Integer.valueOf(et5.getText().toString());
			wWarningDistance1 = Integer.valueOf(et6.getText().toString());
			wWarningDistance2 = Integer.valueOf(et61.getText().toString());
			wWarningDistance3 = Integer.valueOf(et62.getText().toString());
			bWarningBatteryPercent = Integer.valueOf(et7.getText().toString());
			try {
				hwtxCommandjsonObject = new JSONObject();
				hwtxCommandjsonObject.put("bGpsInterval", bGpsInterval);
				hwtxCommandjsonObject.put("bHktAtInterval", bHktAtInterval);
				hwtxCommandjsonObject.put("bLostContactNum", bLostContactNum);
				hwtxCommandjsonObject.put("wWarningDistance1",
						wWarningDistance1);
				hwtxCommandjsonObject.put("wWarningDistance2",
						wWarningDistance2);
				hwtxCommandjsonObject.put("wWarningDistance3",
						wWarningDistance3);
				hwtxCommandjsonObject.put("bWarningBatteryPercent",
						bWarningBatteryPercent);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (bGpsInterval != null || bHktAtInterval != null
					&& bLostContactNum != null && wWarningDistance1 != null
					&& wWarningDistance3 != null && wWarningDistance3 != null
					&& bWarningBatteryPercent != null) {
				if (bGpsInterval < 1 && bGpsInterval > 60) {
					showToast("GPS工作间隔范围为1到60");
					return;
				}
				if (bHktAtInterval < 5 || bHktAtInterval > 60) {
					showToast("广播时间间隔范围为5到60");// 广播时间间隔[5,60]
					return;
				}
				if (bLostContactNum < 1 || bLostContactNum > 10) {
					showToast("失联次数范围为1到10");// 广播时间间隔[1,10]
					return;
				}
				if (wWarningDistance1 < 10 || wWarningDistance1 > 5000) {
					showToast("黄色警告范围为10到5000");// 范围[10, 5000]
					return;
				}
				if (wWarningDistance2 > wWarningDistance1) {
					if (wWarningDistance2 < 10 || wWarningDistance2 > 5000) {
						showToast("红色警告范围为10到5000");// 范围[10, 5000]
						return;
					}
				} else {
					showToast("黄色警告范围大于红色警告范围");
					return;
				}
				if (wWarningDistance2 > wWarningDistance1) {
					if (wWarningDistance3 < 10 || wWarningDistance3 > 5000) {
						showToast("失联警告范围为10到5000");// 范围[10, 5000]
						return;
					}
				} else {
					showToast("红色警告范围大于失联警告范围");
					return;
				}
				if (bWarningBatteryPercent < 0 || bWarningBatteryPercent > 100) {
					showToast("电量百分比告警警告为10到5000");// 范围[0,100]
					return;
				}

				ll_popup.startAnimation(AnimationUtils.loadAnimation(
						SetActivity.this, R.anim.activity_translate_in));
				popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
			} else {
				showToast("所有信息都为必填");
			}
			break;
		case R.id.btn_pop_ok:// 保存到设备 TODO
			// if (NetUtil.isNetworkAvailable(SetActivity.this)) {// 有网
			// sendAsyn(hwtxCommandjsonObject);
			// }else{//无网
			if (mConnected) {// 连接上了才能同步到设备
				hwtxCommandSendGpsPrepare();
				btn_pop_cancle.setText("正在同步中");
			} else {
				showToast("未连接设备");
				popContact.dismiss();
			}
			// }

			break;
		case R.id.btn_pop_network:// TODO 保存到网络
			// 同步到网络
			sendAsyn(hwtxCommandjsonObject);
			break;
		case R.id.btn_pop_cancle:
			popContact.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initDao();
		initHeaderOther();
		initPopwindow();
		tempByte = new ArrayList<byte[]>();
		tv_num = (TextView) findViewById(R.id.tv_num);
		et2 = (EditText) findViewById(R.id.et2);
		et3 = (EditText) findViewById(R.id.et3);
		et5 = (EditText) findViewById(R.id.et5);
		et6 = (EditText) findViewById(R.id.et6);
		et61 = (EditText) findViewById(R.id.et61);
		et62 = (EditText) findViewById(R.id.et62);
		et7 = (EditText) findViewById(R.id.et7);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("队伍设置");
		top_tv_right.setVisibility(View.VISIBLE);
		top_tv_right.setText("保存");
		if (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress())) {
			// 设备名必须不为空才能跳入该界面，并且连接未断开
			// if (!bleCommon.mConnected) {
			// isConnect = true;
			initBluetooth();
			// } else {
			// showToast("设备未连接");
			// App.getInstance().setBleDuiYuanAddress("");
			// App.getInstance().setBleDuiYuanName("");
			// // finish();
			// isConnect = false;
			// }
		} else {
			isConnect = false;
		}
		tv_num.setText(mDeviceName);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 取消关联
	 */
	public void cancleContact() {
		unregisterReceiver(mGattUpdateReceiver);
		unbindService(mServiceConnection);
		if (mBluetoothLeService != null) {
			mBluetoothLeService.disconnect();
			mBluetoothLeService.close();
			mBluetoothLeService = null;
		}
		finish();
	}

	private Thread thread = null;

	public void sendAsyn(final JSONObject hwtxCommandjsonObject) {
		thread = new Thread() {
			public void run() {
				Action(hwtxCommandjsonObject);
			}
		};
		thread.start();
	}

	public void Action(JSONObject hwtxCommandjsonObject) { // TODO
		AddActConfigRequest request = new AddActConfigRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		AddActConfigRequestParameter parameter = new AddActConfigRequestParameter();
		parameter.setAct_id(App.getInstance().getLeaderHuoDongId());
		parameter.setConfig(hwtxCommandjsonObject.toString());
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
					AddActConfigResponse response = new AddActConfigResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					AddActConfigResponsePayload payload = (AddActConfigResponsePayload) response
							.getPayload();
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

	// pop
	private View parentView = null;
	private PopupWindow popContact = null;
	private LinearLayout ll_popup = null;
	private RelativeLayout parent = null;// 半透明背景色
	private Button btn_pop_ok = null;// 同步编号信息到领队宝
	private Button btn_pop_network = null;// 同步编号信息到设备
	private MyProgressBar btn_pop_percent = null;// 同步百分比
	private Button btn_pop_cancle = null;// 取消

	private void initPopwindow() {
		popContact = new PopupWindow(SetActivity.this);
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
		btn_pop_ok.setText("保存设置");
		// btn_pop_network.setText("保存设置到网络");
		btn_pop_ok.setOnClickListener(this);
		btn_pop_network.setOnClickListener(this);
		btn_pop_cancle.setOnClickListener(this);
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
		// // TODO Auto-generated catch block
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
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED_AGIAN);
		intentFilter.addAction(BluetoothLeService.SENDOVER);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	/**
	 * @param data
	 *            获取需要展示的值 。拿到所有的数据之后再进行其他操作，这里需要一个值来判断是否接收完成
	 */
	// 队员
	private SignUpUser signUpUserGet = null;
	private List<SignUpUser> signUpUserListGet = null;
	private BaseDao<SignUpUser> signUpUserDaoGet = null;

	private List<Integer> errorNumList = null;// 未同步成功的队员
	private List<byte[]> tempByte = null;

	private void displayData(byte[] bs) {
		System.out.println("RETURN:\n"+HwtxCommandUtility.bytesToHexText(bs));
		tempByte.add(bs);
		byte[] newByte = ByteUtil.sysCopy(tempByte);
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
		if (Arrays.equals(tagByte, tagHwtxCommandSendGpsPrepare)) {// 设置设备参数返回值
			// 设备返回的byte // TODO
			byte[] returenByte = HwtxCommandUtility.extractBytesFromBytes(
					newByte, 4, newByte.length - 4);
			byte[] tempByte = new byte[newByte.length - 4];
			for (int i = 0; i < tempByte.length; i++) {
				tempByte[i] = 0;
			}
			if (Arrays.equals(returenByte, tempByte)) {// crc值计算错误，需重新发数据
//				if (mConnected) {// 连接上了才能同步到设备
//					hwtxCommandSendGpsPrepare();
//				} else {
//					showToast("未连接设备");
//					popContact.dismiss();
//				}
				showToast("数据同步失败，请重试");
			} else {
				int tempNum = 0;
				for (int i = 0; i < signUpUserListGet.size(); i++) {
					if (!TextUtils.isEmpty(signUpUserListGet.get(i)
							.getStrDeviceSN())) {//同行宝和领队宝的总数
						tempNum++;
					}
				}
				// 我自己需要的byte[]
				byte[] needByte = HwtxCommandUtility.extractBitsFromBytes(
						returenByte, 0, tempNum);//
				errorNumList = new ArrayList<Integer>();
				// 拿到所有bit为0（未成功的）的队员位置
				if (needByte != null) {
					for (int i = 0; i < tempNum; i++) {//根据同行宝和领队宝的总数来计算
						if (HwtxCommandUtility.extractBitFromBytes(needByte, i) == 0)// 1表示同步成功，0表示同步失败
						{
							errorNumList.add(i + 1);
						}
					}
				}
				handlerlist.sendEmptyMessage(2);
			}
		}
	}

	// ------------------------------------end------------------------
	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		Utile.needContinue = false;
		// 蓝牙相关
		mDeviceAddress = App.getInstance().getBleDuiYuanAddress();
		mDeviceName = App.getInstance().getBleDuiYuanName();
		setCharacteristic(mDeviceAddress);

		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		// 01-22 12:40:33.590: D/BluetoothLeService(17916): Trying to use an
		// existing mBluetoothGatt for connection.
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
				mConnected = true;
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED// 未关联成功
					.equals(action)) {
				mConnected = false;
				showToast("设备已断开连接！");
				cancleContact();
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
			} else if (BluetoothLeService.SENDOVER.equals(action)) {// 每一个请求对应一个参数，来区分完成的是哪个
				// 告诉设备需要传的长度之后，再讲设备参数表发送过去,延时几秒

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// 不需要再继续返回这里
						Utile.needContinue = false;
						sendData(commandHwtxCommandSendGpsPrepare
								.getBluetoothPropertyUuidSend(),
								commandHwtxCommandSendGpsPrepare
										.getBluetoothPropertyUuidRead(),
								equipmentParameterList());
					}
				}, 500);
			}
		}
	};

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
		tempByte.clear();
		setCharacteristic(strWrite, strRead);
		if (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress())
				&& mConnected) {
			bleSendDataTongXingBao(strSend);
		} else {
			showToast("设备已断开连接！");
			cancleContact();
		}
	}

	/**
	 * 准备同步设备参数 TODO
	 */
	private HwtxCommandReceiveSetting commandHwtxCommandSendGpsPrepare = null;
	private byte[] tagHwtxCommandSendGpsPrepare = null;

	private void hwtxCommandSendGpsPrepare() {
		if (commandHwtxCommandSendGpsPrepare == null) {
			commandHwtxCommandSendGpsPrepare = new HwtxCommandReceiveSetting();
		}

		if (tagHwtxCommandSendGpsPrepare == null) {
			tagHwtxCommandSendGpsPrepare = commandHwtxCommandSendGpsPrepare
					.getCommandTagRaw();
		}

		try {
			commandHwtxCommandSendGpsPrepare
					.setSettingDataLength(equipmentParameterListSize());
		} catch (HwtxCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utile.needContinue = true;
		sendData(
				commandHwtxCommandSendGpsPrepare.getBluetoothPropertyUuidSend(),
				commandHwtxCommandSendGpsPrepare.getBluetoothPropertyUuidRead(),
				commandHwtxCommandSendGpsPrepare.toBytes());

	}

	/**
	 * 算出所有数据大小
	 */
	public void equipmentParameterListAll() {
		byte[] temp = equipmentParameterList2();
		System.out.println("equipmentParameterList2:\n"+HwtxCommandUtility.bytesToHexText(temp));
		// 本数据结构，除了wCrcGrpInfoAllBtApp之外所有数据的CRC值。(CRC计算：包括从dwSizeInByte开始，到最后一个成员的HWTX_MemberInfo_BtAPP内容）
		command.setConfigCRCInteger(HwtxCommandUtility.crc16(temp,
				temp.length - 2));

	}

	/**
	 * 设备参数表 TODO 需要发送的值
	 */
	HwtxDataFirmwareConfig command = null;

	private byte[] equipmentParameterList() {
		command = new HwtxDataFirmwareConfig();
		command.setbDataType(0x62);
		command.setGpsIntervalInteger(bGpsInterval);
		command.setHktAtIntervalInteger(bHktAtInterval);
		command.setLostContactNumInteger(bLostContactNum);
		command.setWarningDistance1Integer(wWarningDistance1);
		command.setWarningDistance2Integer(wWarningDistance2);
		command.setWarningDistance3Integer(wWarningDistance3);
		command.setWarningBatteryPercentInteger(bWarningBatteryPercent);
		equipmentParameterListAll();
		System.out.println("equipmentParameterList:\n"+HwtxCommandUtility.bytesToHexText(command.toBytes()));
		return command.toBytes();
	}

	/**
	 * @return计算crc值
	 */
	private byte[] equipmentParameterList2() {
		HwtxDataFirmwareConfig command = new HwtxDataFirmwareConfig();
		command.setbDataType(0x62);
		command.setGpsIntervalInteger(bGpsInterval);
		command.setHktAtIntervalInteger(bHktAtInterval);
		command.setLostContactNumInteger(bLostContactNum);
		command.setWarningDistance1Integer(wWarningDistance1);
		command.setWarningDistance2Integer(wWarningDistance2);
		command.setWarningDistance3Integer(wWarningDistance3);
		command.setWarningBatteryPercentInteger(bWarningBatteryPercent);
		return command.toBytes();
	}

	/**
	 * @return 整个大小
	 */
	private int equipmentParameterListSize() {
		HwtxDataFirmwareConfig command = new HwtxDataFirmwareConfig();
		return command.toBytes().length;
	}

	/**
	 * 数据的本地化初始化
	 */
	public void initDao() {
		// 获取队员数据库
		signUpUserGet = new SignUpUser();
		signUpUserDaoGet = new BaseDao<SignUpUser>(signUpUserGet,
				SetActivity.this);
		// 拿取数据
		signUpUserListGet = signUpUserDaoGet.queryAll();
	}

	private Handler handlerlist = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:// 设备参数表设置：
				String str = "";
				for (int i = 0; i < errorNumList.size(); i++) {
					str += errorNumList.get(i) + "号";
				}
				if (TextUtils.isEmpty(str)) {
					showToast("所有队员参数设置同步成功！");
					finish();
				} else {
					showAlertDialog("队员：" + str + "未同步成功                   ");
				}
				popContact.dismiss();

				break;
			case CommonUtility.SERVEROK1:
				// popContact.dismiss();
				// showToast("参数设置成功");
				// finish();
				if (mConnected) {// 连接上了才能同步到设备
					hwtxCommandSendGpsPrepare();
				} else {
					showToast("未连接设备");
					popContact.dismiss();
				}
				break;
			default:
				break;
			}

		}
	};

	/** Dialog */
	public void showAlertDialog(String title) {
		CustomDialog.Builder builder = new CustomDialog.Builder(
				SetActivity.this);
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
}
