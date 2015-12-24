package com.hwacreate.outdoor.leftFragment.myguardianFragment;

import java.util.ArrayList;
import java.util.List;

import com.hwa.lybd.IHWATOLYXYXL;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.protocol.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

public class SetBanLvActivity extends BaseActivity {
	private View parentView = null;
	private CleareditTextView ban_lv_name, ban_lv_center_num, ban_lv_center_pinlv;
	private Button ban_lv_baocun, ban_lv_jiance;
	private String mDeviceName = null;
	private String mDeviceAddress = null;
	private IHWATOLYXYXL iHWATOLYXYXL = null;
	private BleCommon bleCommon = null;
	private String contextnum, frequency;
	private Boolean ly = false;// 蓝牙设置标识
	private Boolean hm = false;// 中心号码查询标识
	private Boolean hmsh = false;// 中心号码设置标识
	private Boolean gj = false;// 执行关机标识
	private String status = new String();// 设置中心号码返回状态
	private String instruction = new String();// 设置蓝牙名称返回状态
	private String newname;// 新的蓝牙名字

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_set_ban_lv);
		parentView = getLayoutInflater().inflate(R.layout.activity_set_ban_lv, null);
		setContentView(parentView);
		init();
		initBluetooth();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			finish();
			break;
		case R.id.ban_lv_jiance:
			byte[] szhm = IHWATOLYXYXL.IXYXLSend_SZHM_CX(000000);
			bleCommon.bleSendDataBeiDou(szhm);
			hm = true;
			break;
		case R.id.ban_lv_baocun:
			newname = ban_lv_name.getText().toString();
			String newnum = ban_lv_center_num.getText().toString();
			String pinlv = ban_lv_center_pinlv.getText().toString();
			int intpin = 0;
			if (TextUtils.isEmpty(pinlv)) {
				intpin = 0;
			} else {
				intpin = Integer.parseInt(pinlv);
			}
			int cha = intpin - 300;
			// 设置蓝牙名称

			if (TextUtils.isEmpty(ban_lv_name.getText().toString())) {
				showToast("请输入名称");
			} else if (newnum.length() != 6) {
				showToast("请设置六位数字的中心号码");

			} else if (cha < 0) {
				showToast("定位频率请不小于300");
			} else if (!newname.equals(mDeviceName) && !TextUtils.isEmpty(ban_lv_name.getText().toString())) {
				try {
					byte[] btsz = IHWATOLYXYXL.IXYXLSend_BTSZ(000000, newname);
					bleCommon.bleSendDataBeiDou(btsz);
					ly = true;
				} catch (NullPointerException e) {
					// TODO: handle exception
					showToast("请检查蓝牙设备");
				}

			} else {
				if (!newnum.equals(contextnum) && newnum != "" || !pinlv.equals(frequency) && pinlv != "") {
					int newcontentnum = Integer.parseInt(newnum);
					int newfrequency = Integer.parseInt(pinlv);
					try {
						byte[] hmsz = IHWATOLYXYXL.IXYXLSend_SZHM_SZ(000000, newcontentnum, newfrequency);
						bleCommon.bleSendDataBeiDou(hmsz);
						hmsh = true;
					} catch (NullPointerException e) {
						// TODO: handle exception
						showToast("请检查蓝牙设备");
					}

				}else {
					showToast("保存成功");
				}
			}

			break;
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				App.getInstance().setBleLingDuiName(newname);
				showToast("设置成功");
				// finish();
			}
		};
	};

	@Override
	protected void onInitData() {
		// TODO Auto-generated method stub
		initHeaderOther();
		ban_lv_baocun = (Button) findViewById(R.id.ban_lv_baocun);
		ban_lv_jiance = (Button) findViewById(R.id.ban_lv_jiance);
		ban_lv_name = (CleareditTextView) findViewById(R.id.ban_lv_name);
		ban_lv_center_num = (CleareditTextView) findViewById(R.id.ban_lv_center_num);
		ban_lv_center_pinlv = (CleareditTextView) findViewById(R.id.ban_lv_center_pinlv);

	}

	@Override
	protected void onResload() {
		top_tv_title.setText("设置");

	}

	@Override
	protected void setMyViewClick() {
		// TODO Auto-generated method stub
		top_itv_back.setOnClickListener(this);
		ban_lv_baocun.setOnClickListener(this);
		ban_lv_jiance.setOnClickListener(this);
	}

	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		iHWATOLYXYXL = new IHWATOLYXYXL();
		// 蓝牙相关
		mDeviceName = App.getInstance().getBleLingDuiName();
		ban_lv_name.setText(mDeviceName);
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

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {// 连接成功，

			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED// 未关联成功
					.equals(action)) {
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
		// 蓝牙设置返回信息
		if (ly) {
			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String btxx = IHWATOLYXYXL.IXYXLReceive_BTXX(newByte);
						JSONObject json = new JSONObject(btxx);
						JSONObject context = json.getJSONObject("context");
						String context_data = context.getString("context_data");
						instruction = context.getString("instruction");
						System.out.println("context_data:" + context_data + ";instruction=" + instruction);
						if (instruction.equals("1")) {
							showToast("保存成功");
							App.getInstance().setBleLingDuiName(ban_lv_name.getEditableText().toString());
							setResult(RESULT_OK);
							finish();
						} else {
							showToast("保存失败");
						}

						ly = false;
						newByte = null;
						tempByte = new ArrayList<byte[]>();
					}
					;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 中心号码查询返回信息
		if (hm) {
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
						ban_lv_center_num.setText(contextnum);
						// 频度信息
						frequency = context.getString("frequency");
						ban_lv_center_pinlv.setText(frequency);
						System.out.println("hmxx======" + context.toString());
						hm = false;
						newByte = null;
						tempByte = new ArrayList<byte[]>();
					}
					;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 设置中心号码返回信息
		if (hmsh) {
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
						if (contextnum.equals(ban_lv_center_num.getText().toString())) {
							new Thread() {
								public void run() {
									handler.sendMessage(handler.obtainMessage(1));
								};
							}.start();
						} else {
							showToast("保存失败");
						}
						hmsh = false;
						newByte = null;
						tempByte = new ArrayList<byte[]>();
						// String fkxx = IHWATOLYXYXL.IReceive_FKXX(newByte);
						// JSONObject json = new JSONObject(fkxx);
						// JSONObject context = json.getJSONObject("context");
						// // 返回状态
						// status = context.getString("status");
						// System.out.println("");
						// if (status.equals("自定义返回信息")) {
						// new Thread() {
						// public void run() {
						// handler.sendMessage(handler.obtainMessage(1,
						// status));
						// };
						// }.start();
						// }
						//
						// hmsh = false;
						// newByte = null;
						// tempByte = new ArrayList<byte[]>();
					}
					;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
