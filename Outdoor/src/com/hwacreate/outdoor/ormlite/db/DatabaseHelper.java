package com.hwacreate.outdoor.ormlite.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hwacreate.outdoor.ormlite.bean.GpsInfo;
import com.hwacreate.outdoor.ormlite.bean.HomeTapDangjiBean;
import com.hwacreate.outdoor.ormlite.bean.HomeTapTopImage;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingItem;
import com.hwacreate.outdoor.ormlite.bean.HuoDongXiangQingLeader;
import com.hwacreate.outdoor.ormlite.bean.SignUpUser;
import com.hwacreate.outdoor.ormlite.bean.TuZhongUser;
import com.hwacreate.outdoor.ormlite.bean.User;
import com.hwacreate.outdoor.ormlite.bean.XieYoujiXiangqing;
import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.hwacreate.outdoor.ormlite.bean.ZhuZhiTapItemBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String TABLE_NAME = "outdoor.db";
	private static DatabaseHelper instance;

	private Map<String, Dao> daos = new HashMap<String, Dao>();

	private DatabaseHelper(Context context) {
		// 2015/12/4更新数据库
		super(context, TABLE_NAME, null, 18);
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, TuZhongUser.class);
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, SignUpUser.class);
			TableUtils.createTable(connectionSource, HuoDongXiangQingItem.class);
			TableUtils.createTable(connectionSource, HuoDongXiangQingLeader.class);
			TableUtils.createTable(connectionSource, YoujiXiangqing.class);
			TableUtils.createTable(connectionSource, HomeTapDangjiBean.class);
			TableUtils.createTable(connectionSource, ZhuZhiTapItemBean.class);
			TableUtils.createTable(connectionSource, HomeTapTopImage.class);
			TableUtils.createTable(connectionSource, GpsInfo.class);
			TableUtils.createTable(connectionSource, XieYoujiXiangqing.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, TuZhongUser.class, true);
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, SignUpUser.class, true);
			TableUtils.dropTable(connectionSource, HuoDongXiangQingItem.class, true);
			TableUtils.dropTable(connectionSource, HuoDongXiangQingLeader.class, true);
			TableUtils.dropTable(connectionSource, YoujiXiangqing.class, true);
			TableUtils.dropTable(connectionSource, HomeTapDangjiBean.class, true);
			TableUtils.dropTable(connectionSource, HomeTapTopImage.class, true);
			TableUtils.dropTable(connectionSource, ZhuZhiTapItemBean.class, true);
			TableUtils.dropTable(connectionSource, GpsInfo.class, true);
			TableUtils.dropTable(connectionSource, XieYoujiXiangqing.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DatabaseHelper getHelper(Context context) {
		context = context.getApplicationContext();
		if (instance == null) {
			synchronized (DatabaseHelper.class) {
				if (instance == null)
					instance = new DatabaseHelper(context);
			}
		}

		return instance;
	}

	public synchronized Dao getDao(Class clazz) throws SQLException {
		Dao dao = null;
		String className = clazz.getSimpleName();

		if (daos.containsKey(className)) {
			dao = daos.get(className);
		}
		if (dao == null) {
			dao = super.getDao(clazz);
			daos.put(className, dao);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close() {
		super.close();

		for (String key : daos.keySet()) {
			Dao dao = daos.get(key);
			dao = null;
		}
	}

}
