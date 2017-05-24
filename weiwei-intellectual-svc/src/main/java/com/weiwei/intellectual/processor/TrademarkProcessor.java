package com.weiwei.intellectual.processor;

import java.util.List;
import java.util.Map;

import com.weiwei.intellectual.beans.TrademarkBean;
import com.weiwei.intellectual.common.base.Constants;
import com.weiwei.intellectual.common.request.TrademarkRequest;
import com.weiwei.intellectual.trademark.SearchHelper;
import com.weiwei.service.processors.base.BaseProcessor;

public class TrademarkProcessor extends BaseProcessor{
	
	private TrademarkRequest request;
	private String searchType;
	
	@Override
	protected void preProcess(Map scopes){
		request = (TrademarkRequest)scopes.get(Constants.TRADEMARK_SERVICE_REQUEST);
		searchType = request.getSearchType();
	}

	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		SearchHelper sh = new SearchHelper();
		if(searchType == null || searchType.equalsIgnoreCase("")){
			return Constants.EVENT_FAIL;
		}
		if("similar_detail".equalsIgnoreCase(searchType)){
			List<TrademarkBean> result_list = (List<TrademarkBean>) sh.getSimilarDetailResult(request);
			scopes.put(Constants.SERVICE_RESPONSE, result_list);
		}else if("similar_search".equalsIgnoreCase(searchType)){
			List<?> result_list = (List<?>) sh.getSimilarQueryResult(request);
			scopes.put(Constants.SERVICE_RESPONSE, result_list);
		}else if("similar_more".equalsIgnoreCase(searchType)){
			List<?> result_list = (List<?>) sh.getSimilarNextPageResult(request);
			scopes.put(Constants.SERVICE_RESPONSE, result_list);
		}
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		
		return event;
	}

}
