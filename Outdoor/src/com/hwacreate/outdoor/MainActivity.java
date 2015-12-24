package com.hwacreate.outdoor;

import org.afinal.simplecache.ACache;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BackHandledInterface;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.base.SlidingBaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.leftFragment.LeftFragment;
import com.hwacreate.outdoor.leftFragment.SheZhiFragment;
import com.hwacreate.outdoor.leftFragment.TuZhongFragment;
import com.hwacreate.outdoor.leftFragment.ZhengDuiChuXingFragment;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.ActivityManageActivity;
import com.hwacreate.outdoor.leftFragment.myguardianFragment.MyGuardianActivity;
import com.hwacreate.outdoor.leftFragment.tuzhong.BaoDaoFragment;
import com.hwacreate.outdoor.leftFragment.tuzhong.RenwuFragment;
import com.hwacreate.outdoor.mainFragment.HuoDongFragment;
import com.hwacreate.outdoor.mainFragment.WoDeFragment;
import com.hwacreate.outdoor.mainFragment.YouJiFragment;
import com.hwacreate.outdoor.mainFragment.ZhuzhiFragment;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.slidingmenu.lib.SlidingMenu;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.keyhua.outdoor.protocol.GetAreaAndPYAction.GetAreaAndPYRequest;
import com.keyhua.outdoor.protocol.GetAreaAndPYAction.GetAreaAndPYRequestParameter;
import com.keyhua.outdoor.protocol.GetAreaInfo.GetAreaInfoRequest;
import com.keyhua.outdoor.protocol.GetAreaInfo.GetAreaInfoRequestParameter;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @author 曾金叶
 * @2015-8-5 @上午 11:15:59 顶部的控件点击与赋值还是需要在activity中实现
 */
public class MainActivity extends SlidingBaseActivity implements BackHandledInterface {
	private BaseFragment mBackHandedFragment;
	// 用于数据的缓存
	private ACache mCache = null;

	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {
		this.mBackHandedFragment = selectedFragment;
	}

	@Override
	public void onBackPressed() {
		if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
			if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
				twoToDefinish();
			} else {
				getSupportFragmentManager().popBackStack();
			}
		}
	}

	// 下面的四个按钮
	private RadioButton radiobutton_select_huodong = null;// 活动
	private RadioButton radiobutton_select_zhuzhi = null;// 组织
	private RadioButton radiobutton_select_youji = null;// 游记
	private RadioButton radiobutton_select_wode = null;// 我的
	private Fragment frgContent = null;// 容器
	protected String title = null;
	/** TOP中的控件主要是拿来进行操作，BaseFrg中的主要是拿来显示与隐藏，针对主界面顶部的控件 */
	protected ImageView iv_btn;// 返回键
	protected ImageView iv_right_menu;// menu
	protected TextView tv_title;// 标题
	protected TextView tv_right;// 右边文字控件
	protected TextView itv_back;// 返回按钮
	protected TextView ctv_center_et;// 搜索框

	/**************************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu(1);
		setContentView(R.layout.activity_main);
		mCache = ACache.get(MainActivity.this);
		init();
		// 打开页面显示的fragment
		frgContent = new HuoDongFragment();
		switchConent(frgContent, getString(R.string.main_wode));

		Resources resource = this.getResources();
		String pkgName = this.getPackageName();
		// Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
		// 这里把apikey存放于manifest文件中，只是一种存放方式，
		// 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
		// "api_key")
		PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "VjLbHteIKhMC4MrKMEytiIoF");
		// Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
		// PushManager.enableLbs(getApplicationContext());

		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
				resource.getIdentifier("notification_custom_builder", "layout", pkgName),
				resource.getIdentifier("notification_icon", "id", pkgName),
				resource.getIdentifier("notification_title", "id", pkgName),
				resource.getIdentifier("notification_text", "id", pkgName));
		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
		cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);

		cBuilder.setLayoutDrawable(resource.getIdentifier("simple_notification_icon", "drawable", pkgName));
		cBuilder.setNotificationSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
		// 推送高级设置，通知栏样式设置为下面的ID
		PushManager.setNotificationBuilder(this, 1, cBuilder);

	}

	// 选取任务(队员)
	private HuoDongXiangQingItem huoDongXiangQing = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingDuiyuanDao = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	/**
	 * 数据的初始化
	 */
	@SuppressLint("NewApi")
	public void initDao() {
		// 队员数据操作
		huoDongXiangQing = new HuoDongXiangQingItem();
		huoDongXiangQingDuiyuanDao = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQing, MainActivity.this);
		huoDongXiangQing = huoDongXiangQingDuiyuanDao.searchByActid(App.getInstance().getHuoDongId());
		// 领队
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader, MainActivity.this);
		huoDongXiangQingLeader = huoDongXiangQingLeaderDao.searchByActid(App.getInstance().getLeaderHuoDongId());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_iv_btn:
			frgContent = null;
			toggle();
			break;
		case R.id.top_iv_right_menu:// 在途中Frg中显示的按钮
			frgContent = null;
			break;
		case R.id.top_itv_back:// 返回键
			frgContent = null;
			toggle();
			break;
		case R.id.top_ctv_center_et:// 搜索框
			Bundle bundle = new Bundle();
			bundle.putInt("Searchid", 1);
			openActivity(AllSearchActivity.class, bundle);
			break;
		case R.id.radiobutton_select_huodong:// 活动
			frgContent = new HuoDongFragment();
			title = getString(R.string.main_zhuye);
			break;
		case R.id.radiobutton_select_zhuzhi:// 组织
			frgContent = new ZhuzhiFragment();
			title = getString(R.string.main_zuzhi);
			break;
		case R.id.radiobutton_select_youji:// 游记
			frgContent = new YouJiFragment();
			title = getString(R.string.main_youji);
			break;
		case R.id.radiobutton_select_wode:// 我的
			frgContent = new WoDeFragment();
			title = getString(R.string.main_wode);
			break;
		case R.id.radiobutton_select_one:// 整队出行、任务、设为领队
			frgContent = null;
			switch (App.getInstance().getBottonChoice()) {
			case CommonUtility.TUZHONG:
				frgContent = new RenwuFragment();
				title = getString(R.string.left_tuzhong_queren);
				break;
			case CommonUtility.LINGDUIGONGJU:
				frgContent = new ZhengDuiChuXingFragment();
				title = getString(R.string.left_gongju_zhengduichuxing);
				break;

			default:
				break;
			}
			break;
		case R.id.radiobutton_select_two:// 同行卫士、报到
			frgContent = null;
			switch (App.getInstance().getBottonChoice()) {
			case CommonUtility.TUZHONG:// 当（队员）领取了活动才能进入
				initDao();
				if (huoDongXiangQing != null) {
					frgContent = new BaoDaoFragment();
					title = getString(R.string.left_tuzhong_baodao);
				} else {
					showToast("请先领取活动");
					radiobutton_select_one.setChecked(true);
				}
				break;
			case CommonUtility.LINGDUIGONGJU:
				openActivity(MyGuardianActivity.class);
				break;
			default:
				break;
			}
			break;
		case R.id.radiobutton_select_three:// 活动管理、地图
			frgContent = null;
			switch (App.getInstance().getBottonChoice()) {
			case CommonUtility.TUZHONG:// 当（队员或领队）领取了活动才能进入
				initDao();
				if (huoDongXiangQing != null || huoDongXiangQingLeader != null) {
					frgContent = new TuZhongFragment();
					title = getString(R.string.left_tuzhong_tuzhong);
				} else {
					showToast("请先领取活动");
					radiobutton_select_one.setChecked(true);
				}
				break;
			case CommonUtility.LINGDUIGONGJU:
				openActivity(ActivityManageActivity.class);
				break;

			default:
				break;
			}
			break;
		default:
			break;
		}
		if (frgContent != null) {
			switchConent(frgContent, title);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		try {
			if (mCache.getAsJSONArray("AreaResponseObject") == null) {
				sendGetAreaInfoActionAsyn();
			} else if (mCache.getAsJSONObject("AreaAndPYJSONObject") == null) {
				sendAsyn();
			}
		} catch (Exception e) {
		}
	}

	// 退出登录头像改变
	public void start() {
		initSlidingMenu(2);
		frgContent = new SheZhiFragment();
		title = getString(R.string.left_sehzhi);
		if (frgContent != null) {
			switchConent(frgContent, title);
		}
	}

	// 定位到我的页面
	public void startwode() {
		initSlidingMenu(1);
		frgContent = new WoDeFragment();
		title = getString(R.string.main_wode);
		if (frgContent != null) {
			switchConent(frgContent, title);
		}
		radiobutton_select_wode.setChecked(true);
	}

	@Override
	protected void onInitData() {
		initFooterOther();
		radiobutton_select_huodong = (RadioButton) findViewById(R.id.radiobutton_select_huodong);
		radiobutton_select_zhuzhi = (RadioButton) findViewById(R.id.radiobutton_select_zhuzhi);
		radiobutton_select_youji = (RadioButton) findViewById(R.id.radiobutton_select_youji);
		radiobutton_select_wode = (RadioButton) findViewById(R.id.radiobutton_select_wode);
		// 标题栏--------------------------------
		iv_btn = (ImageView) findViewById(R.id.top_iv_btn);
		iv_right_menu = (ImageView) findViewById(R.id.top_iv_right_menu);
		tv_title = (TextView) findViewById(R.id.top_tv_title);
		itv_back = (TextView) findViewById(R.id.top_itv_back);
		tv_right = (TextView) findViewById(R.id.top_tv_right);
		ctv_center_et = (TextView) findViewById(R.id.top_ctv_center_et);
	}

	@Override
	protected void onResload() {
	}

	@Override
	protected void setMyViewClick() {
		tv_right.setOnClickListener(this);
		itv_back.setOnClickListener(this);
		iv_btn.setOnClickListener(this);
		iv_right_menu.setOnClickListener(this);
		ctv_center_et.setOnClickListener(this);
		radiobutton_select_huodong.setOnClickListener(this);
		radiobutton_select_zhuzhi.setOnClickListener(this);
		radiobutton_select_youji.setOnClickListener(this);
		radiobutton_select_wode.setOnClickListener(this);
	}

	/**
	 * 初始化侧边栏
	 */
	private void initSlidingMenu(int start) {
		// 设置左侧滑动菜单
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new LeftFragment(start)).commit();
		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动菜单阴影的图像资源
		sm.setShadowDrawable(null);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.6f);
		// 设置触摸屏幕的模式,这里设置为全屏
		// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.5f);
	}

	/**
	 * 切换Fragment
	 * 
	 * @param fragment
	 */
	public void switchConent(Fragment fragment, String title) {
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
		tv_title.setText(title);
	}

	private Thread thread = null;

	/**
	 * @author LaLa 3级联动选择地区
	 * 
	 */
	public void sendGetAreaInfoActionAsyn() {
		thread = new Thread() {
			public void run() {
				getAreaInfoAction();
			}
		};
		thread.start();
	}

	/**
	 * 
	 */
	public void getAreaInfoAction() {
		GetAreaInfoRequest request = new GetAreaInfoRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetAreaInfoRequestParameter parameter = new GetAreaInfoRequestParameter();
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");

				if (ret == 0) {
					mCache.put("AreaResponseObject", responseObject.getJSONObject("pld").getJSONArray("area"));
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	/**
	 * @author LaLa 定位选择城市
	 * 
	 */
	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				getAction();
			}
		};
		thread.start();
	}

	public void getAction() {
		GetAreaAndPYRequest request = new GetAreaAndPYRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetAreaAndPYRequestParameter parameter = new GetAreaAndPYRequestParameter();
		request.setParameter(parameter);
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(request.toJSONString()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					mCache.put("AreaAndPYJSONObject", responseObject);
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
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				System.out.println("地区数据加载完成");
				break;
			case CommonUtility.SERVEROK2:
				System.out.println("定位城市选择数据加载完成");
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
