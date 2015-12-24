package com.hwacreate.outdoor.base;

import com.hwacreate.outdoor.slidingmenu.lib.app.SlidingFragmentActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.view.CustomProgressDialog;
import com.hwacreate.outdoor.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * @author 曾金叶
 * @2015-8-4 @下午5:42:44
 */
@SuppressWarnings("deprecation")
public abstract class SlidingBaseActivity extends SlidingFragmentActivity implements OnClickListener {
	// 屏幕分辨率
	protected static int displayWidth = 0;
	protected static int displayHeight = 0;

	/** 当构造Activity时将来调用, 主要用做数据初始化 , 在onResload之前调用 */
	protected abstract void onInitData();

	/** 每当Activity创建出来时,将先调用, 主要用做资源的加载 */
	protected abstract void onResload();

	/** 每当Activity创建出来时,主要是针对控件的点击事件 */
	protected abstract void setMyViewClick();

	/*** [对Activity生命周期的管理] ***********************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置全屏显示
		setFullScreen(true);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void init() {
		onInitData();
		onResload();
		setMyViewClick();
	}

	// 解决用户连续点击造成出现多个相同的activity
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (CommonUtility.isFastDoubleClick()) {
				return true;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	/** * 检测Android设备是否支持摄像机 */
	protected static boolean checkCameraDevice(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	protected static boolean isCameraCanUse() {
		boolean canUse = true;
		Camera mCamera = null;
		try {
			// TODO camera驱动挂掉,处理??
			mCamera = Camera.open();
		} catch (Exception e) {
			canUse = false;
		}
		if (canUse) {
			mCamera.release();
			mCamera = null;
		}
		return canUse;
	}

	// 判断是否有空格
	protected static boolean isTextReplaceAll(String text) {
		if (TextUtils.isEmpty(text.replaceAll("\\s*", ""))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 程序退出dialog id
	 */
	protected static final int DIALOG_ID_FINISH = 0;
	/**
	 * 等待dialog id
	 */
	protected static final int DIALOG_ID_PROGRESS = 1;
	protected Dialog dialog = null;

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DIALOG_ID_FINISH:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			dialog = builder.setTitle("退出").setMessage("是否要退出？")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// SPUtils.clear(BaseActivity.this);
							System.exit(0);
							finish();
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}).create();
			break;
		case DIALOG_ID_PROGRESS:
			dialog = new ProgressDialog(this);
			((ProgressDialog) dialog).setMessage("数据加载中，请等待...");
			break;
		}
		return dialog;
	}

	// 返回键设置，两次点击退出
	private long firstTime = 0;

	protected void twoToDefinish() {
		long secondtime = System.currentTimeMillis();
		// 连续点击两次时间少于2秒退出
		if (secondtime - firstTime > 2000) {
			showToast("再按一次返回键退出");
			// 保存第一次按下的时间
			firstTime = secondtime;
		} else {
			finish();
			// 清除缓存
			System.exit(0);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 *      没root的手机可用这个储存，root的文件夹还是在，所以还是能用
	 */
	protected void setFullScreen(boolean needState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (!needState) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}

	/*** [重写的toast] ***********************************************/
	protected Toast toast;

	protected void showToast(String text) {
		// 判断toast是否为空 ，空则执行
		if (toast == null) {
			// 将toast的时间设置
			toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		}
		// 通过传参的方式将text赋值到toast上
		toast.setText(text);
		// 显示toast
		toast.show();
	}

	/**
	 * 不带参数跳转
	 */
	protected void openActivity(Class<?> cls) {
		// 新建意图
		Intent intent = new Intent();
		// 设置要跳转的activity
		// 参数1，Context得到包名。参数2，class得到类名，来唯一确定Activity
		intent.setClass(this, cls);
		// 跳转到相应的activity
		startActivity(intent);
		// 切换场景时动作设置
	}

	/**
	 * 带参数跳转
	 */
	protected void openActivity(Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		intent.putExtras(extras);
		startActivity(intent);
	}

	protected void getWidthHeight() {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		displayWidth = mDisplayMetrics.widthPixels;
		displayHeight = mDisplayMetrics.heightPixels;
	}

	protected RadioButton radiobutton_select_one = null;// 整队出行、任务
	protected RadioButton radiobutton_select_two = null;// 同行卫士、报到
	protected RadioButton radiobutton_select_three = null;// 活动管理、地图

	// OtherActivity关联
	public void initFooterOther() {
		radiobutton_select_one = (RadioButton) findViewById(R.id.radiobutton_select_one);
		radiobutton_select_two = (RadioButton) findViewById(R.id.radiobutton_select_two);
		radiobutton_select_three = (RadioButton) findViewById(R.id.radiobutton_select_three);
		radiobutton_select_one.setOnClickListener(this);
		radiobutton_select_two.setOnClickListener(this);
		radiobutton_select_three.setOnClickListener(this);
	}

	private CustomProgressDialog mydialog = null;

	/**
	 * 显示美团进度对话框
	 * 
	 * @param v
	 */
	protected void showdialog() {
		try {
			mydialog = new CustomProgressDialog(this, "正在加载中...", R.anim.frame);
			mydialog.show();
		} catch (Exception e) {
		}
	}

	protected void showdialogtext(String text) {
		try {
			mydialog = new CustomProgressDialog(this, text, R.anim.frame);
			mydialog.show();
		} catch (Exception e) {
		}
	}

	protected void closedialog() {
		try {
			if (mydialog != null && this != null && mydialog.isShowing()) {
				mydialog.dismiss();
			}
		} catch (Exception e) {
		}
	}

	protected boolean isshowdialog() {
		if (mydialog != null && this != null) {
			if (mydialog.isShowing()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	protected CustomProgressDialog getCustomProgressDialog() {
		try {
			if (mydialog != null) {
				return mydialog;
			}
		} catch (Exception e) {
		}
		return null;
	}
}
