package com.hwacreate.outdoor.mainFragment.wode;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import me.nereo.multi_image_selector.bean.Image;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.GridView;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.wode.XinJiaYouJiActivity.ImageGridAdapter;
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
public class XinJianTexstYouJiActivity extends BaseActivity {
	// 布局控件
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
	// action 请求线程
	private Thread thread = null;
	// 新建游记上传接口字段
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
	// 上传
	private HttpMultipartPost post;
	private String result = null;
	private JSONArray resultList = null;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youji_xinjian);
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

	@Override
	protected void onInitData() {
		initHeaderOther();
		resultList = new JSONArray();
		mSelectPath = new ArrayList<String>();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("新建游记");
		top_tv_right.setText("提交审核");
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		}
	}

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

	private void FinishClear() {
		finish();
		Bimp.tempSelectBitmap.clear();
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
									XinJianTexstYouJiActivity.this,
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
