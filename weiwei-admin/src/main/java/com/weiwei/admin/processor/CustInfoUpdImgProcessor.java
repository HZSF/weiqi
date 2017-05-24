package com.weiwei.admin.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.common.base.Constants;
import com.weiwei.admin.common.request.CustomerInfoUpdateImageRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICustomerManager;

public class CustInfoUpdImgProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private CustomerInfoUpdateImageRequest imgRequest;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		imgRequest = (CustomerInfoUpdateImageRequest)scopes.get(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST);
	}
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		customerManager.updateInfoImgByUsername(username, imgRequest.getImg_data(), imgRequest.getSize());
		return Constants.EVENT_SUCCESS;
	}

}
