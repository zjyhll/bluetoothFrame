package com.hwacreate.outdoor.mainFragment.huodongxiangqing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.nereo.multi_image_selector.MultiImageSelectorActivity_Headportrait;

import org.afinal.simplecache.ACache;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.areacity.AreaInfoActivity;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.login.RegisterOrChangPwdActivity;
import com.hwacreate.outdoor.uploadwithprogress.http.HttpMultipartPost;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.PickerView;
import com.hwacreate.outdoor.view.PickerView.onSelectListener;
import com.keyhua.outdoor.protocol.GetUserInfoByIDAction.GetUserInfoByIDRequest;
import com.keyhua.outdoor.protocol.GetUserInfoByIDAction.GetUserInfoByIDRequestParameter;
import com.keyhua.outdoor.protocol.GetUserInfoByIDAction.GetUserInfoByIDResponse;
import com.keyhua.outdoor.protocol.GetUserInfoByIDAction.GetUserInfoByIDResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

/**
 * @author 曾金叶 @2015-8-28
 * @下午2:06:36
 * @个人信息
 */
public class DuiyuanxiangqingActivity extends BaseActivity {
	private View parentView = null;
	// 头像
	private CircleImageView iv_icon = null;

	private TextView geren_name = null;
	private TextView geren_phone = null;
	private TextView geren_email = null;
	private TextView cet_name = null;// 真实姓名
	private TextView cet_sfz = null;// 身份证号码
	private TextView cet_hz = null;// 护照
	private TextView cet_jsz = null;// 驾驶证
	private TextView cet_jjlxm = null;// 紧急联系人姓名
	private TextView cet_jjldh = null;// 紧急联系人电话
	private TextView cet_jtzz = null;// 家庭住址
	private TextView cet_yb = null;// 邮编
	private TextView cet_gh = null;// 固话
	private TextView cet_xx = null;// 血型
	private TextView cet_ggqm = null;// 个性签名
	private TextView cet_qq = null;// qq号码
	private TextView cet_wx = null;// 微信号码
	private TextView cet_yhdj = null;// 用户等级
	private RatingBar cet_yhxj = null;// 用户星级
	private TextView geren_diqu = null;
	private TextView geren_sex = null;
	private TextView geren_shengri = null;
	// 显示的数据
	private String xingbie = null;
	private String nian = null;
	private String yue = null;
	private String ri = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_personal_information_mingdan, null);
		setContentView(parentView);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			App.getInstance().setSelectPath(null);
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		initDate();
		iv_icon = (CircleImageView) findViewById(R.id.iv_icon);
		geren_name = (TextView) findViewById(R.id.cet_yhm);
		geren_phone = (TextView) findViewById(R.id.cet_phonenum);
		geren_email = (TextView) findViewById(R.id.cet_mail);
		cet_name = (TextView) findViewById(R.id.cet_name);
		cet_sfz = (TextView) findViewById(R.id.cet_sfz);
		cet_hz = (TextView) findViewById(R.id.cet_hz);
		cet_jsz = (TextView) findViewById(R.id.cet_jsz);
		cet_jjlxm = (TextView) findViewById(R.id.cet_jjlxm);
		cet_jjldh = (TextView) findViewById(R.id.cet_jjldh);
		cet_jtzz = (TextView) findViewById(R.id.cet_jtzz);
		cet_yb = (TextView) findViewById(R.id.cet_yb);
		cet_gh = (TextView) findViewById(R.id.cet_gh);
		cet_xx = (TextView) findViewById(R.id.cet_xx);
		cet_ggqm = (TextView) findViewById(R.id.cet_ggqm);
		cet_qq = (TextView) findViewById(R.id.cet_qq);
		cet_wx = (TextView) findViewById(R.id.cet_wx);
		cet_yhdj = (TextView) findViewById(R.id.cet_yhdj);
		cet_yhxj = (RatingBar) findViewById(R.id.cet_yhxj);
		geren_diqu = (TextView) findViewById(R.id.cet_diqu);
		geren_sex = (TextView) findViewById(R.id.cet_sex);
		geren_shengri = (TextView) findViewById(R.id.cet_birth);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("队员详细信息");
		top_tv_right.setVisibility(View.GONE);
		sendAsyn();
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
	}

	// 初始化数据
	private void initDate() {
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

	private String realname = null;// 真实姓名
	private String head = null;// 头像url
	private String nickname = null;// 用户名
	private String phoneNum = null;// 电话号码
	private String email = null;// 邮箱
	private String qq = null;// qq
	private String microblog = null;// 微信
	private String sex = null;// 性别
	private String brith = null;// 生日
	private int privince = 0;// 省
	private int city = 0;// 市
	private int county = 0;// 区县
	private String zoneString = null;// 地区
	private String address = null;// 详细地址
	private String bloodtype = null;// 血型
	private String emergency_name = null;// 应急联系人姓名
	private String emergency_phone = null;// 应急联系人电话
	private String sfz = null;// 身份证
	private String hz = null;// 护照
	private String jsz = null;// 驾驶证
	private String yb = null;// 邮编
	private String gh = null;// 固话 TODO
	private String gxqm = null;// 个性签名 TODO
	private int ulevel = 0;// 用户等级
	private int star = 0;// 星级
	private int fraction = 0;// 星级

	// 服务器返回提示信息
	private String msgString = null;
	private int State = 0;

	public void Action() {
		GetUserInfoByIDRequest request = new GetUserInfoByIDRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetUserInfoByIDRequestParameter parameter = new GetUserInfoByIDRequestParameter();
		parameter.setUid(getIntent().getStringExtra("uid"));
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
					GetUserInfoByIDResponse response = new GetUserInfoByIDResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetUserInfoByIDResponsePayload payload = (GetUserInfoByIDResponsePayload) response.getPayload();
					App.getInstance().setUserid(payload.getUserid());
					realname = payload.getRealname();
					head = payload.getHead();
					nickname = payload.getNickname();
					email = payload.getEmail();
					qq = payload.getQq();
					microblog = payload.getMicroblog();
					sex = payload.getSex();
					zoneString = payload.getZoneString();
					brith = payload.getBrith();
					privince = payload.getPrivince() != null ? payload.getPrivince() : 0;
					city = payload.getCity() != null ? payload.getCity() : 0;
					county = payload.getCounty() != null ? payload.getCounty() : 0;
					address = payload.getAddress();
					emergency_name = payload.getEmergency_name();
					emergency_phone = payload.getEmergency_phone();
					phoneNum = payload.getPhonenum();
					sfz = payload.getId_number();
					hz = payload.getPassport();
					fraction = payload.getFraction();
					jsz = payload.getDrivinglicense();
					bloodtype = payload.getBloodtype();
					yb = payload.getDrivinglicense();// TODO
					gxqm = payload.getSignature();
					ulevel = payload.getUlevel() != null ? payload.getUlevel() : 0;
					star = payload.getStar() != null ? payload.getStar() : 0;
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
					if (!TextUtils.isEmpty(head)) {
						mImageLoader.displayImage(head, iv_icon);
					}
					geren_name.setText(nickname);
					geren_phone.setText(phoneNum);
					geren_email.setText(email);
					geren_sex.setText(sex);
					geren_shengri.setText(brith);
					cet_name.setText(realname);
					cet_qq.setText(qq);
					cet_wx.setText(microblog);
					cet_jjlxm.setText(emergency_name);
					cet_jjldh.setText(emergency_phone);
					cet_xx.setText(bloodtype);
					cet_sfz.setText(sfz);
					cet_hz.setText(hz);
					cet_jsz.setText(jsz);
					cet_yb.setText(yb);
					cet_gh.setText(gh);
					cet_ggqm.setText(gxqm);
					cet_jtzz.setText(address);
					if (fraction >= 0 && fraction < 20) {
						cet_yhdj.setText("新手");
					} else if (fraction >= 20 && fraction < 100) {
						cet_yhdj.setText("初级队员");
					} else if (fraction >= 100 && fraction < 200) {
						cet_yhdj.setText("中级队员");
					} else if (fraction >= 200 && fraction < 500) {
						cet_yhdj.setText("高级队员");
					} else if (fraction >= 500) {
						cet_yhdj.setText("大师");
					}
					cet_yhxj.setRating(star);
					if (!TextUtils.isEmpty(zoneString)) {
						geren_diqu.setText(zoneString);
					}
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
