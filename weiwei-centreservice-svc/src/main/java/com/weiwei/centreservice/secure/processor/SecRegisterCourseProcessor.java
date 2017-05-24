package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.CourseRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICourseManager;

public class SecRegisterCourseProcessor extends BaseProcessor{
	
	private CourseRequest request;
	private String username;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		request = (CourseRequest)scopes.get(Constants.CS_TRAIN_COURSE_SERVICE_REQUEST);
		username = (String)scopes.get(Constants.USERNAME);
	}

	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Course.xml");
		ICourseManager courseManager = (ICourseManager)ctx.getBean("courseManagerImpl");
		if(request.getCourseId() != null && !"".equalsIgnoreCase(request.getCourseId().trim())){
			return courseManager.insertNewRegestration(username, request.getCourseId(), request.getNumberOfPeople());
		}
		return Constants.EVENT_FAIL;
	}

}
