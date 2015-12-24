package com.hwacreate.outdoor.mainFragment.wode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import com.hwacreate.outdoor.R;
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
import com.hwacreate.outdoor.view.MyGridView;
import com.hwacreate.outdoor.view.PhotoView;
import com.hwacreate.outdoor.view.PickerView;
import com.hwacreate.outdoor.view.PickerView.onSelectListener;
import com.hwacreate.outdoor.view.ReboundScrollView;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryRequest;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryRequestParameter;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryResponse;
import com.keyhua.outdoor.protocol.AddActHistoryAction.AddActHistoryResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.nereo.multi_image_selector.MultiImageSelectorActivity_YouJi;
import me.nereo.multi_image_selector.bean.Image;

/**
 * @author 曾金叶 编辑游记
 * 
 */
public class EditYoujiActivity extends BaseActivity {
	// 选择照片列表
	private int Count = 5;
	private MyGridView mGridView = null; //
	private ImageGridAdapter mAdapter = null;
	public ArrayList<String> mSelectPath;
	// 点击浏览大图
	private View mParent = null; //
	private View mBg = null; //
	private PhotoView mPhotoView = null; //
	private Info mInfo = null;
	private AlphaAnimation in = new AlphaAnimation(0, 1);
	private AlphaAnimation out = new AlphaAnimation(1, 0);
	private TextView ok = null;//
	private TextView delete = null; //
	private Image tempImage = null;
	private int tempIndex = -1;
	// 上传
	private HttpMultipartPost post;
	private String result = null;
	private JSONArray resultList = null;
	//
	private RelativeLayout rl = null;

	private ImageView iv_btn = null;
	private TextView tv_sjxz = null;
	// pop
	private PopupWindow pop_user = null;// 详细时间pop
	private PickerView pickerviewleft = null;
	private PickerView pickerviewright = null;
	private TextView pickerviewtext = null;
	private LinearLayout pickerview = null;
	private TextView geren_qu = null;
	private TextView geren_wan = null;
	// 存放的数据
	private List<String> left = null;
	private List<String> right = null;
	private String timeleft = null;// 小时
	private String timeright = null;// 分钟
	private ReboundScrollView fragkan_sv = null;
	private CleareditTextView tvl_bt = null;
	private EditText tvl_desc = null;
	// 数据
	private String footprint_title = null;
	private String footprint_time = null;
	private String footprint_desc = null;
	private JSONArray imgs = null;
	// 编辑足迹
	private String footprint = null;
	private int footprintPotion = 0;
	private boolean tianjia = false;
	// 第一次进入标记
	private boolean isfirst = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_youji);
		footprint = getIntent().getStringExtra("footprint");
		footprintPotion = getIntent().getIntExtra("footprintPotion", 0);
		tianjia = getIntent().getBooleanExtra("tianjia", false);
		init();
	}

	protected void onRestart() {
		super.onRestart();
		// if (Bimp.tempSelectBitmap.size() == 0 && mSelectPath.size() == 0) {
		//
		if (mSelectPath.size() == 0) {
			rl.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		} else {
			rl.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		}
		mAdapter.update();

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
		case R.id.tv_sjxz:// 时间选择
			onPopdataShengri();
			break;
		case R.id.top_tv_right:
			// 一个脚印
			footprint_title = tvl_bt.getText().toString();
			footprint_desc = tvl_desc.getText().toString();
			if (resultList != null && resultList.length() == 0) {
				showToast("请选择图片");
			} else if (TextUtils.isEmpty(footprint_title)) {
				showToast("请输入标题");
			} else if (TextUtils.isEmpty(footprint_desc)) {
				showToast("请输入描述");
			} else {
				arrive_time = TimeUtil.getDatetime();
				JSONObject jsonObjectUpto = new JSONObject();
				try {
					// 上传服务器时使用的
					jsonObjectUpto.put("footprint_title", footprint_title);
					jsonObjectUpto.put("footprint_desc", footprint_desc);
					jsonObjectUpto.put("footprint_time", arrive_time);
					if (tianjia) {// 添加
						JSONArray array = new JSONArray();
						for (int i = 0; i < resultList.length(); i++) {
							array.put(resultList.getJSONObject(i).get("fullurl"));
						}
						jsonObjectUpto.put("imgs", array);
						XinJianYouJizjyActivity.footprintArrayUptoNet.put(jsonObjectUpto);
					} else {// 编辑
						JSONArray array = new JSONArray();
						for (int i = 0; i < resultList.length(); i++) {
							array.put(resultList.getJSONObject(i).get("fullurl"));
						}
						if (!TextUtils.isEmpty(footprint)) {
							try {
								JSONObject arrayfoot = new JSONObject(footprint);
								for (int i = 0; i < arrayfoot.getJSONArray("imgs").length(); i++) {
									array.put(arrayfoot.getJSONArray("imgs").get(i));
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						jsonObjectUpto.put("imgs", array);
						XinJianYouJizjyActivity.footprintArrayUptoNet.put(footprintPotion, jsonObjectUpto);
					}
				} catch (JSONException e1) {
				}
				FinishClear();
				finish();
			}
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
			if (mParent.getVisibility() == View.VISIBLE && tempIndex != -1) {
				try {
					mSelectPath.remove(tempIndex);
					resultList.remove(tempIndex);
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
			mAdapter.update();
			break;
		case R.id.iv_btn:
			Intent intent = new Intent(EditYoujiActivity.this, MultiImageSelectorActivity_YouJi.class);
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
		case R.id.geren_qu:
			pop_user.dismiss();
			break;
		case R.id.geren_wan:
			pop_user.dismiss();
			tv_sjxz.setText(timeleft + ":" + timeright);
			break;
		default:
			break;

		}
	}

	@Override
	protected void onInitData() {
		initDate();
		onPopwindowntime();
		initHeaderOther();
		tvl_bt = (CleareditTextView) findViewById(R.id.tvl_bt);
		tvl_desc = (EditText) findViewById(R.id.tvl_desc);
		fragkan_sv = (ReboundScrollView) findViewById(R.id.fragkan_sv);
		fragkan_sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
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
		tv_sjxz = (TextView) findViewById(R.id.tv_sjxz);
		mPhotoView = (PhotoView) findViewById(R.id.img_pic);
		mGridView = (MyGridView) findViewById(R.id.youji_pic);
		//
		if (!TextUtils.isEmpty(footprint)) {
			try {
				JSONObject array = new JSONObject(footprint);
				tvl_bt.setText(array.getString("footprint_title"));
				tvl_desc.setText(array.getString("footprint_desc"));
				tv_sjxz.setText(array.getString("footprint_time"));
				for (int i = 0; i < array.getJSONArray("imgs").length(); i++) {
					if (!mSelectPath.contains((String) array.getJSONArray("imgs").get(i))) {// 如果包含的话就不加入了
						mSelectPath.add((String) array.getJSONArray("imgs").get(i));
					}
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
		if (mSelectPath.size() == 0) {
			rl.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		} else {
			rl.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
		}
		if (isfirst) {
			handlerlist.sendEmptyMessage(CommonUtility.UPLOADING);
			isfirst = false;
		}
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("添加行程");
		top_tv_right.setText("确认");
		mAdapter = new ImageGridAdapter();
		mGridView.setAdapter(mAdapter);
		ok.setOnClickListener(this);
		delete.setOnClickListener(this);
		mBg.setOnClickListener(this);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				if (position == mSelectPath.size()) {
					Intent intent = new Intent(EditYoujiActivity.this, MultiImageSelectorActivity_YouJi.class);
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
					if (!TextUtils.isEmpty(mSelectPath.get(position)) && mSelectPath.get(position).contains("http:")) {
						mImageLoader.displayImage(mSelectPath.get(position), mPhotoView, options);
					} else {
						mImageLoader.displayImage("file://" + mSelectPath.get(position), mPhotoView, options);
					}
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
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		iv_btn.setOnClickListener(this);
		tv_sjxz.setOnClickListener(this);
		geren_qu.setOnClickListener(this);
		geren_wan.setOnClickListener(this);
	}

	/** 得到选择的照片 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity_YouJi.EXTRA_RESULT);
				// 本地地址与网络地址要存两套吗
				imgs = new JSONArray(mSelectPath);
				handlerlist.sendEmptyMessage(CommonUtility.UPLOADING);
			}
		}
	}

	public class ImageGridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (mSelectPath.size() == Count) {
				return Count;
			}
			return (mSelectPath.size() + 1);
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			PhotoView p = new PhotoView(EditYoujiActivity.this);
			p.setLayoutParams(new AbsListView.LayoutParams(App.getInstance().getScreenWidth() / 5,
					App.getInstance().getScreenWidth() / 5));
			p.setScaleType(ImageView.ScaleType.FIT_XY);
			if (position == mSelectPath.size()) {
				p.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jiahao));
				if (position == Count) {
					p.setVisibility(View.GONE);
				}
			} else {
				if (!TextUtils.isEmpty(mSelectPath.get(position)) && mSelectPath.get(position).contains("http:")) {
					mImageLoader.displayImage(mSelectPath.get(position), p, options);
				} else {
					mImageLoader.displayImage("file://" + mSelectPath.get(position), p, options);
				}
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

	public void Action() {
		AddActHistoryRequest request = new AddActHistoryRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		AddActHistoryRequestParameter parameter = new AddActHistoryRequestParameter();
		parameter.setAct_id(App.getInstance().getHuoDongId());
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
				resultList = new JSONArray();
				if (mSelectPath != null && mSelectPath.size() != 0) {
					for (int i = 0; i < mSelectPath.size(); i++) {
						if (!TextUtils.isEmpty(mSelectPath.get(i))) {
							File file = new File(mSelectPath.get(i));
							if (file.exists()) {
								post = new HttpMultipartPost(EditYoujiActivity.this, mSelectPath.get(i));
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
						} else {
							JSONObject jsonObject = new JSONObject();
							resultList.put(jsonObject);
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

	// 初始化数据
	private void initDate() {
		left = new ArrayList<String>();
		right = new ArrayList<String>();
		for (int i = 0; i < 24; i++) {
			left.add(i < 10 ? "0" + i : "" + i);
		}
		for (int i = 0; i < 60; i++) {
			right.add(i < 10 ? "0" + i : "" + i);
		}
	}

	// 时间pop
	private void onPopdataShengri() {
		pickerview.setVisibility(View.VISIBLE);
		pop_user.showAtLocation(fragkan_sv, Gravity.CENTER, 0, 0);
		pickerviewtext.setVisibility(View.VISIBLE);
		pickerviewright.setVisibility(View.VISIBLE);
		pickerviewleft.setData(left);
		pickerviewright.setData(right);
		pickerviewleft.setOnSelectListener(new onSelectListener() {
			@Override
			public void onSelect(String text) {
				// showToast("选择了 " + text);
				timeleft = text;
			}
		});
		pickerviewright.setOnSelectListener(new onSelectListener() {
			@Override
			public void onSelect(String text) {
				// showToast("选择了 " + text);
				timeright = text;
			}
		});
	}

	private void onPopwindowntime() {

		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件
		View view = inflater.inflate(R.layout.popwindow_usershezhi, null);
		// 初始化pop中的组件
		pickerviewleft = (PickerView) view.findViewById(R.id.pickerviewleft);
		pickerviewtext = (TextView) view.findViewById(R.id.pickerviewtext);
		pickerviewright = (PickerView) view.findViewById(R.id.pickerviewright);
		pickerview = (LinearLayout) view.findViewById(R.id.pickerview);
		geren_qu = (TextView) view.findViewById(R.id.geren_qu);
		geren_wan = (TextView) view.findViewById(R.id.geren_wan);
		// 创建PopupWindow对象
		pop_user = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		// 设置此参数获得焦点，否则无法点击
		pop_user.setFocusable(true);
		// 设置点击窗口外边窗口消失
		pop_user.setOutsideTouchable(true);
		// 需要设置一下此参数，点击外边可消失
		pop_user.setBackgroundDrawable(new BitmapDrawable());
	}
}
