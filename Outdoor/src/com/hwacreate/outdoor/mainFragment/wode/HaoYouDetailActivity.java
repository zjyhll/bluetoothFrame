package com.hwacreate.outdoor.mainFragment.wode;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.view.CustomDialog;
import com.keyhua.outdoor.protocol.CancelInterestUserAction.CancelInterestUserRequest;
import com.keyhua.outdoor.protocol.CancelInterestUserAction.CancelInterestUserRequestParameter;
import com.keyhua.outdoor.protocol.CancelInterestUserAction.CancelInterestUserResponse;
import com.keyhua.outdoor.protocol.CancelInterestUserAction.CancelInterestUserResponsePayload;
import com.keyhua.outdoor.protocol.GetFriendsInfoByIDAction.GetFriendsInfoByIDRequest;
import com.keyhua.outdoor.protocol.GetFriendsInfoByIDAction.GetFriendsInfoByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetFriendsInfoByIDAction.GetFriendsInfoByIDResponse;
import com.keyhua.outdoor.protocol.GetFriendsInfoByIDAction.GetFriendsInfoByIDResponsePayload;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserRequest;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserRequestParameter;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserResponse;
import com.keyhua.outdoor.protocol.InterestUserAction.InterestUserResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * @author LaLa 好友详情
 *
 */
public class HaoYouDetailActivity extends BaseActivity {
	private String Userid = null;// 好友id
	private ImageView haoyou_icon = null;
	private TextView haoyou_name = null;
	private ImageView haoyou_sex = null;
	private ImageView haoyou_lingdui = null;
	private TextView haoyou_qianming = null;
	private TextView haoyou_fensinum = null;
	private TextView haoyou_guanzhunum = null;
	private TextView haoyou_diqu = null;
	private TextView haoyou_dengji = null;
	private TextView haoyou_julebu = null;
	private TextView haoyou_huodong = null;
	private TextView haoyou_youji = null;
	private LinearLayout haoyou_julebu_view = null;
	private LinearLayout haoyou_huodong_view = null;
	private LinearLayout haoyou_youji_view = null;
	private TextView haoyou_guanzhu = null;
	private String icon = null;
	private String name = null;
	private String sex = null;
	private String qianming = null;
	private String diqu = null;
	private int fensinum = 0;
	private int guanzhunum = 0;
	private int julebu = 0;
	private int huodong = 0;
	private int youji = 0;
	private int is_attention = 0;// 是否关注
	private int is_leader = 0;// 是否是领队
	private int fraction = 0;// 积分
	private int star = 5;// 星级
	private RatingBar haoyou_ratingbar = null;// 星星等级

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_haoyoudetailed);
		init();
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.haoyou_julebu_view:
			bundle.putString("Userid", Userid);
			bundle.putInt("UserIndex", 1);
			openActivity(HaoYouDetailListActivity.class, bundle);
			break;
		case R.id.haoyou_huodong_view:
			bundle.putString("Userid", Userid);
			bundle.putInt("UserIndex", 2);
			openActivity(HaoYouDetailListActivity.class, bundle);
			break;
		case R.id.haoyou_youji_view:
			bundle.putString("Userid", Userid);
			bundle.putInt("UserIndex", 3);
			openActivity(HaoYouDetailListActivity.class, bundle);
			break;
		case R.id.haoyou_guanzhu:
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (is_attention == 0) {
					sendAsyn2();
				} else if (is_attention == 1) {
					showAlertDialog("是否取消关注该好友?                   ");
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		Userid = getIntent().getExtras().getString("Userid");
		haoyou_icon = (ImageView) findViewById(R.id.haoyou_icon);
		haoyou_name = (TextView) findViewById(R.id.haoyou_name);
		haoyou_sex = (ImageView) findViewById(R.id.haoyou_sex);
		haoyou_lingdui = (ImageView) findViewById(R.id.haoyou_lingdui);
		haoyou_qianming = (TextView) findViewById(R.id.haoyou_qianming);
		haoyou_fensinum = (TextView) findViewById(R.id.haoyou_fensinum);
		haoyou_guanzhunum = (TextView) findViewById(R.id.haoyou_guanzhunum);
		haoyou_diqu = (TextView) findViewById(R.id.haoyou_diqu);
		haoyou_dengji = (TextView) findViewById(R.id.haoyou_dengji);
		haoyou_julebu = (TextView) findViewById(R.id.haoyou_julebu);
		haoyou_huodong = (TextView) findViewById(R.id.haoyou_huodong);
		haoyou_youji = (TextView) findViewById(R.id.haoyou_youji);
		haoyou_julebu_view = (LinearLayout) findViewById(R.id.haoyou_julebu_view);
		haoyou_huodong_view = (LinearLayout) findViewById(R.id.haoyou_huodong_view);
		haoyou_youji_view = (LinearLayout) findViewById(R.id.haoyou_youji_view);
		haoyou_guanzhu = (TextView) findViewById(R.id.haoyou_guanzhu);
		haoyou_ratingbar = (RatingBar) findViewById(R.id.haoyou_ratingbar);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("详情资料");
		if (TextUtils.equals(Userid, App.getInstance().getUserid())) {
			haoyou_guanzhu.setVisibility(View.GONE);
		}
		sendAsyn1();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		haoyou_guanzhu.setOnClickListener(this);
		haoyou_julebu_view.setOnClickListener(this);
		haoyou_huodong_view.setOnClickListener(this);
		haoyou_youji_view.setOnClickListener(this);
	}

	/** Dialog */
	public void showAlertDialog(String title) {
		CustomDialog.Builder builder = new CustomDialog.Builder(HaoYouDetailActivity.this);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				sendAsyn3();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private Thread thread = null;

	// 获取好友详细信息
	public void sendAsyn1() {
		thread = new Thread() {
			public void run() {
				Action1();
			}
		};
		thread.start();
	}

	public void Action1() {
		GetFriendsInfoByIDRequest request = new GetFriendsInfoByIDRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetFriendsInfoByIDRequestParameter parameter = new GetFriendsInfoByIDRequestParameter();
		request.setParameter(parameter);
		parameter.setId(Userid);
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
					GetFriendsInfoByIDResponse response = new GetFriendsInfoByIDResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetFriendsInfoByIDResponsePayload payload = (GetFriendsInfoByIDResponsePayload) response
							.getPayload();
					icon = payload.getHead();
					name = payload.getNickname();
					sex = payload.getSex();
					qianming = payload.getSignature();
					diqu = payload.getCity();
					fensinum = payload.getFasnCount();
					guanzhunum = payload.getIntersCount();
					julebu = payload.getJoinClubCount();
					huodong = payload.getJoinActCount();
					youji = payload.getTvlCount();
					is_attention = payload.getIs_attention();
					is_leader = payload.getIs_leader();
					fraction = payload.getFraction() != null ? payload.getFraction() : 0;
					star = payload.getStar() != null ? payload.getStar() : 5;
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

	// 关注好友
	public void sendAsyn2() {
		thread = new Thread() {
			public void run() {
				Action2();
			}
		};
		thread.start();
	}

	public void Action2() {
		InterestUserRequest request = new InterestUserRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		InterestUserRequestParameter parameter = new InterestUserRequestParameter();
		request.setParameter(parameter);
		parameter.setU_id(Userid);
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
					InterestUserResponse response = new InterestUserResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					InterestUserResponsePayload payload = (InterestUserResponsePayload) response.getPayload();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);
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

	// 取消关注好友
	public void sendAsyn3() {
		thread = new Thread() {
			public void run() {
				Action3();
			}
		};
		thread.start();
	}

	public void Action3() {
		CancelInterestUserRequest request = new CancelInterestUserRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		CancelInterestUserRequestParameter parameter = new CancelInterestUserRequestParameter();
		request.setParameter(parameter);
		parameter.setU_id(Userid);
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
					CancelInterestUserResponse response = new CancelInterestUserResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					CancelInterestUserResponsePayload payload = (CancelInterestUserResponsePayload) response
							.getPayload();
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
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				mImageLoader.displayImage(icon, haoyou_icon, options);
				haoyou_name.setText(name);
				haoyou_qianming.setText(qianming);
				haoyou_diqu.setText(diqu);
				haoyou_fensinum.setText(fensinum + "");
				haoyou_guanzhunum.setText(guanzhunum + "");
				haoyou_julebu.setText(julebu + "");
				haoyou_huodong.setText(huodong + "");
				haoyou_youji.setText(youji + "");
				if (TextUtils.equals(sex, "男")) {
					haoyou_sex.setBackgroundResource(R.drawable.haoyou_nan);
				} else if (TextUtils.equals(sex, "女")) {
					haoyou_sex.setBackgroundResource(R.drawable.haoyou_nv);
				}
				if (is_leader == 0) {
					haoyou_lingdui.setVisibility(View.GONE);
				} else if (is_leader == 1) {
					haoyou_lingdui.setVisibility(View.VISIBLE);
					haoyou_lingdui.setBackgroundResource(R.drawable.haoyou_lingdui);
				}
				haoyou_ratingbar.setRating(star);
				if (fraction >= 0 && fraction < 20) {
					haoyou_dengji.setText("新手");
				} else if (fraction >= 20 && fraction < 100) {
					haoyou_dengji.setText("初级队员");
				} else if (fraction >= 100 && fraction < 200) {
					haoyou_dengji.setText("中级队员");
				} else if (fraction >= 200 && fraction < 500) {
					haoyou_dengji.setText("高级队员");
				} else if (fraction >= 500) {
					haoyou_dengji.setText("大师");
				}
				if (is_attention == 0) {
					haoyou_guanzhu.setText("关注该好友");
				} else if (is_attention == 1) {
					haoyou_guanzhu.setText("取消关注");
				}
				break;
			case CommonUtility.SERVEROK2:
				showToast("关注好友成功");
				sendAsyn1();
				break;
			case CommonUtility.SERVEROK3:
				finish();
				break;
			case CommonUtility.SERVEROK4:
				break;
			case CommonUtility.SERVEROK5:
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
