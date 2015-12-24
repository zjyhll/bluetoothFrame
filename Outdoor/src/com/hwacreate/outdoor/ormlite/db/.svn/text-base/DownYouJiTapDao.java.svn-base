package com.hwacreate.outdoor.ormlite.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.hwacreate.outdoor.ormlite.bean.YoujiXiangqing;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;

/**
 * T代表YoujiXiangqing
 */
public class DownYouJiTapDao<T> extends BaseDao<T> {

	public DownYouJiTapDao(T t, Context context) {
		super(t, context);
	}

	/** 增加单个 */
	@Override
	public void add(T t) {
		// TODO Auto-generated method stub
		super.add(t);
	}

	/** 调用该方法增加所有当季热门活动 */
	@Override
	public void add(List<T> ts) {
		super.add(ts);
	}

	/** 有网刷新时删除所有，再存储新的数据 */
	@Override
	public void deleteAll() {
		super.deleteAll();
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
	 * @param select
	 *            * 的话顺序是不固定的，需参照数据库，像下面那样写的话可以对应来取，不用考虑数据库结构，对应来取便可
	 */
	public List<YoujiXiangqing> getList(int index, int count) {
		List<YoujiXiangqing> t = new ArrayList<YoujiXiangqing>();
		try {
			GenericRawResults<YoujiXiangqing> rawResults = tDao.queryRaw(
					"select footprint_data," + "act_end_time," + "tvl_title," + "picture_url," + "tvl_desc,"
							+ "anchor_longitude," + "act_id," + "anchor_latitude," + "team_ember," + "u_id,"
							+ "act_start_time," + "city," + "scenicsots," + "is_collect," + "distance," + "leader_id,"
							+ "team_number," + "act_type," + "leader_name," + "tvl_cover," + "tvl_type," + "tvl_id,"
							+ "trace_data," + "type," + "user_nickname," + "club_name," + "agreeCount," + "isAgree,"
							+ "team_member, " + "act_strategy," + "logistics, " + "act_join_num_limit,"
							+ " confirmed_member," + " viceleadercount, " + "malecount," + " femalecount,"
							+ " act_entry_fee," + " act_weixin," + " act_most_equip," + " act_level," + " tvl_else,"
							+ " club_logo," + " head," + "tvl_create_time,"
							+ "tvl_desc_intro from outdoor_tb_youjixiangqing" + " limit " + index + "," + count,
					new RawRowMapper<YoujiXiangqing>() {

						@Override
						public YoujiXiangqing mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
							return new YoujiXiangqing(resultColumns[0], resultColumns[1], resultColumns[2],
									resultColumns[3], resultColumns[4], resultColumns[5], resultColumns[6],
									resultColumns[7], resultColumns[8], resultColumns[9], resultColumns[10],
									resultColumns[11], resultColumns[12],
									Integer.parseInt(resultColumns[13] != null ? resultColumns[13] : "0"),
									Integer.parseInt(resultColumns[14] != null ? resultColumns[14] : "0"),
									resultColumns[15],
									Integer.parseInt(resultColumns[16] != null ? resultColumns[16] : "0"),
									resultColumns[17], resultColumns[18], resultColumns[19], resultColumns[20],
									resultColumns[21], resultColumns[22],
									Integer.parseInt(resultColumns[23] != null ? resultColumns[23] : "0"),
									resultColumns[24], resultColumns[25],
									Integer.parseInt(resultColumns[26] != null ? resultColumns[26] : "0"),
									Integer.parseInt(resultColumns[27] != null ? resultColumns[27] : "0"),
									resultColumns[28], resultColumns[39], resultColumns[30],
									Integer.parseInt(resultColumns[31] != null ? resultColumns[31] : "0"),
									Integer.parseInt(resultColumns[32] != null ? resultColumns[32] : "0"),
									Integer.parseInt(resultColumns[33] != null ? resultColumns[33] : "0"),
									Integer.parseInt(resultColumns[34] != null ? resultColumns[34] : "0"),
									Integer.parseInt(resultColumns[35] != null ? resultColumns[35] : "0"),
									resultColumns[36], resultColumns[37], resultColumns[38],
									Integer.parseInt(resultColumns[39] != null ? resultColumns[39] : "0"),
									resultColumns[40], resultColumns[41], resultColumns[42], resultColumns[43],
									resultColumns[44]);
						}

					});
			t = rawResults.getResults();
			return t;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
}
