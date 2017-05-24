package com.weiwei.intellectual.secure.processor;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.intellectual.common.request.TrademarkAddMonitorRequest;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ITrademarkManager;

public class SecAddTrademarkMonitorProcessor extends BaseProcessor{
	
	private String username;
	protected ApplicationContext ctx;
	private TrademarkAddMonitorRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (TrademarkAddMonitorRequest)scopes.get(Constants.TRADEMARK_ADD_MONITOR_REQUEST);
	}

	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		try{
			ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Trademark.xml");
			ITrademarkManager trademarkManager = (ITrademarkManager) ctx.getBean("trademarkManagerImpl");
			String name = URLDecoder.decode(URLDecoder.decode(request.getName(), "UTF-8"), "UTF-8");
			String result = trademarkManager.addTrademarkMonitor(username, request.getRegNum(), Integer.valueOf(request.getCategoryNum()), name);
			if(!StringUtility.isEmptyString(result)){
				return result;
			}else{
				return Constants.EVENT_FAIL;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
