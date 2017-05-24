package com.weiwei.centreservice.secure.processor;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.CommentLikeUnlikeRequest;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICommentManager;

public class SecLikeCommentProcessor extends BaseProcessor{
	protected ApplicationContext ctx;
	private String username;
	private CommentLikeUnlikeRequest request;
	
	@Override
	protected void preProcess(Map scopes){
		username = (String)scopes.get(Constants.USERNAME);
		request = (CommentLikeUnlikeRequest)scopes.get(Constants.SERVICE_REQUEST);
	}
	
	@Override
	protected String executeProcess(Map scopes) {
		ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Announcement.xml");
		ICommentManager commentManager = (ICommentManager)ctx.getBean("commentManagerImpl");
		String request_like = request.getLikeUnlike();
		int sessionId = request.getSessionId();
		if(Constants.YES.equalsIgnoreCase(request_like)){
			commentManager.likeComment(username, sessionId);
		} else { //Cancel like
			commentManager.cancelLikeComment(username, sessionId);
		}
		return Constants.EVENT_SUCCESS;
	}
}
