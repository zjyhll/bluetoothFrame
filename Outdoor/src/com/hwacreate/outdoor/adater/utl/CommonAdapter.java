package com.hwacreate.outdoor.adater.utl;

import java.util.List;

import com.hwacreate.outdoor.utl.MyLogger;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;
	// log
	public static MyLogger loghxd;

	public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
		loghxd = MyLogger.hxdLog();
	}

	// 给mDatas添加所有的数据
	public void addAll(List<T> mDatas) {
		this.mDatas.addAll(mDatas);
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	public List<T> getDatas() {
		return mDatas;
	}

	@Override
	public T getItem(int position) {
		return mDatas != null ? mDatas.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, final View convertView,
			ViewGroup parent) {
		final ViewHolderUntil viewHolder = getViewHolder(position, convertView,
				parent);
		convert(viewHolder, getItem(position), position);
		viewHolder.getConvertView().setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setOncliklisenter(v);
				setOncliklisenter1(v, position);
				setOncliklisenter2(v, getItem(position));
				setOncliklisenter3(v, getItem(position), position);
			}

		});
		return viewHolder.getConvertView();

	}

	public void setOncliklisenter(View v) {

	}

	public void setOncliklisenter1(View v, int position) {

	}

	public void setOncliklisenter2(View v, T item) {

	}

	public void setOncliklisenter3(View v, T item, int position) {

	}

	public abstract void convert(ViewHolderUntil helper, T item, int position);

	private ViewHolderUntil getViewHolder(int position, View convertView,
			ViewGroup parent) {
		return ViewHolderUntil.get(mContext, convertView, parent,
				mItemLayoutId, position);
	}

}
