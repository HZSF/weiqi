package com.weiwei.centreservice.common.request;

public class CommentFetchRequest {
	public String announceId;
	public int startId;
    public int numbers;
	public String getAnnounceId() {
		return announceId;
	}
	public void setAnnounceId(String announceId) {
		this.announceId = announceId;
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
