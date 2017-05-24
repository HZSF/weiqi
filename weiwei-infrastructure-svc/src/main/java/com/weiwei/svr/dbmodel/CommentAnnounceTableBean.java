package com.weiwei.svr.dbmodel;
// removable
public class CommentAnnounceTableBean extends TableCommentAnnounce{
	public String userName;
	public int countLike;
	public String parentComment;
	public String parentCommentUserName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getCountLike() {
		return countLike;
	}
	public void setCountLike(int countLike) {
		this.countLike = countLike;
	}
	public String getParentComment() {
		return parentComment;
	}
	public void setParentComment(String parentComment) {
		this.parentComment = parentComment;
	}
	public String getParentCommentUserName() {
		return parentCommentUserName;
	}
	public void setParentCommentUserName(String parentCommentUserName) {
		this.parentCommentUserName = parentCommentUserName;
	}
	
}
