package com.weiwei.svr.dao;

import java.sql.Timestamp;
import java.util.List;

public interface ICourseDAO {
	public List<?> findByCategoryId(String categoryID, int startId, int endId);
	public List<?> findByCourseId(String CourseId);
	public String insertNewRegestration(String username, String CourseId, int count, Timestamp time);
	public List<?> findRegistedCourseIdByUsername(String username);
	public List<?> findByCourseIdList(List<String> courseIDList);
	public void removeRegistration(String username, String CourseId);
}
