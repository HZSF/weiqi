package com.weiwei.svr.manage;

import java.util.List;

import com.weiwei.svr.dbmodel.TableCommentAnnounce;

public interface ICommentManager {
	public void addComment(String username, int parent_comment_id, int announce_id, String comments);// removable
	public void addNewComment(String username, int session_id, int announce_id, String comments);
	public List<?> getCommentAnnounce(int announce_id); // removable
	public List<?> getCommentAnnounceFromStartId(int announce_id, int start_id, int number);// removable
	public List<?> getCommentLimitedNumbersJoin(int announce_id); // removable
	public List<?> getCommentLimitedNumbersFromStartIdJoin(int announce_id,int start_id, int n); // removable
	public List<?> getCommentLimitedNumbers(int announce_id);
	public List<?> getCommentLimitedNumbersFromStartId(int announce_id, int start_id, int number);
	public String findCustomerNameByUserId(int userId);
	public void likeComment(String username, int session_id);
	public void cancelLikeComment(String username, int session_id);
	public int numberOfLikes(int comment_id);
	public TableCommentAnnounce getCommentById(int comment_id);
}
