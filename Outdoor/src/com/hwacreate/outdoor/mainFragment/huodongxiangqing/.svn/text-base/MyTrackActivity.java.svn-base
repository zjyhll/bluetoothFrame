package com.hwacreate.outdoor.mainFragment.huodongxiangqing;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.SPUtils;
import com.hwacreate.outdoor.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyTrackActivity extends BaseActivity {
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private Boolean D = true;
	private List<LatLng> resultPoints;
	private String trace_data = null;
	private JSONArray array = null;
	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LatLng p2;
	private String anchor_latitude = null;
	private String anchor_longitude = null;
	// 自定义放大缩小控件
	private Button zoomInBtn;
	private Button zoomOutBtn;
	private float currentZoomLevel = 0;
	// 起点
	private Marker mMarkerA;
	// 终点
	private Marker mMarkerB;
	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
	BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
	BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.drawable.foot_turnpoint);
	LatLng llA = null;
	LatLng llB = null;
	private List<LatLng> latLngs = null;
	private List<String> latLngsStr = null;
	private TextView Zoom_ok = null;
	private TextView Zoom_cancle = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track);
		init();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			p2 = new LatLng(Double.parseDouble(anchor_latitude), Double.parseDouble(anchor_longitude));
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(p2);
			mBaiduMap.animateMapStatus(u);

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				resultPoints = new ArrayList<LatLng>();
				// 获取数据库的数据
				try {
					array = new JSONArray(trace_data);
					for (int i = 0; i < array.length(); i++) {
						double myLatitude = Double.parseDouble(array.getJSONObject(i).getString("latitude"));
						double myLongitude = Double.parseDouble(array.getJSONObject(i).getString("longitude"));
						LatLng point = new LatLng(myLatitude, myLongitude);
						resultPoints.add(point);
						LatLng latLng = new LatLng(myLatitude, myLongitude);
						latLngs.add(latLng);
						latLngsStr.add(array.getJSONObject(i).getString("description"));
						point = null;
					}
					if (resultPoints.size() > 1) {
						MapStatusUpdate u;
						u = MapStatusUpdateFactory.newLatLng(new LatLng(
								Double.parseDouble(array.getJSONObject(array.length() - 1).getString("latitude")),
								Double.parseDouble(array.getJSONObject(array.length() - 1).getString("longitude"))));
						mBaiduMap.setMapStatus(u);
						// 折线显示
						OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAAFF0000)
								.points(resultPoints);
						mBaiduMap.addOverlay(ooPolyline);
						Log.i("onPostExecute", "onPostExecute()");
					}
				} catch (NumberFormatException e) {

					e.printStackTrace();
				} catch (JSONException e) {

					e.printStackTrace();
				}
				for (int i = 0; i < latLngs.size(); i++) {
					if (i == 0) {

						mBaiduMap.addOverlay(new MarkerOptions().position(latLngs.get(i)).icon(bdA).draggable(false)
								.perspective(true));
					} else if (i == array.length() - 1) {
						mBaiduMap.addOverlay(new MarkerOptions().position(latLngs.get(i)).icon(bdB).draggable(false)
								.perspective(true));
					} else {
						mBaiduMap.addOverlay(new MarkerOptions().position(latLngs.get(i)).icon(bdC).draggable(false)
								.perspective(true));
					}

				}
				mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker marker) {

						for (int j = 0; j < latLngs.size(); j++) {
							if (marker.getPosition().equals(latLngs.get(j))) {
								showToast(latLngsStr.get(j));
							}
						}
						return true;
					}

				});
				break;
			}
		};
	};

	/**
	 * 显示轨迹
	 */

	@Override
	protected void onPause() {
		if (D) {
			Log.i("GPS轨迹", "onPause()");
		}
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (D) {
			Log.i("GPS轨迹", "onResume()");
		}
		mMapView.onResume();
		// showTrack();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (D) {
			Log.i("GPS轨迹", "onDestroy()");
		}
		// 退出时销毁定位
		mLocClient.stop();
		mMapView.onDestroy();
		bdA.recycle();
		bdB.recycle();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回按钮返回到上一个界面
			finish();
			break;
		case R.id.Zoom_ok:
			App.getInstance().setGuiJi(true);
			finish();
			break;
		case R.id.Zoom_cancle:
			showToast("请前往网页端重新上传轨迹数据！");
			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		latLngs = new ArrayList<LatLng>();
		latLngsStr = new ArrayList<String>();
		Zoom_ok = (TextView) findViewById(R.id.Zoom_ok);
		Zoom_cancle = (TextView) findViewById(R.id.Zoom_cancle);
		// 初始化地图相关
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 开启交通图
		mBaiduMap.setTrafficEnabled(false);
		mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);// 不允许旋转
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(14).build()));// 设置缩放级别
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);
		option.setCoorType("bd09ll"); // 设置坐标类型
		// option.setScanSpan(5 * 1000); // 不设置或者小于1000，手动调用
		// locationClient.requestLocation();就会进行一次定位。
		// 设置定时定位的时间间隔。单位毫秒
		mLocClient.setLocOption(option);
		mLocClient.start();
		// 隐藏自带的地图缩放控件
		mMapView.showZoomControls(false);
		// 轨迹数据内容模板
		trace_data = getIntent().getStringExtra("trace_data");
		// 锚点纬度
		anchor_latitude = getIntent().getStringExtra("anchor_latitude");
		// 锚点经度
		anchor_longitude = getIntent().getStringExtra("anchor_longitude");
		zoomInBtn = (Button) findViewById(R.id.zoomin);
		zoomOutBtn = (Button) findViewById(R.id.zoomout);
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

	@Override
	protected void onResload() {
		top_tv_title.setText("活动轨迹验证");
		handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		Zoom_ok.setOnClickListener(this);
		Zoom_cancle.setOnClickListener(this);
	}

}
