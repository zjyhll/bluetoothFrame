package com.hwacreate.outdoor.leftFragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.bean.HuoDongDuiYuan;
import com.hwacreate.outdoor.bluetooth.le.BleCommon;
import com.hwacreate.outdoor.bluetooth.le.BluetoothLeService;
import com.hwacreate.outdoor.bluetooth.le.ByteUtil;
import com.hwacreate.outdoor.bluetooth.le.Utile;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandException;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveGps;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveMember;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandReceiveWarning;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandSendWarningPrepare;
import com.hwacreate.outdoor.bluetooth.protocol.HwtxCommandUtility;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.leftFragment.gongju.SetActivity;
import com.hwacreate.outdoor.leftFragment.tuzhong.DownOfflineActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.ormlite.bean.GpsInfo;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.bean.SignUpUser;
import com.hwacreate.outdoor.ormlite.bean.TuZhongUser;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.service.GpsInfoCollectionService;
import com.hwacreate.outdoor.service.UpGpsInfoService;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.ParseOject;
import com.hwacreate.outdoor.utl.SPUtils;
import com.hwacreate.outdoor.utl.TimeUtil;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.view.CustomDialog;
import com.hwacreate.outdoor.view.MyListView;
import com.hwacreate.outdoor.view.ReboundScrollView;
import com.keyhua.outdoor.protocol.GetActConfigAction.GetActConfigRequest;
import com.keyhua.outdoor.protocol.GetActConfigAction.GetActConfigRequestParameter;
import com.keyhua.outdoor.protocol.GetActConfigAction.GetActConfigResponse;
import com.keyhua.outdoor.protocol.GetActConfigAction.GetActConfigResponsePayload;
import com.keyhua.outdoor.protocol.GetActUsersAndLeaderLocationAction.GetActUsersAndLeaderLocationLeaderLocationItem;
import com.keyhua.outdoor.protocol.GetActUsersAndLeaderLocationAction.GetActUsersAndLeaderLocationRequest;
import com.keyhua.outdoor.protocol.GetActUsersAndLeaderLocationAction.GetActUsersAndLeaderLocationRequestParameter;
import com.keyhua.outdoor.protocol.GetActUsersAndLeaderLocationAction.GetActUsersAndLeaderLocationResponse;
import com.keyhua.outdoor.protocol.GetActUsersAndLeaderLocationAction.GetActUsersAndLeaderLocationResponsePayload;
import com.keyhua.outdoor.protocol.GetActUsersAndLeaderLocationAction.GetActUsersAndLeaderLocationUserLocationItem;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

/**
 * @author 曾金叶
 * @2015-8-5 @上午10:15:40
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class TuZhongFragment extends BaseFragment implements
		OnCheckedChangeListener {
	// int count=-0;
	private MapView mMapView = null;
	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	private BitmapDescriptor mCurrentMarker;
	// double distance=0;

	private BaiduMap mBaiduMap;
	private LatLng p2;
	// UI相关
	private ImageView requestLocButton;
	// 开始记录轨迹
	private ImageView iv_kaishihuaguiji;
	// 记录中轨迹
	private ImageView iv_jilutinghuaguiji;
	// 暂停记录轨迹
	private ImageView iv_zantinghuaguiji;
	// 停止记录轨迹
	private ImageView iv_tingzhi;
	// 下载离线地图
	private ImageView iv_xiazai;
	// 定位所有人员
	private ImageView iv_xianshisuoyouren;
	// 地图图层显示
	private ImageView iv_dituleixing;
	boolean isFirstLoc = true;// 是否首次定位
	// 自定义放大缩小控件
	private Button zoomInBtn;
	private Button zoomOutBtn;
	private float currentZoomLevel = 0;
	List<GpsInfo> infos;
	List<GpsInfo> infosMy;
	// 轨迹数据库操作
	private BaseDao<GpsInfo> mGpSinfoDao;
	private GpsInfo info = null;
	// 选择图层类别
	private LinearLayout ll_dtlx = null;
	// 参考轨迹的显示与隐藏
	private LinearLayout ll_ckgj = null;
	// 刷新按钮
	private ImageView tv_refresh = null;
	// 失联提示
	private TextView tv_tishi = null;
	// 选择当前参考轨迹
	private TextView tv_ckgj = null;
	// 普通地图
	private TextView tv_ptdt = null;
	// 卫星地图
	private TextView tv_wxdt = null;
	// 交通地图
	private TextView tv_close = null;
	// 计划轨迹
	private ToggleButton tb_jhgj = null;
	// 我的轨迹
	private ToggleButton tb_wdgj = null;
	// 交通图
	private ToggleButton tb_jtdt = null;
	// 点击右上角显示人员列表
	private MyListView lv = null;
	// 失联队员列表
	private MyListView lv_shilian = null;
	private ReboundScrollView rsv = null;
	private RelativeLayout rl = null;
	// lv中数据,需更改bean
	private MyListAdpter listadapter = null;
	private CommonAdapter<TuZhongUser> listadaptersl = null;
	private List<TuZhongUser> tuZhongUserListGet = null;
	private List<TuZhongUser> tuZhongUserListTemp = null;
	private TuZhongUser zhongUser = null;
	//
	private String mDeviceName = null;// 蓝牙名
	private String mDeviceAddress = null;// 蓝牙地址
	// 展示所有人员信息
	// 初始化全局 bitmap 信息，不用时及时 recycle
	private BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_st);
	private BitmapDescriptor bdB = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_en);
	private BitmapDescriptor bdC = BitmapDescriptorFactory
			.fromResource(R.drawable.foot_turnpoint);
	// 实时获取头像
	private BitmapDescriptor bdD = null;
	private List<LatLng> latLngs = null;
	private List<String> latLngsStr = null;
	private boolean showDuiyuan = true;
	// 本地数据（队员）
	private HuoDongXiangQingItem huoDongXiangQingItemhuancun = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingItem1Dao = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	// 参考轨迹
	private String TempTrace_data = null;//
	private JSONArray arrayTempTrace_data = null;
	// 蓝牙设备配置
	private JSONObject hwtxCommandjsonObject = null;
	/** GPS工作间隔[1,60] */
	private Integer bGpsInterval = null;
	/** 广播时间间隔[5,60] */
	private Integer bHktAtInterval = null;
	/** 失联次数 [1-10] */
	private Integer bLostContactNum = null;
	/** 黄色警告距离 范围[10, 5000] */
	private Integer wWarningDistance1 = null;
	/** 红色警告距离 */
	private Integer wWarningDistance2 = null;
	/** 失联警告距离 */
	private Integer wWarningDistance3 = null;
	/** 电量告警 */
	private Integer bWarningBatteryPercent = null;

	/**
	 * 数据的初始化
	 */
	public void initDao() {
		
		// 获取队员数据库
		signUpUserGet = new SignUpUser();
		signUpUserDaoGet = new BaseDao<SignUpUser>(signUpUserGet, getActivity());
		// 拿取数据
		signUpUserListGet = signUpUserDaoGet.queryAll();
		// 失联人员列表
		zhongUser = new TuZhongUser();
		tuZhongUserDaoSet = new BaseDao<TuZhongUser>(zhongUser, getActivity());
		// 数据操作
		huoDongXiangQingItemhuancun = new HuoDongXiangQingItem();
		huoDongXiangQingItem1Dao = new BaseDao<HuoDongXiangQingItem>(
				huoDongXiangQingItemhuancun, getActivity());
		huoDongXiangQingItemhuancun = huoDongXiangQingItem1Dao
				.searchByActid(App.getInstance().getHuoDongId());
		// 领队
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(
				huoDongXiangQingLeader, getActivity());
		huoDongXiangQingLeader = huoDongXiangQingLeaderDao.searchByActid(App
				.getInstance().getLeaderHuoDongId());
		// 轨迹信息数据库
		info = new GpsInfo();
		mGpSinfoDao = new BaseDao(info, getActivity());
		// 参考轨迹
		// 拿到轨迹文件 infosTemp
		if (huoDongXiangQingItemhuancun != null) {
			TempTrace_data = huoDongXiangQingItemhuancun.getTrace_data();
			try {
				arrayTempTrace_data = new JSONArray(TempTrace_data);
			} catch (JSONException e) {
				//
				e.printStackTrace();
			}
		} else {// huoDongXiangQingLeader
			TempTrace_data = huoDongXiangQingLeader.getTrace_data();
			try {
				arrayTempTrace_data = new JSONArray(TempTrace_data);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (!isWorkedUpGpsInfoService()) {// 开启服务
			// 下载完成之后开始上传gps队员信息
			getActivity().startService(
					new Intent(getActivity(), UpGpsInfoService.class));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.getInstance().setBottonChoice(CommonUtility.TUZHONG);
		initDao();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.leftfrag_tuzhong, null);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		getActivity().unregisterReceiver(broadcastReceiver);
		App.getInstance().setMyGuiJi(false);
		App.getInstance().setJiHuaGuiJi(false);
		super.onDestroy();
	}

	/** Dialog 暂停 */
	public void showAlertDialog() {
		final CustomDialog dialog = new CustomDialog(getActivity(),
				R.style.Dialog);
		dialog.setCanceledOnTouchOutside(false);
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.dt_dialog_view, null);
		dialog.addContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		TextView tv_dialog_btn1 = (TextView) view
				.findViewById(R.id.tv_dialog_btn1);
		tv_dialog_btn1.setText("暂停记录当前轨迹");
		TextView tv_dialog_btn2 = (TextView) view
				.findViewById(R.id.tv_dialog_btn2);
		TextView tv_dialog_btn3 = (TextView) view
				.findViewById(R.id.tv_dialog_btn3);
		dialog.show();
		tv_dialog_btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 标记为暂停
				SPUtils.put(getActivity(), "dt_status", ZANTING);
				dialog.dismiss();
				// 暂停状态
				iv_zantinghuaguiji.setVisibility(View.VISIBLE);
				iv_jilutinghuaguiji.setVisibility(View.GONE);
				// 关闭服务
				Intent intent2 = new Intent(
						GpsInfoCollectionService.actionToStop);
				getActivity().sendBroadcast(intent2);
			}
		});
		// 停止
		tv_dialog_btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showdialog();
				// 标记为未开始
				SPUtils.put(getActivity(), "dt_status", WEIKAISHI);
				// 停止时间
				String end_time = TimeUtil.getDatetime();
				mGpSinfoDao.updateEndtime(end_time, (String) SPUtils.get(
						getActivity(), "gps_start_time", ""));
				dialog.dismiss();
				// 恢复到最初状态
				iv_jilutinghuaguiji.setVisibility(View.GONE);
				iv_kaishihuaguiji.setVisibility(View.VISIBLE);
				// 关闭服务
				Intent intent2 = new Intent(
						GpsInfoCollectionService.actionToStop);
				getActivity().sendBroadcast(intent2);
				for (int i = 0; i < resultPoints.size(); i++) {
					if (i == resultPoints.size() - 1) {
						overlayOptions = new MarkerOptions()
								.position(resultPoints.get(i)).icon(bdB)
								.draggable(false).perspective(true);

					}
					mBaiduMap.addOverlay(overlayOptions);
				}
				if (isshowdialog()) {
					closedialog();
				}
				//
				showAlertDialogInputGpsName();
			}

		});
		// 取消
		tv_dialog_btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	AlertDialog dialog2 = null;

	/** Dialog 暂停状态下弹出的对话框 */
	public void showAlertDialogCancle() {

		final CustomDialog dialog = new CustomDialog(getActivity(),
				R.style.Dialog);
		dialog.setCanceledOnTouchOutside(false);
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.dt_dialog_view, null);
		dialog.addContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		TextView tv_dialog_btn1 = (TextView) view
				.findViewById(R.id.tv_dialog_btn1);
		tv_dialog_btn1.setText("继续记录当前轨迹");
		TextView tv_dialog_btn2 = (TextView) view
				.findViewById(R.id.tv_dialog_btn2);
		TextView tv_dialog_btn3 = (TextView) view
				.findViewById(R.id.tv_dialog_btn3);
		dialog.show();
		// 开始记录
		tv_dialog_btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 标记为记录中
				SPUtils.put(getActivity(), "dt_status", JILUZHONG);
				dialog.dismiss();
				iv_zantinghuaguiji.setVisibility(View.GONE);
				// 记录中按钮显示出来
				iv_jilutinghuaguiji.setVisibility(View.VISIBLE);
				// iv_jilutinghuaguiji.setVisibility(View.VISIBLE);
				// 开启服务，记录轨迹到数据库
				getActivity().startService(
						new Intent(getActivity(),
								GpsInfoCollectionService.class));
				showTrack();//
			}
		});
		// 停止
		tv_dialog_btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showdialog();
				// 标记为未开始
				SPUtils.put(getActivity(), "dt_status", WEIKAISHI);
				// 停止时间
				String end_time = TimeUtil.getDatetime();
				mGpSinfoDao.updateEndtime(end_time, (String) SPUtils.get(
						getActivity(), "gps_start_time", ""));
				dialog.dismiss();
				// 恢复到最初状态
				iv_zantinghuaguiji.setVisibility(View.GONE);
				iv_kaishihuaguiji.setVisibility(View.VISIBLE);
				// 关闭服务
				Intent intent2 = new Intent(
						GpsInfoCollectionService.actionToStop);
				getActivity().sendBroadcast(intent2);
				for (int i = 0; i < resultPoints.size(); i++) {
					if (i == resultPoints.size() - 1) {
						overlayOptions = new MarkerOptions()
								.position(resultPoints.get(i)).icon(bdB)
								.draggable(false).perspective(true);

					}
					mBaiduMap.addOverlay(overlayOptions);
				}
				if (isshowdialog()) {
					closedialog();
				}
				//
				showAlertDialogInputGpsName();
			}
		});
		// 取消
		tv_dialog_btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/** Dialog 输入轨迹名称对话框 名称(输入)+时间(默认) */
	public void showAlertDialogInputGpsName() {
		final CustomDialog dialog = new CustomDialog(getActivity(),
				R.style.Dialog);
		dialog.setCanceledOnTouchOutside(false);
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.dt_dialog_view_input, null);
		dialog.addContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		TextView tv_dialog_btn1 = (TextView) view
				.findViewById(R.id.tv_dialog_btn1);
		final CleareditTextView tv_dialog_ctv = (CleareditTextView) view
				.findViewById(R.id.tv_dialog_ctv);
		tv_dialog_btn1.setText("确认");
		TextView tv_dialog_btn3 = (TextView) view
				.findViewById(R.id.tv_dialog_btn3);
		dialog.show();
		// 保存轨迹名称
		tv_dialog_btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tempStr = tv_dialog_ctv.getText().toString();
				if (!TextUtils.isEmpty(tempStr)) {
					if (tempStr.length() < 50) {//

						mGpSinfoDao.updateGpsName(
								// 手动输入的标题加上当前时间
								tempStr
										+ "-"
										+ mGpSinfoDao.searchBylatItemId(
												(String) SPUtils.get(
														getActivity(),
														"gps_start_time", ""))
												.getEnd_time(),
								(String) SPUtils.get(getActivity(),
										"gps_start_time", ""));
						dialog.dismiss();
					} else {
						showToast("你输入的标题过长！");
					}
				} else {
					showToast("请输入轨迹标题！");
				}

			}
		});
		// 取消
		tv_dialog_btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mGpSinfoDao.updateGpsName(
						// 当前时间
						mGpSinfoDao.searchBylatItemId(
								(String) SPUtils.get(getActivity(),
										"gps_start_time", "")).getEnd_time(),
						(String) SPUtils.get(getActivity(), "gps_start_time",
								""));
				dialog.dismiss();
			}
		});
	}

	private final int WEIKAISHI = 1;// 未开始状态
	private final int JILUZHONG = 2;// 记录状态
	private final int ZANTING = 3;// 暂停状态
	private int STATUS = 1;
	private boolean showAllPeople = false;// 是否需要显示所有队员在地图中

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_kaishihuaguiji:// 开始绘制轨迹 1为未开始状态 ，2为记录状态，3为暂停状态
			// if (mGpSinfoDao.queryAll().size() < 5) {
			SPUtils.put(getActivity(), "dt_status", JILUZHONG);
			// 开始时间,每次根据当前这个时间来操作数据库
			String start_time = TimeUtil.getDatetime();
			SPUtils.put(getActivity(), "gps_start_time", start_time);
			info.setStart_time(start_time);
			/** 每次点开始的时候都是新增一个完整的轨迹对象 */
			mGpSinfoDao.add(info);
			iv_kaishihuaguiji.setVisibility(View.GONE);
			// 记录中按钮显示出来
			iv_jilutinghuaguiji.setVisibility(View.VISIBLE);
			// iv_jilutinghuaguiji.setVisibility(View.VISIBLE);
			// 开启服务，记录轨迹到数据库
			getActivity().startService(
					new Intent(getActivity(), GpsInfoCollectionService.class));
			showToast("开始记录轨迹");
			showTrack();//
			// if (mGpSinfoDao.queryAll().size() == 4) {
			// showToast("温馨提示：你最多保存5条轨迹信息，已保存4条！");
			// }
			// } else {
			// showToast("最多录制5条轨迹！");
			// }

			break;
		case R.id.iv_zantinghuaguiji:// 可暂停绘制轨迹
			// 关闭服务
			showAlertDialogCancle();
			break;
		case R.id.iv_xiazai:// 下载离线地图
			openActivity(DownOfflineActivity.class);
			break;
		case R.id.iv_jilutinghuaguiji:// 状态为记录中，点击展示继续、暂停、停止按钮
			// iv_zantinghuaguiji.setVisibility(View.VISIBLE);
			// iv_jilutinghuaguiji.setVisibility(View.GONE);
			// iv_kaishihuaguiji.setVisibility(View.VISIBLE);
			// iv_tingzhi.setVisibility(View.VISIBLE);
			showAlertDialog();
			break;
		case R.id.iv_tingzhi:// 停止绘制轨迹
			// iv_zantinghuaguiji.setVisibility(View.GONE);
			// iv_jilutinghuaguiji.setVisibility(View.GONE);
			// iv_kaishihuaguiji.setVisibility(View.VISIBLE);
			// // 关闭服务
			// Intent intent2 = new
			// Intent(GpsInfoCollectionService.actionToStop);
			// getActivity().sendBroadcast(intent2);
			// showToast("停止记录轨迹");
			// for (int i = 0; i < resultPoints.size(); i++) {
			// if (i == resultPoints.size() - 1) {
			// overlayOptions = new MarkerOptions()
			// .position(resultPoints.get(i)).icon(bdB)
			// .draggable(false).perspective(true);
			//
			// }
			// mBaiduMap.addOverlay(overlayOptions);
			// }
			break;
		case R.id.iv_dituleixing:// 地图类型
			if (ll_dtlx.getVisibility() == View.GONE) {
				ll_dtlx.setVisibility(View.VISIBLE);
			} else {
				ll_dtlx.setVisibility(View.GONE);
			}
			break;
		case R.id.ll_dtlx:// 点击pop框消失
			// ll_dtlx.setVisibility(View.GONE);
			break;
		case R.id.iv_xianshisuoyouren:
			// 获取失联信息表暂时放这里 TODO
			getAlarmLostContactList();
			tv_tishi.setText("");
			// if (showDuiyuan) {// 先判断地图是否绘制完成
			// 算出与离其他队员最近的距离
			double lichengOther = 0;
			double lichengOtherTemp = 0;
			// 失联队员列表
			listShilianbean = new ArrayList<Shilianbean>();
			Shilianbean shilianbean = null;
			if (tuZhongUserListGet != null) {

				for (int i = 0; i < tuZhongUserListGet.size(); i++) {
					if (!TextUtils.isEmpty(tuZhongUserListGet.get(i)
							.getUser_latitude())) {
						lichengOther = DistanceUtil.getDistance(
								p2,
								new LatLng(Double.valueOf(tuZhongUserListGet
										.get(i).getUser_latitude()), Double
										.valueOf(tuZhongUserListGet.get(i)
												.getUser_longitude())));
						if (lichengOtherTemp == 0) {
							// 最近的距离
							lichengOtherTemp = lichengOther;
							lichengOtherTempLong = lichengOther;
						}
						// 假设大于1000就报失联
						if (lichengOther > 1000) {
							shilianbean = new Shilianbean();
							lichengOtherTemp = lichengOther / 1000;
							nameTempLong = tuZhongUserListGet.get(i)
									.getNickname();
							shilianbean
									.setLichengOtherTempLong(lichengOtherTemp);
							shilianbean.setNameTempLong(nameTempLong);
							listShilianbean.add(shilianbean);
						}

					}
				}
				// 展示所有失联人员
				if (listShilianbean.size() > 0) {
					for (int i = 0; i < listShilianbean.size(); i++) {
						tv_tishi.append("\n【失联】\t"
								+ listShilianbean.get(i).getNameTempLong()
								+ " \t距离:"
								+ ParseOject.StringToDouble(listShilianbean
										.get(i).getLichengOtherTempLong())
								+ "km\n");
					}
				} else {
					tv_tishi.setText("状态良好");
				}

				if (tv_tishi.getVisibility() == View.GONE) {
					showAllPeople = true;
					SPUtils.put(getActivity(), "showAllPeople", true);

					showdialog();
					tv_tishi.setVisibility(View.VISIBLE);
					new Thread(new Runnable() {
						@Override
						public void run() {
							for (int i = 0; i < tuZhongUserListGet.size(); i++) {
								try {
									bdD = BitmapDescriptorFactory.fromBitmap(ImageControl
											.GetRoundedCornerBitmap(ImageControl.compressImage(ImageControl
													.getBitmaptz(tuZhongUserListGet
															.get(i).getHead()))));
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (!tuZhongUserListGet.get(i)
										.getUser_latitude().equals("")
										&& !tuZhongUserListGet.get(i)
												.getUser_longitude().equals("")
										&& bdD != null) {// 不显示自己的
									// && !TextUtils.equals(tuZhongUserListGet
									// .get(i).getUserid(), App
									// .getInstance().getUserid())
									overlayOptions = new MarkerOptions()
											.position(
													new LatLng(
															Double.valueOf(tuZhongUserListGet
																	.get(i)
																	.getUser_latitude()),
															Double.valueOf(tuZhongUserListGet
																	.get(i)
																	.getUser_longitude())))
											.icon(bdD).draggable(false)
											.perspective(true);
									if (overlayOptions != null
											&& mBaiduMap != null) {
										mBaiduMap.addOverlay(overlayOptions);
									}

								}
							}
							// 标记出队员
							if (isshowdialog()) {
								closedialog();
							}
						}
					}).start();

				} else {
					showAllPeople = false;
					SPUtils.put(getActivity(), "showAllPeople", false);
					// 清除覆盖物之后再重新绘制轨迹
					// 地图记录状态
					switch ((Integer) SPUtils
							.get(getActivity(), "dt_status", 1)) {
					case WEIKAISHI:// 未开始状态
						if (mBaiduMap != null) {
							mBaiduMap.clear();
							// 关联地图中的默认值，应用退出时未停止画轨迹的情况
							// SPUtils.put(getActivity(), "dt_status", 0);
						}
						break;
					case JILUZHONG:// 记录状态
						// 开启服务，记录轨迹到数据库
						showTrack();
						break;
					case ZANTING:// 暂停状态
						showTrack();
						break;
					default:
						if (mBaiduMap != null) {
							mBaiduMap.clear();
							// 关联地图中的默认值，应用退出时未停止画轨迹的情况
							// SPUtils.put(getActivity(), "dt_status", 0);
						}
						break;
					}
					tv_tishi.setVisibility(View.GONE);
				}
				// } else {
				// showToast("正在加载数据，请稍等");
				// }
			}
			break;
		case R.id.tv_ptdt:// 普通地图
			// ll_dtlx.setVisibility(View.GONE);
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;
		case R.id.tv_ckgj:// 参考轨迹
			// ll_dtlx.setVisibility(View.GONE);
			showTrackTemp();
			break;
		case R.id.tv_refresh:// 刷新
			// ll_dtlx.setVisibility(View.GONE);
			// （同行宝）+（领队宝）获取队员信息表
			if (mConnected) {
				accessTeamInformationTable();
			}
			// 获取所有队员信息
			if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
				sendAsyn();
			} else {
				handlerlistNet.sendEmptyMessage(CommonUtility.SERVEROK3);
			}

			showToast("刷新成功");
			break;
		case R.id.tv_wxdt:// 卫星地图
			// ll_dtlx.setVisibility(View.GONE);
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.tv_close:// 交通地图
			ll_dtlx.setVisibility(View.GONE);
			// mBaiduMap.g
			// mBaiduMap.setTrafficEnabled(true);
			break;
		case R.id.top_tv_right_wode:
			// 展示人员列表，这个列表对于队员来讲是来自蓝牙设备的，通过领队宝同步到同行宝
			if (rsv.getVisibility() == View.GONE) {

				//
				rsv.setVisibility(View.VISIBLE);
				rl.setVisibility(View.GONE);
				top_tv_right_wode.setText("地图");
				// 发送数据
				// accessTeamInformationTable();
				handlerlist.sendEmptyMessage(3);
				if (mConnected) {
					if (huoDongXiangQingLeader != null) {
						// 同步告警失联表 领队
						readysynchronousAlarmLostChart();
					} else {
						// 获取告警失联表 队员
						getAlarmLostContactList();
					}
				}
				// handlerlistNet.sendEmptyMessage(CommonUtility.SERVEROK3);
				handlerlistNet.sendEmptyMessage(CommonUtility.SERVEROK3);

			} else {
				top_tv_right_wode.setText("队员");
				rsv.setVisibility(View.GONE);
				rl.setVisibility(View.VISIBLE);
			}

			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		//
		latLngs = new ArrayList<LatLng>();
		latLngsStr = new ArrayList<String>();

		// 右上角按钮，每次进默认此背景
		top_tv_right_wode.setVisibility(View.VISIBLE);
		top_tv_right_wode.setText("队员");
		//
		lv = (MyListView) getActivity().findViewById(R.id.lv);
		lv_shilian = (MyListView) getActivity().findViewById(R.id.lv_shilian);
		rsv = (ReboundScrollView) getActivity().findViewById(R.id.rsv);
		//
		rl = (RelativeLayout) getActivity().findViewById(R.id.rl);
		ll_dtlx = (LinearLayout) getActivity().findViewById(R.id.ll_dtlx);
		ll_ckgj = (LinearLayout) getActivity().findViewById(R.id.ll_ckgj);
		tv_ptdt = (TextView) getActivity().findViewById(R.id.tv_ptdt);
		tv_ckgj = (TextView) getActivity().findViewById(R.id.tv_ckgj);
		tv_refresh = (ImageView) getActivity().findViewById(R.id.tv_refresh);
		tv_tishi = (TextView) getActivity().findViewById(R.id.tv_tishi);
		tv_wxdt = (TextView) getActivity().findViewById(R.id.tv_wxdt);
		tv_close = (TextView) getActivity().findViewById(R.id.tv_close);
		tb_jhgj = (ToggleButton) getActivity().findViewById(R.id.tb_jhgj);
		tb_wdgj = (ToggleButton) getActivity().findViewById(R.id.tb_wdgj);
		tb_jtdt = (ToggleButton) getActivity().findViewById(R.id.tb_jtdt);

		requestLocButton = (ImageView) getActivity()
				.findViewById(R.id.iv_local);
		iv_kaishihuaguiji = (ImageView) getActivity().findViewById(
				R.id.iv_kaishihuaguiji);
		iv_jilutinghuaguiji = (ImageView) getActivity().findViewById(
				R.id.iv_jilutinghuaguiji);
		iv_tingzhi = (ImageView) getActivity().findViewById(R.id.iv_tingzhi);
		iv_xiazai = (ImageView) getActivity().findViewById(R.id.iv_xiazai);
		iv_zantinghuaguiji = (ImageView) getActivity().findViewById(
				R.id.iv_zantinghuaguiji);
		iv_xianshisuoyouren = (ImageView) getActivity().findViewById(
				R.id.iv_xianshisuoyouren);
		iv_dituleixing = (ImageView) getActivity().findViewById(
				R.id.iv_dituleixing);
		// 2地图初始化
		mMapView = (MapView) getActivity().findViewById(R.id.bmapView);
		// 隐藏自带的地图缩放控件
		mMapView.showZoomControls(false);
		// 删除百度地图logo
		// mMapView.removeViewAt(1);
		zoomInBtn = (Button) getActivity().findViewById(R.id.zoomin);
		zoomOutBtn = (Button) getActivity().findViewById(R.id.zoomout);

		// usersList = new ArrayList<TuZhongUser>();
		// usersListtemp = new ArrayList<TuZhongUser>();
		// zhongUser = new TuZhongUser();
		// zhongUser.setName("测试姓名1");
		// zhongUser.setJuli("测试距离1");
		// usersListtemp.add(zhongUser);
		// zhongUser = new TuZhongUser();
		// zhongUser.setName("测试姓名2");
		// zhongUser.setJuli("测试距离2");
		// usersListtemp.add(zhongUser);
		// zhongUser = new TuZhongUser();
		// zhongUser.setName("测试姓名3");
		// zhongUser.setJuli("测试距离3");
		// usersListtemp.add(zhongUser);//
		tuZhongUserListGet = new ArrayList<TuZhongUser>();
		tuZhongUserListTemp = new ArrayList<TuZhongUser>();
		listadapter = new MyListAdpter();
		// listadapter = new CommonAdapter<TuZhongUser>(
		// TuZhongFragment.this.getActivity(), tuZhongUserListGet,
		// R.layout.item_tuzhong) {
		//
		// public void setOncliklisenter2(View v, final TuZhongUser item) {
		// showdialog();
		// // 显隐对应控件
		// rsv.setVisibility(View.GONE);
		// rl.setVisibility(View.VISIBLE);
		//
		// showToast("加载队员信息中，请稍等");
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// // //
		// try {
		// bdD = BitmapDescriptorFactory.fromBitmap(ImageControl
		// .GetRoundedCornerBitmap(ImageControl
		// .compressImage(ImageControl
		// .getBitmap("file://"
		// + item.getHead()))));
		// } catch (Exception e) {
		// //
		// e.printStackTrace();
		// }
		// if (!item.getUser_latitude().equals("")
		// && !item.getUser_longitude().equals("")) {
		// overlayOptions = new MarkerOptions()
		// .position(
		// new LatLng(
		// Double.valueOf(item
		// .getUser_latitude()),
		// Double.valueOf(item
		// .getUser_longitude())))
		// .icon(bdD).draggable(false)
		// .perspective(true);
		// mBaiduMap.addOverlay(overlayOptions);
		// }
		// if (isshowdialog()) {
		// closedialog();
		// }
		// }
		//
		// }).start();
		//
		// }
		//
		// @Override
		// public void convert(ViewHolderUntil helper, TuZhongUser item,
		// int position) {
		// helper.setText(R.id.tv_name, item.getNickname());
		// //
		// if (item.getUser_latitude().equals("")
		// || item.getUser_longitude().equals("")) {
		// helper.setText(R.id.tv_juli, "该队员尚未使用地图功能");
		// } else {
		// // 显示距离
		// double licheng = DistanceUtil.getDistance(p2,
		// new LatLng(Double.valueOf(item.getUser_latitude()),
		// Double.valueOf(item.getUser_longitude())));
		// if (licheng > 1000) {//
		// helper.setText(R.id.tv_juli,
		// ParseOject.StringToDouble(licheng / 1000)
		// + "km");
		// ((TextView) helper.getView(R.id.tv_juli))
		// .setTextColor(getResources().getColor(
		// android.R.color.holo_red_light));
		// } else if (licheng > 1500) {
		// ((TextView) helper.getView(R.id.tv_juli))
		// .setTextColor(getResources().getColor(
		// R.color.app_green));
		// } else if (licheng > 2000) {
		// ((TextView) helper.getView(R.id.tv_juli))
		// .setTextColor(getResources().getColor(
		// android.R.color.holo_blue_light));
		// } else {
		// helper.setText(R.id.tv_juli,
		// ParseOject.StringToDouble(licheng) + "m");
		// ((TextView) helper.getView(R.id.tv_juli))
		// .setTextColor(getResources().getColor(
		// android.R.color.black));
		// }
		//
		// }
		//
		// mImageLoader.displayImage("file://" + item.getHead(),
		// (CircleImageView) helper.getView(R.id.civ_icon));
		// }
		//
		// };
		wWarningDistance1 = 1000;
		wWarningDistance2 = 2000;
		wWarningDistance3 = 5000;
		lv.setAdapter(listadapter);
		// 获取所有队员信息
		if (NetUtil.isNetworkAvailable(getActivity())) {// 有网
			sendAsyn();
			// 获取配置
			sendGetActConfigAsyn();
		} else {
			handlerlistNet.sendEmptyMessage(CommonUtility.SERVEROK3);
			// 默认配置
			handlerlistNet.sendEmptyMessage(CommonUtility.SERVEROK5);
		}

	}

	@Override
	protected void onResload() {
		mapViewControl();
		// 地图记录状态
		switch ((Integer) SPUtils.get(getActivity(), "dt_status", 1)) {
		case WEIKAISHI:// 未开始状态
			iv_kaishihuaguiji.setVisibility(View.VISIBLE);
			iv_jilutinghuaguiji.setVisibility(View.GONE);
			iv_zantinghuaguiji.setVisibility(View.GONE);
			break;
		case JILUZHONG:// 记录状态
			// 开启服务，记录轨迹到数据库
			getActivity().startService(
					new Intent(getActivity(), GpsInfoCollectionService.class));
			showTrack();
			iv_kaishihuaguiji.setVisibility(View.GONE);
			iv_jilutinghuaguiji.setVisibility(View.VISIBLE);
			iv_zantinghuaguiji.setVisibility(View.GONE);
			break;
		case ZANTING:// 暂停状态
			showTrack();
			iv_kaishihuaguiji.setVisibility(View.GONE);
			iv_jilutinghuaguiji.setVisibility(View.GONE);
			iv_zantinghuaguiji.setVisibility(View.VISIBLE);
			break;
		default:
			iv_kaishihuaguiji.setVisibility(View.VISIBLE);
			iv_jilutinghuaguiji.setVisibility(View.GONE);
			iv_zantinghuaguiji.setVisibility(View.GONE);
			break;
		}
		// 开启广播来绘制地图
		IntentFilter filter = new IntentFilter(GpsInfoCollectionService.action);
		getActivity().registerReceiver(broadcastReceiver, filter);

	}

	/** 判断服务是否启动 */
	public boolean isWorked() {
		ActivityManager myManager = (ActivityManager) getActivity()
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service
					.getClassName()
					.toString()
					.equals("com.hwacreate.outdoor.service.GpsInfoCollectionService")) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void setMyViewClick() {
		top_tv_right_wode.setOnClickListener(this);
		iv_kaishihuaguiji.setOnClickListener(this);
		iv_zantinghuaguiji.setOnClickListener(this);
		iv_jilutinghuaguiji.setOnClickListener(this);
		iv_tingzhi.setOnClickListener(this);
		iv_xiazai.setOnClickListener(this);
		iv_dituleixing.setOnClickListener(this);
		iv_xianshisuoyouren.setOnClickListener(this);
		tv_ptdt.setOnClickListener(this);
		tv_ckgj.setOnClickListener(this);
		tv_refresh.setOnClickListener(this);
		tv_wxdt.setOnClickListener(this);
		tv_close.setOnClickListener(this);
		ll_dtlx.setOnClickListener(this);

		tb_jhgj.setOnCheckedChangeListener(this);
		tb_wdgj.setOnCheckedChangeListener(this);
		tb_jtdt.setOnCheckedChangeListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	protected void headerOrFooterViewControl() {
		initMainFooter("任务", "报到", "地图");
		radiobutton_select_one
				.setBackgroundResource(R.drawable.select_item_downnocheck);
		radiobutton_select_two
				.setBackgroundResource(R.drawable.select_item_downnocheck);
		radiobutton_select_three
				.setBackgroundResource(R.drawable.select_item_down);
		radiobutton_select_one.setTextColor(getResources().getColor(
				R.color.title));
		radiobutton_select_two.setTextColor(getResources().getColor(
				R.color.title));
		radiobutton_select_three.setTextColor(getResources().getColor(
				R.color.title));
		radiobutton_select_three.setChecked(true);
	}

	@Override
	public void onStart() {
		super.onStart();
		// 队员
		mDeviceName = App.getInstance().getBleDuiYuanName();
		mDeviceAddress = App.getInstance().getBleDuiYuanAddress();
		// 当前界面的广播接收器
		if (!TextUtils.isEmpty(mDeviceName)) {
			initBluetooth();
			// tv_contact.setText("已关联同行宝:" + mDeviceName + "\t\t点击断开");
		} else {
			// tv_contact.setText("关联同行宝");
			showToast("未关联同行宝");
		}
	}

	// ----------------------------------------------------百度地图
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			logzjy.i("服务中的请求同样返回到这里来了！");
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			p2 = new LatLng(location.getLatitude(), location.getLongitude());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p2);
			mBaiduMap.animateMapStatus(u);

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	/** 服务中发送广播过来进行绘图 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			showTrack();//
			/**
			 * 在广播返回的时候从设备获取gps（领队宝） 获取GPS数据表
			 * 每个从硬件获取数据的操作，1硬件都会先返回数据的大小，2再返回数据TODO
			 */
			// getGPSDataTable();

		}
	};
	private List<LatLng> resultPoints;
	private Marker marker = null;
	private OverlayOptions overlayOptions = null;
	private JSONArray array = null;

	/**
	 * 显示轨迹
	 */
	private void showTrack() {

		if (mBaiduMap != null) {
			mBaiduMap.clear();
			// 关联地图中的默认值，应用退出时未停止画轨迹的情况
			// SPUtils.put(getActivity(), "dt_status", 0);

		}
		if ((Boolean) SPUtils.get(getActivity(), "showAllPeople", false)) {// 是否需要将所有队员显示
			allPeople();
		}
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				resultPoints = new ArrayList<LatLng>();
				// 获取数据库的数据
				BaseDao<GpsInfo> dao = new BaseDao(new GpsInfo(), getActivity());
				infos = new ArrayList<GpsInfo>();
				infos.add(dao.searchBylatItemId((String) SPUtils.get(
						getActivity(), "gps_start_time", "")));
				if (infos.size() != 0 && infos.get(0) != null) {
					if (!TextUtils.isEmpty(infos.get(0).getLocationInfo())) {
						try {
							array = new JSONArray(infos.get(0)
									.getLocationInfo());
							for (int i = 0; i < array.length(); i++) {
								double myLongitude = array.getJSONObject(i)
										.getDouble("longitude");
								double myLatitude = array.getJSONObject(i)
										.getDouble("latitude");
								LatLng point = new LatLng(myLatitude,
										myLongitude);
								resultPoints.add(point);
								point = null;
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					// for (GpsInfo info : infos) {
					// double myLatitude =
					// Double.parseDouble(info.getLatitude());
					// double myLongitude = Double
					// .parseDouble(info.getLongitude());
					// LatLng point = new LatLng(myLatitude, myLongitude);
					// resultPoints.add(point);
					// // latLngs.add(point);
					// point = null;
					//
					// }
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// 数据库最后一个坐标的位置
				if (array != null) {

					if (array.length() >= 2) {
						MapStatusUpdate u = null;
						try {
							u = MapStatusUpdateFactory.newLatLng(new LatLng(
									array.getJSONObject(array.length() - 1)
											.getDouble("latitude"), array
											.getJSONObject(array.length() - 1)
											.getDouble("longitude")));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
						mBaiduMap.setMapStatus(u);
						// 折线显示
						if (resultPoints.size() >= 2) {
							OverlayOptions ooPolyline = new PolylineOptions()
									.width(10).color(0xAAFF0000)
									.points(resultPoints); //
							mBaiduMap.addOverlay(ooPolyline);
						}
						Log.i("onPostExecute", "onPostExecute()");
						// 得到了所有信息之后才可点击显示队员按钮

					}
					// 显示出起点
					for (int i = 0; i < resultPoints.size(); i++) {
						if (i == 0) {
							overlayOptions = new MarkerOptions()
									.position(resultPoints.get(i)).icon(bdA)
									.draggable(false).perspective(true);
							//
							marker = (Marker) (mBaiduMap
									.addOverlay(overlayOptions));
							Bundle bundle = new Bundle();
							bundle.putSerializable("info", "测试");
							marker.setExtraInfo(bundle);

							mBaiduMap.addOverlay(overlayOptions);
						}
					}
				}
				super.onPostExecute(result);
			}

		}.execute();

	}

	private void allPeople() {
		//
		tv_tishi.setText("");
		// if (showDuiyuan) {// 先判断地图是否绘制完成
		// 算出与离其他队员最近的距离
		double lichengOther = 0;
		double lichengOtherTemp = 0;
		// 失联队员列表
		listShilianbean = new ArrayList<Shilianbean>();
		Shilianbean shilianbean = null;
		if (tuZhongUserListGet != null) {

			for (int i = 0; i < tuZhongUserListGet.size(); i++) {
				if (!TextUtils.isEmpty(tuZhongUserListGet.get(i)
						.getUser_latitude())) {
					lichengOther = DistanceUtil.getDistance(
							p2,
							new LatLng(Double.valueOf(tuZhongUserListGet.get(i)
									.getUser_latitude()), Double
									.valueOf(tuZhongUserListGet.get(i)
											.getUser_longitude())));
					if (lichengOtherTemp == 0) {
						// 最近的距离
						lichengOtherTemp = lichengOther;
						lichengOtherTempLong = lichengOther;
					}
					// 假设大于1000就报失联
					if (lichengOther > 1000) {
						shilianbean = new Shilianbean();
						lichengOtherTemp = lichengOther / 1000;
						nameTempLong = tuZhongUserListGet.get(i).getNickname();
						shilianbean.setLichengOtherTempLong(lichengOtherTemp);
						shilianbean.setNameTempLong(nameTempLong);
						listShilianbean.add(shilianbean);
					}

				}
			}
			// 展示所有失联人员
			if (listShilianbean.size() > 0) {
				for (int i = 0; i < listShilianbean.size(); i++) {
					tv_tishi.append("\n【失联】\t"
							+ listShilianbean.get(i).getNameTempLong()
							+ " \t距离:"
							+ ParseOject.StringToDouble(listShilianbean.get(i)
									.getLichengOtherTempLong()) + "km\n");
				}
			} else {
				tv_tishi.setText("状态良好");
			}

			if (tv_tishi.getVisibility() == View.GONE) {
				showdialog();
				tv_tishi.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < tuZhongUserListGet.size(); i++) {
							try {
								bdD = BitmapDescriptorFactory.fromBitmap(ImageControl.GetRoundedCornerBitmap(ImageControl
										.compressImage(ImageControl
												.getBitmaptz(tuZhongUserListGet
														.get(i).getHead()))));
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!tuZhongUserListGet.get(i).getUser_latitude()
									.equals("")
									&& !tuZhongUserListGet.get(i)
											.getUser_longitude().equals("")
									&& !TextUtils.equals(tuZhongUserListGet
											.get(i).getUserid(), App
											.getInstance().getUserid())) {// 不显示自己的
								overlayOptions = new MarkerOptions()
										.position(
												new LatLng(
														Double.valueOf(tuZhongUserListGet
																.get(i)
																.getUser_latitude()),
														Double.valueOf(tuZhongUserListGet
																.get(i)
																.getUser_longitude())))
										.icon(bdD).draggable(false)
										.perspective(true);
								if (overlayOptions != null && mBaiduMap != null) {
									mBaiduMap.addOverlay(overlayOptions);
								}

							}
						}
						// 标记出队员
						if (isshowdialog()) {
							closedialog();
						}
					}
				}).start();

			}
		}
	}

	/**
	 * 对地图的控制
	 */
	private void mapViewControl() {
		mCurrentMode = LocationMode.NORMAL;
		OnClickListener btnClickListener = new OnClickListener() {
			public void onClick(View v) {
				// TODO 获取gps暂时放这里
				// if (mConnected) {
				getGPSDataTable();
				// }
				switch (mCurrentMode) {
				case NORMAL:
					mCurrentMode = LocationMode.FOLLOWING;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				// case COMPASS:
				// mCurrentMode = LocationMode.NORMAL;
				// mBaiduMap
				// .setMyLocationConfigeration(new MyLocationConfiguration(
				// mCurrentMode, true, mCurrentMarker));
				// break;
				case FOLLOWING:
					mCurrentMode = LocationMode.NORMAL;

					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;
				default:
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, mCurrentMarker));
					break;

				}
			}
		};
		requestLocButton.setOnClickListener(btnClickListener);
		// 2
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 开启罗盘，不显示
		mBaiduMap.getUiSettings().setCompassEnabled(true);
		// 开启交通图
		mBaiduMap.setTrafficEnabled(false);
		mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);// 不允许旋转
		mBaiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(17).build()));// 设置缩放级别
		// 定位初始化
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5 * 1000); // 不设置或者小于1000，手动调用
										// locationClient.requestLocation();就会进行一次定位。
										// 设置定时定位的时间间隔。单位毫秒

		mLocClient.setLocOption(option);
		mLocClient.start();
		// 按钮缩放
		zoomInBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				float zoomLevel = mBaiduMap.getMapStatus().zoom;
				if (zoomLevel < mBaiduMap.getMaxZoomLevel()) {
					// MapStatusUpdateFactory.zoomIn();
					mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
					zoomOutBtn.setEnabled(true);
				} else {
					// showToast("已经放至最大！");
					zoomInBtn.setEnabled(false);
				}
			}
		});
		zoomOutBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				float zoomLevel = mBaiduMap.getMapStatus().zoom;
				if (zoomLevel > mBaiduMap.getMinZoomLevel()) {
					mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
					zoomInBtn.setEnabled(true);
				} else {
					zoomOutBtn.setEnabled(false);
					showToast("已经缩至最小！");
				}
			}
		});
		// 覆盖物点击
		mBaiduMap.setOnMarkerClickListener(clickListener);
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChange(MapStatus arg0) {
				logzjy.e("*******onMapStatusChange**********");
				currentZoomLevel = arg0.zoom;
				if (currentZoomLevel == mBaiduMap.getMaxZoomLevel()) {
					zoomInBtn.setEnabled(false);
					// showToast("已经放至最大！");
				} else if (currentZoomLevel == mBaiduMap.getMinZoomLevel()) {
					zoomOutBtn.setEnabled(false);
					showToast("已经缩至最小！");
				} else {
					zoomInBtn.setEnabled(true);
					zoomOutBtn.setEnabled(true);
				}
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				logzjy.e("*********onMapStatusChangeFinish********");
			}

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {

				logzjy.e("*****************" + Float.toString(currentZoomLevel));
			}
		});

	}

	// 失联人员
	double lichengOtherTempLong = 0;
	String nameTempLong = null;
	List<Shilianbean> listShilianbean = null;
	// SPUtils.put(getActivity(), "Leader_latitude",
	// LeaderLocationItem.getLeader_latitude());
	// SPUtils.put(getActivity(), "Leader_latitude",
	// LeaderLocationItem.getLeader_longitude());
	// SPUtils.put(getActivity(), "Leadrid",
	// LeaderLocationItem.getLeadrid());
	/**
	 * 地图中点击某个人获取信息
	 */
	private OnMarkerClickListener clickListener = new OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			for (int j = 0; j < tuZhongUserListGet.size(); j++) {

				if (!TextUtils.isEmpty(tuZhongUserListGet.get(j)
						.getUser_latitude())) {
					if (marker
							.getPosition()
							.toString()
							.equals(new LatLng(Double
									.valueOf(tuZhongUserListGet.get(j)
											.getUser_latitude()), Double
									.valueOf(tuZhongUserListGet.get(j)
											.getUser_longitude())).toString())) {// 两相等时，获取当前点击的item

						if (huoDongXiangQingLeader != null) {// 领队

							// 1 生成一个TextView用户在地图中显示InfoWindow
							TextView location = new TextView(getActivity()
									.getApplicationContext());
							location.setGravity(Gravity.CENTER_VERTICAL);
							location.setTextColor(getResources().getColor(
									R.color.content));
							location.setBackgroundResource(R.drawable.btn_back_nor);
							location.setPadding(30, 20, 30, 50);
							// 自己的点
							LatLng myLatLng = new LatLng(
									Double.valueOf(marker.getPosition().latitude),
									Double.valueOf(marker.getPosition().longitude));
							// 与领队之间的距离
							double licheng = DistanceUtil.getDistance(p2,
									myLatLng);

							if (licheng > 1000) {
								location.setText(tuZhongUserListGet.get(j)
										.getNickname()
										+ "\n离我距离:"
										+ ParseOject
												.StringToDouble(licheng / 1000)
										+ "km"
										+ "\n最近上传时间:"
										+ tuZhongUserListGet.get(j)
												.getLocation_time());
							} else {
								location.setText(tuZhongUserListGet.get(j)
										.getNickname()
										+ "\n离我距离:"
										+ ParseOject.StringToDouble(licheng)
										+ "m"
										+ "\n最近上传时间:"
										+ tuZhongUserListGet.get(j)
												.getLocation_time());
							}

							// btn 变成 View 图片
							BitmapDescriptor descriptor = BitmapDescriptorFactory
									.fromView(location);
							// 2 将marker所在的经纬度的信息转化成屏幕上的坐标
							final LatLng ll = marker.getPosition();
							Point p = mBaiduMap.getProjection()
									.toScreenLocation(ll);
							p.y -= 47;
							LatLng llInfo = mBaiduMap.getProjection()
									.fromScreenLocation(p);
							// 3 为弹出的InfoWindow添加点击事件

							/**
							 * 弹窗的点击事件： - InfoWindow 展示的bitmap position -
							 * InfoWindow 显示的地理位置 - InfoWindow Y 轴偏移量 listener -
							 * InfoWindow 点击监听者 InfoWindow 点击的时候 消失。
							 */
							InfoWindow mInfoWindow = new InfoWindow(descriptor,
									llInfo, -60,
									new OnInfoWindowClickListener() {

										public void onInfoWindowClick() {
											// 当用户点击 弹窗 触发：
											// 开启 POI 检索、 开启 路径规矩, 跳转界面！
											// 1 隐藏 弹窗！
											mBaiduMap.hideInfoWindow();
										}
									});
							// 显示InfoWindow
							mBaiduMap.showInfoWindow(mInfoWindow);
							// 设置详细信息布局为可见
							// mMarkerInfoLy.setVisibility(View.VISIBLE);
							// 根据商家信息为详细信息布局设置信息
							// popupInfo(mMarkerInfoLy, info);
							return true;
						} else {

							if (!TextUtils.isEmpty(tuZhongUserListGet.get(j)
									.getUser_latitude())) {
								if (marker
										.getPosition()
										.toString()
										.equals(new LatLng(
												Double.valueOf(tuZhongUserListGet
														.get(j)
														.getUser_latitude()),
												Double.valueOf(tuZhongUserListGet
														.get(j)
														.getUser_longitude()))
												.toString())) {// 两相等时，获取当前点击的item

									// 1 生成一个TextView用户在地图中显示InfoWindow
									TextView location = new TextView(
											getActivity()
													.getApplicationContext());
									location.setGravity(Gravity.CENTER_VERTICAL);
									location.setTextColor(getResources()
											.getColor(R.color.content));
									location.setBackgroundResource(R.drawable.btn_back_nor);
									location.setPadding(30, 20, 30, 50);
									// 自己的点
									LatLng myLatLng = new LatLng(
											Double.valueOf(marker.getPosition().latitude),
											Double.valueOf(marker.getPosition().longitude));
									// 与领队之间的距离
									double licheng = DistanceUtil.getDistance(
											p2, myLatLng);

									// 算出与领队的距离
									double lichengOther = 0;
									// double lichengOtherTemp = 0;
									// String nameTemp = null;
									for (int i = 0; i < tuZhongUserListGet
											.size(); i++) {
										if (!TextUtils
												.isEmpty(tuZhongUserListGet
														.get(i)
														.getUser_latitude())) {

											// } else {
											// 算出当前点与领队的距离
											lichengOther = DistanceUtil
													.getDistance(
															myLatLng,
															new LatLng(
																	Double.valueOf((String) SPUtils
																			.get(getActivity(),
																					"Leader_latitude",
																					"")),
																	Double.valueOf((String) SPUtils
																			.get(getActivity(),
																					"Leader_longitude",
																					""))));
											// if (lichengOtherTemp == 0) {
											// // 最近的距离
											// lichengOtherTemp =
											// lichengOther;
											// } else {
											// if (lichengOtherTemp >
											// lichengOther)
											// {
											// lichengOtherTemp =
											// lichengOther;
											//
											// }
											// }

											// nameTemp =
											// tuZhongUserListGet.get(i)
											// .getNickname();
										}
									}
									if (tuZhongUserListGet
											.get(j)
											.getUserid()
											.equals((String) SPUtils.get(
													getActivity(), "Leadrid",
													""))) {// 算出当前点与领队的距离
										if (licheng > 1000) {
											location.setText(tuZhongUserListGet
													.get(j).getNickname()
													+ "(领队)"
													+ "\n离我距离:"
													+ ParseOject
															.StringToDouble(licheng / 1000)
													+ "km"
													+ "km"
													+ ")"
													+ "\n最近上传时间:"
													+ tuZhongUserListGet.get(j)
															.getLocation_time());
										} else {
											location.setText(tuZhongUserListGet
													.get(j).getNickname()
													+ "(领队)"
													+ "\n离我距离:"
													+ ParseOject
															.StringToDouble(licheng)
													+ "m"

													+ "m"
													+ ")"
													+ "\n最近上传时间:"
													+ tuZhongUserListGet.get(j)
															.getLocation_time());
										}
									} else {
										if (licheng > 1000) {
											location.setText(tuZhongUserListGet
													.get(j).getNickname()
													+ "\n离我距离:"
													+ ParseOject
															.StringToDouble(licheng / 1000)
													+ "km"
													+ "\n距离领队:"
													+ "("
													+ ParseOject
															.StringToDouble(lichengOther / 1000)
													+ "km"
													+ ")"
													+ "\n最近上传时间:"
													+ tuZhongUserListGet.get(j)
															.getLocation_time());
										} else {
											location.setText(tuZhongUserListGet
													.get(j).getNickname()
													+ "\n离我距离:"
													+ ParseOject
															.StringToDouble(licheng)
													+ "m"
													+ "\n距离领队:"
													+ "("
													+ ParseOject
															.StringToDouble(lichengOther)
													+ "m"
													+ ")"
													+ "\n最近上传时间:"
													+ tuZhongUserListGet.get(j)
															.getLocation_time());
										}
									}
									// btn 变成 View 图片
									BitmapDescriptor descriptor = BitmapDescriptorFactory
											.fromView(location);
									// 2 将marker所在的经纬度的信息转化成屏幕上的坐标
									final LatLng ll = marker.getPosition();
									Point p = mBaiduMap.getProjection()
											.toScreenLocation(ll);
									p.y -= 47;
									LatLng llInfo = mBaiduMap.getProjection()
											.fromScreenLocation(p);
									// 3 为弹出的InfoWindow添加点击事件

									/**
									 * 弹窗的点击事件： - InfoWindow 展示的bitmap position
									 * - InfoWindow 显示的地理位置 - InfoWindow Y 轴偏移量
									 * listener - InfoWindow 点击监听者 InfoWindow
									 * 点击的时候 消失。
									 */
									InfoWindow mInfoWindow = new InfoWindow(
											descriptor, llInfo, -60,
											new OnInfoWindowClickListener() {

												public void onInfoWindowClick() {
													// 当用户点击 弹窗 触发：
													// 开启 POI 检索、 开启 路径规矩, 跳转界面！
													// 1 隐藏 弹窗！
													mBaiduMap.hideInfoWindow();
												}
											});
									// 显示InfoWindow
									mBaiduMap.showInfoWindow(mInfoWindow);
									// 设置详细信息布局为可见
									// mMarkerInfoLy.setVisibility(View.VISIBLE);
									// 根据商家信息为详细信息布局设置信息
									// popupInfo(mMarkerInfoLy, info);
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}
	};

	// ----------------------------------------------------蓝牙
	/**
	 * 初始化蓝牙相关
	 */
	private void initBluetooth() {
		isReceiver = true;
		Utile.needContinue = false;
		tempByte = new ArrayList<byte[]>();
		// 蓝牙相关
		setCharacteristic(mDeviceAddress);
		Intent gattServiceIntent = new Intent(getActivity(),
				BluetoothLeService.class);
		getActivity().bindService(gattServiceIntent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		// 当前界面的广播接收器
		getActivity().registerReceiver(mGattUpdateReceiver,
				makeGattUpdateIntentFilter());
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

	/** 准备同步队员信息表 */
	// private void readySynchronizationInformationTable() {
	// HwtxCommandSendMemberPrepare command = new
	// HwtxCommandSendMemberPrepare();
	// // hwtxCommandSendMemberPrepare.setMemberDataLength(memberDataLength);
	// sendData(command.getBluetoothPropertyUuidSend(),
	// command.getBluetoothPropertyUuidRead(), command.toBytes());
	// }

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

	/** （领队宝）准备同步告警/失联表 先把所有队员的信息转化为byte（我这边自己规定数据格式）之后，1发送数据大小2发送数据TODO TODO */
	private HwtxCommandSendWarningPrepare commandReadysynchronousAlarmLostChart = null;
	private byte[] tagReadysynchronousAlarmLostChart = null;
	private String tempGson = null;

	private void readysynchronousAlarmLostChart() {
		if (tuZhongUserListGet != null) {
			Gson gson = new Gson();
			// 将list转String
			tempGson = gson.toJson(tuZhongUserListGet);

			commandReadysynchronousAlarmLostChart = new HwtxCommandSendWarningPrepare();
			tagReadysynchronousAlarmLostChart = commandReadysynchronousAlarmLostChart
					.getCommandTagRaw();
			try {
				commandReadysynchronousAlarmLostChart
						.setWarningDataLength(tempGson.getBytes().length);
				// commandReadysynchronousAlarmLostChart.setWarningDataLength("1"
				// .getBytes().length);
			} catch (HwtxCommandException e) {
				//
				e.printStackTrace();
			}
			System.out.println("commandReadysynchronousAlarmLostChart:"
					+ commandReadysynchronousAlarmLostChart.bytesToHexText());
			// 还需要执行发送数据命令
			Utile.needContinue = true;
			sendData(
					commandReadysynchronousAlarmLostChart
							.getBluetoothPropertyUuidSend(),
					commandReadysynchronousAlarmLostChart
							.getBluetoothPropertyUuidRead(),
					commandReadysynchronousAlarmLostChart.toBytes());
			// 告诉设备需要传的长度之后，再讲设备参数表发送过去,延时几秒
			// new Handler().postDelayed(new Runnable() {
			//
			// @Override
			// public void run() {
			// sendData(commandReadysynchronousAlarmLostChart
			// .getBluetoothPropertyUuidSend(),
			// commandReadysynchronousAlarmLostChart
			// .getBluetoothPropertyUuidRead(),
			// Warningbyte);
			// }
			// }, 500);
		}
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

	private boolean isReceiver = false; // 是否注册

	/**
	 * 取消关联 TODO
	 */
	private void cancleContact() {
		App.getInstance().setBleDuiYuanAddress("");
		App.getInstance().setBleDuiYuanName("");
		if (isReceiver) {
			isReceiver = false;
			getActivity().unregisterReceiver(mGattUpdateReceiver);
			getActivity().unbindService(mServiceConnection);
			if (mBluetoothLeService != null) {
				mBluetoothLeService.disconnect();
				mBluetoothLeService.close();
				mBluetoothLeService = null;
			}
		}
	}

	// tv_contact.setText("关联同行宝");

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
				mConnected = false;
				cancleContact();
			} else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {// 返回的值
				mConnected = true;
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
				Utile.needContinue = false;
				// 从设备获取值之后转成我想要的，方式如下
				// Gson gson = new Gson();
				// List<TuZhongUser> tuZhongUserListGetGson = gson.fromJson(
				// tempGson, new TypeToken<List<TuZhongUser>>() {
				// }.getType());
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						if (commandReadysynchronousAlarmLostChart != null) {
							sendData(commandReadysynchronousAlarmLostChart
									.getBluetoothPropertyUuidSend(),
									commandReadysynchronousAlarmLostChart
											.getBluetoothPropertyUuidRead(),
									tempGson.getBytes());
						}
					}
				}, 500);
				// sendData(
				// commandReadysynchronousAlarmLostChart
				// .getBluetoothPropertyUuidSend(),
				// commandReadysynchronousAlarmLostChart
				// .getBluetoothPropertyUuidRead(), "1".getBytes());
			}
		}
	};
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
		System.out.println("RETURN:\n" + HwtxCommandUtility.bytesToHexText(bs));
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
		if (Arrays.equals(tagByte, tagAccessTeamInformationTable)) {// 获取队员信息表
			handlerlist.sendEmptyMessage(3);
		} else if (Arrays.equals(tagByte, tagReadysynchronousAlarmLostChart)) { // 准备同步告警/失联表：
			// 设备返回的byte //
			byte[] returenByte = HwtxCommandUtility.extractBytesFromBytes(
					newByte, 4, newByte.length - 4);
			byte[] tempByte = new byte[newByte.length - 4];
			for (int i = 0; i < tempByte.length; i++) {
				tempByte[i] = 0;
			}
			if (Arrays.equals(returenByte, tempByte)) {// crc值计算错误，需重新发数据
				// 同步告警失联表 领队
				// readysynchronousAlarmLostChart();
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
				handlerlist.sendEmptyMessage(7);
			}
		} else if (Arrays.equals(tagByte, tagGetAlarmLostContactList)) {// 获取告警/失联表
			handlerlist.sendEmptyMessage(4);
		} else if (Arrays.equals(tagByte, tagGetGPSDataTable)) {// 获取GPS数据表
			handlerlist.sendEmptyMessage(5);
		}
	}

	// private List<TuZhongUser> usersList = null;
	// private List<TuZhongUser> usersListtemp = null;
	private Handler handlerlist = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {

			case 3:// 获取队员信息表
					// usersList.clear();
					// usersList.addAll(usersListtemp);
					// showToast("数据已拿到！");
					// listadapter.addAll(usersList);
					// listadaptersl.addAll(usersList);
					// listadapter.notifyDataSetChanged();
					// listadaptersl.notifyDataSetChanged();
				break;
			case 4:
				showToast("获取告警/失联表 数据已拿到！");
				break;
			case 5:
				showToast("获取GPS数据表 数据已拿到！");
				break;
			case 7:
				String str = "";
				for (int i = 0; i < errorNumList.size(); i++) {
					str += errorNumList.get(i) + "号";
				}
				if (TextUtils.isEmpty(str)) {
					showToast("所有队员告警/失联表同步成功！");
				} else {
					showAlertDialog("队员：" + str + "未同步成功                   ");
				}

				break;
			default:
				break;
			}

		}
	};

	/** Dialog */
	public void showAlertDialog(String title) {
		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
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

	private int showTrackMyInt = 0;
	private boolean showTrackMyboolean = true;

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.tb_jhgj:// 计划轨迹
			showdialog();
			if (isChecked) {
				// ll_ckgj.setVisibility(View.VISIBLE);
				showTrackTemp();
				App.getInstance().setJiHuaGuiJi(true);
			} else {
				// ll_ckgj.setVisibility(View.GONE);
				getNowTrack();
				App.getInstance().setJiHuaGuiJi(false);
			}
			// 当已勾选了我的轨迹时，需显示
			if (App.getInstance().getMyGuiJi() && infosMy != null) {
				showTrackMyInt = 0;
				App.getInstance().setMyGuiJi(true);
				// 获取数据库的数据
				BaseDao<GpsInfo> dao = new BaseDao(new GpsInfo(), getActivity());
				infosMy = (dao.queryAll());
				while (showTrackMyboolean) {
					if (showDuiyuan) {
						showTrackMyInt++;
						if (showTrackMyInt == 5
								|| infosMy.size() == showTrackMyInt) {// 最多画5条轨迹
							showTrackMyboolean = false;
						}
						showTrackMy();
					}
				}
				showDuiyuan = true;
				showTrackMyboolean = true;
				showTrackMyInt = 0;
			}
			if (isshowdialog()) {
				closedialog();
			}
			break;
		case R.id.tb_wdgj:// 我的轨迹

			if (isChecked) {// showDuiyuan
				showdialog();
				App.getInstance().setMyGuiJi(true);
				// 获取数据库的数据
				BaseDao<GpsInfo> dao = new BaseDao(new GpsInfo(), getActivity());
				infosMy = (dao.queryAll());
				while (showTrackMyboolean) {
					if (showDuiyuan) {
						showTrackMyInt++;
						if (showTrackMyInt == 5
								|| infosMy.size() == showTrackMyInt) {// 最多画5条轨迹
							showTrackMyboolean = false;
						}
						showTrackMy();
					}
				}
				showDuiyuan = true;
				showTrackMyboolean = true;
				showTrackMyInt = 0;
				if (isshowdialog()) {
					closedialog();
				}
			} else {
				App.getInstance().setMyGuiJi(false);
				getNowTrack();
			}
			// 当已勾选了计划轨迹时，需显示
			if (App.getInstance().getJiHuaGuiJi()) {
				showTrackTemp();
			}

			break;
		case R.id.tb_jtdt:// 交通图
			if (isChecked) {
				mBaiduMap.setTrafficEnabled(true);
			} else {
				mBaiduMap.setTrafficEnabled(false);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 获取当前状态的轨迹情况
	 */
	public void getNowTrack() {
		// 地图记录状态
		switch ((Integer) SPUtils.get(getActivity(), "dt_status", 1)) {
		case WEIKAISHI:// 未开始状态
			if (mBaiduMap != null) {
				mBaiduMap.clear();
				// 关联地图中的默认值，应用退出时未停止画轨迹的情况
				// SPUtils.put(getActivity(), "dt_status", 0);
			}
			break;
		case JILUZHONG:// 记录状态
			// 开启服务，记录轨迹到数据库
			showTrack();
			break;
		case ZANTING:// 暂停状态
			showTrack();
			break;
		default:
			break;
		}

	}

	private Thread thread = null;

	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	// 领队经纬度
	private GetActUsersAndLeaderLocationLeaderLocationItem LeaderLocationItem = null;
	// 队员经纬度
	private ArrayList<GetActUsersAndLeaderLocationUserLocationItem> UserLocationItem = null;

	public void Action() {
		GetActUsersAndLeaderLocationRequest request = new GetActUsersAndLeaderLocationRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetActUsersAndLeaderLocationRequestParameter parameter = new GetActUsersAndLeaderLocationRequestParameter();
		// 同时只能有一个进行中
		if (huoDongXiangQingItemhuancun != null) {
			parameter.setAct_id(huoDongXiangQingItemhuancun.getAct_id());
		} else {// huoDongXiangQingLeaderList
			parameter.setAct_id(huoDongXiangQingLeader.getAct_id());
		}
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
					GetActUsersAndLeaderLocationResponse response = new GetActUsersAndLeaderLocationResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetActUsersAndLeaderLocationResponsePayload payload = (GetActUsersAndLeaderLocationResponsePayload) response
							.getPayload();
					LeaderLocationItem = payload.getLeaderLocation();
					UserLocationItem = payload.getUserLocationList();
					// State = payload.getRegistState();
					// msgString = payload.getResult();
					if (UserLocationItem.size() != 0) {

					}
					handlerlistNet.sendEmptyMessage(CommonUtility.SERVEROK1);
				} else if (ret == 500) {
					handlerlistNet.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlistNet
							.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlistNet.sendEmptyMessage(CommonUtility.SERVERERROR);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			handlerlistNet.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	/**
	 * 获得蓝牙设备配置
	 */
	public void sendGetActConfigAsyn() {
		thread = new Thread() {
			public void run() {
				getActConfigAction();
			}
		};
		thread.start();
	}

	public void getActConfigAction() { //
		GetActConfigRequest request = new GetActConfigRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetActConfigRequestParameter parameter = new GetActConfigRequestParameter();
		parameter.setAct_id(App.getInstance().getLeaderHuoDongId());
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
					GetActConfigResponse response = new GetActConfigResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetActConfigResponsePayload payload = (GetActConfigResponsePayload) response
							.getPayload();
					if (TextUtils.isEmpty(payload.getConfig())) {// 为空时使用默认值
						handlerlist.sendEmptyMessage(CommonUtility.SERVEROK5);
					} else {
						hwtxCommandjsonObject = new JSONObject(
								payload.getConfig());
						handlerlist.sendEmptyMessage(CommonUtility.SERVEROK4);
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

	private BaseDao<TuZhongUser> tuZhongUserDaoSet = null;
	// 服务器返回提示信息
	private String msgString = null;
	private int State = 0;
	@SuppressLint("HandlerLeak")
	Handler handlerlistNet = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				// 领队的经、纬度、id
				SPUtils.put(getActivity(), "Leader_latitude",
						LeaderLocationItem.getLeader_latitude());
				SPUtils.put(getActivity(), "Leader_longitude",
						LeaderLocationItem.getLeader_longitude());
				SPUtils.put(getActivity(), "Leadrid",
						LeaderLocationItem.getLeadrid());
				// 先删除再存
				tuZhongUserDaoSet.deleteAll();
				logzjy.e("进来了");
				if (!TextUtils.equals(LeaderLocationItem.getLeadrid(), App
						.getInstance().getUserid())) {// 不存自己的
					// 保存领队的数据
					zhongUser = new TuZhongUser();
					zhongUser.setNickname(LeaderLocationItem.getRealname());
					zhongUser.setUser_latitude(LeaderLocationItem
							.getLeader_latitude());
					zhongUser.setUser_longitude(LeaderLocationItem
							.getLeader_longitude());
					zhongUser.setUserid(LeaderLocationItem.getLeadrid());
					zhongUser.setLocation_time(LeaderLocationItem
							.getLocation_time());
					zhongUser.setHead(ImageControl.saveImageToGallery(
							getActivity(),
							ImageControl.getHttpBitmap(LeaderLocationItem
									.getHead()), 4));
					if (!TextUtils.equals(
							LeaderLocationItem.getLeader_latitude(), "")) {
						// 距离
						double distanceLeader = DistanceUtil.getDistance(
								p2,
								new LatLng(Double.valueOf(LeaderLocationItem
										.getLeader_latitude()), Double
										.valueOf(LeaderLocationItem
												.getLeader_longitude())));
						zhongUser.setDistance(distanceLeader);
					}

					tuZhongUserDaoSet.add(zhongUser);
				}
				new Thread(new Runnable() {

					@Override
					public void run() {
						for (int j = 0; j < UserLocationItem.size(); j++) {
							if (!TextUtils
									.equals(UserLocationItem.get(j).getUserid(),
											App.getInstance().getUserid())) {// 不存自己的

								// 所有队员的数据
								zhongUser = new TuZhongUser();
								zhongUser.setNickname(UserLocationItem.get(j)
										.getNickname());
								zhongUser.setUser_latitude(UserLocationItem
										.get(j).getUser_latitude());
								zhongUser.setUser_longitude(UserLocationItem
										.get(j).getUser_longitude());
								zhongUser.setUserid(UserLocationItem.get(j)
										.getUserid());
								zhongUser.setLocation_time(UserLocationItem
										.get(j).getLocation_time());
								zhongUser.setHead(ImageControl
										.saveImageToGallery(
												getActivity(),
												ImageControl
														.getHttpBitmap(UserLocationItem
																.get(j)
																.getHead()), 4));
								if (!TextUtils.equals(UserLocationItem.get(j)
										.getUser_latitude(), "")) {
									// 距离
									double distance = DistanceUtil.getDistance(
											p2,
											new LatLng(
													Double.valueOf(UserLocationItem
															.get(j)
															.getUser_latitude()),
													Double.valueOf(UserLocationItem
															.get(j)
															.getUser_longitude())));
									zhongUser
											.setDistance(distance > 0 ? distance
													: 0);
								}

								tuZhongUserDaoSet.add(zhongUser);
							}
							// 设置设配器
							if (j == UserLocationItem.size() - 1) {
								handlerlistNet
										.sendEmptyMessage(CommonUtility.SERVEROK3);

							}

						}
					}
				}).start();
				break;
			case CommonUtility.SERVEROK3://
				// 数据库中拿取数据
				if (tuZhongUserListGet != null) {
					tuZhongUserListGet.clear();
				}
				tuZhongUserListGet = tuZhongUserDaoSet.searchOrderByDistance();
				listadapter.notifyDataSetChanged();
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
			case CommonUtility.SERVEROK2:

				break;
			case CommonUtility.SERVEROK4:// 拿到所有的设备参数
				try {
					bGpsInterval = hwtxCommandjsonObject.getInt("bGpsInterval");
					bHktAtInterval = hwtxCommandjsonObject
							.getInt("bLostContactNum");
					bLostContactNum = hwtxCommandjsonObject
							.getInt("bLostContactNum");
					wWarningDistance1 = hwtxCommandjsonObject
							.getInt("wWarningDistance1");
					wWarningDistance2 = hwtxCommandjsonObject
							.getInt("wWarningDistance2");
					wWarningDistance3 = hwtxCommandjsonObject
							.getInt("wWarningDistance3");
					bWarningBatteryPercent = hwtxCommandjsonObject
							.getInt("bWarningBatteryPercent");
				} catch (JSONException e) {
					//
					e.printStackTrace();
				}
				break;
			case CommonUtility.SERVEROK5:
				wWarningDistance1 = 1000;
				wWarningDistance2 = 2000;
				wWarningDistance3 = 5000;
				break;
			default:
				break;
			}
		};
	};

	public class MyListAdpter extends BaseAdapter {

		@Override
		public int getCount() {
			return tuZhongUserListGet != null ? tuZhongUserListGet.size() : 0;
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
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_tuzhong, null);
				holder = new ViewHolder();
				holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
				holder.civ_icon = (CircleImageView) convertView
						.findViewById(R.id.civ_icon);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.tv_juli = (TextView) convertView
						.findViewById(R.id.tv_juli);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (tuZhongUserListGet.get(position).getUser_latitude().equals("")
					|| tuZhongUserListGet.get(position).getUser_longitude()
							.equals("")) {
				holder.tv_juli.setText("该队员尚未使用地图功能");
			} else {
				// 显示距离
				double licheng = tuZhongUserListGet.get(position).getDistance();
				if (licheng > wWarningDistance1 && licheng < wWarningDistance2) {//
					holder.tv_juli.setText(ParseOject
							.StringToDouble(licheng / 1000) + "km");
					holder.tv_juli.setTextColor(getResources().getColor(
							android.R.color.holo_red_light));
				} else if (licheng > wWarningDistance2
						&& licheng < wWarningDistance3) {
					holder.tv_juli.setText(ParseOject
							.StringToDouble(licheng / 1000) + "km");
					holder.tv_juli.setTextColor(getResources().getColor(
							R.color.app_green));
				} else if (licheng > wWarningDistance3) {
					holder.tv_juli.setText(ParseOject
							.StringToDouble(licheng / 1000) + "km");
					holder.tv_juli.setTextColor(getResources().getColor(
							android.R.color.holo_blue_light));
				} else {
					holder.tv_juli.setText(ParseOject.StringToDouble(licheng)
							+ "m");
					holder.tv_juli.setTextColor(getResources().getColor(
							android.R.color.black));
				}

			}
			// if
			// (TextUtils.equals(tuZhongUserListGet.get(position).getUserid(),
			// App.getInstance().getUserid())) {// 不显示自己的,而不是不显示领队的
			// lv.removeViewAt(position);
			// }
			mImageLoader.displayImage(
					"file://" + tuZhongUserListGet.get(position).getHead(),
					holder.civ_icon);

			holder.tv_name.setText(tuZhongUserListGet.get(position)
					.getNickname());
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//
					// 显隐对应控件
					rsv.setVisibility(View.GONE);
					rl.setVisibility(View.VISIBLE);

					new Thread(new Runnable() {
						@Override
						public void run() {
							// //
							try {
								bdD = BitmapDescriptorFactory.fromBitmap(ImageControl.GetRoundedCornerBitmap(ImageControl
										.compressImage(ImageControl
												.getBitmaptz(tuZhongUserListGet
														.get(position)
														.getHead()))));
							} catch (Exception e) {
								//
								e.printStackTrace();
							}
							if (!tuZhongUserListGet.get(position)
									.getUser_latitude().equals("")
									&& !tuZhongUserListGet.get(position)
											.getUser_longitude().equals("")) {
								overlayOptions = new MarkerOptions()
										.position(
												new LatLng(
														Double.valueOf(tuZhongUserListGet
																.get(position)
																.getUser_latitude()),
														Double.valueOf(tuZhongUserListGet
																.get(position)
																.getUser_longitude())))
										.icon(bdD).draggable(false)
										.perspective(true);
								mBaiduMap.addOverlay(overlayOptions);
							}
							if (isshowdialog()) {
								closedialog();
							}
						}

					}).start();
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private LinearLayout ll;
			private CircleImageView civ_icon;
			private TextView tv_name, tv_juli;
		}
	}

	// 我的最近五条轨迹--------------------------------------------------------------------------------------
	private List<LatLng> resultPointsMy;
	private Marker markerMy = null;
	private OverlayOptions overlayOptionsMy = null;
	private JSONArray arrayMy = null;

	/**
	 * 显示轨迹
	 */
	private void showTrackMy() {
		showDuiyuan = false;
		resultPointsMy = new ArrayList<LatLng>();

		if (infosMy.size() != 0
				&& infosMy.size() - showTrackMyInt < infosMy.size()) {
			if (!TextUtils.isEmpty(infosMy.get(infosMy.size() - showTrackMyInt)
					.getLocationInfo())) {
				try {
					arrayMy = new JSONArray(infosMy.get(
							infosMy.size() - showTrackMyInt).getLocationInfo());
					for (int i = 0; i < arrayMy.length(); i++) {
						double myLongitude = arrayMy.getJSONObject(i)
								.getDouble("longitude");
						double myLatitude = arrayMy.getJSONObject(i).getDouble(
								"latitude");
						LatLng point = new LatLng(myLatitude, myLongitude);
						resultPointsMy.add(point);
						point = null;
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		// 数据库最后一个坐标的位置
		if (arrayMy != null) {

			if (arrayMy.length() >= 2) {
				MapStatusUpdate u = null;
				try {
					u = MapStatusUpdateFactory.newLatLng(new LatLng(arrayMy
							.getJSONObject(arrayMy.length() - 1).getDouble(
									"latitude"), arrayMy.getJSONObject(
							arrayMy.length() - 1).getDouble("longitude")));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mBaiduMap.setMapStatus(u);
				// 折线显示
				if (resultPointsMy.size() >= 2) {
					OverlayOptions ooPolyline = new PolylineOptions().width(10)
							.color(0xAAFF0000).points(resultPointsMy); //
					mBaiduMap.addOverlay(ooPolyline);
				}
				Log.i("onPostExecute", "onPostExecute()");
				// 得到了所有信息之后才可点击显示队员按钮

			}
			// 显示出起点
			for (int i = 0; i < resultPointsMy.size(); i++) {
				if (i == 0) {
					overlayOptionsMy = new MarkerOptions()
							.position(resultPointsMy.get(i)).icon(bdA)
							.draggable(false).perspective(true);
					//
					markerMy = (Marker) (mBaiduMap.addOverlay(overlayOptionsMy));
					Bundle bundle = new Bundle();
					bundle.putSerializable("info", "测试");
					markerMy.setExtraInfo(bundle);

					mBaiduMap.addOverlay(overlayOptionsMy);
				}
				if (i == resultPointsMy.size() - 1) {
					overlayOptionsMy = new MarkerOptions()
							.position(resultPointsMy.get(i)).icon(bdB)
							.draggable(false).perspective(true);
					//
					markerMy = (Marker) (mBaiduMap.addOverlay(overlayOptionsMy));
					Bundle bundle = new Bundle();
					bundle.putSerializable("info", "测试");
					markerMy.setExtraInfo(bundle);

					mBaiduMap.addOverlay(overlayOptionsMy);
				}
			}
		}
		showDuiyuan = true;
	}

	// 参考轨迹--------------------------------------------------------------------------------------
	private List<LatLng> resultPointsTemp;
	private Marker markerTemp = null;
	private OverlayOptions overlayOptionsTemp = null;

	/**
	 * 显示参考轨迹
	 */
	private void showTrackTemp() {
		resultPointsTemp = new ArrayList<LatLng>();
		if (arrayTempTrace_data.length() != 0) {
			try {
				for (int i = 0; i < arrayTempTrace_data.length(); i++) {
					double TempLongitude = arrayTempTrace_data.getJSONObject(i)
							.getDouble("longitude");
					double TempLatitude = arrayTempTrace_data.getJSONObject(i)
							.getDouble("latitude");
					LatLng point = new LatLng(TempLatitude, TempLongitude);
					resultPointsTemp.add(point);
					point = null;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// 数据库最后一个坐标的位置
		if (arrayTempTrace_data != null) {
			if (arrayTempTrace_data.length() >= 2) {
				MapStatusUpdate u = null;
				try {
					u = MapStatusUpdateFactory.newLatLng(new LatLng(
							arrayTempTrace_data.getJSONObject(0).getDouble(
									"latitude"), arrayTempTrace_data
									.getJSONObject(0).getDouble("longitude")));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				mBaiduMap.setMapStatus(u);
				// 折线显示
				if (resultPointsTemp.size() >= 2) {
					OverlayOptions ooPolyline = new PolylineOptions().width(10)
							.color(0xAAFF0000).points(resultPointsTemp); //
					mBaiduMap.addOverlay(ooPolyline);
				}
				Log.i("onPostExecute", "onPostExecute()");
				// 得到了所有信息之后才可点击显示队员按钮

			}
			// 显示出起点
			for (int i = 0; i < resultPointsTemp.size(); i++) {
				if (i == 0) {
					overlayOptionsTemp = new MarkerOptions()
							.position(resultPointsTemp.get(i)).icon(bdA)
							.draggable(false).perspective(true);
					//
					markerTemp = (Marker) (mBaiduMap
							.addOverlay(overlayOptionsTemp));
					Bundle bundle = new Bundle();
					bundle.putSerializable("info", "测试");
					markerTemp.setExtraInfo(bundle);

					mBaiduMap.addOverlay(overlayOptionsTemp);
				}
				// 显示出终点
				if (i == resultPointsTemp.size() - 1) {
					overlayOptionsTemp = new MarkerOptions()
							.position(resultPointsTemp.get(i)).icon(bdB)
							.draggable(false).perspective(true);
					//
					markerTemp = (Marker) (mBaiduMap
							.addOverlay(overlayOptionsTemp));
					Bundle bundle = new Bundle();
					bundle.putSerializable("info", "测试");
					markerTemp.setExtraInfo(bundle);

					mBaiduMap.addOverlay(overlayOptionsTemp);
				}
			}
			// 显示出终点
		}
	}

	/** 判断服务是否启动 */
	public boolean isWorkedUpGpsInfoService() {
		if (getActivity() != null) {
			ActivityManager myManager = (ActivityManager) getActivity()
					.getSystemService(Context.ACTIVITY_SERVICE);
			ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
					.getRunningServices(30);
			for (int i = 0; i < runningService.size(); i++) {
				if (runningService.get(i).service
						.getClassName()
						.toString()
						.equals("com.hwacreate.outdoor.service.UpGpsInfoService")) {
					return true;
				}
			}
		}
		return false;
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
			}, 1000);// 需要延时操作

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
}
