package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IFinanceManager;

public class SecCancelAppliedCreditLoanProcessor extends BaseProcessor{
	private String username;
	private String loanId;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		loanId = (String)scopes.get(Constants.MEMBER_FINANCE_CANCEL_CREDIT_LOAN_SERVICE_REQUEST_LOANID);
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Finance.xml");
		IFinanceManager financeManager = (IFinanceManager)ctx.getBean("financeManagerImpl");
		if(!StringUtility.isEmptyString(loanId)){
			financeManager.cancelCreditLoan(username, loanId);
			return Constants.EVENT_SUCCESS;
		}
		return Constants.EVENT_FAIL;
	}
}
