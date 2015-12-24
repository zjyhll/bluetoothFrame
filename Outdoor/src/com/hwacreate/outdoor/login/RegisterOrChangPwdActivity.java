package com.hwacreate.outdoor.login;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.CheckIdentifyCode.CheckIdentifyCodeRequest;
import com.keyhua.outdoor.protocol.CheckIdentifyCode.CheckIdentifyCodeRequestParameter;
import com.keyhua.outdoor.protocol.CheckIdentifyCode.CheckIdentifyCodeResponse;
import com.keyhua.outdoor.protocol.CheckIdentifyCode.CheckIdentifyCodeResponsePayload;
import com.keyhua.outdoor.protocol.CheckUserRegist.CheckUserRegistRequest;
import com.keyhua.outdoor.protocol.CheckUserRegist.CheckUserRegistRequestParameter;
import com.keyhua.outdoor.protocol.CheckUserRegist.CheckUserRegistResponse;
import com.keyhua.outdoor.protocol.CheckUserRegist.CheckUserRegistResponsePayload;
import com.keyhua.outdoor.protocol.sendIdentifyCode.AskServerSendMSGRequest;
import com.keyhua.outdoor.protocol.sendIdentifyCode.AskServerSendMSGRequestParameter;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

/**
 * @author ZT 注册忘记修改 SMSSDK
 * 
 */
public class RegisterOrChangPwdActivity extends BaseActivity {
	private CleareditTextView phoneNum = null;
	private CleareditTextView getyzm_et = null;
	private TextView getyzm_tv = null;
	private TextView yzm = null;
	private LinearLayout xieyi_ll = null;
	// 还是应该判断一下
	private String phoneNumStr = null;
	private String getyzm_etStr = null;
	// 手机验证码功能--------------------------------------------------
	private static Integer timerTicket = 0;
	private Handler handler = new Handler();
	private Timer delayTimer = null;
	//
	private String resultStr = null;
	// 是注册界面还是忘记密码界面
	private int zhuceorwangji = 0;
	private boolean ischeck = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yzm);
		initHeaderOther();
		init();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Cancel timer
		if (delayTimer != null) {
			delayTimer.cancel();
			delayTimer.purge();
			delayTimer = null;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getyzm_tv:// 获取验证码
			if (NetUtil.isNetworkAvailable(RegisterOrChangPwdActivity.this)) {
				phoneNumStr = phoneNum.getText().toString();
				if (zhuceorwangji == CommonUtility.XIUGAIMIMA) {
					if (TextUtils.isEmpty(phoneNumStr)) {
						phoneNumStr = phoneNum.getHint().toString();
					}
				}
				if (TextUtils.isEmpty(phoneNumStr)) {
					showToast("请输入手机号码");
				} else if (!CommonUtility.isPhoneNumber(phoneNumStr)) {
					showToast("手机号码错误");
				} else {
					ischeck = true;
					getyzm_tv.setClickable(false);
					getyzm_tv.setBackgroundResource(R.drawable.yaoyaole_yanzhengma_hui);
					showdialogtext("发送验证码中...");
					sendAsyn1();
				}
			} else {
				showToast(CommonUtility.ISNETCONNECTED);
			}
			break;
		case R.id.yzm:// 跳转到下个界面
			if (NetUtil.isNetworkAvailable(RegisterOrChangPwdActivity.this)) {
				getyzm_etStr = getyzm_et.getText().toString();
				phoneNumStr = phoneNum.getText().toString();
				if (TextUtils.isEmpty(phoneNumStr)) {
					phoneNumStr = phoneNum.getHint().toString();
				}
				if (TextUtils.isEmpty(phoneNumStr)) {
					showToast("请输入手机号码");
				} else if (!ischeck && TextUtils.isEmpty(getyzm_etStr)) {
					showToast("请点击获取验证码");
				} else if (TextUtils.isEmpty(getyzm_etStr)) {
					showToast("请输入验证码");
				} else {
					ischeck = false;
					yzm.setClickable(false);
					sendAsyn3();
				}
			} else {
				showToast(CommonUtility.ISNETCONNECTED);
			}
			break;
		case R.id.top_itv_back:// 返回
			finish();
			break;
		case R.id.xieyi_ll:// 协议
			openActivity(RegisterProActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		phoneNum = (CleareditTextView) findViewById(R.id.phoneNum);
		getyzm_et = (CleareditTextView) findViewById(R.id.getyzm_et);
		getyzm_tv = (TextView) findViewById(R.id.getyzm_tv);
		yzm = (TextView) findViewById(R.id.yzm);
		xieyi_ll = (LinearLayout) findViewById(R.id.xieyi_ll);
		Bundle bundle = getIntent().getExtras();
		zhuceorwangji = bundle.getInt("zhuceorwangji");
		switch (zhuceorwangji) {
		case CommonUtility.ZHUCE:// 注册
			top_tv_title.setText("注册");
			break;
		case CommonUtility.WANGJIMIMA:// 忘记
			top_tv_title.setText("忘记密码");
			xieyi_ll.setVisibility(View.GONE);
			break;
		case CommonUtility.XIUGAIMIMA:// 修改
			top_tv_title.setText("修改密码");
			xieyi_ll.setVisibility(View.GONE);
			phoneNum.setHint(App.getInstance().getPhonenum() != null ? App.getInstance().getPhonenum() : "");
			phoneNum.setEnabled(false);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResload() {
	}

	@Override
	protected void setMyViewClick() {
		getyzm_tv.setOnClickListener(this);
		top_itv_back.setOnClickListener(this);
		yzm.setOnClickListener(this);
		xieyi_ll.setOnClickListener(this);
	}

	private Thread thread = null;

	// 判断是否注册过
	public void sendAsyn1() {
		thread = new Thread() {
			public void run() {
				Action1();
			}
		};
		thread.start();
	}

	private int isregist = 0;// 用户是否注册，0为未注册，1为已注册

	public void Action1() {
		CheckUserRegistRequest request = new CheckUserRegistRequest();
		CheckUserRegistRequestParameter parameter = new CheckUserRegistRequestParameter();
		parameter.setPhonenum(phoneNumStr);
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
					CheckUserRegistResponse response = new CheckUserRegistResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					CheckUserRegistResponsePayload payload = (CheckUserRegistResponsePayload) response.getPayload();
					isregist = payload.getIsregist();
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

	// 请求服务器发送短信验证码
	public void sendAsyn2() {
		thread = new Thread() {
			public void run() {
				Action2();
			}
		};
		thread.start();
	}

	// 服务器返回提示信息

	public void Action2() {
		AskServerSendMSGRequest request = new AskServerSendMSGRequest();
		AskServerSendMSGRequestParameter parameter = new AskServerSendMSGRequestParameter();
		parameter.setPhonenum(phoneNumStr);
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
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);
				} else if (ret == 999) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROKYAN);
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

	// 校验短信验证码
	public void sendAsyn3() {
		thread = new Thread() {
			public void run() {
				Action3();
			}
		};
		thread.start();
	}

	private int isvalid = 0;// 用户是否注册，0无效，1为有效

	public void Action3() {
		CheckIdentifyCodeRequest request = new CheckIdentifyCodeRequest();
		CheckIdentifyCodeRequestParameter parameter = new CheckIdentifyCodeRequestParameter();
		parameter.setPhonenum(phoneNumStr);
		parameter.setIdentifyCode(getyzm_etStr);
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
					CheckIdentifyCodeResponse response = new CheckIdentifyCodeResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					CheckIdentifyCodeResponsePayload payload = (CheckIdentifyCodeResponsePayload) response.getPayload();
					isvalid = payload.getIsvalid();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
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
			if (zhuceorwangji == CommonUtility.WANGJIMIMA) {
				isregist = 0;
			} else if (zhuceorwangji == CommonUtility.XIUGAIMIMA) {
				isregist = 0;
			}
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				switch (isregist) {
				case 0:
					sendAsyn2();
					break;
				case 1:
					closedialog();
					getyzm_tv.setClickable(true);
					getyzm_tv.setBackgroundResource(R.drawable.yaoyaole_yanzhengma);
					showToast("你已经注册过了");
					break;

				default:
					break;
				}
				break;
			case CommonUtility.SERVEROK2:
				closedialog();
				showToast("验证码发送成功");
				delayTimer = new Timer();
				timerTicket = 60;
				TimerTask delayTask = new TimerTask() {
					@Override
					public void run() {
						handler.post(new Runnable() {
							public void run() {
								if (timerTicket <= 0) {
									getyzm_tv.setText("获取验证码");
									getyzm_tv.setClickable(true);
									getyzm_tv.setBackgroundResource(R.drawable.yaoyaole_yanzhengma);
									// Cancel Timer
									delayTimer.cancel();
									delayTimer.purge();
									delayTimer = null;
								} else {
									getyzm_tv.setText(timerTicket.toString() + "秒后再次获取");
									getyzm_tv.setClickable(false);
								}
								timerTicket = timerTicket - 1;
							}
						});
					}
				};
				delayTimer.schedule(delayTask, 0, 1000);
				break;
			case CommonUtility.SERVEROK3:
				Bundle bundle = new Bundle();
				switch (isvalid) {
				case 0:
					showToast("校验失败，请重新获取验证码");
					break;
				case 1:
					switch (zhuceorwangji) {
					case CommonUtility.ZHUCE:// 注册
						bundle.putString("phoneNumStr", phoneNumStr);
						bundle.putString("getyzm_etStr", getyzm_etStr);
						bundle.putInt("zhuceorwangji", CommonUtility.ZHUCE);
						openActivity(MimaActivity.class, bundle);
						break;
					case CommonUtility.WANGJIMIMA:// 忘记
						bundle.putString("phoneNumStr", phoneNumStr);
						bundle.putString("getyzm_etStr", getyzm_etStr);
						bundle.putInt("zhuceorwangji", CommonUtility.WANGJIMIMA);
						openActivity(MimaActivity.class, bundle);
						break;
					case CommonUtility.XIUGAIMIMA:// 修改
						bundle.putString("phoneNumStr", phoneNumStr);
						bundle.putString("getyzm_etStr", getyzm_etStr);
						bundle.putInt("zhuceorwangji", CommonUtility.XIUGAIMIMA);
						openActivity(MimaActivity.class, bundle);
						break;
					default:
						break;
					}
					break;

				default:
					break;
				}
				yzm.setClickable(true);
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
			case CommonUtility.SERVEROKYAN:
				closedialog();
				getyzm_tv.setClickable(true);
				getyzm_tv.setBackgroundResource(R.drawable.yaoyaole_yanzhengma);
				showToast("发送验证码上限，请与明日重试");
				break;
			default:
				break;
			}
		};
	};
}
