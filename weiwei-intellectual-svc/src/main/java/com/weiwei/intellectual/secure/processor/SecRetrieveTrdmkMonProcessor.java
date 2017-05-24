package com.weiwei.intellectual.secure.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.beans.TrademarkBean;
import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableTrademarkMonitor;
import com.weiwei.svr.manage.ITrademarkManager;

public class SecRetrieveTrdmkMonProcessor extends BaseProcessor{

	private String username;
	protected ApplicationContext ctx;
	private List<TrademarkBean> result_list;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
	}
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		try{
			ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Trademark.xml");
			ITrademarkManager trademarkManager = (ITrademarkManager) ctx.getBean("trademarkManagerImpl");
			List<TableTrademarkMonitor> tablelist = (List<TableTrademarkMonitor>) trademarkManager.findTrademarkMonitorByUserName(username);
			if(tablelist != null && tablelist.size() > 0){
				result_list = new ArrayList<TrademarkBean>();
				for(TableTrademarkMonitor table : tablelist){
					TrademarkBean bean = new TrademarkBean();
					bean.setCategoryNum(String.valueOf(table.getCategory_number()));
					bean.setRegNum(String.valueOf(table.getRegister_id()));
					bean.setName(table.getName());
					result_list.add(bean);
				}
			}
			scopes.put(Constants.SERVICE_RESPONSE, result_list);
			return Constants.EVENT_SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
