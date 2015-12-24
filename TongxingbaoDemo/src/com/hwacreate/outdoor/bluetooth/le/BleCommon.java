package com.hwacreate.outdoor.bluetooth.le;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.example.tongxingbaodemo.R;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BleCommon {
	// 北斗设备
	public static final String UUID_KEY_DATA_WRITE_BEIDOU = "0000ffe1-0000-1000-8000-00805f9b34fb";
	public static final String UUID_KEY_DATA_READ_BEIDOU = "0000ffe1-0000-1000-8000-00805f9b34fb";
	// 同行卫士，两值相同
	// public static final String UUID_KEY_DATA_WRITE_TONGXING =
	// "91cc0002-a393-3087-7d75-da3752217337";
	// public static final String UUID_KEY_DATA_READ_TONGXING =
	// "91cc0003-a393-3087-7d75-da3752217337";
	// 服务
	public BluetoothLeService mBluetoothLeService;
	// 读写特性
	private BluetoothGattCharacteristic mNotifyCharacteristicWrite;
	private BluetoothGattCharacteristic mNotifyCharacteristicRead;
	// 同行卫士或北斗设备的UUID，根据具体的动作动态更改
	private String UUID_KEY_DATA_WRITE = null;
	private String UUID_KEY_DATA_READ = null;
	private String mDeviceAddress = null;
	public boolean mConnected = false;

	/**
	 * @param mDeviceAddress
	 *            蓝牙设备地址
	 */
	public void setCharacteristic(String mDeviceAddress) {
		this.mDeviceAddress = mDeviceAddress;
	}

	/**
	 * @param mDeviceAddress
	 *            蓝牙设备地址
	 * @param UUID_KEY_DATA_READ
	 *            从蓝牙设备读取返回数据
	 * @param UUID_KEY_DATA_WRITE
	 *            从app往蓝牙设备写数据
	 */
	public void setCharacteristic(String mDeviceAddress,
			String uUID_KEY_DATA_WRITE, String uUID_KEY_DATA_READ) {
		this.mDeviceAddress = mDeviceAddress;
		UUID_KEY_DATA_WRITE = uUID_KEY_DATA_WRITE;
		UUID_KEY_DATA_READ = uUID_KEY_DATA_READ;
	}

	/**
	 * @param mDeviceAddress
	 *            蓝牙设备地址
	 */
	public void setCharacteristic(String uUID_KEY_DATA_WRITE,
			String uUID_KEY_DATA_READ) {
		UUID_KEY_DATA_WRITE = uUID_KEY_DATA_WRITE;
		UUID_KEY_DATA_READ = uUID_KEY_DATA_READ;
	}

	private static BleCommon mInstance = null;

	public static BleCommon getInstance() {

		if (mInstance == null) {
			mInstance = new BleCommon();
		}
		return mInstance;
	}

	// Code to manage Service lifecycle.
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

	public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
				mConnected = true;
				// invalidateOptionsMenu();
			} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
					.equals(action)) {
				mConnected = false;
				// invalidateOptionsMenu();
				// clearUI();
			} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED// 发现设备进行关联
					.equals(action)) {
				// Show all the supported services and characteristics on the
				// user interface.
				if (mBluetoothLeService != null) {
					displayGattServices(mBluetoothLeService
							.getSupportedGattServices());
				}

				// displayGattServices(mBluetoothLeService2
				// .getSupportedGattServices());
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {// 返回的值
				displayData(intent
						.getByteArrayExtra(BluetoothLeService.EXTRA_DATA));
			}
		}
	};

	private void updateConnectionState(final int resourceId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// mConnectionState.setText(resourceId);
			}
		});
	}

	/**
	 * @param data
	 *            获取需要展示的值 。拿到所有的数据之后再进行其他操作，这里需要一个值来判断是否接收完成
	 */
	List<byte[]> tempByte = new ArrayList<byte[]>();

	private void displayData(byte[] bs) {

		tempByte.add(bs);
		byte[] newByte = ByteUtil.sysCopy(tempByte);
		try {
			System.out.println("data:----" + new String(newByte, "GB2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	/** 发送大于20字节或小于20字节数据方法 */
	public void bleSendDataBeiDou(String str) {
		byte[] writeByte = null;
		try {
			writeByte = str.getBytes("gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** 返回数据的特性监听，必须放在往设备写数据之前 */
		mBluetoothLeService.setCharacteristicNotification(
				mNotifyCharacteristicRead, true);
		/** 发送数据命令 */
		mBluetoothLeService.write(mNotifyCharacteristicWrite, writeByte);

	}

	/** 发送大于20字节或小于20字节数据方法 */
	public void bleSendDataBeiDou(byte[] writeByte) {
		/** 返回数据的特性监听，必须放在往设备写数据之前 */
		mBluetoothLeService.setCharacteristicNotification(
				mNotifyCharacteristicRead, true);
		/** 发送数据命令 */
		mBluetoothLeService.write(mNotifyCharacteristicWrite, writeByte);

	}

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
			mBluetoothLeService.setCharacteristicNotification(
					mNotifyCharacteristicRead, true);
			/** 发送数据命令 */
//			new Handler().postDelayed(new Runnable() {
//				public void run() {
					mBluetoothLeService.write(mNotifyCharacteristicWrite,
							writeByte);
//				}
//			}, 500);// 需要延时操作
			/** 接收数据命令 测试用 */
			 new Handler().postDelayed(new Runnable() {
			 public void run() {
			 byte[] readByte = Utile.hexStr2Bytes("36363636");
			 mBluetoothLeService.write(mNotifyCharacteristicWrite,
			 readByte);
			 }
			 }, 500);// 需要延时操作
		}

	}

	public IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		// 保留
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		// intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		// intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED_AGIAN);
		return intentFilter;
	}
}
