package com.hwacreate.outdoor.leftFragment.myguardianFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.hwa.lybd.IHWATOLYXYXL;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 曾金叶 我的卫士 @2015-8-12
 * @上午9:57:44
 */
public class MyGuardianActivity extends BaseActivity {
	// 关联按钮
	private TextView tv_tocontact = null;
	// sos按钮
	private TextView tv_sos = null;
	// 上传位置信息按钮
	private TextView tv_location = null;
	// 关联活动按钮
	private TextView tv_guanlian = null;
	private TextView tv_equipmentname = null;
	private TextView tv_tip = null;// 提示sos是否可用
	private TextView tv_status0 = null;// 时间
	private TextView tv_status = null;// IC卡状态
	private TextView tv_status1 = null;// 蓝牙
	private TextView tv_status2 = null;// 硬件状态
	private TextView tv_status3 = null;// 电池状态
	private TextView tv_status4 = null;// 入站状态
	private TextView tv_status5 = null;// 电池电量
	private TextView tv_status6 = null;// 功率
	// 远程通信按钮
	private RadioButton rb_message = null;

	private LinearLayout ll_hardware = null;
	private BluetoothAdapter ba; // 蓝牙适配器
	// 时间
	private SimpleDateFormat sDateFormat = null;
	private String date = null;

	// 传入的值
	private String strdef = "设为领队";
	private boolean bdef = false;
	private boolean cleakable = true;
	// 蓝牙设备
	private boolean sos_cleakable = true;
	private boolean location_clickable = true;
	private BroadcastReceiver batteryReceiver = null;
	private String mDeviceName = null;
	private String mDeviceAddress = null;
	private IHWATOLYXYXL iHWATOLYXYXL = null;
	private BleCommon bleCommon = null;
	private Boolean zj = false;// 终端自检标识
	private Boolean jy = false;// sos标识
	private Boolean sj = false;// 时间标识
	private Boolean dw = false;// 定位
	private Boolean hm = false;// 中心号码查询标识
	private Boolean tx = false;// 通讯申请标识
	private Boolean bleconnect = false;// 蓝牙设备是否连接标识
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private List<HuoDongXiangQingLeader> huoDongXiangQingLeaderList = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;
	private String id;

	private String contextnum, frequency, inputcontentStr;
	private int usernum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 指定下面的显示已经按钮的点击效果
		parentView = getLayoutInflater().inflate(R.layout.leftfrag_myguardianfrg, null);
		setContentView(parentView);
		initBluetooth();
		init();
		showActivity();

	}

	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		iHWATOLYXYXL = new IHWATOLYXYXL();
		// 蓝牙相关
		mDeviceName = App.getInstance().getBleLingDuiName();
		mDeviceAddress = App.getInstance().getBleLingDuiDuiAddress();
		if (!TextUtils.isEmpty(mDeviceAddress)) {
			bleCommon = BleCommon.getInstance();
			bleCommon.setCharacteristic(mDeviceAddress, BleCommon.UUID_KEY_DATA_WRITE_BEIDOU,
					BleCommon.UUID_KEY_DATA_READ_BEIDOU);
			Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
			bindService(gattServiceIntent, bleCommon.mServiceConnection, BIND_AUTO_CREATE);
			// 广播接收器
			registerReceiver(bleCommon.mGattUpdateReceiver, bleCommon.makeGattUpdateIntentFilter());
			registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		//
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			finish();
			break;
		case R.id.tv_tocontact:// 跳到关联领队宝界面
			if (!TextUtils.isEmpty(mDeviceAddress)) {// 已关联
				if (bleconnect) {
					try {
						byte[] sjsc = iHWATOLYXYXL.ISend_SJSC(000000, 0);
						bleCommon.bleSendDataBeiDou(sjsc);
						tv_status0.setText("检测中...");
						tv_status.setText("检测中...");
						tv_status1.setText("检测中...");
						tv_status2.setText("检测中...");
						tv_status3.setText("检测中...");
						tv_status4.setText("检测中...");
						tv_status5.setText("检测中...");
						tv_status6.setText("检测中...");
						sj = true;
					} catch (NullPointerException e) {
						// TODO: handle exception
						showToast("请检查蓝牙设备");
					}
				} else {
					showToast("请检查蓝牙设备");
				}

			} else {// 未关联
				if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
					showToast(getResources().getString(R.string.ble_not_supported));
				} else {
					Bundle bundle = new Bundle();
					bundle.putBoolean("fromTuZhong", false);
					openActivity(ContactMyGuardianAcitivty.class, bundle);
					finish();
				}
			}
			break;
		case R.id.tv_sos:
			if (bleconnect) {
				if (sos_cleakable) {
					btn_pop_ok.setText("启动卫星远程SOS求救");

				} else {
					btn_pop_ok.setText("撤销卫星远程SOS求救");

				}
				ll_popup.startAnimation(
						AnimationUtils.loadAnimation(MyGuardianActivity.this, R.anim.activity_translate_in));
				popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
			} else {
				showToast("请检查蓝牙设备");
			}

			break;
		case R.id.btn_pop_ok:// 启动卫星远程SOS求救
			try {
				// byte[] szhm = IHWATOLYXYXL.IXYXLSend_SZHM_CX(000000);
				// bleCommon.bleSendDataBeiDou(szhm);
				byte[] qdjy = iHWATOLYXYXL.IXYXLSend_QDJY(000000);
				bleCommon.bleSendDataBeiDou(qdjy);
				if (sos_cleakable) {
					tv_tip.setVisibility(View.GONE);
					tv_sos.setText("sos呼救中");
					jy = true;
					tv_location.setClickable(false);
					rb_message.setClickable(false);
				} else {
					tv_tip.setVisibility(View.GONE);
					tv_sos.setText("sos");
					tv_location.setClickable(true);
					rb_message.setClickable(true);
					// tv_tip.setText("远程救援呼救中...");
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
				showToast("请检查蓝牙设备");
			}
			popContact.dismiss();
			if (sos_cleakable) {
				sos_cleakable = false;
			} else {
				sos_cleakable = true;
			}
			break;
		case R.id.btn_pop_cancle:
			popContact.dismiss();
			break;
		case R.id.radiobutton_select_one:// 远程通信
			openActivity(CommunicationActivity.class);
			break;
		case R.id.radiobutton_select_two:// 设置
			Intent intent = new Intent(this, SetBanLvActivity.class);
			startActivityForResult(intent, 1);
			// openActivity(SetBanLvActivity.class);
			break;
		case R.id.radiobutton_select_three:// 删除设备
			ll_popdelete.startAnimation(
					AnimationUtils.loadAnimation(MyGuardianActivity.this, R.anim.activity_translate_in));
			popDelete.showAtLocation(parentView, Gravity.CENTER, 0, 0);
			break;
		case R.id.btn_pop_delete_ok:
			popDelete.dismiss();
			// 取消关联
			unregisterReceiver(bleCommon.mGattUpdateReceiver);
			unbindService(bleCommon.mServiceConnection);
			bleCommon.mBluetoothLeService.disconnect();
			bleCommon.mBluetoothLeService.close();
			bleCommon.mBluetoothLeService = null;
			App.getInstance().setBleLingDuiAddress("");
			App.getInstance().setBleLingDuiName("");
			openActivity(MyGuardianActivity.class);
			finish();
			break;
		case R.id.btn_pop_delete_cancle:
			popDelete.dismiss();
			break;
		case R.id.tv_location:
			if (bleconnect) {
				if (location_clickable) {
					btn_pop_location_ok.setText("启动上传当前位置");
				} else {
					btn_pop_location_ok.setText("撤销上传我的当前位置");
				}
				ll_poplocation.startAnimation(
						AnimationUtils.loadAnimation(MyGuardianActivity.this, R.anim.activity_translate_in));
				popLoaction.showAtLocation(parentView, Gravity.CENTER, 0, 0);
			} else {
				showToast("请检查蓝牙设备");
			}
			break;
		case R.id.btn_pop_location_ok:// 启动定位
			try {
				byte[] qddw = iHWATOLYXYXL.ISend_DWSQ(000000, 0, 0);
				bleCommon.bleSendDataBeiDou(qddw);
				if (location_clickable) {
					tv_location.setText("正在上传位置");
					tv_sos.setClickable(false);
					rb_message.setClickable(false);
					dw = true;
				} else {
					tv_location.setText("上传当前位置");
					tv_sos.setClickable(true);
					rb_message.setClickable(true);
				}
			} catch (NullPointerException e) {
				// TODO: handle exception
				showToast("请检查蓝牙设备");
			}
			popLoaction.dismiss();
			if (location_clickable) {
				location_clickable = false;
			} else {
				location_clickable = true;
			}
			break;
		case R.id.btn_pop_location_cancle:
			popLoaction.dismiss();
			break;
		case R.id.tv_guanlian:
			huoDongXiangQingLeader = new HuoDongXiangQingLeader();
			huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader, this);
			huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
			if (huoDongXiangQingLeaderList != null && huoDongXiangQingLeaderList.size() != 0) {
				id = huoDongXiangQingLeaderList.get(0).getAct_id();
				System.out.println("dqhd=====" + id);
				try {
					byte[] szhm = iHWATOLYXYXL.IXYXLSend_SZHM_CX(000000);
					System.out.println("szhm====" + String.valueOf(szhm));
					if (bleCommon != null) {
						bleCommon.bleSendDataBeiDou(szhm);
						hm = true;
					} else {
						showToast("ble null");
						System.out.println("ble null");
					}
					hm = true;
				} catch (NullPointerException e) {
					// TODO: handle exception
					showToast("fahfiahfiah");
				}

			} else {
				showToast("ceshibaishi");
			}
			break;
		default:
			break;
		}

	}

	private Timer timer = new Timer(true);

	// 任务
	private TimerTask task = new TimerTask() {
		public void run() {
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		}
	};

	/**
	 * 展示不同的activity
	 */
	public void showActivity() {
		if (!TextUtils.isEmpty(mDeviceAddress)) {// 已关联
			ll_hardware.setVisibility(View.VISIBLE);
			tv_tip.setVisibility(View.GONE);
			tv_equipmentname.setText(mDeviceName);
			logzjy.e(mDeviceName);
			tv_tocontact.setText("硬件自检");
			if (!TextUtils.isEmpty(App.getInstance().getLeaderHuoDongId())) {
				tv_guanlian.setVisibility(View.GONE);
				tv_sos.setBackgroundResource(R.drawable.solid_sosing);
				tv_sos.setClickable(true);
				tv_location.setBackgroundResource(R.drawable.solid_location_up);
				tv_location.setClickable(true);
				rg_button.setVisibility(View.VISIBLE);
			} else {
				tv_guanlian.setVisibility(View.VISIBLE);
				tv_sos.setVisibility(View.GONE);
				tv_location.setVisibility(View.GONE);
				rg_button.setVisibility(View.VISIBLE);
			}

		} else {// 未关联
			ll_hardware.setVisibility(View.GONE);
			tv_tip.setVisibility(View.VISIBLE);
			tv_equipmentname.setText(getString(R.string.left_gongju_myguardian_notcontact));
			tv_tocontact.setText(getString(R.string.left_gongju_myguardian_tocontact));
			tv_tip.setText(getString(R.string.left_gongju_myguardian_notcontact_title));
			tv_guanlian.setVisibility(View.GONE);
			tv_sos.setBackgroundResource(R.drawable.solid_sos);
			tv_sos.setClickable(false);
			tv_location.setBackgroundResource(R.drawable.solid_location);
			tv_location.setClickable(false);
			rg_button.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onInitData() {
		initFooterOther(" 远程通信", "设置", " 删除设备");
		initHeaderOther();
		initPopwindow();
		initPopDeletewindow();
		initPopLocationwindow();
		sDateFormat = new SimpleDateFormat("HH:mm:ss");
		ba = BluetoothAdapter.getDefaultAdapter();
		tv_tocontact = (TextView) findViewById(R.id.tv_tocontact);
		tv_sos = (TextView) findViewById(R.id.tv_sos);
		tv_location = (TextView) findViewById(R.id.tv_location);
		tv_guanlian = (TextView) findViewById(R.id.tv_guanlian);
		rb_message = (RadioButton) findViewById(R.id.radiobutton_select_one);
		tv_equipmentname = (TextView) findViewById(R.id.tv_equipmentname);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		tv_status = (TextView) findViewById(R.id.tv_status);
		tv_status1 = (TextView) findViewById(R.id.tv_status1);
		tv_status2 = (TextView) findViewById(R.id.tv_status2);
		tv_status3 = (TextView) findViewById(R.id.tv_status3);
		tv_status4 = (TextView) findViewById(R.id.tv_status4);
		tv_status5 = (TextView) findViewById(R.id.tv_status5);
		tv_status6 = (TextView) findViewById(R.id.tv_status6);
		ll_hardware = (LinearLayout) findViewById(R.id.ll_hardware);
		tv_status0 = (TextView) findViewById(R.id.tv_status0);
		// 启动定时器
		timer.schedule(task, 1*1000, 10 * 60 * 1000);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("我的卫士");

	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		tv_tocontact.setOnClickListener(this);
		btn_pop_ok.setOnClickListener(this);
		btn_pop_delete_ok.setOnClickListener(this);
		btn_pop_cancle.setOnClickListener(this);
		btn_pop_delete_cancle.setOnClickListener(this);
		tv_sos.setOnClickListener(this);
		tv_location.setOnClickListener(this);
//		tv_guanlian.setOnClickListener(this);
		btn_pop_location_ok.setOnClickListener(this);
		btn_pop_location_cancle.setOnClickListener(this);
	}

	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			if (msg.what == 0) {
				if (!TextUtils.isEmpty(App.getInstance().getBleLingDuiDuiAddress())) {// 已关联
					try {
						byte[] sjsc = iHWATOLYXYXL.ISend_SJSC(000000, 0);
						System.out.println("sjscdata===" + sjsc);
						bleCommon.bleSendDataBeiDou(sjsc);
						sj = true;
						tv_status0.setText("检测中...");
						tv_status1.setText("检测中...");
						tv_status2.setText("检测中...");
						tv_status3.setText("检测中...");
						tv_status4.setText("检测中...");
						tv_status5.setText("检测中...");
						tv_status6.setText("检测中...");
					} catch (NullPointerException e) {
						// TODO: handle exception
						showToast("请检查蓝牙设备");
					}

				} else {// 未关联
					if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
						showToast(getResources().getString(R.string.ble_not_supported));
					} else {
						Bundle bundle = new Bundle();
						bundle.putBoolean("fromTuZhong", false);
						openActivity(ContactMyGuardianAcitivty.class, bundle);
						finish();
					}
				}
			}
			if (msg.what == 1) {
				byte[] zdzj = iHWATOLYXYXL.IXYXLSend_ZDZJ(000000);
				bleCommon.bleSendDataBeiDou(zdzj);
				zj = true;
			}
			if (msg.what == 2) {

			}
			if (msg.what == 3) {
				usernum = Integer.parseInt(msg.obj.toString());
				// 蓝牙通讯申请
				byte[] sjsc = IHWATOLYXYXL.ISend_TXSQ(000000, 2, usernum, String.valueOf("101" + id));
				bleCommon.bleSendDataBeiDou(sjsc);
				tx = true;
			}
		}
	};

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {// 连接成功，
				bleconnect = true;
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED// 未关联成功
					.equals(action)) {
				bleconnect = false;
				tv_tip.setVisibility(View.GONE);
				showToast("设备已断开连接！");
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {// 返回的值
				displayData(intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA));
			}
		}
	};

	private IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	/**
	 * @param data
	 *            获取需要展示的值 。拿到所有的数据之后再进行其他操作，这里需要一个值来判断是否接收完成
	 */
	private List<byte[]> tempByte = new ArrayList<byte[]>();

	private void displayData(byte[] bs) {
		byte[] newByte;
		if (sj) {
			
			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String data = IHWATOLYXYXL.IReceive_SJXX(newByte);
						System.out.println("SJXXdata===" + data);
						JSONObject json = new JSONObject(data);
						JSONObject context = json.getJSONObject("context");
						String time = context.getString("time");
						tv_status0.setText(time);
						sj = false;
						newByte = null;
						tempByte.clear();
						if (!time.equals("")) {
							new Thread() {
								public void run() {
									handler.sendMessage(handler.obtainMessage(1));
								};
							}.start();
						}

					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sj = false;
			}
		} else if (zj) {

			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String data = IHWATOLYXYXL.IXYXLReceive_ZDXX(newByte);
						System.out.println("ZDXXdata===" + data);
						JSONObject json = new JSONObject(data);
						int state = json.getInt("state");
						if (state == 0) {
							Toast.makeText(this, json.getString("context"), Toast.LENGTH_SHORT).show();
						} else {
							JSONObject context = json.getJSONObject("context");
							// IC卡状态
							String icstate = context.getString("icstate");
							tv_status.setText(icstate);
							// 蓝牙状态
							String lystate = context.getString("lystate");
							tv_status1.setText(lystate);
							// 电池电量
							String battery = context.getString("battery");
							tv_status5.setText(battery);
							// 电池状态
							String dtstate = context.getString("dtstate");
							tv_status3.setText(dtstate);
							// 硬件状态
							String yjstate = context.getString("yjstate");
							tv_status2.setText(yjstate);
							// 入站状态
							String rzstate = context.getString("rzstate");
							tv_status4.setText(rzstate);
							JSONObject power = context.getJSONObject("power");
							String power1 = power.getString("power1");
							String power2 = power.getString("power2");
							String power3 = power.getString("power3");
							String power4 = power.getString("power4");
							String power5 = power.getString("power5");
							String power6 = power.getString("power6");
							tv_status6.setText(
									power1 + "," + power2 + "," + power3 + "," + power4 + "," + power5 + "," + power6);
							newByte = null;
							tempByte.clear();
							zj = false;
						}

					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				zj = false;
			}
		}

		else if (jy) {
			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String data = IHWATOLYXYXL.IXYXLReceive_QDXX(newByte);
						JSONObject json = new JSONObject(data);
						JSONObject context = json.getJSONObject("context");
						String status = context.getString("status");
						showToast(status);
						newByte = null;
						tempByte.clear();
						;
						jy = false;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jy = false;
			}
		}

		else if (dw) {
			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String data = IHWATOLYXYXL.IReceive_DWXX(newByte);
						System.out.println("DWXXdata===" + data);
						JSONObject json = new JSONObject(data);
						int state = json.getInt("state");
						if (state == 0) {
							Toast.makeText(this, json.getString("context"), Toast.LENGTH_SHORT).show();
							dw = false;
						} else {
							JSONObject context = json.getJSONObject("context");
							String type = context.getString("type");
							String key = context.getString("key");
							String precision = context.getString("precision");
							String emergency = context.getString("emergency");
							String solutions = context.getString("solutions");
							String elevationtype = context.getString("elevationtype");
							String queryaddress = context.getString("queryaddress");
							String time = context.getString("time");
							String longitude = context.getString("longitude");
							String latitude = context.getString("latitude");
							newByte = null;
							dw = false;
							tempByte.clear();
							;
							inputcontentStr = longitude + "," + latitude + "," + elevationtype;
						}

					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dw = false;
			}
		}
		// 中心号码查询返回信息
		else if (hm) {
			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String hmxx = IHWATOLYXYXL.IXYXLReceive_HMXX(newByte);
						JSONObject json = new JSONObject(hmxx);
						JSONObject context = json.getJSONObject("context");
						// 中心号码
						contextnum = context.getString("contextnum");
						// 频度信息
						frequency = context.getString("frequency");
						System.out.println("hmxx====" + context.toString());
						hm = false;
						newByte = null;
						tempByte.clear();
						if (!contextnum.equals("")) {
							new Thread() {
								public void run() {
									handler.sendMessage(handler.obtainMessage(3, contextnum));
								};
							}.start();
						}
					}
					;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				hm = false;
			}
		} else if (tx) {
			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String data = IHWATOLYXYXL.IReceive_FKXX(newByte);
						JSONObject json = new JSONObject(data);
						JSONObject context = json.getJSONObject("context");
						System.out.println("FKXX===CONTEXT" + context.toString());
						String status = context.getString("status");
						System.out.println("fkxx====status" + status);
						showToast(status);
						tx = false;
						newByte = null;
						tempByte.clear();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				tx = false;
			}
		}

	}

	// GPS状态
	public void getGPSStatus() {
		String str = Settings.Secure.getString(MyGuardianActivity.this.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (str != null) {
			tv_status2.setText("已开启");
		} else {
			tv_status2.setText("未开启");
		}
	}

	// WIFI状态
	public void getWifiStatus() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			tv_status3.setText("已开启");
		} else {
			tv_status3.setText("未开启");
		}
	}

	// 电量百分比
	public void getBatteryleave() {
		batteryReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				//
				int level = intent.getIntExtra("level", 0);
				// level加%就是当前电量了
				tv_status5.setText(String.valueOf(level + "%"));
			}
		};
		registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	// 蓝牙状态
	public void getBluetoothStatus() {
		// 蓝牙适配器是否存在，即是否发生了错误
		if (ba == null) {
			tv_status1.setText("无蓝牙设备"); // error
		} else if (ba.isEnabled()) {
			int a2dp = ba.getProfileConnectionState(BluetoothProfile.A2DP); // 可操控蓝牙设备，如带播放暂停功能的蓝牙耳机
			int headset = ba.getProfileConnectionState(BluetoothProfile.HEADSET); // 蓝牙头戴式耳机，支持语音输入输出
			int health = ba.getProfileConnectionState(BluetoothProfile.HEALTH); // 蓝牙穿戴式设备
			// 查看是否蓝牙是否连接到三种设备的一种，以此来判断是否处于连接状态还是打开并没有连接的状态
			int flag = -1;
			if (a2dp == BluetoothProfile.STATE_CONNECTED) {
				flag = a2dp;
			} else if (headset == BluetoothProfile.STATE_CONNECTED) {
				flag = headset;
			} else if (health == BluetoothProfile.STATE_CONNECTED) {
				flag = health;
			}
			// 说明连接上了三种设备的一种
			if (flag != -1) {
				tv_status1.setText("已连接");
			} else if (flag == -1) {
				tv_status1.setText("未连接");
			}
		} else {
			tv_status1.setText("蓝牙已关闭"); // shut off
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	// pop sos按钮弹出
	private View parentView = null;
	private PopupWindow popContact = null;
	private LinearLayout ll_popup = null;
	private RelativeLayout parent = null;// 半透明背景色
	private Button btn_pop_ok = null;// 选择
	private Button btn_pop_cancle = null;// 取消
	private View view1 = null;

	private void initPopwindow() {//
		popContact = new PopupWindow(MyGuardianActivity.this);
		view1 = getLayoutInflater().inflate(R.layout.pop_sos, null);
		ll_popup = (LinearLayout) view1.findViewById(R.id.ll_popup);
		popContact.setWidth(LayoutParams.MATCH_PARENT);
		popContact.setHeight(LayoutParams.WRAP_CONTENT);
		popContact.setBackgroundDrawable(new BitmapDrawable());
		popContact.setFocusable(true);
		popContact.setOutsideTouchable(true);
		popContact.setContentView(view1);
		// pop中的三个按钮
		// 此方法可防止5.0版本下面的banner挡住PopWindows
		popContact.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		parent = (RelativeLayout) view1.findViewById(R.id.parent);
		btn_pop_ok = (Button) view1.findViewById(R.id.btn_pop_ok);
		btn_pop_ok.setText("启动卫星远程SOS求救");
		btn_pop_cancle = (Button) view1.findViewById(R.id.btn_pop_cancle);
	}

	// pop 删除设备按钮弹出
	private PopupWindow popDelete = null;
	private LinearLayout ll_popdelete = null;
	private RelativeLayout parentdelete = null;// 半透明背景色
	private Button btn_pop_delete_ok = null;// 选择
	private Button btn_pop_delete_cancle = null;// 取消

	private View view2 = null;

	private void initPopDeletewindow() {//
		popDelete = new PopupWindow(MyGuardianActivity.this);
		view2 = getLayoutInflater().inflate(R.layout.pop_deleteequ, null);
		ll_popdelete = (LinearLayout) view2.findViewById(R.id.ll_popdelete);
		popDelete.setWidth(LayoutParams.MATCH_PARENT);
		popDelete.setHeight(LayoutParams.WRAP_CONTENT);
		popDelete.setBackgroundDrawable(new BitmapDrawable());
		popDelete.setFocusable(true);
		popDelete.setOutsideTouchable(true);
		popDelete.setContentView(view2);
		// pop中的三个按钮
		// 此方法可防止5.0版本下面的banner挡住PopWindows
		popDelete.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		parentdelete = (RelativeLayout) view2.findViewById(R.id.parentdelete);
		btn_pop_delete_ok = (Button) view2.findViewById(R.id.btn_pop_delete_ok);
		btn_pop_delete_cancle = (Button) view2.findViewById(R.id.btn_pop_delete_cancle);
	}

	// pop location按钮弹出
	private PopupWindow popLoaction = null;
	private LinearLayout ll_poplocation = null;
	private RelativeLayout parentlocation = null;// 半透明背景色
	private Button btn_pop_location_ok = null;// 选择
	private Button btn_pop_location_cancle = null;// 取消

	private View view3 = null;

	private void initPopLocationwindow() {
		popLoaction = new PopupWindow(this);
		view3 = getLayoutInflater().inflate(R.layout.pop_location, null);
		ll_poplocation = (LinearLayout) view3.findViewById(R.id.ll_popup_location);
		popLoaction.setWidth(LayoutParams.MATCH_PARENT);
		popLoaction.setHeight(LayoutParams.WRAP_CONTENT);
		popLoaction.setBackgroundDrawable(new BitmapDrawable());
		popLoaction.setFocusable(true);
		popLoaction.setOutsideTouchable(true);
		popLoaction.setContentView(view3);
		// pop中的三个按钮
		// 此方法可防止5.0版本下面的banner挡住PopWindows
		popLoaction.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		parentlocation = (RelativeLayout) view3.findViewById(R.id.parent_location);
		btn_pop_location_ok = (Button) view3.findViewById(R.id.btn_pop_location_ok);
		btn_pop_location_cancle = (Button) view3.findViewById(R.id.btn_pop_location_cancle);
	}

	private void clearUI() {
	}

	private void updateConnectionState(final int resourceId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// mConnectionState.setText(resourceId);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// if (!TextUtils.isEmpty(App.getInstance().getBleLingDuiDuiAddress()))
		// {// 已关联
		// unregisterReceiver(mGattUpdateReceiver);
		// unbindService(mServiceConnection);
		// mBluetoothLeService = null;
		// }

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				tv_equipmentname.setText(App.getInstance().getBleLingDuiName());
			}
		}
	}

}
