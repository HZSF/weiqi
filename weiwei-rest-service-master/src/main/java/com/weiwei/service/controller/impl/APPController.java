package com.weiwei.service.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.admin.beans.CityBean;
import com.weiwei.centreservice.common.request.*;
import com.weiwei.centreservice.processor.*;
import com.weiwei.centreservice.beans.*;
import com.weiwei.common.processor.GetCityListProcessor;
import com.weiwei.common.processor.GetProvinceListProcessor;
import com.weiwei.intellectual.beans.*;
import com.weiwei.intellectual.common.request.*;
import com.weiwei.intellectual.processor.*;
import com.weiwei.intellectual.secure.processor.SecRetrievePatentAchievementTransformProcessor;
import com.weiwei.parent.common.base.Constants;
import com.weiwei.service.common.response.GeneralServiceResponse;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.version.bean.VersionInfo;

@RestController("regularController")
@RequestMapping("/weiwei")
public class APPController {
    
	private Map<String, Object> scopes = new HashMap<String, Object>();
	private BaseProcessor processor;
	
    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String test(){
    	System.out.println("regularController tesing");
    	return "Hello tester!";
    }
    
    @RequestMapping(value="/door", method=RequestMethod.GET)
    public int door(){
    	return 1;
    }
    
    @RequestMapping(value="/version")
    public VersionInfo versionService(){
    	VersionInfo versionInfo = new VersionInfo();
    	versionInfo.setVersion("1.1.2");
    	return versionInfo;
    }

    @RequestMapping(value="/intellecture/trademark", method = RequestMethod.POST)
	public GeneralServiceResponse intellectualService(@RequestBody TrademarkRequest serviceRequest) {
		// TODO Auto-generated method stub
    	
    	scopes.put("trademark_service", serviceRequest);
    	processor = new TrademarkProcessor();
    	processor.doProcess(scopes);
    	GeneralServiceResponse<Object> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			if(scopes.get("service_response") == null){
				return null;
			}
			serviceResponse.setResponseObjectList((ArrayList)scopes.get("service_response"));
		}	
		return serviceResponse;
	}
    
    @RequestMapping(value="/trademark/naming/escape")
    public GeneralServiceResponse<TrademarkNamingEscapeBean> nameingEscape(@RequestBody String str){
    	scopes.put(Constants.TRADEMARK_TRADE_NAMING_REQUEST, str);
    	processor = new TrademarkNamingEscapeProcessor();
    	processor.doProcess(scopes);
    	GeneralServiceResponse<TrademarkNamingEscapeBean> serviceResponse = new GeneralServiceResponse<>();
    	if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
    		serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
    	}
    	return serviceResponse;
    }
    
    @RequestMapping(value="/trademark/naming")
    public GeneralServiceResponse<String> trademarkNaming(@RequestBody TrademarkNamingRequest request){
    	scopes.put(Constants.TRADEMARK_TRADE_NAMING_REQUEST, request);
    	processor = new TrademarkNamingProcessor();
    	processor.doProcess(scopes);
    	GeneralServiceResponse<String> serviceResponse = new GeneralServiceResponse<>();
    	if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
    		serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
    	}
    	return serviceResponse;
    }
    
    @RequestMapping(value="/trademark/trade")
    public GeneralServiceResponse<TrademarkTradeBean> retrieveTrademarkTradeService(@RequestBody TrademarkTradeRetrieveRequest serviceRequest){
    	scopes.put(Constants.TRADEMARK_TRADE_RETRIEVE_REQUEST, serviceRequest);
    	processor = new RetrieveTrademarkTradeProcessor();
    	processor.doProcess(scopes);
    	GeneralServiceResponse<TrademarkTradeBean> serviceResponse = new GeneralServiceResponse<>();
    	if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
    		serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
    	}
    	return serviceResponse;
    }
    
    @RequestMapping(value="/patent/achieveTransform")
	public GeneralServiceResponse<PatentAchievementTransformBean> retrieveAchieveTransformPatentService(@RequestBody PatentAchievementTransformRequest serviceRequest){
		scopes.put(Constants.PATENT_ARCHIEVE_TRANSFORM_REQUEST, serviceRequest);
		processor = new SecRetrievePatentAchievementTransformProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PatentAchievementTransformBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}

	@RequestMapping(value="/centreservice/announcement")
	public GeneralServiceResponse<AnnouncementBean> announcementService(@RequestBody AnnouncementRequest serviceRequest) {
		// TODO Auto-generated method stub
		
		scopes.put(Constants.CS_ANNOUNCEMENT_SERVICE_REQUEST, serviceRequest);
		processor = new AnnouncementProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<AnnouncementBean> serviceResponse = new GeneralServiceResponse<AnnouncementBean>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get("service_response"));
		}	
		return serviceResponse;
	}
	
	@RequestMapping(value="/announcement/search")
	public GeneralServiceResponse<AnnouncementBean> announcementSearchService(@RequestBody AnnouncementSearchRequest serviceRequest) {
		scopes.put(Constants.CS_ANNOUNCEMENT_SERVICE_REQUEST, serviceRequest);
		processor = new AnnouncementSearchProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<AnnouncementBean> serviceResponse = new GeneralServiceResponse<AnnouncementBean>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get("service_response"));
		}	
		return serviceResponse;
	}
	
	@RequestMapping(value="/centreservice/course")
	public GeneralServiceResponse<CourseBean> courseService(@RequestBody CourseRequest serviceRequest){
		scopes.put(Constants.CS_TRAIN_COURSE_SERVICE_REQUEST, serviceRequest);
		processor = new CourseProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<CourseBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/announce/comment")
	/*public GeneralServiceResponse<AnnounceCommentBean> commentAnnounceService(@RequestBody CommentAnnounceRequest serviceRequest){
		scopes.put(Constants.COMMENT_ANNOUNCE_ADD_REQUEST, serviceRequest);
		processor = new AnnounceCommentProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<AnnounceCommentBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}*/
	public GeneralServiceResponse<CommentBean> commentAnnounceService(@RequestBody CommentFetchRequest serviceRequest){
		scopes.put(Constants.COMMENT_ANNOUNCE_ADD_REQUEST, serviceRequest);
		processor = new CommentFetchProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<CommentBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/sell")
	public GeneralServiceResponse<PropertyOnSellBean> propertyOnSellListService(@RequestBody PropertySellListRequest serviceRequest){
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new PropertySellListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnSellBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/property/fetch/lend")
	public GeneralServiceResponse<PropertyOnLendBean> propertyOnLendListService(@RequestBody PropertyLendListRequest serviceRequest){
		scopes.put(Constants.PROPERTY_REQUEST, serviceRequest);
		processor = new PropertyLendListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<PropertyOnLendBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/province")
	public GeneralServiceResponse<String> getProvinceList(){
		processor = new GetProvinceListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<String> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
	@RequestMapping(value="/city")
	public GeneralServiceResponse<CityBean> getCityList(@RequestBody String provinceId){
		scopes.put(Constants.ADMIN_CUSTOMER_INFO_UPDATE_REQUEST, provinceId);
		processor = new GetCityListProcessor();
		processor.doProcess(scopes);
		GeneralServiceResponse<CityBean> serviceResponse = new GeneralServiceResponse<>();
		if(scopes.containsKey(Constants.SERVICE_RESPONSE)){
			serviceResponse.setResponseObjectList((ArrayList)scopes.get(Constants.SERVICE_RESPONSE));
		}
		return serviceResponse;
	}
	
}

