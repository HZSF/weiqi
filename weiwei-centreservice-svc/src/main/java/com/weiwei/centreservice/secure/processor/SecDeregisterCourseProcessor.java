package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICourseManager;

public class SecDeregisterCourseProcessor extends BaseProcessor{

	private String username;
	private String deregisterCourseId;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		deregisterCourseId = (String)scopes.get(Constants.MEMBER_COURSE_DEREGISTER_SERVICE_REQUEST_COURSEID);
		username = (String)scopes.get(Constants.USERNAME);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Course.xml");
		ICourseManager courseManager = (ICourseManager)ctx.getBean("courseManagerImpl");
		if(deregisterCourseId != null && !"".equalsIgnoreCase(deregisterCourseId.trim())){
			courseManager.removeRegistration(username, deregisterCourseId.trim());
			return Constants.EVENT_SUCCESS;
		}
		return Constants.EVENT_FAIL;
	}

}
