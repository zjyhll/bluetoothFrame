package com.hwacreate.outdoor.areacity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.mainFragment.wode.GenRenZiLiaoActivity;
import com.keyhua.protocol.json.JSONException;
import com.hwacreate.outdoor.R;

/** 区 */
public class AreaInfoZoneActivity extends BaseActivity {
	private ListView lv = null;
	private AreaInfoAdapter adapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_info);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			App.getInstance().setPrivincename("");
			App.getInstance().setCityname("");
			App.getInstance().setCountyname("");
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		App.getInstance().setPrivincename("");
		App.getInstance().setCityname("");
		App.getInstance().setCountyname("");
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		lv = (ListView) findViewById(R.id.lv);
		adapter = new AreaInfoAdapter();
		lv.setAdapter(adapter);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("选择区县");
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
	}

	@SuppressLint("InflateParams")
	private class AreaInfoAdapter extends BaseAdapter {
		private LayoutInflater infater = null;

		public AreaInfoAdapter() {
			this.infater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {

			return AreaInfoCityActivity.city == null ? 0 : AreaInfoCityActivity.city.length();
		}

		@Override
		public Object getItem(int arg0) {

			return null;
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null || convertView.getTag() == null) {
				convertView = infater.inflate(R.layout.item_aerainfo_lv, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			try {
				holder.item_areainfo_area
						.setText(String.valueOf(AreaInfoCityActivity.city.getJSONObject(position).get("aname")));
			} catch (JSONException e) {

				e.printStackTrace();
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						App.getInstance().setCountyname(
								String.valueOf(AreaInfoCityActivity.city.getJSONObject(position).get("aname")));
						App.getInstance().setCounty(Integer
								.valueOf(String.valueOf(AreaInfoCityActivity.city.getJSONObject(position).get("aid"))));
						// openActivity(GenRenZiLiaoActivity.class);
						finish();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			// 省市区
			TextView item_areainfo_area;

			public ViewHolder(View view) {
				item_areainfo_area = (TextView) view.findViewById(R.id.item_areainfo_area);
			}
		}
	}
}
