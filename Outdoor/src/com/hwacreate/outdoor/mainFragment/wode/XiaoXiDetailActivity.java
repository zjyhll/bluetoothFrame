package com.hwacreate.outdoor.mainFragment.wode;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.keyhua.outdoor.protocol.GetUserMessageByIdAction.GetUserMessageByIdRequest;
import com.keyhua.outdoor.protocol.GetUserMessageByIdAction.GetUserMessageByIdRequestParameter;
import com.keyhua.outdoor.protocol.GetUserMessageByIdAction.GetUserMessageByIdResponse;
import com.keyhua.outdoor.protocol.GetUserMessageByIdAction.GetUserMessageByIdResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

/**
 * @author LaLa 消息详情
 * 
 */
public class XiaoXiDetailActivity extends BaseActivity {
	private TextView xiaoxi_name = null;
	private TextView xiaoxi_time = null;
	private TextView xiaoxi_content = null;
	private int Msgid = 0;
	private String title = null;
	private String time = null;
	private String content = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_xiaoxi);
		init();
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		Msgid = getIntent().getExtras().getInt("Msgid", 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResload() {
		top_tv_title.setText(getString(R.string.wode_xiaoxidetail));
		xiaoxi_name = (TextView) findViewById(R.id.xiaoxi_name);
		xiaoxi_time = (TextView) findViewById(R.id.xiaoxi_time);
		xiaoxi_content = (TextView) findViewById(R.id.xiaoxi_content);
		if (Msgid != 0) {
			sendAsyn();
		}
	}

	// 刷新end------------------------------------------------------------------
	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
	}

	private Thread thread = null;

	// 获得用户删除消息
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() {
		GetUserMessageByIdRequest request = new GetUserMessageByIdRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetUserMessageByIdRequestParameter parameter = new GetUserMessageByIdRequestParameter();
		parameter.setMsg_id(Msgid);
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
					GetUserMessageByIdResponse response = new GetUserMessageByIdResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetUserMessageByIdResponsePayload payload = (GetUserMessageByIdResponsePayload) response
							.getPayload();
					title = payload.getMsg_title();
					time = payload.getMsg_time();
					content = payload.getMsg_content();
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
				xiaoxi_name.setText(title);
				xiaoxi_time.setText(time);
				xiaoxi_content.setText(content);
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
