package com.weiwei.centreservice.secure.processor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.FinancialAppliedCreditLoanBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableLoan;
import com.weiwei.svr.manage.IFinanceManager;

public class SecAppliedCreditLoanProcessor extends BaseProcessor{

	private String loanId;
	protected ApplicationContext ctx;
	private FinancialAppliedCreditLoanBean loanBean;
	
	@Override
	protected void preProcess(Map scopes){
		loanId = (String)scopes.get(Constants.MEMBER_FINANCE_CREDIT_LOAN_SERVICE_REQUEST_LOANID);
		loanBean = new FinancialAppliedCreditLoanBean();
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Finance.xml");
		IFinanceManager financeManager = (IFinanceManager)ctx.getBean("financeManagerImpl");
		try{
			if(!StringUtility.isEmptyString(loanId)){
				List<TableLoan> appliedLoanList = (List<TableLoan>)financeManager.findCreditLoanById(loanId);
				if(appliedLoanList != null && appliedLoanList.size() > 0){
					TableLoan tableLoan = appliedLoanList.get(0);
					Class<?> loanBeanClass = loanBean.getClass();
					Class<?> tableLoanClass = tableLoan.getClass();
					Field[] fields = loanBeanClass.getFields();
					for(Field field : fields){
						String name = field.getName();
						Field tableField = tableLoanClass.getField(name);
						if(tableField != null){
							Object value = tableField.get(tableLoan);
							field.set(loanBean, value);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, loanBean);
		return event;
	}

}
