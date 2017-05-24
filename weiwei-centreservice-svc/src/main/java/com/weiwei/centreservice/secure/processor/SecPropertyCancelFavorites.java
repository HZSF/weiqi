package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.PropertyCancelFavoriteRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.IPropertyManager;

public class SecPropertyCancelFavorites extends BaseProcessor {
	protected ApplicationContext ctx;
	private String username;
	private PropertyCancelFavoriteRequest request;
	private int category;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PropertyCancelFavoriteRequest)scopes.get(Constants.PROPERTY_REQUEST);
		category = request.getCategory();
	}
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Property.xml");
		IPropertyManager propertyMgr = (IPropertyManager)ctx.getBean("propertyManagerImpl");
		int result = -1;
		switch(category){
		case 1:
			result = propertyMgr.cancelFavoritesSell(username, request.getId());
			break;
		case 2:
			result = propertyMgr.cancelFavoritesLend(username, request.getId());
			break;
		}
		if(result == 1)
			return Constants.EVENT_SUCCESS;
		else if(result == 0)
			return Constants.EVENT_EXISTED;
		else
			return Constants.EVENT_FAIL;
	}
}
