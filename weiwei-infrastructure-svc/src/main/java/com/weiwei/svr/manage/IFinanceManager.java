package com.weiwei.svr.manage;

import java.sql.Timestamp;
import java.util.List;

import com.weiwei.svr.dbmodel.TableLoan;

public interface IFinanceManager {
	public String insertNewCreditLoanAndUserMap(String username, TableLoan tableLoanData);
	public List<?> findCreditLoanById(String id);
	public List<TableLoan> findCreditLoanByUsername(String username);
	public List<?> findLendingById(String username, String id);
	public List<?> findLendingByUsername(String username);
	public void cancelCreditLoan(String username, String loanId);
	public String insertNewLending(String username, double amount, Timestamp deadline, String bankAbbr);
	public List<?> findBankTableById(int id);
	public void cancelLending(String username, String lendId);
}
