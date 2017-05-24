package com.weiwei.svr.dbmodel;

import java.sql.Timestamp;

public class TableCommentAnnounce {
	public int id;
	public int customer_id;
	public String parent_comment_id;
	public String comment;
	public int announce_id;
	public Timestamp comment_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getParent_comment_id() {
		return parent_comment_id;
	}
	public void setParent_comment_id(String parent_comment_id) {
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
	public Timestamp getComment_time() {
		return comment_time;
	}
	public void setComment_time(Timestamp comment_time) {
		this.comment_time = comment_time;
	}
	
}
