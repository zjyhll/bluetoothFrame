package com.example.tongxingbaodemo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.bluetooth.le.Utile;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandAddDevice;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandException;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandLeaderSetting;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandQueryMode;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveGps;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveMember;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveSetting;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveWarning;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandSendMemberPrepare;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandSendWarningPrepare;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandUtility;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataFirmwareConfig;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataGroupInfoAllBtApp;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberDeviceInfo;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberInfo;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberInfoApp;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxDataMemberInfoBtApp;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	TextView tv = null;
	Button bt0 = null;
	Button bt1 = null;
	Button bt2 = null;
	Button bt22 = null;
	Button bt3 = null;
	Button bt4 = null;
	Button bt5 = null;
	Button bt6 = null;
	Button bt7 = null;
	Button bt8 = null;
	Button bt9 = null;
	Button bt10 = null;
	private String mDeviceName = null;// 蓝牙名
	private String mDeviceAddress = null;// 蓝牙地址
	private BleCommon bleCommon = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv = (TextView) findViewById(R.id.tv);
		bt0 = (Button) findViewById(R.id.bt0);
		bt1 = (Button) findViewById(R.id.bt1);
		bt2 = (Button) findViewById(R.id.bt2);
		bt22 = (Button) findViewById(R.id.bt22);
		bt3 = (Button) findViewById(R.id.bt3);
		bt4 = (Button) findViewById(R.id.bt4);
		bt5 = (Button) findViewById(R.id.bt5);
		bt6 = (Button) findViewById(R.id.bt6);
		bt7 = (Button) findViewById(R.id.bt7);
		bt8 = (Button) findViewById(R.id.bt8);
		bt9 = (Button) findViewById(R.id.bt9);
		bt10 = (Button) findViewById(R.id.bt10);
		bt0.setOnClickListener(this);
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt22.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		bt5.setOnClickListener(this);
		bt6.setOnClickListener(this);
		bt7.setOnClickListener(this);
		bt8.setOnClickListener(this);
		bt9.setOnClickListener(this);
		bt10.setOnClickListener(this);
		tempByte = new ArrayList<byte[]>();
		initBluetooth();
	}

	protected void openActivity(Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		intent.putExtras(extras);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt0:// 关联设备
			initBluetooth();
			break;
		case R.id.bt1:// 查询模式
			queryMode();
			break;
		case R.id.bt2:// 设为领队
			setTeamLeader();
			break;
		case R.id.bt22:// 取消领队
			cancelTeamLeader();
			break;
		case R.id.bt3:// 同步设备参数表
			hwtxCommandSendGpsPrepare();
			break;
		case R.id.bt4:// 添加入组
			addIn();
			break;
		case R.id.bt5:// 同步队员信息表
			synchronizationInformationTable();
			break;
		case R.id.bt6:// 获取队员信息表
			accessTeamInformationTable();
			break;
		case R.id.bt7:// 同步设备参数表
			hwtxCommandSendGpsPrepare();
			break;
		case R.id.bt8:// 同步告警/失联表
			readysynchronousAlarmLostChart();
			break;
		case R.id.bt9:// 获取告警/失联表
			getAlarmLostContactList();
			break;
		case R.id.bt10:// 获取GPS数据表
			getGPSDataTable();
			break;

		default:
			break;
		}

	}

	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		mDeviceAddress = "DD:41:C0:E9:55:FB"; // TODO
		// 我的
		// mDeviceAddress = "DA:FB:3F:A4:AA:E5";

		isRegister = false;
		// 蓝牙相关
		bleCommon = BleCommon.getInstance();
		bleCommon.setCharacteristic(mDeviceAddress);
		if (bleCommon != null && bleCommon.mConnected) {
			// connected = false;
		} else {
			isRegister = true;
			Intent gattServiceIntent = new Intent(this,
					BluetoothLeService.class);
			bindService(gattServiceIntent, bleCommon.mServiceConnection,
					Context.BIND_AUTO_CREATE);
			// 广播接收器
			registerReceiver(bleCommon.mGattUpdateReceiver,
					bleCommon.makeGattUpdateIntentFilter());
			// 当前界面的广播接收器
			registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
			// new Thread(new Runnable() {
			//
			// @Override
			// public void run() {
			// while (bleCommon.mConnected) {
			// queryMode();
			// }
			// }
			//
			// }).start();
			//
		}
	}

	/**
	 * 查询模式命令
	 */
	private HwtxCommandQueryMode commandqueryMode = null;
	private byte[] tagQueryMode = null;

	private void queryMode() {
		commandqueryMode = new HwtxCommandQueryMode();
		tagQueryMode = commandqueryMode.getCommandTagRaw();
		sendData(commandqueryMode.getBluetoothPropertyUuidSend(),
				commandqueryMode.getBluetoothPropertyUuidRead(),
				commandqueryMode.toBytes());
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

	/** 准备同步队员信息表 */
	HwtxCommandSendMemberPrepare commandSynchronizationInformationTable = null;
	private byte[] tagSynchronizationInformationTable = null;

	public void synchronizationInformationTable() {
		// 需要发送的数据
		final byte[] informationTableByte = InformationTable();
		if (informationTableByte != null) {
			commandSynchronizationInformationTable = new HwtxCommandSendMemberPrepare();
			tagSynchronizationInformationTable = commandSynchronizationInformationTable
					.getCommandTagRaw();
			try {
				commandSynchronizationInformationTable
						.setMemberDataLength(informationTableByte.length);
			} catch (HwtxCommandException e) {
				//
				e.printStackTrace();
			}
			sendData(
					commandSynchronizationInformationTable
							.getBluetoothPropertyUuidSend(),
					commandSynchronizationInformationTable
							.getBluetoothPropertyUuidRead(),
					commandSynchronizationInformationTable.toBytes());
			// 告诉设备需要传的长度之后，再讲设备参数表发送过去,延时几秒
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					sendData(commandSynchronizationInformationTable
							.getBluetoothPropertyUuidSend(),
							commandSynchronizationInformationTable
									.getBluetoothPropertyUuidRead(),
							informationTableByte);
				}
			}, 500);
		}
	}

	/** 队员详细信息表 */
	public byte[] InformationTable() {
		// 5循环加入以上多个队员
		HwtxDataGroupInfoAllBtApp groupInfoAllBtApp1 = new HwtxDataGroupInfoAllBtApp();
		// 本数据结构，除了wCrcGrpInfoAllBtApp之外所有数据的CRC值。(CRC计算：包括从dwSizeInByte开始，到最后一个成员的HWTX_MemberInfo_BtAPP内容）
		groupInfoAllBtApp1.setCrcGrpInfoAllBtAppShort((short) 0x1234);
		// 整个有效表的实际总大小：包括wCrcGrpInfoAllBtApp,dwSizeInByte~到最后一个成员的HWTX_MemberInfo_BtAPP内容。
		groupInfoAllBtApp1.setSizeInByteShort((short) 0x2234);
		// 本结构的数据类型. 应该是: HT_TYPE_GIA
		groupInfoAllBtApp1.setbDataType(0x63);
		// 时间戳。由领队APP每次修改本表时，写入当前系统时间。每次修改表元素，都需要更新为不同的时间戳。----重要，底层FW根据它来判断是否更新了新元素。
		groupInfoAllBtApp1.setRtcTimeInteger(0x32345678);
		// 小组领队宝同行宝总数。（设备总数：包含有领队宝设备、同行宝设备的所有成员。不含无设备的成员或未参加的成员）
		groupInfoAllBtApp1.setGrpCountDeviceByte((byte) 0x42);
		// 实际参加的总人数:含有设备的，无设备；
		groupInfoAllBtApp1.setGrpCountAttendByte((byte) 0x52);
		// 报名总人数。小组总人数：包括有领队宝设备的、有同行宝设备的、无同行宝但参加了本次活动的队员、报名但未参加的队员。
		groupInfoAllBtApp1.setGrpCountTotalByte((byte) 0x62);
		// for循环遍历[1234]，加入5中
		// for (int i = 0; i < signUpUserListGet.size(); i++) {
		/** 组内编号 */
		int devNumInGrou = 1;
		/** 设备编号 */
		String deviceSnString = "ceshi";
		/** 昵称，最大10字节字母或5个汉字 */
		String nickNameString = "ceshi";
		/** 本设备是否领队宝 */
		boolean tDeviceIsLdb = true;
		/** 是否有同行宝设备 */
		boolean deviceReady = true;
		/** 否参加了这次活动 */
		boolean isAttend = true;
		/** 是否启用APP图标 */
		boolean isUseAP = true;
		// 1
		HwtxDataMemberDeviceInfo deviceInfo1 = new HwtxDataMemberDeviceInfo();
		// 2
		HwtxDataMemberInfo memberInfo1 = new HwtxDataMemberInfo();
		try {
			if (devNumInGrou != 0 && deviceInfo1 != null
					&& deviceSnString != null) {
				// 该设备队员的组内编号，范围[1,30]
				deviceInfo1.setDevNumInGroup(devNumInGrou);
				// 本设备是否领队宝（APP确保一个队伍只有一个领队）,0-TXB同行宝（队员）,1-LDB领队宝(领队)；
				deviceInfo1.setDeviceIsLdb(tDeviceIsLdb);
				// 是否有同行宝设备。由领队APP设定：根据strDeviceSN是否为非零来判断，0-无同行宝；1-有同行宝
				deviceInfo1.setDeviceReady(deviceReady);
				memberInfo1.setDevInfo(deviceInfo1);
				// 唯一的设备SN串号，固定6字节的数字，由设备量产时写入。有非0值，则表示该队员有同行宝；如果为0值，则表示该队员无同行宝硬件。
				memberInfo1.setDeviceSnString(deviceSnString);
			} else {
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
		// }
		groupInfoAllBtApp1.addIndexMemberInfoArray(memberInfoBtApp);
		return groupInfoAllBtApp1.toBytes();
	}

	/**
	 * 添加入组 手动与扫描TODO 0xYYYYYYYY 00：入组失败，0xYYYYYYYY 01：入组成功
	 */
	private HwtxCommandAddDevice commandAddIn = null;
	private byte[] tagAddIn = null;

	private void addIn() {
		commandAddIn = new HwtxCommandAddDevice();
		tagAddIn = commandAddIn.getCommandTagRaw();
		try {
			commandAddIn.setDeviceSn("cheshi");
		} catch (HwtxCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendData(commandAddIn.getBluetoothPropertyUuidSend(),
				commandAddIn.getBluetoothPropertyUuidRead(),
				commandAddIn.toBytes());
	}

	/**
	 * 准备同步设备参数
	 */
	private HwtxCommandReceiveSetting commandHwtxCommandSendGpsPrepare = null;
	private byte[] tagHwtxCommandSendGpsPrepare = null;

	private void hwtxCommandSendGpsPrepare() {
		final byte[] equipmentParameterListByte = equipmentParameterList();
		if (equipmentParameterListByte != null) {
			commandHwtxCommandSendGpsPrepare = new HwtxCommandReceiveSetting();
			tagHwtxCommandSendGpsPrepare = commandHwtxCommandSendGpsPrepare
					.getCommandTagRaw();
			try {
				commandHwtxCommandSendGpsPrepare
						.setSettingDataLength(equipmentParameterListByte.length);
			} catch (HwtxCommandException e) {
				//
				e.printStackTrace();
			}
			sendData(
					commandHwtxCommandSendGpsPrepare
							.getBluetoothPropertyUuidSend(),
					commandHwtxCommandSendGpsPrepare
							.getBluetoothPropertyUuidRead(),
					commandHwtxCommandSendGpsPrepare.toBytes());
			// 告诉设备需要传的长度之后，再讲设备参数表发送过去,延时几秒
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					sendData(commandHwtxCommandSendGpsPrepare
							.getBluetoothPropertyUuidSend(),
							commandHwtxCommandSendGpsPrepare
									.getBluetoothPropertyUuidRead(),
							equipmentParameterListByte);
				}
			}, 500);
		}

	}

	/**
	 * 设备参数表
	 */
	private byte[] equipmentParameterList() {
		HwtxDataFirmwareConfig command = new HwtxDataFirmwareConfig();
		command.setbDataType(0x62);
		command.setGpsIntervalInteger(5);
		command.setHktAtIntervalInteger(5);
		command.setLostContactNumInteger(5);
		command.setWarningDistance1Integer(1000);
		command.setWarningDistance2Integer(1200);
		command.setWarningDistance3Integer(1300);
		command.setWarningBatteryPercentInteger(15);
		command.setConfigCRCInteger(0x87654321);
		return command.toBytes();
	}

	/** （同行宝）+（领队宝）获取队员信息表 */
	private HwtxCommandReceiveMember commandAccessTeamInformationTable = null;
	private byte[] tagAccessTeamInformationTable = null;

	private void accessTeamInformationTable() {
		commandAccessTeamInformationTable = new HwtxCommandReceiveMember();
		tagAccessTeamInformationTable = commandAccessTeamInformationTable
				.getCommandTagRaw();
		sendData(
				commandAccessTeamInformationTable
						.getBluetoothPropertyUuidSend(),
				commandAccessTeamInformationTable
						.getBluetoothPropertyUuidRead(),
				commandAccessTeamInformationTable.toBytes());
	}

	/** （领队宝）准备同步告警/失联表 先把所有队员的信息转化为byte（我这边自己规定数据格式）之后，1发送数据大小2发送数据TODO */
	private HwtxCommandSendWarningPrepare commandReadysynchronousAlarmLostChart = null;
	private byte[] tagReadysynchronousAlarmLostChart = null;

	private void readysynchronousAlarmLostChart() {
		final byte[] Warningbyte = "准备同步告警/失联表 ".getBytes();
		commandReadysynchronousAlarmLostChart = new HwtxCommandSendWarningPrepare();
		tagReadysynchronousAlarmLostChart = commandReadysynchronousAlarmLostChart
				.getCommandTagRaw();
		try {
			commandReadysynchronousAlarmLostChart
					.setWarningDataLength(Warningbyte.length);
		} catch (HwtxCommandException e) {
			//
			e.printStackTrace();
		}
		sendData(
				commandReadysynchronousAlarmLostChart
						.getBluetoothPropertyUuidSend(),
				commandReadysynchronousAlarmLostChart
						.getBluetoothPropertyUuidRead(),
				commandReadysynchronousAlarmLostChart.toBytes());
		// 告诉设备需要传的长度之后，再讲设备参数表发送过去,延时几秒
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				sendData(commandReadysynchronousAlarmLostChart
						.getBluetoothPropertyUuidSend(),
						commandReadysynchronousAlarmLostChart
								.getBluetoothPropertyUuidRead(), Warningbyte);
			}
		}, 500);
	}

	/** （同行宝）获取告警/失联表 1设备会先返回数据大小2返回数据TODO */
	private HwtxCommandReceiveWarning commandGetAlarmLostContactList = null;
	private byte[] tagGetAlarmLostContactList = null;

	private void getAlarmLostContactList() {
		commandGetAlarmLostContactList = new HwtxCommandReceiveWarning();
		tagGetAlarmLostContactList = commandGetAlarmLostContactList
				.getCommandTagRaw();
		sendData(commandGetAlarmLostContactList.getBluetoothPropertyUuidSend(),
				commandGetAlarmLostContactList.getBluetoothPropertyUuidRead(),
				commandGetAlarmLostContactList.toBytes());
	}

	/** 准备获取GPS数据表 */
	// private void getGPSDataGpsPrepare() {
	// HwtxCommandSendGpsPrepare command = new HwtxCommandSendGpsPrepare();
	// sendData(command.getBluetoothPropertyUuidSend(),
	// command.getBluetoothPropertyUuidRead(), command.toBytes());
	// }

	/** （领队宝） 获取GPS数据表 每个从硬件获取数据的操作，1硬件都会先返回数据的大小，2再返回数据TODO */
	private HwtxCommandReceiveGps commandGetGPSDataTable = null;
	private byte[] tagGetGPSDataTable = null;

	private void getGPSDataTable() {
		commandGetGPSDataTable = new HwtxCommandReceiveGps();
		tagGetGPSDataTable = commandGetGPSDataTable.getCommandTagRaw();
		sendData(commandGetGPSDataTable.getBluetoothPropertyUuidSend(),
				commandGetGPSDataTable.getBluetoothPropertyUuidRead(),
				commandGetGPSDataTable.toBytes());
	}

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {// 连接成功，步骤1连接2获取读写特性，所以这步不能做发送数据操作
				/**
				 * 3) 查询模式：向UUID为0x0011特性写数据0xYYYYYYYY 48575458 02
				 * 000000000000发送查询模式命令
				 */
				queryMode();
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED// 未关联成功
					.equals(action)) {
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
				queryMode();
			}
		}
	};
	/**
	 * @param data
	 *            获取需要展示的值 。拿到所有的数据之后再进行其他操作，这里需要一个值来判断是否接收完成
	 */
	private List<byte[]> tempByte = null;

	private void displayData(byte[] bs) {
		tempByte.add(bs);
		byte[] newByte = ByteUtil.sysCopy(tempByte);
		String byteRead = null;
		try {
			byteRead = new String(newByte, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 返回的tag值
		byte[] tagByte = HwtxCommandUtility
				.extractBytesFromBytes(newByte, 0, 4);
		if (Arrays.equals(tagByte, tagQueryMode)) {// 查询模式返回值
			byte[] mode = HwtxCommandUtility.extractBytesFromBytes(newByte, 5,
					1);
			if (Arrays.equals(mode, new byte[00])) {// 队员模式
				handlerlist.sendEmptyMessage(4);
			} else if (Arrays.equals(mode, new byte[01])) {// 领队模式
				handlerlist.sendEmptyMessage(1);
			}
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagSetTeamLeader)) {// 设置领队模式返回值,设置了之后继续查询看是否设置成功
			handlerlist.sendEmptyMessage(2);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagCancelTeamLeader)) {// 设置取消领队模式返回值,设置了之后继续查询看是否设置成功
			handlerlist.sendEmptyMessage(2);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagSynchronizationInformationTable)) {// 同步队员信息表返回值
			handlerlist.sendEmptyMessage(5);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagAddIn)) {// 添加入组返回值
			handlerlist.sendEmptyMessage(6);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagHwtxCommandSendGpsPrepare)) {// 设置设备参数返回值
			handlerlist.sendEmptyMessage(7);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagAccessTeamInformationTable)) {// 获取队员信息表
			handlerlist.sendEmptyMessage(8);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagReadysynchronousAlarmLostChart)) { // 准备同步告警/失联表：
			handlerlist.sendEmptyMessage(9);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagGetAlarmLostContactList)) {// 获取告警/失联表
			handlerlist.sendEmptyMessage(10);
			tv.setText(Utile.byte2HexStr(newByte));
		} else if (Arrays.equals(tagByte, tagGetGPSDataTable)) {// 获取GPS数据表
			handlerlist.sendEmptyMessage(11);
			tv.setText(Utile.byte2HexStr(newByte));
		} else {//
			tv.setText("tag不同");
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

		if (bleCommon != null) {
			bleCommon.setCharacteristic(strWrite, strRead);
			if (!TextUtils.isEmpty(mDeviceAddress) && bleCommon.mConnected) {
				bleCommon.bleSendDataTongXingBao(strSend);
			} else {
				cancleContact();
			}
		} else {
		}
	}

	private boolean isLeader = false;
	private Handler handlerlist = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// 当前为领队模式
				tv.setText("取消领队");
				isLeader = true;
				break;
			case 2:
				queryMode();
				break;
			case 4:// 当前为队员模式
				isLeader = false;
				tv.setText("设为领队");
				break;
			case 5:// 同步队员信息表返回值
				break;
			case 6:// 添加入组返回值
				break;
			case 7:// 设置设备参数返回值
				break;
			case 8:// 获取队员信息表
				break;
			case 9:// 准备同步告警/失联表
				break;
			case 10:// 获取告警/失联表
				break;
			case 11:// 获取GPS数据表
				break;
			default:
				break;
			}

		}
	};

	private IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED_AGIAN);
		return intentFilter;
	}

	private boolean isRegister = false;// 判断接收器是否注册

	/**
	 * 取消关联
	 */
	public void cancleContact() {
		// if (isRegister == true) {
		// unregisterReceiver(mGattUpdateReceiver);
		// unregisterReceiver(bleCommon.mGattUpdateReceiver);
		// unbindService(bleCommon.mServiceConnection);
		// if (bleCommon.mBluetoothLeService != null) {
		// bleCommon.mBluetoothLeService.disconnect();
		// bleCommon.mBluetoothLeService.close();
		// bleCommon.mBluetoothLeService = null;
		// bleCommon = null;
		// }
		// isRegister = false;
		// }

	}
}
