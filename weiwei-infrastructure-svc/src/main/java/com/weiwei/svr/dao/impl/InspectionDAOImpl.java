package com.weiwei.svr.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IInspectionDAO;
import com.weiwei.svr.dbmodel.TableUserInspection;

@Service
public class InspectionDAOImpl extends JdbcDaoSupport implements IInspectionDAO {

	@Autowired
	public InspectionDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public List<TableUserInspection> getBookedInspectionByUsername(String username){
		String sql = "SELECT * FROM user_inspection_map WHERE userName=?";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableUserInspection.class));
	}
}
