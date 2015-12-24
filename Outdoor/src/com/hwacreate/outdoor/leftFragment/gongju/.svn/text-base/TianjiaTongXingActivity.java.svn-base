package com.hwacreate.outdoor.leftFragment.gongju;

import in.srain.cube.image.CubeImageView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bluetooth.le.MipcaActivityCapture;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.view.MyListView;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportRequest;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportRequestParameter;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportResponse;
import com.keyhua.outdoor.protocol.TeamReportAction.TeamReportResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

public class TianjiaTongXingActivity extends BaseActivity {
	// 设备中蓝牙添加
	private MyListView lv = null;
	private MyListAdpterAdd listadapteradd = null;
	private TextView tv_tocontact_search = null;// 搜索按钮

	// pop TODO
	private View parentView = null;
	private PopupWindow popContact = null;
	private LinearLayout ll_popup = null;
	private RelativeLayout parent = null;// 半透明背景色
	private Button btn_pop_ok = null;// 选择添加
	private Button btn_pop_time = null;// 等待倒计时
	private Button btn_pop_cancle = null;// 取消
	// 现实人员列表
	private ListView lv_home = null;// listView
	private MyListAdpter listadapter = null;
	private LinearLayout ll_renyuan = null;
	// 添加形式
	private LinearLayout ll_input = null;
	private TextView tv_saomiao = null;// 二维码扫描
	private TextView tv_input = null;// 手动输入
	private View view = null;

	private void initPopwindow() {// TODO
		popContact = new PopupWindow(TianjiaTongXingActivity.this);
		view = getLayoutInflater().inflate(R.layout.poptong_xingduiwu, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		popContact.setWidth(LayoutParams.MATCH_PARENT);
		popContact.setHeight(LayoutParams.WRAP_CONTENT);
		popContact.setBackgroundDrawable(new BitmapDrawable());
		popContact.setFocusable(true);
		popContact.setOutsideTouchable(true);
		popContact.setContentView(view);
		// pop中的三个按钮
		// 此方法可防止5.0版本下面的banner挡住PopWindows
		popContact.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		parent = (RelativeLayout) view.findViewById(R.id.parent);
		btn_pop_ok = (Button) view.findViewById(R.id.btn_pop_ok);
		btn_pop_time = (Button) view.findViewById(R.id.btn_pop_time);
		btn_pop_cancle = (Button) view.findViewById(R.id.btn_pop_cancle);
		btn_pop_ok.setOnClickListener(this);
		btn_pop_cancle.setOnClickListener(this);
		btn_pop_time.setOnClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_tianjian_tong_xing, null);
		setContentView(parentView);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.tv_tocontact_search:
			// 蓝牙设备的搜索，然后展示到listview中
			showToast("正在搜索...");
			break;
		case R.id.btn_pop_ok:
			btn_pop_ok.setVisibility(View.GONE);
			btn_pop_cancle.setVisibility(View.GONE);
			btn_pop_time.setText("已答应，添加成功");
			// 刷新对应的listview
			break;
		case R.id.btn_pop_time:// 隐藏人员列表，显示扫描按钮，手动输入按钮
			ll_renyuan.setVisibility(View.GONE);
			tv_saomiao.setVisibility(View.VISIBLE);
			tv_input.setVisibility(View.VISIBLE);
			popContact.dismiss();
			break;
		case R.id.btn_pop_cancle:
			popContact.dismiss();
			break;
		case R.id.tv_saomiao:// 二维码扫描
			// showToast("二维码扫描");
			Intent intent = new Intent();
			intent.setClass(TianjiaTongXingActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, CommonUtility.SCANNIN_GREQUEST_CODE);
			finish();
			break;
		case R.id.tv_input:// 手动输入添加
			showToast("手动输入添加");
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		initPopwindow();
		// 蓝牙设备列表
		lv = (MyListView) findViewById(R.id.lv);
		listadapteradd = new MyListAdpterAdd(TianjiaTongXingActivity.this);
		tv_tocontact_search = (TextView) findViewById(R.id.tv_tocontact_search);
		tv_saomiao = (TextView) findViewById(R.id.tv_saomiao);
		tv_input = (TextView) findViewById(R.id.tv_input);
		lv.setAdapter(listadapteradd);
		// 人员列表
		lv_home = (ListView) findViewById(R.id.lv_home);
		ll_renyuan = (LinearLayout) findViewById(R.id.ll_renyuan);
		ll_input = (LinearLayout) findViewById(R.id.ll_input);
		listadapter = new MyListAdpter(TianjiaTongXingActivity.this);
		lv_home.setAdapter(listadapter);
	}

	@Override
	protected void onResload() {
		// TODO Auto-generated method stub
		top_tv_title.setText("添加同行");
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		tv_tocontact_search.setOnClickListener(this);
		tv_input.setOnClickListener(this);
		tv_saomiao.setOnClickListener(this);

	}

	// 蓝牙搜索出来的设备列表
	public class MyListAdpterAdd extends BaseAdapter {
		private Context context = null;

		public MyListAdpterAdd(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_contactmyguardian, null);
				holder = new ViewHolder();
				holder.tv_lyname = (TextView) convertView.findViewById(R.id.tv_lyname);
				holder.tv_tocontact = (TextView) convertView.findViewById(R.id.tv_tocontact);
				// 设值
				holder.tv_tocontact.setText("加入");
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 显示人员列表，隐藏扫描按钮，手动输入按钮
			holder.tv_tocontact.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ll_renyuan.setVisibility(View.VISIBLE);
					tv_saomiao.setVisibility(View.GONE);
					tv_input.setVisibility(View.GONE);
					ll_popup.startAnimation(
							AnimationUtils.loadAnimation(TianjiaTongXingActivity.this, R.anim.activity_translate_in));
					popContact.showAtLocation(parentView, Gravity.CENTER, 0, 0);
				}
			});
			return convertView;
		}

		private class ViewHolder {
			// 蓝牙名、关联按钮
			private TextView tv_lyname, tv_tocontact;
		}
	}

	/**
	 * @author 曾金叶
	 * @2015-8-6 @上午9:58:49
	 * @category 所添加的人员列表
	 * 
	 */
	public class MyListAdpter extends BaseAdapter {
		private Context context = null;

		public MyListAdpter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_tongxingduiwu, null);
				holder = new ViewHolder();
				holder.id_icon = (CubeImageView) convertView.findViewById(R.id.id_icon);
				holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
				holder.tv_bianhao = (TextView) convertView.findViewById(R.id.tv_bianhao);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
			return convertView;
		}

		private class ViewHolder {
			private CubeImageView id_icon;// 右边的icon
			private TextView tv_num, tv_bianhao, tv_name;// 排名，编号，名字
		}
	}

	private String ordercode = null;
	private String huoDongId = null;// 活动id 二维码扫描获取
	private String userid = null;// 用户id 二维码扫描获取

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CommonUtility.SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				/** 跳到相应的界面中 */
				ordercode = data.getStringExtra("ordercode");
				if (!TextUtils.isEmpty(ordercode)) {
					// 拿到值后展示在对应的队员设备号中
					try {
						JSONObject jsonObject = new JSONObject(ordercode);
						huoDongId = jsonObject.getString("huoDongId");
						userid = jsonObject.getString("userid");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sendAsyn();
				} else {
					showToast("二维码不正确，请重新扫描");
				}
				System.out.println("ordercode:" + ordercode);
			}
			break;
		}
	}

	/** 队员报到 ，领队扫描，不管是队员主动网络报到还是领队二维码扫描报到，都必须要在有网的情况下进行 */
	private Thread thread = null;

	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	private Integer result = null;
	private String msgstr = null;

	public void Action() { // TODO
		TeamReportRequest request = new TeamReportRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		TeamReportRequestParameter parameter = new TeamReportRequestParameter();
		parameter.setAct_id(huoDongId);
		parameter.setUser_id(userid);
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
					TeamReportResponse response = new TeamReportResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					TeamReportResponsePayload payload = (TeamReportResponsePayload) response.getPayload();
					result = payload.getResult();
					msgstr = payload.getMsg();
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
				switch (result) {
				case 0:// 失败
					break;
				case 1:// 成功
					break;
				default:
					break;
				}
				showToast(msgstr);
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
