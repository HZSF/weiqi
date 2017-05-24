package com.weiwei.svr.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IHealthyDAO;
import com.weiwei.svr.dbmodel.TableUserHealthcheck;
import com.weiwei.svr.utils.Constants;

@Service
public class HealthyDAOImpl extends JdbcDaoSupport implements IHealthyDAO {

	@Autowired
	public HealthyDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public String insertNewHeathCheckBooking(String username) {
		String sql_query = "SELECT * FROM user_healthcheck_map WHERE userName=? AND isApplied='Y'";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserHealthcheck.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_healthcheck_map (userName, isApplied) VALUES (?, ?)";
			getJdbcTemplate().update(sql_insert, new Object[]{username, "Y"});
			return Constants.EVENT_SUCCESS;
		}else{
			//String sql_update = "UPDATE user_healthcheck_map SET isApplied = ? WHERE userName = ?";
			//getJdbcTemplate().update(sql_update, new Object[]{"Y", username});
			return Constants.EVENT_EXISTED;
		}
	}

	public void cancelHealthCheckBooking(String username) {
		String sql_query = "SELECT * FROM user_healthcheck_map WHERE userName=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserHealthcheck.class));
		if(result != null && result.size() > 0){
			String sql_update = "UPDATE user_healthcheck_map SET isApplied = ? WHERE userName = ?";
			getJdbcTemplate().update(sql_update, new Object[]{"N", username});
		}
	}

	public boolean getHealthCheckIsApplied(String username) {
		String sql_query = "SELECT * FROM user_healthcheck_map WHERE userName=? AND isApplied = ?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username, "Y"}, new BeanPropertyRowMapper(TableUserHealthcheck.class));
		if(result != null && result.size() > 0){
			return true;
		}else{
			return false;
		}
	}

}
