package com.weiwei.svr.dbmodel;

public class CommentTableJoinBean extends TableComment{
	public int announce_id;
	public String userName;
	public int countComments;
	public String commentGroup;
	public String usernameGroup;
	public int countLike;
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
	public int getAnnounce_id() {
		return announce_id;
	}
	public void setAnnounce_id(int announce_id) {
		this.announce_id = announce_id;
	}
	public int getCountComments() {
		return countComments;
	}
	public void setCountComments(int countComments) {
		this.countComments = countComments;
	}
	public String getCommentGroup() {
		return commentGroup;
	}
	public void setCommentGroup(String commentGroup) {
		this.commentGroup = commentGroup;
	}
	public String getUsernameGroup() {
		return usernameGroup;
	}
	public void setUsernameGroup(String usernameGroup) {
		this.usernameGroup = usernameGroup;
	}
	
}
