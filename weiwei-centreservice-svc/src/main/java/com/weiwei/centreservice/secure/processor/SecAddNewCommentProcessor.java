package com.weiwei.centreservice.secure.processor;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weiwei.centreservice.common.base.Constants;
import com.weiwei.centreservice.common.request.CommentAddRequest;
import com.weiwei.parent.common.base.StringUtility;
import com.weiwei.service.processors.base.BaseProcessor;
import com.weiwei.svr.manage.ICommentManager;

public class SecAddNewCommentProcessor extends BaseProcessor{

		protected ApplicationContext ctx;
		private String username;
		private CommentAddRequest request;
		
		@Override
		protected void preProcess(Map scopes){
			username = (String)scopes.get(Constants.USERNAME);
			request = (CommentAddRequest)scopes.get(Constants.COMMENT_ANNOUNCE_ADD_REQUEST);
		}
		
		@Override
		protected String executeProcess(Map scopes) {
			// TODO Auto-generated method stub
			ctx = new ClassPathXmlApplicationContext("classpath*:centreservice/Announcement.xml");
			ICommentManager commentManager = (ICommentManager)ctx.getBean("commentManagerImpl");
			try{
				String comment = URLDecoder.decode(URLDecoder.decode(request.getCommentContent(), "UTF-8"), "UTF-8");
				int sessionId = 0;
				if(!StringUtility.isEmptyString(request.getSessionId())){
					sessionId = Integer.valueOf(request.getSessionId());
				}
				int announceId = 0;
				if(!StringUtility.isEmptyString(request.getAnnounceId())){
					announceId = Integer.valueOf(request.getAnnounceId());
				}
				commentManager.addNewComment(username, sessionId, announceId, comment);
				return Constants.EVENT_SUCCESS;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
}
