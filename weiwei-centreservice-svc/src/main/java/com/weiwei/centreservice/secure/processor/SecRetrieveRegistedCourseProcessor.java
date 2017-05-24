package com.weiwei.centreservice.secure.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.beans.CourseBean;
import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICourseManager;

public class SecRetrieveRegistedCourseProcessor extends BaseProcessor{

	private List<?> courseList_db = null;
	private List<CourseBean> courseList_response = null;
	private String username;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Course.xml");
		ICourseManager courseManager = (ICourseManager)ctx.getBean("courseManagerImpl");
		courseList_db = courseManager.findRegistedCoursesByUsername(username);
		return Constants.EVENT_SUCCESS;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		if(courseList_db == null){
			return event;
		}
		
		courseList_response = new ArrayList<CourseBean>();
		for(int i=0; i<courseList_db.size(); i++){
			com.weiwei.svr.dbmodel.Course courseDBTable = (com.weiwei.svr.dbmodel.Course)courseList_db.get(i);
			CourseBean courseBean = new CourseBean(courseDBTable);
			courseList_response.add(courseBean);
			
		}
		scopes.put(Constants.SERVICE_RESPONSE, courseList_response);
		return event;
	}

}
