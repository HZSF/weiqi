package com.weiwei.centreservice.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.AnnouncementSearchRequest;
import com.weiwei.centreservice.beans.AnnouncementBean;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.AnnounceManager;
import com.weiwei.svr.manage.impl.AnnounceManagerImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnouncementSearchProcessor extends BaseProcessor{
	
	private List<?> announcementList_db = null;
	private List<AnnouncementBean> announcementList_response = null;
	private AnnouncementSearchRequest request = null;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		request = (AnnouncementSearchRequest)scopes.get(Constants.CS_ANNOUNCEMENT_SERVICE_REQUEST);
	}

	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Announcement.xml");
		AnnounceManager announceManager = (AnnounceManager) ctx.getBean(AnnounceManagerImpl.class);
		announcementList_db = announceManager.findByKeyword(request.getKeyword());
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		announcementList_response = new ArrayList<AnnouncementBean>();
		for(int i=0; i<announcementList_db.size(); i++){
			com.weiwei.svr.dbmodel.Announce announce = (com.weiwei.svr.dbmodel.Announce)announcementList_db.get(i);
			if(Constants.EVENT_SUCCESS.equalsIgnoreCase(event)){
				AnnouncementBean announcement = new AnnouncementBean(announce);
				announcement.fillPublishTime();
				announcementList_response.add(announcement);
			}
		}
		
		scopes.put(Constants.SERVICE_RESPONSE, announcementList_response);
		return event;
	}

}
