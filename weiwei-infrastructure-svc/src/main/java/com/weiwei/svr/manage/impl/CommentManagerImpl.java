package com.weiwei.svr.manage.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.ICommentDAO;
import com.weiwei.svr.dao.ICustomerDAO;
import com.weiwei.svr.dbmodel.TableCommentAnnounce;
import com.weiwei.svr.dbmodel.TableCustomer;
import com.weiwei.svr.dbmodel.TableCustomers;
import com.weiwei.svr.manage.ICommentManager;

@Service
public class CommentManagerImpl implements ICommentManager{
	@Autowired
	ICommentDAO commentDao;
	@Autowired
	private ICustomerDAO customerDAO;

	public void addComment(String username, int parent_comment_id, int announce_id, String comments) {
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username); 
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			Calendar calendar = Calendar.getInstance();
			Timestamp date = new Timestamp(calendar.getTimeInMillis());
			if(parent_comment_id == 0){
				commentDao.addCommentWithoutParent(customerID, announce_id, comments, date);
			}else{
				commentDao.addComment(customerID, parent_comment_id, announce_id, comments, date);
			}
		}
	}
	
	public void addNewComment(String username, int session_id, int announce_id, String comments) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		if(session_id <= 0){
			commentDao.addNewSessionComment(username, announce_id, comments, date);
		} else {
			commentDao.addOldSessionComment(username, session_id, comments, date);
		}
	}
	

	public List<?> getCommentAnnounce(int announce_id) {
		return commentDao.getCommentLimitedNumbers(announce_id, 20);
	}

	public String findCustomerNameByUserId(int userId) {
		List<TableCustomers> tableCustomerList = (List<TableCustomers>)customerDAO.findCustomerByUserId(userId);
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			return tableCustomer.getUserName();
		}
		return null;
	}

	public void likeComment(String username, int session_id) {
		Calendar calendar = Calendar.getInstance();
		Timestamp date = new Timestamp(calendar.getTimeInMillis());
		commentDao.addLikeComment(username, session_id, date);
	}

	public void cancelLikeComment(String username, int session_id) {
		List<TableCustomers> tableCustomerList = (List<TableCustomers>) customerDAO.findCustomerIdByUsername(username);
		if(tableCustomerList != null && tableCustomerList.size() > 0){
			TableCustomers tableCustomer = tableCustomerList.get(0);
			int customerID = tableCustomer.getId();
			commentDao.cancelLikeComment(customerID, session_id);
		}
	}

	public int numberOfLikes(int comment_id) {
		return commentDao.numberOfLikes(comment_id);
	}

	public TableCommentAnnounce getCommentById(int comment_id) {
		ArrayList<TableCommentAnnounce> tableList = (ArrayList<TableCommentAnnounce>) commentDao.getCommentById(comment_id);
		if(tableList != null && tableList.size() > 0){
			return tableList.get(0);
		}
		return null;
	}

	public List<?> getCommentAnnounceFromStartId(int announce_id, int start_id, int number) {
		return commentDao.getCommentLimitedNumbersFromStartId(announce_id, start_id, number);
	}

	public List<?> getCommentLimitedNumbersJoin(int announce_id) {
		if(announce_id == 0){
			return commentDao.getCommentLimitedNumbersJoin(20);
		}else{
			return commentDao.getCommentLimitedNumbersJoin(announce_id, 20);
		}
	}

	public List<?> getCommentLimitedNumbersFromStartIdJoin(int announce_id, int start_id, int n) {
		if(announce_id == 0){
			return commentDao.getCommentLimitedNumbersFromStartIdJoin(start_id, n);
		}else{
			return commentDao.getCommentLimitedNumbersFromStartIdJoin(announce_id, start_id, n);
		}
	}
	
	public List<?> getCommentLimitedNumbers(int announce_id) {
		if(announce_id == 0){
			return commentDao.getCommentLimitedNumbersJoinQuery(20);
		}else{
			return commentDao.getCommentLimitedNumbersJoinQuery(announce_id, 20);
		}
	}

	public List<?> getCommentLimitedNumbersFromStartId(int announce_id, int start_id, int number) {
		if(announce_id == 0){
			return commentDao.getCommentLimitedNumbersFromStartIdJoinQuery(start_id, number);
		}else{
			return commentDao.getCommentLimitedNumbersFromStartIdJoinQuery(announce_id, start_id, number);
		}
	}
	
}
