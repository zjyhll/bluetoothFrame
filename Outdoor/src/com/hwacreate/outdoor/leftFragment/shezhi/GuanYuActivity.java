package com.hwacreate.outdoor.leftFragment.shezhi;

import android.os.Bundle;
import android.view.View;

import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.R;

/**
 * @author LaLa 关于软件
 *
 */
public class GuanYuActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shezhi_guanyu);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("关于户外同行");
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
	}
}
