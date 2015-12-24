package com.hwacreate.outdoor.leftFragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.afinal.simplecache.ACache;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.bluetooth.le.Utile;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandLeaderSetting;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandQueryMode;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandUtility;
import com.hwacreate.outdoor.leftFragment.gongju.DianMingActivity;
import com.hwacreate.outdoor.leftFragment.gongju.DisplayDongtaiActivity;
import com.hwacreate.outdoor.leftFragment.gongju.SetActivity;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.ContactMyGuardianAcitivty;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.KeXuanHuoDongActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.huodongxiangqing.HuoDongXiangQingActivity;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.service.UpGpsInfoService;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.SPUtils;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ZhengDuiChuXingFragment extends BaseFragment {
	private LinearLayout chuxing_item = null, chuxing_yitianjia = null;

	private TextView chuxing_time = null, chuxing_start_time = null,
			chuxing_name = null, chuxing_diqu = null, chuxing_juli = null,
			chuxing_tab1 = null, chuxing_tab2 = null, chuxing_tab3 = null,
			chuxing_tianjia;// 添加活动
	private LinearLayout ll_chuxing_tianjia = null;
	private ImageView chuxing_image = null;
	private TextView chuxing_tongxingbao = null;
	private String mDeviceName = null;// 蓝牙名
	private String mDeviceAddress = null;// 蓝牙地址
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private List<HuoDongXiangQingLeader> huoDongXiangQingLeaderList = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.getInstance().setBottonChoice(CommonUtility.LINGDUIGONGJU);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.leftfrag_zhengduichuxing, container,
				false);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!TextUtils.isEmpty(App.getInstance().getLeaderHuoDongId())) {// 已选取活动
			initDao();
			ll_chuxing_tianjia.setVisibility(View.GONE);
			chuxing_yitianjia.setVisibility(View.VISIBLE);
			if (!isWorked()) {// 开启服务
				// 下载完成之后开始上传gps队员信息
				getActivity().startService(
						new Intent(getActivity(), UpGpsInfoService.class));
			}
		} else {
			ll_chuxing_tianjia.setVisibility(View.VISIBLE);
			chuxing_yitianjia.setVisibility(View.GONE);
			if (isWorked()) {// 开启服务
				// 关闭服务
				Intent intent2 = new Intent(UpGpsInfoService.actionToStop);
				getActivity().sendBroadcast(intent2);
			}
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		// 队员
		mDeviceName = App.getInstance().getBleDuiYuanName();
		mDeviceAddress = App.getInstance().getBleDuiYuanAddress();
		// 与设备交互
		if (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress())) {
			chuxing_tongxingbao.setText("已关联同行宝:" + mDeviceName + "\t\t点击断开");
			top_tv_right_wode.setClickable(true);
			top_tv_right_wode.setText("正在查询中");
			initBluetooth();
		} else {
			chuxing_tongxingbao.setText("添加同行宝");
			top_tv_right_wode.setText("设备未连接");
			top_tv_right_wode.setClickable(false);
		}

	}

	private boolean isRegister = false;// 判断接收器是否注册

	/**
	 * 数据的本地化初始化
	 */
	public void initDao() {
		// 数据操作
		huoDongXiangQingLeaderList = huoDongXiangQingLeaderDao.queryAll();
		if (huoDongXiangQingLeaderList != null
				&& huoDongXiangQingLeaderList.size() != 0) {
			chuxing_time.setText(TimeDateUtils
					.formatDateFromDatabaseTime(huoDongXiangQingLeaderList.get(
							0).getAct_venue_time()));
			chuxing_start_time
					.setText(TimeDateUtils
							.formatDateFromDatabaseTime(huoDongXiangQingLeaderList
									.get(0).getAct_start_time())
							+ "/"
							+ TimeDateUtils
									.formatDateFromDatabaseTime(huoDongXiangQingLeaderList
											.get(0).getAct_end_time()));
			chuxing_name.setText(huoDongXiangQingLeaderList.get(0)
					.getAct_title());
			chuxing_diqu.setText(Html.fromHtml(huoDongXiangQingLeaderList
					.get(0).getAct_desc()));
			if (!TextUtils.isEmpty(huoDongXiangQingLeaderList.get(0)
					.getClub_name())) {
				chuxing_juli.setText(huoDongXiangQingLeaderList.get(0)
						.getClub_name());
			} else if (!TextUtils.isEmpty(huoDongXiangQingLeaderList.get(0)
					.getLeader_name())) {
				chuxing_juli.setText("领队:"
						+ huoDongXiangQingLeaderList.get(0).getLeader_name());
			}
			try {
				JSONArray jsonArray = new JSONArray(huoDongXiangQingLeaderList
						.get(0).getAct_logo());
				mImageLoader.displayImage("file://"
						+ jsonArray.getJSONObject(0).getString("image"),
						chuxing_image, options);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onInitData() {
		tempByte = new ArrayList<byte[]>();
		// 数据库操作
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(
				huoDongXiangQingLeader, getActivity());
		//
		chuxing_tongxingbao = (TextView) getActivity().findViewById(
				R.id.chuxing_tongxingbao);
		chuxing_image = (ImageView) getActivity().findViewById(
				R.id.chuxing_image);
		chuxing_item = (LinearLayout) getActivity().findViewById(
				R.id.chuxing_item);
		chuxing_yitianjia = (LinearLayout) getActivity().findViewById(
				R.id.chuxing_yitianjia);
		chuxing_time = (TextView) getActivity().findViewById(R.id.chuxing_time);
		chuxing_start_time = (TextView) getActivity().findViewById(
				R.id.chuxing_start_time);
		chuxing_name = (TextView) getActivity().findViewById(R.id.chuxing_name);
		chuxing_diqu = (TextView) getActivity().findViewById(R.id.chuxing_diqu);
		chuxing_juli = (TextView) getActivity().findViewById(R.id.chuxing_juli);
		chuxing_tab1 = (TextView) getActivity().findViewById(R.id.chuxing_tab1);
		chuxing_tab2 = (TextView) getActivity().findViewById(R.id.chuxing_tab2);
		chuxing_tab3 = (TextView) getActivity().findViewById(R.id.chuxing_tab3);
		chuxing_tianjia = (TextView) getActivity().findViewById(
				R.id.chuxing_tianjia);
		ll_chuxing_tianjia = (LinearLayout) getActivity().findViewById(
				R.id.ll_chuxing_tianjia);
	}

	@Override
	protected void onResload() {
		// 右上角按钮，每次进默认此背景
		top_tv_right_wode.setVisibility(View.VISIBLE);

	}

	@Override
	protected void setMyViewClick() {
		chuxing_tianjia.setOnClickListener(this);
		chuxing_tab1.setOnClickListener(this);
		chuxing_tab2.setOnClickListener(this);
		chuxing_tab3.setOnClickListener(this);
		chuxing_item.setOnClickListener(this);
		chuxing_tongxingbao.setOnClickListener(this);
		top_tv_right_wode.setOnClickListener(this);

	}

	@Override
	protected void headerOrFooterViewControl() {
		initMainFooter("整队出行", "同行卫士", "活动管理");
		radiobutton_select_one
				.setBackgroundResource(R.drawable.select_item_down);
		radiobutton_select_one.setChecked(true);
		radiobutton_select_two
				.setBackgroundResource(R.drawable.select_item_downnocheck);
		radiobutton_select_three
				.setBackgroundResource(R.drawable.select_item_downnocheck);
		radiobutton_select_one.setTextColor(getResources().getColor(
				R.color.title));
		radiobutton_select_two.setTextColor(getResources().getColor(
				R.color.title));
		radiobutton_select_three.setTextColor(getResources().getColor(
				R.color.title));
	}

	@Override
	public void onClick(View v) {
		if (!TextUtils.isEmpty(App.getInstance().getAut())) {
			switch (v.getId()) {
			case R.id.chuxing_tongxingbao:
				if (!TextUtils.isEmpty(App.getInstance().getLeaderHuoDongId())) {// 先选取活动
					if (!getActivity().getPackageManager().hasSystemFeature(// 设备是否支持蓝牙设备
							PackageManager.FEATURE_BLUETOOTH_LE)) {
						showToast(getActivity().getResources().getString(
								R.string.ble_not_supported));
					} else {
						if (!TextUtils.isEmpty(App.getInstance()
								.getBleDuiYuanName())) {
							cancleContact();
						} else {
							Bundle bundle = new Bundle();
							bundle.putBoolean("fromTuZhong", true);
							openActivity(ContactMyGuardianAcitivty.class,
									bundle);
						}
					}
				} else {
					showToast("请先选取活动！");
				}
				break;
			case R.id.top_tv_right_wode:
				top_tv_right_wode.setText("请稍等");
				if (isLeader) {// 设为队员
					cancelTeamLeader();
				} else {// 设为领队
					setTeamLeader();
				}
				break;
			case R.id.chuxing_item:
				Bundle bundle = new Bundle();
				bundle.putInt("fromrenwu", CommonUtility.XianShiTab_Leader_NOW);
				bundle.putString("act_id", App.getInstance()
						.getLeaderHuoDongId());
				openActivity(HuoDongXiangQingActivity.class, bundle);
				break;
			case R.id.chuxing_tab1:// 发布实时动态
				if (huoDongXiangQingLeaderList.get(0).getAct_state() == 4) {// 当状态为出行时才能发布实时动态
					openActivity(DisplayDongtaiActivity.class);
				} else {
					showToast("当状态为出行时才能发布实时动态!");
				}

				break;
			case R.id.chuxing_tab2:// 整队点名
				// if
				// (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress()))
				// {
				// if (bleCommon.mConnected) {
				// 需要取消当前的广播接收器
				if (isRegister == true && getActivity() != null) {
					getActivity().unregisterReceiver(mGattUpdateReceiver);
					isRegister = false;
				}
				openActivity(DianMingActivity.class);
				// } else {
				// showToast("设备已断开连接");
				// App.getInstance().setBleDuiYuanAddress("");
				// App.getInstance().setBleDuiYuanName("");
				// chuxing_tongxingbao.setText("添加同行宝");
				// if (bleCommon.mBluetoothLeService != null) {
				// cancleContact();
				// }
				// }
				// } else {
				// showToast("请先关联同行宝！");
				// }

				break;
			case R.id.chuxing_tab3:// 活动设置
				// if
				// (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress()))
				// {
				// if (bleCommon.mConnected) {
				if (isRegister == true && getActivity() != null) {
					getActivity().unregisterReceiver(mGattUpdateReceiver);
					isRegister = false;
				}
				openActivity(SetActivity.class);
				// } else {
				// showToast("设备已断开连接");
				// App.getInstance().setBleDuiYuanAddress("");
				// App.getInstance().setBleDuiYuanName("");
				// chuxing_tongxingbao.setText("添加同行宝");
				// if (bleCommon.mBluetoothLeService != null) {
				// cancleContact();
				// }
				// }
				// } else {
				// showToast("请先关联同行宝！");
				// }
				break;
			case R.id.chuxing_tianjia:// 跳入可选活动界面
				openActivity(KeXuanHuoDongActivity.class);
				break;
			default:
				break;
			}
		} else {
			showToastDengLu();
			openActivity(LoginActivity.class);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (isRegister == true && getActivity() != null) {
			getActivity().unregisterReceiver(mGattUpdateReceiver);
			isRegister = false;
		}
	}

	/**
	 * 取消关联
	 */
	public void cancleContact() {
		App.getInstance().setBleDuiYuanAddress("");
		App.getInstance().setBleDuiYuanName("");
		chuxing_tongxingbao.setText("添加同行宝");
		if (isRegister == true && getActivity() != null) {
			getActivity().unregisterReceiver(mGattUpdateReceiver);
			getActivity().unbindService(mServiceConnection);
			if (mBluetoothLeService != null) {
				mBluetoothLeService.disconnect();
				mBluetoothLeService.close();
				mBluetoothLeService = null;
			}
			isRegister = false;
		}

	}

	/** 判断服务是否启动 */
	public boolean isWorked() {
		ActivityManager myManager = (ActivityManager) getActivity()
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals("com.hwacreate.outdoor.service.UpGpsInfoService")) {
				return true;
			}
		}
		return false;
	}

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Message message = Message.obtain();
			message.obj = intent;
			handler.sendMessageDelayed(message, 3000);// 经过多次测试，我发现只需要在广播接收器中做个延时操作就能保证数据返回成功
														// 2015/12/10/0：50,大概这里就是手机在告诉蓝牙：我需要与你交互吧

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
				queryMode();

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
				// queryMode();
			}
		}
	};
	/**
	 * @param data
	 *            获取需要展示的值 。拿到所有的数据之后再进行其他操作，这里需要一个值来判断是否接收完成
	 */
	private List<byte[]> tempByte = null;

	private void displayData(byte[] bs) {
		// tempByte.add(bs);
		// byte[] newByte = ByteUtil.sysCopy(tempByte);
		System.out.println("RETURN:\n"+HwtxCommandUtility.bytesToHexText(bs));
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
		if (Arrays.equals(tagByte, tagQueryMode)) {// 查询模式返回值
			byte[] mode = HwtxCommandUtility.extractBytesFromBytes(newByte, 4,
					1);
			byte[] byt0 = { 0 };
			byte[] byt1 = { 1 };
			if (Arrays.equals(mode, byt0)) {// 队员模式
				handlerlist.sendEmptyMessage(4);
			} else if (Arrays.equals(mode, byt1)) {// 领队模式
				handlerlist.sendEmptyMessage(1);
			}

		} else if (Arrays.equals(tagByte, tagSetTeamLeader)) {// 设置领队模式返回值,设置了之后继续查询看是否设置成功
			handlerlist.sendEmptyMessage(2);
		} else if (Arrays.equals(tagByte, tagCancelTeamLeader)) {// 设置取消领队模式返回值,设置了之后继续查询看是否设置成功
			handlerlist.sendEmptyMessage(2);
		} else {// TODO 待删
			top_tv_right_wode.setText("请重试");
		}
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
		tempByte.clear();

		setCharacteristic(strWrite, strRead);// TODO
		if (!TextUtils.isEmpty(App.getInstance().getBleDuiYuanAddress())) {
			// if (bleCommon.mConnected) {
			System.out.println("2----------------------------");
			bleSendDataTongXingBao(strSend);
			// }

		} else {
			showToast("设备已断开连接！");
			cancleContact();
		}
	}

	private boolean isLeader = false;
	private Handler handlerlist = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// 当前为领队模式
				top_tv_right_wode.setText("取消领队");
				isLeader = true;
				showToast("当前为领队模式");
				break;
			case 2:
				queryMode();
				break;
			case 4:// 当前为队员模式
				isLeader = false;
				top_tv_right_wode.setText("设为领队");
				showToast("当前为队员模式");
				break;
			default:
				break;
			}

		}
	};

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
		System.out.println("21----------------------------"
				+ mBluetoothLeService);
		UUID_KEY_DATA_WRITE = uUID_KEY_DATA_WRITE;
		UUID_KEY_DATA_READ = uUID_KEY_DATA_READ;
		if (mBluetoothLeService != null) {
			System.out.println("211----------------------------");
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
					System.out.println("22----------------------------");
					// loghxd.e(gattCharacteristic2.getUuid().toString());
				}
			}
			// 获取写入蓝牙设备的特性
			for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
				if (gattCharacteristic.getUuid().toString()
						.equals(UUID_KEY_DATA_WRITE)) {
					mNotifyCharacteristicWrite = gattCharacteristic;
					System.out.println("23----------------------------");
					// loghxd.e(gattCharacteristic.getUuid().toString());
				}
			}// TODO 循环完了之后再进行发数据操作
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
		System.out.println("3----------------------------");
		if (mBluetoothLeService != null) {
			/** 返回数据的特性监听，必须放在往设备写数据之前 */
			if (mNotifyCharacteristicRead != null) {
				mBluetoothLeService.setCharacteristicNotification(
						mNotifyCharacteristicRead, true);
			}
			System.out.println("4----------------------------");
			/** 发送数据命令 */
			new Handler().postDelayed(new Runnable() {
				public void run() {

					if (mBluetoothLeService != null) {
						System.out.println("5----------------------------");
						if(mNotifyCharacteristicWrite!=null){
							mBluetoothLeService.write(mNotifyCharacteristicWrite,
									writeByte);
						}
						
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
	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		Utile.onlyOnce = false;
		isRegister = false;
		// 蓝牙相关
		setCharacteristic(mDeviceAddress);
		// if (mConnected) {
		// // connected = false;
		// // queryMode();//
		// top_tv_right_wode.setText("取消领队");
		// Intent gattServiceIntent = new Intent(getActivity(),
		// BluetoothLeService.class);
		// getActivity().bindService(gattServiceIntent, mServiceConnection,
		// Context.BIND_AUTO_CREATE);
		// } else {
		isRegister = true;
		Intent gattServiceIntent = new Intent(getActivity(),
				BluetoothLeService.class);
		getActivity().bindService(gattServiceIntent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		// 当前界面的广播接收器
		getActivity().registerReceiver(mGattUpdateReceiver,
				makeGattUpdateIntentFilter());
		//
		// }
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				//
				// queryMode();
			}
		}, 3000);// 无论什么情况都延迟3秒
	}

	/**
	 * 查询模式命令
	 */
	private HwtxCommandQueryMode commandqueryMode = null;
	private byte[] tagQueryMode = null;

	private void queryMode() {
		if (commandqueryMode == null) {
			commandqueryMode = new HwtxCommandQueryMode();
		}
		if (tagQueryMode == null) {
			tagQueryMode = commandqueryMode.getCommandTagRaw();
		}
		System.out
				.println("commandqueryMode:\n"
						+ HwtxCommandUtility.bytesToHexText(commandqueryMode
								.toBytes()));
		sendData(commandqueryMode.getBluetoothPropertyUuidSend(),
				commandqueryMode.getBluetoothPropertyUuidRead(),
				commandqueryMode.toBytes());
		System.out.println("1----------------------------");

		// top_tv_right_wode.setText(tagQueryMode+"");
	}

	/**
	 * 设置领队
	 */
	private HwtxCommandLeaderSetting commandsetTeamLeader = null;
	private byte[] tagSetTeamLeader = null;

	private void setTeamLeader() {
		commandsetTeamLeader = new HwtxCommandLeaderSetting();
		tagSetTeamLeader = commandsetTeamLeader.getCommandTagRaw();
		commandsetTeamLeader.enableLeader();
		sendData(commandsetTeamLeader.getBluetoothPropertyUuidSend(),
				commandsetTeamLeader.getBluetoothPropertyUuidRead(),
				commandsetTeamLeader.toBytes());
	}

	/**
	 * 取消领队
	 */
	private HwtxCommandLeaderSetting commandCancelTeamLeader = null;
	private byte[] tagCancelTeamLeader = null;

	private void cancelTeamLeader() {
		commandCancelTeamLeader = new HwtxCommandLeaderSetting();
		tagCancelTeamLeader = commandCancelTeamLeader.getCommandTagRaw();
		commandCancelTeamLeader.disableLeader();
		sendData(commandCancelTeamLeader.getBluetoothPropertyUuidSend(),
				commandCancelTeamLeader.getBluetoothPropertyUuidRead(),
				commandCancelTeamLeader.toBytes());
	}

}
