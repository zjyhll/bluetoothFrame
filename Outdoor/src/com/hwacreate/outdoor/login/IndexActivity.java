package com.hwacreate.outdoor.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.utl.UpdateManager;
import com.hwacreate.outdoor.view.CustomDialog;
import com.hwacreate.outdoor.R;

public class IndexActivity extends BaseActivity {
	// 欢迎页面
	private LinearLayout tiaoguo = null;
	private TextView tiaoguo_time = null;
	private int time_miao = 7;
	private ImageView index_pic = null;
	private String fullurl = null;
	// 用于数据的缓存
	private ACache mCache = null;
	// 检测更新///////////////////////////
	private UpdateManager mUpdateManager;
	private String installUrl;
	private String force;
	private Context context;
	private boolean tiaoguobool = false;// 判断是否跳过，不然点击得太快，更新对话框会弹出时报错

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		context = this;
		mCache = ACache.get(IndexActivity.this);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tiaoguo:
			tiaoguobool = true;
			handler_time.removeCallbacks(runnable);
			Bundle bundle = new Bundle();
			bundle.putInt("login", 1);
			openActivity(LoginActivity.class, bundle);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		tiaoguo = (LinearLayout) findViewById(R.id.tiaoguo);
		tiaoguo_time = (TextView) findViewById(R.id.tiaoguo_time);
		index_pic = (ImageView) findViewById(R.id.index_pic);
	}

	@Override
	protected void onResload() {
		if (mCache.getAsBitmap("index_bitmap") != null) {
			index_pic.setImageBitmap(mCache.getAsBitmap("index_bitmap"));
		}
		if (NetUtil.isNetworkAvailable(IndexActivity.this)) {
			// sendGetYYLAppBootAdsAsyn();
			// 检测更新///////////////////////
			new Thread() {
				@Override
				public void run() {
					super.run();
					isUpdate();
				}
			}.start();
		} else {
			showAlertDialog();
		}
	}

	@Override
	protected void setMyViewClick() {
		tiaoguo.setOnClickListener(this);
	}

	private void startRun() {
		tiaoguo_time.setText(7 + "s");
		runnable.run();
	}

	// Handler与Runnable（最简单型）
	Handler handler_time = new Handler();
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (time_miao > 1) {
				time_miao--;
				tiaoguo_time.setText(time_miao + "s");
				handler_time.postDelayed(this, 1000);
			} else {
				time_miao = 0;
				tiaoguo_time.setText(time_miao + "s");
				handler_time.removeCallbacks(runnable);
				Bundle bundle = new Bundle();
				bundle.putInt("login", 1);
				openActivity(LoginActivity.class, bundle);
				finish();
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			handler_time.removeCallbacks(runnable);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// private Thread thread = null;
	//
	// public void sendGetYYLAppBootAdsAsyn() {
	// thread = new Thread() {
	// public void run() {
	// getYYLAppBootAdsAction();
	// }
	// };
	// thread.start();
	// }
	//
	// private String mString = null;
	//
	// public void getYYLAppBootAdsAction() {
	// GetYYLAppBootAdsRequest request = new GetYYLAppBootAdsRequest();
	// GetYYLAppBootAdsRequestParameter parameter = new
	// GetYYLAppBootAdsRequestParameter();
	// request.setParameter(parameter);
	// String requestUrl = CommonUtility.URL;
	// JSONRequestSender sender = new JSONRequestSender(requestUrl);
	//
	// JSONObject responseObject = null;
	// try {
	// responseObject = sender.send(new JSONObject(request.toJSONString()));
	// } catch (JSONException e) {
	// e.printStackTrace();
	// } catch (ProtocolMissingFieldException e) {
	// e.printStackTrace();
	// } catch (Exception e) {// 服务器错误的时候不能跳转
	// e.printStackTrace();
	// }
	// if (responseObject != null) {
	// try {
	// int ret = (int) responseObject.get("ret");
	// mString = responseObject.getString("msg");
	//
	// if (ret == 0) {
	// GetYYLAppBootAdsResponse response = new GetYYLAppBootAdsResponse();
	// try {
	// response.fromJSONString(responseObject.toString());
	// } catch (ProtocolInvalidMessageException e) {
	// e.printStackTrace();
	// } catch (ProtocolMissingFieldException e) {
	// e.printStackTrace();
	// }
	// // 处理返回的参数，需要强制类型转换
	// GetYYLAppBootAdsResponsePayload payload =
	// (GetYYLAppBootAdsResponsePayload) response.getPayload();
	// this.fullurl = payload.getFullurl();
	// handlerlist.sendEmptyMessage(USERLOGINREQUEST);
	// } else if (ret == 500) {
	// handlerlist.sendEmptyMessage(CommonUtility.KONG);
	// } else {
	// handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
	// }
	// } catch (JSONException e1) {
	//
	// e1.printStackTrace();
	// }
	// } else {// 当服务器无法连接时还是需要跳转
	// handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
	// }
	// }

	// private final int USERLOGINREQUEST = 1;// 成功
	// private final int USERLOGINREQUESTFAILE = 2;// 失败
	// @SuppressLint("HandlerLeak")
	// Handler handlerlist = new Handler() {
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case USERLOGINREQUEST:
	// // 缓存图片,不为空并且不相同
	// if (mCache.getAsString("index_fullurl") != null
	// && !mCache.getAsString("index_fullurl").equals(fullurl)) {
	// ImageLoader.getInstance().displayImage(fullurl, index_pic, optionsin);
	// new MYTask().execute(fullurl);
	// } else if (mCache.getAsString("index_fullurl") == null) {
	// ImageLoader.getInstance().displayImage(fullurl, index_pic, optionsin);
	// new MYTask().execute(fullurl);
	// } else {
	// // 上一次的url与这一次的相同就不缓存了
	// }
	// break;
	// case USERLOGINREQUESTFAILE:
	// break;
	// case CommonUtility.SERVERERROR:
	// startRun();
	// break;
	// case CommonUtility.KONG:
	// startRun();
	// try {
	// if (TextUtils.isEmpty(mString)) {
	// showToast(getResources().getString(R.string.kong));
	// } else {
	// showToast(mString);
	// }
	// } catch (Exception e) {
	// }
	// break;
	// default:
	// break;
	// }
	// };
	// };

	/**
	 * 使用异步任务的规则： 1、申明的类继承AsyncTask 标注三个参数的类型
	 * 2、第一个参数表示要执行的任务，通常是网络的路径；第二个参数表示进度的刻度，第三个参数表示任务执行的返回结果
	 * 
	 * @author liende
	 * 
	 */
	public class MYTask extends AsyncTask<String, Void, Bitmap> {
		/**
		 * 表示任务执行之前的操作
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/**
		 * 主要是完成耗时的操作
		 */
		@Override
		protected Bitmap doInBackground(String... arg0) {
			// 使用网络连接类HttpClient类王城对网络数据的提取
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(arg0[0]);
			Bitmap bitmap = null;
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					byte[] data = EntityUtils.toByteArray(httpEntity);
					bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				}
			} catch (Exception e) {
			}
			return bitmap;
		}

		/**
		 * 主要是更新UI的操作
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			// 保存图片
			mCache.put("index_bitmap", result, ACache.TIME_DAY);
			mCache.put("index_fullurl", fullurl, ACache.TIME_DAY);
		}
	}

	// /** 应用是否更新 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// 有版本更新
					// 此时将计时器取消了
				handler_time.removeCallbacks(runnable);
				mUpdateManager = new UpdateManager(IndexActivity.this, installUrl, handler);
				mUpdateManager.checkUpdateInfo();
				break;
			case 1:// 无版本更新
				startRun();
				break;
			case 2:
				startRun();
				break;
			case 3:// 取消更新按钮(强制更新)
				if (TextUtils.equals(force, "true")) {
					handler_time.removeCallbacks(runnable);
					finish();
				} else {
					startRun();
				}
				break;
			case 4:
				showToast("更新失败，请重新更新");
				break;
			}
		};
	};

	/**
	 * android:versionCode和android:versionName两个字段分别表示版本代码，版本名称。versionCode是整型数字
	 * ，versionName是字符串。versionName是给用户看的，不太容易比较大小，升级检查时，可以以检查versionCode为主，
	 * 方便比较出版本的前后大小。
	 */
	public void isUpdate() {
		String baseUrl = "%s//app_update.jsp?version=%s&platform=android&channel=%s";
		String checkUpdateUrl = String.format(baseUrl, CommonUtility.URLIMAIGN, getVersion(context), "andstd");// 版本号和市场下载的编号
		HttpClient httpClient = new DefaultHttpClient();
		// // 请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		// // 读取超时
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
				force = versionJsonObj.getString("force");
				installUrl = versionJsonObj.getString("download");
				if (tiaoguobool == false) {// 如果没跳转
					if (TextUtils.equals(update, "true")) {// 需要更新
						// // 需要更新
						Log.i("info", "need update");
						// // 这里来检测版本是否需要更新
						handler.sendEmptyMessage(0);
					} else {
						// // 不需要更新,当前版本高于FIR上的app版本.
						Log.i("info", " no need update");
						handler.sendEmptyMessage(1);
					}
				}
			} else {
				handler.sendEmptyMessage(2);
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

	public void showAlertDialog() {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage("无法连接服务器，请检查网络连接");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				handler.sendEmptyMessage(3);
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				handler.sendEmptyMessage(3);
			}
		});
		builder.create().show();
	}
}
