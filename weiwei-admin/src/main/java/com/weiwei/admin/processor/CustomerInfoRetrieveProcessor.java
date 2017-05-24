package com.weiwei.admin.processor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.beans.CustomerInfoBean;
import com.weiwei.admin.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableCustomers;
import com.weiwei.svr.manage.ICustomerManager;

public class CustomerInfoRetrieveProcessor extends BaseProcessor{
	
	private String username;
	protected ApplicationContext ctx;
	private CustomerInfoBean bean;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		bean = new CustomerInfoBean();
	}

	@Override
	protected String executeProcess(Map scopes){
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		try{
			List<TableCustomers> customerList = (List<TableCustomers>)customerManager.findCustomerByUsername(username);
			if(customerList != null && customerList.size() > 0){
				TableCustomers customer = customerList.get(0);
				Class<?> beanClass = bean.getClass();
				Class<?> tableClass = customer.getClass();
				Field[] fields = beanClass.getFields();
				for(Field field : fields){
					String name = field.getName();
					Field tableField = tableClass.getField(name);
					if(tableField != null){
						Object value = tableField.get(customer);
						field.set(bean, value);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, bean);
		return event;
	}

}
