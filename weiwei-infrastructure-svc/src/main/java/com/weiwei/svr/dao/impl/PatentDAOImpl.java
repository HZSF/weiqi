package com.weiwei.svr.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IPatentDAO;
import com.weiwei.svr.dbmodel.TableAchievementTransform;
import com.weiwei.svr.dbmodel.TableAnnualFeeDetail;
import com.weiwei.svr.dbmodel.TableAnnualFeeMonitor;

@Service
public class PatentDAOImpl extends JdbcDaoSupport implements IPatentDAO{

	@Autowired
	public PatentDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public List<?> findAnnualFeeMonitorByCustomerID(int id) {
		String sql = "SELECT * FROM annual_fee_monitor WHERE customer_id=? AND is_deleted IS NULL";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(id)}, new BeanPropertyRowMapper(TableAnnualFeeMonitor.class));
	}
	
	public List<?> findAnnualFeeMonitor(int id, String patentId) {
		String sql = "SELECT * FROM annual_fee_monitor WHERE customer_id=? AND patent_id = ? AND is_deleted IS NULL LIMIT 1";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(id), patentId}, new BeanPropertyRowMapper(TableAnnualFeeMonitor.class));
	}

	public List<?> findAchievementTransformLimitedNumbers(int n) {
		String sql = "SELECT * FROM achievement_transform WHERE is_deleted IS NULL ORDER BY id ASC LIMIT " + n;
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(TableAchievementTransform.class));
	}

	public List<?> findAchievementTransformByStartIdLimitedNumbers(int startId, int n) {
		String sql = "SELECT * FROM achievement_transform WHERE id > ? AND is_deleted IS NULL ORDER BY id ASC LIMIT " + n;
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(startId)}, new BeanPropertyRowMapper(TableAchievementTransform.class));
	}

	public List<?> findAnnualFeeDetailByMonitorID(int id) {
		String sql = "SELECT * FROM annual_fee_detail WHERE annual_fee_monitor_id=?";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(id)}, new BeanPropertyRowMapper(TableAnnualFeeDetail.class));
	}
	
	public List<?> findAnnualFeeDetailByMonitorIDArray(Integer[] ids){
		if(ids.length <= 0){
			return null;
		}
		StringBuilder querys = new StringBuilder();
		for(int i=0; i<ids.length-1; i++){
			querys.append("?, ");
		}
		querys.append("?");
		String sql = "SELECT * FROM annual_fee_detail WHERE annual_fee_monitor_id IN (" + querys.toString() + ")";
		return getJdbcTemplate().query(sql, ids, new BeanPropertyRowMapper(TableAnnualFeeDetail.class));
	}

	public void addAnnualFeeMonitor(TableAnnualFeeMonitor data) {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO annual_fee_monitor (patent_id, customer_id, title, add_date, apply_date, applicant) VALUES (?, ?, ?, ?, ?, ?)";
		String patent_id = data.getPatent_id();
		int customer_id = data.getCustomer_id();
		String title = data.getTitle();
		Timestamp add_date = data.getAdd_date();
		Timestamp apply_date = data.getApply_date();
		String applicant = data.getApplicant();
		getJdbcTemplate().update(sql_insert, new Object[]{patent_id, customer_id, title, add_date, apply_date, applicant});
	}

	public void deleteAnnFeeMonitor(int custId, String patentId) {
		// TODO Auto-generated method stub
		String sql = "UPDATE annual_fee_monitor SET is_deleted='1' WHERE patent_id=? AND customer_id=? AND is_deleted IS NULL";
		getJdbcTemplate().update(sql, patentId, custId);
	}

	public void addFeeDetail(TableAnnualFeeDetail data) {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO annual_fee_detail (annual_fee_monitor_id, customer_apply_date, customer_apply_year, customer_apply_year_month) VALUES (?, ?, ?, ?)";
		int annFeeMonId = data.getAnnual_fee_monitor_id();
		Timestamp applyDate = data.getCustomer_apply_date();
		int applyYear = data.getCustomer_apply_year();
		int applyMonth = data.getCustomer_apply_year_month();
		getJdbcTemplate().update(sql_insert, new Object[]{annFeeMonId, applyDate, applyYear, applyMonth});
	}

	public void addAchievementTransform(int custId, String patentId, String title, Timestamp applyDate, double price) {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO achievement_transform (customer_id, patent_id, title, apply_date, price) VALUES (?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql_insert, new Object[]{custId, patentId, title, applyDate, price});
	}

}
