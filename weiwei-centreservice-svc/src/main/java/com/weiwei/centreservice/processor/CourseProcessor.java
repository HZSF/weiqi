package com.weiwei.centreservice.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.CourseRequest;
import com.weiwei.centreservice.beans.CourseBean;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICourseManager;

public class CourseProcessor extends BaseProcessor{
	
	private List<?> courseList_db = null;
	private List<CourseBean> courseList_response = null;
	private CourseRequest request = null;
	protected ApplicationContext ctx;
	
	@Override
	protected void preProcess(Map scopes){
		request = (CourseRequest)scopes.get(Constants.CS_TRAIN_COURSE_SERVICE_REQUEST);
	}

	@Override
	protected String executeProcess(Map scopes) {
		// TODO Auto-generated method stub
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Course.xml");
		ICourseManager courseManager = (ICourseManager)ctx.getBean("courseManagerImpl");
		if(request.getCategoryId()!=null && request.getStartNumber()!=null && request.getEndNumber()!=null){
			if(Integer.parseInt(request.getEndNumber()) >= Integer.parseInt(request.getStartNumber())){
				courseList_db = courseManager.findByCategoryId(request.getCategoryId(), Integer.parseInt(request.getStartNumber()), Integer.parseInt(request.getEndNumber()));
				return Constants.EVENT_CS_TRAIN_COURSE_LIST;
			}
		}
		else if(request.getCourseId()!=null && !"".equalsIgnoreCase(request.getCourseId().trim())){
			courseList_db = courseManager.findByCourseId(request.getCourseId());
			return Constants.EVENT_CS_TRAIN_COURSE_BODY;
		}
		return Constants.EVENT_FAIL;
	}
	
	@Override
	protected String postProcess(Map scopes, String event){
		courseList_response = new ArrayList<CourseBean>();
		for(int i=0; i<courseList_db.size(); i++){
			if(Constants.EVENT_CS_TRAIN_COURSE_LIST.equalsIgnoreCase(event)){
				com.weiwei.svr.dbmodel.Course courseDBTable = (com.weiwei.svr.dbmodel.Course)courseList_db.get(i);
				CourseBean courseBean = new CourseBean(courseDBTable);
				courseList_response.add(courseBean);
			}
			else if(Constants.EVENT_CS_TRAIN_COURSE_BODY.equalsIgnoreCase(event)){
				com.weiwei.svr.dbmodel.CourseBodyTable courseBodyTable = (com.weiwei.svr.dbmodel.CourseBodyTable)courseList_db.get(i);
				CourseBean courseBean = new CourseBean(courseBodyTable);
				courseList_response.add(courseBean);
			}
		}
		scopes.put(Constants.SERVICE_RESPONSE, courseList_response);
		return event;
	}

}
