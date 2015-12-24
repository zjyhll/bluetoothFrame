package com.hwacreate.outdoor.mainFragment.wode;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity_YouJi;
import me.nereo.multi_image_selector.bean.Image;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.utl.Bimp;
import com.hwacreate.outdoor.view.CleareditTextView;
import com.hwacreate.outdoor.view.Info;
import com.hwacreate.outdoor.view.PhotoView;
import com.hwacreate.outdoor.view.PickerView;
import com.hwacreate.outdoor.view.PickerView.onSelectListener;
import com.hwacreate.outdoor.R;

/**
 * @author LaLa 添加游记行程
 *
 */
public class XinJiaXingChengActivity extends BaseActivity {
	private CleareditTextView xingcheng_text = null;
	private LinearLayout xingcheng_date = null;
	private TextView xingcheng_date_show = null;
	private LinearLayout xingcheng_time = null;
	private TextView xingcheng_time_show = null;
	private View parentView = null;
	// 选择照片列表
	private int Count = 8;
	private GridView mGridView = null;
	private ImageGridAdapter mAdapter = null;
	private ArrayList<String> mSelectPath;
	// pop选择栏选择
	private PopupWindow popUser = null;
	private PickerView pickerviewleft = null;
	private TextView pickerviewtext = null;
	private PickerView pickerviewright = null;
	private PickerView pickerviewright_right = null;
	private TextView geren_qu = null;
	private TextView geren_wan = null;
	private LinearLayout geren_pop_view = null;
	private RelativeLayout geren_pop_image = null;
	// 存放的数据
	private List<String> datashi = null;
	private List<String> datafen = null;
	private List<String> datanian = null;
	private List<String> datayue = null;
	private List<String> datari = null;
	// 显示的数据
	private String shi = null;
	private String fen = null;
	private String nian = null;
	private String yue = null;
	private String ri = null;
	// 判断是哪一个pop
	private boolean isTime = false;
	private boolean isDate = false;
	private boolean isRuiNian = false;
	// 点击浏览大图
	private View mParent = null;
	private View mBg = null;
	private PhotoView mPhotoView = null;
	private Info mInfo = null;
	private AlphaAnimation in = new AlphaAnimation(0, 1);
	private AlphaAnimation out = new AlphaAnimation(1, 0);
	private TextView ok = null;
	private TextView delete = null;
	private Image tempImage = null;
	private int tempIndex = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_xinjiaxingcheng, null);
		setContentView(parentView);
		init();
	}

	protected void onRestart() {
		super.onRestart();
		mAdapter.update();
	}

	@Override
	public void onBackPressed() {
		if (mParent.getVisibility() == View.VISIBLE) {
			mBg.startAnimation(out);
			mPhotoView.animaTo(mInfo, new Runnable() {
				@Override
				public void run() {
					mParent.setVisibility(View.GONE);
				}
			});
		} else {
			FinishClear();
		}
	}

	private void FinishClear() {
		finish();
		mSelectPath.clear();
		Bimp.tempSelectBitmap.clear();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.top_itv_back:
			FinishClear();
			break;
		case R.id.top_tv_right:
			FinishClear();
			break;
		case R.id.xingcheng_date:
			onPopdatadate();
			break;
		case R.id.xingcheng_time:
			onPopdatatime();
			break;
		case R.id.geren_qu:
			popUser.dismiss();
			break;
		case R.id.geren_wan:
			popUser.dismiss();
			if (isTime) {
				if (shi != null && fen != null) {
					xingcheng_time_show.setText(shi + ":" + fen);
				} else if (shi == null && fen != null) {
					xingcheng_time_show.setText(datashi.get(0) + ":" + fen);
				} else if (shi != null && fen == null) {
					xingcheng_time_show.setText(shi + ":" + datafen.get(0));
				} else if (shi == null && fen == null) {
					xingcheng_time_show.setText(datashi.get(0) + ":" + datafen.get(0));
				}
			} else if (isDate) {
				if (nian != null && yue != null && ri != null) {
					xingcheng_date_show.setText(nian + yue + ri);
				} else if (nian != null && yue != null && ri == null) {
					xingcheng_date_show.setText(nian + yue + datari.get(0));
				} else if (nian != null && yue == null && ri == null) {
					xingcheng_date_show.setText(nian + datayue.get(0) + datari.get(0));
				} else if (nian == null && yue != null && ri == null) {
					xingcheng_date_show.setText(datanian.get(0) + yue + datari.get(0));
				} else if (nian == null && yue != null && ri != null) {
					xingcheng_date_show.setText(datanian.get(0) + yue + ri);
				} else if (nian == null && yue == null && ri != null) {
					xingcheng_date_show.setText(datanian.get(0) + datayue.get(0) + ri);
				} else if (nian != null && yue == null && ri != null) {
					xingcheng_date_show.setText(nian + datayue.get(0) + ri);
				} else if (nian == null && yue == null && ri == null) {
					xingcheng_date_show.setText(datanian.get(0) + datayue.get(0) + datari.get(0));
				}
			}
			break;
		case R.id.geren_pop_view:
			break;
		case R.id.geren_pop_image:
			popUser.dismiss();
			break;
		case R.id.bg_pic:
			if (mParent.getVisibility() == View.VISIBLE) {
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
			}
			break;
		case R.id.ok_pic:
			if (mParent.getVisibility() == View.VISIBLE) {
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
			}
			break;
		case R.id.delete_pic:
			if (mParent.getVisibility() == View.VISIBLE && tempImage != null && tempIndex != -1) {
				try {
					Bimp.tempSelectBitmap.remove(tempImage);
					mSelectPath.remove(tempImage.getPath());
					mAdapter.update();
				} catch (Exception e) {
				}
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
				tempImage = null;
				tempIndex = -1;
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onInitData() {
		initHeaderOther();
		initDate();
		onPopwindownbie();
		mSelectPath = new ArrayList<String>();
		in.setDuration(300);
		out.setDuration(300);
		mParent = findViewById(R.id.parent_pic);
		mBg = findViewById(R.id.bg_pic);
		ok = (TextView) findViewById(R.id.ok_pic);
		delete = (TextView) findViewById(R.id.delete_pic);
		mPhotoView = (PhotoView) findViewById(R.id.img_pic);
		mGridView = (GridView) findViewById(R.id.xingcheng_pic);
		xingcheng_text = (CleareditTextView) findViewById(R.id.xingcheng_text);
		xingcheng_date = (LinearLayout) findViewById(R.id.xingcheng_date);
		xingcheng_date_show = (TextView) findViewById(R.id.xingcheng_date_show);
		xingcheng_time = (LinearLayout) findViewById(R.id.xingcheng_time);
		xingcheng_time_show = (TextView) findViewById(R.id.xingcheng_time_show);
	}

	@Override
	protected void onResload() {
		top_tv_title.setText("添加行程");
		top_tv_right.setText("确定");
		mAdapter = new ImageGridAdapter();
		mGridView.setAdapter(mAdapter);
	}

	@Override
	protected void setMyViewClick() {
		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
		xingcheng_date.setOnClickListener(this);
		xingcheng_time.setOnClickListener(this);
		geren_qu.setOnClickListener(this);
		geren_wan.setOnClickListener(this);
		geren_pop_view.setOnClickListener(this);
		geren_pop_image.setOnClickListener(this);
		ok.setOnClickListener(this);
		delete.setOnClickListener(this);
		mBg.setOnClickListener(this);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				if (position == Bimp.tempSelectBitmap.size()) {
					Intent intent = new Intent(XinJiaXingChengActivity.this, MultiImageSelectorActivity_YouJi.class);
					// 是否显示拍摄图片
					intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SHOW_CAMERA, true);
					// 最大可选择图片数量
					intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SELECT_COUNT, Count);
					// 选择模式
					intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_SELECT_MODE,
							MultiImageSelectorActivity_YouJi.MODE_MULTI);
					// 多选数据
					if (mSelectPath != null && mSelectPath.size() > 0) {
						intent.putExtra(MultiImageSelectorActivity_YouJi.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
					}
					startActivityForResult(intent, REQUEST_IMAGE);
				} else {
					PhotoView p = (PhotoView) view;
					mInfo = p.getInfo();
					tempIndex = position;
					tempImage = Bimp.tempSelectBitmap.get(position);
					mPhotoView.setImageBitmap(tempImage.getBitmap());
					mBg.startAnimation(in);
					mParent.setVisibility(View.VISIBLE);
					mPhotoView.animaFrom(mInfo);
				}
			}
		});
		mPhotoView.enable();
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBg.startAnimation(out);
				mPhotoView.animaTo(mInfo, new Runnable() {
					@Override
					public void run() {
						mParent.setVisibility(View.GONE);
					}
				});
			}
		});
	}

	private void onPopwindownbie() {
		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件
		View view = inflater.inflate(R.layout.popwindow_usershezhi, null);
		// 初始化pop中的组件
		pickerviewleft = (PickerView) view.findViewById(R.id.pickerviewleft);
		pickerviewtext = (TextView) view.findViewById(R.id.pickerviewtext);
		pickerviewright = (PickerView) view.findViewById(R.id.pickerviewright);
		pickerviewright_right = (PickerView) view.findViewById(R.id.pickerviewright_right);
		geren_qu = (TextView) view.findViewById(R.id.geren_qu);
		geren_wan = (TextView) view.findViewById(R.id.geren_wan);
		geren_pop_view = (LinearLayout) view.findViewById(R.id.geren_pop_view);
		geren_pop_image = (RelativeLayout) view.findViewById(R.id.geren_pop_image);
		// 创建PopupWindow对象
		popUser = new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popUser.setBackgroundDrawable(new BitmapDrawable());
	}

	/** 得到选择的照片 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			if (resultCode == RESULT_OK) {
				mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity_YouJi.EXTRA_RESULT);
			}
		}
	}

	// 初始化数据
	private void initDate() {
		datashi = new ArrayList<String>();
		datafen = new ArrayList<String>();
		datanian = new ArrayList<String>();
		datayue = new ArrayList<String>();
		datari = new ArrayList<String>();
		for (int i = 0; i < 24; i++) {
			if (i < 10)
				datashi.add("0" + i);
			else
				datashi.add(i + "");
		}
		for (int i = 0; i < 60; i++) {
			if (i < 10)
				datafen.add("0" + i);
			else
				datafen.add(i + "");
		}
		for (int i = 2015; i < 2050; i++) {
			datanian.add(i + "年");
		}
		for (int i = 1; i < 13; i++) {
			datayue.add(i + "月");
		}
		for (int i = 1; i < 32; i++) {
			datari.add(i + "日");
		}
	}

	// 时间
	private void onPopdatatime() {
		isTime = true;
		isDate = false;
		popUser.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		pickerviewright.setVisibility(View.VISIBLE);
		pickerviewtext.setVisibility(View.VISIBLE);
		pickerviewright_right.setVisibility(View.GONE);
		pickerviewleft.setData(datashi);
		pickerviewright.setData(datafen);
		pickerviewleft.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				shi = text;
			}
		});
		pickerviewright.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				fen = text;
			}
		});
	}

	// 日期
	private void onPopdatadate() {
		isTime = false;
		isDate = true;
		popUser.showAtLocation(parentView, Gravity.CENTER, 0, 0);
		pickerviewtext.setVisibility(View.GONE);
		pickerviewright.setVisibility(View.VISIBLE);
		pickerviewright_right.setVisibility(View.VISIBLE);
		pickerviewleft.setData(datanian);
		pickerviewright.setData(datayue);
		pickerviewright_right.setData(datari);
		pickerviewleft.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				nian = text;
				String string = nian.subSequence(0, 4).toString();
				int year = Integer.valueOf(string).intValue();
				if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
					// 是闰年
					isRuiNian = true;
					if (yue != null) {
						if (yue.equals("2月")) {
							datari.clear();
							for (int i = 1; i < 30; i++) {
								datari.add(i + "日");
							}
							pickerviewright_right.setData(datari);
						}
					}
				} else {
					// 不是闰年
					isRuiNian = false;
					if (yue != null) {
						if (yue.equals("2月")) {
							datari.clear();
							for (int i = 1; i < 29; i++) {
								datari.add(i + "日");
							}
							pickerviewright_right.setData(datari);
						}
					}
				}
			}
		});
		pickerviewright.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				yue = text;
				if (yue.equals("2月") && !isRuiNian) {
					datari.clear();
					for (int i = 1; i < 29; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("2月") && isRuiNian) {
					datari.clear();
					for (int i = 1; i < 30; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("4月") || yue.equals("6月") || yue.equals("9月") || yue.equals("11月")) {
					datari.clear();
					for (int i = 1; i < 31; i++) {
						datari.add(i + "日");
					}
				} else if (yue.equals("1月") || yue.equals("3月") || yue.equals("5月") || yue.equals("7月")
						|| yue.equals("8月") || yue.equals("10月") || yue.equals("12月")) {
					datari.clear();
					for (int i = 1; i < 32; i++) {
						datari.add(i + "日");
					}
				}
				pickerviewright_right.setData(datari);
			}
		});
		pickerviewright_right.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				ri = text;
			}
		});
	}

	public class ImageGridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == Count) {
				return Count;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
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
		public View getView(int position, View view, ViewGroup parent) {
			PhotoView p = new PhotoView(XinJiaXingChengActivity.this);
			p.setLayoutParams(new AbsListView.LayoutParams(App.getInstance().getScreenWidth() / 5,
					App.getInstance().getScreenWidth() / 5));
			p.setScaleType(ImageView.ScaleType.FIT_XY);
			if (position == Bimp.tempSelectBitmap.size()) {
				p.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jiahao));
				if (position == Count) {
					p.setVisibility(View.GONE);
				}
			} else {
				p.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
				// 把PhotoView当普通的控件把触摸功能关掉
				p.disenable();
			}
			return p;
		}

		public void update() {
			loading();
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					mAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	}
}
