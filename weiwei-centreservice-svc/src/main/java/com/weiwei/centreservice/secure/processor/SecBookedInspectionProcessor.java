package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.InspectionIsAppliedBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IInspectionManager;

public class SecBookedInspectionProcessor extends BaseProcessor{

	private String username;
	protected ApplicationContext ctx;
	private boolean[] isApplied;
	private int inspection_length = 3;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Inspection.xml");
		IInspectionManager inspectionManager = (IInspectionManager)ctx.getBean("inspectionManagerImpl");
		isApplied = inspectionManager.getInspectionIsApplied(username);
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		InspectionIsAppliedBean bean = new InspectionIsAppliedBean();
		bean.setIsAppliedWood(Constants.NO);
		bean.setIsAppliedKids(Constants.NO);
		bean.setIsAppliedTextile(Constants.NO);
		if(isApplied.length >= inspection_length ){
			if(isApplied[0]){
				bean.setIsAppliedWood(Constants.YES);
			}
			if(isApplied[1]){
				bean.setIsAppliedKids(Constants.YES);
			}
			if(isApplied[2]){
				bean.setIsAppliedTextile(Constants.YES);
			}
		}
		scopes.put(Constants.SERVICE_RESPONSE, bean);
		return event;
	}
}
