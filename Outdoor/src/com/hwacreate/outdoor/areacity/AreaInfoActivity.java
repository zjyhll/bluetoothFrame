package com.hwacreate.outdoor.areacity;

import org.afinal.simplecache.ACache;

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
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.hwacreate.outdoor.R;

/** 省 */
public class AreaInfoActivity extends BaseActivity {
	// 用于数据的缓存
	private ACache mCache = null;
	private ListView lv = null;
	private AreaInfoAdapter adapter = null;
	public static JSONArray jsonObjectshiqu = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_info);
		mCache = ACache.get(AreaInfoActivity.this);
		init();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		App.getInstance().setPrivincename("");
		App.getInstance().setCityname("");
		App.getInstance().setCountyname("");
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
	protected void onInitData() {
		initHeaderOther();
		lv = (ListView) findViewById(R.id.lv);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("选择省份");
		if (mCache.getAsJSONArray("AreaResponseObject") != null) {
			adapter = new AreaInfoAdapter(mCache.getAsJSONArray("AreaResponseObject"));
			lv.setAdapter(adapter);
		}
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
	}

	private class AreaInfoAdapter extends BaseAdapter {
		private LayoutInflater infater = null;
		private JSONArray nameList = null;

		public AreaInfoAdapter(JSONArray nameList) {
			this.infater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.nameList = nameList;
		}

		@Override
		public int getCount() {
			return nameList == null ? 0 : nameList.length();
		}

		@Override
		public Object getItem(int arg0) {

			return null;
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@SuppressLint("InflateParams")
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
				holder.item_areainfo_area.setText(
						String.valueOf(nameList.getJSONObject(position).getJSONObject("province").get("aname")));
			} catch (JSONException e) {

				e.printStackTrace();
			}
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						jsonObjectshiqu = nameList.getJSONObject(position).getJSONArray(String
								.valueOf(nameList.getJSONObject(position).getJSONObject("province").get("aname")));
					} catch (JSONException e) {

						e.printStackTrace();
					}
					try {
						App.getInstance().setPrivincename(String
								.valueOf(nameList.getJSONObject(position).getJSONObject("province").get("aname")));
						App.getInstance().setPrivince(Integer.parseInt(
								String.valueOf(nameList.getJSONObject(position).getJSONObject("province").get("aid"))));
						openActivity(AreaInfoCityActivity.class);
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
