package com.hwacreate.outdoor.leftFragment;

import java.util.List;

import org.afinal.simplecache.ACache;

import com.hwacreate.outdoor.MainActivity;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.leftFragment.tuzhong.RenwuFragment;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.HuoDongFragment;
import com.hwacreate.outdoor.mainFragment.WoDeFragment;
import com.hwacreate.outdoor.mainFragment.wode.GenRenZiLiaoActivity;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @author 曾金叶
 * @2015-8-5 @上午10:20:14
 */
public class LeftFragment extends BaseFragment {
	private RadioButton tv_huodong;// 活动
	private RadioButton tv_tuzhong;// 途中
	private RadioButton tv_shezhi;// 设置
	private RadioButton tv_gongju;// 领队工具
	private CircleImageView left_image;// 左边头像
	private TextView left_banbenhao;// 版本号
	private int start = 0;// 判断在设置界面退出登录左侧选择效果
	// （队员）本地数据,无网络时使用
	private HuoDongXiangQingItem huoDongXiangQingItemhuancunRead = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingItem1DaoRead = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	public LeftFragment(int start) {
		this.start = start;
	}

	/**
	 * 领取的活动详情
	 */
	public HuoDongXiangQingItem initDaoRead() {
		// 选择的活动，判断有无网
		huoDongXiangQingItemhuancunRead = new HuoDongXiangQingItem();
		huoDongXiangQingItem1DaoRead = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQingItemhuancunRead,
				getActivity());
		return huoDongXiangQingItem1DaoRead.searchByActid(App.getInstance().getHuoDongId());
	}

	/**
	 * 领取的活动详情
	 */
	public HuoDongXiangQingLeader initDaoReadLeader() {
		// 选择的活动，判断有无网
		// 领队
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader, getActivity());
		return huoDongXiangQingLeaderDao.searchByActid(App.getInstance().getLeaderHuoDongId());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_menu, null);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_image:
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				App.getInstance().setDownTouXiang(true);
				openActivity(GenRenZiLiaoActivity.class);
			} else {
				openActivity(LoginActivity.class);
				showToastDengLu();
			}
			break;
		case R.id.tv_huodong: // 活动
			newContent = new HuoDongFragment();
			title = getString(R.string.left_huodong);
			break;
		case R.id.tv_tuzhong:// 途中
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (initDaoRead() == null && initDaoReadLeader() == null) {// 若领队或队员状态都未领取任务，则跳入任务界面
					newContent = new RenwuFragment();
					title = getString(R.string.left_tuzhong_queren);
				} else {// 在领取过任务的情况下，默认是跳入地图界面
					newContent = new TuZhongFragment();
					title = getString(R.string.left_tuzhong);
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
			}
			break;
		case R.id.tv_shezhi:// 设置
			newContent = new SheZhiFragment();
			title = getString(R.string.left_sehzhi);
			break;
		case R.id.tv_gongju:// 领队工具
			if (!TextUtils.isEmpty(App.getInstance().getAut())) {
				if (App.getInstance().getIs_leader() == 1) {
					newContent = new ZhengDuiChuXingFragment();
					title = getString(R.string.left_gongju_zhengduichuxing);
				} else {
					showToast("你还不是领队");
				}
			} else {
				showToastDengLu();
				openActivity(LoginActivity.class);
			}
			break;
		default:
			break;
		}
		if (newContent != null) {
			switchFragment(newContent, title);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		if (TextUtils.isEmpty(App.getInstance().getAut()) || TextUtils.isEmpty(App.getInstance().getHeadurl())) {
			mImageLoader.displayImage("drawable://" + R.drawable.temp_hxd_zhuzhi_wode, left_image, options);
		} else {
			mImageLoader.displayImage(App.getInstance().getHeadurl(), left_image, options);
		}
		if (App.getInstance().getDownTouXiang()) {
			StartTouXiang();
		}
		App.getInstance().setDownTouXiang(false);
	}

	@Override
	protected void onInitData() {
		left_image = (CircleImageView) getActivity().findViewById(R.id.left_image);
		tv_huodong = (RadioButton) getActivity().findViewById(R.id.tv_huodong);
		tv_tuzhong = (RadioButton) getActivity().findViewById(R.id.tv_tuzhong);
		tv_shezhi = (RadioButton) getActivity().findViewById(R.id.tv_shezhi);
		tv_gongju = (RadioButton) getActivity().findViewById(R.id.tv_gongju);
		left_banbenhao = (TextView) getActivity().findViewById(R.id.left_banbenhao);
	}

	@Override
	protected void onResload() {
		left_banbenhao.setText("V" + getVersion(getActivity()));
		if (start == 1) {
			tv_huodong.setChecked(true);
		} else if (start == 2) {
			tv_shezhi.setChecked(true);
		}
	}

	@Override
	protected void setMyViewClick() {
		tv_huodong.setOnClickListener(this);
		tv_tuzhong.setOnClickListener(this);
		tv_shezhi.setOnClickListener(this);
		tv_gongju.setOnClickListener(this);
		left_image.setOnClickListener(this);
	}

	@Override
	protected void headerOrFooterViewControl() {

	}

}
