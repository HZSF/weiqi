package com.weiwei.svr.dao;

import java.sql.Timestamp;
import java.util.List;

public interface ICommentDAO {
	public void addComment(int userId, int parent_comment_id, int announce_id, String comments, Timestamp time);// removable
	public void addNewSessionComment(String username, int announce_id, String comments, Timestamp time);
	public void addOldSessionComment(String username, int session_id, String comments, Timestamp time);
	public void addCommentWithoutParent(int userId, int announce_id, String comments, Timestamp time);
	public List<?> getCommentLimitedNumbers(int announce_id, int n);
	public List<?> getCommentLimitedNumbersFromStartId(int announce_id, int start_id, int n);
	public List<?> getCommentLimitedNumbersJoin(int announce_id, int n); // removable
	public List<?> getCommentLimitedNumbersJoin(int n); // removable
	public List<?> getCommentLimitedNumbersFromStartIdJoin(int announce_id,int start_id, int n); // removable
	public List<?> getCommentLimitedNumbersFromStartIdJoin(int start_id, int n); // removable
	public List<?> getCommentLimitedNumbersJoinQuery(int announce_id, int n);
	public List<?> getCommentLimitedNumbersJoinQuery(int n);
	public List<?> getCommentLimitedNumbersFromStartIdJoinQuery(int announce_id, int start_id, int n);
	public List<?> getCommentLimitedNumbersFromStartIdJoinQuery(int start_id, int n);
	public void addLikeComment(String username, int session_id, Timestamp time);
	public void cancelLikeComment(int userId, int session_id);
	public int numberOfLikes(int comment_id);
	public List<?> getCommentById(int comment_id);
}
