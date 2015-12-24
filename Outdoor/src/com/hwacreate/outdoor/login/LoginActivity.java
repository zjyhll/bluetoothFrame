package com.hwacreate.outdoor.login;

import org.afinal.simplecache.ACache;

import com.hwacreate.outdoor.MainActivity;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DataCleanManager;
import com.hwacreate.outdoor.utl.FastBlur;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoRequest;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoRequestParameter;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoResponse;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoResponsePayload;
import com.keyhua.outdoor.protocol.SetUserChannelAction.SetUserChannelRequest;
import com.keyhua.outdoor.protocol.SetUserChannelAction.SetUserChannelRequestParameter;
import com.keyhua.outdoor.protocol.SetUserChannelAction.SetUserChannelResponse;
import com.keyhua.outdoor.protocol.SetUserChannelAction.SetUserChannelResponsePayload;
import com.keyhua.outdoor.protocol.UserLogin.UserLoginRequest;
import com.keyhua.outdoor.protocol.UserLogin.UserLoginRequestParameter;
import com.keyhua.outdoor.protocol.UserLogin.UserLoginResponse;
import com.keyhua.outdoor.protocol.UserLogin.UserLoginResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {
	// 登录页面
	private CleareditTextView yonghuming = null, mima = null;
	private TextView denglu = null;
	private TextView zhuce = null;
	private TextView wangji = null;
	// 用户名
	private String yonghumingStr = null;
	// 密码
	private String mimaStr = null;
	// 关闭
	private ImageView _close = null;
	// login为1的时候是从欢迎界面跳过来的
	private int login = 0;
	// 用于数据的缓存
	private ACache mCache = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		setContentView(R.layout.activity_login);
		mCache = ACache.get(LoginActivity.this);
		init();
	}

	// 选取任务(队员)
	private HuoDongXiangQingItem huoDongXiangQing = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingDuiyuanDao = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.denglu:// 登录
			if (NetUtil.isNetworkAvailable(LoginActivity.this)) {
				yonghumingStr = yonghuming.getText().toString();
				mimaStr = mima.getText().toString();
				if (TextUtils.isEmpty(yonghumingStr)) {// 将参数yonghumingStr与mimaStr传入接口
					showToast("请输入用户名");
				} else if (TextUtils.isEmpty(mimaStr)) {
					showToast("请输入密码");
				} else {
					showdialogtext("登录中...");
					if (!TextUtils.isEmpty(App.getInstance().getPhonenum())
							&& !TextUtils.equals(yonghumingStr, App.getInstance().getPhonenum())) {
						Bitmap value = null;
						mCache.put("head_bitmap", value);
						mCache.put("blur_bitmap", value);
						App.getInstance().ClearAll();
						huoDongXiangQing = new HuoDongXiangQingItem();
						huoDongXiangQingDuiyuanDao = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQing,
								LoginActivity.this);
						huoDongXiangQingLeader = new HuoDongXiangQingLeader();
						huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader,
								LoginActivity.this);
						if (huoDongXiangQingDuiyuanDao != null) {
							huoDongXiangQingDuiyuanDao.deleteAll();
						}
						if (huoDongXiangQingLeaderDao != null) {
							huoDongXiangQingLeaderDao.deleteAll();
						}
					}
					sendAsyn();
				}
			} else {
				showToast(CommonUtility.ISNETCONNECTED);
			}
			break;
		case R.id.zhuce:// 注册
			if (NetUtil.isNetworkAvailable(LoginActivity.this)) {
				bundle.putInt("zhuceorwangji", CommonUtility.ZHUCE);
				openActivity(RegisterOrChangPwdActivity.class, bundle);
			} else {
				showToast(CommonUtility.ISNETCONNECTED);
			}
			break;
		case R.id.wangji:// 忘记密码
			if (NetUtil.isNetworkAvailable(LoginActivity.this)) {
				bundle.putInt("zhuceorwangji", CommonUtility.WANGJIMIMA);
				openActivity(RegisterOrChangPwdActivity.class, bundle);
			} else {
				showToast(CommonUtility.ISNETCONNECTED);
			}
			break;
		case R.id.yaoyaole_close:// 关闭页面
			if (login == 1) {
				openActivity(MainActivity.class);
			}
			finish();

			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (login == 1) {
			openActivity(MainActivity.class);
		}
	}

	@Override
	protected void onInitData() {
		login = getIntent().getIntExtra("login", 0);
		yonghuming = (CleareditTextView) findViewById(R.id.edityonghuming);
		mima = (CleareditTextView) findViewById(R.id.editmima);
		denglu = (TextView) findViewById(R.id.denglu);
		zhuce = (TextView) findViewById(R.id.zhuce);
		wangji = (TextView) findViewById(R.id.wangji);
		_close = (ImageView) findViewById(R.id.yaoyaole_close);
	}

	@Override
	protected void onResload() {
		if (!TextUtils.isEmpty(App.getInstance().getPhonenum())) {
			yonghuming.setText(App.getInstance().getPhonenum());
		}
	}

	@Override
	protected void setMyViewClick() {
		denglu.setOnClickListener(this);
		zhuce.setOnClickListener(this);
		wangji.setOnClickListener(this);
		_close.setOnClickListener(this);
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
		UserLoginRequest request = new UserLoginRequest();
		UserLoginRequestParameter parameter = new UserLoginRequestParameter();
		parameter.setPhoneNumber(yonghumingStr);
		parameter.setPassword(mimaStr);
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
					UserLoginResponse response = new UserLoginResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UserLoginResponsePayload payload = (UserLoginResponsePayload) response.getPayload();
					msgString = payload.getMsg();
					State = payload.getLoginState();
					App.getInstance().setAut(payload.getAut());
					App.getInstance().setPhonenum(payload.getPhonenum());
					App.getInstance().setRole(payload.getRole());
					App.getInstance().setUserid(payload.getUserid());
					App.getInstance().setHeadurl(payload.getHead());
					App.getInstance().setNickname(payload.getNickname());
					new Thread(downloadRun).start();
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

	public void sendChannelIdAsyn() {
		thread = new Thread() {
			public void run() {
				getChannelIdAction();
			}
		};
		thread.start();
	}

	private int ChannelIdState = 2;
	private String ChannelIdString = null;

	public void getChannelIdAction() {
		SetUserChannelRequest request = new SetUserChannelRequest();
		SetUserChannelRequestParameter parameter = new SetUserChannelRequestParameter();
		parameter.setCh_id(App.getInstance().getChannelId());
		parameter.setD_type(0);
		request.setParameter(parameter);
		request.setAuthenticationToken(App.getInstance().getAut());
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
				ChannelIdString = responseObject.getString("msg");
				if (ret == 0) {
					SetUserChannelResponse response = new SetUserChannelResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					SetUserChannelResponsePayload payload = (SetUserChannelResponsePayload) response.getPayload();
					ChannelIdState = payload.getState();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.ChANNELRSERVERERROR);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} else {
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	public void sendGetUserAsyn() {
		thread = new Thread() {
			public void run() {
				GetUserAction();
			}
		};
		thread.start();
	}

	public void GetUserAction() {
		GetUserInfoRequest request = new GetUserInfoRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetUserInfoRequestParameter parameter = new GetUserInfoRequestParameter();
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
					GetUserInfoResponse response = new GetUserInfoResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetUserInfoResponsePayload payload = (GetUserInfoResponsePayload) response.getPayload();
					App.getInstance().setUserid(payload.getUserid());
					App.getInstance().setRealname(payload.getRealname());
					App.getInstance().setHeadurl(payload.getHead());
					App.getInstance().setNickname(payload.getNickname());
					App.getInstance().setEmail(payload.getEmail());
					App.getInstance().setQq(payload.getQq());
					App.getInstance().setMicroblog(payload.getMicroblog());
					App.getInstance().setSex(payload.getSex());
					App.getInstance().setBrith(payload.getBrith());
					App.getInstance().setPrivince(payload.getPrivince() != null ? payload.getPrivince() : 0);
					App.getInstance().setCity(payload.getCity() != null ? payload.getCity() : 0);
					App.getInstance().setCounty(payload.getCounty() != null ? payload.getCounty() : 0);
					App.getInstance().setZoneString(payload.getZoneString());
					App.getInstance().setAddress(payload.getAddress());
					App.getInstance().setEmergency_name(payload.getEmergency_name());
					App.getInstance().setEmergency_phone(payload.getEmergency_phone());
					App.getInstance().setPhonenum(payload.getPhonenum());
					App.getInstance().setYb(payload.getZipcode());
					App.getInstance().setGxqm(payload.getSignature());
					App.getInstance().setSfz(payload.getId_number());
					App.getInstance().setHz(payload.getPassport());
					App.getInstance().setJsz(payload.getDrivinglicense());
					App.getInstance().setBloodtype(payload.getBloodtype());
					App.getInstance().setRegisttime(payload.getRegisttime());
					App.getInstance().setUlevel(payload.getUlevel() != null ? payload.getUlevel() : 0);
					App.getInstance().setStar(payload.getStar() != null ? payload.getStar() : 0);
					App.getInstance().setIs_leader(payload.getIs_leader() != null ? payload.getIs_leader() : 0);
					App.getInstance().setVerify(payload.getVerify() != null ? payload.getVerify() : 0);
					App.getInstance().setMyCollectedAct(payload.getMyCollectedAct());
					App.getInstance().setMyCollectedTvl(payload.getMyCollectedTvl());
					App.getInstance().setMyWritedTvl(payload.getMyWritedTvl());
					App.getInstance().setFraction(payload.getFraction() != null ? payload.getFraction() : 0);
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK4);
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

	private Bitmap bitmap = null;
	/**
	 * 下载线程
	 */
	Runnable downloadRun = new Runnable() {

		@Override
		public void run() {
			try {
				bitmap = ImageControl.getHttpBitmap(App.getInstance().getHeadurl());
				handler.sendEmptyMessage(1);
			} catch (Exception e) {
				handler.sendEmptyMessage(2);
			}
		}
	};

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				mCache.put("head_bitmap", bitmap);
				handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
				break;
			case 2:
				Bitmap value = null;
				mCache.put("head_bitmap", value);
				handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
				break;

			default:
				break;
			}
		}
	};

	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (State == 1) {
					sendGetUserAsyn();
				} else {
					if (isshowdialog()) {
						closedialog();
					}
					showToast(msgString);
				}
				break;
			case CommonUtility.SERVEROK2:
				showToast(msgString);
				break;
			case CommonUtility.SERVEROK3:
				if (ChannelIdState == 0) {
					logzt.d("绑定成功");
				}
				showToast(msgString);
				if (isshowdialog()) {
					closedialog();
				}
				if (login == 1) {
					openActivity(MainActivity.class);
				}
				App.getInstance().setIsChannelId(true);
				finish();
				break;
			case CommonUtility.SERVEROK4:
				if (!TextUtils.isEmpty(App.getInstance().getChannelId())) {
					if (App.getInstance().getIsChannelId()) {
						showToast(msgString);
						if (isshowdialog()) {
							closedialog();
						}
						if (login == 1) {
							openActivity(MainActivity.class);
						}
						finish();
					} else {
						sendChannelIdAsyn();
					}
				}
				break;
			case CommonUtility.ChANNELRSERVERERROR:
				showToast("登录失败，请重新登录");
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
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
