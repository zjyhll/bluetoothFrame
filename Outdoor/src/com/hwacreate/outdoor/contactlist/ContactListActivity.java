package com.hwacreate.outdoor.contactlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.afinal.simplecache.ACache;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.contactlist.MyLetterListView.OnTouchingLetterChangedListener;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.keyhua.outdoor.protocol.GetAreaAndPYAction.GetAreaAndPYListItem;
import com.keyhua.outdoor.protocol.GetAreaAndPYAction.GetAreaAndPYRequest;
import com.keyhua.outdoor.protocol.GetAreaAndPYAction.GetAreaAndPYRequestParameter;
import com.keyhua.outdoor.protocol.GetAreaAndPYAction.GetAreaAndPYResponse;
import com.keyhua.outdoor.protocol.GetAreaAndPYAction.GetAreaAndPYResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author LaLa 城市定位选择
 * 
 */

public class ContactListActivity extends BaseActivity implements OnScrollListener {
	private BaseAdapter adapter;
	private ResultListAdapter resultListAdapter;
	private ListView personList;
	private ListView resultList;
	private TextView overlay; // 对话框首字母textview
	private MyLetterListView letterListView; // A-Z listview
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler;
	private OverlayThread overlayThread; // 显示首字母对话框
	private ArrayList<City> allCity_lists; // 所有城市列表
	private ArrayList<City> city_lists;// 城市列表
	private ArrayList<City> city_hot;
	private ArrayList<City> city_result;
	private ArrayList<String> city_history;
	private EditText sh;
	private TextView tv_noresult;

	private String currentCity; // 用于保存定位到的城市
	private int locateProcess = 1; // 记录当前定位的状态 正在定位-定位成功-定位失败
	private boolean isNeedFresh;

	// 用于数据的缓存
	private ACache mCache = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactlist_main);
		mCache = ACache.get(ContactListActivity.this);
		init();
	}

	@Override
	public void onBackPressed() {
		if (resultList.getVisibility() == View.VISIBLE) {
			resultList.setVisibility(View.GONE);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			if (resultList.getVisibility() == View.VISIBLE) {
				resultList.setVisibility(View.GONE);
			} else {
				finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		personList = (ListView) findViewById(R.id.list_view);
		allCity_lists = new ArrayList<City>();
		city_hot = new ArrayList<City>();
		city_result = new ArrayList<City>();
		city_history = new ArrayList<String>();
		resultList = (ListView) findViewById(R.id.search_result);
		sh = (EditText) findViewById(R.id.sh);
		tv_noresult = (TextView) findViewById(R.id.tv_noresult);
		alphaIndexer = new HashMap<String, Integer>();
		handler = new Handler();
		overlayThread = new OverlayThread();
		isNeedFresh = true;
		letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("选择城市");
		locateProcess = 1;
		personList.setAdapter(adapter);
		personList.setOnScrollListener(this);
		resultListAdapter = new ResultListAdapter(this, city_result);
		resultList.setAdapter(resultListAdapter);
		initOverlay();
		hotCityInit();
		hisCityInit();
		if (mCache.getAsJSONObject("AreaAndPYJSONObject") != null) {
			cityInit();
			setAdapter(allCity_lists, city_hot, city_history);
			getNowLocation();
		} else {
			sendAsyn();
		}
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		sh.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString() == null || "".equals(s.toString())) {
					letterListView.setVisibility(View.VISIBLE);
					personList.setVisibility(View.VISIBLE);
					resultList.setVisibility(View.GONE);
					tv_noresult.setVisibility(View.GONE);
				} else {
					city_result.clear();
					letterListView.setVisibility(View.GONE);
					personList.setVisibility(View.GONE);
					getResultCityList(s.toString());
					if (city_result.size() <= 0) {
						tv_noresult.setVisibility(View.VISIBLE);
						resultList.setVisibility(View.GONE);
					} else {
						tv_noresult.setVisibility(View.GONE);
						resultList.setVisibility(View.VISIBLE);
						resultListAdapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		personList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position >= 3) {
					App.getInstance().setContactCity(allCity_lists.get(position).getName());
					if (resultList.getVisibility() == View.VISIBLE) {
						resultList.setVisibility(View.GONE);
					} else {
						finish();
					}
				}
			}
		});
		resultList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				App.getInstance().setContactCity(city_result.get(position).getName());
				if (resultList.getVisibility() == View.VISIBLE) {
					resultList.setVisibility(View.GONE);
				} else {
					finish();
				}
			}
		});
	}

	private void cityInit() {
		City city = new City("定位", "0"); // 当前定位城市
		allCity_lists.add(city);
		city = new City("热门", "1"); // 热门城市
		allCity_lists.add(city);
		city = new City("全部", "2"); // 全部城市
		allCity_lists.add(city);
		city_lists = getCityList();
		allCity_lists.addAll(city_lists);
	}

	/**
	 * 查找城市
	 */
	@SuppressWarnings("unchecked")
	private void getResultCityList(String keyword) {
		for (GetAreaAndPYListItem item : mlist) {
			if (item.getCity().contains(keyword)) {
				City city = new City(item.getCity(), item.getField());
				city_result.add(city);
			}
			if (item.getField().contains(keyword)) {
				City city = new City(item.getCity(), item.getField());
				city_result.add(city);
			}
		}
		Collections.sort(city_result, comparator);
	}

	private ArrayList<GetAreaAndPYListItem> mlist = null;

	/**
	 * 所有城市
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<City> getCityList() {
		ArrayList<City> list = new ArrayList<City>();
		mlist = new ArrayList<GetAreaAndPYListItem>();
		JSONObject jsonObject = mCache.getAsJSONObject("AreaAndPYJSONObject");
		if (jsonObject != null) {
			try {
				int ret = jsonObject.getInt("ret");
				if (ret == 0) {
					GetAreaAndPYResponse response = new GetAreaAndPYResponse();
					try {
						response.fromJSONString(jsonObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetAreaAndPYResponsePayload payload = (GetAreaAndPYResponsePayload) response.getPayload();
					this.mlist = payload.getList();
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			City city;
			for (GetAreaAndPYListItem item : mlist) {
				city = new City(item.getCity(), item.getField());
				list.add(city);
			}
			Collections.sort(list, comparator);
		} else {
			sendAsyn();
		}
		return list;
	}

	/**
	 * @author LaLa 定位选择城市
	 * 
	 */
	private Thread thread = null;

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
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
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
				System.out.println("定位地区数据加载完成");
				cityInit();
				setAdapter(allCity_lists, city_hot, city_history);
				getNowLocation();
				break;
			case CommonUtility.SERVEROK2:
				System.out.println("定位城市选择数据加载完成");
				break;
			case CommonUtility.KONG:
				if (ContactListActivity.this != null) {
					showToast("初始化列表失败，请重试");
				}
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 历史城市
	 */
	private void hisCityInit() {
	}

	/**
	 * 热门城市
	 */
	public void hotCityInit() {
		City city = new City("上海", "2");
		city_hot.add(city);
		city = new City("北京", "2");
		city_hot.add(city);
		city = new City("广州", "2");
		city_hot.add(city);
		city = new City("深圳", "2");
		city_hot.add(city);
		city = new City("武汉", "2");
		city_hot.add(city);
		city = new City("天津", "2");
		city_hot.add(city);
		city = new City("西安", "2");
		city_hot.add(city);
		city = new City("南京", "2");
		city_hot.add(city);
		city = new City("杭州", "2");
		city_hot.add(city);
		city = new City("成都", "2");
		city_hot.add(city);
		city = new City("重庆", "2");
		city_hot.add(city);
	}

	/**
	 * a-z排序
	 */
	@SuppressWarnings("rawtypes")
	Comparator comparator = new Comparator<City>() {
		@Override
		public int compare(City lhs, City rhs) {
			String a = lhs.getPinyi().substring(0, 1);
			String b = rhs.getPinyi().substring(0, 1);
			int flag = a.compareTo(b);
			if (flag == 0) {
				return a.compareTo(b);
			} else {
				return flag;
			}
		}
	};

	private void setAdapter(List<City> list, List<City> hotList, List<String> hisCity) {
		adapter = new ListAdapter(this, list, hotList, hisCity);
		personList.setAdapter(adapter);
	}

	/**
	 * 百度地图Start
	 * ----------------------------------------------------------------
	 */
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	public MyLocationListener mMyLocationListener;

	/**
	 * 获取当前城市
	 */
	public void getNowLocation() {
		// 百度地图
		mLocationClient = new LocationClient(getApplicationContext());
		// 放于MyLocationListener()之前
		InitLocation();

	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			Log.e("info", "city = " + arg0.getCity());
			if (!isNeedFresh) {
				return;
			}
			isNeedFresh = false;
			if (arg0.getCity() == null) {
				locateProcess = 3; // 定位失败
				personList.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				return;
			}
			currentCity = arg0.getCity().substring(0, arg0.getCity().length() - 1);
			locateProcess = 2; // 定位成功
			personList.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mLocationClient != null) {
			mLocationClient.stop();
		}
	}

	/**
	 * 百度地图END
	 * ------------------------------------------------------------------
	 */

	private class ResultListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ArrayList<City> results = new ArrayList<City>();

		public ResultListAdapter(Context context, ArrayList<City> results) {
			inflater = LayoutInflater.from(context);
			this.results = results;
		}

		@Override
		public int getCount() {
			return results.size();
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
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.contactlist_list_item, null);
				viewHolder = new ViewHolder();
				viewHolder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.name.setText(results.get(position).getName());
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					App.getInstance().setContactCity(results.get(position).getName());
					finish();
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView name;
		}
	}

	public class ListAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<City> list;
		private List<City> hotList;
		private List<String> hisCity;
		final int VIEW_TYPE = 4;

		public ListAdapter(Context context, List<City> list, List<City> hotList, List<String> hisCity) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
			this.context = context;
			this.hotList = hotList;
			this.hisCity = hisCity;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				// 当前汉语拼音首字母
				String currentStr = getAlpha(list.get(i).getPinyi());
				// 上一个汉语拼音首字母，如果不存在为" "
				String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getPinyi()) : " ";
				if (!previewStr.equals(currentStr)) {
					String name = getAlpha(list.get(i).getPinyi());
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getViewTypeCount() {
			return VIEW_TYPE;
		}

		@Override
		public int getItemViewType(int position) {
			return position < 3 ? position : 3;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final TextView city;
			int viewType = getItemViewType(position);
			if (viewType == 0) { // 定位
				convertView = inflater.inflate(R.layout.contactlist_frist_list_item, null);
				TextView locateHint = (TextView) convertView.findViewById(R.id.locateHint);
				city = (TextView) convertView.findViewById(R.id.lng_city);
				city.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (locateProcess == 2) {
							App.getInstance().setContactCity(city.getText().toString());
							if (resultList.getVisibility() == View.VISIBLE) {
								resultList.setVisibility(View.GONE);
							} else {
								finish();
							}
						} else if (locateProcess == 3) {
							locateProcess = 1;
							personList.setAdapter(adapter);
							adapter.notifyDataSetChanged();
							mLocationClient.stop();
							isNeedFresh = true;
							InitLocation();
							currentCity = "";
							mLocationClient.start();
						}
					}
				});
				ProgressBar pbLocate = (ProgressBar) convertView.findViewById(R.id.pbLocate);
				if (locateProcess == 1) { // 正在定位
					locateHint.setText("正在定位");
					city.setVisibility(View.GONE);
					pbLocate.setVisibility(View.VISIBLE);
				} else if (locateProcess == 2) { // 定位成功
					locateHint.setText("当前定位城市");
					city.setVisibility(View.VISIBLE);
					city.setText(currentCity);
					mLocationClient.stop();
					pbLocate.setVisibility(View.GONE);
				} else if (locateProcess == 3) {
					locateHint.setText("未定位到城市,请选择");
					city.setVisibility(View.VISIBLE);
					city.setText("重新选择");
					pbLocate.setVisibility(View.GONE);
				}
			} else if (viewType == 1) {
				convertView = inflater.inflate(R.layout.contactlist_recent_city, null);
				GridView hotCity = (GridView) convertView.findViewById(R.id.recent_city);
				hotCity.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						App.getInstance().setContactCity(city_hot.get(position).getName());
						if (resultList.getVisibility() == View.VISIBLE) {
							resultList.setVisibility(View.GONE);
						} else {
							finish();
						}
					}
				});
				hotCity.setAdapter(new HotCityAdapter(context, this.hotList));
				TextView hotHint = (TextView) convertView.findViewById(R.id.recentHint);
				hotHint.setText("热门城市");
			} else if (viewType == 2) {
				convertView = inflater.inflate(R.layout.contactlist_total_item, null);
			} else {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.contactlist_list_item, null);
					holder = new ViewHolder();
					holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
					holder.name = (TextView) convertView.findViewById(R.id.name);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (position >= 1) {
					holder.name.setText(list.get(position).getName());
					String currentStr = getAlpha(list.get(position).getPinyi());
					String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getPinyi()) : " ";
					if (!previewStr.equals(currentStr)) {
						holder.alpha.setVisibility(View.VISIBLE);
						holder.alpha.setText(currentStr);
					} else {
						holder.alpha.setVisibility(View.GONE);
					}
				}
			}
			return convertView;
		}

		private class ViewHolder {
			TextView alpha; // 首字母标题
			TextView name; // 城市名字
		}
	}

	class HotCityAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<City> hotCitys;

		public HotCityAdapter(Context context, List<City> hotCitys) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.hotCitys = hotCitys;
		}

		@Override
		public int getCount() {
			return hotCitys.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.contactlist_item_city, null);
			TextView city = (TextView) convertView.findViewById(R.id.city);
			city.setText(hotCitys.get(position).getName());
			return convertView;
		}
	}

	class HitCityAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		private List<String> hotCitys;

		public HitCityAdapter(Context context, List<String> hotCitys) {
			this.context = context;
			inflater = LayoutInflater.from(this.context);
			this.hotCitys = hotCitys;
		}

		@Override
		public int getCount() {
			return hotCitys.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = inflater.inflate(R.layout.contactlist_item_city, null);
			TextView city = (TextView) convertView.findViewById(R.id.city);
			city.setText(hotCitys.get(position));
			return convertView;
		}
	}

	private boolean mReady;

	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay() {
		mReady = true;
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.contactlist_overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private boolean isScroll = false;

	private class LetterListViewListener implements OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			isScroll = false;
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				personList.setSelection(position);
				overlay.setText(s);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1000);
			}
		}
	}

	// 设置overlay不可见
	private class OverlayThread implements Runnable {
		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}
	}

	// 获得汉语拼音首字母
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else if (str.equals("0")) {
			return "定位";
		} else if (str.equals("1")) {
			return "热门";
		} else if (str.equals("2")) {
			return "全部";
		} else {
			return "#";
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
			isScroll = true;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (!isScroll) {
			return;
		}
		if (firstVisibleItem > 2) {
			if (mReady) {
				String text;
				String name = allCity_lists.get(firstVisibleItem).getName();
				String pinyin = allCity_lists.get(firstVisibleItem).getPinyi();
				text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
				overlay.setText(text);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1000);
			}
		}
	}

}