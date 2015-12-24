package com.hwacreate.outdoor.leftFragment.shezhi;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.KeXuanHuoDongActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoRequest;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoRequestParameter;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoResponse;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoResponsePayload;
import com.keyhua.outdoor.protocol.OutdoorFeedbackAction.OutdoorFeedbackRequest;
import com.keyhua.outdoor.protocol.OutdoorFeedbackAction.OutdoorFeedbackRequestParameter;
import com.keyhua.outdoor.protocol.OutdoorFeedbackAction.OutdoorFeedbackResponse;
import com.keyhua.outdoor.protocol.OutdoorFeedbackAction.OutdoorFeedbackResponsePayload;
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
import android.widget.TextView;

/**
 * @author LaLa 意见反馈
 *
 */
public class YiJianActivity extends BaseActivity {
	private CleareditTextView yijianphone = null;
	private EditText yijianfankui = null;
	private TextView yijianfabiao = null;
	private String phone = null;
	private String fankui = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shezhi_yijian);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.yijianfabiao:
			phone = yijianphone.getText().toString();
			fankui = yijianfankui.getText().toString();
			if (NetUtil.isNetworkAvailable(YiJianActivity.this)) {
				if (TextUtils.isEmpty(phone)) {
					showToast("请输入手机号码");
				} else if (!CommonUtility.isPhoneNumber(phone)) {
					showToast("手机号码错误");
				} else if (TextUtils.isEmpty(fankui)) {
					showToast("请输入反馈信息");
				} else if (fankui.length() < 5) {
					showToast("反馈信息不能少于5个字");
				} else {
					if (!TextUtils.isEmpty(App.getInstance().getAut())) {
						sendAsyn();
					} else {
						showToastDengLu();
						App.getInstance().setAut("");
						openActivity(LoginActivity.class);
					}
				}
			} else {
				showToast(CommonUtility.ISNETCONNECTED);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		yijianphone = (CleareditTextView) findViewById(R.id.yijianphone);
		yijianfankui = (EditText) findViewById(R.id.yijianfankui);
		yijianfabiao = (TextView) findViewById(R.id.yijianfabiao);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("意见反馈");
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		yijianfabiao.setOnClickListener(this);
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

	private int State = 0;
	private String Msg = null;

	public void Action() {
		OutdoorFeedbackRequest request = new OutdoorFeedbackRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		OutdoorFeedbackRequestParameter parameter = new OutdoorFeedbackRequestParameter();
		parameter.setFbk_msg(fankui);
		parameter.setPhonenum(phone);
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
					OutdoorFeedbackResponse response = new OutdoorFeedbackResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					OutdoorFeedbackResponsePayload payload = (OutdoorFeedbackResponsePayload) response.getPayload();
					State = payload.getState();
					Msg = payload.getMsg();
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
				if (State == 0) {
					showToast("反馈失败");
				} else {
					showToast(Msg);
					finish();
				}
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
			default:
				break;
			}
		};
	};
}
