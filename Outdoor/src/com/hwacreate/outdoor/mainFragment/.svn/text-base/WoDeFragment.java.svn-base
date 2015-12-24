package com.hwacreate.outdoor.mainFragment;

import java.util.ArrayList;
import java.util.List;

import org.afinal.simplecache.ACache;

import com.hwacreate.outdoor.R;
import com.hwacreate.outdoor.app.App;
import com.hwacreate.outdoor.base.BaseFragment;
import com.hwacreate.outdoor.client.JSONRequestSender;
import com.hwacreate.outdoor.login.LoginActivity;
import com.hwacreate.outdoor.mainFragment.wode.DownYouJiActivity;
import com.hwacreate.outdoor.mainFragment.wode.GenRenZiLiaoActivity;
import com.hwacreate.outdoor.mainFragment.wode.HaoYouActivity;
import com.hwacreate.outdoor.mainFragment.wode.HuoDongDingDanActivity;
import com.hwacreate.outdoor.mainFragment.wode.JuLeBuActivity;
import com.hwacreate.outdoor.mainFragment.wode.ShouHuoDongActivity;
import com.hwacreate.outdoor.mainFragment.wode.ShouYouJiActivity;
import com.hwacreate.outdoor.mainFragment.wode.XiaoXiActivity;
import com.hwacreate.outdoor.mainFragment.wode.XieYouJiActivity;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.hwacreate.outdoor.ormlite.db.BaseDao;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.hwacreate.outdoor.utl.FastBlur;
import com.hwacreate.outdoor.utl.ImageControl;
import com.hwacreate.outdoor.utl.NetUtil;
import com.hwacreate.outdoor.view.CircleImageView;
import com.hwacreate.outdoor.view.CustomDialog;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoRequest;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoRequestParameter;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoResponse;
import com.keyhua.outdoor.protocol.GetUserInfo.GetUserInfoResponsePayload;
import com.keyhua.outdoor.protocol.GetUserNotReadMessageCountAction.GetUserNotReadMessageCountRequest;
import com.keyhua.outdoor.protocol.GetUserNotReadMessageCountAction.GetUserNotReadMessageCountRequestParameter;
import com.keyhua.outdoor.protocol.GetUserNotReadMessageCountAction.GetUserNotReadMessageCountResponse;
import com.keyhua.outdoor.protocol.GetUserNotReadMessageCountAction.GetUserNotReadMessageCountResponsePayload;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutRequest;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutRequestParameter;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutResponse;
import com.keyhua.outdoor.protocol.UserAppLoginOut.UserAppLoginOutResponsePayload;
import com.keyhua.protocol.exception.ProtocolInvalidMessageException;
import com.keyhua.protocol.exception.ProtocolMissingFieldException;
import com.keyhua.protocol.json.JSONException;
import com.keyhua.protocol.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 曾金叶 我的
 * @2015-8-5 @上午10:15:40
 */
public class WoDeFragment extends BaseFragment {
	private TextView wode_dengji = null;// 队员等级
	private RatingBar wode_ratingbar = null;// 星星等级
	private CircleImageView wode_imageview = null;// 头像
	private ImageView wode_imageview_bac = null;// 头像
	private TextView wode_name = null;// 用户名
	private LinearLayout wode_haoyou = null;// 好友
	private LinearLayout wode_julebu = null;// 俱乐部
	private LinearLayout wode_huodong = null;// 活动订单
	private LinearLayout wode_xiaoxi = null;// 消息
	private RelativeLayout wode_xieyouji = null;// 我写的游记
	private RelativeLayout wode_shouhuodong = null;// 收藏的活动
	private RelativeLayout wode_shouyouji = null;// 收藏的游记
	private RelativeLayout wode_downloud = null;// 下载的游记
	private TextView wode_xieyouji_text = null;// 我写的游记
	private TextView wode_shouhuodong_text = null;// 收藏的活动
	private TextView wode_shouyouji_text = null;// 收藏的游记
	private TextView wode_downloud_text = null;// 下载的游记
	private FrameLayout ll_personinfo = null;
	private FrameLayout ll_noview = null;
	private TextView wode_denglu = null;
	// 消息提示
	private View wode_haoyou_xiaoxi = null;
	private View wode_julebu_xiaoxi = null;
	private View wode_huodong_xiaoxi = null;
	private View wode_xiaoxi_xiaoxi = null;
	// 用于数据的缓存
	private ACache mCache = null;
	private boolean isHeadUrl = false;// 判断是否在web端修改头像
	// 取得下载游记的数据库信息
	private List<YoujiXiangqing> mYoujiDatas = null;
	private YoujiXiangqing youjiXiangqing = null;
	private BaseDao<YoujiXiangqing> youjiBaseDao = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCache = ACache.get(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.mainfrag_wode, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!TextUtils.isEmpty(App.getInstance().getAut())) {
			ll_personinfo.setVisibility(View.VISIBLE);
			ll_noview.setVisibility(View.GONE);
			if (NetUtil.isNetworkAvailable(getActivity())) {
				sendAsyn();
				sendAsyn1();
			} else {
				setData();
				showToastNet();
			}
			mYoujiDatas = youjiBaseDao.queryAll();
			if (mYoujiDatas != null) {
				App.getInstance().setMyDownloadTvl(mYoujiDatas.size());
			}
			top_tv_right_wode.setText("退出");
			top_tv_right_wode.setVisibility(View.VISIBLE);
		} else {
			top_tv_right_wode.setText("");
			top_tv_right_wode.setVisibility(View.GONE);
			ll_personinfo.setVisibility(View.GONE);
			ll_noview.setVisibility(View.VISIBLE);
			wode_xieyouji_text.setText("0");
			wode_shouhuodong_text.setText("0");
			wode_shouyouji_text.setText("0");
			wode_downloud_text.setText("0");
		}
	}

	@Override
	public void onClick(View v) {
		if (!TextUtils.isEmpty(App.getInstance().getAut())) {
			Bundle bundle = new Bundle();
			switch (v.getId()) {
			case R.id.wode_haoyou:
				openActivity(HaoYouActivity.class, bundle);
				break;
			case R.id.wode_julebu:
				openActivity(JuLeBuActivity.class, bundle);
				break;
			case R.id.wode_huodong:
				openActivity(HuoDongDingDanActivity.class, bundle);
				break;
			case R.id.wode_xiaoxi:
				openActivity(XiaoXiActivity.class, bundle);
				break;
			case R.id.wode_xieyouji:
				openActivity(XieYouJiActivity.class, bundle);
				break;
			case R.id.wode_shouhuodong:
				openActivity(ShouHuoDongActivity.class, bundle);
				break;
			case R.id.wode_shouyouji:
				openActivity(ShouYouJiActivity.class, bundle);
				break;
			case R.id.wode_downloudyouji:
				openActivity(DownYouJiActivity.class, bundle);
				break;
			case R.id.ll_personinfo:
				openActivity(GenRenZiLiaoActivity.class);
				break;
			case R.id.top_tv_right_wode:
				showAlertDialog("是否退出登录?                   ", 1);
				break;

			default:
				break;
			}
		} else {
			showToastDengLu();
			openActivity(LoginActivity.class);
		}
	}

	/** Dialog */
	public void showAlertDialog(String title, final int index) {
		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
		builder.setCancelable(false);// 点击对话框外部不关闭对话框
		builder.setMessage(title);
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				switch (index) {
				case 1:
					showdialogtext("退出登录中...");
					initDao();
					Bitmap value = null;
					mCache.put("head_bitmap", value);
					mCache.put("blur_bitmap", value);
					App.getInstance().ClearAll();
					sendAsyn2();
					break;
				case 2:
					break;

				default:
					break;
				}
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	// 选取任务(队员)
	private HuoDongXiangQingItem huoDongXiangQing = null;
	private BaseDao<HuoDongXiangQingItem> huoDongXiangQingDuiyuanDao = null;
	// 选取任务(领队)
	private HuoDongXiangQingLeader huoDongXiangQingLeader = null;
	private BaseDao<HuoDongXiangQingLeader> huoDongXiangQingLeaderDao = null;

	/**
	 * 数据的初始化
	 */
	@SuppressLint("NewApi")
	public void initDao() {
		// 队员数据操作
		huoDongXiangQing = new HuoDongXiangQingItem();
		huoDongXiangQingDuiyuanDao = new BaseDao<HuoDongXiangQingItem>(huoDongXiangQing, getActivity());
		huoDongXiangQing = huoDongXiangQingDuiyuanDao.searchByActid(App.getInstance().getHuoDongId());
		// 领队
		huoDongXiangQingLeader = new HuoDongXiangQingLeader();
		huoDongXiangQingLeaderDao = new BaseDao<HuoDongXiangQingLeader>(huoDongXiangQingLeader, getActivity());
		huoDongXiangQingLeader = huoDongXiangQingLeaderDao.searchByActid(App.getInstance().getLeaderHuoDongId());
		if (huoDongXiangQingDuiyuanDao != null) {
			huoDongXiangQingDuiyuanDao.deleteAll();
		}
		if (huoDongXiangQingLeaderDao != null) {
			huoDongXiangQingLeaderDao.deleteAll();
		}
	}

	@Override
	protected void onInitData() {
		initMainHeader();
		ll_personinfo = (FrameLayout) getActivity().findViewById(R.id.ll_personinfo);
		ll_noview = (FrameLayout) getActivity().findViewById(R.id.ll_noview);
		wode_denglu = (TextView) getActivity().findViewById(R.id.wode_denglu);
		wode_dengji = (TextView) getActivity().findViewById(R.id.wode_duiyuan);
		wode_ratingbar = (RatingBar) getActivity().findViewById(R.id.wode_ratingbar);
		wode_imageview = (CircleImageView) getActivity().findViewById(R.id.wode_imageview);
		wode_imageview_bac = (ImageView) getActivity().findViewById(R.id.wode_imageview_bac);
		wode_name = (TextView) getActivity().findViewById(R.id.wode_name);
		wode_haoyou = (LinearLayout) getActivity().findViewById(R.id.wode_haoyou);
		wode_julebu = (LinearLayout) getActivity().findViewById(R.id.wode_julebu);
		wode_huodong = (LinearLayout) getActivity().findViewById(R.id.wode_huodong);
		wode_xiaoxi = (LinearLayout) getActivity().findViewById(R.id.wode_xiaoxi);
		wode_xieyouji = (RelativeLayout) getActivity().findViewById(R.id.wode_xieyouji);
		wode_shouhuodong = (RelativeLayout) getActivity().findViewById(R.id.wode_shouhuodong);
		wode_shouyouji = (RelativeLayout) getActivity().findViewById(R.id.wode_shouyouji);
		wode_downloud = (RelativeLayout) getActivity().findViewById(R.id.wode_downloudyouji);
		wode_xieyouji_text = (TextView) getActivity().findViewById(R.id.wode_xieyouji_text);
		wode_shouhuodong_text = (TextView) getActivity().findViewById(R.id.wode_shouhuodong_text);
		wode_shouyouji_text = (TextView) getActivity().findViewById(R.id.wode_shouyouji_text);
		wode_downloud_text = (TextView) getActivity().findViewById(R.id.wode_downloud_text);
		wode_haoyou_xiaoxi = (View) getActivity().findViewById(R.id.wode_haoyou_xiaoxi);
		wode_julebu_xiaoxi = (View) getActivity().findViewById(R.id.wode_julebu_xiaoxi);
		wode_huodong_xiaoxi = (View) getActivity().findViewById(R.id.wode_huodong_xiaoxi);
		wode_xiaoxi_xiaoxi = (View) getActivity().findViewById(R.id.wode_xiaoxi_xiaoxi);
	}

	@Override
	protected void onResload() {
		// 初始化取得下载游记的数据库
		youjiXiangqing = new YoujiXiangqing();
		mYoujiDatas = new ArrayList<YoujiXiangqing>();
		youjiBaseDao = new BaseDao<YoujiXiangqing>(youjiXiangqing, getActivity());
		mYoujiDatas = youjiBaseDao.queryAll();
		App.getInstance().setMyDownloadTvl(mYoujiDatas != null ? mYoujiDatas.size() : 0);
	}

	@Override
	protected void setMyViewClick() {
		top_tv_right_wode.setOnClickListener(this);
		wode_haoyou.setOnClickListener(this);
		ll_personinfo.setOnClickListener(this);
		wode_julebu.setOnClickListener(this);
		wode_huodong.setOnClickListener(this);
		wode_xiaoxi.setOnClickListener(this);
		wode_xieyouji.setOnClickListener(this);
		wode_shouhuodong.setOnClickListener(this);
		wode_shouyouji.setOnClickListener(this);
		wode_downloud.setOnClickListener(this);
		wode_denglu.setOnClickListener(this);
	}

	@Override
	protected void headerOrFooterViewControl() {

	}

	private Thread thread = null;

	public void sendAsyn() {
		thread = new Thread() {
			public void run() {
				Action();
			}
		};
		thread.start();
	}

	public void Action() {
		GetUserInfoRequest request = new GetUserInfoRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetUserInfoRequestParameter parameter = new GetUserInfoRequestParameter();
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
					GetUserInfoResponse response = new GetUserInfoResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetUserInfoResponsePayload payload = (GetUserInfoResponsePayload) response.getPayload();
					if (TextUtils.equals(payload.getHead(), App.getInstance().getHeadurl())) {
						isHeadUrl = true;
					}
					App.getInstance().setUserid(payload.getUserid());
					App.getInstance().setRealname(payload.getRealname());
					App.getInstance().setHeadurl(payload.getHead());
					App.getInstance().setNickname(payload.getNickname());
					App.getInstance().setEmail(payload.getEmail());
					App.getInstance().setQq(payload.getQq());
					App.getInstance().setMicroblog(payload.getMicroblog());
					App.getInstance().setSex(payload.getSex());
					App.getInstance().setBrith(payload.getBrith());
					App.getInstance().setPrivince(payload.getPrivince() != null ? payload.getPrivince() : 0);
					App.getInstance().setCity(payload.getCity() != null ? payload.getCity() : 0);
					App.getInstance().setCounty(payload.getCounty() != null ? payload.getCounty() : 0);
					App.getInstance().setZoneString(payload.getZoneString());
					App.getInstance().setAddress(payload.getAddress());
					App.getInstance().setEmergency_name(payload.getEmergency_name());
					App.getInstance().setEmergency_phone(payload.getEmergency_phone());
					App.getInstance().setPhonenum(payload.getPhonenum());
					App.getInstance().setYb(payload.getZipcode());
					App.getInstance().setGxqm(payload.getSignature());
					App.getInstance().setSfz(payload.getId_number());
					App.getInstance().setHz(payload.getPassport());
					App.getInstance().setJsz(payload.getDrivinglicense());
					App.getInstance().setBloodtype(payload.getBloodtype());
					App.getInstance().setRegisttime(payload.getRegisttime());
					App.getInstance().setUlevel(payload.getUlevel() != null ? payload.getUlevel() : 0);
					App.getInstance().setStar(payload.getStar() != null ? payload.getStar() : 0);
					App.getInstance().setIs_leader(payload.getIs_leader() != null ? payload.getIs_leader() : 0);
					App.getInstance().setVerify(payload.getVerify() != null ? payload.getVerify() : 0);
					App.getInstance().setMyCollectedAct(payload.getMyCollectedAct());
					App.getInstance().setMyCollectedTvl(payload.getMyCollectedTvl());
					App.getInstance().setMyWritedTvl(payload.getMyWritedTvl());
					App.getInstance().setFraction(payload.getFraction() != null ? payload.getFraction() : 0);
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	// 获得用户未读消息个数
	public void sendAsyn1() {
		thread = new Thread() {
			public void run() {
				Action1();
			}
		};
		thread.start();
	}

	private int isrecomment = 0; // 是否提醒（小红点），1：有，0：没有
	private int count = 10; // 新消息数量（小红点数字）

	public void Action1() {
		GetUserNotReadMessageCountRequest request = new GetUserNotReadMessageCountRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		GetUserNotReadMessageCountRequestParameter parameter = new GetUserNotReadMessageCountRequestParameter();
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
					GetUserNotReadMessageCountResponse response = new GetUserNotReadMessageCountResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					GetUserNotReadMessageCountResponsePayload payload = (GetUserNotReadMessageCountResponsePayload) response
							.getPayload();
					count = payload.getCount();
					isrecomment = payload.getIsrecomment();
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	public void sendAsyn2() {
		thread = new Thread() {
			public void run() {
				Action2();
			}
		};
		thread.start();
	}

	public void Action2() {
		UserAppLoginOutRequest request = new UserAppLoginOutRequest();
		request.setAuthenticationToken(App.getInstance().getAut());
		UserAppLoginOutRequestParameter parameter = new UserAppLoginOutRequestParameter();
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
					UserAppLoginOutResponse response = new UserAppLoginOutResponse();
					try {
						response.fromJSONString(responseObject.toString());
					} catch (ProtocolInvalidMessageException e) {
						e.printStackTrace();
					} catch (ProtocolMissingFieldException e) {
						e.printStackTrace();
					}
					// 处理返回的参数，需要强制类型转换
					UserAppLoginOutResponsePayload payload = (UserAppLoginOutResponsePayload) response.getPayload();
					State = payload.getRegistState();
					msgString = payload.getResult();
					handlerlist.sendEmptyMessage(CommonUtility.SERVEROK3);
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
			handlerlist.sendEmptyMessage(CommonUtility.SERVERERROR);
		}
	}

	private void setData() {
		if (!TextUtils.isEmpty(App.getInstance().getHeadurl())) {
			if (mCache.getAsBitmap("head_bitmap") != null && !isHeadUrl) {
				wode_imageview.setImageBitmap(mCache.getAsBitmap("head_bitmap"));
			} else {
				new Thread(downloadHead).start();
			}
			if (mCache.getAsBitmap("blur_bitmap") != null && !isHeadUrl) {
				wode_imageview_bac.setImageBitmap(mCache.getAsBitmap("blur_bitmap"));
			} else {
				new Thread(downloadRun).start();
			}
		}
		if (!TextUtils.isEmpty(App.getInstance().getNickname())) {
			wode_name.setText(App.getInstance().getNickname());
		}
		wode_ratingbar.setRating(App.getInstance().getStar());
		int fraction = App.getInstance().getFraction();
		if (fraction >= 0 && fraction < 20) {
			wode_dengji.setText("新手");
		} else if (fraction >= 20 && fraction < 100) {
			wode_dengji.setText("初级队员");
		} else if (fraction >= 100 && fraction < 200) {
			wode_dengji.setText("中级队员");
		} else if (fraction >= 200 && fraction < 500) {
			wode_dengji.setText("高级队员");
		} else if (fraction >= 500) {
			wode_dengji.setText("大师");
		}
		wode_xieyouji_text.setText(App.getInstance().getMyWritedTvl() + "");
		wode_shouhuodong_text.setText(App.getInstance().getMyCollectedAct() + "");
		wode_shouyouji_text.setText(App.getInstance().getMyCollectedTvl() + "");
		wode_downloud_text.setText(App.getInstance().getMyDownloadTvl() + "");
	}

	// 服务器返回提示信息
	private String msgString = null;
	private int State = 0;

	@SuppressLint("HandlerLeak")
	Handler handlerlist = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonUtility.SERVEROK1:
				setData();
				break;
			case CommonUtility.SERVEROK2:
				if (isrecomment == 1) {
					wode_xiaoxi_xiaoxi.setVisibility(View.VISIBLE);
				} else {
					wode_xiaoxi_xiaoxi.setVisibility(View.GONE);
				}
				break;
			case CommonUtility.SERVEROK3:
				if (State == 0) {
					if (isshowdialog()) {
						closedialog();
					}
					App.getInstance().setAut("");
					showToast(msgString);
					StartTouXiang();
				} else {
					showToast(msgString);
				}
				if (isshowdialog()) {
					closedialog();
				}
				break;
			case CommonUtility.SERVERERRORLOGIN:
				showToastLogin();
				App.getInstance().setAut("");
				ll_personinfo.setVisibility(View.GONE);
				ll_noview.setVisibility(View.VISIBLE);
				wode_xieyouji_text.setText("0");
				wode_shouhuodong_text.setText("0");
				wode_shouyouji_text.setText("0");
				wode_downloud_text.setText("0");
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

	private Bitmap bitmap = null;
	/**
	 * 下载线程
	 */
	Runnable downloadRun = new Runnable() {

		@Override
		public void run() {
			try {
				bitmap = ImageControl.getHttpBitmap(App.getInstance().getHeadurl());
				handler.sendEmptyMessage(1);
			} catch (Exception e) {
			}
		}
	};
	Runnable downloadHead = new Runnable() {

		@Override
		public void run() {
			try {
				bitmap = ImageControl.getHttpBitmap(App.getInstance().getHeadurl());
				handler.sendEmptyMessage(2);
			} catch (Exception e) {
			}
		}
	};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				wode_imageview_bac.setImageBitmap(bitmap);
				applyBlur();
				break;
			case 2:
				wode_imageview.setImageBitmap(bitmap);
				mCache.put("head_bitmap", bitmap);
				break;

			default:
				break;
			}
		}
	};

	private void applyBlur() {
		wode_imageview_bac.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				wode_imageview_bac.getViewTreeObserver().removeOnPreDrawListener(this);
				wode_imageview_bac.buildDrawingCache();
				Bitmap bmp = wode_imageview_bac.getDrawingCache();
				blur(bmp, wode_imageview_bac);
				return true;
			}
		});
	}

	private void blur(Bitmap bkg, ImageView view) {
		Bitmap overlay = null;
		try {
			float scaleFactor = 8;
			float radius = 2;
			overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth() / scaleFactor),
					(int) (view.getMeasuredHeight() / scaleFactor), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(overlay);
			canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
			canvas.scale(1 / scaleFactor, 1 / scaleFactor);
			Paint paint = new Paint();
			paint.setFlags(Paint.FILTER_BITMAP_FLAG);
			canvas.drawBitmap(bkg, 0, 0, paint);
			overlay = FastBlur.doBlur(overlay, (int) radius, true);
			view.setImageBitmap(overlay);
			mCache.put("blur_bitmap", overlay);
		} catch (Exception e) {
			mCache.put("blur_bitmap", overlay);
		}
	}
}
