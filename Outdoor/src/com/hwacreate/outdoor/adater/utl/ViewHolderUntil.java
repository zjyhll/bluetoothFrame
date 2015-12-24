package com.hwacreate.outdoor.adater.utl;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;

public class ViewHolderUntil {
	// 声明一个类似于Map的容器，用来存放ViewHolder中的View控件
	private final SparseArray<View> mViews;
	// 声明一个View 类型的ConvertVIew,用来返回adapter中用来显示listView的Item
	private View mConvertView;
	private int mPosition;
	private Context mContext;
	private DisplayImageOptions options;

	public ViewHolderUntil(Context context, ViewGroup parent, int layoutId, int position) {
		// TODO Auto-generated constructor stub
		this.mViews = new SparseArray<View>();
		// 通过传入item的id，以及从getView中传入的父布局，来构造一个ConvertView
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.mPosition = position;
		this.mContext = context;
		// 给convertview设置viewholder
		mConvertView.setTag(this);
		options = new DisplayImageOptions.Builder()
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.dmbg_default)
				// 设置图片URL为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.dmbg_default)
				// 设置图片加载或解码过程中发生错误显示的图片
				.displayer(new RoundedBitmapDisplayer(4)) // 设置成圆角图片
				.displayer(new FadeInBitmapDisplayer(100)).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.build(); // 创建配置过得DisplayImageOption对象
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolderUntil get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {

		if (convertView == null) {
			return new ViewHolderUntil(context, parent, layoutId, position);
		}
		return (ViewHolderUntil) convertView.getTag();
	}

	/**
	 * 通过控件的Id获取对应的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {

		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 返回ConvertVIew
	 * 
	 * @return ConvertView
	 */
	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolderUntil setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolderUntil setTexthtml(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(Html.fromHtml(text));
		return this;
	}

	/**
	 * 为TextView设置颜色
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolderUntil setTextColor(int viewId, int colorid) {
		TextView view = getView(viewId);
		view.setTextColor(colorid);
		return this;
	}

	/**
	 * 为TextView设置图片
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolderUntil setTextDrawables(int viewId, int left, int top, int right, int bottom) {
		TextView view = getView(viewId);
		view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
		return this;
	}

	/**
	 * 为MylistView设置适配器
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setMylistViewAdpater(int viewId, BaseAdapter adapter) {
		MyListView view = getView(viewId);
		view.setAdapter(adapter);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为CubeImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setCubeImageResource(int viewId, int drawableId) {
		CubeImageView view = getView(viewId);
		view.setImageResource(drawableId);
		return this;
	}

	/**
	 * 为CubeImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setCubeImageBitmap(int viewId, Bitmap bm) {
		CubeImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为CubeImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setCubeImageByUrlSD(int viewId, String url,
			com.nostra13.universalimageloader.core.ImageLoader mLoader) {
		ImageView view = getView(viewId);
		mLoader.displayImage("file://" + url, view, options);
		return this;
	}

	/**
	 * 为CubeImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setCubeImageByUrl(int viewId, String url, ImageLoader imageLoader) {
		CubeImageView view = getView(viewId);
		view.loadImage(imageLoader, url);
		return this;
	}

	/**
	 * 为CubeImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setCubeImageByUrlLoader(int viewId, String url,
			com.nostra13.universalimageloader.core.ImageLoader mLoader) {
		ImageView view = getView(viewId);
		mLoader.displayImage(url, view, options);
		return this;
	}

	/**
	 * 为CubeImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setCubeImageByUrlSDXQ(int viewId, String url,
			com.nostra13.universalimageloader.core.ImageLoader mLoader) {
		ImageView view = getView(viewId);
		if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
			mLoader.displayImage("file://" + url, view, options);
		} else {
			mLoader.displayImage("drawable://" + R.drawable.loadingnot, view, options);
		}
		return this;
	}

	/**
	 * 为CubeImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolderUntil setCubeImageByUrlXQ(int viewId, String url, ImageLoader imageLoader,
			com.nostra13.universalimageloader.core.ImageLoader mLoader) {
		CubeImageView view = getView(viewId);
		if (NetUtil.isWIFIConnected(mContext) || !App.getInstance().isTb_wifi()) {
			view.loadImage(imageLoader, url);
		} else {
			mLoader.displayImage("drawable://" + R.drawable.loadingnot, view, options);
		}
		return this;
	}

	public int getPosition() {
		return mPosition;
	}

}
