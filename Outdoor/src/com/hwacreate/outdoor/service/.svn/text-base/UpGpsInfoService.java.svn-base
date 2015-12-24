package com.hwacreate.outdoor.service;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.TimeUtil;
import com.keyhua.outdoor.protocol.SetLeaderLocationAction.SetLeaderLocationRequest;
import com.keyhua.outdoor.protocol.SetLeaderLocationAction.SetLeaderLocationRequestParameter;
import com.keyhua.outdoor.protocol.SetLeaderLocationAction.SetLeaderLocationResponse;
import com.keyhua.outdoor.protocol.SetLeaderLocationAction.SetLeaderLocationResponsePayload;
import com.keyhua.outdoor.protocol.SetUserLocationAction.SetUserLocationRequest;
import com.keyhua.outdoor.protocol.SetUserLocationAction.SetUserLocationRequestParameter;
import com.keyhua.outdoor.protocol.SetUserLocationAction.SetUserLocationResponse;
import com.keyhua.outdoor.protocol.SetUserLocationAction.SetUserLocationResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

public class UpGpsInfoService extends Service {
	//
	private Boolean D = true;

	public UpGpsInfoService() {

	}

	@Override
	public IBinder onBind(Intent intent) {
		if (D) {
			Log.i("GPS服务数据收集", "IBinder()");
		}
		return null;
	}

	// 本地数据
	private HuoDongXiangQingItem huoDongXiangQingItemhuancun = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingItem1Dao = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	//
	/**
	 * 数据的初始化
	 */
	public void initDao() {
		huoDongXiangQingItemhuancun = new HuoDongXiangQingItem();
		huoDongXiangQingItem1Dao = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQingItemhuancun,
				getApplicationContext());
		// 数据操作
		huoDongXiangQingItemhuancun = huoDongXiangQingItem1Dao.searchByActid(App.getInstance().getHuoDongId());

		// 领队
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader,
				getApplicationContext());
		huoDongXiangQingLeader = huoDongXiangQingLeaderDao.searchByActid(App.getInstance().getLeaderHuoDongId());
	}

	String gps_start_time = null;

	@Override
	public void onCreate() {
		if (huoDongXiangQingItemhuancun == null || huoDongXiangQingLeader == null) {
			initDao();
		}
		if (D) {
			Log.i("GPS服务数据收集", "onCreate()");
		}
		// 接收关闭线程请求
		IntentFilter filter = new IntentFilter(UpGpsInfoService.actionToStop);
		registerReceiver(broadcastReceiver, filter);
		super.onCreate();
		// 在没有点停止记录轨迹之前都需要拿到之前存在数据库中的json，这里需要一个值在判断点击状态

	}

	/**
	 * 百度地图Start
	 * ----------------------------------------------------------------
	 */
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "gcj02";
	public MyLocationListener mMyLocationListener;

	// 经纬度
	public void getNowLocation() {
		// 百度地图
		// ,有时候没办法执行mLocationClient.requestLocation()回掉，getApplicationContext()与this换下就行了
		mLocationClient = new LocationClient(getApplicationContext());
		// 放于MyLocationListener()之前
		InitLocation();
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll");
		option.setScanSpan(30 * 1000);// 每10分钟上传一次
//		option.setScanSpan(10 * 60 * 1000);// 每10分钟上传一次
		mLocationClient.setLocOption(option);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();
	}

	/**
	 * 实现实位回调监听
	 */
	double longitude = 0;
	double latitude = 0;

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			longitude = location.getLongitude();
			latitude = location.getLatitude();
			// 向服务器发送自己的经纬度
			if (huoDongXiangQingItemhuancun != null) {// 作为队员领取活动
				if (huoDongXiangQingItemhuancun.getLeader_id().equals(App.getInstance().getUserid())) {// 当前为领队时
					sendAsyn();
				} else {// 当前为队员时
					sendDuiYuanAsyn();
				}
			} else {// 作为领队领取活动
				if (huoDongXiangQingLeader != null) {
					if (huoDongXiangQingLeader.getLeader_id().equals(App.getInstance().getUserid())) {// 当前为领队时
						sendAsyn();
					} else {// 当前为队员时
						sendDuiYuanAsyn();
					}
				}
			}

		}
	}

	/**
	 * 百度地图END
	 * ------------------------------------------------------------------
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (D) {
			Log.i("GPS服务数据收集", "onStartCommand()");
		}
		getNowLocation();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if (D) {
			Log.i("GPS服务数据收集", "onDestroy()");
		}
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
		// 关闭广播、服务时间计时器
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	}

	public static final String action = "com.hwacreate.outdoor.upgpsservice.action";
	public static final String actionToStop = "com.hwacreate.outdoor.upgpsservice.action.stopservice";

	/** 服务中发送广播过来进行绘图 ，暂停或停止 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			stopSelf();
		}
	};
	// 向服务器发送经纬度
	private Thread thread = null;

	// 领队经纬度
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() {
		SetLeaderLocationRequest request = new SetLeaderLocationRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		SetLeaderLocationRequestParameter parameter = new SetLeaderLocationRequestParameter();
		if (huoDongXiangQingItemhuancun != null) {// 作为队员领取时
			parameter.setAct_id(huoDongXiangQingItemhuancun.getAct_id());
		} else {// huoDongXiangQingLeader作为领队领取时
			parameter.setAct_id(huoDongXiangQingLeader.getAct_id());
		}

		parameter.setLeader_latitude(latitude + "");
		parameter.setLeader_longitude(longitude + "");
		parameter.setLocation_time(TimeUtil.getDatetime());
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
					SetLeaderLocationResponse response = new SetLeaderLocationResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					SetLeaderLocationResponsePayload payload = (SetLeaderLocationResponsePayload) response.getPayload();
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

	// 队员经纬度
	public void sendDuiYuanAsyn() {
		thread = new Thread() {
			public void run() {
				ActionDuiYuan();
			}
		};
		thread.start();
	}

	public void ActionDuiYuan() {
		SetUserLocationRequest request = new SetUserLocationRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		SetUserLocationRequestParameter parameter = new SetUserLocationRequestParameter();
		if (huoDongXiangQingItemhuancun != null) {// 作为队员领取时
			parameter.setAct_id(huoDongXiangQingItemhuancun.getAct_id());
		} else {// huoDongXiangQingLeader作为领队领取时
			parameter.setAct_id(huoDongXiangQingLeader.getAct_id());
		}

		parameter.setUser_latitude(latitude + "");
		parameter.setUser_longitude(longitude + "");
		parameter.setLocation_time(TimeUtil.getDatetime());
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
					SetUserLocationResponse response = new SetUserLocationResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					SetUserLocationResponsePayload payload = (SetUserLocationResponsePayload) response.getPayload();
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

	// 服务器返回提示信息
	private String msgString = null;
	private int State = 0;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				break;
			case CommonUtility.SERVERERRORLOGIN:
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
