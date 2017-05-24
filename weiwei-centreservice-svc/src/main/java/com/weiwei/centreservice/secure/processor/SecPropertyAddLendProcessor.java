package com.weiwei.centreservice.secure.processor;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.PropertyAddLendRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IPropertyManager;

public class SecPropertyAddLendProcessor extends BaseProcessor {
	protected ApplicationContext ctx;
	private String username;
	private PropertyAddLendRequest request;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PropertyAddLendRequest)scopes.get(Constants.PROPERTY_REQUEST);
	}
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Property.xml");
		IPropertyManager propertyMgr = (IPropertyManager)ctx.getBean("propertyManagerImpl");
		try{
			String region = URLDecoder.decode(URLDecoder.decode(request.getRegion(), "UTF-8"), "UTF-8");
			String category = URLDecoder.decode(URLDecoder.decode(request.getCategory(), "UTF-8"), "UTF-8");
			String description = URLDecoder.decode(URLDecoder.decode(request.getDescription(), "UTF-8"), "UTF-8");
			int property_area = request.getProperty_area();
			int levels = request.getLevels();
			int ask_price = request.getAsk_price();
			propertyMgr.insertLendProperty(username, region, category, property_area, levels, ask_price, description);
			return Constants.EVENT_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
