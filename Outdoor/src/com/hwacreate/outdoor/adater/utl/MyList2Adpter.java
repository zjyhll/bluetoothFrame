package com.hwacreate.outdoor.adater.utl;

import java.util.List;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.hwacreate.outdoor.view.CircleImageView;
import com.keyhua.outdoor.protocol.GetCollectActivityList.GetCollectActivityListResponsePayloadListItem;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;

/**
 * @author 曾金叶
 * @2015-8-6 @上午9:58:49 adapter
 */

public class MyList2Adpter extends BaseAdapter {
	private Context context = null;
	public List<GetCollectActivityListResponsePayloadListItem> mDatas = null;
	private ImageLoader imageLoader = null;
	private com.nostra13.universalimageloader.core.ImageLoader mImageLoader = null;
	private DisplayImageOptions options;

	public MyList2Adpter(Context context, List<GetCollectActivityListResponsePayloadListItem> list,
			ImageLoader imageLoader, com.nostra13.universalimageloader.core.ImageLoader mImageLoader,
			DisplayImageOptions options) {
		this.context = context;
		this.mDatas = list;
		this.imageLoader = imageLoader;
		this.mImageLoader = mImageLoader;
		this.options = options;
	}

	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_homelist, null);
			holder = new ViewHolder();
			holder.home_view = (RelativeLayout) convertView.findViewById(R.id.home_view);
			holder.iv_icon = (CircleImageView) convertView.findViewById(R.id.iv_icon);
			holder.icon = (CubeImageView) convertView.findViewById(R.id.icon);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_action = (TextView) convertView.findViewById(R.id.tv_action);
			holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.home_view.setLayoutParams(
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, App.getInstance().getScreenHeight() / 3));
		try {
			JSONArray jsonArray = new JSONArray(mDatas.get(position).getAct_logo());
			holder.icon.loadImage(imageLoader, jsonArray.getString(0));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		holder.tv_title.setText(mDatas.get(position).getAct_title());
		holder.tv_time.setText(TimeDateUtils.formatDateFromDatabaseTime(mDatas.get(position).getAct_start_time()));
		if (!TextUtils.isEmpty(mDatas.get(position).getClub())) {
			holder.tv_des.setText(mDatas.get(position).getClub());
			mImageLoader.displayImage(mDatas.get(position).getClub_head(), holder.iv_icon, options);
		} else if (!TextUtils.isEmpty(mDatas.get(position).getLeader())) {
			holder.tv_des.setText(mDatas.get(position).getLeader());
			mImageLoader.displayImage(mDatas.get(position).getLeader_head(), holder.iv_icon, options);
		}
		holder.tv_content.setText(mDatas.get(position).getAct_desc_intro());
		holder.tv_action.setText(mDatas.get(position).getAct_type());
		if (TextUtils.equals(mDatas.get(position).getAct_type(), "登山")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.dengshan));
		} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "徒步")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.tubu));
		} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "骑行")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.qixing));
		} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "自驾")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.zijia));
		} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "摄影")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.sheying));
		} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "休闲")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.xiuxian));
		} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "露营")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.luying));
		} else if (TextUtils.equals(mDatas.get(position).getAct_type(), "亲子")) {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.qinzi));
		} else {
			holder.tv_action.setTextColor(context.getResources().getColor(R.color.content));
		}
		switch (mDatas.get(position).getAct_state()) {
		case CommonUtility.BAOMINGZHONGInt:
			holder.tv_status.setText(CommonUtility.BAOMINGZHONGStr);
			holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
			break;
		case CommonUtility.ZHENGDUIInt:
			holder.tv_status.setText(CommonUtility.ZHENGDUIStr);
			holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
			break;
		case CommonUtility.ZHUNBEIInt:
			holder.tv_status.setText(CommonUtility.ZHUNBEIStr);
			holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
			break;
		case CommonUtility.CHUXINGInt:
			holder.tv_status.setText(CommonUtility.CHUXINGStr);
			holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
			break;
		case CommonUtility.DIANPINGInt:
			holder.tv_status.setText(CommonUtility.DIANPINGStr);
			holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
			break;
		case CommonUtility.JIESHUInt:
			holder.tv_status.setText(CommonUtility.JIESHUStr);
			holder.tv_status.setTextColor(context.getResources().getColor(R.color.content));
			break;
		}

		return convertView;
	}

	private class ViewHolder {
		private RelativeLayout home_view = null;
		private CircleImageView iv_icon;
		private CubeImageView icon;
		private TextView tv_title, tv_content, tv_des, tv_time, tv_action, tv_status;
	}
}
