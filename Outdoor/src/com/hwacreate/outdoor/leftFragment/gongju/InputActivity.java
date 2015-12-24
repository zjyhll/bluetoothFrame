package com.hwacreate.outdoor.leftFragment.gongju;

import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bluetooth.le.MipcaActivityCapture;
import com.hwacreate.outdoor.utl.SPUtils;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.R.id;
import com.hwacreate.outdoor.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class InputActivity extends BaseActivity {
	private LinearLayout ll = null;
	private CleareditTextView ctv = null;
	private String ctvStr = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		initHeaderOther();
		init();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.top_itv_back://
			finish();
			break;
		case R.id.top_tv_right://
			ctvStr = ctv.getText().toString();
			if (!TextUtils.isEmpty(ctvStr)) {
				SPUtils.put(InputActivity.this, "input_ctv", ctvStr);
			}
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		// TODO Auto-generated method stub
		ll = (LinearLayout) findViewById(R.id.ll);
		ctv = (CleareditTextView) findViewById(R.id.ctv);
	}

	@Override
	protected void onResload() {
		// TODO Auto-generated method stub
		top_tv_title.setText("手动输入");
		top_tv_right.setText("确认");
	}

	@Override
	protected void setMyViewClick() {
		// TODO Auto-generated method stub

		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
	}

}
