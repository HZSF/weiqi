package com.weiwei.centreservice.secure.processor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.FinancialAppliedLendingBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableBank;
import com.weiwei.svr.dbmodel.TableLending;
import com.weiwei.svr.manage.IFinanceManager;

public class SecAppliedLendingProcessor extends BaseProcessor{
	private String lendId;
	private String username;
	protected ApplicationContext ctx;
	private FinancialAppliedLendingBean lendBean;
	
	@Override
	protected void preProcess(Map scopes){
		lendId = (String)scopes.get(Constants.MEMBER_FINANCE_LENDING_SERVICE_REQUEST_LOANID);
		lendBean = new FinancialAppliedLendingBean();
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Finance.xml");
		IFinanceManager financeManager = (IFinanceManager)ctx.getBean("financeManagerImpl");
		try{
			if(!StringUtility.isEmptyString(lendId)){
				List<TableLending> appliedLendList = (List<TableLending>)financeManager.findLendingById(username, lendId);
				if(appliedLendList != null && appliedLendList.size() > 0){
					TableLending tableLend = appliedLendList.get(0);
					List<TableBank> bankList = (List<TableBank>)financeManager.findBankTableById(tableLend.getBankId());
					if(bankList != null && bankList.size() > 0){
						TableBank tableBank = bankList.get(0);
						lendBean.setBankAbbr(tableBank.getAbbreviation());
					}
					lendBean.setDeadLine(tableLend.getDeadLine().toString().substring(0, 10));
					lendBean.setLoadSum(tableLend.getLoadSum());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, lendBean);
		return event;
	}
}
