package com.weiwei.svr.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IFinanceDAO;
import com.weiwei.svr.dbmodel.TableBank;
import com.weiwei.svr.dbmodel.TableLending;
import com.weiwei.svr.dbmodel.TableLoan;
import com.weiwei.svr.dbmodel.TableUserLoan;

@Service
public class FinanceDAOImpl extends JdbcDaoSupport implements IFinanceDAO{

	@Autowired
	public FinanceDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public boolean isExistedCreditLoan(String username){
		String sql = "SELECT * FROM loan l JOIN (SELECT loanId FROM dedecmsv57utf8sp1.loan_usermap lu WHERE lu.`userName`=?) AS loanid ON l.id=loanid AND (l.audit_flag IS NULL OR l.audit_flag<>9)";
		List<?> result = getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableLoan.class));
		if(result == null || result.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public String insertNewCreditLoan(String username, TableLoan tableLoanData) {
		Object[] params = (Object[])tableLoanData.getFieldValues();
		String sql_insert = "INSERT INTO loan (" + tableLoanData.toJdbcNameString() + ") VALUES (" + tableLoanData.toJdbcInsertString() + ")";
		getJdbcTemplate().update(sql_insert, params);
		String sql_query = "SELECT id FROM loan WHERE " + tableLoanData.toJdbcQueryString();
		List<TableLoan> result = getJdbcTemplate().query(sql_query, params, new BeanPropertyRowMapper(TableLoan.class));
		if(result.size() >= 1){
			int size = result.size();
			TableLoan tableLoan = result.get(size-1);
			return tableLoan.getId();
		}
		return null;
	}
	
	public void insertUsernameLoanData(String username, String loanId){
		String sql_query = "SELECT * FROM loan_usermap WHERE userName=? AND loanId=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username, loanId}, new BeanPropertyRowMapper(TableUserLoan.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO loan_usermap (userName, loanId) VALUES (?, ?)";
			getJdbcTemplate().update(sql_insert, new Object[]{username, loanId});
		}
	}

	public List<?> findCreditLoanById(String loanId) {
		String sql = "SELECT * FROM loan WHERE id=? AND (audit_flag IS NULL OR audit_flag<>9)";
		return getJdbcTemplate().query(sql, new String[]{loanId}, new BeanPropertyRowMapper(TableLoan.class));
	}

	public List<?> findLendingById(String username, String id) {
		String sql = "SELECT * FROM money_lending WHERE userName=? AND id=?";
		return getJdbcTemplate().query(sql, new String[]{username, id}, new BeanPropertyRowMapper(TableLending.class));
	}

	public List<?> findLoanIdByUsername(String username) {
		String sql = "SELECT * FROM loan_usermap WHERE userName=? AND is_cancelled IS NULL";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableUserLoan.class));
	}

	public List<?> findLendingByUsername(String username) {
		String sql = "SELECT * FROM money_lending WHERE userName=? AND (audit_flag IS NULL OR audit_flag<>9)";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableLending.class));
	}

	public void removeUserLoanData(String username, String loanId) {
		/*String sql_query = "SELECT * FROM loan_usermap WHERE userName=? AND loanId=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username, loanId}, new BeanPropertyRowMapper(TableUserLoan.class));
		if(result != null && result.size() != 0){
			String sql_delete = "DELETE FROM loan_usermap WHERE userName=? AND loanId=?";
			getJdbcTemplate().update(sql_delete, new Object[]{username, loanId});
		}*/
		String sql = "UPDATE loan_usermap SET is_cancelled='1' WHERE userName=? AND loanId=? AND is_cancelled IS NULL";
		getJdbcTemplate().update(sql, username, loanId);
	}

	public void removeCreditLoanData(String loanId) {
		/*String sql_query = "SELECT * FROM loan WHERE id=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{loanId}, new BeanPropertyRowMapper(TableLoan.class));
		if(result != null && result.size() != 0){
			String sql_delete = "DELETE FROM loan WHERE id=?";
			getJdbcTemplate().update(sql_delete, new Object[]{loanId});
		}*/
		String sql = "UPDATE loan SET audit_flag='9', audit_comment='取消申请' WHERE id=?";
		getJdbcTemplate().update(sql, loanId);
	}
	
	public List<?> findBankTableByAbbr(String Abbr){
		String sql = "SELECT * FROM bank WHERE abbreviation=?";
		return getJdbcTemplate().query(sql, new String[]{Abbr}, new BeanPropertyRowMapper(TableBank.class));
	}
	
	public List<?> findBankTableById(int id){
		String sql = "SELECT * FROM bank WHERE id=?";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(id)}, new BeanPropertyRowMapper(TableBank.class));
	}
	
	public boolean isExistedLending(String username){
		String sql = "SELECT * FROM money_lending WHERE userName=? AND (audit_flag IS NULL OR audit_flag<>9)";
		List<?> result = getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableLending.class));
		if(result == null || result.size() == 0){
			return false;
		}else{
			return true;
		}
	}

	public void insertNewLending(TableLending tableLendData) {
		String sql_insert = "INSERT INTO money_lending (loadSum, deadLine, bankId, userName) VALUES (?, ?, ?, ?)";
		getJdbcTemplate().update(sql_insert, new Object[]{tableLendData.getLoadSum(), tableLendData.getDeadLine(), tableLendData.getBankId(), tableLendData.getUserName()});
	}

	public void cancelLending(String username, String lendId) {
		/*String sql_query = "SELECT * FROM money_lending WHERE userName=? AND id=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username, lendId}, new BeanPropertyRowMapper(TableLending.class));
		if(result != null && result.size() != 0){
			String sql_delete = "DELETE FROM money_lending WHERE userName=? AND id=?";
			getJdbcTemplate().update(sql_delete, new Object[]{username, lendId});
		}*/
		String sql = "UPDATE money_lending SET audit_flag='9', audit_comment='取消申请' WHERE id=?";
		getJdbcTemplate().update(sql, lendId);
	}

}
