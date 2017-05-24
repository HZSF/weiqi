package com.weiwei.svr.manage.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IFinanceDAO;
import com.weiwei.svr.dbmodel.TableBank;
import com.weiwei.svr.dbmodel.TableLending;
import com.weiwei.svr.dbmodel.TableLoan;
import com.weiwei.svr.dbmodel.TableUserLoan;
import com.weiwei.svr.manage.IFinanceManager;
import com.weiwei.svr.utils.Constants;

@Service
public class FinanceManagerImpl implements IFinanceManager{

	@Autowired
	private IFinanceDAO financeDao;
	
	public String insertNewCreditLoanAndUserMap(String username, TableLoan tableLoanData){
		if(financeDao.isExistedCreditLoan(username))
			return Constants.EVENT_EXISTED;
		String loanId = financeDao.insertNewCreditLoan(username, tableLoanData);
		if(loanId != null && !"".equalsIgnoreCase(loanId)){
			financeDao.insertUsernameLoanData(username, loanId);
		}
		return Constants.EVENT_SUCCESS;
	}

	public List<?> findCreditLoanById(String id) {
		return financeDao.findCreditLoanById(id);
	}

	public List<?> findLendingById(String username, String id) {
		return financeDao.findLendingById(username, id);
	}

	public List<TableLoan> findCreditLoanByUsername(String username) {
		List<TableUserLoan> userLoanList = (List<TableUserLoan>)financeDao.findLoanIdByUsername(username);
		if(userLoanList == null || userLoanList.size() == 0){
			return null;
		}else{
			List<TableLoan> result = new ArrayList<TableLoan>();
			for(TableUserLoan userLoanData : userLoanList){
				List<?> subResult = financeDao.findCreditLoanById(userLoanData.getLoanId());
				for(int i=0; i<subResult.size(); i++){
					result.add((TableLoan)subResult.get(i));
				}
			}
			return result;
		}
	}

	public List<?> findLendingByUsername(String username) {
		return financeDao.findLendingByUsername(username);
	}

	public void cancelCreditLoan(String username, String loanId) {
		financeDao.removeUserLoanData(username, loanId);
		financeDao.removeCreditLoanData(loanId);
	}

	public void insertNewLending(TableLending tableLendData) {
		financeDao.insertNewLending(tableLendData);
	}

	public String insertNewLending(String username, double amount, Timestamp deadline, String bankAbbr) {
		if(financeDao.isExistedLending(username))
			return Constants.EVENT_EXISTED;
		List<TableBank> bankTableList = (List<TableBank>)financeDao.findBankTableByAbbr(bankAbbr);
		if(bankTableList != null && bankTableList.size() > 0){
			TableBank tableBank = bankTableList.get(0);
			TableLending tableLendData = new TableLending();
			tableLendData.setUserName(username);
			tableLendData.setDeadLine(deadline);
			tableLendData.setLoadSum(amount);
			tableLendData.setBankId(tableBank.getId());
			financeDao.insertNewLending(tableLendData);
			return Constants.EVENT_SUCCESS;
		}
		return Constants.EVENT_FAIL;
	}

	public List<?> findBankTableById(int id) {
		return financeDao.findBankTableById(id);
	}

	public void cancelLending(String username, String lendId) {
		financeDao.cancelLending(username, lendId);
	}
}
