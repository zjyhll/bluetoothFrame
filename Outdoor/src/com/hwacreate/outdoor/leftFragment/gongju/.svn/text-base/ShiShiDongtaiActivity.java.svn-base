package com.hwacreate.outdoor.leftFragment.gongju;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import me.nereo.multi_image_selector.MultiImageSelectorActivity_YouJi;
import me.nereo.multi_image_selector.bean.Image;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.uploadwithprogress.http.HttpMultipartPost;
import com.hwacreate.outdoor.utl.Bimp;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.TimeUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.view.Info;
import com.hwacreate.outdoor.view.PhotoView;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryRequest;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryRequestParameter;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryResponse;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

/**
 * @author 曾金叶 发布实时动态
 * 
 */
public class ShiShiDongtaiActivity extends BaseActivity {
	private CleareditTextView tvl_desc_edit = null;// 描述 TODO

	// 选择照片列表
	private int Count = 5;
	private GridView mGridView = null; // TODO
	private ImageGridAdapter mAdapter = null;
	public ArrayList<String> mSelectPath;
	// 点击浏览大图
	private View mParent = null; // TODO
	private View mBg = null; // TODO
	private PhotoView mPhotoView = null; // TODO
	private Info mInfo = null;
	private AlphaAnimation in = new AlphaAnimation(0, 1);
	private AlphaAnimation out = new AlphaAnimation(1, 0);
	private TextView ok = null;// TODO
	private TextView delete = null; // TODO
	private Image tempImage = null;
	private int tempIndex = -1;
	// 上传
	private HttpMultipartPost post;
	private String result = null;
	private JSONArray resultList = null;
	//
	private RelativeLayout rl = null;

	private ImageView iv_btn = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shishidongtai);
		getNowLocation();
		init();
	}

	protected void onRestart() {
		super.onRestart();

		mAdapter.update();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 界面控件的显示
		if (Bimp.tempSelectBitmap.size() == 0) {
			rl.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		} else {
			rl.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onBackPressed() {
		if (mParent.getVisibility() == View.VISIBLE) {
			mBg.startAnimation(out);
			mPhotoView.animaTo(mInfo, new Runnable() {
				@Override
				public void run() {
					mParent.setVisibility(View.GONE);
				}
			});
		} else if (post != null) {
			if (!post.isCancelled()) {
				post.cancel(true);
			}
		} else {
			FinishClear();
		}
	}

	private void FinishClear() {
		finish();
		Bimp.tempSelectBitmap.clear();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			FinishClear();
			break;
		case R.id.top_tv_right:
			descript = tvl_desc_edit.getText().toString();
			arrive_time = TimeUtil.getDatetime_System();
			// if (!TextUtils.isEmpty(rarrive_lag)
			// && !TextUtils.isEmpty(arrive_lng)
			// && !TextUtils.isEmpty(arrive_time)
			// && !TextUtils.isEmpty(descript)) {
			sendAsyn(); // TODO
			// }else{
			// showToast("描述为空");
			// }

			break;
		case R.id.bg_pic:
			if (mParent.getVisibility() == View.VISIBLE) {
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
			}
			break;
		case R.id.ok_pic:
			if (mParent.getVisibility() == View.VISIBLE) {
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {

					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
			}
			break;
		case R.id.delete_pic:
			if (mParent.getVisibility() == View.VISIBLE && tempImage != null && tempIndex != -1) {
				try {
					Bimp.tempSelectBitmap.remove(tempImage);
					mSelectPath.remove(tempImage.getPath());
					mAdapter.update();
				} catch (Exception e) {
				}
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
				tempImage = null;
				tempIndex = -1;
			}
			break;
		case R.id.iv_btn:
			Intent intent = new Intent(ShiShiDongtaiActivity.this, MultiImageSelectorActivity_YouJi.class);
			// 是否显示拍摄图片
			intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SHOW_CAMERA, true);
			// 最大可选择图片数量
			intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SELECT_COUNT, Count);
			// 选择模式
			intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SELECT_MODE,
					MultiImageSelectorActivity_YouJi.MODE_MULTI);
			// 多选数据
			if (mSelectPath != null && mSelectPath.size() > 0) {
				intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
			}
			startActivityForResult(intent, REQUEST_IMAGE);
			break;
		default:
			break;

		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		resultList = new JSONArray();
		mSelectPath = new ArrayList<String>();
		in.setDuration(300);
		out.setDuration(300);
		mParent = findViewById(R.id.parent_pic);
		iv_btn = (ImageView) findViewById(R.id.iv_btn);

		rl = (RelativeLayout) findViewById(R.id.rl);
		mBg = findViewById(R.id.bg_pic);
		ok = (TextView) findViewById(R.id.ok_pic);
		delete = (TextView) findViewById(R.id.delete_pic);
		mPhotoView = (PhotoView) findViewById(R.id.img_pic);
		mGridView = (GridView) findViewById(R.id.youji_pic);
		tvl_desc_edit = (CleareditTextView) findViewById(R.id.tvl_desc);
		tvl_desc_edit.setFocusable(true);
		tvl_desc_edit.setFocusableInTouchMode(true);
		tvl_desc_edit.requestFocus();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("发布实时动态");
		top_tv_right.setText("发布");
		mAdapter = new ImageGridAdapter();
		mGridView.setAdapter(mAdapter);
		ok.setOnClickListener(this);
		delete.setOnClickListener(this);
		mBg.setOnClickListener(this);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				if (position == Bimp.tempSelectBitmap.size()) {
					Intent intent = new Intent(ShiShiDongtaiActivity.this, MultiImageSelectorActivity_YouJi.class);
					// 是否显示拍摄图片
					intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SHOW_CAMERA, true);
					// 最大可选择图片数量
					intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SELECT_COUNT, Count);
					// 选择模式
					intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SELECT_MODE,
							MultiImageSelectorActivity_YouJi.MODE_MULTI);
					// 多选数据
					if (mSelectPath != null && mSelectPath.size() > 0) {
						intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
					}
					startActivityForResult(intent, REQUEST_IMAGE);
				} else {
					PhotoView p = (PhotoView) view;
					mInfo = p.getInfo();
					tempIndex = position;
					tempImage = Bimp.tempSelectBitmap.get(position);
					mPhotoView.setImageBitmap(tempImage.getBitmap());
					mBg.startAnimation(in);
					mParent.setVisibility(View.VISIBLE);
					mPhotoView.animaFrom(mInfo);
				}
			}
		});
		mPhotoView.enable();
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
			}
		});

		// KeyBoardUtils.openKeybord(tvl_desc_edit, this);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		iv_btn.setOnClickListener(this);
	}

	/** 得到选择的照片 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity_YouJi.EXTRA_RESULT);
				handlerlist.sendEmptyMessage(CommonUtility.UPLOADING);
			}
		}
	}

	public class ImageGridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == Count) {
				return Count;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			PhotoView p = new PhotoView(ShiShiDongtaiActivity.this);
			p.setLayoutParams(new AbsListView.LayoutParams(App.getInstance().getScreenWidth() / 5,
					App.getInstance().getScreenWidth() / 5));
			p.setScaleType(ImageView.ScaleType.FIT_XY);
			if (position == Bimp.tempSelectBitmap.size()) {
				p.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jiahao));
				if (position == Count) {
					p.setVisibility(View.GONE);
				}
			} else {
				p.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
				// 把PhotoView当普通的控件把触摸功能关掉
				p.disenable();
			}
			return p;
		}

		public void update() {
			loading();
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}

	private Thread thread = null;

	private String arrive_time = "";// 时间戳
	private String rarrive_lag = "";// 纬度
	private String descript = "";// 描述
	private String images = "";// 图片，字符串json数组
	private String act_id = "";// 活动id
	private String arrive_lng = "";// 经度

	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() { // TODO
		AddActHistoryRequest request = new AddActHistoryRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		AddActHistoryRequestParameter parameter = new AddActHistoryRequestParameter();
		parameter.setAct_id(App.getInstance().getLeaderHuoDongId());
		parameter.setArrive_lng(arrive_lng);
		parameter.setArrive_time(arrive_time);
		parameter.setDescript(descript);
		parameter.setRarrive_lag(rarrive_lag);
		parameter.setImages(resultList != null ? resultList.toString() : "");
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
					AddActHistoryResponse response = new AddActHistoryResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					AddActHistoryResponsePayload payload = (AddActHistoryResponsePayload) response.getPayload();
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

	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				FinishClear();
				showToast("发布成功");
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
			case CommonUtility.UPLOADING:
				if (mSelectPath != null && mSelectPath.size() != 0) {
					for (int i = 0; i < mSelectPath.size(); i++) {
						File file = new File(mSelectPath.get(i));
						if (file.exists()) {
							post = new HttpMultipartPost(ShiShiDongtaiActivity.this, mSelectPath.get(i));
							try {
								result = post.execute().get();
								try {
									JSONObject jsonObject = new JSONObject(result);
									resultList.put(jsonObject);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						}
					}
				}
				break;
			case CommonUtility.UPLOADINGCANCLE:
				if (post != null) {
					if (!post.isCancelled()) {
						post.cancel(true);
					}
				}
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 百度地图Start
	 * ----------------------------------------------------------------
	 */
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	public MyLocationListener mMyLocationListener;

	/**
	 * 获取当前城市
	 */
	public void getNowLocation() {
		// 百度地图
		mLocationClient = new LocationClient(this.getApplicationContext());
		// 放于MyLocationListener()之前
		InitLocation();
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			arrive_lng = String.valueOf(location.getLongitude());
			rarrive_lag = String.valueOf(location.getLatitude());
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
	}

	/**
	 * 百度地图END
	 * ------------------------------------------------------------------
	 */
}
