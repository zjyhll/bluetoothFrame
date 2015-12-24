package com.hwacreate.outdoor.leftFragment.gongju;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.EvaluateActivityAction.EvaluateActivityRequest;
import com.keyhua.outdoor.protocol.EvaluateActivityAction.EvaluateActivityRequestParameter;
import com.keyhua.outdoor.protocol.EvaluateActivityAction.EvaluateActivityResponse;
import com.keyhua.outdoor.protocol.EvaluateActivityAction.EvaluateActivityResponsePayload;
import com.keyhua.outdoor.protocol.UserEvaluateActivityAction.UserEvaluateActivityRequest;
import com.keyhua.outdoor.protocol.UserEvaluateActivityAction.UserEvaluateActivityRequestParameter;
import com.keyhua.outdoor.protocol.UserEvaluateActivityAction.UserEvaluateActivityResponse;
import com.keyhua.outdoor.protocol.UserEvaluateActivityAction.UserEvaluateActivityResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShouDuiDianPingActivity extends BaseActivity {
	private TextView tv_tijiao = null;// 提交按钮
	private EditText ctv_dianping = null;// 输入内容
	private RatingBar wode_ratingbar_1 = null;// 最新动态
	private RatingBar wode_ratingbar_2 = null;// 危险系数
	private RatingBar wode_ratingbar_3 = null;// 沿途风景
	private RatingBar wode_ratingbar_4 = null;// 体力挑战
	private RatingBar wode_ratingbar_5 = null;// 后勤资源
	private RatingBar wode_ratingbar_6 = null;// 推荐重游
	private RatingBar wode_ratingbar_7 = null;// 领队素质
	private float wode_ratingbar_1_Str = 0;// 最新动态
	private float wode_ratingbar_2_Str = 0;// 危险系数
	private float wode_ratingbar_3_Str = 0;// 沿途风景
	private float wode_ratingbar_4_Str = 0;// 体力挑战
	private float wode_ratingbar_5_Str = 0;// 后勤资源
	private float wode_ratingbar_6_Str = 0;// 推荐重游
	private float wode_ratingbar_7_Str = 0;// 领队素质
	private String content = null;// 点评内容
	private String act_id = null;
	private int isLeader = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shou_dui_dian_ping);
		act_id = getIntent().getStringExtra("act_id");
		isLeader = getIntent().getIntExtra("isLeader", 0);
		initHeaderOther();
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.tv_tijiao:
			wode_ratingbar_1_Str = wode_ratingbar_1.getRating();
			wode_ratingbar_2_Str = wode_ratingbar_2.getRating();
			wode_ratingbar_3_Str = wode_ratingbar_3.getRating();
			wode_ratingbar_4_Str = wode_ratingbar_4.getRating();
			wode_ratingbar_5_Str = wode_ratingbar_5.getRating();
			wode_ratingbar_6_Str = wode_ratingbar_6.getRating();
			wode_ratingbar_7_Str = wode_ratingbar_7.getRating();
			content = ctv_dianping.getText().toString();
			if (!TextUtils.isEmpty(content)) {
				if (isLeader == 2) {// 队员
					sendAsyn1();
				} else if (isLeader == 1) {// 领队
					sendAsyn();
				}
			} else {
				showToast("点评内容为空！");
			}
			break;
		default:
			break;
		}
	}

	private TextView tv11 = null;
	private TextView tv12 = null;
	private TextView tv13 = null;
	private TextView tv14 = null;
	private CircleImageView tv15 = null;
	private TextView tv16 = null;
	private TextView tv17 = null;
	private TextView tv18 = null;
	private TextView tv19 = null;
	private TextView tv20 = null;
	private TextView tv21 = null;
	private ImageView iv_map = null;

	@Override
	protected void onInitData() {
		tv11 = (TextView) findViewById(R.id.tv11);
		tv12 = (TextView) findViewById(R.id.tv12);
		tv13 = (TextView) findViewById(R.id.tv13);
		tv14 = (TextView) findViewById(R.id.tv14);
		tv15 = (CircleImageView) findViewById(R.id.tv15);
		tv16 = (TextView) findViewById(R.id.tv16);
		tv17 = (TextView) findViewById(R.id.tv17);
		tv18 = (TextView) findViewById(R.id.tv18);
		tv19 = (TextView) findViewById(R.id.tv19);
		tv20 = (TextView) findViewById(R.id.tv20);
		tv21 = (TextView) findViewById(R.id.tv21);
		iv_map = (ImageView) findViewById(R.id.iv_map);
		tv_tijiao = (TextView) findViewById(R.id.tv_tijiao);
		ctv_dianping = (EditText) findViewById(R.id.ctv_dianping);
		wode_ratingbar_1 = (RatingBar) findViewById(R.id.wode_ratingbar_1);
		wode_ratingbar_2 = (RatingBar) findViewById(R.id.wode_ratingbar_2);
		wode_ratingbar_3 = (RatingBar) findViewById(R.id.wode_ratingbar_3);
		wode_ratingbar_4 = (RatingBar) findViewById(R.id.wode_ratingbar_4);
		wode_ratingbar_5 = (RatingBar) findViewById(R.id.wode_ratingbar_5);
		wode_ratingbar_6 = (RatingBar) findViewById(R.id.wode_ratingbar_6);
		wode_ratingbar_7 = (RatingBar) findViewById(R.id.wode_ratingbar_7);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("收队点评");
		iv_map.setVisibility(View.GONE);
		tv11.setText(getIntent().getExtras().getString("tv11"));
		tv12.setText(getIntent().getExtras().getString("tv12"));
		tv13.setText(getIntent().getExtras().getString("tv13"));
		tv14.setText(getIntent().getExtras().getString("tv14"));
		if (!TextUtils.isEmpty(getIntent().getExtras().getString("tv15"))) {
			mImageLoader.displayImage(getIntent().getExtras().getString("tv15"), tv15, options);
			tv15.setVisibility(View.VISIBLE);
		} else {
			tv15.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(getIntent().getExtras().getString("tv16"))) {
			tv16.setText(getIntent().getExtras().getString("tv16"));
			tv16.setVisibility(View.VISIBLE);
		} else {
			tv16.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(getIntent().getExtras().getString("tv17"))) {
			tv17.setText(getIntent().getExtras().getString("tv17"));
			tv17.setVisibility(View.VISIBLE);
		} else {
			tv17.setVisibility(View.GONE);
		}
		tv18.setText(getIntent().getExtras().getString("tv18"));
		tv19.setText(getIntent().getExtras().getString("tv19"));
		tv20.setText(getIntent().getExtras().getString("tv20"));
		tv21.setText(getIntent().getExtras().getString("tv21"));
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		tv_tijiao.setOnClickListener(this);
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

	private String mString = null;

	public void Action() {
		EvaluateActivityRequest request = new EvaluateActivityRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		EvaluateActivityRequestParameter parameter = new EvaluateActivityRequestParameter();
		parameter.setAct_id(act_id);
		parameter.setNewest_status((int) wode_ratingbar_1_Str);
		parameter.setDanger_coefficient((int) wode_ratingbar_2_Str);
		parameter.setScenery((int) wode_ratingbar_3_Str);
		parameter.setPhysical_challenge((int) wode_ratingbar_4_Str);
		parameter.setLogistics_resources((int) wode_ratingbar_5_Str);
		parameter.setRecommended_revisit((int) wode_ratingbar_6_Str);
		parameter.setLeader_quality((int) wode_ratingbar_7_Str);
		parameter.setEvaluate(content);
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
				mString = responseObject.getString("msg");
				if (ret == 0) {
					EvaluateActivityResponse response = new EvaluateActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					EvaluateActivityResponsePayload payload = (EvaluateActivityResponsePayload) response.getPayload();
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

	public void sendAsyn1() {
		thread = new Thread() {
			public void run() {
				Action1();
			}
		};
		thread.start();
	}

	public void Action1() {
		UserEvaluateActivityRequest request = new UserEvaluateActivityRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		UserEvaluateActivityRequestParameter parameter = new UserEvaluateActivityRequestParameter();
		parameter.setAct_id(act_id);
		parameter.setNewest_status((int) wode_ratingbar_1_Str);
		parameter.setDanger_coefficient((int) wode_ratingbar_2_Str);
		parameter.setScenery((int) wode_ratingbar_3_Str);
		parameter.setPhysical_challenge((int) wode_ratingbar_4_Str);
		parameter.setLogistics_resources((int) wode_ratingbar_5_Str);
		parameter.setRecommended_revisit((int) wode_ratingbar_6_Str);
		parameter.setLeader_quality((int) wode_ratingbar_7_Str);
		parameter.setEvaluate(content);
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
				mString = responseObject.getString("msg");
				if (ret == 0) {
					UserEvaluateActivityResponse response = new UserEvaluateActivityResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UserEvaluateActivityResponsePayload payload = (UserEvaluateActivityResponsePayload) response
							.getPayload();
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
				if (!TextUtils.equals(mString, "操作执行成功")) {
					showToast(mString);
					mString = "";
				} else {
					showToast("点评成功");
				}
				finish();
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				if (!TextUtils.equals(mString, "操作执行成功")) {
					showToast(mString);
					mString = "";
				} else {
					showToast("点评失败，请稍后重试");
				}
				break;
			default:
				break;
			}
		};
	};
}
