package com.weiwei.centreservice.beans;

public class CommentBean {
	public int session_id;
	public int id;
	public int announce_id;
	public String comment;
	public String comment_time;
	public String customer_name;
	public int numberOfLike;
	public int numberOfComment;
	public String[] commentList;
	public String[] customernameList;
	public int getSession_id() {
		return session_id;
	}
	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAnnounce_id() {
		return announce_id;
	}
	public void setAnnounce_id(int announce_id) {
		this.announce_id = announce_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public int getNumberOfLike() {
		return numberOfLike;
	}
	public void setNumberOfLike(int numberOfLike) {
		this.numberOfLike = numberOfLike;
	}
	public int getNumberOfComment() {
		return numberOfComment;
	}
	public void setNumberOfComment(int numberOfComment) {
		this.numberOfComment = numberOfComment;
	}
	public String[] getCommentList() {
		return commentList;
	}
	public void setCommentList(String[] commentList) {
		this.commentList = commentList;
	}
	public String[] getCustomernameList() {
		return customernameList;
	}
	public void setCustomernameList(String[] customernameList) {
		this.customernameList = customernameList;
	}
	
}
