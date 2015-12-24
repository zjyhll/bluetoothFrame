package com.hwacreate.outdoor.leftFragment.tuzhong;

import java.util.ArrayList;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.R;

public class DownOfflineActivity extends BaseActivity implements MKOfflineMapListener {
	private TextView cityid = null;// 用来显示城市id
	private CleareditTextView city = null;// 输入搜索内容
	private TextView search = null;// 搜索
	private TextView state = null;// 下载状态
	private TextView start = null;// 开始下载
	private TextView stop = null;// 停止下载
	private ListView allcitylist = null;
	private MKOfflineMap mOffline = null;
	private MyAllCityAdpter allCityAdpter = null;
	/**
	 * 已下载的离线地图信息列表
	 */
	private ArrayList<MKOLUpdateElement> localMapList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_down_offline);
		mOffline = new MKOfflineMap();
		mOffline.init(this);
		initHeaderOther();
		init();
	}

	@Override
	protected void onPause() {
		int cityidInt = Integer
				.parseInt(!TextUtils.isEmpty(cityid.getText().toString()) ? cityid.getText().toString() : "0");
		MKOLUpdateElement temp = mOffline.getUpdateInfo(cityidInt);
		if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
			mOffline.pause(cityidInt);
		}
		super.onPause();
	}

	boolean isdown = false;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			String cityStr = city.getText().toString();
			if (TextUtils.isEmpty(cityStr)) {
				showToast("查询内容为空");
			} else {
				search(cityStr);
			}
			break;
		case R.id.start:
			isdown = false;
			String cityidStrstart = cityid.getText().toString();
			for (int i = 0; i < localMapList.size(); i++) {
				if (TextUtils.equals(cityidStrstart, localMapList.get(i).cityID + "")) {
					isdown = true;
					state.setText("已下载");
				}
			}
			if (TextUtils.isEmpty(cityidStrstart)) {
				showToast("先选择或查询所需城市");
			} else {
				if (isReal) {
					start(cityidStrstart);
				} else {
					showToast("请输入正确的城市名");
				}
			}
			break;
		case R.id.stop:
			String cityidStrstop = cityid.getText().toString();
			if (TextUtils.isEmpty(cityidStrstop)) {
				showToast("先选择或查询所需城市");
			} else {
				stop(cityidStrstop);
			}
			break;
		case R.id.top_itv_back:
			finish();
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {

		top_tv_title.setText("选择对应城市进行下载");
		cityid = (TextView) findViewById(R.id.cityid);
		cityid.setText("");
		city = (CleareditTextView) findViewById(R.id.city);
		search = (TextView) findViewById(R.id.search);
		state = (TextView) findViewById(R.id.state);
		start = (TextView) findViewById(R.id.start);
		stop = (TextView) findViewById(R.id.stop);
		allcitylist = (ListView) findViewById(R.id.allcitylist);
		allCityAdpter = new MyAllCityAdpter(this);
		state.setText("未开始");
		// 获取已下过的离线地图信息
		localMapList = mOffline.getAllUpdateInfo();
		if (localMapList == null) {
			localMapList = new ArrayList<MKOLUpdateElement>();
		}

	}

	private ArrayList<DownOffinebean> allCities = null;

	@Override
	protected void onResload() {
		// 获取所有支持离线地图的城市
		allCities = new ArrayList<DownOffinebean>();
		ArrayList<MKOLSearchRecord> records2 = mOffline.getOfflineCityList();
		DownOffinebean downOffinebean = null;
		if (records2 != null) {
			for (MKOLSearchRecord r : records2) {
				// allCities.add(r.cityName + "(" + r.cityID + ")" + " --"
				// + this.formatDataSize(r.size));
				downOffinebean = new DownOffinebean();
				downOffinebean.setCityID(r.cityID);
				downOffinebean.setCityName(r.cityName);
				downOffinebean.setSize(r.size);
				allCities.add(downOffinebean);
			}
		}
		allcitylist.setAdapter(allCityAdpter);
	}

	@Override
	protected void setMyViewClick() {
		search.setOnClickListener(this);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		top_itv_back.setOnClickListener(this);
	}

	/**
	 * 搜索离线需市
	 * 
	 * @param view
	 */
	boolean isReal = false;

	public void search(String str) {
		ArrayList<MKOLSearchRecord> records = mOffline.searchCity(str);
		if (records == null || records.size() != 1) {
			isReal = false;
			// cityid.setText("");
			return;
		}
		cityid.setText(String.valueOf(records.get(0).cityID));
		showToast("点击开始进行下载");
		isReal = true;
	}

	/**
	 * 开始下载
	 * 
	 * @param view
	 */
	public void start(String str) {
		int cityid = Integer.parseInt(str);
		mOffline.start(cityid);
		if (!isdown) {
			state.setText("请稍等");
		}

		// Toast.makeText(this, "开始下载 ", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 暂停下载
	 * 
	 * @param view
	 */
	public void stop(String str) {
		int cityid = Integer.parseInt(str);
		mOffline.pause(cityid);
		// Toast.makeText(this, "暂停下载 ", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		/**
		 * 退出时，销毁离线地图模块
		 */
		mOffline.destroy();
		super.onDestroy();
	}

	public String formatDataSize(int size) {
		String ret = "";
		if (size < (1024 * 1024)) {
			ret = String.format("%dK", size / 1024);
		} else {
			ret = String.format("%.1fM", size / (1024 * 1024.0));
		}
		return ret;
	}

	@Override
	public void onGetOfflineMapState(int type, int stateInt) {
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
			MKOLUpdateElement update = mOffline.getUpdateInfo(stateInt);
			// 处理下载进度更新提示
			if (update != null) {
				state.setText(String.format("%s : %d%%", update.cityName, update.ratio));
			}
			if (update.ratio == 100) {
				cityid.setText("");
				city.setText("");
				isReal = false;
			}
		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// 有新离线地图安装
			Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// 版本更新提示
			// MKOLUpdateElement e = mOffline.getUpdateInfo(state);

			break;
		default:
			break;
		}
	}

	public class MyAllCityAdpter extends BaseAdapter {
		private Context context = null;

		public MyAllCityAdpter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return allCities.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_dituliebiao, null);
				holder = new ViewHolder();
				holder.ll = (RelativeLayout) convertView.findViewById(R.id.ll);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				holder.tv_daixao = (TextView) convertView.findViewById(R.id.tv_daixao);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.ll.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isReal = true;
					city.setText(allCities.get(position).getCityName());
					cityid.setText(allCities.get(position).getCityID() + "");
					showToast("你选择了" + allCities.get(position).getCityName());
				}
			});
			holder.tv_name.setText(allCities.get(position).getCityName());
			holder.tv_daixao.setText(formatDataSize(allCities.get(position).getSize()));
			return convertView;
		}

		private class ViewHolder {
			private RelativeLayout ll;
			private TextView tv_name, tv_daixao;
		}
	}
}
