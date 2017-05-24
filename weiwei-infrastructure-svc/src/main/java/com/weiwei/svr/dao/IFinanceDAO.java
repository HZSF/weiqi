package com.weiwei.svr.dao;

import java.util.List;

import com.weiwei.svr.dbmodel.TableLending;
import com.weiwei.svr.dbmodel.TableLoan;

public interface IFinanceDAO {
	public boolean isExistedCreditLoan(String username);
	public String insertNewCreditLoan(String username, TableLoan tableLoanData);
	public void insertUsernameLoanData(String username, String loanId);
	public List<?> findCreditLoanById(String id);
	public List<?> findLoanIdByUsername(String username);
	public List<?> findLendingById(String username, String id);
	public List<?> findLendingByUsername(String username) ;
	public void removeUserLoanData(String username, String loanId);
	public void removeCreditLoanData(String loanId);
	public List<?> findBankTableByAbbr(String Abbr);
	public List<?> findBankTableById(int id);
	public boolean isExistedLending(String username);
	public void insertNewLending(TableLending tableLendData);
	public void cancelLending(String username, String lendId);
}
