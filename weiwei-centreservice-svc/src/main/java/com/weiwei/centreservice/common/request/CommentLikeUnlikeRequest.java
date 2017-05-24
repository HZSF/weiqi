package com.weiwei.centreservice.common.request;

public class CommentLikeUnlikeRequest {
	public int sessionId;
	public String likeUnlike;
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public String getLikeUnlike() {
		return likeUnlike;
	}
	public void setLikeUnlike(String likeUnlike) {
		this.likeUnlike = likeUnlike;
	}
	
}
