package com.weiwei.admin.processor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.common.base.Constants;
import com.weiwei.admin.common.request.CustomerInfoUpdateRequest;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICustomerManager;

public class CustomerInfoUpdateProcessor extends BaseProcessor{
	private String username;
	protected ApplicationContext ctx;
	private CustomerInfoUpdateRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (CustomerInfoUpdateRequest)scopes.get(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes){
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		try{
			Class<?> requestClass = request.getClass();
			Field[] fields = requestClass.getFields();
			for(Field field : fields){
				String value_encode = (String)field.get(request);
				if(!StringUtility.isEmptyString(value_encode)){
					String fieldName = field.getName();
					String value = URLDecoder.decode(URLDecoder.decode(value_encode, "UTF-8"), "UTF-8");
					customerManager.updateInfoByUsername(username, fieldName, value);
					break;
				}
			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}catch(IllegalAccessException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}catch(IOException e){
			e.printStackTrace();
			return Constants.EVENT_FAIL;
		}
		return Constants.EVENT_SUCCESS;
	}
}
