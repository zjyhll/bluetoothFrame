package com.hwacreate.outdoor.mainFragment.wode;

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
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.uploadwithprogress.http.HttpMultipartPost;
import com.hwacreate.outdoor.utl.Bimp;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.view.Info;
import com.hwacreate.outdoor.view.PhotoView;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelRequest;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelRequestParameter;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelResponse;
import com.keyhua.outdoor.protocol.AddTravelAction.AddTravelResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

/**
 * @author LaLa 新加游记详情
 * 
 */
public class XinJiaYouJiActivity extends BaseActivity {
	private CleareditTextView tvl_title_edit = null;// 游记名称
	private CleareditTextView tvl_type_edit = null;// 类型
	private CleareditTextView scenicsots_edit = null;// 景区
	private CleareditTextView city_edit = null;// 离线地图包城市
	private CleareditTextView trace_data_edit = null;// 轨迹
	private CleareditTextView distance_edit = null;// 全程距离
	private CleareditTextView act_type_edit = null;// 活动类型
	private CleareditTextView leader_id_edit = null;// 领队
	private CleareditTextView leader_name_edit = null;// 领队名称
	private CleareditTextView team_number_edit = null;// 队伍人数
	private CleareditTextView is_share_edit = null;// 是否分享给大家
	private CleareditTextView act_start_time_edit = null;// 开始时间
	private CleareditTextView act_end_time_edit = null;// 结束时间
	private CleareditTextView tvl_desc_edit = null;// 游记描述

	// 选择照片列表
	private int Count = 8;
	private GridView mGridView = null;
	private ImageGridAdapter mAdapter = null;
	public ArrayList<String> mSelectPath;
	// 点击浏览大图
	private View mParent = null;
	private View mBg = null;
	private PhotoView mPhotoView = null;
	private Info mInfo = null;
	private AlphaAnimation in = new AlphaAnimation(0, 1);
	private AlphaAnimation out = new AlphaAnimation(1, 0);
	private TextView ok = null;
	private TextView delete = null;
	private Image tempImage = null;
	private int tempIndex = -1;
	// 上传
	private HttpMultipartPost post;
	private String result = null;
	private JSONArray resultList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiayouji);
		init();
	}

	protected void onRestart() {
		super.onRestart();
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
		case R.id.top_tv_right:
			tvl_title = tvl_title_edit.getText().toString();
			tvl_desc = tvl_desc_edit.getText().toString();
			tvl_type = tvl_type_edit.getText().toString();
			scenicsots = scenicsots_edit.getText().toString();
			city = city_edit.getText().toString();
			trace_data = trace_data_edit.getText().toString();
			act_type = act_type_edit.getText().toString();
			act_start_time = act_start_time_edit.getText().toString();
			act_end_time = act_end_time_edit.getText().toString();
			distance = Integer.valueOf(!TextUtils.isEmpty(distance_edit
					.getText()) ? distance_edit.getText().toString() : "0");
			team_number = Integer.valueOf(!TextUtils.isEmpty(team_number_edit
					.getText()) ? team_number_edit.getText().toString() : "0");
			leader_id = leader_id_edit.getText().toString();
			leader_name = leader_name_edit.getText().toString();
			is_share = Integer.valueOf(!TextUtils.isEmpty(is_share_edit
					.getText().toString()) ? is_share_edit.getText().toString()
					: "0");
			sendAsyn();
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
			if (mParent.getVisibility() == View.VISIBLE && tempImage != null
					&& tempIndex != -1) {
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
		mBg = findViewById(R.id.bg_pic);
		ok = (TextView) findViewById(R.id.ok_pic);
		delete = (TextView) findViewById(R.id.delete_pic);
		mPhotoView = (PhotoView) findViewById(R.id.img_pic);
		mGridView = (GridView) findViewById(R.id.youji_pic);
		tvl_title_edit = (CleareditTextView) findViewById(R.id.tvl_title);
		tvl_type_edit = (CleareditTextView) findViewById(R.id.tvl_type);
		scenicsots_edit = (CleareditTextView) findViewById(R.id.scenicsots);
		city_edit = (CleareditTextView) findViewById(R.id.city);
		trace_data_edit = (CleareditTextView) findViewById(R.id.trace_data);
		distance_edit = (CleareditTextView) findViewById(R.id.distance);
		act_type_edit = (CleareditTextView) findViewById(R.id.act_type);
		leader_id_edit = (CleareditTextView) findViewById(R.id.leader_id);
		leader_name_edit = (CleareditTextView) findViewById(R.id.leader_name);
		team_number_edit = (CleareditTextView) findViewById(R.id.team_number);
		is_share_edit = (CleareditTextView) findViewById(R.id.is_share);
		act_start_time_edit = (CleareditTextView) findViewById(R.id.act_start_time);
		act_end_time_edit = (CleareditTextView) findViewById(R.id.act_end_time);
		tvl_desc_edit = (CleareditTextView) findViewById(R.id.tvl_desc);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("新建游记");
		top_tv_right.setText("发布");
		mAdapter = new ImageGridAdapter();
		mGridView.setAdapter(mAdapter);
		ok.setOnClickListener(this);
		delete.setOnClickListener(this);
		mBg.setOnClickListener(this);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				if (position == Bimp.tempSelectBitmap.size()) {
					Intent intent = new Intent(XinJiaYouJiActivity.this,
							MultiImageSelectorActivity_YouJi.class);
					// 是否显示拍摄图片
					intent.putExtra(
							MultiImageSelectorActivity_YouJi.EXTRA_SHOW_CAMERA,
							true);
					// 最大可选择图片数量
					intent.putExtra(
							MultiImageSelectorActivity_YouJi.EXTRA_SELECT_COUNT,
							Count);
					// 选择模式
					intent.putExtra(
							MultiImageSelectorActivity_YouJi.EXTRA_SELECT_MODE,
							MultiImageSelectorActivity_YouJi.MODE_MULTI);
					// 多选数据
					if (mSelectPath != null && mSelectPath.size() > 0) {
						intent.putExtra(
								MultiImageSelectorActivity_YouJi.EXTRA_DEFAULT_SELECTED_LIST,
								mSelectPath);
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
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
	}

	/** 得到选择的照片 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				mSelectPath = data
						.getStringArrayListExtra(MultiImageSelectorActivity_YouJi.EXTRA_RESULT);
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
			PhotoView p = new PhotoView(XinJiaYouJiActivity.this);
			p.setLayoutParams(new AbsListView.LayoutParams(App.getInstance()
					.getScreenWidth() / 5,
					App.getInstance().getScreenWidth() / 5));
			p.setScaleType(ImageView.ScaleType.FIT_XY);
			if (position == Bimp.tempSelectBitmap.size()) {
				p.setImageBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.jiahao));
				if (position == Count) {
					p.setVisibility(View.GONE);
				}
			} else {
				p.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
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

	private String act_id = "act1";// 活动id
	private String tvl_title = null;// 行程(游记标题)
	private String tvl_cover = null;// 游记封面图片
	private String tvl_desc = null;// 游记描述
	private String tvl_type = null;// 游记类型(同活动类型)
	private String scenicsots = null;// 游记(活动)景区
	private String city = null;// 离线地图包城市
	private String picture_url = null;// 截图路径
	private String trace_data = null;// 轨迹数据内容
	private String anchor_longitude = null;// 锚点经度
	private String anchor_latitude = null;// 锚点纬度
	private String act_type = null;// 活动类型
	private String act_start_time = null;// 开始时间
	private String act_end_time = null;// 结束时间
	private int distance = 0;// 全程距离
	private int team_number = 0;// 队伍人数
	private String leader_id = null;// 领队id
	private String leader_name = null;// 领队名称
	private String footprint_data = null;// 脚印数据
	private int is_share = 0;// 是否分享(是否私有)

	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() {
		AddTravelRequest request = new AddTravelRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		AddTravelRequestParameter parameter = new AddTravelRequestParameter();
		parameter.setTvl_title(tvl_title);
		parameter.setTvl_cover(tvl_cover);
		parameter.setTvl_desc(tvl_desc);
		parameter.setTvl_type(tvl_type);
		parameter.setScenicsots(scenicsots);
		parameter.setCity(city);
		parameter.setPicture_url(resultList != null ? resultList.toString()
				: "");
		parameter.setAnchor_longitude(anchor_longitude);
		parameter.setAnchor_latitude(anchor_latitude);
		parameter.setAct_start_time(act_start_time);
		parameter.setAct_end_time(act_end_time);
		parameter.setDistance(distance);
		parameter.setTeam_number(team_number);
		parameter.setLeader_id(leader_id);
		parameter.setLeader_name(leader_name);
		parameter.setFootprint_data(footprint_data);
		parameter.setIs_share(is_share);
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
					AddTravelResponse response = new AddTravelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					AddTravelResponsePayload payload = (AddTravelResponsePayload) response
							.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
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

	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				FinishClear();
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
							post = new HttpMultipartPost(
									XinJiaYouJiActivity.this,
									mSelectPath.get(i));
							try {
								result = post.execute().get();
								try {
									JSONObject jsonObject = new JSONObject(
											result);
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
}
