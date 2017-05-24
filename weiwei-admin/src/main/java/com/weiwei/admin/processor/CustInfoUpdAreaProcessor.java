package com.weiwei.admin.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.common.base.Constants;
import com.weiwei.admin.common.request.CustomerInfoUpdateAreaRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICustomerManager;

public class CustInfoUpdAreaProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private CustomerInfoUpdateAreaRequest request;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (CustomerInfoUpdateAreaRequest)scopes.get(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST);
	}
	@Override
	protected String executeProcess(Map scopes){
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		try{
			customerManager.updateInfoAreaByUsername(username, request.getProvince_id(), request.getCity_id());
			
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
