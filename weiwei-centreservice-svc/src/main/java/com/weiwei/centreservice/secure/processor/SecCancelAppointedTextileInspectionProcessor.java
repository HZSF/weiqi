package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IInspectionTextileManager;

public class SecCancelAppointedTextileInspectionProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Inspection.xml");
		IInspectionTextileManager inspectionTextileManager = (IInspectionTextileManager)ctx.getBean("inspectionTextileManagerImpl");
		inspectionTextileManager.cancelBookedTextileInspection(username);
		return Constants.EVENT_SUCCESS;
	}
}
