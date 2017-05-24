package com.weiwei.centreservice.secure.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.FinancialAppliedLoanBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableLending;
import com.weiwei.svr.dbmodel.TableLoan;
import com.weiwei.svr.manage.IFinanceManager;

public class SecAppliedLoanProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	public List<FinancialAppliedLoanBean> financialAppliedList;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		financialAppliedList = new ArrayList<FinancialAppliedLoanBean>();
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Finance.xml");
		IFinanceManager financeManager = (IFinanceManager)ctx.getBean("financeManagerImpl");
		List<TableLoan> appliedLoanList = financeManager.findCreditLoanByUsername(username);
		if(appliedLoanList != null && appliedLoanList.size() > 0){
			for(TableLoan tableLoan : appliedLoanList){
				FinancialAppliedLoanBean bean = new FinancialAppliedLoanBean();
				bean.setLoanType("Loan");
				bean.setLoanAmount(String.valueOf(tableLoan.getLoan_limit()));
				bean.setLoanId(tableLoan.getId());
				financialAppliedList.add(bean);
			}
		}
		List<TableLending> appliedLendingList = (List<TableLending>)financeManager.findLendingByUsername(username);
		if(appliedLendingList != null && appliedLendingList.size() > 0){
			for(TableLending tableLending : appliedLendingList){
				FinancialAppliedLoanBean bean = new FinancialAppliedLoanBean();
				bean.setLoanType("Lend");
				bean.setLoanAmount(String.valueOf(tableLending.getLoadSum()));
				bean.setLoanId(String.valueOf(tableLending.getId()));
				financialAppliedList.add(bean);
			}
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, financialAppliedList);
		return event;
	}

}
