package com.hwacreate.outdoor.base;

import java.util.ArrayList;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.MyLogger;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CustomProgressDialog;
import com.hwacreate.outdoor.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.ActionBar.LayoutParams;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.image.impl.DefaultImageLoadHandler;

/**
 * @author 曾金叶
 * @2015-8-4 @下午5:42:44
 */
@SuppressWarnings("deprecation")
public abstract class BaseActivity extends FragmentActivity implements OnClickListener {
	protected static final int REQUEST_IMAGE = 2;
	// 屏幕分辨率
	protected static int displayWidth = 0;
	protected static int displayHeight = 0;
	// 图片加载
	protected ImageLoader imageLoader = null;
	protected ImageLoader imageLoaderRectF = null;
	protected com.nostra13.universalimageloader.core.ImageLoader mImageLoader = com.nostra13.universalimageloader.core.ImageLoader
			.getInstance();
	protected DisplayImageOptions options;

	// log
	public MyLogger loghxd = MyLogger.hxdLog();
	public MyLogger logzt = MyLogger.ztLog();
	public MyLogger logzjy = MyLogger.zjyLog();

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
		setFullScreen(true);
		imageLoader = ImageLoaderFactory.create(this);
		imageLoaderRectF = ImageLoaderFactory.create(this);
		DefaultImageLoadHandler handler = new DefaultImageLoadHandler(this);
		// pick one of the following method
		// handler.setLoadingBitmap(Bitmap loadingBitmap);
		// handler.setLoadingResources(int loadingBitmap);
		// handler.setLoadingImageColor(int color);
		// handler.setLoadingImageColor(String colorString);
		handler.setLoadingImageColor(getResources().getColor(R.color.white));
		handler.setErrorResources(R.drawable.dmbg_default);
		imageLoader.setImageLoadHandler(handler);

		DefaultImageLoadHandler handler1 = new DefaultImageLoadHandler(this);
		// pick one of the following method
		// handler.setLoadingBitmap(Bitmap loadingBitmap);
		// handler.setLoadingResources(int loadingBitmap);
		// handler.setLoadingImageColor(int color);
		// handler.setLoadingImageColor(String colorString);
		handler1.setLoadingImageColor(getResources().getColor(R.color.white));
		handler1.setErrorResources(R.drawable.dmbg_default);
		handler1.setImageRounded(true, 8);
		imageLoaderRectF.setImageLoadHandler(handler1);
		options = new DisplayImageOptions.Builder()
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.dmbg_default)
				// 设置图片URL为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.dmbg_default)
				// 设置图片加载或解码过程中发生错误显示的图片
				.displayer(new RoundedBitmapDisplayer(4)) // 设置成圆角图片
				.displayer(new FadeInBitmapDisplayer(100)).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 创建配置过得DisplayImageOption对象

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void init() {
		onInitData();
		onResload();
		setMyViewClick();
	}

	/**
	 * 获取版本号
	 * 
	 */
	public String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = String.valueOf(info.versionCode);
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0";
		}
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
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText(text);
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}

	protected void showToastLogin() {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText("登录失效，请重新登录");
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}

	protected void showToastDengLu() {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText("请先登录");
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
	}

	protected void showToastNet() {
		try {
			// 判断toast是否为空 ，空则执行
			if (toast == null) {
				// 将toast的时间设置
				toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
			}
			// 通过传参的方式将text赋值到toast上
			toast.setText("没有网络连接，请检查网络");
			// 显示toast
			toast.show();
		} catch (Exception e) {
		}
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

	/*** [初始化arraylist] ***********************************************/
	public static <T> ArrayList<T> createArrayList(T... elements) {
		ArrayList<T> list = new ArrayList<T>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}

	protected void getWidthHeight() {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		displayWidth = mDisplayMetrics.widthPixels;
		displayHeight = mDisplayMetrics.heightPixels;
	}

	protected ImageView top_iv_btn = null;// 左边的图片
	protected TextView top_itv_back = null;// 左边的文字
	protected TextView top_tv_title = null;// 中间的文字
	protected TextView top_ctv_center_et = null;// 中间的搜索框
	protected TextView top_tv_right = null;// 右边的文字
	protected ImageView top_iv_right_menu = null;// 右边的图片

	// MainActivity关联
	public void initHeaderOrFooterMain() {
		top_iv_btn = (ImageView) findViewById(R.id.top_iv_btn);
		top_itv_back = (TextView) findViewById(R.id.top_itv_back);
		top_tv_title = (TextView) findViewById(R.id.top_tv_title);
		top_ctv_center_et = (TextView) findViewById(R.id.top_ctv_center_et);
		top_tv_right = (TextView) findViewById(R.id.top_tv_right);
		top_iv_right_menu = (ImageView) findViewById(R.id.top_iv_right_menu);
		top_iv_btn.setVisibility(View.GONE);
		top_tv_right.setVisibility(View.GONE);
		top_iv_right_menu.setVisibility(View.GONE);
		top_itv_back.setVisibility(View.VISIBLE);
	}

	// OtherActivity关联
	public void initHeaderOther() {
		top_itv_back = (TextView) findViewById(R.id.top_itv_back);
		top_tv_title = (TextView) findViewById(R.id.top_tv_title);
		top_tv_right = (TextView) findViewById(R.id.top_tv_right);
	}

	protected RadioButton radiobutton_select_one = null;// 整队出行、任务
	protected RadioButton radiobutton_select_two = null;// 同行卫士、报到
	protected RadioButton radiobutton_select_three = null;// 活动管理、地图
	protected RadioGroup rg_button = null;//

	// OtherActivity关联
	public void initFooterOther(String str1, String str2, String str3) {
		radiobutton_select_one = (RadioButton) findViewById(R.id.radiobutton_select_one);
		radiobutton_select_two = (RadioButton) findViewById(R.id.radiobutton_select_two);
		radiobutton_select_three = (RadioButton) findViewById(R.id.radiobutton_select_three);
		rg_button = (RadioGroup) findViewById(R.id.rg_button);
		radiobutton_select_one.setText(str1);
		radiobutton_select_two.setText(str2);
		radiobutton_select_three.setText(str3);
		radiobutton_select_one.setOnClickListener(this);
		radiobutton_select_two.setOnClickListener(this);
		radiobutton_select_three.setOnClickListener(this);
	}

	protected LinearLayout top_tv_view = null;
	protected ImageView top_tv_shoucang = null;
	protected ImageView top_tv_fenxiang = null;

	public void initHeaderOtherHuoDong() {
		top_itv_back = (TextView) findViewById(R.id.top_itv_back);
		top_tv_title = (TextView) findViewById(R.id.top_tv_title);
		top_tv_right = (TextView) findViewById(R.id.top_tv_right);
		top_tv_view = (LinearLayout) findViewById(R.id.top_tv_view);
		top_tv_shoucang = (ImageView) findViewById(R.id.top_tv_shoucang);
		top_tv_fenxiang = (ImageView) findViewById(R.id.top_tv_fenxiang);
		top_tv_right.setOnClickListener(this);
		top_itv_back.setOnClickListener(this);
		top_tv_shoucang.setOnClickListener(this);
		top_tv_fenxiang.setOnClickListener(this);
	}

	protected TextView huodong_select_one = null;// 整队出行、任务
	protected TextView huodong_select_two = null;// 同行卫士、报到
	protected TextView huodong_select_three = null;// 活动管理、地图
	protected LinearLayout huodong_select_two_view = null;
	protected LinearLayout huodong_select_three_view = null;
	protected LinearLayout huodong_view = null;//

	public void initFooterOtherHuoDong(String str1, String str2, String str3, boolean three, int index) {
		huodong_view = (LinearLayout) findViewById(R.id.huodong_view);
		huodong_select_one = (TextView) findViewById(R.id.huodong_select_one);
		huodong_select_two = (TextView) findViewById(R.id.huodong_select_two);
		huodong_select_three = (TextView) findViewById(R.id.huodong_select_three);
		huodong_select_two_view = (LinearLayout) findViewById(R.id.huodong_select_two_view);
		huodong_select_three_view = (LinearLayout) findViewById(R.id.huodong_select_three_view);
		huodong_select_one.setText(str1);
		huodong_select_two.setText(str2);
		huodong_select_three.setText(str3);
		if (index == 1) {
			// huodong_select_two.setBackgroundResource(R.color.huodong1);
		} else {
			// huodong_select_two.setBackgroundResource(R.color.huodong2);
		}
		if (three) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(App.getInstance().getScreenWidth() / 3,
					LayoutParams.WRAP_CONTENT);
			huodong_select_one.setLayoutParams(lp);
			// huodong_select_two.setLayoutParams(lp);
			// huodong_select_three.setLayoutParams(lp);
			huodong_select_three_view.setVisibility(View.VISIBLE);
			// huodong_select_three.setBackgroundResource(R.color.huodong2);
		} else {
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(App.getInstance().getScreenWidth() / 3,
					LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(App.getInstance().getScreenWidth() * 2 / 3,
					LayoutParams.WRAP_CONTENT);
			huodong_select_one.setLayoutParams(lp1);
			// huodong_select_two.setLayoutParams(lp2);
			huodong_select_three_view.setVisibility(View.GONE);
		}
		huodong_select_one.setOnClickListener(this);
		huodong_select_two.setOnClickListener(this);
		huodong_select_three.setOnClickListener(this);
	}

	/** 判断服务是否启动 */
	public boolean isWorked() {
		ActivityManager myManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals("com.hwacreate.outdoor.service.GpsInfoCollectionService")) {
				return true;
			}
		}
		return false;
	}

	public void showShareContent(String url) {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(url);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(url);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImageUrl("https://mmbiz.qlogo.cn/mmbiz/k1oxCjhZmvWicCpic4cKibCjxmicia8SZHnibrOiatNXXveiaGL7ggtlFSvK8mf0Ey9drNjcNNABTwnRMNsXlT8yCH0gYw/0?wx_fmt=png");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("户外同行");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(url);
		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();
		// 在自动授权时可以禁用SSO方式
		// if(!CustomShareFieldsPage.getBoolean("enableSSO", true))
		oks.disableSSOWhenAuthorize();
		/** 直接分享。不用显示界面 */
		oks.setSilent(true);
		// 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
		oks.show(this);
	}

	/**
	 * @author zjy java的垃圾回收机制并没有那么的聪明，我们finish掉了，但里面相关的资源他未必回收。
	 *         有可能他自以为很聪明的留下来等着我们下次使用。 所以我们需要在onStop的方法中手动释放imageView这样的大型资源。
	 */
	private void releaseImageView(ImageView imageView) {
		Drawable d = imageView.getDrawable();
		if (d != null)
			d.setCallback(null);
		imageView.setImageDrawable(null);
		imageView.setBackgroundDrawable(null);
	}
}
