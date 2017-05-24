package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IHealthyManager;

public class SecBookHealthCheckProcessor extends BaseProcessor{

	private String username;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Healthy.xml");
		IHealthyManager healthyManager = (IHealthyManager)ctx.getBean("healthyManagerImpl");
		return healthyManager.insertNewHeathCheckBooking(username);
	}
}
