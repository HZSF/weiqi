package com.weiwei.common.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.admin.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.ProvinceTable;
import com.weiwei.svr.manage.ICustomerManager;

public class GetProvinceListProcessor extends BaseProcessor{
	protected ApplicationContext ctx;
	private List<String> beanList = null;

	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:admin/Customer.xml");
		ICustomerManager customerManager = (ICustomerManager)ctx.getBean("customerManagerImpl");
		beanList = new ArrayList<String>();
		List<ProvinceTable> tableList = (List<ProvinceTable>)customerManager.findProvinceList();
		if(tableList != null & tableList.size()>0){
			for(int i=0; i<tableList.size(); i++){
				beanList.add(tableList.get(i).getId()-1, tableList.get(i).getProvince_name());
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
