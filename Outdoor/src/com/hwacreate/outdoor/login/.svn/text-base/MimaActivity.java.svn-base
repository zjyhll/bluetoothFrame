package com.hwacreate.outdoor.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hwacreate.outdoor.MainActivity;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.UserFindPass.UserFindPass2Request;
import com.keyhua.outdoor.protocol.UserFindPass.UserFindPass2RequestParameter;
import com.keyhua.outdoor.protocol.UserFindPass.UserFindPass2Response;
import com.keyhua.outdoor.protocol.UserFindPass.UserFindPass2ResponsePayload;
import com.keyhua.outdoor.protocol.UserRegist.UserRegistRequest1;
import com.keyhua.outdoor.protocol.UserRegist.UserRegistRequestParameter1;
import com.keyhua.outdoor.protocol.UserRegist.UserRegistResponse1;
import com.keyhua.outdoor.protocol.UserRegist.UserRegistResponsePayload1;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

/**
 * @author NANA
 * 
 */
public class MimaActivity extends BaseActivity {
	private CleareditTextView mima = null;
	private CleareditTextView mima_r = null;
	private TextView ok = null;
	private String mimaStr = null;
	// 重新输入的密码
	private String mima_rStr = null;
	// 用户名
	private String phoneNumStr = null;
	private String getyzm_etStr = null;
	private String resultStr = null;
	// 是注册界面还是忘记密码界面
	private int zhuceorwangji = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mima);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok:
			if (NetUtil.isNetworkAvailable(MimaActivity.this)) {
				mimaStr = mima.getText().toString();
				mima_rStr = mima_r.getText().toString();
				if (TextUtils.isEmpty(mimaStr) || TextUtils.isEmpty(mima_rStr)) {
					showToast("密码不能为空");
				} else if (!TextUtils.equals(mimaStr, mima_rStr)) {
					showToast("两次输入的密码不一样");
				} else {
					// 在不为空的情况下进来
					if (TextUtils.equals(mimaStr, mima_rStr)) {
						// 这里会访问接口，将参数传入接口
						switch (zhuceorwangji) {
						case CommonUtility.ZHUCE:
							sendAsyn();
							break;
						case CommonUtility.WANGJIMIMA:
							sendAsyn1();
							break;
						case CommonUtility.XIUGAIMIMA:
							sendAsyn1();
							break;

						default:
							break;
						}
					} else {
						showToast("两次输入的密码不同");
					}
				}
			} else {
				showToast(CommonUtility.ISNETCONNECTED);
			}
			break;
		case R.id.top_itv_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle bundle = getIntent().getExtras();
		phoneNumStr = bundle.getString("phoneNumStr");
		getyzm_etStr = bundle.getString("getyzm_etStr");
		zhuceorwangji = bundle.getInt("zhuceorwangji");
		switch (zhuceorwangji) {
		case CommonUtility.ZHUCE:
			top_tv_title.setText("设置密码");
			break;
		case CommonUtility.WANGJIMIMA:
			top_tv_title.setText("重设密码");
			mima.setHint("输入重设密码");
			break;
		case CommonUtility.XIUGAIMIMA:
			top_tv_title.setText("修改密码");
			mima.setHint("输入修改密码");
			break;

		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		mima = (CleareditTextView) findViewById(R.id.mima);
		mima_r = (CleareditTextView) findViewById(R.id.mima_r);
		ok = (TextView) findViewById(R.id.ok);
	}

	@Override
	protected void onResload() {
	}

	@Override
	protected void setMyViewClick() {
		ok.setOnClickListener(this);
		top_itv_back.setOnClickListener(this);
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

	// 服务器返回提示信息
	private String msgString = null;
	private int State = 0;

	public void Action() {
		UserRegistRequest1 request = new UserRegistRequest1();
		UserRegistRequestParameter1 parameter = new UserRegistRequestParameter1();
		parameter.setPhonenum(phoneNumStr);
		parameter.setPassword(mimaStr);
		parameter.setValidatecode(getyzm_etStr);
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
					UserRegistResponse1 response = new UserRegistResponse1();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UserRegistResponsePayload1 payload = (UserRegistResponsePayload1) response.getPayload();
					msgString = payload.getMsg();
					App.getInstance().setAut(payload.getAut());
					App.getInstance().setPhonenum(payload.getPhonenum());
					App.getInstance().setRole(payload.getRole());
					App.getInstance().setUserid(payload.getUserid());
					App.getInstance().setNickname(payload.getNickname());
					App.getInstance().setHeadurl(payload.getHead());
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
		UserFindPass2Request request = new UserFindPass2Request();
		request.setAuthenticationToken(App.getInstance().getAut());
		UserFindPass2RequestParameter parameter = new UserFindPass2RequestParameter();
		parameter.setPhoneNumber(phoneNumStr);
		parameter.setNewpassword(mimaStr);
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
					UserFindPass2Response response = new UserFindPass2Response();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UserFindPass2ResponsePayload payload = (UserFindPass2ResponsePayload) response.getPayload();
					State = payload.getRegistState();
					msgString = payload.getResult();
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
				showToast(msgString);
				openActivity(MainActivity.class);
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
