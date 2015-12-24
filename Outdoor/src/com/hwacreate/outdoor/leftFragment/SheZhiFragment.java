package com.hwacreate.outdoor.leftFragment;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.afinal.simplecache.ACache;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.leftFragment.shezhi.GuanYuActivity;
import com.hwacreate.outdoor.leftFragment.shezhi.GuanYuSheBeiActivity;
import com.hwacreate.outdoor.leftFragment.shezhi.HelpActivity;
import com.hwacreate.outdoor.leftFragment.shezhi.YiJianActivity;
import com.hwacreate.outdoor.leftFragment.tuzhong.OfflineActivity;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.login.RegisterOrChangPwdActivity;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.DataCleanManager;
import com.hwacreate.outdoor.utl.UpdateManager;
import com.hwacreate.outdoor.view.CustomDialog;
import com.keyhua.outdoor.protocol.OutdoorFeedbackAction.OutdoorFeedbackRequest;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutRequest;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutRequestParameter;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutResponse;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * @author 曾金叶
 * @2015-8-5 @上午10:15:40
 */
public class SheZhiFragment extends BaseFragment implements OnCheckedChangeListener {
	private ToggleButton tb_wifi = null; // WIFI开关
	private ToggleButton tb_phonelocation = null;// 定位开关
	private ToggleButton tb_outh = null;// 云同步开关
	private RelativeLayout tv_clear_view = null;// 清除缓存
	private TextView tv_m = null;// 缓存的大小
	private RelativeLayout tv_yijian_view = null;// 意见反馈
	private RelativeLayout tv_gengxin_view = null;// 软件更新
	private View tv_gengxin_red = null;// 提示是否有更新的需求
	private RelativeLayout tv_help_view = null;// 帮助
	private RelativeLayout tv_guanyu_view = null;// 关于软件
	private RelativeLayout tv_guanyushebei_view = null;// 关于硬件
	private RelativeLayout tv_xiugaimima_view = null;// 修改密码
	private RelativeLayout tv_dituclear_view = null;// 离线地图
	private TextView tv_tuichudenglu = null;// 退出登录
	private String huancun = null;
	// 用于数据的缓存
	private ACache mCache = null;
	private List<String> Outdoor = null;
	// 选取任务(队员)
	private HuoDongXiangQingItem huoDongXiangQing = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingDuiyuanDao = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCache = ACache.get(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.leftfrag_shezhi, null);
		return view;
	}

	@Override
	public void onClick(View v) {
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.tv_clear_view:
			showAlertDialog("是否清除缓存?                   ", 2);
			break;
		case R.id.tv_yijian_view:
			openActivity(YiJianActivity.class);
			break;
		case R.id.tv_gengxin_view:
			tv_clear_view.setEnabled(false);
			tv_tuichudenglu.setEnabled(false);
			// 检测更新///////////////////////
			new Thread() {
				@Override
				public void run() {
					super.run();
					isUpdate(1);
				}
			}.start();
			break;
		case R.id.tv_help_view:
			openActivity(HelpActivity.class);
			break;
		case R.id.tv_guanyu_view:
			openActivity(GuanYuActivity.class);
			break;
		case R.id.tv_guanyushebei_view:
			openActivity(GuanYuSheBeiActivity.class);
			break;
		case R.id.tv_tuichudenglu:
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				showAlertDialog("是否退出登录?                   ", 1);
			} else {
				showToastDengLu();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
			}
			break;
		case R.id.tv_xiugaimima_view:
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				bundle.putInt("zhuceorwangji", CommonUtility.XIUGAIMIMA);
				openActivity(RegisterOrChangPwdActivity.class, bundle);
			} else {
				showToastDengLu();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
			}
			break;
		case R.id.tv_dituclear_view:
			openActivity(OfflineActivity.class);
			break;
		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}

	// 检测更新///////////////////////////
	private UpdateManager mUpdateManager;
	private String installUrl;

	/**
	 * android:versionCode和android:versionName两个字段分别表示版本代码，版本名称。versionCode是整型数字
	 * ，versionName是字符串。versionName是给用户看的，不太容易比较大小，升级检查时，可以以检查versionCode为主，
	 * 方便比较出版本的前后大小。
	 */
	public void isUpdate(int index) {
		String baseUrl = "%s/manager/app_update.jsp?version=%s&platform=android";
		String checkUpdateUrl = String.format(baseUrl, CommonUtility.URLIMAIGN, getVersioncode(getActivity()));// 版本号和市场下载的编号
		HttpClient httpClient = new DefaultHttpClient();
		// 请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		// 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		try {
			HttpGet httpGet = new HttpGet(checkUpdateUrl);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String firResponse = EntityUtils.toString(httpEntity);
				JSONObject versionJsonObj = new JSONObject(firResponse);
				String update = versionJsonObj.getString("update");
				installUrl = versionJsonObj.getString("download");
				if (TextUtils.equals(update, "true")) {// 需要更新
					if (index == 1) {
						handler.sendEmptyMessage(0);
					} else if (index == 2) {
						handler.sendEmptyMessage(5);
					}
				} else {// 不需要更新
					if (index == 1) {
						handler.sendEmptyMessage(1);
					} else if (index == 2) {
						handler.sendEmptyMessage(6);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			handler.sendEmptyMessage(2);
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			handler.sendEmptyMessage(2);
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			handler.sendEmptyMessage(2);
			e.printStackTrace();
		} catch (UnknownHostException e) {
			handler.sendEmptyMessage(2);
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			handler.sendEmptyMessage(2);
			e.printStackTrace();
		} catch (IOException e) {
			handler.sendEmptyMessage(2);
			e.printStackTrace();
		} catch (JSONException e) {
			handler.sendEmptyMessage(2);
			e.printStackTrace();
		}

	}

	// /** 应用是否更新 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			tv_clear_view.setEnabled(true);
			tv_tuichudenglu.setEnabled(true);
			switch (msg.what) {
			case 0:// 有版本更新
					// 此时将计时器取消了
				mUpdateManager = new UpdateManager(getActivity(), installUrl, handler);
				mUpdateManager.checkUpdateInfo();
				break;
			case 1:// 无版本更新
				showToast("当前最新版本");
				break;
			case 2:
				showToast("更新失败，请重试或重新下载");
				break;
			case 3:// 取消更新按钮(强制更新)
				break;
			case 4:
				showToast("更新失败，请重新更新");
				break;
			case 5:
				tv_gengxin_red.setVisibility(View.VISIBLE);
				break;
			case 6:
				tv_gengxin_red.setVisibility(View.GONE);
				break;
			}
		};
	};

	/** Dialog */
	public void showAlertDialog(String title, final int index) {
		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				switch (index) {
				case 1:
					showdialogtext("退出登录中...");
					sendAsyn();
					break;
				case 2:
					showdialogtext("正在清除中...");
					DataCleanManager.cleanApplicationData(getActivity(), "com.hwacreate.outdoor");
					File file = new File(DataCleanManager.getDiskCacheDir(getActivity()));
					getCacheSize(file);
					// 删除图片的文件夹
					for (int i = 0; i < Outdoor.size(); i++) {
						File appDir = new File(Environment.getExternalStorageDirectory(), Outdoor.get(i));
						if (appDir.exists()) {
							DataCleanManager.deleteFile(appDir);
						}
					}
					if (isshowdialog()) {
						closedialog();
					}
					showToast("清除成功！");
					break;

				default:
					break;
				}
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void onInitData() {
		Outdoor = new ArrayList<String>();
		tb_wifi = (ToggleButton) getActivity().findViewById(R.id.tb_wifi);
		tb_phonelocation = (ToggleButton) getActivity().findViewById(R.id.tb_phonelocation);
		tb_outh = (ToggleButton) getActivity().findViewById(R.id.tb_outh);
		tv_clear_view = (RelativeLayout) getActivity().findViewById(R.id.tv_clear_view);
		tv_yijian_view = (RelativeLayout) getActivity().findViewById(R.id.tv_yijian_view);
		tv_gengxin_view = (RelativeLayout) getActivity().findViewById(R.id.tv_gengxin_view);
		tv_help_view = (RelativeLayout) getActivity().findViewById(R.id.tv_help_view);
		tv_guanyu_view = (RelativeLayout) getActivity().findViewById(R.id.tv_guanyu_view);
		tv_guanyushebei_view = (RelativeLayout) getActivity().findViewById(R.id.tv_guanyushebei_view);
		tv_xiugaimima_view = (RelativeLayout) getActivity().findViewById(R.id.tv_xiugaimima_view);
		tv_dituclear_view = (RelativeLayout) getActivity().findViewById(R.id.tv_dituclear_view);
		tv_m = (TextView) getActivity().findViewById(R.id.tv_m);
		tv_gengxin_red = (View) getActivity().findViewById(R.id.tv_gengxin_red);
		tv_tuichudenglu = (TextView) getActivity().findViewById(R.id.tv_tuichudenglu);
	}

	@Override
	protected void onResload() {
		tb_wifi.setButtonDrawable(R.drawable.toggle_btn);
		tb_phonelocation.setButtonDrawable(R.drawable.toggle_btn);
		tb_outh.setButtonDrawable(R.drawable.toggle_btn);
		if (App.getInstance().isTb_wifi()) {
			tb_wifi.setChecked(true);
			App.getInstance().setTb_wifi(true);
		}
		if (CommonUtility.isOPen(getActivity())) {
			tb_phonelocation.setChecked(true);
			App.getInstance().setTb_phonelocation(true);
			tb_phonelocation.setEnabled(false);
		}
		if (App.getInstance().isTb_outh()) {
			tb_outh.setChecked(true);
			App.getInstance().setTb_outh(true);
		}
		File file = new File(DataCleanManager.getDiskCacheDir(getActivity()));
		getCacheSize(file);
		Outdoor.add("outdoor");
		// Outdoor.add("outdoorhuodong");
		Outdoor.add("outdoortopimage");
		// Outdoor.add("outdooryouji");
		new Thread() {
			@Override
			public void run() {
				super.run();
				isUpdate(2);
			}
		}.start();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (TextUtils.isEmpty(App.getInstance().getAut())) {
			tv_tuichudenglu.setText("登    录");
		} else {
			tv_tuichudenglu.setText("退出登录");
		}
	}

	/**
	 * 获取当前缓存
	 */
	public void getCacheSize(File file) {
		try {
			huancun = DataCleanManager.getCacheSize(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tv_m.setText(huancun);
	}

	@Override
	protected void setMyViewClick() {
		tb_wifi.setOnCheckedChangeListener(this);
		tb_phonelocation.setOnCheckedChangeListener(this);
		tb_outh.setOnCheckedChangeListener(this);
		tv_clear_view.setOnClickListener(this);
		tv_yijian_view.setOnClickListener(this);
		tv_gengxin_view.setOnClickListener(this);
		tv_help_view.setOnClickListener(this);
		tv_guanyu_view.setOnClickListener(this);
		tv_guanyushebei_view.setOnClickListener(this);
		tv_tuichudenglu.setOnClickListener(this);
		tv_xiugaimima_view.setOnClickListener(this);
		tv_dituclear_view.setOnClickListener(this);
	}

	@Override
	protected void headerOrFooterViewControl() {
		main_rg.setVisibility(View.GONE);
		top_iv_btn.setVisibility(View.VISIBLE);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.tb_wifi:
			if (isChecked) {
				App.getInstance().setTb_wifi(true);
			} else {
				App.getInstance().setTb_wifi(false);
			}
			break;
		case R.id.tb_phonelocation:
			if (isChecked) {
				App.getInstance().setTb_phonelocation(true);
				CommonUtility.toggleGPS(getActivity());
			} else {
				App.getInstance().setTb_phonelocation(false);
				CommonUtility.toggleGPS(getActivity());
			}
			break;
		case R.id.tb_outh:
			if (isChecked) {
				App.getInstance().setTb_outh(true);
			} else {
				App.getInstance().setTb_outh(false);
			}
			break;

		default:
			break;
		}
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

	public void Action() {
		UserAppLoginOutRequest request = new UserAppLoginOutRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		UserAppLoginOutRequestParameter parameter = new UserAppLoginOutRequestParameter();
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
					UserAppLoginOutResponse response = new UserAppLoginOutResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UserAppLoginOutResponsePayload payload = (UserAppLoginOutResponsePayload) response.getPayload();
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

	// 服务器返回提示信息
	private String msgString = null;
	private int State = 0;
	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				if (State == 0) {
					App.getInstance().setAut("");
					showToast(msgString);
					tv_tuichudenglu.setText("登    录");
					Start();
					Bitmap value = null;
					mCache.put("head_bitmap", value);
					mCache.put("blur_bitmap", value);
					App.getInstance().ClearAll();
					huoDongXiangQing = new HuoDongXiangQingItem();
					huoDongXiangQingDuiyuanDao = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQing, getActivity());
					huoDongXiangQingLeader = new HuoDongXiangQingLeader();
					huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader,
							getActivity());
					if (huoDongXiangQingDuiyuanDao != null) {
						huoDongXiangQingDuiyuanDao.deleteAll();
					}
					if (huoDongXiangQingLeaderDao != null) {
						huoDongXiangQingLeaderDao.deleteAll();
					}
				} else {
					showToast(msgString);
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				if (isshowdialog()) {
					closedialog();
				}
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
