package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.CreditLoanRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableLoan;
import com.weiwei.svr.manage.IFinanceManager;

public class SecLoanApplicationProcessor extends BaseProcessor{

	private CreditLoanRequest request;
	private String username;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		request = (CreditLoanRequest)scopes.get(Constants.CS_FINANCE_LOAN_SERVICE_REQUEST);
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Finance.xml");
		IFinanceManager financeManager = (IFinanceManager)ctx.getBean("financeManagerImpl");
		TableLoan tableLoanData = request;
		if(tableLoanData != null){
			return financeManager.insertNewCreditLoanAndUserMap(username, tableLoanData);
		}
		return Constants.EVENT_FAIL;
	}

}
