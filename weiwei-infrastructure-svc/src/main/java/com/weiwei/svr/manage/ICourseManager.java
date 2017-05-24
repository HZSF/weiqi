package com.weiwei.svr.manage;

import java.util.List;

public interface ICourseManager {
	public List<?> findByCategoryId(String categoryID, int startId, int endId);
	public List<?> findByCourseId(String CourseId);
	public String insertNewRegestration(String username, String CourseId, int count);
	public List<?> findRegistedCoursesByUsername(String username);
	public void removeRegistration(String username, String CourseId);
}
