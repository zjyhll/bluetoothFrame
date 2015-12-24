package com.hwacreate.outdoor.mainFragment.wode;

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
import com.hwacreate.outdoor.leftFragment.myguardianFragment.ActivityManageActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.uploadwithprogress.http.HttpMultipartPost;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.IDCard;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.RegexChk;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.view.PickerView;
import com.hwacreate.outdoor.view.PickerView.onSelectListener;
import com.keyhua.outdoor.protocol.UpdateUserInfo.UpdateHWTXUserInfoRequest;
import com.keyhua.outdoor.protocol.UpdateUserInfo.UpdateHWTXUserInfoRequestParameter;
import com.keyhua.outdoor.protocol.UpdateUserInfo.UpdateHWTXUserInfoResponse;
import com.keyhua.outdoor.protocol.UpdateUserInfo.UpdateHWTXUserInfoResponsePayload;
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
public class GenRenZiLiaoActivity extends BaseActivity {
	private View parentView = null;
	// 头像
	private CircleImageView iv_icon = null;

	private CleareditTextView geren_name = null;
	private TextView geren_phone = null;
	private CleareditTextView geren_email = null;
	private CleareditTextView cet_name = null;// 真实姓名
	private CleareditTextView cet_sfz = null;// 身份证号码
	private CleareditTextView cet_hz = null;// 护照
	private CleareditTextView cet_jsz = null;// 驾驶证
	private CleareditTextView cet_jjlxm = null;// 紧急联系人姓名
	private CleareditTextView cet_jjldh = null;// 紧急联系人电话
	private CleareditTextView cet_jtzz = null;// 家庭住址
	private CleareditTextView cet_yb = null;// 邮编
	private CleareditTextView cet_gh = null;// 固话
	private TextView cet_xx = null;// 血型
	private CleareditTextView cet_ggqm = null;// 个性签名
	private CleareditTextView cet_qq = null;// qq号码
	private CleareditTextView cet_wx = null;// 微信号码
	private TextView cet_yhdj = null;// 用户等级
	private RatingBar cet_yhxj = null;// 用户星级
	private TextView geren_diqu = null;
	private TextView geren_sex = null;
	private TextView geren_shengri = null;
	// pop选择栏选择
	private PopupWindow popUser = null;
	private PickerView pickerviewleft = null;
	private TextView pickerviewtext = null;
	private PickerView pickerviewright = null;
	private PickerView pickerviewright_right = null;
	private TextView geren_qu = null;
	private TextView geren_wan = null;
	private LinearLayout geren_pop_view = null;
	private RelativeLayout geren_pop_image = null;
	// 存放的数据
	private List<String> dataxingbie = null;
	private List<String> dataXueXing = null;
	private List<String> datanian = null;
	private List<String> datayue = null;
	private List<String> datari = null;
	// 显示的数据
	private String xuexing = null;
	private String xingbie = null;
	private String nian = null;
	private String yue = null;
	private String ri = null;
	// 判断是哪一个pop
	private boolean isXuexing = false;
	private boolean isXingbie = false;
	private boolean isShengri = false;
	private boolean isRuiNian = false;
	// 上传
	private HttpMultipartPost post;
	private String result = null;
	// 用于数据的缓存
	private ACache mCache = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_personal_information, null);
		setContentView(parentView);
		mCache = ACache.get(GenRenZiLiaoActivity.this);
		init();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		App.getInstance().setSelectPath("");
		App.getInstance().setSelectPathTemp("");
		App.getInstance().setPrivincename("");
		App.getInstance().setCityname("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			App.getInstance().setSelectPath("");
			App.getInstance().setSelectPathTemp("");
			App.getInstance().setPrivincename("");
			App.getInstance().setCityname("");
			finish();
			break;
		case R.id.iv_icon:
			Intent intent = new Intent(GenRenZiLiaoActivity.this, MultiImageSelectorActivity_Headportrait.class);
			// 是否显示拍摄图片
			intent.putExtra(MultiImageSelectorActivity_Headportrait.EXTRA_SHOW_CAMERA, true);
			// 最大可选择图片数量
			intent.putExtra(MultiImageSelectorActivity_Headportrait.EXTRA_SELECT_COUNT, 1);
			// 选择模式
			intent.putExtra(MultiImageSelectorActivity_Headportrait.EXTRA_SELECT_MODE,
					MultiImageSelectorActivity_Headportrait.MODE_SINGLE);
			startActivityForResult(intent, REQUEST_IMAGE);
			break;
		case R.id.cet_diqu:
			App.getInstance().setAreaInfo(1);
			openActivity(AreaInfoActivity.class);
			break;
		case R.id.cet_sex:
			onPopdatabie();
			break;
		case R.id.cet_xx:
			onPopdataxue();
			break;
		case R.id.cet_birth:
			onPopdataShengri();
			break;
		case R.id.geren_qu:
			popUser.dismiss();
			break;
		case R.id.geren_wan:
			popUser.dismiss();
			if (isXingbie) {
				if (xingbie != null) {
					geren_sex.setText(xingbie);
				} else {
					geren_sex.setText(dataxingbie.get(0));
				}
			} else if (isXuexing) {
				if (xuexing != null) {
					cet_xx.setText(xuexing);
				} else {
					cet_xx.setText(dataXueXing.get(0));
				}
			} else if (isShengri) {
				if (nian != null && yue != null && ri != null) {
					geren_shengri.setText(nian + yue + ri);
				} else if (nian != null && yue != null && ri == null) {
					geren_shengri.setText(nian + yue + datari.get(0));
				} else if (nian != null && yue == null && ri == null) {
					geren_shengri.setText(nian + datayue.get(0) + datari.get(0));
				} else if (nian == null && yue != null && ri == null) {
					geren_shengri.setText(datanian.get(0) + yue + datari.get(0));
				} else if (nian == null && yue != null && ri != null) {
					geren_shengri.setText(datanian.get(0) + yue + ri);
				} else if (nian == null && yue == null && ri != null) {
					geren_shengri.setText(datanian.get(0) + datayue.get(0) + ri);
				} else if (nian != null && yue == null && ri != null) {
					geren_shengri.setText(nian + datayue.get(0) + ri);
				} else if (nian == null && yue == null && ri == null) {
					geren_shengri.setText(datanian.get(0) + datayue.get(0) + datari.get(0));
				}
			}
			break;
		case R.id.geren_pop_view:
			break;
		case R.id.geren_pop_image:
			popUser.dismiss();
			break;
		case R.id.top_tv_right:
			if (NetUtil.isNetworkAvailable(GenRenZiLiaoActivity.this)) {
				if (!TextUtils.isEmpty(result)) {
					try {
						JSONObject jsonObject = new JSONObject(result);
						head = jsonObject.getString("fullurl");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					head = App.getInstance().getHeadurl();
				}
				nickname = geren_name.getText().toString();
				email = geren_email.getText().toString();
				sex = geren_sex.getText().toString();
				brith = geren_shengri.getText().toString();
				// 真实姓名
				realname = cet_name.getText().toString();// TODO
				// qq
				qq = cet_qq.getText().toString();
				// 微信
				microblog = cet_wx.getText().toString();
				// 紧急联系人姓名
				emergency_name = cet_jjlxm.getText().toString();
				// 紧急联系人电话
				emergency_phone = cet_jjldh.getText().toString();
				// 血型
				bloodtype = cet_xx.getText().toString();
				// 身份证号码
				sfz = cet_sfz.getText().toString();
				// 护照
				hz = cet_hz.getText().toString();
				// 驾驶证
				jsz = cet_jsz.getText().toString();
				// 邮编
				yb = cet_yb.getText().toString();
				// 固话
				gh = cet_gh.getText().toString();
				// 个性签名
				gxqm = cet_ggqm.getText().toString();
				// 家庭住址
				address = cet_jtzz.getText().toString();
				if (TextUtils.isEmpty(realname)) {
					showToast("真实姓名为空");
				} else if (TextUtils.isEmpty(sex)) {
					showToast("请选择性别");
				} else if (TextUtils.isEmpty(sfz)) {
					showToast("身份证为空");
				} else if (!TextUtils.isEmpty(IDCard.IDCardValidate(sfz.contains("x") ? sfz.replace("x", "X") : sfz))) {
					showToast(IDCard.IDCardValidate(sfz.contains("x") ? sfz.replace("x", "X") : sfz));
				} else if (TextUtils.isEmpty(address)) {
					showToast("家庭住址为空");
				} else {
					sendAsyn();
				}
			} else {
				showToastNet();
			}
			break;
		default:
			break;
		}

	}

	/** 得到选择的照片 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				// ArrayList<String> mSelectPath =
				// data.getStringArrayListExtra(MultiImageSelectorActivity_Headportrait.EXTRA_RESULT);
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!TextUtils.isEmpty(App.getInstance().getZoneString())) {
			geren_diqu.setText(App.getInstance().getZoneString());
		}
		if (!TextUtils.isEmpty(App.getInstance().getPrivincename())
				&& !TextUtils.isEmpty(App.getInstance().getCityname())
				&& !TextUtils.isEmpty(App.getInstance().getCountyname())) {
			geren_diqu.setText(App.getInstance().getPrivincename() + "-" + App.getInstance().getCityname() + "-"
					+ App.getInstance().getCountyname());
		}
		if (!TextUtils.isEmpty(App.getInstance().getSelectPath())
				&& !TextUtils.isEmpty(App.getInstance().getSelectPathTemp())) {
			mImageLoader.displayImage("file://" + App.getInstance().getSelectPathTemp(), iv_icon, options);
			if (NetUtil.isNetworkAvailable(GenRenZiLiaoActivity.this)) {
				if (!TextUtils.equals(App.getInstance().getSelectPath(), App.getInstance().getSelectPathTemp())) {
					handlerlist.sendEmptyMessage(CommonUtility.UPLOADING);
				}
			} else {
				showToastNet();
			}
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		initDate();
		onPopwindownbie();
		iv_icon = (CircleImageView) findViewById(R.id.iv_icon);
		geren_name = (CleareditTextView) findViewById(R.id.cet_yhm);
		geren_phone = (TextView) findViewById(R.id.cet_phonenum);
		geren_email = (CleareditTextView) findViewById(R.id.cet_mail);
		cet_name = (CleareditTextView) findViewById(R.id.cet_name);
		cet_sfz = (CleareditTextView) findViewById(R.id.cet_sfz);
		cet_hz = (CleareditTextView) findViewById(R.id.cet_hz);
		cet_jsz = (CleareditTextView) findViewById(R.id.cet_jsz);
		cet_jjlxm = (CleareditTextView) findViewById(R.id.cet_jjlxm);
		cet_jjldh = (CleareditTextView) findViewById(R.id.cet_jjldh);
		cet_jtzz = (CleareditTextView) findViewById(R.id.cet_jtzz);
		cet_yb = (CleareditTextView) findViewById(R.id.cet_yb);
		cet_gh = (CleareditTextView) findViewById(R.id.cet_gh);
		cet_xx = (TextView) findViewById(R.id.cet_xx);
		cet_ggqm = (CleareditTextView) findViewById(R.id.cet_ggqm);
		cet_qq = (CleareditTextView) findViewById(R.id.cet_qq);
		cet_wx = (CleareditTextView) findViewById(R.id.cet_wx);
		cet_yhdj = (TextView) findViewById(R.id.cet_yhdj);
		cet_yhxj = (RatingBar) findViewById(R.id.cet_yhxj);
		geren_diqu = (TextView) findViewById(R.id.cet_diqu);
		geren_sex = (TextView) findViewById(R.id.cet_sex);
		geren_shengri = (TextView) findViewById(R.id.cet_birth);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("个人信息");
		top_tv_right.setText("保存");
		if (!TextUtils.isEmpty(App.getInstance().getHeadurl())) {
			mImageLoader.displayImage(App.getInstance().getHeadurl(), iv_icon, options);
		}
		if (!TextUtils.isEmpty(App.getInstance().getNickname())) {
			geren_name.setText(App.getInstance().getNickname());
		}
		if (!TextUtils.isEmpty(App.getInstance().getPhonenum())) {
			geren_phone.setText(App.getInstance().getPhonenum());
		}
		if (!TextUtils.isEmpty(App.getInstance().getEmail())) {
			geren_email.setText(App.getInstance().getEmail());
		}
		if (!TextUtils.isEmpty(App.getInstance().getSex())) {
			geren_sex.setText(App.getInstance().getSex());
		}
		if (!TextUtils.isEmpty(App.getInstance().getBrith())) {
			geren_shengri.setText(App.getInstance().getBrith());
		}
		if (App.getInstance().getPrivince() != 0) {
			privince = App.getInstance().getPrivince();
		}
		if (App.getInstance().getCity() != 0) {
			city = App.getInstance().getCity();
		}
		if (App.getInstance().getCounty() != 0) {
			county = App.getInstance().getCounty();
		}
		if (!TextUtils.isEmpty(App.getInstance().getRealname())) {
			cet_name.setText(App.getInstance().getRealname());
		}
		if (!TextUtils.isEmpty(App.getInstance().getQq())) {
			cet_qq.setText(App.getInstance().getQq());
		}
		if (!TextUtils.isEmpty(App.getInstance().getMicroblog())) {
			cet_wx.setText(App.getInstance().getMicroblog());
		}
		if (!TextUtils.isEmpty(App.getInstance().getEmergency_name())) {
			cet_jjlxm.setText(App.getInstance().getEmergency_name());
		}
		if (!TextUtils.isEmpty(App.getInstance().getEmergency_phone())) {
			cet_jjldh.setText(App.getInstance().getEmergency_phone());
		}
		if (!TextUtils.isEmpty(App.getInstance().getBloodtype())) {
			cet_xx.setText(App.getInstance().getBloodtype());
		}
		if (!TextUtils.isEmpty(App.getInstance().getSfz())) {
			cet_sfz.setText(App.getInstance().getSfz());
		}
		if (!TextUtils.isEmpty(App.getInstance().getHz())) {
			cet_hz.setText(App.getInstance().getHz());
		}
		if (!TextUtils.isEmpty(App.getInstance().getJsz())) {
			cet_jsz.setText(App.getInstance().getJsz());
		}
		if (!TextUtils.isEmpty(App.getInstance().getYb())) {
			cet_yb.setText(App.getInstance().getYb());
		}
		if (!TextUtils.isEmpty(App.getInstance().getGh())) {
			cet_gh.setText(App.getInstance().getGh());
		}
		if (!TextUtils.isEmpty(App.getInstance().getGxqm())) {
			cet_ggqm.setText(App.getInstance().getGxqm());
		}
		if (!TextUtils.isEmpty(App.getInstance().getAddress())) {
			cet_jtzz.setText(App.getInstance().getAddress());
		}
		int fraction = App.getInstance().getFraction();
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
		cet_yhxj.setRating(App.getInstance().getStar());
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		cet_xx.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		iv_icon.setOnClickListener(this);
		geren_diqu.setOnClickListener(this);
		geren_sex.setOnClickListener(this);
		geren_shengri.setOnClickListener(this);
		geren_qu.setOnClickListener(this);
		geren_wan.setOnClickListener(this);
		geren_pop_view.setOnClickListener(this);
		geren_pop_image.setOnClickListener(this);
	}

	// 初始化数据
	private void initDate() {
		dataxingbie = new ArrayList<String>();
		datanian = new ArrayList<String>();
		datayue = new ArrayList<String>();
		datari = new ArrayList<String>();
		dataXueXing = new ArrayList<String>();
		dataxingbie.add("男");
		dataxingbie.add("女");
		dataXueXing.add("A");
		dataXueXing.add("B");
		dataXueXing.add("O");
		dataXueXing.add("AB");
		for (int i = 2015; i < 2050; i++) {
			datanian.add(i + "年");
		}
		for (int i = 1949; i < 2015; i++) {
			datanian.add(i + "年");
		}
		for (int i = 1; i < 13; i++) {
			datayue.add(i + "月");
		}
		for (int i = 1; i < 32; i++) {
			datari.add(i + "日");
		}
	}

	private void onPopwindownbie() {
		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件
		View view = inflater.inflate(R.layout.popwindow_usershezhi, null);
		// 初始化pop中的组件
		pickerviewleft = (PickerView) view.findViewById(R.id.pickerviewleft);
		pickerviewtext = (TextView) view.findViewById(R.id.pickerviewtext);
		pickerviewright = (PickerView) view.findViewById(R.id.pickerviewright);
		pickerviewright_right = (PickerView) view.findViewById(R.id.pickerviewright_right);
		geren_qu = (TextView) view.findViewById(R.id.geren_qu);
		geren_wan = (TextView) view.findViewById(R.id.geren_wan);
		geren_pop_view = (LinearLayout) view.findViewById(R.id.geren_pop_view);
		geren_pop_image = (RelativeLayout) view.findViewById(R.id.geren_pop_image);
		// 创建PopupWindow对象
		popUser = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popUser.setBackgroundDrawable(new BitmapDrawable());
	}

	// 性别pop
	private void onPopdatabie() {
		isXingbie = true;
		isShengri = false;
		isXuexing = false;
		popUser.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		pickerviewtext.setVisibility(View.GONE);
		pickerviewright.setVisibility(View.GONE);
		pickerviewright_right.setVisibility(View.GONE);
		pickerviewleft.setData(dataxingbie);
		pickerviewleft.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				xingbie = text;
			}
		});
	}

	// 血型pop
	private void onPopdataxue() {
		isXuexing = true;
		isXingbie = false;
		isShengri = false;
		popUser.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		pickerviewtext.setVisibility(View.GONE);
		pickerviewright.setVisibility(View.GONE);
		pickerviewright_right.setVisibility(View.GONE);
		pickerviewleft.setData(dataXueXing);
		pickerviewleft.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				xuexing = text;
			}
		});
	}

	// 生日pop
	private void onPopdataShengri() {
		isXingbie = false;
		isShengri = true;
		isXuexing = false;
		popUser.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		pickerviewright.setVisibility(View.VISIBLE);
		pickerviewright_right.setVisibility(View.VISIBLE);
		pickerviewleft.setData(datanian);
		pickerviewright.setData(datayue);
		pickerviewright_right.setData(datari);
		pickerviewleft.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				nian = text;
				String string = nian.subSequence(0, 4).toString();
				int year = Integer.valueOf(string).intValue();
				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
					// 是闰年
					isRuiNian = true;
					if (yue != null) {
						if (yue.equals("2月")) {
							datari.clear();
							for (int i = 1; i < 30; i++) {
								datari.add(i + "日");
							}
							pickerviewright_right.setData(datari);
						}
					}
				} else {
					// 不是闰年
					isRuiNian = false;
					if (yue != null) {
						if (yue.equals("2月")) {
							datari.clear();
							for (int i = 1; i < 29; i++) {
								datari.add(i + "日");
							}
							pickerviewright_right.setData(datari);
						}
					}
				}
			}
		});
		pickerviewright.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				yue = text;
				if (yue.equals("2月") && !isRuiNian) {
					datari.clear();
					for (int i = 1; i < 29; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("2月") && isRuiNian) {
					datari.clear();
					for (int i = 1; i < 30; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("4月") || yue.equals("6月") || yue.equals("9月") || yue.equals("11月")) {
					datari.clear();
					for (int i = 1; i < 31; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("1月") || yue.equals("3月") || yue.equals("5月") || yue.equals("7月")
						|| yue.equals("8月") || yue.equals("10月") || yue.equals("12月")) {
					datari.clear();
					for (int i = 1; i < 32; i++) {
						datari.add(i + "日");
					}
				}
				pickerviewright_right.setData(datari);
			}
		});
		pickerviewright_right.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				ri = text;
			}
		});
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
	private String email = null;// 邮箱
	private String qq = null;// qq
	private String microblog = null;// 微信
	private String sex = null;// 性别
	private String brith = null;// 生日
	private int privince = 0;// 省
	private int city = 0;// 市
	private int county = 0;// 区县
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

	// 服务器返回提示信息
	private String msgString = null;
	private int State = 0;

	public void Action() {
		UpdateHWTXUserInfoRequest request = new UpdateHWTXUserInfoRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		UpdateHWTXUserInfoRequestParameter parameter = new UpdateHWTXUserInfoRequestParameter();
		parameter.setRealname(realname);
		parameter.setHead(head);
		parameter.setNickname(nickname);
		parameter.setEmail(email);
		parameter.setQq(qq);
		parameter.setMicroblog(microblog);
		parameter.setSex(sex.replace("保密", ""));
		parameter.setBrith(brith);
		parameter.setPrivince(privince);
		parameter.setCity(city);
		parameter.setCounty(county);
		parameter.setAddress(address);
		parameter.setBloodtype(bloodtype);
		parameter.setEmergency_name(emergency_name);
		parameter.setEmergency_phone(emergency_phone);
		parameter.setId_number(sfz.contains("x") ? sfz.replace("x", "X") : sfz);
		parameter.setPassport(hz);
		parameter.setDrivinglicense(jsz);
		parameter.setZipcode(yb);
		parameter.setSignature(gxqm);
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
					UpdateHWTXUserInfoResponse response = new UpdateHWTXUserInfoResponse();
					try {
						response.fromJSONString(String.valueOf(responseObject));
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UpdateHWTXUserInfoResponsePayload payload = (UpdateHWTXUserInfoResponsePayload) response
							.getPayload();
					msgString = payload.getMsg();
					State = payload.getState();
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
					showToast(msgString);
					if (!TextUtils.equals(App.getInstance().getHeadurl(), head)) {
						App.getInstance().setHeadurl(head);
						Bitmap value = null;
						mCache.put("blur_bitmap", value);
						mCache.put("head_bitmap", value);
					}
					App.getInstance().setSelectPath("");
					App.getInstance().setSelectPathTemp("");
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
			case CommonUtility.UPLOADING:
				if (!TextUtils.isEmpty(App.getInstance().getSelectPathTemp())) {
					File file = new File(App.getInstance().getSelectPathTemp());
					if (file.exists()) {
						post = new HttpMultipartPost(GenRenZiLiaoActivity.this, App.getInstance().getSelectPathTemp());
						try {
							result = post.execute().get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
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
