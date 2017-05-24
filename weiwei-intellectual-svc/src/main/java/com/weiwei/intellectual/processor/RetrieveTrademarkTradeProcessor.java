package com.weiwei.intellectual.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.intellectual.beans.TrademarkTradeBean;
import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.intellectual.common.request.TrademarkTradeRetrieveRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.dbmodel.TableTrademarkTrade;
import com.weiwei.svr.manage.ITrademarkManager;

public class RetrieveTrademarkTradeProcessor extends BaseProcessor{

	private TrademarkTradeRetrieveRequest request;
	private List<TrademarkTradeBean> trademarkTradeBeansList;
	protected ApplicationContext ctx;
	private int initTrademarkNumbers = 20;
	
	@Override
	protected void preProcess(Map scopes){
		request = (TrademarkTradeRetrieveRequest)scopes.get(Constants.TRADEMARK_TRADE_RETRIEVE_REQUEST);
		trademarkTradeBeansList = new ArrayList<TrademarkTradeBean>();
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:intellectual/Trademark.xml");
		ITrademarkManager trademarkManager = (ITrademarkManager) ctx.getBean("trademarkManagerImpl");
		List<TableTrademarkTrade> tableTradeList = null;
		if(request == null || request.getStartFromId() == 0){
			tableTradeList = (List<TableTrademarkTrade>)trademarkManager.findTrademarkTradeLimitedNumbers(initTrademarkNumbers);
		}else{
			int numbers = request.getNumbers();
			if(numbers<=0){
				numbers = initTrademarkNumbers;
			}
			tableTradeList = (List<TableTrademarkTrade>)trademarkManager.findTrademarkTradeByStartIdLimitedNumbers(request.getStartFromId(),numbers);
		}
		if(tableTradeList != null && tableTradeList.size() > 0){
			for(TableTrademarkTrade tableTrade : tableTradeList){
				TrademarkTradeBean bean = new TrademarkTradeBean();
				bean.setId(tableTrade.getId());
				bean.setRegNum(tableTrade.getRegister_id());
				bean.setCategoryNum(String.valueOf(tableTrade.getCategory_number()));
				bean.setName(tableTrade.getName());
				bean.setPrice(tableTrade.getPrice_ask());
				if(tableTrade.getSubmit_date() != null)
					bean.setApply_date(tableTrade.getSubmit_date().toString());
				trademarkTradeBeansList.add(bean);
			}
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		scopes.put(Constants.SERVICE_RESPONSE, trademarkTradeBeansList);
		return event;
	}

}
