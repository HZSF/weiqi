package com.weiwei.centreservice.beans;

import com.weiwei.centreservice.model.Course;
import com.weiwei.svr.dbmodel.CourseBodyTable;

public class CourseBean extends Course{
	public CourseBean(){
		super();
	}
	public CourseBean(com.weiwei.svr.dbmodel.Course c){
		super();
		setTitle(c.getTitle());
		setCourseID(c.getId());
		setTypeId(c.getTypeid());
	}
	public CourseBean(CourseBodyTable c){
		super();
		setBody(c.getBody());
		setCourseID(c.getAid());
	}
	
}
