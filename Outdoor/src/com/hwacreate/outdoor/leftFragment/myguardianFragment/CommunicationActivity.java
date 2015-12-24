package com.hwacreate.outdoor.leftFragment.myguardianFragment;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.afinal.simplecache.ACache;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.hwa.lybd.IHWATOLYXYXL;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

public class CommunicationActivity extends BaseActivity implements OnMenuItemClickListener {
	private CleareditTextView tv_inputcontent = null;// 输入框
	private TextView tv_input_send = null;// 发送按钮
	private String inputcontentStr = null;// 输入的内容
	// 上拉下拉刷新
	private LoadMoreListViewContainer loadMoreListViewContainer = null;
	//
	private ListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	private PtrFrameLayout mPtrFrameLayout;
	// 时间list
	private SimpleDateFormat sDateFormat = null;
	private String date = null;
	private JSONArray jsonArray = null;
	private JSONObject jsonObject = null;
	// 用于数据的缓存
	private ACache mCache = null;

	private String mDeviceName = null;
	private String mDeviceAddress = null;
	private IHWATOLYXYXL iHWATOLYXYXL = null;
	private BleCommon bleCommon = null;
	private String contextnum, frequency;
	private Boolean hm = false;// 中心号码查询标识
	private Boolean tx = false;// 通讯申请标识
	private String context_data, time;// 通信信息内容，时间
	private String xx = new String();// 标记自己或服务器
	private static Integer timerTicket = 0;// 倒计时
	private static long timer = 0;
	private Timer delayTimer = null;
	private TextView tv_choose = null;
	private EditText et_putnum = null;
	private int status = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_communication);
		mCache = ACache.get(CommunicationActivity.this);
		initBluetooth();
		init();
		SharedPreferences share = getSharedPreferences("shijian", Activity.MODE_WORLD_READABLE);
		long lasttime = share.getLong("lasttime", 0);
		if (lasttime != 0) {
			long now = System.currentTimeMillis();
			long s = (System.currentTimeMillis() - lasttime) / 1000;
			if (s < 90 && s > 0) {
				delayTimer = new Timer();
				timer = 90 - s;
				TimerTask delayTask = new TimerTask() {
					@Override
					public void run() {
						handler.post(new Runnable() {
							public void run() {
								if (timer <= 0) {
									tv_input_send.setText("发送");
									tv_input_send.setClickable(true);
									// Cancel Timer
									delayTimer.cancel();
									delayTimer.purge();
									delayTimer = null;
								} else {
									tv_input_send.setText(String.valueOf(timer) + "秒后再次发送");
									tv_input_send.setClickable(false);
								}
								timer = timer - 1;
							}
						});
					}
				};
				delayTimer.schedule(delayTask, 0, 1000);
			}
		}
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

	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {// 连接成功，
				// byte [] b = iHWATOLYXYXL.IXYXLSend_ZDZJ(000000);
				// bleCommon.bleSendDataBeiDou(b);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.tv_input_send:
			// 模拟聊天
			date = sDateFormat.format(new java.util.Date());
			inputcontentStr = tv_inputcontent.getText().toString();
			tv_inputcontent.setText("");
			jsonObject = new JSONObject();
			try {
				jsonObject.put("data", date.toString());
				jsonObject.put("content", inputcontentStr);
				SharedPreferences sharedPreferences = getSharedPreferences("shijian", Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				editor.putLong("lasttime", System.currentTimeMillis());
				editor.commit();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonArray.put(jsonObject);
			listadapter.notifyDataSetChanged();
			lv_home.smoothScrollToPosition(listadapter.getCount() - 1);
			if (!TextUtils.isEmpty(mDeviceAddress)) {// 已关联
				// 中心号码查询
				byte[] szhm = IHWATOLYXYXL.IXYXLSend_SZHM_CX(000000);
				bleCommon.bleSendDataBeiDou(szhm);
				hm = true;
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
			delayTimer = new Timer();
			timerTicket = 90;
			TimerTask delayTask = new TimerTask() {
				@Override
				public void run() {
					handler.post(new Runnable() {
						public void run() {
							if (timerTicket <= 0) {
								tv_input_send.setText("发送");
								tv_input_send.setClickable(true);
								// Cancel Timer
								delayTimer.cancel();
								delayTimer.purge();
								delayTimer = null;
							} else {
								tv_input_send.setText(timerTicket.toString() + "秒后再次发送");
								tv_input_send.setClickable(false);
							}
							timerTicket = timerTicket - 1;
						}
					});
				}
			};
			delayTimer.schedule(delayTask, 0, 1000);
			break;
		case R.id.tv_choose:
			PopupMenu menu = new PopupMenu(this, tv_choose);
			if (status == 0) {
				menu.getMenuInflater().inflate(R.menu.message, menu.getMenu());
				menu.setOnMenuItemClickListener(this);
				menu.show();
			} else if (status == 1) {
				menu.getMenuInflater().inflate(R.menu.message1, menu.getMenu());
				menu.setOnMenuItemClickListener(this);
				menu.show();
			}
			break;
		default:
			break;
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {
				int usernum = Integer.parseInt(msg.obj.toString());
				// 蓝牙通讯申请
				byte[] sjsc = iHWATOLYXYXL.ISend_TXSQ(000000, 2, usernum, "102" + inputcontentStr);
				bleCommon.bleSendDataBeiDou(sjsc);
				tx = true;
			}
		}
	};
	/**
	 * @param data
	 *            获取需要展示的值 。拿到所有的数据之后再进行其他操作，这里需要一个值来判断是否接收完成
	 */
	private List<byte[]> tempByte = new ArrayList<byte[]>();

	private void displayData(byte[] bs) {
		byte[] newByte;

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
						// 频度信息
						frequency = context.getString("frequency");
						hm = false;
						newByte = null;
						tempByte = new ArrayList<byte[]>();
						if (!contextnum.equals("")) {
							new Thread() {
								public void run() {
									handler.sendMessage(handler.obtainMessage(1, contextnum));
								};
							}.start();
						}
					}
					;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 通讯申请返回信息
		if (tx) {
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
						String status = context.getString("status");
						showToast(status);
						tx = false;
						newByte = null;
						tempByte = new ArrayList<byte[]>();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempByte.add(bs);
			newByte = ByteUtil.sysCopy(tempByte);
			try {
				if (newByte[0] == 36) {
					char data5 = (char) newByte[5];
					char data6 = (char) newByte[6];
					String useraddress = "" + (((int) (data5 & 0xff) << 8) + (int) (data6 & 0xff));
					if (newByte.length == Integer.parseInt(useraddress)) {
						String data = IHWATOLYXYXL.IReceive_TXXX(newByte);
						System.out.println("TXXX=======" + data);
						JSONObject json = new JSONObject(data);
						JSONObject context = json.getJSONObject("context");
						context_data = context.getString("context_data");
						time = context.getString("time");
						xx = "1";
						newByte = null;
						tempByte = new ArrayList<byte[]>();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		if (jsonArray != null) {
			mCache.put("communication", jsonArray);
		}
		if (delayTimer != null) {
			delayTimer.cancel();
			delayTimer.purge();
			delayTimer = null;
		}
		super.onDestroy();

	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		sDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");
		tv_inputcontent = (CleareditTextView) findViewById(R.id.tv_inputcontent);
		tv_input_send = (TextView) findViewById(R.id.tv_input_send);
		tv_choose = (TextView) findViewById(R.id.tv_choose);
		et_putnum = (EditText) findViewById(R.id.et_putnum);
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
				if (NetUtil.isNetworkAvailable(CommunicationActivity.this)) {// 有网
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
				if (NetUtil.isNetworkAvailable(CommunicationActivity.this)) {// 有网
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
				// showToastNet();
				break;
			default:
				break;
			}
		}
	};

	// 刷新end------------------------------------------------------------------
	@Override
	protected void onResload() {
		top_tv_title.setText("我的卫士");
		lv_home = (ListView) findViewById(R.id.lv_home);
		listadapter = new MyListAdpter(CommunicationActivity.this);
		jsonArray = mCache.getAsJSONArray("communication");
		if (jsonArray == null) {
			jsonArray = new JSONArray();
		}
		lv_home.setAdapter(listadapter);
		refreshAndLoadMore();
		lv_home.smoothScrollToPosition(listadapter.getCount() - 1);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		tv_input_send.setOnClickListener(this);
		tv_choose.setOnClickListener(this);
	}

	// 自己发的
	final int TYPE_SELF = 0;
	// 服务器发的
	final int TYPE_OTHER = 1;

	/**
	 * @author 曾金叶
	 * @2015-8-6 @上午9:58:49
	 * @category adapter ,写在本activity，不用分出来
	 */
	public class MyListAdpter extends BaseAdapter {
		private Context context = null;
		private LayoutInflater inflater = null;

		public MyListAdpter(Context context) {
			this.context = context;

		}

		@Override
		public int getCount() {
			return jsonArray != null ? jsonArray.length() : 0;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// 每个convert view都会调用此方法，获得当前所需要的view样式
		@Override
		public int getItemViewType(int position) {
			int p = position;
			// if (p % 2 == 0) {
			// return TYPE_SELF;
			// } else {
			// return TYPE_OTHER;
			// }
			if (!xx.equals("1")) {
				return TYPE_SELF;
			} else {
				return TYPE_OTHER;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolderSelf holderSelf = null;
			ViewHolderOther holderOther = null;
			int type = getItemViewType(position);
			if (convertView == null) {
				inflater = LayoutInflater.from(context);
				holderSelf = new ViewHolderSelf();
				holderOther = new ViewHolderOther();
				switch (type) {
				case TYPE_SELF:
					convertView = inflater.inflate(R.layout.item_communication_self_lv, parent, false);
					holderSelf.icon_self = (CubeImageView) convertView.findViewById(R.id.icon);
					holderSelf.time_self = (TextView) convertView.findViewById(R.id.time_self);
					holderSelf.content_self = (TextView) convertView.findViewById(R.id.content_self);
					convertView.setTag(holderSelf);
					break;
				case TYPE_OTHER:
					convertView = inflater.inflate(R.layout.item_communication_other_lv, parent, false);
					holderOther.icon_other = (CubeImageView) convertView.findViewById(R.id.icon);
					holderOther.time_other = (TextView) convertView.findViewById(R.id.time_other);
					holderOther.content_other = (TextView) convertView.findViewById(R.id.content_other);
					convertView.setTag(holderOther);
					break;
				default:
					break;
				}
			} else {
				switch (type) {
				case TYPE_SELF:
					holderSelf = (ViewHolderSelf) convertView.getTag();
					break;
				case TYPE_OTHER:
					holderOther = (ViewHolderOther) convertView.getTag();
					break;
				}

			}
			// holder.icon.loadImage(imageLoader, imagesUrl.get(0));
			// 设置资源
			switch (type) {
			case TYPE_SELF:
				try {
					holderSelf.time_self.setText(jsonArray.getJSONObject(position).getString("data"));
					holderSelf.content_self.setText(jsonArray.getJSONObject(position).getString("content"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TYPE_OTHER:
				holderOther.time_other.setText(time);
				holderOther.content_other.setText(context_data);
				// try {
				// holderOther.time_other.setText(jsonArray.getJSONObject(
				// position).getString("data"));
				// holderOther.content_other.setText(jsonArray.getJSONObject(
				// position).getString("content"));

				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				break;
			}

			return convertView;
		}

		private class ViewHolderSelf {
			private CubeImageView icon_self;
			private TextView time_self, content_self;
		}

		private class ViewHolderOther {
			private CubeImageView icon_other;
			private TextView time_other, content_other;
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.popmenu_pingtai:
			et_putnum.setBackgroundResource(R.drawable.et_num_unchoose);
			et_putnum.setHint("");
			et_putnum.setEnabled(false);
			tv_choose.setText("发送给平台");
			status = 0;
			break;
		case R.id.popmenu_phone:
			et_putnum.setBackgroundResource(R.drawable.solid_video);
			et_putnum.setHint("请输入手机号");
			et_putnum.setHintTextColor(getResources().getColor(R.color.content));
			et_putnum.setEnabled(true);
			tv_choose.setText("发送给手机");
			status = 1;
			break;
		}
		return false;
	}
}
