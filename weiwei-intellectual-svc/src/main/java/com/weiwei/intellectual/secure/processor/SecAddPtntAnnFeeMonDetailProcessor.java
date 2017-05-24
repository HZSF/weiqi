package com.weiwei.intellectual.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IPatentManager;

public class SecAddPtntAnnFeeMonDetailProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private String patentId;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		patentId = (String)scopes.get(Constants.PATENTID_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		try{
			ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Patent.xml");
			IPatentManager patentManager = (IPatentManager) ctx.getBean("patentManagerImpl");
			patentManager.addDelegateMonitor(username, patentId);
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
