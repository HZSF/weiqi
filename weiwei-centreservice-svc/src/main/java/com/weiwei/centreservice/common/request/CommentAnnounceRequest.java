package com.weiwei.centreservice.common.request;
// removable
public class CommentAnnounceRequest {
	public String parenCommentId;
	public String announceId;
	public String comment;
	public int startId;
    public int numbers;
	public String getParenCommentId() {
		return parenCommentId;
	}
	public void setParenCommentId(String parenCommentId) {
		this.parenCommentId = parenCommentId;
	}
	public String getAnnounceId() {
		return announceId;
	}
	public void setAnnounceId(String announceId) {
		this.announceId = announceId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getStartId() {
		return startId;
	}
	public void setStartId(int startId) {
		this.startId = startId;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	
}
