package com.weiwei.centreservice.beans;

public class AnnounceCommentBean {
	public int id;
	public String customer_name;
	public int parent_comment_id;
	public String comment;
	public int announce_id;
	public String comment_time;
	public String parent_comment;
    public String parent_comment_customer;
	public int numberOfLike;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public int getParent_comment_id() {
		return parent_comment_id;
	}
	public void setParent_comment_id(int parent_comment_id) {
		this.parent_comment_id = parent_comment_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getAnnounce_id() {
		return announce_id;
	}
	public void setAnnounce_id(int announce_id) {
		this.announce_id = announce_id;
	}
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	public String getParent_comment() {
		return parent_comment;
	}
	public void setParent_comment(String parent_comment) {
		this.parent_comment = parent_comment;
	}
	public String getParent_comment_customer() {
		return parent_comment_customer;
	}
	public void setParent_comment_customer(String parent_comment_customer) {
		this.parent_comment_customer = parent_comment_customer;
	}
	public int getNumberOfLike() {
		return numberOfLike;
	}
	public void setNumberOfLike(int numberOfLike) {
		this.numberOfLike = numberOfLike;
	}
	
}
