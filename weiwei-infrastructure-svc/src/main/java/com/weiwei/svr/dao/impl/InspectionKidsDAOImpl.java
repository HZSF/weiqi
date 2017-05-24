package com.weiwei.svr.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.parent.common.base.Constants;
import com.weiwei.svr.dao.IInspectionKidsDAO;
import com.weiwei.svr.dbmodel.TableUserInspection;

@Service
public class InspectionKidsDAOImpl extends JdbcDaoSupport implements IInspectionKidsDAO {

	@Autowired
	public InspectionKidsDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public String insertNewKidsInspectionBooking(String username) {
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_inspection_map (userName, isApplied_kidsClothe) VALUES (?, ?)";
			getJdbcTemplate().update(sql_insert, new Object[]{username, "Y"});
			return Constants.EVENT_SUCCESS;
		}else{
			String sql_query1 = "SELECT * FROM user_inspection_map WHERE userName=? AND isApplied_kidsClothe='Y'";
			List<?> result1 = getJdbcTemplate().query(sql_query1, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
			if(result1 == null || result1.size() == 0){
				String sql_update = "UPDATE user_inspection_map SET isApplied_kidsClothe = ? WHERE userName = ?";
				getJdbcTemplate().update(sql_update, new Object[]{"Y", username});
				return Constants.EVENT_SUCCESS;
			}else{
				return Constants.EVENT_EXISTED;
			}
		}
	}

	public void cancelBookedKidsInspection(String username) {
		String sql_query = "SELECT * FROM user_inspection_map WHERE userName=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
		if(result != null && result.size() > 0){
			String sql_update = "UPDATE user_inspection_map SET isApplied_kidsClothe = ? WHERE userName = ?";
			getJdbcTemplate().update(sql_update, new Object[]{"N", username});
		}
	}

}
