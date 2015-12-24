package com.hwacreate.outdoor.leftFragment.gongju;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hwacreate.outdoor.adater.utl.CommonAdapter;
import com.hwacreate.outdoor.adater.utl.ViewHolderUntil;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseActivity;
import com.hwacreate.outdoor.bean.HuoDongXiangQingDongTai;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.TimeDateUtils;
import com.keyhua.outdoor.protocol.DeleteActHistoryRecordByidAction.DeleteActHistoryRecordByidRequest;
import com.keyhua.outdoor.protocol.DeleteActHistoryRecordByidAction.DeleteActHistoryRecordByidRequestParameter;
import com.keyhua.outdoor.protocol.DeleteActHistoryRecordByidAction.DeleteActHistoryRecordByidResponse;
import com.keyhua.outdoor.protocol.DeleteActHistoryRecordByidAction.DeleteActHistoryRecordByidResponsePayload;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListHistorylistItem;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListRequest;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListRequestParameter;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListResponse;
import com.keyhua.outdoor.protocol.GetActHistoryListAction.GetActHistoryListResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONArray;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;
import com.hwacreate.outdoor.R;

public class DisplayDongtaiActivity extends BaseActivity {
	private ListView typeraiders_listview = null;
	private List<HuoDongXiangQingDongTai> HuoDongXiangQingDongTaiBeans = null;
	private HuoDongXiangQingDongTai HuoDongXiangQingDongTaiBean = null;
	private CommonAdapter<HuoDongXiangQingDongTai> listadapter1 = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_dongtai);
		initHeaderOther();
		init();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.top_itv_back:
			finish();
			break;
		case R.id.top_tv_right:
			openActivity(ShiShiDongtaiActivity.class);
			break;
		case R.id.haoyou_tianjia:// 删除
			break;

		default:
			break;
		}
	}

	@Override
	protected void onStart() {

		super.onStart();
		sendActHistoryListAsyn();
	}

	@Override
	protected void onInitData() {
		typeraiders_listview = (ListView) findViewById(R.id.typeraiders_listview);
	}

	@Override
	protected void onResload() {

		top_tv_title.setText("实时动态");
		top_tv_right.setText("发布动态");

	}

	@Override
	protected void setMyViewClick() {

		top_itv_back.setOnClickListener(this);
		top_tv_right.setOnClickListener(this);
	}

	private List<GetActHistoryListHistorylistItem> mHistoryList = null;

	// 取得历史
	private Thread thread = null;

	public void sendActHistoryListAsyn() {
		thread = new Thread() {
			public void run() {
				getActHistoryListAction();
			}
		};
		thread.start();
	}

	public void getActHistoryListAction() {
		GetActHistoryListRequest request = new GetActHistoryListRequest();
		GetActHistoryListRequestParameter parameter = new GetActHistoryListRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setAct_id(App.getInstance().getLeaderHuoDongId());
		request.setParameter(parameter);
		String requestString = null;
		try {
			requestString = request.toJSONString();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(requestString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					GetActHistoryListResponse response = new GetActHistoryListResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					GetActHistoryListResponsePayload payload = (GetActHistoryListResponsePayload) response.getPayload();
					this.mHistoryList = payload.getHistorylistList();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK1);
				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
				}
			} catch (JSONException e1) {

				e1.printStackTrace();
			}
		} else {
			handlerlist.sendEmptyMessage(CommonUtility.KONG);
		}
	}

	// 删除当前实时动态
	public void sendDeleteActHistoryRecordByidAsyn() {
		thread = new Thread() {
			public void run() {
				DeleteActHistoryRecordByidAction();
			}
		};
		thread.start();
	}

	private String hty_recd_id = "";
	// 删除是否成功
	private Integer state = 0;

	public void DeleteActHistoryRecordByidAction() {
		DeleteActHistoryRecordByidRequest request = new DeleteActHistoryRecordByidRequest();
		DeleteActHistoryRecordByidRequestParameter parameter = new DeleteActHistoryRecordByidRequestParameter();
		request.setAuthenticationToken(App.getInstance().getAut());
		parameter.setAct_id(App.getInstance().getLeaderHuoDongId());
		parameter.setHty_recd_id(hty_recd_id);
		request.setParameter(parameter);
		String requestString = null;
		try {
			requestString = request.toJSONString();
		} catch (ProtocolMissingFieldException e) {
			e.printStackTrace();
		}
		String requestUrl = CommonUtility.URL;
		JSONRequestSender sender = new JSONRequestSender(requestUrl);
		JSONObject responseObject = null;
		try {
			responseObject = sender.send(new JSONObject(requestString));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (responseObject != null) {
			try {
				int ret = responseObject.getInt("ret");
				if (ret == 0) {
					DeleteActHistoryRecordByidResponse response = new DeleteActHistoryRecordByidResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					DeleteActHistoryRecordByidResponsePayload payload = (DeleteActHistoryRecordByidResponsePayload) response
							.getPayload();
					state = payload.getState();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK2);

				} else if (ret == 500) {
					handlerlist.sendEmptyMessage(CommonUtility.KONG);
				} else if (ret == 5011) {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERRORLOGIN);
				} else {
					handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
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
				HuoDongXiangQingDongTaiBeans = new ArrayList<HuoDongXiangQingDongTai>();
				if (mHistoryList != null && mHistoryList.size() != 0) {
					for (int i = 0; i < mHistoryList.size(); i++) {
						HuoDongXiangQingDongTaiBean = new HuoDongXiangQingDongTai();
						HuoDongXiangQingDongTaiBean.setAct_id(mHistoryList.get(i).getAct_id());
						HuoDongXiangQingDongTaiBean.setArrive_lng(mHistoryList.get(i).getArrive_lng());
						HuoDongXiangQingDongTaiBean.setArrive_time(mHistoryList.get(i).getArrive_time());
						HuoDongXiangQingDongTaiBean.setDescript(mHistoryList.get(i).getDescript());
						HuoDongXiangQingDongTaiBean.setHty_recd_id(mHistoryList.get(i).getHty_recd_id());
						HuoDongXiangQingDongTaiBean.setImages(mHistoryList.get(i).getImages());
						HuoDongXiangQingDongTaiBean.setRarrive_lag(mHistoryList.get(i).getRarrive_lag());
						HuoDongXiangQingDongTaiBeans.add(HuoDongXiangQingDongTaiBean);
					}
					listadapter1 = new CommonAdapter<HuoDongXiangQingDongTai>(DisplayDongtaiActivity.this,
							HuoDongXiangQingDongTaiBeans, R.layout.item_huodong_dongtai_control) {

						@Override
						public void convert(ViewHolderUntil helper, final HuoDongXiangQingDongTai item, int position) {
							helper.getView(R.id.haoyou_tianjia).setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {

									hty_recd_id = item.getHty_recd_id();
									sendDeleteActHistoryRecordByidAsyn();
								}
							});
							helper.setText(R.id.dongtai_time,
									TimeDateUtils.formatDateFromDatabaseTimeSF(item.getArrive_time()));
							if (position == 0) {
								helper.getView(R.id.dongtai_zuixin).setVisibility(View.VISIBLE);
							} else {
								helper.getView(R.id.dongtai_zuixin).setVisibility(View.GONE);
							}
							helper.setText(R.id.dongtai_des, item.getDescript());
							try {
								JSONArray jsonArray = new JSONArray(item.getImages());
								if (jsonArray != null) {
									switch (jsonArray.length()) {
									case 1:
										helper.setCubeImageByUrl(R.id.icon1,
												jsonArray.getJSONObject(0).getString("fullurl"), imageLoader);
										helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon2).setVisibility(View.INVISIBLE);
										helper.getView(R.id.icon3).setVisibility(View.INVISIBLE);
										helper.getView(R.id.icon4).setVisibility(View.INVISIBLE);
										helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
										break;
									case 2:
										helper.setCubeImageByUrl(R.id.icon1,
												jsonArray.getJSONObject(0).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon2,
												jsonArray.getJSONObject(1).getString("fullurl"), imageLoader);
										helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon3).setVisibility(View.INVISIBLE);
										helper.getView(R.id.icon4).setVisibility(View.INVISIBLE);
										helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
										break;
									case 3:
										helper.setCubeImageByUrl(R.id.icon1,
												jsonArray.getJSONObject(0).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon2,
												jsonArray.getJSONObject(1).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon3,
												jsonArray.getJSONObject(2).getString("fullurl"), imageLoader);
										helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon3).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon4).setVisibility(View.INVISIBLE);
										helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
										break;
									case 4:
										helper.setCubeImageByUrl(R.id.icon1,
												jsonArray.getJSONObject(0).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon2,
												jsonArray.getJSONObject(1).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon3,
												jsonArray.getJSONObject(2).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon4,
												jsonArray.getJSONObject(3).getString("fullurl"), imageLoader);
										helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon3).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon4).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon5).setVisibility(View.INVISIBLE);
										break;
									case 5:
										helper.setCubeImageByUrl(R.id.icon1,
												jsonArray.getJSONObject(0).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon2,
												jsonArray.getJSONObject(1).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon3,
												jsonArray.getJSONObject(2).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon4,
												jsonArray.getJSONObject(3).getString("fullurl"), imageLoader);
										helper.setCubeImageByUrl(R.id.icon5,
												jsonArray.getJSONObject(4).getString("fullurl"), imageLoader);
										helper.getView(R.id.icon1).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon2).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon3).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon4).setVisibility(View.VISIBLE);
										helper.getView(R.id.icon5).setVisibility(View.VISIBLE);
										break;

									default:
										break;
									}
								}
							} catch (JSONException e) {
							}
						}

					};
					typeraiders_listview.setAdapter(listadapter1);
				} else {
					// content.setVisibility(View.VISIBLE);
					// content.setText("无");
				}
				break;
			case CommonUtility.SERVEROK2:
				if (state == 1) {// 成功
					HuoDongXiangQingDongTaiBeans.clear();
					listadapter1.notifyDataSetChanged();
					showToast("删除成功");
					sendActHistoryListAsyn();
				} else {
					showToast("删除失败");
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				openActivity(LoginActivity.class);
				break;
			case CommonUtility.SERVERERROR:
				break;
			case CommonUtility.KONG:
				break;
			default:
				break;
			}
		};
	};

}
