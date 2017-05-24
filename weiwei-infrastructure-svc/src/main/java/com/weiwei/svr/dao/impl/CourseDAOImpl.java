package com.weiwei.svr.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.parent.common.base.Constants;
import com.weiwei.svr.dao.ICourseDAO;
import com.weiwei.svr.dbmodel.Course;
import com.weiwei.svr.dbmodel.CourseBodyTable;
import com.weiwei.svr.dbmodel.TableUserCourse;

@Service
public class CourseDAOImpl extends JdbcDaoSupport implements ICourseDAO{

	@Autowired
	public CourseDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public List<?> findByCategoryId(String categoryID, int startId, int endId) {
		String numbers = String.valueOf(endId-startId+1);
		String sql = "SELECT title, id FROM dede_archives WHERE typeid=? ORDER BY id ASC LIMIT "+numbers;
		return  getJdbcTemplate().query(sql, new String[]{categoryID}, new BeanPropertyRowMapper(Course.class));
	}

	//Query course body.
	public List<?> findByCourseId(String CourseId) {
		String sql = "SELECT body FROM dede_addonarticle WHERE aid=?";
		return getJdbcTemplate().query(sql, new String[]{CourseId}, new BeanPropertyRowMapper(CourseBodyTable.class));
	}

	public String insertNewRegestration(String username, String CourseId, int count, Timestamp time) {
		String sql_query = "SELECT * FROM user_coursemap WHERE userName=? AND courseId=? AND is_cancelled IS NULL";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username, CourseId}, new BeanPropertyRowMapper(TableUserCourse.class));
		if(result == null || result.size() == 0){
			String sql_insert = "INSERT INTO user_coursemap (userName, courseId, numberOfAppointPerson, appointTime) VALUES (?, ?, ?, ?)";
			getJdbcTemplate().update(sql_insert, new Object[]{username, CourseId, count, time});
			return Constants.EVENT_SUCCESS;
		}else{
			return Constants.EVENT_EXISTED;
		}
	}

	public List<?> findRegistedCourseIdByUsername(String username) {
		String sql = "SELECT courseId FROM user_coursemap WHERE userName=? AND is_cancelled IS NULL";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableUserCourse.class));
	}
	
	public List<?> findByCourseIdList(List<String> courseIDList){
		//Set<String> courseIDSet = new HashSet<String>(courseIDList);
		//MapSqlParameterSource parameters = new MapSqlParameterSource();
		//parameters.addValue("ids", courseIDSet);
		int length = courseIDList.size();
		if(length == 0){
			return null;
		}
		String[] parameters = new String[length];
		String subSql = "";
		for(int i=0; i<length-1; i++){
			subSql += "?,";
			parameters[i] = courseIDList.get(i);
		}
		subSql += "?";
		parameters[length-1] = courseIDList.get(length-1);
		String sql = "SELECT title, id, typeid FROM dede_archives WHERE id IN ("+subSql+")";
		return getJdbcTemplate().query(sql, parameters, new BeanPropertyRowMapper(Course.class));
	}
	
	public void removeRegistration(String username, String CourseId){
		/*String sql_query = "SELECT * FROM user_coursemap WHERE userName=? AND courseId=?";
		List<?> result = getJdbcTemplate().query(sql_query, new String[]{username, CourseId}, new BeanPropertyRowMapper(TableUserCourse.class));
		if(result != null && result.size() != 0){
			String sql_delete = "DELETE FROM user_coursemap WHERE userName=? AND courseId=?";
			getJdbcTemplate().update(sql_delete, new Object[]{username, CourseId});
		}*/
		String sql = "UPDATE user_coursemap SET is_cancelled='1' WHERE userName=? AND courseId=? AND is_cancelled IS NULL ";
		getJdbcTemplate().update(sql, username, CourseId);	
	}

}
