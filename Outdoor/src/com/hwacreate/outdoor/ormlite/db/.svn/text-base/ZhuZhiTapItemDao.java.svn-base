package com.hwacreate.outdoor.ormlite.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.hwacreate.outdoor.ormlite.bean.ZhuZhiTapItemBean;
import com.hwacreate.outdoor.utl.CommonUtility;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;

/**
 * T代表ZhuZhiTapItemBean
 */
public class ZhuZhiTapItemDao<T> extends BaseDao<T> {

	public ZhuZhiTapItemDao(T t, Context context) {
		super(t, context);
	}

	/**
	 * 当没有网络时从数据库中获得数据 ， 测试时每次加载更多只增加2条数据， 数据库中每列顺序如下，跟构造函数中顺序不同，需注意：
	 * 
	 * @param content
	 *            0
	 * @param ways
	 *            1
	 * @param imgUrl
	 *            2
	 * @param status
	 *            3
	 * @param time
	 *            4
	 * @param tip
	 *            5
	 * @param title
	 *            6
	 * @param type
	 *            7
	 * @param limit用法为
	 *            ：第一个为下标（从第几个开始取），第二个参数为取几个（为固定值）
	 * @param offset
	 *            从第几个开始
	 */
	public List<ZhuZhiTapItemBean> getList(int type, int index, int count) {
		List<ZhuZhiTapItemBean> t = new ArrayList<ZhuZhiTapItemBean>();
		try {
			GenericRawResults<ZhuZhiTapItemBean> rawResults = tDao
					.queryRaw(
							"select imgUrl,tv_clob,tv_Province,tv_town,tv_chengyuan,tv_huodong,tv_join,type from tb_zhuzhitapitem where type = "
									+ type + " limit " + index + "," + count + "",
							new RawRowMapper<ZhuZhiTapItemBean>() {

								@Override
								public ZhuZhiTapItemBean mapRow(String[] columnNames, String[] resultColumns)
										throws SQLException {
									return new ZhuZhiTapItemBean(resultColumns[0], resultColumns[1], resultColumns[2],
											resultColumns[3], resultColumns[4], resultColumns[5], resultColumns[6],
											Integer.parseInt(resultColumns[7]));
								}

							});
			t = rawResults.getResults();
			return t;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
}
