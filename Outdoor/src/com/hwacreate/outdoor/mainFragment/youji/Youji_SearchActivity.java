package com.hwacreate.outdoor.mainFragment.youji;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.R;

/**
 * @author LaLa 详细列表信息
 * 
 */
public class Youji_SearchActivity extends BaseActivity {

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youji_sousuo);
		init();
	}

	public void init() {
		onInitData();
		onResload();
		setMyViewClick();
	}

	@Override
	protected void onInitData() {
		// TODO Auto-generated method stub
		initHeaderOrFooterMain();
		top_tv_title.setText("设置搜索条件");
		setMyViewClick();

	}

	@Override
	protected void onResload() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setMyViewClick() {
		// TODO Auto-generated method stub
		top_itv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;

		default:
			break;
		}
	}

}
