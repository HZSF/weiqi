package com.weiwei.svr.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.ICommentDAO;
import com.weiwei.svr.dbmodel.CommentAnnounceTableBean;
import com.weiwei.svr.dbmodel.CommentTableJoinBean;
import com.weiwei.svr.dbmodel.TableCommentAnnounce;

@Service
public class CommentDAOImpl extends JdbcDaoSupport implements ICommentDAO{
	@Autowired
	public CommentDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public void addComment(int userId, int parent_comment_id, int announce_id, String comments, Timestamp time){
		String sql_insert = "INSERT INTO comment_announce (customer_id, parent_comment_id, announce_id, comment, comment_time) VALUES (?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql_insert, new Object[]{userId, parent_comment_id, announce_id, comments, time});
	}
	
	public void addNewSessionComment(String username, int announce_id, String comments, Timestamp time) {
		String sql_insert1 = "INSERT INTO comment_session (announce_id) VALUES (" + String.valueOf(announce_id) + ")";
		String sql_insert2 = "INSERT INTO comment (comment_content, comment_time, session_id, customer_id) VALUES ('" + comments + "', '" + time + "', (SELECT LAST_INSERT_ID()), "
				+ "(SELECT id FROM customers WHERE userName='" +username+"'))";
		getJdbcTemplate().batchUpdate(new String[]{sql_insert1, sql_insert2});
	}
	
	public void addOldSessionComment(String username, int session_id, String comments, Timestamp time) {
		String sql_insert = "INSERT INTO comment (comment_content, comment_time, session_id, customer_id) VALUES (?, ?, ?, (SELECT id FROM customers WHERE userName=?))";
		getJdbcTemplate().update(sql_insert, new Object[]{comments, time, session_id, username});
	}
	
	public void addCommentWithoutParent(int userId, int announce_id, String comments, Timestamp time){
		String sql_insert = "INSERT INTO comment_announce (customer_id, announce_id, comment, comment_time) VALUES (?, ?, ?, ?)";
		getJdbcTemplate().update(sql_insert, new Object[]{userId, announce_id, comments, time});
	}

	public List<?> getCommentLimitedNumbers(int announce_id, int n) {
		String sql = "SELECT * FROM comment_announce WHERE announce_id = ? ORDER BY comment_time ASC LIMIT " + n;
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(announce_id)}, new BeanPropertyRowMapper(TableCommentAnnounce.class));
	}

	public void addLikeComment(String username, int session_id, Timestamp time) {
		String sql_insert = "INSERT INTO comment_like (customer_id, session_id, like_time) VALUES ((SELECT id FROM dedecmsv57utf8sp1.customers WHERE userName = ?), ?, ?)";
		getJdbcTemplate().update(sql_insert, new Object[]{username, session_id, time});
	}

	public void cancelLikeComment(int userId, int session_id) {
		String sql = "UPDATE comment_like SET is_cancelled='1' WHERE customer_id=? AND session_id=? AND is_cancelled IS NULL";
		getJdbcTemplate().update(sql, userId, session_id);
	}

	public int numberOfLikes(int comment_id) {
		String sql = "SELECT COUNT(*) FROM comment_announce_like WHERE comment_id=? AND is_cancelled IS NULL";
		return getJdbcTemplate().queryForObject(sql, new Object[]{comment_id}, Integer.class);
	}

	public List<?> getCommentById(int comment_id) {
		String sql = "SELECT * FROM comment_announce WHERE id = ?";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(comment_id)}, new BeanPropertyRowMapper(TableCommentAnnounce.class));
	}

	public List<?> getCommentLimitedNumbersFromStartId(int announce_id,int start_id, int n) {
		String sql = "SELECT * FROM comment_announce WHERE announce_id = ? AND id > ? ORDER BY comment_time ASC LIMIT " + n;
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(announce_id), String.valueOf(start_id)}, new BeanPropertyRowMapper(TableCommentAnnounce.class));
	}
	
	public List<?> getCommentLimitedNumbersJoin(int announce_id, int n) {
		String sql = "SELECT c4.*, c5.comment AS parentComment, c6.userName AS parentCommentUserName FROM " 
				+ "(SELECT c3.*, COUNT(c2.id) AS countLike FROM "
				+ "(SELECT c.*, c1.userName FROM dedecmsv57utf8sp1.comment_announce c "
				+ "JOIN dedecmsv57utf8sp1.customers c1 ON c1.id = c.customer_id AND c.announce_id = ? LIMIT " + n + ") c3 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce_like c2 ON c3.id=c2.comment_id AND c2.is_cancelled IS NULL "
				+ "GROUP BY c3.id) c4 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce c5 ON c4.parent_comment_id=c5.id AND c4.parent_comment_id IS NOT NULL "
				+ "LEFT JOIN dedecmsv57utf8sp1.customers c6 ON c5.customer_id=c6.id";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(announce_id)}, new BeanPropertyRowMapper(CommentAnnounceTableBean.class));
	}
	
	public List<?> getCommentLimitedNumbersJoin(int n) {
		String sql = "SELECT c4.*, c5.comment AS parentComment, c6.userName AS parentCommentUserName FROM " 
				+ "(SELECT c3.*, COUNT(c2.id) AS countLike FROM "
				+ "(SELECT c.*, c1.userName FROM dedecmsv57utf8sp1.comment_announce c "
				+ "JOIN dedecmsv57utf8sp1.customers c1 ON c1.id = c.customer_id) c3 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce_like c2 ON c3.id=c2.comment_id AND c2.is_cancelled IS NULL "
				+ "GROUP BY c3.id ORDER BY countLike DESC LIMIT " + n + ") c4 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce c5 ON c4.parent_comment_id=c5.id AND c4.parent_comment_id IS NOT NULL "
				+ "LEFT JOIN dedecmsv57utf8sp1.customers c6 ON c5.customer_id=c6.id";
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(CommentAnnounceTableBean.class));
	}
	
	public List<?> getCommentLimitedNumbersFromStartIdJoin(int announce_id,int start_id, int n) {
		String sql = "SELECT c4.*, c5.comment AS parentComment, c6.userName AS parentCommentUserName FROM "  
				+ "(SELECT c3.*, COUNT(c2.id) AS countLike FROM "
				+ "(SELECT c.*, c1.userName FROM dedecmsv57utf8sp1.comment_announce c "
				+ "JOIN dedecmsv57utf8sp1.customers c1 ON c1.id = c.customer_id AND c.announce_id = ? AND c.id > ? LIMIT " + n + ") c3 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce_like c2 ON c3.id=c2.comment_id AND c2.is_cancelled IS NULL "
				+ "GROUP BY c3.id) c4 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce c5 ON c4.parent_comment_id=c5.id AND c4.parent_comment_id IS NOT NULL "
				+ "LEFT JOIN dedecmsv57utf8sp1.customers c6 ON c5.customer_id=c6.id";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(announce_id), String.valueOf(start_id)}, new BeanPropertyRowMapper(CommentAnnounceTableBean.class));
	}
	
	public List<?> getCommentLimitedNumbersFromStartIdJoin(int start_id, int n) {
		String sql = "SELECT c4.*, c5.comment AS parentComment, c6.userName AS parentCommentUserName FROM "  
				+ "(SELECT c3.*, COUNT(c2.id) AS countLike FROM "
				+ "(SELECT c.*, c1.userName FROM dedecmsv57utf8sp1.comment_announce c "
				+ "JOIN dedecmsv57utf8sp1.customers c1 ON c1.id = c.customer_id AND c.id > ?) c3 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce_like c2 ON c3.id=c2.comment_id AND c2.is_cancelled IS NULL "
				+ "GROUP BY c3.id ORDER BY countLike DESC LIMIT " + n + ") c4 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_announce c5 ON c4.parent_comment_id=c5.id AND c4.parent_comment_id IS NOT NULL "
				+ "LEFT JOIN dedecmsv57utf8sp1.customers c6 ON c5.customer_id=c6.id";
		return getJdbcTemplate().query(sql, new String[]{String.valueOf(start_id)}, new BeanPropertyRowMapper(CommentAnnounceTableBean.class));
	}

	public List<?> getCommentLimitedNumbersJoinQuery(int announce_id, int n) {
		String sql = "SELECT c1.*, COUNT(cl.id) AS countLike FROM "
				+ "(SELECT cm.*, ctemp.announce_id, ctemp.countComments, ctemp.userName, ctemp.commentGroup, ctemp.usernameGroup FROM "
				+ "(SELECT cs.announce_id, cmc.userName, MIN(cmc.comment_time) AS first_comment, COUNT(cmc.id) AS countComments, "
				+ "GROUP_CONCAT(cmc.comment_content ORDER BY comment_time SEPARATOR ';-;') AS commentGroup, GROUP_CONCAT(cmc.userName ORDER BY comment_time SEPARATOR ';-;') AS usernameGroup "
				+ "FROM dedecmsv57utf8sp1.comment_session cs "
				+ "JOIN (SELECT cm.*, cust.userName "
				+ "FROM dedecmsv57utf8sp1.comment cm JOIN dedecmsv57utf8sp1.customers cust ON cust.id = cm.customer_id ORDER BY session_id, comment_time DESC) cmc "
				+ "ON cs.id = cmc.session_id AND announce_id = ? "
				+ "GROUP BY cs.id) ctemp, dedecmsv57utf8sp1.comment cm "
				+ "WHERE ctemp.first_comment = cm.comment_time "
				+ "ORDER BY comment_time DESC LIMIT " + n +") c1 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_like cl ON c1.session_id = cl.session_id AND cl.is_cancelled IS NULL "
				+ "GROUP BY c1.session_id ORDER BY comment_time DESC";
		return getJdbcTemplate().query(sql, new Object[]{announce_id}, new BeanPropertyRowMapper(CommentTableJoinBean.class));
	}

	public List<?> getCommentLimitedNumbersJoinQuery(int n) {
		String sql = "SELECT c1.*, COUNT(cl.id) AS countLike FROM "
				+ "(SELECT cm.*, ctemp.announce_id, ctemp.countComments, ctemp.userName, ctemp.commentGroup, ctemp.usernameGroup FROM "
				+ "(SELECT cs.announce_id, cmc.userName, MIN(cmc.comment_time) AS first_comment, COUNT(cmc.id) AS countComments, "
				+ "GROUP_CONCAT(cmc.comment_content ORDER BY comment_time SEPARATOR ';-;') AS commentGroup, GROUP_CONCAT(cmc.userName ORDER BY comment_time SEPARATOR ';-;') AS usernameGroup "
				+ "FROM dedecmsv57utf8sp1.comment_session cs "
				+ "JOIN (SELECT cm.*, cust.userName "
				+ "FROM dedecmsv57utf8sp1.comment cm JOIN dedecmsv57utf8sp1.customers cust ON cust.id = cm.customer_id ORDER BY session_id, comment_time DESC) cmc "
				+ "ON cs.id = cmc.session_id "
				+ "GROUP BY cs.id) ctemp, dedecmsv57utf8sp1.comment cm "
				+ "WHERE ctemp.first_comment = cm.comment_time "
				+ "ORDER BY comment_time DESC LIMIT " + n +") c1 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_like cl ON c1.session_id = cl.session_id AND cl.is_cancelled IS NULL "
				+ "GROUP BY c1.session_id ORDER BY comment_time DESC";
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(CommentTableJoinBean.class));
	}

	public List<?> getCommentLimitedNumbersFromStartIdJoinQuery(int announce_id, int start_id, int n) {
		String sql = "SELECT c1.*, COUNT(cl.id) AS countLike FROM "
				+ "(SELECT cm.*, ctemp.announce_id, ctemp.countComments, ctemp.userName, ctemp.commentGroup, ctemp.usernameGroup FROM "
				+ "(SELECT cs.announce_id, cmc.userName, MIN(cmc.comment_time) AS first_comment, COUNT(cmc.id) AS countComments, "
				+ "GROUP_CONCAT(cmc.comment_content ORDER BY comment_time SEPARATOR ';-;') AS commentGroup, GROUP_CONCAT(cmc.userName ORDER BY comment_time SEPARATOR ';-;') AS usernameGroup "
				+ "FROM dedecmsv57utf8sp1.comment_session cs "
				+ "JOIN (SELECT cm.*, cust.userName "
				+ "FROM dedecmsv57utf8sp1.comment cm JOIN dedecmsv57utf8sp1.customers cust ON cust.id = cm.customer_id ORDER BY session_id, comment_time DESC) cmc "
				+ "ON cs.id = cmc.session_id AND announce_id = ? "
				+ "GROUP BY cs.id) ctemp, dedecmsv57utf8sp1.comment cm "
				+ "WHERE ctemp.first_comment = cm.comment_time AND cm.session_id < ? "
				+ "ORDER BY comment_time DESC LIMIT " + n +") c1 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_like cl ON c1.session_id = cl.session_id AND cl.is_cancelled IS NULL "
				+ "GROUP BY c1.session_id ORDER BY comment_time DESC";
		return getJdbcTemplate().query(sql, new Object[]{announce_id, start_id}, new BeanPropertyRowMapper(CommentTableJoinBean.class));
	}

	public List<?> getCommentLimitedNumbersFromStartIdJoinQuery(int start_id,int n) {
		String sql = "SELECT c1.*, COUNT(cl.id) AS countLike FROM "
				+ "(SELECT cm.*, ctemp.announce_id, ctemp.countComments, ctemp.userName, ctemp.commentGroup, ctemp.usernameGroup FROM "
				+ "(SELECT cs.announce_id, cmc.userName, MIN(cmc.comment_time) AS first_comment, COUNT(cmc.id) AS countComments, "
				+ "GROUP_CONCAT(cmc.comment_content ORDER BY comment_time SEPARATOR ';-;') AS commentGroup, GROUP_CONCAT(cmc.userName ORDER BY comment_time SEPARATOR ';-;') AS usernameGroup "
				+ "FROM dedecmsv57utf8sp1.comment_session cs "
				+ "JOIN (SELECT cm.*, cust.userName "
				+ "FROM dedecmsv57utf8sp1.comment cm JOIN dedecmsv57utf8sp1.customers cust ON cust.id = cm.customer_id ORDER BY session_id, comment_time DESC) cmc "
				+ "ON cs.id = cmc.session_id "
				+ "GROUP BY cs.id) ctemp, dedecmsv57utf8sp1.comment cm "
				+ "WHERE ctemp.first_comment = cm.comment_time AND cm.session_id < ? "
				+ "ORDER BY comment_time DESC LIMIT " + n +") c1 "
				+ "LEFT JOIN dedecmsv57utf8sp1.comment_like cl ON c1.session_id = cl.session_id AND cl.is_cancelled IS NULL "
				+ "GROUP BY c1.session_id ORDER BY comment_time DESC";
		return getJdbcTemplate().query(sql, new Object[]{start_id}, new BeanPropertyRowMapper(CommentTableJoinBean.class));
	}

}
