package com.weiwei.svr.manage.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.ICourseDAO;
import com.weiwei.svr.dbmodel.TableUserCourse;
import com.weiwei.svr.manage.ICourseManager;

@Service
public class CourseManagerImpl implements ICourseManager{

	@Autowired
	private ICourseDAO courseDao;
	
	public List<?> findByCategoryId(String categoryID, int startId, int endId) {
		// TODO Auto-generated method stub
		return courseDao.findByCategoryId(categoryID, startId, endId);
	}

	public List<?> findByCourseId(String CourseId) {
		// TODO Auto-generated method stub
		return courseDao.findByCourseId(CourseId);
	}

	public String insertNewRegestration(String username, String CourseId, int count) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		return courseDao.insertNewRegestration(username, CourseId, count, date);
	}

	public List<?> findRegistedCoursesByUsername(String username) {
		List<TableUserCourse> courseList = (List)courseDao.findRegistedCourseIdByUsername(username);
		if(courseList == null || courseList.size() == 0){
			return null;
		}
		List<String> courseIdList = new ArrayList<String>();
		for(int i=0; i<courseList.size(); i++){
			courseIdList.add(courseList.get(i).getCourseId());
		}
		
		return courseDao.findByCourseIdList(courseIdList);
	}
	
	public void removeRegistration(String username, String CourseId){
		courseDao.removeRegistration(username, CourseId);
		return;
	}

}
