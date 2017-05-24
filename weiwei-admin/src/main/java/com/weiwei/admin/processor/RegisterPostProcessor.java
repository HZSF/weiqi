package com.weiwei.admin.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICustomerManager;

public class RegisterPostProcessor extends BaseProcessor{
	
	private String username;
	private String password;
	private String phoneNumber;
	private String companyName;
	protected ApplicationContext ctx;

	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		password = (String)scopes.get(Constants.PASSWORD);
		phoneNumber = (String)scopes.get(Constants.PHONENUMBER);
		companyName = (String)scopes.get(Constants.COMPANYNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		customerManager.registerNewCustomer(username, password, phoneNumber, companyName);
		return Constants.EVENT_SUCCESS;
	}

}
