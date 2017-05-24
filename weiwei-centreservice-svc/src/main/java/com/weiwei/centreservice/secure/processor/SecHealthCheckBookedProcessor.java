package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IHealthyManager;

public class SecHealthCheckBookedProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private boolean isApplied = false;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Healthy.xml");
		IHealthyManager healthyManager = (IHealthyManager)ctx.getBean("healthyManagerImpl");
		isApplied = healthyManager.getHealthCheckIsApplied(username);
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		if(isApplied){
			return Constants.YES;
		}else{
			return Constants.NO;
		}
	}
}
