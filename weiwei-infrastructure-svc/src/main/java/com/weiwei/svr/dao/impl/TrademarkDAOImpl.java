package com.weiwei.svr.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.ITrademarkDAO;
import com.weiwei.svr.dbmodel.TableAchievementTransform;
import com.weiwei.svr.dbmodel.TableTrademarkMonitor;
import com.weiwei.svr.dbmodel.TableTrademarkTrade;

@Service
public class TrademarkDAOImpl extends JdbcDaoSupport implements ITrademarkDAO {
	
	@Autowired
	public TrademarkDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}

	public void addTrademarkMonitor(int userId, String regNum, int categoryNum, String name) {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO trademark_monitor (customer_id, register_id, category_number, name) VALUES (?, ?, ?, ?)";
		getJdbcTemplate().update(sql_insert, new Object[]{userId, regNum, categoryNum, name});
	}

	public List<?> findTrademarkMonitor(int userId, String regNum) {
		// TODO Auto-generated method stub
		String sql = "SELECT * From trademark_monitor WHERE customer_id=? AND register_id=? AND is_deleted IS NULL";
		return getJdbcTemplate().query(sql, new Object[]{userId, regNum}, new BeanPropertyRowMapper(TableTrademarkMonitor.class));
	}

	public List<?> findTrademarkMonitorByUserId(int userId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * From trademark_monitor WHERE customer_id=? AND is_deleted IS NULL";
		return getJdbcTemplate().query(sql, new Object[]{userId}, new BeanPropertyRowMapper(TableTrademarkMonitor.class));
	}

	public void cancelTrademarkMonitor(int userId, String regNum) {
		// TODO Auto-generated method stub
		String sql = "UPDATE trademark_monitor SET is_deleted='1' WHERE register_id=? AND customer_id=? AND is_deleted IS NULL";
		getJdbcTemplate().update(sql, regNum, userId);
	}

	public void addTrademarkSell(int userId, String regNum, int categoryNum, String name, int priceAsk, Timestamp date) {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO trademark_trade (customer_id, register_id, category_number, name, price_ask, submit_date) VALUES (?, ?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql_insert, new Object[]{userId, regNum, categoryNum, name, priceAsk, date});
	}

	public List<?> findTrademarkTrade(int userId, String regNum) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM trademark_trade WHERE customer_id=? AND register_id=? AND is_deleted IS NULL";
		return getJdbcTemplate().query(sql, new Object[]{userId, regNum}, new BeanPropertyRowMapper(TableTrademarkTrade.class));
	}
	
	public List<?> findTrademarkTradeLimitedNumbers(int n) {
		String sql = "SELECT * FROM trademark_trade WHERE is_deleted IS NULL ORDER BY id ASC LIMIT " + n;
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(TableTrademarkTrade.class));
	}

	public List<?> findTrademarkTradeByStartIdLimitedNumbers(int startId, int n) {
		String sql = "SELECT * FROM trademark_trade WHERE id > ? AND is_deleted IS NULL ORDER BY id ASC LIMIT " + n;
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(startId)}, new BeanPropertyRowMapper(TableTrademarkTrade.class));
	}

}
