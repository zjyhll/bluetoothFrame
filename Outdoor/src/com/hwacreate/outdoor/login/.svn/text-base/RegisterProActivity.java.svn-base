package com.hwacreate.outdoor.login;

import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.R;

import android.os.Bundle;
import android.view.View;

public class RegisterProActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_pro);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:// 返回
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
		top_tv_title.setText("服务协议");
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);

	}

}
