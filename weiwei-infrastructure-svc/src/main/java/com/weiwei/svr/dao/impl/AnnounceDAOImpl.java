package com.weiwei.svr.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dbmodel.Announce;
import com.weiwei.svr.dao.IAnnouncementDAO;

@Service
public class AnnounceDAOImpl extends JdbcDaoSupport implements IAnnouncementDAO{
	
	@Autowired
	public AnnounceDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public List<Announce> findBySequenceId(int startId, int endId) {
		// TODO Auto-generated method stub
		
		String numbers = String.valueOf(endId-startId+1);
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		Date currentDate = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date lastMonthDate = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		Date mBeforeLastMonthDate = cal.getTime(); 
		
		SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM");
		String month1 = formatter5.format(currentDate);
		String month2 = formatter5.format(lastMonthDate);
		String month3 = formatter5.format(mBeforeLastMonthDate);
		String sql = "SELECT aid, title, url FROM dede_co_htmls where nid=4 and (url like '%"+month1+"%' or url like '%"+month2+"%' or url like '%"+month3+"%') ORDER BY url desc LIMIT "+numbers;
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Announce.class));
		
	}

	public List<?> findByUrl(String url) {
		// TODO Auto-generated method stub
		String sql = "SELECT aid, result FROM dede_co_htmls WHERE nid=4 AND url=?";
		
		return  getJdbcTemplate().query(sql, new String[]{url}, new BeanPropertyRowMapper(Announce.class));
	}
	
	public List<Announce> findeByKeywords(String key) {
		String sqlString = "SELECT aid, title, url FROM dedecmsv57utf8sp1.dede_co_htmls WHERE nid = '4' and title like '%" + key + "%' order by url desc limit 50";
		return getJdbcTemplate().query(sqlString, new BeanPropertyRowMapper(Announce.class));
	}

}
