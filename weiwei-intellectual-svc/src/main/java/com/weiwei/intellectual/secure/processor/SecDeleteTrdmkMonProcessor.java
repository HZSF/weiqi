package com.weiwei.intellectual.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ITrademarkManager;

public class SecDeleteTrdmkMonProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private String regNum;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		regNum = (String)scopes.get(Constants.TRADEMARK_REQUEST);
	}
	@Override
	protected String executeProcess(Map scopes) {
		try{
			ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Trademark.xml");
			ITrademarkManager trademarkManager = (ITrademarkManager) ctx.getBean("trademarkManagerImpl");
			trademarkManager.cancelTrademarkMonitor(username, regNum);
		}catch(Exception e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
