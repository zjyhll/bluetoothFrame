package com.hwacreate.outdoor.mainFragment.youji;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.view.PhotoView;
import com.hwacreate.outdoor.R;

/**
 * Created by liuheng on 2015/8/19.
 */
public class ViewPagerActivity extends BaseActivity {

	private ViewPager mPager;
	private ArrayList<String> imageList = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		imageList = getIntent().getExtras().getStringArrayList("imageurl");
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
		mPager.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return 1;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				PhotoView view = new PhotoView(ViewPagerActivity.this);
				view.enable();
				view.setLayoutParams(
						new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				if (imageList != null) {
					mImageLoader.displayImage(imageList.get(position), view, options);
					container.addView(view);

				}

				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onInitData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResload() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setMyViewClick() {
		// TODO Auto-generated method stub

	}
}
