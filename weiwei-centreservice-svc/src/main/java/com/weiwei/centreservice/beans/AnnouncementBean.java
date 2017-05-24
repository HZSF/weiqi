package com.weiwei.centreservice.beans;

import java.util.Map;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.base.UtilityMethods;
import com.weiwei.centreservice.model.Announcement;
import com.weiwei.svr.dbmodel.Announce;

public class AnnouncementBean extends Announcement{
	
	public AnnouncementBean(){
		super();
	}
	public AnnouncementBean(Announce a){
		super();
		title = a.getTitle();
		url = a.getUrl();
	}
	
	@Override
	public void fillAnnouncement(Map map){
		super.fillAnnouncement(map);
		String url = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_URL);
		fillPublishTime(url);
		
		String result = UtilityMethods.getMapValueString(map, Constants.DB_COLUMN_ANNOUNCE_RESULT);
		fillBody(result);
	}
	
	public void fillPublishTime(){
		fillPublishTime(url);
	}
	
	private void fillPublishTime(String url){
		if("".equalsIgnoreCase(url)){
			return;
		}
		url = url.substring(0, url.lastIndexOf("/"));
		publishTime = url.substring(url.lastIndexOf("/")+1);
	}
	
	public void fillBody(String result){
		if("".equalsIgnoreCase(result)){
			return;
		}
		//parse result
		String startKeyword = "{dede:field name='body'}";
		String endKeyword = "{/dede:field}";
		result = result.substring(result.indexOf(startKeyword));
		body = result.substring(startKeyword.length(), result.indexOf(endKeyword));
		
	}
}
