package com.weiwei.centreservice.secure.processor;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.PropertyOnLendBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.PropertyLendListRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.PropertyLendJoinTable;
import com.weiwei.svr.manage.IPropertyManager;

public class SecPropertyFavoriteLendListProcessor extends BaseProcessor{
	protected ApplicationContext ctx;
	private String username;
	private PropertyLendListRequest request;
	private boolean moreComment;
	private List<PropertyOnLendBean> result_list;
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (PropertyLendListRequest)scopes.get(Constants.PROPERTY_REQUEST);
		if(request!= null && request.getStartId() > 0){
			moreComment = true;
		} else {
			moreComment = false;
		}
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Property.xml");
		IPropertyManager propertyMgr = (IPropertyManager)ctx.getBean("propertyManagerImpl");
		result_list = new ArrayList<PropertyOnLendBean>();
		ArrayList<PropertyLendJoinTable> tableBeanList = null;
		int numbers = 1;
		if(request.getNumbers() > 0){
			numbers = request.getNumbers();
		}
		if(moreComment){
			tableBeanList =  (ArrayList<PropertyLendJoinTable>)propertyMgr.getFavoriteLendListLimitedNumberStartFromId(username, request.getStartId(), numbers);
		}else{
			tableBeanList = (ArrayList<PropertyLendJoinTable>)propertyMgr.getFavoriteLendListLimitedNumber(username, numbers);
		}
		try{
			if(tableBeanList != null && tableBeanList.size() > 0){
				for(PropertyLendJoinTable table : tableBeanList){
					PropertyOnLendBean bean = new PropertyOnLendBean();
					Class<?> beanClass = bean.getClass();
					Class<?> tableClass = table.getClass();
					Field[] fields = beanClass.getFields();
					for(Field field : fields){
						String name = field.getName();
						if(!"submit_date".equalsIgnoreCase(name)){
							Field tableField = tableClass.getField(name);
							if(tableField != null){
								Object value = tableField.get(table);
								field.set(bean, value);
							}
						}
					}
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					bean.setSubmit_date(format.format(table.getSubmit_time()));
					result_list.add(bean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		scopes.put(Constants.SERVICE_RESPONSE, result_list);
		return Constants.EVENT_SUCCESS;
	}
}
