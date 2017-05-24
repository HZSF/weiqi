package com.weiwei.common.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.beans.CityBean;
import com.weiwei.admin.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableCities;
import com.weiwei.svr.manage.ICustomerManager;

public class GetCityListProcessor extends BaseProcessor{
	protected ApplicationContext ctx;
	private List<CityBean> beanList = null;
	private int province_id;
	
	@Override
	protected void preProcess(Map scopes){
		province_id =Integer.valueOf((String) scopes.get(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST));
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		beanList = new ArrayList<CityBean>();
		List<TableCities> tableList = (List<TableCities>)customerManager.findCityListByProvinceId(province_id);
		if(tableList != null & tableList.size()>0){
			for(int i=0; i<tableList.size(); i++){
				CityBean bean = new CityBean();
				bean.setId(tableList.get(i).getId());
				bean.setCityName(tableList.get(i).getCity_name());
				beanList.add(bean);
			}
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, beanList);
		return event;
	}
}
