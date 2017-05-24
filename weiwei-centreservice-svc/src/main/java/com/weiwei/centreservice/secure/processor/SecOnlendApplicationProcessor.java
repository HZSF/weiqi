package com.weiwei.centreservice.secure.processor;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.OnLendingFormSubmitRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IFinanceManager;

public class SecOnlendApplicationProcessor extends BaseProcessor{
	private OnLendingFormSubmitRequest request;
	private String username;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (OnLendingFormSubmitRequest)scopes.get(Constants.CS_FINANCE_ONLEND_SERVICE_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Finance.xml");
		IFinanceManager financeManager = (IFinanceManager)ctx.getBean("financeManagerImpl");
		if(request != null){
			return financeManager.insertNewLending(username, request.getLoadSum(), Timestamp.valueOf(request.getDeadLine()+" 00:00:00"), request.getBankAbbr());
		}else{
			return Constants.EVENT_FAIL;
		}
	}
}
